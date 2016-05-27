/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.scheduler

import java.nio.ByteBuffer
import java.util.Properties

import scala.language.existentials
import org.apache.spark._
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.executor.TaskMetrics
import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD
import org.apache.spark.shuffle.ShuffleWriter
import org.apache.spark.shuffle.sort.SortShuffleWriter

import scala.reflect.ClassTag

/**
 * A ShuffleMapTask divides the elements of an RDD into multiple buckets (based on a partitioner
 * specified in the ShuffleDependency).
 *
 * See [[org.apache.spark.scheduler.Task]] for more information.
 *
 * @param stageId id of the stage this task belongs to
 * @param stageAttemptId attempt id of the stage this task belongs to
 * @param taskBinary broadcast version of the RDD and the ShuffleDependency. Once deserialized,
 *                   the type should be (RDD[_], ShuffleDependency[_, _, _]).
 * @param partition partition of the RDD this task is associated with
 * @param locs preferred task execution locations for locality scheduling
 * @param metrics a [[TaskMetrics]] that is created at driver side and sent to executor side.
 * @param localProperties copy of thread-local properties set by the user on the driver side.
 */
private[spark] class ShuffleMapTask[U: ClassTag, C: ClassTag](
    stageId: Int,
    stageAttemptId: Int,
    taskBinary: Broadcast[Array[Byte]],
    partition: Partition,
    @transient private var locs: Seq[TaskLocation],
    metrics: TaskMetrics,
    localProperties: Properties)
  extends Task[MapStatus](stageId, stageAttemptId, partition.index, metrics, localProperties)
  with Logging {

  var _zeroValue: U  = _
  var _seqOp: (U, C) => U = null
  var _combOp: (U, U) => U = null

  def setAggregate(zeroValue: U, seqOp:(U, C) => U, combOp:(U, U) => U): Unit = {
    _zeroValue = zeroValue
    _seqOp = seqOp
    _combOp = combOp
  }

  /** A constructor used only in test suites. This does not require passing in an RDD. */
  def this(partitionId: Int) {
    this(0, 0, null, new Partition { override def index: Int = 0 }, null, null, new Properties)
  }

  @transient private val preferredLocs: Seq[TaskLocation] = {
    if (locs == null) Nil else locs.toSet.toSeq
  }

  override def runTask(context: TaskContext): MapStatus = {
    // Deserialize the RDD using the broadcast variable.
    val deserializeStartTime = System.currentTimeMillis()
    val ser = SparkEnv.get.closureSerializer.newInstance()
    val (rdd, dep) = ser.deserialize[(RDD[_], ShuffleDependency[_, _, _])](
      ByteBuffer.wrap(taskBinary.value), Thread.currentThread.getContextClassLoader)
    _executorDeserializeTime = System.currentTimeMillis() - deserializeStartTime

//    var writer: ShuffleWriter[Any, Any] = null
    var writer: ShuffleWriter[Any, Any] = null
    var sortWriter: SortShuffleWriter[Any, Any, C, U] = null
    try {
      val manager = SparkEnv.get.shuffleManager
      writer = manager.getWriter[Any, Any](dep.shuffleHandle, partitionId, context)
      sortWriter = writer.asInstanceOf[SortShuffleWriter[Any, Any, C, U]]
      sortWriter.setAggregate(_zeroValue, _seqOp, _combOp)
      sortWriter.write(rdd.iterator(partition, context).asInstanceOf[Iterator[_ <: Product2[Any, Any]]])
      sortWriter.stop(success = true).get
    } catch {
      case e: Exception =>
        try {
          if (writer != null) {
            writer.stop(success = false)
          }
        } catch {
          case e: Exception =>
            log.debug("Could not stop writer", e)
        }
        throw e
    }
  }

  override def preferredLocations: Seq[TaskLocation] = preferredLocs

  override def toString: String = "ShuffleMapTask(%d, %d)".format(stageId, partitionId)
}

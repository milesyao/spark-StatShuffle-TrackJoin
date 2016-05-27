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

package org.apache.spark.rdd

import scala.reflect.ClassTag
import org.apache.spark._
import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.serializer.Serializer
import org.apache.spark.storage.StorageLevel

private[spark] class SaRDDPartition(val idx: Int) extends Partition {
  override val index: Int = idx

  override def hashCode(): Int = index

  override def equals(other: Any): Boolean = super.equals(other)
}

class SaRDD[K: ClassTag, V: ClassTag, C: ClassTag] (
           @transient var prev: RDD[(K, V)],
           part: Partitioner,
           val zeroValue: C,
           val seqOp: (C, V) => C,
           val combOp: (C, C) => C)
extends RDD[(K,V)](prev.context, Nil) {

  override var storageLevel = StorageLevel.MEMORY_ONLY

  private var userSpecifiedSerializer: Option[Serializer] = None

  private var keyOrdering: Option[Ordering[K]] = None

  private var aggregator: Option[Aggregator[K, V, C]] = None

  private var mapSideCombine: Boolean = false

  var aggregateRes: C = _

  /** Set a serializer for this RDD's shuffle, or null to use the default (spark.serializer) */
  def setSerializer(serializer: Serializer): SaRDD[K, V, C] = {
    this.userSpecifiedSerializer = Option(serializer)
    this
  }

  /** Set key ordering for RDD's shuffle. */
  def setKeyOrdering(keyOrdering: Ordering[K]): SaRDD[K, V, C] = {
    this.keyOrdering = Option(keyOrdering)
    this
  }

  /** Set aggregator for RDD's shuffle. */
  def setAggregator(aggregator: Aggregator[K, V, C]): SaRDD[K, V, C] = {
    this.aggregator = Option(aggregator)
    this
  }

  /** Set mapSideCombine flag for RDD's shuffle. */
  def setMapSideCombine(mapSideCombine: Boolean): SaRDD[K, V, C] = {
    this.mapSideCombine = mapSideCombine
    this
  }

  override def getDependencies: Seq[Dependency[_]] = {
    val serializer = userSpecifiedSerializer.getOrElse {
      val serializerManager = SparkEnv.get.serializerManager
      if (mapSideCombine) {
        serializerManager.getSerializer(implicitly[ClassTag[K]], implicitly[ClassTag[C]])
      } else {
        serializerManager.getSerializer(implicitly[ClassTag[K]], implicitly[ClassTag[V]])
      }
    }
    val saDependency:ShuffleDependency[K,V,C] =
      new ShuffleDependency(prev, part, serializer, keyOrdering, aggregator, mapSideCombine)

    saDependency.setAggregate(zeroValue, seqOp, combOp)
    List(saDependency)
  }

  override val partitioner = Some(part)

  override def getPartitions: Array[Partition] = {
    Array.tabulate[Partition](part.numPartitions)(i => new ShuffledRDDPartition(i))
  }

  override protected def getPreferredLocations(partition: Partition): Seq[String] = {
    val tracker = SparkEnv.get.mapOutputTracker.asInstanceOf[MapOutputTrackerMaster]
    val dep = dependencies.head.asInstanceOf[ShuffleDependency[K, V, C]]
    tracker.getPreferredLocationsForShuffle(dep, partition.index)
  }

  override def compute(split: Partition, context: TaskContext): Iterator[(K, C)] = {
    val tracker = SparkEnv.get.mapOutputTracker.asInstanceOf[MapOutputTrackerMaster]
    val dep = dependencies.head.asInstanceOf[ShuffleDependency[K, V, C]]
    SparkEnv.get.shuffleManager.getReader(dep.shuffleHandle, split.index, split.index + 1, context)
      .read()
      .asInstanceOf[Iterator[(K, C)]]
  }

  def getAggregateRes(): C = {
    val tracker = SparkEnv.get.mapOutputTracker.asInstanceOf[MapOutputTrackerMaster]
    val dep = dependencies.head.asInstanceOf[ShuffleDependency[K, V, C]]
    var saRes = zeroValue
    val intermediate:Array[C] = tracker.getSaRes(dep.shuffleId).get
    for(subRes <- intermediate) {
      saRes = combOp(subRes, saRes)
    }
    saRes
  }

  override def clearDependencies() {
    super.clearDependencies()
    prev = null
  }

  override def iterator(split: Partition, context: TaskContext): Iterator[(K,V)] = {
      getOrCompute(split, context)
  }

}

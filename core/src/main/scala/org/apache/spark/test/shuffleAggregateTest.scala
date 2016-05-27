package org.apache.spark.test
import org.apache.log4j.Logger
import org.apache.log4j.Level

import scala.io.Source
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.SaRDD


/**
  * Created by cnyao on 5/15/16.
  */
object shuffleAggregateTest {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    Logger.getLogger("test").setLevel(Level.DEBUG)
    val conf = new SparkConf().setAppName("Shuffle Aggregate Test")
      .setMaster("local")
      .set("spark.shuffle.spill", "false")

    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(List(1, 2, 3, 4, 2, 1), 2);

    val rddWithKey = rdd.map(x => (x, x+1))

    val seqOp = (i:Int, j:Int) => i+j
    val combOp = (i: Int, j:Int) => i+j
    val saRDD = rddWithKey.shuffleAggregate(new HashPartitioner(2),0, seqOp, combOp)

    printf(saRDD.getAggregateRes().toString())

  }
}

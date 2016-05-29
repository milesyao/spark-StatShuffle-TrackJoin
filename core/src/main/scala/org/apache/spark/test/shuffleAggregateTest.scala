package org.apache.spark.test
import org.apache.log4j.Logger
import org.apache.log4j.Level

import scala.io.Source

import org.apache.spark.{HashPartitioner, SparkContext, SparkConf}
import org.apache.spark.SparkContext._
import org.apache.spark.Logging

/**
  * Created by cnyao on 5/15/16.
  */
object shuffleAggregateTest extends Logging{
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    Logger.getLogger("test").setLevel(Level.DEBUG)
    val conf = new SparkConf().setAppName("GroupBy Test")
      .setMaster("local")
      .set("spark.shuffle.manager", "hash")
      .set("spark.shuffle.spill", "false")
    val sc = new SparkContext(conf)
    //get big data
    val rdd = sc.parallelize(List(1, 2, 3, 4, 2, 1), 2)
    val rddWithKey = rdd.map(x => (x, 1))
    val finalRDD = rddWithKey.shuffleAggregate(new HashPartitioner(2),1)

    printf(finalRDD.collect().mkString(",") + "\n")
    printf(finalRDD.getAggregateRes().get.mkString(",") + "\n")

  }
}

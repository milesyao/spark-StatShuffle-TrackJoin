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
    val rdd = sc.parallelize(List(1, 2, 3, 4, 2, -1, -4), 2)
    val rddWithKey = rdd.map(x => if(x>0) (x, 10) else (x, -1))

//    val createCombiner = (input1: Int) => 1
//    val mergeValue  = (input1: Int, input2: Int) => input2
//    val combineValue = (input1: Int, input2: Int) => input1 + input2
    val createCombiner = (input1: Int) => {
//      printf("creating!")
      true
    }
    val mergeValue = (input1: Boolean, input2: Int) => {
      if (input2 >= 0) {
        true
      } else {
        false
      }
    }
    val combineValue = (input1: Boolean, input2: Boolean) => {
      if(input1 && input2) {
        true
      } else {
        false
      }
    }
    printf(rddWithKey.collect.mkString(",") + "\n")
    val finalRDD = rddWithKey.shuffleAggregate(new HashPartitioner(2),createCombiner, mergeValue, combineValue)

//    printf(finalRDD.collect().mkString(",") + "\n")
    printf(finalRDD.getAggregateRes().get.mkString(",") + "\n")

  }
}

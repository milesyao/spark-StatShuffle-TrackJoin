package org.apache.spark.mllib.stat.test;
// no position
/**
 * Performs Welch's 2-sample t-test. The null hypothesis is that the two data sets have equal mean.
 * This test does not assume equal variance between the two samples and does not assume equal
 * sample size.
 * <p>
 * @see http://en.wikipedia.org/wiki/Welch%27s_t_test
 */
  class WelchTTest implements org.apache.spark.mllib.stat.test.StreamingTestMethod, org.apache.spark.Logging {
  static public final  java.lang.String methodName () { throw new RuntimeException(); }
  static public final  java.lang.String nullHypothesis () { throw new RuntimeException(); }
  static private final  com.twitter.chill.MeatLocker<org.apache.commons.math3.stat.inference.TTest> tTester () { throw new RuntimeException(); }
  static public  org.apache.spark.streaming.dstream.DStream<org.apache.spark.mllib.stat.test.StreamingTestResult> doTest (org.apache.spark.streaming.dstream.DStream<scala.Tuple2<org.apache.spark.util.StatCounter, org.apache.spark.util.StatCounter>> data) { throw new RuntimeException(); }
  static private  org.apache.spark.mllib.stat.test.StreamingTestResult test (org.apache.spark.util.StatCounter statsA, org.apache.spark.util.StatCounter statsB) { throw new RuntimeException(); }
}

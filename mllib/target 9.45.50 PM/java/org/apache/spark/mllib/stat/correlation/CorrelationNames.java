package org.apache.spark.mllib.stat.correlation;
// no position
/**
 * Maintains supported and default correlation names.
 * <p>
 * Currently supported correlations: <code>pearson</code>, <code>spearman</code>.
 * Current default correlation: <code>pearson</code>.
 * <p>
 * After new correlation algorithms are added, please update the documentation here and in
 * Statistics.scala for the correlation APIs.
 */
  class CorrelationNames {
  static public  scala.collection.immutable.Map<java.lang.String, org.apache.spark.Logging> nameToObjectMap () { throw new RuntimeException(); }
  static public  java.lang.String defaultCorrName () { throw new RuntimeException(); }
}

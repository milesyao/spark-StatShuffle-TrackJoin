package org.apache.spark.mllib.regression;
/**
 * Class that represents the features and labels of a data point.
 * <p>
 * param:  label Label for this data point.
 * param:  features List of features for this data point.
 */
public  class LabeledPoint implements scala.Product, scala.Serializable {
  static public  org.apache.spark.mllib.regression.LabeledPoint parse (java.lang.String s) { throw new RuntimeException(); }
  public  double label () { throw new RuntimeException(); }
  public  org.apache.spark.mllib.linalg.Vector features () { throw new RuntimeException(); }
  // not preceding
  public   LabeledPoint (double label, org.apache.spark.mllib.linalg.Vector features) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}

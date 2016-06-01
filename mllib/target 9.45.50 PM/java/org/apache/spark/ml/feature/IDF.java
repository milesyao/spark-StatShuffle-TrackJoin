package org.apache.spark.ml.feature;
/**
 * :: Experimental ::
 * Compute the Inverse Document Frequency (IDF) given a collection of documents.
 */
public final class IDF extends org.apache.spark.ml.Estimator<org.apache.spark.ml.feature.IDFModel> implements org.apache.spark.ml.feature.IDFBase, org.apache.spark.ml.util.DefaultParamsWritable {
  static public  org.apache.spark.ml.feature.IDF load (java.lang.String path) { throw new RuntimeException(); }
  public  java.lang.String uid () { throw new RuntimeException(); }
  // not preceding
  public   IDF (java.lang.String uid) { throw new RuntimeException(); }
  public   IDF () { throw new RuntimeException(); }
  /** @group setParam */
  public  org.apache.spark.ml.feature.IDF setInputCol (java.lang.String value) { throw new RuntimeException(); }
  /** @group setParam */
  public  org.apache.spark.ml.feature.IDF setOutputCol (java.lang.String value) { throw new RuntimeException(); }
  /** @group setParam */
  public  org.apache.spark.ml.feature.IDF setMinDocFreq (int value) { throw new RuntimeException(); }
  public  org.apache.spark.ml.feature.IDFModel fit (org.apache.spark.sql.DataFrame dataset) { throw new RuntimeException(); }
  // not preceding
  public  org.apache.spark.sql.types.StructType transformSchema (org.apache.spark.sql.types.StructType schema) { throw new RuntimeException(); }
  public  org.apache.spark.ml.feature.IDF copy (org.apache.spark.ml.param.ParamMap extra) { throw new RuntimeException(); }
}

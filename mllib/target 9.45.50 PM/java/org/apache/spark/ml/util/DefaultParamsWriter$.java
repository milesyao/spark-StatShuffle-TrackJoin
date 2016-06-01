package org.apache.spark.ml.util;
// no position
  class DefaultParamsWriter$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final DefaultParamsWriter$ MODULE$ = null;
  public   DefaultParamsWriter$ () { throw new RuntimeException(); }
  /**
   * Saves metadata + Params to: path + "/metadata"
   *  - class
   *  - timestamp
   *  - sparkVersion
   *  - uid
   *  - paramMap
   *  - (optionally, extra metadata)
   * @param extraMetadata  Extra metadata to be saved at same level as uid, paramMap, etc.
   * @param paramMap  If given, this is saved in the "paramMap" field.
   *                  Otherwise, all {@link org.apache.spark.ml.param.Param}s are encoded using
   *                  {@link org.apache.spark.ml.param.Param.jsonEncode()}.
   * @param instance (undocumented)
   * @param path (undocumented)
   * @param sc (undocumented)
   */
  public  void saveMetadata (org.apache.spark.ml.param.Params instance, java.lang.String path, org.apache.spark.SparkContext sc, scala.Option<org.json4s.JsonAST.JObject> extraMetadata, scala.Option<org.json4s.JsonAST.JValue> paramMap) { throw new RuntimeException(); }
}

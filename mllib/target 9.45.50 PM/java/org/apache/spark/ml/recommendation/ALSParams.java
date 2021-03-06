package org.apache.spark.ml.recommendation;
/**
 * Common params for ALS.
 */
  interface ALSParams extends org.apache.spark.ml.recommendation.ALSModelParams, org.apache.spark.ml.param.shared.HasMaxIter, org.apache.spark.ml.param.shared.HasRegParam, org.apache.spark.ml.param.shared.HasPredictionCol, org.apache.spark.ml.param.shared.HasCheckpointInterval, org.apache.spark.ml.param.shared.HasSeed {
  /**
   * Param for rank of the matrix factorization (>= 1).
   * Default: 10
   * @group param
   * @return (undocumented)
   */
  public  org.apache.spark.ml.param.IntParam rank () ;
  /** @group getParam */
  public  int getRank () ;
  /**
   * Param for number of user blocks (>= 1).
   * Default: 10
   * @group param
   * @return (undocumented)
   */
  public  org.apache.spark.ml.param.IntParam numUserBlocks () ;
  /** @group getParam */
  public  int getNumUserBlocks () ;
  /**
   * Param for number of item blocks (>= 1).
   * Default: 10
   * @group param
   * @return (undocumented)
   */
  public  org.apache.spark.ml.param.IntParam numItemBlocks () ;
  /** @group getParam */
  public  int getNumItemBlocks () ;
  /**
   * Param to decide whether to use implicit preference.
   * Default: false
   * @group param
   * @return (undocumented)
   */
  public  org.apache.spark.ml.param.BooleanParam implicitPrefs () ;
  /** @group getParam */
  public  boolean getImplicitPrefs () ;
  /**
   * Param for the alpha parameter in the implicit preference formulation (>= 0).
   * Default: 1.0
   * @group param
   * @return (undocumented)
   */
  public  org.apache.spark.ml.param.DoubleParam alpha () ;
  /** @group getParam */
  public  double getAlpha () ;
  /**
   * Param for the column name for ratings.
   * Default: "rating"
   * @group param
   * @return (undocumented)
   */
  public  org.apache.spark.ml.param.Param<java.lang.String> ratingCol () ;
  /** @group getParam */
  public  java.lang.String getRatingCol () ;
  /**
   * Param for whether to apply nonnegativity constraints.
   * Default: false
   * @group param
   * @return (undocumented)
   */
  public  org.apache.spark.ml.param.BooleanParam nonnegative () ;
  /** @group getParam */
  public  boolean getNonnegative () ;
  /**
   * Validates and transforms the input schema.
   * @param schema input schema
   * @return output schema
   */
  public  org.apache.spark.sql.types.StructType validateAndTransformSchema (org.apache.spark.sql.types.StructType schema) ;
}

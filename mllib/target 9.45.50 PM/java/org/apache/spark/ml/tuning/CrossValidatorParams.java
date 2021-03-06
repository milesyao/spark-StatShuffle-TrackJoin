package org.apache.spark.ml.tuning;
/**
 * Params for {@link CrossValidator} and {@link CrossValidatorModel}.
 */
  interface CrossValidatorParams extends org.apache.spark.ml.tuning.ValidatorParams {
  /**
   * Param for number of folds for cross validation.  Must be >= 2.
   * Default: 3
   * @group param
   * @return (undocumented)
   */
  public  org.apache.spark.ml.param.IntParam numFolds () ;
  /** @group getParam */
  public  int getNumFolds () ;
}

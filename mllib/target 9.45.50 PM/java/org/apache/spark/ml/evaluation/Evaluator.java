package org.apache.spark.ml.evaluation;
/**
 * :: DeveloperApi ::
 * Abstract class for evaluators that compute metrics from predictions.
 */
public abstract class Evaluator implements org.apache.spark.ml.param.Params {
  public   Evaluator () { throw new RuntimeException(); }
  /**
   * Evaluates model output and returns a scalar metric (larger is better).
   * <p>
   * @param dataset a dataset that contains labels/observations and predictions.
   * @param paramMap parameter map that specifies the input columns and output metrics
   * @return metric
   */
  public  double evaluate (org.apache.spark.sql.DataFrame dataset, org.apache.spark.ml.param.ParamMap paramMap) { throw new RuntimeException(); }
  /**
   * Evaluates the output.
   * @param dataset a dataset that contains labels/observations and predictions.
   * @return metric
   */
  public abstract  double evaluate (org.apache.spark.sql.DataFrame dataset) ;
  /**
   * Indicates whether the metric returned by {@link evaluate()} should be maximized (true, default)
   * or minimized (false).
   * A given evaluator may support multiple metrics which may be maximized or minimized.
   * @return (undocumented)
   */
  public  boolean isLargerBetter () { throw new RuntimeException(); }
  public abstract  org.apache.spark.ml.evaluation.Evaluator copy (org.apache.spark.ml.param.ParamMap extra) ;
}

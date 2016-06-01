package org.apache.spark.repl;
/**
 * Command class enabling Spark-specific command line options (provided by
 * <i>org.apache.spark.repl.SparkRunnerSettings</i>).
 * <p>
 * @example new SparkCommandLine(Nil).settings
 * <p>
 * param:  args The list of command line arguments
 * param:  settings The underlying settings to associate with this set of
 *                 command-line options
 */
public  class SparkCommandLine extends scala.tools.nsc.CompilerCommand {
  public  scala.tools.nsc.Settings settings () { throw new RuntimeException(); }
  // not preceding
  public   SparkCommandLine (scala.collection.immutable.List<java.lang.String> args, scala.tools.nsc.Settings settings) { throw new RuntimeException(); }
  public   SparkCommandLine (scala.collection.immutable.List<java.lang.String> args, scala.Function1<java.lang.String, scala.runtime.BoxedUnit> error) { throw new RuntimeException(); }
  public   SparkCommandLine (scala.collection.immutable.List<java.lang.String> args) { throw new RuntimeException(); }
}

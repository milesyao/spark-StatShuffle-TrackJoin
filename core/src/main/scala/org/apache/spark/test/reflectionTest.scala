/**
  * Created by cnyao on 5/26/16.
  */

class TestClass {
  var testValue:Any = _
  def getValue(): Any = testValue
  def setValue[Any](input: Any):Unit = {
    testValue = input
  }
}

object reflectionTest {
  def main(args: Array[String]): Unit = {
    val tclass = new TestClass()
    tclass.setValue(20)
    val x = 1
    printf(x.toString() + " nimabi\n")
    printf(tclass.getValue.asInstanceOf[Int].toString() + " nimabi\n")
    tclass.setValue(20.0)
    printf(tclass.getValue.asInstanceOf[Double].toString() + " nimabi\n")
  }
  // def main(args: Array[String]): Unit = {
  //    printf("hehehehehehe\n")
  // }
}

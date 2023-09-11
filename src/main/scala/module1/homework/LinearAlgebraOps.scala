package module1.homework

object LinearAlgebraOps{

  val sum: PartialFunction[(Array[Int], Array[Int]), Array[Int]] =  {
    case (v1, v2) if v1.length == v2.length => v1.zip(v2).map(x => x._1 + x._2)
    case _ => throw new Exception("Operation is not supported")
  }

  def scale(v1: Array[Int], a: Int): Array[Int] = {
    v1.map(x => x * a)
  }

  val axpy: PartialFunction[(Array[Int], Array[Int], Int), Array[Int]] = {
    case (v1, v2, a) if v1.length == v2.length => sum(scale(v1, a), v2)
    case _ => throw new Exception("Operation is not supported")
  }

}
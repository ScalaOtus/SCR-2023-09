package module1.homework

object LinearAlgebraOps{
  def sum(v1: Array[Int], v2: Array[Int]): Array[Int] = {
    if (v1.length != v2.length) throw new Exception("Operation is not supported")
    v1.zip(v2).map(x => x._1 + x._2)
  }

  def scale(v1: Array[Int], a: Int): Array[Int] = {
    v1.map(x => x * a)
  }

  def axpy(v1: Array[Int], v2: Array[Int], a: Int): Array[Int] = {
    if (v1.length != v2.length) throw new Exception("Operation is not supported")
    sum(scale(v1, a), v2)
  }
}
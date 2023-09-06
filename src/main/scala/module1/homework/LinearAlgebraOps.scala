package module1.homework

object LinearAlgebraOps{
  def sum(v1: Array[Int], v2: Array[Int]): Array[Int] =
    if (v1.length != v2.length) throw new Exception("Operation is not supported")
    else v1.zip(v2).map(d => d._1 + d._2)

  def scale(v: Array[Int], a: Int): Array[Int] =
    v.map(d => d * a)

  def axpy(a: Int, v1: Array[Int], v2: Array[Int]): Array[Int] =
    if (v1.length != v2.length) throw new Exception("Operation is not supported")
    else v1.zip(v2).map(d => a * d._1 + d._2)
}
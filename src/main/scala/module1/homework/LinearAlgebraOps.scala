package module1.homework

object LinearAlgebraOps {
  def sum(v1: Array[Int], v2: Array[Int]): Array[Int] = {
    if (v1.length != v2.length)
      throw new Exception("Operation is not supported")
    ((v1 zip v2) map { case (a, b) => a + b }).toArray
  }

  def scale(a: Int, v: Array[Int]): Array[Int] = {
    (v map { el => el * a }).toArray
  }

  def axpy(a: Int, v1: Array[Int], v2: Array[Int]) = {
    if (v1.length != v2.length)
      throw new Exception("Operation is not supported")
    ((v1 zip v2) map { case (x, y) => a * x + y }).toArray
  }

}

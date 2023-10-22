package module1.homework

object LinearAlgebraOps{
  def sum(v1: Array[Int], v2: Array[Int]): Array[Int] = {
    if (v1.length != v2.length) {
      throw new Exception("Operation is not supported")
    }
    v1.zip(v2).collect({case (a, b) => a + b})
  }

  def scale(a: Int, v: Array[Int]): Array[Int] = {
    v.map(it => it * a)
  }

  def axpy(a: Int, v1: Array[Int], v2: Array[Int]): Array[Int] = {
    sum(scale(a, v1), v2)
  }
}
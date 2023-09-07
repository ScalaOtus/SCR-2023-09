package module1.homework

object LinearAlgebraOps{
  def sum(v1: Array[Int], v2: Array[Int]): Array[Int] = if (v1.length == v2.length) v1.zip(v2).map { case (x, y) => x + y } else throw new Exception("Operation is not supported.")

  // Example of sum method usage when all is O.K.
  // val a1 = Array(1, 2, 3)
  // val a2 = Array(1, 2, 4)
  // val sumResult = sum(a1, a2)
  // println(sumResult.mkString(","))
  // "2,4,7" will be printed.

  // Example of sum method usage when exception will be thrown.
  // val a1 = Array(1, 2, 3)
  // val a2 = Array(1, 2, 4, 5)
  // val sumResult = sum(a1, a2)
  // println(sumResult.mkString(","))
  // "Exception in thread "main" java.lang.Exception: Operation is not supported." will be printed.

  def scale(array: Array[Int], value: Int): Array[Int] = if (array.length != 0) array.map(_ * value) else throw new Exception("Incoming array is empty.")

  // Example of scale method usage when all is O.K.
  // val a1 = Array(1, 2, 3)
  // def scale(array: Array[Int], value: Int): Array[Int] = if (array.length != 0) array.map(_ * value) else throw new Exception("Incoming array is empty.")
  // val scaleResult = scale(a1, 5)
  // println(scaleResult.mkString(","))
  // "5,10,15" will be printed.

  // Example of scale method usage when exception will be thrown.
  // val a1 = Array(1, 2, 3)
  // val a2 = Array(1, 2, 4, 5)
  // val sumResult = sum(a1, a2)
  // println(sumResult.mkString(","))
  // "Exception in thread "main" java.lang.Exception: Incoming array is empty." will be printed.

  def axpy(v1: Array[Int], v2: Array[Int], value: Int): Array[Int] = if (v1.length == v2.length)  v1.map(_ * value).zip(v2).map { case (x, y) => x + y } else throw new Exception("Operation is not supported.")

  // Example of axpy method usage when all is O.K.
  // val a1 = Array(1, 2, 3)
  // val a2 = Array(1, 2, 3)
  // val scaleResult = axpy(a1, a2, 5)
  // println(sumResult.mkString(","))
  // "6,12,18" will be printed.

  // Example of axpy method usage when exception will be thrown.
  // val a1 = Array(1, 2, 3)
  // val a2 = Array(1, 2, 3, 4)
  // val scaleResult = axpy(a1, a2, 5)
  // println(sumResult.mkString(","))
  // "Exception in thread "main" java.lang.Exception: Operation is not supported." will be printed.
}
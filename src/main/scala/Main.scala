import module1.homework.LinearAlgebraOps
object Main {

  def main(args: Array[String]): Unit = {
    val hw1Arr1 = Array(1, 2)
    val hw1Arr2 = Array(3, 4)
    val hw1Sum = LinearAlgebraOps.sum(hw1Arr1, hw1Arr2)
    println(s"Sum of vectors ${arrayToString(hw1Arr1)} and ${arrayToString(hw1Arr2)} is ${arrayToString(hw1Sum)}")

    val hw1Arr3 = Array(1, 2, 3)
    val hw1Scalar1 = 2
    val hw1Scale = LinearAlgebraOps.scale(hw1Arr3, hw1Scalar1)
    println(s"Scale of vector ${arrayToString(hw1Arr3)} and scalar $hw1Scalar1 is ${arrayToString(hw1Scale)}")

    val hw1Axpy = LinearAlgebraOps.axpy(hw1Scalar1, hw1Arr1, hw1Arr2)
    println(s"Axpy of vectors ${arrayToString(hw1Arr1)} and ${arrayToString(hw1Arr2)} and scalar $hw1Scalar1 is ${arrayToString(hw1Axpy)}")
    println("Hello, World!")
  }

  def arrayToString(arr: Array[Int]): String =
    s"(${arr.mkString(", ")})"
}
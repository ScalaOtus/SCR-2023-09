package module1.homework

import org.scalatest.flatspec.AnyFlatSpec
import module1.homework.LinearAlgebraOps.{sum, scale, axpy}

class Homework01 extends AnyFlatSpec {

  "check sum" should "ok" in {
    assert(sum(Array(2, -4, 5), Array(-1, 1, 3)) sameElements Array(1, -3, 8))
    assert(sum(Array(1, 0), Array(-1, 1)) sameElements Array(0, 1))
    assert(sum(Array(21), Array(21)) sameElements Array(42))
    assert(sum(Array[Int](), Array[Int]()) sameElements Array[Int]())
  }
  //         url = https://github.com/Oxidpion/SCR-2023-09.git

  "check sum" should "throw exception" in {
    assertThrows[Exception](sum(Array(1), Array(2, 3)))
    assertThrows[Exception](sum(Array(1, 2), Array(3)))
  }

  "check scale" should "ok" in {
    assert(scale(3, Array(-1, 0, 10)) sameElements Array(-3, 0, 30))
    assert(scale(-1, Array(1, 0, -1)) sameElements Array(-1, 0, 1))
    assert(scale(10, Array[Int]()) sameElements Array[Int]())
  }

  "check axpy" should "ok" in {
    assert(axpy(2, Array(1, 0, -2), Array(3, 3, 3)) sameElements Array(5, 3, -1))
    assert(axpy(-1, Array[Int](), Array[Int]()) sameElements Array[Int]())
  }

  "check axpy" should "throws exception" in {
    assertThrows[Exception](axpy(-1, Array(1), Array(2, 3)))
  }
}

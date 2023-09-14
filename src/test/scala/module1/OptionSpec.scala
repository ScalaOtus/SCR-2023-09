package module1


import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers.{be, equal, have, not}
import org.scalatest.matchers.should
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper


class FunSpecExample extends AnyFunSuite{
  test("2 + 2 = 4"){
   assert(2 * 2 == 4)
   //2 * 2 should be <= 5
  }
  test("isEmpty on empty Option"){
    assert(None.isEmpty)
  }

}

class FlatSpecExample extends AnyFlatSpec{
  "2 + 2" should "equal 4" in {
    assertResult(4)(2 * 2)
  }

  "isEmpty" should "return true" in {
    assert(Option.empty[Int].isEmpty)
  }

  "head" should "throw" in {
    assertThrows[Exception](List().head)
  }

  "length" should  "work" in {
    List(1) should have length 1
  }
}

class OptionSpec extends AnyFunSpec{
  describe("an Option"){
    describe("when empty"){
      it("isEmpty should return true"){
        assert(None.isEmpty)
      }
    }
    describe("when non empty"){
      it("isEmpty should return false"){
        Some(1).isEmpty should equal (false)
      }
    }
  }
}
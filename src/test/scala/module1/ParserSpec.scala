package module1

import module1.subtyping.Car
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.matchers.must.Matchers.{be, equal, have, not}


class ParserSpec extends AnyFlatSpec{
 "parser" should "parse cars as expected" in{
   val str = "1997;Ford;G;true\n1901;Ford;T;false"
   val result: List[Car] = ???
   val expected = List(Car(1997, "Ford", "G", true), Car(1901, "Ford", "T", false))
   result should equal(expected)
 }
}

package module1.homework

import org.scalatest.flatspec.AnyFlatSpec
import module1.recursion.{fib, fibRec, fibRecTail}
import module1.opt.{Option, Some, None}
import module1.list.{filter, incList, shoutString, ::, Nil}

class Homework02 extends AnyFlatSpec {

  "check fib" should "ok" in {
    assert(fib(0) == 0)
    assert(fib(1) == 1)
    assert(fib(10) == 55)
    assert(fib(15) == 610)
  }

  "check fibRec" should "ok" in {
    assert(fibRec(0) == 0)
    assert(fibRec(1) == 1)
    assert(fibRec(10) == 55)
    assert(fibRec(15) == 610)
  }

  "check fibRecTail" should "ok" in {
    assert(fibRecTail(0) == 0)
    assert(fibRecTail(1) == 1)
    assert(fibRecTail(10) == 55)
    assert(fibRecTail(15) == 610)
  }

  "check printIfAny" should "ok" in {
    assert(Some("42").printIfAny() == ())
    assert(None.printIfAny() == ())
  }

  "check Option.zip" should "ok" in {
    assert(Option.zip(None, Some(42)) == None)
    assert(Option.zip(Some(23), None) == None)
    assert(Option.zip(Some(12), Some("42")) == Some((12, "42")))
  }

  "check Option.filter" should "ok" in {
    assert(None.filter(_ => false) == None)
    assert(None.filter(_ => true) == None)
    assert(Some('c').filter(_ => false) == None)
    assert(Some('c').filter(_ => true) == Some('c'))
  }

  "check List.filter" should "ok" in {
    assert(filter("0" :: "1" :: "2" :: Nil, (v : String) => v != "1") == "0" :: "2" :: Nil)
    assert(filter("0" :: "1" :: "2" :: Nil, (v : String) => v == "1") == "1" :: Nil)
    assert(filter(Nil, (_ : Int) => true) == Nil)
  }

  "check List.incList" should "ok" in {
    assert(incList(3 :: 5 :: 7 :: Nil) == 4 :: 6 :: 8 :: Nil)
    assert(incList(Nil) == Nil)
  }

  "check List.shoutString" should "ok" in {
    assert(shoutString("a" :: "ab" :: "abc" :: Nil) == "!a" :: "!ab" :: "!abc" :: Nil)
    assert(shoutString(Nil) == Nil)
  }

  "check List.drop" should "ok" in {
    assert(Nil.drop(10) == Nil)
    assert((1 :: 2 :: 3 :: Nil).drop(2) == 3 :: Nil)
    assert((1 :: 2 :: 3 :: Nil).drop(10) == Nil)
  }

  "check List.drop1" should "ok" in {
    assert(Nil.drop1(10) == Nil)
    assert((1 :: 2 :: 3 :: Nil).drop1(2) == 3 :: Nil)
    assert((1 :: 2 :: 3 :: Nil).drop1(10) == Nil)
  }
}

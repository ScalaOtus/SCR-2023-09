package module1

import module1.type_classes.JsValue.{JsNull, JsNumber, JsString}


object type_classes {

  sealed trait JsValue
  object JsValue {
    final case class JsObject(get: Map[String, JsValue]) extends JsValue
    final case class JsString(get: String) extends JsValue
    final case class JsNumber(get: Double) extends JsValue
    final case object JsNull extends JsValue
  }

  // 1
  trait JsonWriter[T]{
    def write(v: T): JsValue
  }

  object JsonWriter{
    def apply[T](implicit ev: JsonWriter[T]): JsonWriter[T] = ev


    implicit val strToJsWriter = new JsonWriter[String] {
      override def write(v: String): JsValue = JsString(v)
    }

    implicit val intToJsWriter = new JsonWriter[Int] {
      override def write(v: Int): JsValue = JsNumber(v)
    }

    implicit def optToJsWriter[A](implicit ev: JsonWriter[A]): JsonWriter[Option[A]] =
      new JsonWriter[Option[A]] {
        override def write(v: Option[A]): JsValue = v match {
          case Some(x) => ev.write(x)
          case None => JsNull
        }
      }
  }

  implicit class JsonSyntax[T](v: T){
    def toJson(implicit ev: JsonWriter[T]): JsValue = ev.write(v)
  }



  // 3
  def toJson[T : JsonWriter](v: T): JsValue = {
      JsonWriter[T].write(v)
  }

  toJson("vbfbbgb")
  toJson(12)
  toJson(Option(12))
  toJson(Option("vfvfvf"))

  "fdbbbbfg".toJson
  Option(13).toJson




  // 1 type constructor
  trait Ordering[T]{
    def less(a: T, b: T): Boolean
  }

  // 2 component


  object Ordering{
    def from[A](f: (A, A) => Boolean): Ordering[A] = new Ordering[A] {
      override def less(a: A, b: A): Boolean = f(a, b)
    }

    implicit val intOrdering = Ordering.from[Int]((a, b) => a < b)

    implicit val strOrdering = Ordering.from[String]((a, b) => a < b)

    implicit val subAnimalOrdering: Ordering[SubAnimal] = ???
  }

  // 3 implicit parameter

  trait Animal
  trait SubAnimal extends Animal

  class Cat extends SubAnimal
  class Dog extends SubAnimal

  val a1: SubAnimal = ???
  val a2: SubAnimal = ???

  val a3: Cat = ???
  val a4: Dog = ???

  _max(a1, a2)

  _max(a3, a4)

  def _max[A](a: A, b: A)(implicit ordering: Ordering[A]): A =
    if(ordering.less(a, b)) b else a

  _max(5, 10)
  _max("ab", "abcd")


  // 1
  trait Eq[T]{
    def ===(a: T, b: T): Boolean
  }

  implicit class EqSyntax[T](a: T){
    def ===(b: T)(implicit eq: Eq[T]): Boolean =
      eq.===(a, b)
  }

  object Eq{
    implicit val strEq = new Eq[String]{
      override def ===(a: String, b: String): Boolean = a == b
    }
  }


  val result = List("a", "b", "c").filter(str => str == 1)

}

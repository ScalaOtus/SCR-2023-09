package module1

import cats.{Id, Monad, Monoid, Semigroupal}
import cats.data.{Chain, Ior, Kleisli, NonEmptyChain, NonEmptyList, OptionT, Validated, ValidatedNec, Writer, WriterT}
import cats.implicits._

import scala.concurrent.Future
import scala.util.Try

object cats_type_classes{

  // Semigroup

  trait Semigroup[A]{
    // ассоциативную бинарную операция combine
    // combine(x, combine(y, z)) <==> combine(combine(x, y), z)

    def combine(x: A, y: A): A
  }

  object Semigroup{
    def apply[A](implicit ev: Semigroup[A]): Semigroup[A] = ev

    implicit val intSemigroup = new Semigroup[Int] {
      override def combine(x: Int, y: Int): Int = x + y
    }
  }

  val _ = List(1, 2, 3).foldLeft(0)(_ |+| _)


  // Мержим Map-ы

  // Map("a" -> 1, "b" -> 2)
  // Map("b" -> 3, "c" -> 4)
  // Map("a" -> 1, "b" -> 5, "c" -> 4)


  def optCombine[V : Semigroup](v: V, optV: Option[V]): V =
    optV.map(e => Semigroup[V].combine(v, e)).getOrElse(v)

  def mergeMap[K, V : Semigroup](lhs: Map[K, V], rhs: Map[K, V]): Map[K, V] =
    lhs.foldLeft(rhs){ case (acc, (k, v)) =>
      acc.updated(k, optCombine(v, acc.get(k)))
    }

  val m = mergeMap(Map("a" -> 1, "b" -> 2), Map("b" -> 3, "c" -> 4))


  def combineAll[A : Monoid](l: List[A]): A =
    l.foldLeft(Monoid[A].empty)(Monoid[A].combine(_ , _))


  // Monoid

  trait Monoid[A] extends Semigroup[A]{
    def empty: A
    // ассоциативную бинарную операция combine
    // combine(x, combine(y, z)) <==> combine(combine(x, y), z)
    // combine(x, empty) <==> x
    // combine(empty, x) <==> x
  }

  object Monoid{
    def apply[A](implicit ev: Monoid[A]): Monoid[A] = ev
  }


  // Functor


  trait Functor[F[_]]{
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }
  // f и g f andThen g
  // map(f andThen g) <==> map(f) andThen map(g)



  // Monad


}

object functional {


  // Kleisli
  case class Id(id: Int) extends AnyVal

  val f1: String => Int = _.toInt
  val f2: Int => Id = Id

  val f3: String => Id = f1 andThen f2

  val f4: String => Option[Int] = s => Try(s.toInt).toOption
  val f5: Int => Option[Id] = f2 andThen Option.apply[Id]
  val f6: Kleisli[Option, String, Id] = Kleisli(f4) andThen Kleisli(f5)
  val _f6: String => Option[Id] = f6.run

  _f6("2") // Some(Id(2))


}

object transformers {

  val f1: Future[Int] = Future.successful(2)
  def f2(i: Int): Future[Option[Int]] = Future.successful(Try(10 / i).toOption)
  def f3(i: Int): Future[Option[Int]] = Future.successful(Try(10 / i).toOption)

  import scala.concurrent.ExecutionContext.Implicits.global

  val r: OptionT[Future, Int] = for{
    i0 <- OptionT.liftF(f1)
    i1 <- OptionT(f2(i0))
    i2 <- OptionT(f3(i1))
  } yield i1 + i2

  val _: Future[Option[Int]] = r.value


}

object dataStructures{

  /**
   * Chain
   */

  // Конструкторы

  val empty: Chain[Int] = Chain.empty[Int]

  val ch1: Chain[Int] = empty :+ 1
  val ch2: Chain[Int] = 1 +: empty
  val ch3: Chain[Int] = Chain.fromSeq(List(1, 2, 3))


  // операторы

  empty.headOption

  /**
   * NonEmptyChain
   */

  // конструкторы


  /**
   * NonEmptyList
   **/


}

object validation{

  type EmailValidationError = String
  type NameValidationError = String
  type AgeValidationError = String
  type Name = String
  type Email = String
  type Age = Int

  case class UserDTO(email: String, name: String, age: Int)
  case class User(email: String, name: String, age: Int)

  def emailValidatorE: Either[EmailValidationError, Email] = Left("Not valid email")

  def userNameValidatorE: Either[NameValidationError, Name] =  Left("Not valid name")

  def userAgeValidatorE: Either[AgeValidationError, Age] = Right(30)


  // короткое замыкание
  def validateUserDataE = ???



  // Validated



  //Конструкторы


  // Операторы



  // Решаем задачу валидации с помощью Validated





   // Ior



  // Конструкторы



  // Операторы


  // Решаем задачу

}







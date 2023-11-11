package module1

import java.util.UUID
import scala.annotation.tailrec
import java.time.Instant
import scala.collection.immutable.Stack
import scala.collection.mutable.ListBuffer
import scala.language.{higherKinds, postfixOps}



/**
 * referential transparency
 */



 // recursion

object recursion {

  /**
   * Реализовать метод вычисления n!
   * n! = 1 * 2 * ... n
   */

  def fact(n: Int): Int = {
    var _n = 1
    var i = 2
    while (i <= n){
      _n *= i
      i += 1
    }
    _n
  }

  def factRec(n: Int): Int =
    if( n <= 0) 1 else n * factRec(n - 1)

  def factRecTail(n: Int): Int = {

    @tailrec
    def loop(n: Int, accum: Int): Int =
      if( n <= 1) accum
      else loop(n - 1, n * accum)

    loop(n, 1)
  }



  /**
   * реализовать вычисление N числа Фибоначчи
   * F0 = 0, F1 = 1, Fn = Fn-1 + Fn - 2
   *
   */


}

object hof{


  // обертки

  def logRunningTime[A, B](f: A => B): A => B = a => {
    val start = System.currentTimeMillis()
    val result: B = f(a)
    val end = System.currentTimeMillis()
    println(end - start)
    result
  }

  def doomy(str: String) = {
    Thread.sleep(1000)
    println(str)
  }



  // изменение поведения ф-ции

  val arr = Array(1, 2, 3, 4, 5)

  def isOdd(i: Int): Boolean = i % 2 > 0

  def not[A](f: A => Boolean): A => Boolean = a => !f(a)

  def isEven: Int => Boolean = not(isOdd)


  // (A, B, C) => D   curring A => B => C => D
  // изменение самой функции

  def curried[A, B, C](f: (A, B) => C): A => B => C = a => b => f(a, b)

  def partial[A, B, C](a: A, f: (A, B) => C): B => C = curried(f)(a)

  def sum(x: Int, y: Int): Int  = x + y

  val s0: Int => Int => Int = curried(sum)

  val s1: Function1[Int, Int] = s0(2)

  val s2: Int = s1(3) // 5

  val p0: Int => Int = partial(2, sum)

  p0(3) // 5


















  trait Consumer{
       def subscribe(topic: String): Stream[Record]
   }

   case class Record(value: String)

   case class Request()
   
   object Request {
       def parse(str: String): Request = ???
   }

  /**
   *
   * (Опционально) Реализовать ф-цию, которая будет читать записи Request из топика,
   * и сохранять их в базу
   */
   def createRequestSubscription() = ???



}






/**
 *  Реализуем тип Option
 */


 object opt {

  /**
   *
   * Реализовать структуру данных Option, который будет указывать на присутствие либо отсутсвие результата
   */

  sealed trait Option[+T]{

    def isEmpty: Boolean =  this match {
      case Some(v) => false
      case None => true
    }

    def get: T = this match {
      case Some(v) => v
      case None => throw new Exception("None get")
    }

    def map[B](f: T => B): Option[B] = flatMap(v => Some(f(v)))

    def flatMap[B](f: T => Option[B]): Option[B] = this match {
      case Some(v) => f(v)
      case None => None
    }

    /**
     *
     * Реализовать метод printIfAny, который будет печатать значение, если оно есть
     */

    def printIfAny = this match {
      case Some(v) => println(v)
      case None => ()
    }

    /**
     *
     * Реализовать метод filter, который будет возвращать не пустой Option
     * в случае если исходный не пуст и предикат от значения = true
     */

    def filter(f: T => Boolean): Option[T] = this match {
      case Some(v) if f(v) => this
      case _ => None
    }
  }
  case class Some[T](v: T) extends Option[T]
  case object None extends Option[Nothing]

  object Option{
    /**
     *
     * Реализовать метод zip, который будет создавать Option от пары значений из 2-х Option
     */
    def zip[A, B](v1: Option[A], v2: Option[B]): Option[(A, B)] = (v1, v2) match {
      case (Some(v1), Some(v2)) => Some(v1, v2)
      case _ => None
    }
  }




  // Animal -> Dog
  // Covariant + отношения переносятся на контейнер
  // Contravariant - отношения переносятся на контейнер наоборот
  // Invariant - нет отношений


  /**
   *
   * Реализовать метод printIfAny, который будет печатать значение, если оно есть
   */


  /**
   *
   * Реализовать метод zip, который будет создавать Option от пары значений из 2-х Option
   */


  /**
   *
   * Реализовать метод filter, который будет возвращать не пустой Option
   * в случае если исходный не пуст и предикат от значения = true
   */

 }

  trait Animal
  case object Dog
  case object Cat

 object list {
   /**
    *
    * Реализовать односвязанный иммутабельный список List
    * Список имеет два случая:
    * Nil - пустой список
    * Cons - непустой, содердит первый элемент (голову) и хвост (оставшийся список)
    */

    sealed trait List[+T] {
     /**
      * Метод cons, добавляет элемент в голову списка, для этого метода можно воспользоваться названием `::`
      *
      */

      def ::[TT >: T](el: TT): List[TT] = new ::(el, this)

     /**
      * Метод mkString возвращает строковое представление списка, с учетом переданного разделителя
      *
      */

      def mkString(delimeter: String): String = this match {
        case ::(head, Nil) => s"$head"
        case ::(head, tail) => s"$head$delimeter" + tail.mkString(delimeter)
        case Nil => ""
      }

     /**
      * Конструктор, позволяющий создать список из N - го числа аргументов
      * Для этого можно воспользоваться *
      *
      * Например вот этот метод принимает некую последовательность аргументов с типом Int и выводит их на печать
      * def printArgs(args: Int*) = args.foreach(println(_))
      */

     /**
      *
      * Реализовать метод reverse который позволит заменить порядок элементов в списке на противоположный
      */
      def reverse: List[T] = {
       def reversed(acc: List[T], remaining: List[T]): List[T] = remaining match {
         case Nil => acc
         case ::(head, tail) => reversed(head :: acc, tail)
       }

       reversed(Nil, this)
      }
     /**
      *
      * Реализовать метод map для списка который будет применять некую ф-цию к элементам данного списка
      */
      def map[B](f: T => B): List[B] = flatMap(v => f(v) :: Nil)

      def flatMap[B](f: T => List[B]): List[B] = this match {
        case ::(head, tail) => f(head) ::: tail.flatMap(f)
      }

     /**
      *
      * Реализовать метод filter для списка который будет фильтровать список по некому условию
      */

      def filter(p: T => Boolean): List[T] = this match {
       case Nil => Nil
       case ::(head, tail) =>
         if (p(head)) ::(head, tail.filter(p))
         else tail.filter(p)
      }

      def :::[TT >: T](that: List[TT]): List[TT] = {
        def go(l1: List[TT], l2: List[TT], acc: List[TT]): List[TT] = l2 match {
          case ::(head, tail) => go(l1, tail, acc.::(head))
          case Nil => l1 match {
            case ::(head, tail) => go(tail, Nil, acc.::(head))
            case Nil => acc
          }
        }
        go(this, that, Nil).reverse
      }




      def map[B](f: T => B): List[B] = ???

      def flatMap[B](f: T => List[B]): List[B] = this match {
        case ::(head, tail) => f(head) ::: tail.flatMap(f)
        case Nil => Nil
      }

      @tailrec
      final def foldLeft[B](acc: B)(op: (B, T) => B): B = this match {
        case ::(head, tail) => tail.foldLeft(op(acc, head))(op)
        case Nil => acc
      }

      def foldLeft2 = ???

      def take(n: Int): List[T] = {
        val r = this.foldLeft((0, List[T]())){ case ((i, acc), el) =>
          if( i == n) (i, acc)
          else (i + 1, acc.::(el))
        }
        r._2
      }

      def drop(n: Int): List[T] = ???

   }

    case class ::[A](head: A, tail: List[A]) extends List[A]
    case object Nil extends List[Nothing]


    object List{
      def apply[A](v: A*): List[A] = if(v.isEmpty) Nil else ::(v.head, apply(v.tail:_*))
    }


    /**
      *
      * Написать функцию incList котрая будет принимать список Int и возвращать список,
      * где каждый элемент будет увеличен на 1
      */
    def incList(values: List[Int]): List[Int] = values.map(v => v + 1)

    /**
      *
      * Написать функцию shoutString котрая будет принимать список String и возвращать список,
      * где к каждому элементу будет добавлен префикс в виде '!'
      */
    def shoutString(values: List[String]): List[String] = values.map(v => "!".concat(v))
 }

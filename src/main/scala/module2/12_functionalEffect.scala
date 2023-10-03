package module2

import scala.io.StdIn

object functional_effects {


  object simpleProgram {

    lazy val greet = {
      println("Как тебя зовут?")
      val name = StdIn.readLine()
      println(s"Привет, $name")
    }

    lazy val askForAge = {
      println("Сколько тебе лет?")
      val age = StdIn.readInt()
      if (age > 18) println("Можешь проходить")
      else println("Ты еще не можешь пройти")
    }


    def greetAndAskForAge = ???


  }


  object functionalProgram {

    /**
     * Executable encoding and Declarative encoding
     */

    object executableEncoding {

      /**
       * 1. Объявить исполняемую модель Console
       */

      case class Console[A](run: () => A){ self =>

        def map[B](f: A => B): Console[B] = flatMap(a =>
          Console.succeed(f(a))
        )
        def flatMap[B](f: A => Console[B]): Console[B] =
          Console.succeed(f(self.run()).run())
      }


      object Console{
        def succeed[A](a: => A): Console[A] = Console(() => a)

        def printLine(str: String): Console[Unit] =
          Console(() => println(str))

        def readLine(): Console[String] =
          Console(() => StdIn.readLine())
      }

/*      lazy val greet = {
        println("Как тебя зовут?")
        val name = StdIn.readLine()
        println(s"Привет, $name")
      }*/

      val gE: Console[Unit] = for{
        _ <- Console.printLine("Как тебя зовут?")
        name <- Console.readLine()
        _ <- Console.printLine(s"Привет, $name")
      } yield ()

      /**
       * 2. Объявить конструкторы
       */



      /**
       * 3. Описать желаемую программу с помощью нашей модель
       */


    }


    object declarativeEncoding {

      /**
       * 1. Объявить декларативную модель Console
       */
      sealed trait Console[A]
      case class PrintLine[A](str: String, rest: Console[A]) extends Console[A]
      case class ReadLine[A](f: String => Console[A]) extends Console[A]
      case class Succeed[A](v: () => A) extends Console[A]


      implicit class ConsoleOps[A](console: Console[A]){
        def flatMap[B](f: A => Console[B]): Console[B] = console match {
          case PrintLine(str, rest) => PrintLine(str, rest.flatMap(f))
          case ReadLine(ff) => ReadLine(str => ff(str).flatMap(f))
          case Succeed(v) => f(v())
        }

        def map[B](f: A => B): Console[B] = flatMap(a =>
          Succeed(() => f(a)))
      }

      object Console{
        def succeed[A](v: => A): Console[A] = Succeed(() => v)
        def printLine(str: String): Console[Unit] = PrintLine(str, succeed())
        def readLine: Console[String] = ReadLine(str => succeed(str))
      }

      /**
       * 2. Написать конструкторы
       * 
       */

      /**
       * 3. Описать желаемую программу с помощью нашей модели
       */

      /*      lazy val greet = {
        println("Как тебя зовут?")
        val name = StdIn.readLine()
        println(s"Привет, $name")
      }*/

//      val p1 = PrintLine("Как тебя зовут?",
//        ReadLine(str =>
//          PrintLine(s"Привет, $str",
//            Succeed(() => ())))
//      )
//
      val gD: Console[Unit] = for{
        _ <- Console.printLine("Как тебя зовут?")
        name <- Console.readLine
        _ <- Console.printLine(s"Привет, $name")
      } yield ()


      /**
       * 4. Написать операторы
       *
       */

      /**
       * 5. Написать интерпретатор для нашей ф-циональной модели
       *
       */

      def interpret[A](console: Console[A]): A = console match {
        case PrintLine(str, rest) =>
          println(str)
          interpret(rest)
        case ReadLine(f) =>
          interpret(f(StdIn.readLine()))
        case Succeed(v) => v()
      }



      /**
       * Реализуем туже прошрамму что и в случае с исполняемым подходом
       */

    }

  }

}
package module2


import cats.effect.IO.Async
import cats.effect.concurrent.Ref
import cats.{Applicative, ApplicativeError, Apply, Defer, Eval, FlatMap, Functor, Monad, MonadError}
import cats.effect.{Async, Concurrent, ConcurrentEffect, ExitCase, ExitCode, IO, IOApp, Sync, SyncIO, Timer}

import scala.concurrent.duration.DurationInt
import cats.effect.implicits._
import cats.implicits.catsSyntaxApply

import scala.concurrent.{ExecutionContext, Future}
import scala.language.{higherKinds, postfixOps}
import scala.util.Try
import cats.implicits._


import scala.io.StdIn

object typeClasses {

  def inc[F[_]: Functor](el: F[Int]): F[Int] =
    Functor[F].map(el)( _ + 1)

  def tupled[F[_] : Monad, A, B](fa: F[A], fb: F[B]): F[(A, B)] =
    Monad[F].flatMap(fa){a =>
      Monad[F].map(fb){ b =>
        (a, b)
      }
    }

  def mapN[F[_] : Monad, A, B, C](t: (F[A], F[B]))(f: (A, B) => C): F[C] =
    tupled(t._1, t._2).map{ case (a, b) =>
      f(a, b)
    }

  def when[F[_]: Functor, A](fa: F[A])(f: A => Boolean): F[A] = {
    Functor[F].map(fa){ a =>
      if(f(a)) a
      else throw new Throwable("Condition doesn't hold")
    }
  }

  def echo0[F[_]: Monad] = for{
    str <- Monad[F].pure(StdIn.readLine())
    _ <- Monad[F].pure(println(str))
  } yield ()

  def echo1[F[_]: Sync] = for{
    str <- Sync[F].delay(StdIn.readLine())
    _ <- Sync[F].delay(println(str))
  } yield ()

}


object A{
  def main(args: Array[String]): Unit = {

    type Eth[A] = Either[String, A]


//    val r1: Option[Int] = typeClasses.inc(Option(1))
//    val r2: List[Int] = typeClasses.inc(List(1, 2, 3))
//    val r3: Eth[Int] = typeClasses.inc[Eth](Left("Ooops"))
//
//    val r4 = typeClasses.tupled(Option(1), Option(2))
//    val r5 = typeClasses.tupled(List(1, 2, 3), List(1, 2, 3))
//    val r6 = typeClasses.tupled[Eth, Int, Int](Right(1), Left("Ooops"))
//
//    val r7: Option[Int] = typeClasses.mapN(Option(1), Option(2)){ case (a, b) =>
//      a + b
//    }
//
//    println(r1)
//    println(r2)
//    println(r3)
//    println(r4)
//    println(r5)
//    println(r6)

   val r8: toyCatsEffect.IO[Unit] =  typeClasses.echo1[toyCatsEffect.IO]
    r8.run()
    r8.run()
    r8
  }
}

object toyCatsEffect {

  case class IO[+A](run: () => A){self =>

    def map[B](f: A => B): IO[B] = flatMap(v => IO(() => f(v)))

    def flatMap[B](f: A => IO[B]): IO[B] =
      IO(() => f(self.run()).run())
  }

  object IO{
    def delay[A](v: => A): IO[A] = IO(() => v)
    def fail[A](e: Throwable): IO[A] = IO(() => throw e)

    implicit val sync: Sync[toyCatsEffect.IO] = new Sync[toyCatsEffect.IO]{
      override def suspend[A](thunk: => IO[A]): IO[A] =
        IO(() => thunk.run())

      override def bracketCase[A, B](acquire: IO[A])(use: A => IO[B])
                                    (release: (A, ExitCase[Throwable]) => IO[Unit]): IO[B] = ???

      override def raiseError[A](e: Throwable): IO[A] = IO.fail(e)

      override def handleErrorWith[A](fa: IO[A])(f: Throwable => IO[A]): IO[A] = IO{ () =>
        try{
          fa.run()
        } catch {
          case e: Throwable =>
            f(e).run()
        }
      }

      override def flatMap[A, B](fa: IO[A])(f: A => IO[B]): IO[B] = fa.flatMap(f)

      override def tailRecM[A, B](a: A)(f: A => IO[Either[A, B]]): IO[B] = ???

      override def pure[A](x: A): IO[A] = IO.delay(x)
    }
  }

}
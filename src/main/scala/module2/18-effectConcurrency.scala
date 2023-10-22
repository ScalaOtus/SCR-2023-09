package module2

import cats.{Applicative, Functor, Monad, Parallel, effect, ~>}
import cats.effect.concurrent.Ref
import cats.effect.{Blocker, ContextShift, ExitCode, IO, IOApp, Timer}
import cats.implicits._
import module1.utils.NameableThreads
import module2.debug.DebugHelper

import java.util.concurrent.Executors
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, duration}

object effectConcurrency{

  val e = Executors.newWorkStealingPool(4)
  val ec = ExecutionContext.fromExecutor(e)
  implicit val timer: Timer[IO] = IO.timer(ec)
  implicit val contextShift = IO.contextShift(ec)

  /**
   * Напишите эффект, который будет считать время выполнения любого эффекта
   */

  def printEffectRunningTime[A](io: => IO[A])(implicit T: Timer[IO]): IO[A] = for{
    start <- T.clock.realTime(duration.SECONDS)
    r <- io
    end <- T.clock.realTime(duration.SECONDS)
    _ <- IO.delay(println(s"Running time: ${end - start}"))
  } yield r

  /**
   * Эффект который все что делает, это спит заданное кол-во времени, в данном случае 1 секунду
   */

  val sleep1second = IO.sleep(1 seconds)

  /**
   * Эффект который все что делает, это спит заданное кол-во времени, в данном случае 3 секунды
   */

  val sleep3second = IO.sleep(1 seconds)

  /**
   * Создать эффект который печатает в консоль GetExchangeRatesLocation1 спустя 3 секунды
   */
  val getExchangeRatesLocation1: IO[String] = sleep3second *> IO.delay("GetExchangeRatesLocation1")


  /**
   * Создать эффект который печатает в консоль GetExchangeRatesLocation2 спустя 1 секунд
   */

  val getExchangeRatesLocation2: IO[String] = sleep1second *> IO.delay("GetExchangeRatesLocation2")

  /**
   * Написать эффект котрый получит курсы из обеих локаций паралельно
   */

  val g2l: IO[(String, String)] = (getExchangeRatesLocation1.debug, getExchangeRatesLocation2.debug).tupled.debug


  val g2lP: IO[(String, String)] = Parallel[IO].sequential(
    (Parallel[IO].parallel(getExchangeRatesLocation1.debug),
      Parallel[IO].parallel(getExchangeRatesLocation2.debug)).tupled
  ).debug

  val g2lP2: IO[(String, String)] = (getExchangeRatesLocation1.debug, getExchangeRatesLocation2.debug).parTupled

  val g2lP3: IO[(String, String)] = for{
    fiber1 <- getExchangeRatesLocation1.start.debug
    r2 <- getExchangeRatesLocation2.debug
    r1 <- fiber1.join
  } yield (r1, r2)

  val r1: IO[Unit] = Blocker[IO].use{ blocker =>
    for{
      _ <- IO.delay("1").debug
      _ <- blocker.blockOn(IO.delay(println("2")).debug)
      _ <- IO.delay("2").debug
    } yield ()
  }

  val csh1 = for{
    _ <- (sleep1second *> IO(1)).debug
    _ <- IO.shift
    _ <- (sleep3second *> IO(2)).debug
    _ <- IO.shift
    _ <- (sleep1second *> IO(3)).debug
  } yield ()


}

object CatsEffectApp3 extends IOApp{
  import module2.{effectConcurrency => ec}

  override def run(args: List[String]): IO[ExitCode] =
    ec.printEffectRunningTime(ec.csh1.debug).as(ExitCode.Success)
}

package module2

import zio.{Fiber, IO, Ref, Task, UIO, URIO, ZIO, clock}
import zio.clock.{Clock, sleep}
import zio.console.{Console, putStrLn}
import zio.duration.durationInt
import zio.internal.{Executor, ZIOSucceedNow}

import java.io.IOException
import java.util.concurrent.TimeUnit
import scala.language.postfixOps


object zioConcurrency {


  // эфект содержит в себе текущее время
  val currentTime: URIO[Clock, Long] = clock.currentTime(TimeUnit.SECONDS)




  /**
   * Напишите эффект, который будет считать время выполнения любого эффекта
   */


  def printEffectRunningTime[R, E, A](zio: ZIO[R, E, A]): ZIO[Console with Clock with R, E, A] = for{
    start <- currentTime
    r <- zio
    end <- currentTime
    _ <- putStrLn(s"Running time ${end - start}")
  } yield r




  /**
   * Эффект который все что делает, это спит заданное кол-во времени, в данном случае 1 секунду
   */
  lazy val sleep1Second = ZIO.sleep(1 seconds)

  /**
   * Эффект который все что делает, это спит заданное кол-во времени, в данном случае 3 секунды
   */
  lazy val sleep3Seconds = ZIO.sleep(3 seconds)

  /**
   * Создать эффект который печатает в консоль GetExchangeRatesLocation1 спустя 3 секунды
   */
  lazy val getExchangeRatesLocation1: ZIO[Console with Clock, Nothing, Unit] =
    sleep3Seconds zipRight putStrLn("GetExchangeRatesLocation1")

  /**
   * Создать эффект который печатает в консоль GetExchangeRatesLocation2 спустя 1 секунду
   */
  lazy val getExchangeRatesLocation2 =
    sleep1Second zipRight putStrLn("GetExchangeRatesLocation2")


  /**
   * Написать эффект котрый получит курсы из обеих локаций
   */
  lazy val getFrom2Locations =
    getExchangeRatesLocation2 zip getExchangeRatesLocation1

  /**
   * Написать эффект котрый получит курсы из обеих локаций паралельно
   */

  val f: URIO[Console with Clock, Fiber.Runtime[Nothing, Unit]] = getExchangeRatesLocation1.fork

  lazy val getFrom2Locations2 = for{
    fiber1 <- getExchangeRatesLocation1.fork
    fiber2 <- getExchangeRatesLocation2.fork
    r1 <- fiber1.join
    r2 <- fiber2.join
  } yield (r1, r2)


  /**
   * Предположим нам не нужны результаты, мы сохраняем в базу и отправляем почту
   */


   lazy val writeUserToDB = sleep1Second *> putStrLn("User saved")

   lazy val sendMail = sleep3Seconds *> putStrLn("Mail sent")

  /**
   * Написать эффект котрый сохранит в базу и отправит почту паралельно
   */

  lazy val writeAndSend = for{
    f1 <- writeUserToDB.fork
    f2 <- sendMail.fork
    _ <- f1.join
    _ <- f2.join
  } yield ()


  /**
   *  Greeter
   */

  lazy val greeter = (sleep1Second zipRight putStrLn("Hello")).forever

  lazy val g1 = for{
    fiber <- ZIO.effect(while (true){println("Hello")}).fork
    _ <- putStrLn("G1")
    _ <- fiber.interrupt
  } yield ()


  /***
   * Greeter 2
   * 
   * 
   * 
   */


 lazy val greeter2 = ???
  

  /**
   * Прерывание эффекта
   */

   lazy val app3 = ???





  /**
   * Получние информации от сервиса занимает 1 секунду
   */
  def getFromService(ref: Ref[Int]) = ???

  /**
   * Отправка в БД занимает в общем 5 секунд
   */
  def sendToDB(ref: Ref[Int]): ZIO[Clock, Exception, Unit] = ???


  /**
   * Написать программу, которая конкурентно вызывает выше описанные сервисы
   * и при этом обеспечивает сквозную нумерацию вызовов
   */

  
  lazy val app1 = ???

  /**
   *  Concurrent operators
   */


  lazy val p1 = getExchangeRatesLocation1 zipPar getExchangeRatesLocation2

  lazy val p2 = getExchangeRatesLocation1 race getExchangeRatesLocation2

  lazy val p3 = ZIO.foreachPar(List(1, 2, 3)){ i =>
     ZIO.sleep(1 seconds) *> putStrLn(i.toString)
  }


  /**
   * Lock
   */


  // Правило 1
  lazy val doSomething: UIO[Unit] = ???
  lazy val doSomethingElse: UIO[Unit] = ???

  lazy val executor: Executor = ???

  lazy val eff = for{
    f1 <- doSomething.fork
    _ <- doSomethingElse
    r <- f1.join
  } yield r

  lazy val result = eff.lock(executor)



  // Правило 2
  lazy val executor1: Executor = ???
  lazy val executor2: Executor = ???



  lazy val eff2 = for{
      f1 <- doSomething.lock(executor2).fork
      _ <- doSomethingElse
      r <- f1.join
    } yield r

  lazy val result2 = eff2.lock(executor)



}
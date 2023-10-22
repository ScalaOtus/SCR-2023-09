package module2

import cats.{Applicative, Functor}
import cats.effect.{ExitCode, IO, IOApp, Resource, Sync}
import cats.effect.concurrent.Ref
import cats.implicits._

import java.io.{BufferedReader, File, FileInputStream, FileOutputStream, FileReader, InputStream, OutputStream}
import javax.print.attribute.standard.Destination
import scala.io.{BufferedSource, Source, StdIn}
import scala.util.Try

object catsEffectIO {

  // конструкторы


  // создать эффект печатающий в консоль GetExchangeRatesLocation1

  val io1: IO[Unit] = IO(println("GetExchangeRatesLocation1"))

  // создать эффект печатающий в консоль GetExchangeRatesLocation2
  val io2: IO[Unit] = IO.delay(println("GetExchangeRatesLocation1"))

  // создать эффект содержащий число 10

  val io3: IO[Int] = IO.pure(10)

  // создать эффект содержащий конкатенацию строк

  val io4 = IO.pure("Hello" + ", world")

  // создать эффект выбрасывающий ошибку

  val io5: IO[Int] = IO.raiseError[Int](new Throwable("oops"))
  val io6: IO[Int] = IO.delay[Int](1 / 0)

  // создание из других эффектов

  val io7: IO[Int] = IO.fromTry(Try(2 / 0))
  val io8 = IO.fromEither(Left(new Throwable("oops")))

  // кобинаторы

  val io9: IO[(Unit, Unit)] = io1.flatMap(i1 => io2.map(i2 => (i1, i2)))
  val io10: IO[(Unit, Unit)] = (io1, io2).tupled
  val io11: IO[(Unit, Unit)] = (io1, io2).mapN{ case (i1, i2) =>
    (i1, i2)
  }

  val io12 = IO.delay(2 / 0)
  // работа c ошибками

  val io13: IO[Int] = io12.onError{
    case e: ArithmeticException => IO.delay(println(e.getMessage))
  }

  val io14: IO[Int] = io12.adaptError{
    case e: ArithmeticException => new Exception("Operation forbidden")
  }
  val io15: IO[Int] = io12.handleError(_ => 0)

  val io16 = io12.recover{
    case e: ArithmeticException => 0
  }

  val io17 = io12.recoverWith{
    case e: ArithmeticException => IO.pure(0)
  }

  val io18 = io12.handleErrorWith{
    _ => IO.pure(0)
  }


  val io19: IO[Unit] = IO.delay(println("Введите число: ")) *>
    IO.delay(StdIn.readLine()).flatMap{ str =>
      IO.delay(str.toInt)
    }.handleErrorWith(_ => IO.delay(println("Это не число, попробуйте снова")) *> io19)
      .flatMap(i => IO.delay(println(s"Вы ввели $i")))

  val io20: IO[Either[Throwable, Int]] = io12.attempt
}

object catsResources {

    type JFile = String

    def openFile[F[_]: Sync](fileName: String): F[JFile] =
      Sync[F].suspend(Sync[F].delay(println(s"Opening file ${fileName}"))
        *> Sync[F].delay(fileName))

    def close[F[_]: Sync](file: JFile): F[Unit] =
      Sync[F].delay(println(s"Closing file $file"))


    val result1: IO[Unit] = openFile[IO]("test1")
      .bracket(f => IO.raiseError(new Throwable("oh no")))(close[IO])

    def fileResource[F[_]: Sync](name: String): Resource[F, String] =
      Resource.make(openFile[F](name))(s => close[F](s))

    val file1: Resource[IO, String] = fileResource[IO]("test1")
    val file2: Resource[IO, String] = fileResource[IO]("test2")

   val file3: Resource[IO, BufferedReader] =
     Resource.fromAutoCloseable(IO.delay(new BufferedReader(new FileReader("test1"))))

    // Композиция ресурсов

    val result2: Resource[IO, (String, String)] = for{
      f1 <- file1
      f2 <- file2
    } yield (f1, f2)

    val r3: IO[String] = result2.use{ case (s1, s2) =>
      IO.delay(s1 + s2)
    }

    // Использование ресурсов


    // реализовать копирование файла из / в

}




object CatsEffectApp2 extends IOApp{

  override def run(args: List[String]): IO[ExitCode] = {
    catsResources.r3.map(println).as(ExitCode.Success)
  }
}
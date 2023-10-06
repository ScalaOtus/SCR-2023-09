package module2

import module2.tryFinally.traditional.{Resource, acquireResource}
import zio.{IO, RIO, Task, UIO, URIO, ZIO}

import java.io.{BufferedReader, Closeable, File, FileReader, IOException}
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.io.Source
import scala.util.{Failure, Success, Try}
import scala.language.postfixOps
import scala.io.BufferedSource

object tryFinally {

  object traditional {

    trait Resource

    def acquireResource: Resource = ???

    def use(resource: Resource): Unit = ???

    def releaseResource(resource: Resource): Unit = ???


    /**
     * Напишите код, который обеспечит корректную работу с ресурсом:
     * получить ресурс -> использовать -> освободить
     *
     */

     val result = {
       val resource = acquireResource
       try{
         use(resource)
       } finally {
         releaseResource(resource)
       }
     }

    /**
     *
     * обобщенная версия работы с ресурсом
     */

     def withResource[R, A](resource: => R)(release: R => Any)(use: R => A): A = {
       try {
         use(resource)
       } finally {
         release(resource)
       }
     }


    /**
     * Прочитать строки из файла test.txt
     */

    val result2: List[String] =
      withResource(Source.fromFile("test.txt"))(_.close())(_.getLines().toList)

  }

  object future{
    implicit val global = scala.concurrent.ExecutionContext.global

    def acquireFutureResource: Future[Resource] = ???
    def use(resource: Resource): Future[Unit] = ???

    def releaseFutureResource(resource: Resource): Future[Unit] = ???

    /**
     * Написать вспомогательный оператор ensuring, котрый позволит корректно работать
     * с ресурсами в контексте Future
     *
     */


     implicit class FutureOps[A](future: Future[A]){

        def transformWith[S](f: Try[A] => Future[S])(implicit eC: ExecutionContext): Future[S] = {
          val p = Promise[S]
          future.onComplete{
            f.andThen(_.onComplete {
              case Failure(exception) => p.failure(exception)
              case Success(value) => p.success(value)
            })
          }
          p.future
        }

        def ensuring(finalizer: Future[Any]): Future[A] = future.transformWith{
          case Failure(exception) => finalizer.flatMap(_ => Future.failed(exception))
          case Success(value) => finalizer.flatMap(_ => Future.successful(value))
        }
     }

     lazy val result2Future = acquireFutureResource.flatMap(r => use(r)
       .ensuring(releaseFutureResource(r)))


  }

  object zioBracket{


    /**
     * реалтзовать ф-цию, которая будет описывать открытие файла с помощью ZIO эффекта
     */
    def openFile(fileName: String): Task[BufferedSource] = ZIO.effect(Source.fromFile(fileName))

    /**
     * реалтзовать ф-цию, которая будет описывать закрытие файла с помощью ZIO эффекта
     */

    def closeFile(file: Source) = ZIO.effect(file.close()).orDie

    /**
     * Написать эффект, котрый прочитает строчки из файла и выведет их в консоль
     */

    def handleFile(file: Source): ZIO[Any, Throwable, Unit] = ZIO.foreach(file.getLines().toList){ line =>
      ZIO.effect(println(line))
    }.unit

    /**
     * Написать эффект, который откроет 2 файла, прочитает из них строчки,
     * выведет их в консоль и корректно закроет оба файла
     */

    val twoFiles: Task[Unit] = ZIO.bracket(openFile("test1.txt"))(closeFile){ f1 =>
      ZIO.bracket(openFile("test2.txt"))(closeFile){ f2 =>
        handleFile(f1) zipRight handleFile(f2)
      }
    }

    /**
     * Рефакторинг выше написанного кода
     *
     */

    def withFile = ???


    val twoFiles2 = ???

  }

}

object toyZManaged{

  import  module2.tryFinally.zioBracket._

  final case class ZManaged[-R, +E, A](
                                        acquire: ZIO[R, E, A],
                                        release: A => URIO[R, Any]
                                      ){ self =>


    def use[R1 <: R, E1 >: E, B](f: A => ZIO[R1, E1, B]): ZIO[R1, E1, B] =
      acquire.bracket(release)(f)

    def map[B](f: A => B): ZManaged[R, E, B] = ???

    def flatMap[R1 <: R, E1 >: E, B](f: A => ZManaged[R1, E1, B]): ZManaged[R1, E1, B] = ???

  }

}

object zioZManaged{

  import zio.ZManaged
  import  module2.tryFinally.zioBracket._

  /**
   * Создание ZManaged
   */

  /**
   * написать эффект открывающий / закрывающий первый файл
   */
  lazy val file1: ZManaged[Any, Throwable, BufferedSource] =
    ZManaged.make(openFile("test1.txt"))(closeFile)

  /** написать эффект открывающий / закрывающий второй файл
    *
   */
  lazy val file2 =  ZManaged.make(openFile("test2.txt"))(closeFile)



  /**
   * Использование ресурсов
   */


  /**
   * Написать эффект, котрый восользуется ф-цией handleFile из блока про bracket
   * для печати строчек в консоль
   */
  lazy val printFile1: ZIO[Any, Throwable, Unit] = file1.use(handleFile)


  /**
   * Комбинирование ресурсов
   */



  // Комбинирование
  lazy val combined: ZManaged[Any, Throwable, (BufferedSource, BufferedSource)] = file1 zip file2



  /**
   * Написать эффект, который прочитает и выведет строчки из обоих файлов
   */
  val combinedEffect: ZIO[Any, Throwable, Unit] = combined.use{ case (source, source1) =>
    handleFile(source) zipRight handleFile(source1)
  }


  /**
   * Множество ресурсов
   */

  lazy val fileNames: List[String] = ???

  def file(name: String): ZManaged[Any, IOException, Source] = ???


  // множественное открытие / закрытие
  lazy val files: ZManaged[Any, IOException, List[Source]] = ZManaged.foreach(fileNames){ n =>
    file(n)
  }



  // Использование

  def processFiles(file: Source *): Task[Unit] = ???

  // обработать N файлов
  lazy val r1: Task[List[String]] = files.use{ l =>
    ZIO.foreach(l){ s =>
      ZIO.effect(s.getLines().toList)
    }.map(_.flatten)
  }


  lazy val files3: ZManaged[Any, IOException, List[Source]] = ???

  /**
   * Прочитать строчки из файлов и вернуть список этих строк используя files3
   */
  lazy val r3: Task[List[String]] = ???
  



  // Конструирование

  lazy val eff1: Task[Int] = ???

  // Из эффекта
  lazy val m1 = ZManaged.fromEffect(eff1)

  type Transactor

  def mkTransactor(c: Config): ZManaged[Any, Throwable, Transactor] = ???

  // микс ZManaged и ZIO
  type Config
  val config: Task[Config] = ???

  lazy val m2: ZManaged[Any, Throwable, Transactor] = for{
    c <- config.toManaged_
    tr <- mkTransactor(c)
  } yield tr


}
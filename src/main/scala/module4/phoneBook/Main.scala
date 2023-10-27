package module4.phoneBook


import module4.phoneBook.db.{DataSource, LiquibaseService}
import module4.phoneBook.services.PhoneBookService
import module4.phoneBook.services.PhoneBookService.PhoneBookService
import zio._


object Main extends App {

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    val r: ZIO[PhoneBookService with DataSource, Throwable, Unit] = PhoneBookService.list().flatMap(l => ZIO(println(l)))
    (LiquibaseService.performMigration *> r).provideSomeLayer(App.appEnvironment)
      .exitCode
  }
}

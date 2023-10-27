package module4.phoneBook

import zio._
import zio.config.ReadError
import zio.config.typesafe.TypesafeConfig

package object configuration {

  case class Config(liquibase: LiquibaseConfig)
  
  case class LiquibaseConfig(changeLog: String)
  case class DbConfig(driver: String, url: String, user: String, password: String)


  type Configuration = zio.Has[Config]

  object Configuration{
    val live: Layer[ReadError[String], Configuration] = ???
  }
}

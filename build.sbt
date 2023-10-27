
scalaVersion := "2.11.5"

name := "scr-2023-09"
organization := "ru.otus"
version := "1.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.0.0"
libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "1.0.4",
  "dev.zio" %% "zio-macros" % "1.0.4"
)
libraryDependencies ++= Seq(
  "io.getquill"          %% "quill-jdbc-zio" % "3.12.0",
  "io.github.kitlangton" %% "zio-magic"      % "0.3.11"
)
libraryDependencies ++= Seq(
  "dev.zio" %% "zio-config" % "1.0.5",
  "dev.zio" %% "zio-config-typesafe" % "1.0.5"
)

libraryDependencies += "org.liquibase" % "liquibase-core" % "3.4.2"
libraryDependencies += "org.postgresql" % "postgresql" % "42.3.1"


scalacOptions ++= Seq(
  "-Xexperimental"
)
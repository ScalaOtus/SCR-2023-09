
scalaVersion := "2.11.5"

name := "scr-2023-09"
organization := "ru.otus"
version := "1.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test"

scalacOptions ++= Seq(
  "-Xexperimental"
)
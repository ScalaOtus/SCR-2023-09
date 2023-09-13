import module1.{hof, type_system}

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")

    val r: String => Unit = hof.logRunningTime(hof.doomy)
    r("Doomy")

  }
}
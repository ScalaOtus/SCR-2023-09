package module2.homework


import scala.language.higherKinds

object catsEffectHomework{

  /**
   * Тайп класс для генерации псевдо случайных чисел
   * @tparam F
   */
  trait Random[F[_]] {
    /***
     *
     * @param min значение от (включительно)
     * @param max значение до (исключается)
     * @return псевдо случайное число в заданном диапазоне
     */
    def nextIntBetween(min: Int, max: Int): F[Int]
  }



  object Random{
    /**
     * 1. реализовать сумонер метод для класса Random, в последствии он должен позволить
     * использовать Random например вот так для IO:
     * Random[IO].nextIntBetween(1, 10)
     *
     * @return Random[F]
     */
    def apply = ???


    /**
     * 2. Реализовать инстанс тайп класса для IO
     */
    implicit val ioRandom = ???
  }

  /**
   * Тайп класс для совершения операций с консолью
   * @tparam F
   */
  trait Console[F[_]]{
    def printLine(str: String): F[Unit]
    def readLine(): F[String]
  }

  object Console{
    /**
     * 3. реализовать сумонер метод для класса Console, в последствии он должен позволить
     * использовать Console например вот так для IO:
     * Console[IO].printLine("Hello")
     *
     * @return Console[F]
     */
    def apply = ???

    /**
     * 4. Реализовать инстанс тайп класса для IO
     */
    implicit val ioConsole = ???
  }

  /**
   * 5.
   * Используя Random и Console для IO, напишите консольную программу которая будет предлагать пользователю угадать число от 1 до 3
   * и печатать в когнсоль угадал или нет. Программа должна выполняться до тех пор, пока пользователь не угадает.
   * Подумайте, на какие наиболее простые эффекты ее можно декомпозировать.
   */

    val guessProgram = ???



  /**
   * 6. реализовать функцию doWhile (общего назначения) для IO, которая будет выполнять эффект до тех пор, пока его значение в условии не даст true
   * Подумайте над сигнатурой, еам нужно принимать эффект и условие относительно его значения, для того чтобы повторять либо заканчивать выполнение.
   */

  def doWhile = ???

}

/**
 * 7. Превратите данный объект в исполняемую cats effect программу, которая будет запускать
 * guessProgram
 */
object HomeworkApp{

}
package module1

object functions {


  /**
   * Функции
   */

  def sum(x: Int, y: Int): Int = x + y

  sum(2, 4) // 6

  /**
   * Реализовать ф-цию  sum, которая будет суммировать 2 целых числа и выдавать результат
   */

  val sum2: (Int, Int) => Int = (x, y) => x + y

  sum2(2, 4) // 6

  val sum3 = sum2

  List(sum2, sum3)

  sum3(2, 4)

  def foo(f: (Int, Int) => Int): (Int, Int) => Int = f

  val sum4: (Int, Int) => Int = sum _

  foo(sum)


  // Partial function

  val divide: PartialFunction[(Int, Int), Double] = new PartialFunction[(Int, Int), Double] {
    override def isDefinedAt(x: (Int, Int)): Boolean = x._2 != 0

    override def apply(v1: (Int, Int)): Double = v1._1 / v1._2
  }

  val divide2: PartialFunction[(Int, Int), Double] = {
    case (x, y) if y != 0 => x / y
  }

  divide2.isDefinedAt((2, 0)) // false

  val l: List[Double] = List((4, 2), (2, 2), (1, 0)).collect(divide2)






  // SAM Single Abstract Method

  trait Printer{
    def apply(str: String): Unit
  }

  val p: Printer = s => println(s)

  p("hello")



  /**
   *  Задание 1. Написать ф-цию метод isEven, которая будет вычислять является ли число четным
   */


  /**
   * Задание 2. Написать ф-цию метод isOdd, которая будет вычислять является ли число нечетным
   */


  /**
   * Задание 3. Написать ф-цию метод filterEven, которая получает на вход массив чисел и возвращает массив тех из них,
   * которые являются четными
   */



  /**
   * Задание 4. Написать ф-цию метод filterOdd, которая получает на вход массив чисел и возвращает массив тех из них,
   * которые являются нечетными
   */


  /**
   * return statement
   *
   */
}
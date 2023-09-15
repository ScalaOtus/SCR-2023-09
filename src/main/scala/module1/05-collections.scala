package module1

object coll{

  // создать список чисел

  val numbers = List(1, 2, 3, 4, 5)

  // удвоить числа в списке

  val doubleNumbers = numbers.map(_ * 2)



  val words = List("Hello", "World")

  // преобразовать список слов, в список букв

  val chars: List[Char] = words.flatMap(_.toList)



  // отфильтровать четные числа из списка numbers

  val evenNumbers: List[Int] = numbers.filter(_ % 2 == 0)

  val mixedList = List("apple", 2, "banana", 4, "cherry", 6)

  // получить список только из чисел

  val numbersOnly: List[Int] = mixedList.collect{case i: Int => i}


  // Посчитать сумму чисел в списке numbers

  val sum: Int = numbers.sum

  // комбинация элементов с помощью бинарной ассоциативной операции и аккумулятора

  val product = numbers.foldLeft(1)(_ * _)

  // комбинация элементов с помощью бинарной ассоциативной операции с лева направо
  // найти максимальное число


  // комбинация элементов с помощью бинарной ассоциативной операции с лева направо и аккумулятора


  // комбинация элементов с помощью бинарной ассоциативной операции c сохранением промежуточных
  // результатов


  // Map

  val map = Map("1" -> 1, "2" -> 2, "3" -> 3)
  val map2: Map[String, Int] = map.map{ case (k, v) =>
    k ->  (v + 1)
  }

  // сортировка

  case class User(email: String, password: String)
  val users = List(User("b@yandex.ru", "345"), User("a@yandex.ru", "123"), User("c@yandex.ru", "078"))

  val s1 = numbers.sortWith((a, b) => a > b)
  val s2 = users.sortBy(_.email)

  // Цепочка операций

  val result = numbers.filter(_ % 2 == 0).map(_ * 2).sum


}
package module1

object pattern_matching{

  /**
   * Матчинг на типы
   */

  val i: Any = ???

  i match {
    case Int => println("Int")
    case v: String => println("String")
    case v: List[String] => println("List[String]")
    case v: List[Int] => println("List[Int]")
  }



  /**
   * Структурный матчинг
   */

  // isInstanceOf
  // asInstanceOf



  sealed trait Animal{
    def name: String
    def age: Int

    def whoIam: String  = this match {
      case Dog(name, age) => s"I'm dog, $name"
      case Cat(name, age) => s"I'm cat, $name"
    }
  }


  case class Dog(name: String, age: Int) extends Animal


  case class Cat(name: String, age: Int) extends Animal

  /**
   * Матчинг на литерал
   */

  val dog: Dog = ???

  val Dog(n, age) = dog

  val Bim = "Bim"

  dog match {
    case Dog("Bim", age) => println("гав")
    case _ =>
  }


  /**
   * Матчинг на константу
   */

  dog match {
    case Dog(Bim, age) => println("гав")
    case _ =>
  }


  /**
   * Матчинг с условием (гарды)
   */

//  dog match {
//    case Dog(name, age) if age > 5 => ???
//    case Cat(name, age) => ???
//  }




  /**
   * "as" паттерн
   */


  def treatCat(cat: Cat) = ???
  def treatDog(dog: Dog) = ???

  def treat(a: Animal): Animal = a match {
    case d @ Dog(name, age) => treatDog(d)
    case c @ Cat(name, age) => treatCat(c)
  }


  /**
   * используя паттерн матчинг напечатать имя и возраст
   */


  class Person(name: String, age: Int)

  object Person{
    def apply(name: String, age: Int): Person = ???
    def unapply(p: Person): Option[(String, Int)] = ???
  }

  val person: Person = ???

  person match {
    case Person(name, age) => println(name)
  }


  final case class Employee(name: String, address: Address)
  final case class Address(street: String, number: Int)

  val alex = Employee("Alex", Address("XXX", 221))


  /**
   * воспользовавшись паттерн матчингом напечатать номер из поля адрес
   */

   alex match {
     case Employee(_, Address(_, number)) => println(number)
   }




  /**
   * Паттерн матчинг может содержать литералы.
   * Реализовать паттерн матчинг на alex с двумя кейсами.
   * 1. Имя должно соотвествовать Alex
   * 2. Все остальные
   */




  /**
   * Паттерны могут содержать условия. В этом случае case сработает,
   * если и паттерн совпал и условие true.
   * Условия в паттерн матчинге называются гардами.
   */




  /**
   * Реализовать паттерн матчинг на alex с двумя кейсами.
   * 1. Имя должно начинаться с A
   * 2. Все остальные
   */

  alex match {
    case Employee(name, _) if name.startsWith("A") =>
  }



  /**
   *
   * Мы можем поместить кусок паттерна в переменную использую `as` паттерн,
   * x @ ..., где x это любая переменная.
   * Это переменная может использоваться, как в условии,
   * так и внутри кейса
   */

    trait PaymentMethod
    case object Card extends PaymentMethod
    case object WireTransfer extends PaymentMethod
    case object Cash extends PaymentMethod

    case class Order(paymentMethod: PaymentMethod)

    lazy val order: Order = ???

    lazy val pm: PaymentMethod = ???


    def checkByCard(o: Order) = ???

    def checkOther(o: Order) = ???


   pm match {
     case Card | WireTransfer =>
     case _ =>
   }

  /**
   * Мы можем использовать вертикальную черту `|` для матчинга на альтернативы
   */

   sealed trait A
   case class B(v: Int) extends A
   case class C(v: Int) extends A
   case class D(v: Int) extends A

   val a: A = ???

}
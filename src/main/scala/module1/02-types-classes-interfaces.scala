package module1

import java.io.{Closeable, File}
import scala.io.{BufferedSource, Source}


object type_system {

  /**
   * Scala type system
   *
   */



  def absurd(v: Nothing) = ???


  // Generics


//  lazy val file: File = ???

//  lazy val source: BufferedSource = Source.fromFile(file)

  // получить строчки из файла

  //val lines: Iterator[String] = source.getLines()

//  lazy val lines2 = try{
//    source.getLines()
//  } catch {
//    case e => println(e.toString)
//  } finally {
//    source.close()
//  }


 // обобщенный метод для работы с ресурсами

  def ensureClose[S <: {def close(): Unit}, T](source: S)(action: S => T): T = {
    try{
      action(source)
    } finally {
      source.close()
    }
  }

 // val r1: Iterator[String] = ensureClose(source)(bs => bs.getLines())


 // ограничения связанные с дженериками


  /**
   *
   * class
   *
   * конструкторы / поля / методы / компаньоны
   */


   class User (val email: String, val password: String = "123"){

      println("Initialized User")
      def checkPassword(): Boolean = ???
   }

   new User("foo")

   object User {
     def from(email: String, password: String): User  =
       new User("t@q.com", "123")
   }

   val user: User = User.from("t@q.com", "123")

  user.email
  user.password


  /**
   * Задание 1: Создать класс "Прямоугольник"(Rectangle),
   * мы должны иметь возможность создавать прямоугольник с заданной
   * длиной(length) и шириной(width), а также вычислять его периметр и площадь
   *
   */


  /**
   * object
   *
   * 1. Паттерн одиночка
   * 2. Ленивая инициализация
   * 3. Могут быть компаньонами
   */


  /**
   * case class
   *
   */

   case class User2(email: String, password: String)

   val u = User2("", "")

   u.copy(password = "foo")




  // создать case класс кредитная карта с двумя полями номер и cvc


  /**
   * case object
   *
   * Используются для создания перечислений или же в качестве сообщений для Акторов
   */


  /**
   * trait
   *
   */


   trait UserService{
    def get(email: String): User
   }

   trait Identifiable{
     def id: Int
   }

   class UserServiceImpl extends UserService with Identifiable {
     override def get(email: String): User = ???

     override def id: Int = ???
   }

  class Foo
  val us = new UserService{
    override def get(email: String): User = ???
  }







  class A {
    def className() = "A"
  }

  trait B extends A {
    override def className() = "B" + super.className()
  }

  trait C extends B {
    override def className() = "C" + super.className()
  }

  trait D extends A {
    override def className() = "D" + super.className()
  }

  trait E extends C {
    override def className(): String = "E" + super.className()
  }

  // A -> D -> B -> C
  // CBDA
  val v = new A with D with C with B


  // A -> B -> C -> E -> D
  // DECBA
  val v1 = new A with E with D with C with B


  /**
   * Value classes и Universal traits
   */


}
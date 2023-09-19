package module1

import scala.Stream

object parser{


}

case class Car(year: Int, mark: String, model: String, canDrive: Boolean)


object lazyOps{

  // Конструирование

  val stream1: Stream[Int] = 1 #:: 2 #:: Stream.empty[Int]
  val stream2: Stream[Int] = Stream.from(1, 1)

//  stream2.map{ i =>
//    println(s"map $i")
//    i + 2
//  }.filter{ i =>
//    println(s"filter $i")
//    i % 2 == 0
//  }.take(10).toList

  stream2.take(10).toList.view.map{ i =>
    println(s"map $i")
    i + 2
  }.filter{ i =>
    println(s"filter $i")
    i % 2 == 0
  }.force

}
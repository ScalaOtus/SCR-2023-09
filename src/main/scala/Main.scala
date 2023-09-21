import module1.threads.{Thread1, ToyFuture, getRatesLocation1, getRatesLocation2, getRatesLocation3, getRatesLocation4, printRunningTime}
import module1.{executor, hof, lazyOps, list, type_system}


object Main {

  def main(args: Array[String]): Unit = {
    println("Hello, World!" +
      s" thread - ${Thread.currentThread().getName}" )

//    val t1 = new Thread{
//      override def run(): Unit ={
//        Thread.sleep(1000)
//        println(s"Hello ${Thread.currentThread().getName}" )
//      }
//    }
//    val t2 = new Thread{
//      override def run(): Unit ={
//        Thread.sleep(2000)
//        println(s"Hello ${Thread.currentThread().getName}" )
//      }
//    }
//    t2.start()
//    t1.start()

    def rates = {
       val t1 = ToyFuture(10)(executor.pool1)
       val t2 = ToyFuture(20)(executor.pool1)

       t1.onComplete{ i1 =>
         t2.onComplete{i2 =>
           println(i1 + i2)
         }
       }

       val r: ToyFuture[Unit] = for{
         i1 <- t1
         i2 <- t2
       } yield println(i1 + i2)
    }

    printRunningTime(rates)

  }
}
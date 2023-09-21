package module1

import module1.utils.NameableThreads

import java.io.File
import java.util.{Timer, TimerTask}
import java.util.concurrent.{Callable, Executor, ExecutorService, Executors, ForkJoinPool, ThreadFactory, ThreadPoolExecutor}
import scala.collection.mutable
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future, Promise, TimeoutException}
import scala.io.{BufferedSource, Source}
import scala.language.{existentials, postfixOps}
import scala.util.Try

object threads {


  // Thread

  class Thread1 extends Thread{
    override def run(): Unit = {
      println(s"Hello ${Thread.currentThread().getName}" )
    }
  }


  def printRunningTime(v: => Unit): Unit = {
    val start = System.currentTimeMillis()
    v
    val end = System.currentTimeMillis()
    println(s"Execution time ${end - start}")
  }

  // rates

  def getRatesLocation1 = async{
    Thread.sleep(1000)
    println("GetRatesLocation1")
  }

  def getRatesLocation2 = async{
    Thread.sleep(2000)
    println("GetRatesLocation2")
  }

  // async

  def async(f: => Unit): Thread = new Thread{
    override def run(): Unit = f
  }

  def async2[A](f: =>A): A = {
    var r: A = null.asInstanceOf[A]
    val t = new Thread{
      override def run(): Unit = r = f
    }
    t.start()
    r
  }

  def getRatesLocation3 = async2{
    Thread.sleep(1000)
    println("GetRatesLocation1")
    10
  }

  def getRatesLocation4 = async2{
    Thread.sleep(2000)
    println("GetRatesLocation2")
    20
  }

  class ToyFuture[T](v: () => T){
    private var r: T = null.asInstanceOf[T]
    private var isCompleted: Boolean = false
    private val q = mutable.Queue[T => _]()

    def flatMap[B](f: T => ToyFuture[B]): ToyFuture[B] = ???
    def map[B](f: T => B): ToyFuture[B] = ???

    def onComplete[U](f: T => U): Unit = {
      if(isCompleted) f(r)
      else q.enqueue(f)
    }

    def start(executor: Executor) = {
      val res = new Runnable {
        override def run(): Unit = {
          val result = v()
          r = result
          isCompleted = true
          while (q.nonEmpty){
            q.dequeue()(result)
          }
        }
      }
      executor.execute(res)
    }

  }

  object ToyFuture{
    def apply[T](v: => T)(executor: Executor): ToyFuture[T] = {
      val t = new ToyFuture[T](() => v)
      t.start(executor)
      t
    }
  }

}

object executor {
      val pool1: ExecutorService =
        Executors.newFixedThreadPool(2, NameableThreads("fixed-pool-1"))
      val pool2: ExecutorService =
        Executors.newCachedThreadPool(NameableThreads("cached-pool-2"))
      val pool3: ExecutorService =
        Executors.newWorkStealingPool(4)
      val pool4: ExecutorService =
        Executors.newSingleThreadExecutor(NameableThreads("singleThread-pool-4"))
}


object try_{


}

object future{
  // constructors

  def longRunningComputation: Int = ???

  implicit val ec1 = scala.concurrent.ExecutionContext.global

  lazy val f1 = Future("Hello from scala")
  lazy val f2 = Future.successful("Hello from scala")
  lazy val f3 = Future.failed(new Throwable(""))
  lazy val f4 = Future.fromTry(Try())

  val r1: String = Await.result(f1, 5 seconds)
  val r2: Future[String] = Await.ready(f1, 5 seconds)



  // Execution contexts
  lazy val ec = ExecutionContext.fromExecutor(executor.pool1)
  lazy val ec2 = ExecutionContext.fromExecutor(executor.pool2)
  lazy val ec3 = ExecutionContext.fromExecutor(executor.pool3)
  lazy val ec4 = ExecutionContext.fromExecutor(executor.pool4)


}
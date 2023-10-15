package module1.futures

import module1.homework.futures.task_futures_sequence
import org.scalatest.flatspec.AnyFlatSpec

import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class futures_sequence_test extends AnyFlatSpec {

  def limitedExec(limit: Int): ExecutionContext = new ExecutionContext {
    val counter = new AtomicInteger(0)
    val global: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

    override def execute(runnable: Runnable): Unit = {
      counter.incrementAndGet()
      if (counter.get() > limit) {
        throw new Exception("Runnable limit reached, You can do better :)")
      } else {
        global.execute(runnable)
      }
    }

    override def reportFailure(cause: Throwable): Unit = println(cause.getLocalizedMessage)
  }

  def await[A](future: Future[A]): A = Await.result(future, Duration.Inf)

  def fut(i: Int)(implicit ex: ExecutionContext): Future[Int] = Future {
    Thread.sleep(1000)
    i
  }

  "full sequence" should "process list of success module1.futures" in {
    /**
      * best answer will process task with 9 runnable
      * good answer will process task with 12 runnable
      * satisfied answer will process task with any number of runnable
      * choose which one you want
      * */
    val limit = 100

    implicit val exec: ExecutionContext = limitedExec(limit)
    val fut1 = fut(1)
    val fut2 = fut(2)
    val fut3 = fut(3)

   assert(await(task_futures_sequence.fullSequence[Int](List(fut1, fut2, fut3))) === (List(1, 2, 3), List()))
  }

  it should "process list of success and failures" in {
    /**
     * best answer will process task with 7 runnable
     * good answer will process task with 8 runnable
     * satisfied answer will process task with any number of runnable
     * choose which one you want
     * */

    val limit = 100

    implicit val exec: ExecutionContext = limitedExec(limit)
    val ex1 = new Exception("ex1")
    val ex2 = new Exception("ex2")
    val failed1 = Future.failed(ex1)
    val failed2 = Future.failed(ex2)
    val fut1 = fut(1)

   assert(await(task_futures_sequence.fullSequence[Int](List(fut1, failed1, failed2))) === (List(1), List(ex1, ex2)))
  }

  it should "process list of failures" in {
    /**
     * best answer will process task with 4 runnable
     * satisfied answer will process task with any number of runnable
     * choose which one you want
     * */

    val limit = 100

    implicit val exec: ExecutionContext = limitedExec(limit)
    val ex1 = new Exception("ex1")
    val ex2 = new Exception("ex2")
    val failed1 = Future.failed(ex1)
    val failed2 = Future.failed(ex2)

   assert(await(task_futures_sequence.fullSequence[Int](List(failed1, failed2))) === (List(), List(ex1, ex2)))
  }
}
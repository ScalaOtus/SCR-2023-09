package module1.homework.futures

import module1.homework.futures.HomeworksUtils.TaskSyntax

import scala.concurrent
import scala.concurrent.Future.successful
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success, Try}

object task_futures_sequence {

  /**
   * В данном задании Вам предлагается реализовать функцию fullSequence,
   * похожую на Future.sequence, но в отличии от нее,
   * возвращающую все успешные и не успешные результаты.
   * Возвращаемое тип функции - кортеж из двух списков,
   * в левом хранятся результаты успешных выполнений,
   * в правово результаты неуспешных выполнений.
   * Не допускается использование методов объекта Await и мутабельных переменных var
   */

  /**
   * @param futures список асинхронных задач
   * @return асинхронную задачу с кортежом из двух списков
   */
  def fullSequence[A](futures: List[Future[A]])
                     (implicit ex: ExecutionContext): Future[(List[A], List[Throwable])] = {

    val f = Future.sequence(futures.map(f => f.map(Success(_)).recover{
      case x => Failure(x)
    }))
    val tupleFuture = f.map(_.partition(_.isSuccess))
    val values = tupleFuture.map(_._1).map(vl => vl.map(v => v.get))
    val exceptions = tupleFuture.map(_._2).map(vl => vl.map(v => v.failed.get))
    values.zip(exceptions)
  }
}

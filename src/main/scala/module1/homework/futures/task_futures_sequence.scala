package module1.homework.futures

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success}

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
  def fullSequence[T](futures: List[Future[T]])
                     (implicit ex: ExecutionContext): Future[(List[T], List[Throwable])] = {
    val futuresWithResult = futures.map { future =>
      val promise = scala.concurrent.Promise[(T, Option[Throwable])]
      future.onComplete {
        case Success(value) => promise.success((value, None))
        case Failure(ex) => promise.success((null.asInstanceOf[T], Some(ex)))
      }
      promise.future
    }

    Future.sequence(futuresWithResult).map { results =>
      val successfulResults = results.collect { case (value, None) if value != null.asInstanceOf[T] => value }
      val unsuccessfulResults = results.collect { case (null, Some(ex)) => ex }
      (successfulResults, unsuccessfulResults)
    }
  }


}

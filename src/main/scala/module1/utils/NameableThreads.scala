package module1.utils

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

final class NameableThreads private(prefix: String)  extends ThreadFactory{

  val threadNum: AtomicInteger = new AtomicInteger()
  val name = s"$prefix-thread-"
  override def newThread(r: Runnable): Thread = new Thread(r, name + threadNum.getAndAdd(1))
}

object NameableThreads{
  def apply(prefix: String): NameableThreads = new NameableThreads(prefix)
}
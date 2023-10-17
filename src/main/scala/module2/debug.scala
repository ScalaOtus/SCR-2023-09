package module2

import cats.effect.IO

object debug {
  /** Extension methods for an effect of type `IO[A]`. */

  implicit class DebugHelper[A](ioa: IO[A]) {
    /** Print to the console the value of the effect
     * along with the thread it was computed on. */
    def debug: IO[A] =
      for {
        a <- ioa
        tn = Thread.currentThread.getName
        _ = println(s"[${tn}] $a")
      } yield a }
}

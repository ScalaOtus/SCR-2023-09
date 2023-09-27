package module1.homework.futures

object HomeworksUtils {

  final case class TaskNotDone(text: String)
    extends RuntimeException(s"выполните задание: \n $text")

  trait TaskDef {
    def applySeq(num: Seq[Int]): Nothing

    def apply(num: Int*): Nothing = applySeq(num)
  }

  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  implicit class TaskSyntax(private val cs: StringContext) extends AnyVal {

    def task(refs: Any*): TaskDef = _ => {
      val message = cs.s(refs: _*).stripMargin
      throw TaskNotDone(message)
    }
  }

}

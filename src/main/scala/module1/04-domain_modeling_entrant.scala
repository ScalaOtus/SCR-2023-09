package module1

import java.time.LocalDate
import scala.util.matching.Regex

object entrant {
// БО "Абитуриент"
  case class Entrant(
      id: String, // Уникальный идентификатор
      userId: String, // Ссылка на объект "Пользователь"
      firstName: String,
      middleName: Option[String],
      lastName: String,
      birthDate: LocalDate,
      phone: String,
      email: Option[String], // Валидный e-mail.
      snils: Option[String], // Страховой номер индивидуального лицевого счёта
      isFromCrimea: Option[Boolean], // Гражданин крыма?
      documentUID: Option[String] // Если гражданин крыма - ссылка на документ
  )

  type EntrantId // Что представляет собой id, прсто любая строка?

  type UserId // // Что представляет собой id, прсто любая строка?

  type FirstName

  type LastName

  type MiddleName

  type BirthDate

  type Phone

  type Email

  type ValidEmail

  type Snils

  type NonEmptyString

  type CrimeaCitizen

  object functional_domain_model {

    type Country
    type Operator
    type Number
    type DocumentId

    sealed trait Email
    case class ValidEmail(email: NonEmptyString, validateDate: LocalDate) extends Email
    case object NotValidEmail extends Email

    case class Phone(country: Country, op: Operator, number: Number)

    case class EntrantId(value: GUID) extends AnyVal
    case class UserId(value: GUID) extends AnyVal
    
    type GUID = String
    type NonEmptyString = String

    case class Range50 private (str: String)

    object Range50 {
      def from(str: String): Option[Range50] = 
        Option(str.trim()).filter(str => str.nonEmpty && str.length() <= 50).map(Range50(_))
    }
    
    case class Cyrillic50Chars private(c: Cyrillic, r: Range50) // Cyrillic * Range50

    object Cyrillic50Chars{
      def from(str: String): Option[Cyrillic50Chars] = ???
    }

    final case class Cyrillic private (str: String)



    case class FirstName(value: Cyrillic50Chars)

    case class CrimeaCitizen(documentId: DocumentId)

  }

}

package module4.phoneBook

import module4.phoneBook.dao.entities.PhoneRecord

import scala.util.{Failure, Success, Try}

package object dto {

    case class PhoneRecordDTO(phone: String, fio: String, zipCode: String, address: String)

    object PhoneRecordDTO{

        def from(phoneRecord: PhoneRecord): PhoneRecordDTO = PhoneRecordDTO(
            phoneRecord.phone,
            phoneRecord.fio,
            "",
            ""
        ) 
    }

    case class RecordId(id: Int)

    object IdVar{
        def unapply(str: String): Option[RecordId] = {
            Try(str.toInt) match {
                case Failure(_) => None
                case Success(value) if value < 0 => None
            }
        }
    }
}

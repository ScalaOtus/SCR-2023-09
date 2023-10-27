package module4.phoneBook.dao.repositories

import io.getquill.context.ZioJdbc._
import module4.phoneBook.dao.entities._
import module4.phoneBook.db
import zio.{Has, ULayer, ZLayer}
import io.getquill.Ord

object PhoneRecordRepository {
  val ctx = db.Ctx
  import ctx._

  type PhoneRecordRepository = Has[Service]

  trait Service{
      def find(phone: String): QIO[Option[PhoneRecord]]
      def list(): QIO[List[PhoneRecord]]
      def insert(phoneRecord: PhoneRecord): QIO[Unit]
      def update(phoneRecord: PhoneRecord): QIO[Unit]
      def delete(id: String): QIO[Unit]
  }

  class ServiceImpl extends Service{

      val phoneRecordSchema = quote{
        querySchema[PhoneRecord](""""PhoneRecord"""")
      }

      val addressSchema = quote{
        querySchema[Address](""""Address"""")
      }
      

     // SELECT x1."id", x1."phone", x1."fio", x1."addressId" FROM "PhoneRecord" x1 WHERE x1."phone" = ?
      def find(phone: String): QIO[Option[PhoneRecord]] = ctx.run(
        phoneRecordSchema.filter(_.phone == lift(phone)).sortBy(_.phone).take(1)
      ).map(_.headOption)

      // SELECT x."id", x."phone", x."fio", x."addressId" FROM "PhoneRecord" x
      def list(): QIO[List[PhoneRecord]] = ctx.run(phoneRecordSchema)
      
      def insert(phoneRecord: PhoneRecord): QIO[Unit] = ctx.run(
        phoneRecordSchema.insert(lift(phoneRecord))
      ).unit


      def insert(phoneRecords: List[PhoneRecord]): QIO[Unit] = ctx.run(
        liftQuery(phoneRecords).foreach( p => phoneRecordSchema.insert(p))
      ).unit

      def update(phoneRecord: PhoneRecord): QIO[Unit] = 
        ctx.run(phoneRecordSchema.filter(_.id == lift(phoneRecord.id)).update(lift(phoneRecord))).unit

      def delete(id: String): QIO[Unit] = 
        ctx.run(phoneRecordSchema.filter(_.id == lift(id)).delete).unit


        // implicit joins

        // SELECT phoneRecord."id", phoneRecord."phone", phoneRecord."fio", phoneRecord."addressId", address."id", address."zipCode", address."streetAddress" FROM "PhoneRecord" phoneRecord, "Address" address WHERE address."id" = phoneRecord."addressId"
        ctx.run(
          for{
            phoneRecord <- phoneRecordSchema
            address <- addressSchema if (address.id == phoneRecord.addressId)
          } yield (phoneRecord, address)
        )

        // applicative join

        ctx.run(
          phoneRecordSchema.join(addressSchema).on(_.addressId == _.id)
        )

        // flat join
        ctx.run(
          for{
            phoneRecord <- phoneRecordSchema
            address <- addressSchema.join(_.id == phoneRecord.addressId)
            address <- addressSchema.join(_.id == phoneRecord.addressId)
            address <- addressSchema.join(_.id == phoneRecord.addressId)
          } yield (phoneRecord)
        )
  }

  
 
  val live: ULayer[PhoneRecordRepository] = ???
}

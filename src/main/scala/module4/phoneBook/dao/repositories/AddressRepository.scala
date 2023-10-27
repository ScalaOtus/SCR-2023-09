package module4.phoneBook.dao.repositories

import zio.Has
import io.getquill.context.ZioJdbc._
import module4.phoneBook.dao.entities.Address
import module4.phoneBook.db
import zio.{ULayer, ZLayer}

object AddressRepository {
  type AddressRepository = Has[Service]
  
  import db.Ctx._

  trait Service{
      def findBy(id: String): QIO[Option[Address]]
      def insert(phoneRecord: Address): QIO[Unit]
      def update(phoneRecord: Address): QIO[Unit]
      def delete(id: String): QIO[Unit]
  }

  class ServiceImpl extends Service{

      val addressSchema = quote{
        querySchema[Address](""""Address"""")
    }
      def findBy(id: String): QIO[Option[Address]] = 
         run(addressSchema.filter(_.id == lift(id))).map(_.headOption)
      
      def insert(address: Address): QIO[Unit] = run(addressSchema.insert(lift(address))).unit
      
      def update(address: Address): QIO[Unit] = run(addressSchema.update(lift(address))).unit
      
      def delete(id: String): QIO[Unit] = run(addressSchema.filter(_.id == lift(id)).delete).unit
      
  }

  val live: ULayer[AddressRepository] = ZLayer.succeed(new ServiceImpl)
}

package module4.phoneBook.services

import module4.phoneBook.dao.entities.{Address, PhoneRecord}
import module4.phoneBook.dao.repositories.{AddressRepository, PhoneRecordRepository}
import module4.phoneBook.db
import module4.phoneBook.db.DataSource
import module4.phoneBook.dto._
import zio.{Has, RIO, ZIO, ZLayer}
import zio.random.Random

object PhoneBookService {

     type PhoneBookService = Has[Service]
  
     trait Service{
       def list(): ZIO[DataSource, Throwable, List[PhoneRecordDTO]]
       def find(phone: String): ZIO[DataSource, Option[Throwable], (String, PhoneRecordDTO)]
       def insert(phoneRecord: PhoneRecordDTO): RIO[DataSource with Random, String]
       def update(id: String, addressId: String, phoneRecord: PhoneRecordDTO): RIO[DataSource, Unit]
       def delete(id: String): RIO[DataSource, Unit]
     }

    class Impl(phoneRecordRepository: PhoneRecordRepository.Service, addressRepository: AddressRepository.Service) extends Service {
       val ctx  = db.Ctx


      override def list(): ZIO[DataSource, Throwable, List[PhoneRecordDTO]] =
        phoneRecordRepository.list().map(_.map(PhoneRecordDTO.from))

      def find(phone: String): ZIO[DataSource, Option[Throwable], (String, PhoneRecordDTO)] = for{
          result <- phoneRecordRepository.find(phone).some
        } yield (result.id, PhoneRecordDTO.from(result))

        def insert(phoneRecord: PhoneRecordDTO): RIO[DataSource with Random, String] = for{
          uuid <- zio.random.nextUUID.map(_.toString())
          uuid2 <- zio.random.nextUUID.map(_.toString())
          address = Address(uuid, phoneRecord.zipCode, phoneRecord.address)
          _ <- ctx.transaction(
            for{
             _ <- addressRepository.insert(address)
             _ <- phoneRecordRepository.insert(PhoneRecord(uuid2, phoneRecord.phone, phoneRecord.fio, address.id))
            } yield ()
          )
        } yield uuid
        
        def update(id: String, addressId: String,  phoneRecord: PhoneRecordDTO): RIO[DataSource, Unit] = for{
            _ <- phoneRecordRepository.update(PhoneRecord(id, phoneRecord.phone, phoneRecord.fio, addressId))
        } yield ()
        
        def delete(id: String): RIO[DataSource, Unit] = for{
            _ <- phoneRecordRepository.delete(id)
        } yield ()
        
    }

    val live: ZLayer[PhoneRecordRepository.PhoneRecordRepository with AddressRepository.AddressRepository, Nothing, PhoneBookService.PhoneBookService] = 
      ZLayer.fromServices[PhoneRecordRepository.Service, AddressRepository.Service, PhoneBookService.Service]((repo, addressRepo) => 
        new Impl(repo, addressRepo)
      )


  def find(phone: String): ZIO[PhoneBookService with DataSource, Option[Throwable], (String, PhoneRecordDTO)] =
    ZIO.accessM(_.get.find(phone))

  def list(): ZIO[PhoneBookService with DataSource, Throwable, List[PhoneRecordDTO]] =
    ZIO.accessM(_.get.list())
}

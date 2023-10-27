package module4.phoneBook

import module4.phoneBook.dao.repositories.{AddressRepository, PhoneRecordRepository}
import module4.phoneBook.db._
import module4.phoneBook.services.PhoneBookService
import zio.blocking.Blocking
import zio.clock.Clock
import zio.random.Random

object App {

    type AppEnvironment = PhoneBookService.PhoneBookService with 
        PhoneRecordRepository.PhoneRecordRepository with 
        AddressRepository.AddressRepository with
        Clock with Blocking with LiquibaseService.Liqui with 
        LiquibaseService.LiquibaseService 
        with Random with DataSource


    val appEnvironment = Blocking.live >+> zioDS >+>
    LiquibaseService.liquibaseLayer ++ 
    PhoneRecordRepository.live >+> AddressRepository.live >+> 
    PhoneBookService.live ++ LiquibaseService.live

}

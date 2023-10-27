package module4.phoneBook.dao.entities

case class PhoneRecord(id: String, phone: String, fio: String, addressId: String)

case class Address(id: String, zipCode: String, streetAddress: String)
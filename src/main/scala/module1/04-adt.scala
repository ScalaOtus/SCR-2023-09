package module1

import java.time.LocalDate
import java.time.YearMonth

object adt {

  object tuples {

    /** Tuples ()
      */



    type ProductUnitBoolean = (Unit, Boolean)


    /** Создать возможные экземпляры с типом ProductUnitBoolean
      */

    lazy val p1: ProductUnitBoolean = ((), true)
    lazy val p2: ProductUnitBoolean = ((), false)


    /** Реализовать тип Person который будет содержать имя и возраст
      */

    type Person = (String, Int)

    val p3: Person = ("Alex", 42)


    /**  Реализовать тип `CreditCard` который может содержать номер (String),
      *  дату окончания (java.time.YearMonth), имя (String), код безопастности (Short)
      */

    type CreditCard = (String, java.time.YearMonth, String, Short)

    lazy val cc: CreditCard = ???



  }

  object case_classes {

    /** Case classes
      */


    //  Реализовать Person с помощью case класса

    case class Person(name: String, age: Int)
    // Создать экземпляр для Tony Stark 42 года

    val tony = Person("Tony Stark", 42)


    // Создать case class для кредитной карты

    case class CreditCard(number: String, date: java.time.YearMonth, name: String, cvc: Short)

  }



  object either {

    /** Sum
      */

    /** Either - это наиболее общий способ хранить один из двух или более кусочков информации в одно время.
      * Также как и кортежи обладает целым рядом полезных методов
      * Иммутабелен
      */

    // trait Ether
    // Right() extends Either
    // Left() extends Either

    type IntOrString = Either[Int, String]
    type BooleanOrUnit = Either[Boolean, Unit]

    val v1: BooleanOrUnit = Left(true)
    val v2: BooleanOrUnit = Left(false)
    val v3: BooleanOrUnit = Right(())

    /** Реализовать экземпляр типа IntOrString с помощью конструктора Right
      */
    val intOrString: IntOrString = Left(1)


    type CreditCard
    type WireTransfer
    type Cash

    /** \
      * Реализовать тип PaymentMethod который может быть представлен одной из альтернатив
      */
    type PaymentMethod = Either[CreditCard, Either[WireTransfer, Cash]]

  }

  object sealed_traits {

    /** Также Sum type можно представить в виде sealed trait с набором альтернатив
      */

    sealed trait PaymentMethod
    final case class CreditCard() extends PaymentMethod
    final case class WireTransfer() extends PaymentMethod
    final case class Cash() extends PaymentMethod

  }

  object cards {

    sealed trait Suit                 // масть
    case object Clubs extends Suit               // крести
    case object Diamonds extends Suit            // бубны
    case object Spades extends Suit              // пики
    case object Hearts extends Suit              // червы
    sealed trait Rank                 // номинал
    type Two                  // двойка
    type Three                // тройка
    type Four                 // четверка
    type Five                 // пятерка
    type Six                  // шестерка
    type Seven                // семерка
    type Eight                // восьмерка
    type Nine                 // девятка
    type Ten                  // десятка
    type Jack                 // валет
    type Queen                // дама
    type King                 // король
    type Ace                  // туз

    case class Card(suit: Suit, rank: Rank)                 // карта
    type Deck = Set[Card]                 // колода
    type Hand = Set[Card]                 // рука
    case class Player(name: String, hand: Hand)               // игрок
    case class Game(deck: Deck, players: Set[Player])                // игра
    type PickupCard = (Hand, Card) => Card           // взять карту

  }

}

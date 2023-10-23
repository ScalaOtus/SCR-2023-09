package module1.homework.collections

import scala.util.Random


object exp1 {
  case class BallStock(black: Int, white: Int) {

    def giveBall(): BallStock = this match {
      case BallStock(0, v) => BallStock(0, v - 1)
      case BallStock(v, 0) => BallStock(v - 1, 0)
      case BallStock(b, w) if Random.nextBoolean() => BallStock(b, w - 1)
      case BallStock(b, w) => BallStock(b - 1, w)
    }
  }

  object BallsExperiment {

    val CountExperiments: Int = 10000

    def doExperiment(): Double = {
      val countOk = List.fill(CountExperiments)(() => BallStock(3, 3))
        .map(s => s().giveBall().giveBall())
        .count(s => s.white < 3)

      countOk.doubleValue() / CountExperiments
    }
  }

  BallsExperiment.doExperiment()
} // 0.75

object exp2 {

  sealed trait BallColor
  case object White extends BallColor
  case object Black extends BallColor


  type BallStock = List[BallColor]

  object BallStock {
    def apply(nBlack: Int, nWhite: Int): BallStock = Random.shuffle(
      List.fill(nBlack)(Black) ++ List.fill(nWhite)(White)
    )
  }

  object BallsExperiment {
    val CountExperiments: Int = 1000000

    def doExperiment() = {
      val countOk = List.fill(CountExperiments)(BallStock.apply(3, 3))
        .map(stock => stock.take(2).count(_ == White))
        .count(_ > 0)
      countOk.toDouble / CountExperiments
    }
  }

  BallsExperiment.doExperiment()
}
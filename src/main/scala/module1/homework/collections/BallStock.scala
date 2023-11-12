package module1.homework.collections

import scala.util.Random

object exp {
  class UrnExperiment {
    val urn: List[Boolean] = List.fill(3)(true) ++ List.fill(3)(false)

    def drawTwoBalls(): (Boolean, Boolean) = {
      val shuffledUrn = Random.shuffle(urn)
      (shuffledUrn.head, shuffledUrn.tail.head)
    }
  }

  object UrnExperiment {
    def checkProbability(): Unit = {
      val results = List.fill(100000)(new UrnExperiment).map(_.drawTwoBalls())

      val whiteBallCount = results.count {
        case (firstBall, secondBall) => firstBall || secondBall
      }
      val probability = whiteBallCount.toDouble / results.length

      println(s"Вероятность появления белого шара: $probability")
    }
  }

  UrnExperiment.checkProbability()

}

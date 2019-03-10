package chapter5

import cats.data.EitherT
import cats.instances.future._
import cats.syntax.applicative._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Autobots {

  type Response1[A] = Future[Either[String, A]]

  type Response[A] = EitherT[Future, String, A]

  def getPowerLevel(autobot: String): Response[Int] = {
    powerLevels.get(autobot) match {
      case Some(powerLevel) =>
        powerLevel.pure[Response]
      case None =>
        EitherT.left(Future(s"$autobot could not be reached"))
    }
  }

  def canPerformSpecialMove(ally1: String, ally2: String): Response[Boolean] = {
    for {
      powerLevel1 <- getPowerLevel(ally1)
      powerLevel2 <- getPowerLevel(ally2)
    } yield (powerLevel1 + powerLevel2) > 15
  }

  private val powerLevels = Map(
    "Jazz"      -> 6,
    "Bumblebee" -> 8,
    "Hot Rod"   -> 10
  )

}

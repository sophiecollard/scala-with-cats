package chapter4

import cats.data.Reader
import cats.syntax.applicative._

object ReaderExample {

  final case class Database(
    usernames: Map[Int, String],
    passwords: Map[String, String]
  )

  type DatabaseReader[A] = Reader[Database, A]

  def findUsername(userId: Int): DatabaseReader[Option[String]] = {
    Reader(db => db.usernames.get(userId))
  }

  def checkPassword(username: String, password: String): DatabaseReader[Boolean] = {
    Reader(db => db.passwords.get(username).contains(password))
  }

  def checkLogin(userId: Int, password: String): DatabaseReader[Boolean] = {
    for {
      maybeUsername <- findUsername(userId)
      passwordIsValid <- maybeUsername match {
        case Some(username) => checkPassword(username, password)
        case None           => false.pure[DatabaseReader]
      }
    } yield passwordIsValid
  }

}

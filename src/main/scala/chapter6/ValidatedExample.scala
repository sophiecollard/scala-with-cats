package chapter6

import cats.data.Validated
import cats.instances.vector._ // for Semigroupal
import cats.syntax.apply._ // for mapN
import cats.syntax.either._

object ValidatedExample {

  final case class User(name: String, age: Int)

  type FormData = Map[String, String]
  type FailFast[A] = Either[Vector[String], A]
  type FailSlow[A] = Validated[Vector[String], A]

  def readUser(data: FormData): FailSlow[User] = {
//    readName(data).toValidated.andThen { name =>
//      readAge(data).toValidated.map { age =>
//        User(name, age)
//      }
//    }

    (
      readName(data).toValidated,
      readAge(data).toValidated
    ).mapN(User.apply)
  }

  def readName(data: FormData): FailFast[String] = {
    val fieldName = "name"

    for {
      name <- getValue(fieldName)(data)
      _ <- nonBlank(fieldName)(name)
    } yield name
  }

  def readAge(data: FormData): FailFast[Int] = {
    val fieldName = "age"

    for {
      ageAsString <- getValue(fieldName)(data)
      age <- parseInt(fieldName)(ageAsString)
      _ <- nonNegative(fieldName)(age)
    } yield age
  }

  private def getValue(fieldName: String)(data: FormData): FailFast[String] = {
    data
      .get(fieldName)
      .toRight(Vector(s"$fieldName field not specified"))
  }

  private def parseInt(name: String)(value: String): FailFast[Int] = {
    Either
      .catchOnly[NumberFormatException](value.toInt)
      .leftMap(_ => Vector(s"$name must be an integer"))
  }

  private def nonNegative(name: String)(value: Int): FailFast[Int] = {
    Either.right[Vector[String], Int](value)
      .ensure(Vector(s"$name must be positive"))(_ >= 0)
  }

  private def nonBlank(name: String)(value: String): FailFast[String] = {
    Either.right[Vector[String], String](value)
      .ensure(Vector(s"$name must not be blank"))(_.trim.nonEmpty)
  }

}

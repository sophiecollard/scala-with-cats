package chapter4

import cats.data.Writer
import cats.instances.vector._
import cats.syntax.applicative._
import cats.syntax.writer._

object WriterExample {

  // Helper method
  def slowly[A](body: => A): A = {
    try {
      body
    } finally {
      Thread.sleep(100)
    }
  }

  // Regular implementation of factorial
  def factorial(n: Int): Int = {
    val result = slowly {
      if (n <= 1) {
        n
      } else {
        factorial(n - 1) * n
      }
    }
    println(s"factorial of $n is $result")
    result
  }

  type Logged[A] = Writer[Vector[String], A]

  def factorialW(n: Int): Writer[Vector[String], Int] = {
    for {
      result <- slowly {
        if (n <= 1) {
          n.pure[Logged]
        } else {
          factorialW(n - 1).map(_ * n)
        }
      }
      _ <- Vector(s"factorial of $n is $result").tell
    } yield result
  }

}

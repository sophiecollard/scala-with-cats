package chapter4

import cats.data.State
import cats.syntax.applicative._

object StateExample {

  type CalcState[A] = State[List[Int], A]

  def evalAll(input: List[String]): CalcState[Int] = {
    input.foldLeft(0.pure[CalcState]) { (acc, next) =>
      acc.flatMap(_ => evalOne(next))
    }
  }

  def evalOne(input: String): CalcState[Int] = {
    input match {
      case "+" => operator(_ + _)
      case "*" => operator(_ * _)
      case n   => operand(n.toInt)
    }
  }

  def operand(n: Int): CalcState[Int] = State[List[Int], Int] { oldStack =>
    val result = n
    val newStack = n :: oldStack
    (newStack, result)
  }

  def operator(f: (Int, Int) => Int): CalcState[Int] = State[List[Int], Int] {
    case b :: a :: tail =>
      val result = f(a, b)
      (tail, result)
    case _ =>
      sys.error("Less than two operands found")
  }

}

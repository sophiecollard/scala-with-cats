package chapter3

trait PrintableSyntax {

  implicit class PrintableOps[A](value: A) {
    def format(implicit printable: Printable[A]): String = {
      printable.format(value)
    }
  }

}

package chapter3.printable

trait PrintableInstances {

  implicit val stringPrintable: Printable[String] = {
    new Printable[String] {
      def format(value: String): String = {
        "\"" + value + "\""
      }
    }
  }

  implicit val booleanPrintable: Printable[Boolean] = {
    new Printable[Boolean] {
      def format(value: Boolean): String = {
        if (value) "yes" else "no"
      }
    }
  }

}

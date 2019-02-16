package chapter3.printable

trait PrintableImplicits
  extends PrintableSyntax
    with PrintableInstances

object PrintableImplicits extends PrintableImplicits

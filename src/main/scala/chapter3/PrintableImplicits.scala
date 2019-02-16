package chapter3

trait PrintableImplicits
  extends PrintableSyntax
    with PrintableInstances

object PrintableImplicits extends PrintableImplicits

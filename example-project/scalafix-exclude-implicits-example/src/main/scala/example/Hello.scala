package example

import scala.io.Codec

object Hello extends  App {
  def foo(implicit codec: Codec) = 3
  foo
}


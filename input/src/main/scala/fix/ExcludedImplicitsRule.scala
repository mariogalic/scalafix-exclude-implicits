/*
rule = ExcludedImplicitsRule
ExcludedImplicitsRuleConfig.blacklist = [
  fallbackSystemCodec
  myUnwantedImplicit
]
*/
package fix

import scala.io.Codec

object ExcludedImplicitsRule {
  Foo.bar
}

object Foo {
  def bar(implicit codec: Codec): Int = 3
}

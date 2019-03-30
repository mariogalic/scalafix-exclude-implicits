/*
rule = ExcludedImplicitsRule
ExcludedImplicitsRuleConfig.blacklist = [
  fallbackSystemCodec
  myUnwantedImplicit
]
*/
package fix

import scala.io.Codec

object Hello {
  def foo(implicit codec: Codec) = 3
  foo
}

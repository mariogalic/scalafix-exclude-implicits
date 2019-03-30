package fix

import metaconfig.Configured
import scalafix.v1._
import scala.meta._

case class ExcludedImplicitsRuleConfig(
  blacklist: List[String] = Nil
)

object ExcludedImplicitsRuleConfig {
  def default = ExcludedImplicitsRuleConfig()
  implicit val surface =
    metaconfig.generic.deriveSurface[ExcludedImplicitsRuleConfig]
  implicit val decoder =
    metaconfig.generic.deriveDecoder(default)
}

class ExcludedImplicitsRule(config: ExcludedImplicitsRuleConfig)
    extends SemanticRule("ExcludedImplicitsRule") {

  def this() = this(ExcludedImplicitsRuleConfig())

  override def withConfiguration(config: Configuration): Configured[Rule] =
    config.conf
      .getOrElse("ExcludedImplicitsRuleConfig")(this.config)
      .map { newConfig => new ExcludedImplicitsRule(newConfig) }

  case class ExcludedImplicitsDiagnostic(callee: Term, excludedImplicit: String) extends Diagnostic {
    override def position: Position = callee.pos
    override def message: String =
      s"Attempting to pass excluded implicit $excludedImplicit to $callee'"
  }

  override def fix(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect {
      case term: Term if term.synthetic.isDefined => // TODO: Use ApplyTree(func, args)
        val struct = term.synthetic.structure
        val isImplicit = struct.contains("implicit")
        val excludedImplicit = config.blacklist.find(struct.contains)
        if (isImplicit && excludedImplicit.isDefined)
          Patch.lint(ExcludedImplicitsDiagnostic(term, excludedImplicit.getOrElse(config.blacklist.mkString(","))))
        else
          Patch.empty
    }.asPatch
  }

}

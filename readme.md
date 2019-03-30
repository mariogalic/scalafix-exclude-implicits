# Scalafix rule to exclude specific implicits

This project attempts to solve SO question: [Exclude a specific implicit from a Scala project](https://stackoverflow.com/questions/50336960/exclude-a-specific-implicit-from-a-scala-project)

[Scalafix](https://github.com/scalacenter/scalafix/releases/tag/v0.9.0) can inspect [synthetic code](https://github.com/scalacenter/scalafix/blob/master/docs/developers/semantic-tree.md) such as inferred type parameters, implicit arguments, implicit conversions, inferred .apply and for-comprehensions.


TODO: Replace the inefficient implementation which uses `String.contains` with perhaps `ApplyTree`.

```
scalafixDependencies in ThisBuild += "com.gu" %% "scalafix-exclude-implicits" % "0.1.0-SNAPSHOT",
```

`.scalafix.conf`:
```
rule = ExcludedImplicitsRule
ExcludedImplicitsRuleConfig.blacklist = [
  fallbackSystemCodec
  myUnwantedImplicit
]
```

`scalacOptions:`
```
scalacOptions ++= List(
      "-Yrangepos",
      "-Xplugin-require:semanticdb",
      "-P:semanticdb:synthetics:on"
    )
```

Publish locally with `sbt rules/publishLocal`

Code is based on:
* Project was created via `sbt new scalacenter/scalafix.g8`
* https://scalacenter.github.io/scalafix/docs/developers/tutorial.html
* https://github.com/scalacenter/scalafix/tree/master/scalafix-rules/src/main/scala/scalafix/internal/rule





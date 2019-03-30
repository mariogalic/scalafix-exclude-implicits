lazy val V = _root_.scalafix.sbt.BuildInfo
inThisBuild(
  List(
    organization := "com.gu",
    homepage := Some(url("https://github.com/mario-galic/scalafix-exclude-implicits")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "mario-galic",
        "Mario Galic",
        "mariol.galic@guardian.co.uk",
        url("https://github.com/mario-galic")
      )
    ),
    scalaVersion := V.scala212,
    addCompilerPlugin(scalafixSemanticdb),
    scalacOptions ++= List(
      "-Yrangepos",
      "-Xplugin-require:semanticdb",
      "-P:semanticdb:synthetics:on"
    )
  )
)

skip in publish := true

lazy val rules = project.settings(
  moduleName := "scalafix-exclude-implicits",
  libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % V.scalafixVersion
)

lazy val input = project.settings(
  skip in publish := true
)

lazy val output = project.settings(
  skip in publish := true
)

lazy val tests = project
  .settings(
    skip in publish := true,
    libraryDependencies += "ch.epfl.scala" % "scalafix-testkit" % V.scalafixVersion % Test cross CrossVersion.full,
    compile.in(Compile) := 
      compile.in(Compile).dependsOn(compile.in(input, Compile)).value,
    scalafixTestkitOutputSourceDirectories :=
      sourceDirectories.in(output, Compile).value,
    scalafixTestkitInputSourceDirectories :=
      sourceDirectories.in(input, Compile).value,
    scalafixTestkitInputClasspath :=
      fullClasspath.in(input, Compile).value,
  )
  .dependsOn(rules)
  .enablePlugins(ScalafixTestkitPlugin)

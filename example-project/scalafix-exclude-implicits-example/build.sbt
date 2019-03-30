import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "scalafix-exclude-implicits-example",
    libraryDependencies += scalaTest % Test,
    addCompilerPlugin(scalafixSemanticdb),
    scalacOptions ++= Seq(
      "-Yrangepos",
      "-Xplugin-require:semanticdb",
      "-P:semanticdb:synthetics:on",
    ),
    scalafixDependencies in ThisBuild += "com.gu" %% "scalafix-exclude-implicits" % "0.1.0-SNAPSHOT",
  )


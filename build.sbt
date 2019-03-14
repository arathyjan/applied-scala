name := "applied-scala"

version := "1.0"

scalaVersion := "2.12.8"

// We force the timezone to UTC to be consistent between deving on host and within docker
// To set this parameter we need to enable forking to a separate JVM process
fork := true

javaOptions += "-Duser.timezone=UTC"

// When building for deployment
// Overrides the "mainClass" setting in the "Compile" configuration
mainClass in Compile := Some("com.reagroup.appliedscala.Main")

// When building to run locally/test
// Overrides the "mainClass setting in the "Compile" configuration, only during the "run" task
mainClass in(Compile, run) := Some("com.reagroup.appliedscala.Main")

resolvers ++= Seq(
  "rea nexus release" at "http://rea-sonatype-nexus.services.delivery.realestate.com.au/nexus/content/repositories/releases"
)

val catsVersion = "1.1.0"
val circeVersion = "0.9.3"
val Http4sVersion = "0.18.15"
val Http4sTimerVersion = "0.0.3"
val postgresqlVersion = "42.2.4"
val doobieVersion = "0.5.3"
val specs2Version = "4.3.6"
val reaScalaLoggingVersion = "1.0.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
  // for auto-derivation of JSON codecs
  "io.circe" %% "circe-generic",
  // for string interpolation to JSON model
  "io.circe" %% "circe-literal"
).map(_ % circeVersion)

libraryDependencies ++= Seq(
  "com.rea-group" %% "rea-scala-logging" % reaScalaLoggingVersion,
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
  "org.http4s" %% "http4s-circe" % Http4sVersion,
  "org.http4s" %% "http4s-dsl" % Http4sVersion,
  "org.postgresql" % "postgresql" % postgresqlVersion,
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.specs2" %% "specs2-core" % specs2Version % "test",
  "org.specs2" %% "specs2-matcher-extra" % specs2Version % "test",
  "org.specs2" %% "specs2-scalacheck" % specs2Version % "test",
  "org.http4s" %% "http4s-testing" % Http4sVersion % "test",
)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
//  "-Xlint",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-language:higherKinds",
  "-Ypartial-unification"
)

scalacOptions in (Compile, console) := Seq("without -Ywarn-unused-imports")
scalacOptions in Test ++= Seq("-Yrangepos")

testFrameworks := Seq(TestFrameworks.Specs2)

test in assembly := {}

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

assemblyOutputPath in assembly := new File("target/app.jar")

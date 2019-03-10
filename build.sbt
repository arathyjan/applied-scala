name := "applied-scala"

version := "1.0"

scalaVersion := "2.12.8"

mainClass := Some("com.reagroup.appliedscala.Main")

resolvers ++= Seq(
  "rea nexus release" at "http://rea-sonatype-nexus.services.delivery.realestate.com.au/nexus/content/repositories/releases"
)

val catsVersion = "1.1.0"
val circeVersion = "0.9.3"
val http4sVersion = "0.18.19"
val postgresqlVersion = "42.2.4"
val doobieVersion = "0.5.3"
val specs2Version = "4.3.6"
val reaScalaLoggingVersion = "1.0.0"
val reaDiagnosticsVersion = "1.1.0"

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
  "com.rea-group"   %% "rea-scala-logging"      % reaScalaLoggingVersion,
  "com.rea-group"   %% "http4s-diagnostics"     % reaDiagnosticsVersion,
  "org.typelevel"   %% "cats-core"              % catsVersion,
  "org.http4s"      %% "http4s-blaze-server"    % http4sVersion,
  "org.http4s"      %% "http4s-blaze-client"    % http4sVersion,
  "org.http4s"      %% "http4s-circe"           % http4sVersion,
  "org.http4s"      %% "http4s-dsl"             % http4sVersion,
  "org.postgresql"   % "postgresql"             % postgresqlVersion,
  "org.tpolecat"    %% "doobie-core"            % doobieVersion,
  "org.tpolecat"    %% "doobie-postgres"        % doobieVersion,
  "org.specs2"      %% "specs2-core"            % specs2Version % "test",
  "org.specs2"      %% "specs2-matcher-extra"   % specs2Version % "test",
  "org.specs2"      %% "specs2-scalacheck"      % specs2Version % "test",
  "org.http4s"      %% "http4s-testing"         % http4sVersion % "test",
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

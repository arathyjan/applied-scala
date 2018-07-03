addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.4")

// waiting on this https://github.com/flyway/flywaydb.org/pull/86
resolvers += "Flyway" at "https://davidmweber.github.io/flyway-sbt.repo"

addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.2.0")

addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.2.1")

addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.2")
package com.reagroup.appliedscala.urls.diagnostics

import java.util.concurrent.TimeUnit.SECONDS
import scala.concurrent.duration.Duration
import cats.effect.IO
import com.reagroup.api.infrastructure.diagnostics._

class PostgresqlRepositoryCheck(diagnostic: IO[Unit]) {

  /*
   * Calling `diagnostic` returns `IO[Unit]`. The rea-scala-diagnostics library
   * wants a `CheckResult`, so return `CheckSucceeded` or `CheckFailed`
   * depending on the outcome of that `IO` value.
   */
  def check(): IO[CheckResult] =
    diagnostic.attempt.map {
      case Right(_) => CheckSucceeded()
      case Left(e) => CheckFailed("postgresql diagnostic failed", Some(e))
    }

}

object PostgresqlRepositoryCheck {

  def apply(diagnostic: IO[Unit]): DiagnosticCheckDefinition[IO] = {
    val postgresqlRepositoryCheck = new PostgresqlRepositoryCheck(diagnostic)


//    DiagnosticCheckDefinition("postgtresql", Duration(1, SECONDS), postgresqlRepositoryCheck.check)
    DiagnosticCheckDefinition(name = "postgtresql", check = () => postgresqlRepositoryCheck.check())
    /*
     * We need to construct a `DiagnosticCheckDefinition` that uses `postgresqlRepositoryCheck`.
     *
     * Hint: `io.unsafeToFuture()` will give you the `Future` rea-scala-diagnostics wants.
     */
  }

}

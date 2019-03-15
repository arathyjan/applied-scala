package com.reagroup.appliedscala.urls.diagnostics

import java.util.concurrent.TimeUnit.SECONDS
import scala.concurrent.duration.Duration
import cats.effect.IO
import com.reagroup.api.infrastructure.diagnostics.Diagnostic._

class PostgresqlRepositoryCheck(diagnostic: () => IO[Unit]) {

  /*
   * Calling `diagnostic` returns `IO[Unit]`. The rea-scala-diagnostics library
   * wants a `CheckResult`, so return `CheckSucceeded` or `CheckFailed`
   * depending on the outcome of that `IO` value.
   */
  def check(): IO[CheckResult] =
    ???

}

object PostgresqlRepositoryCheck {

  def apply(diagnostic: () => IO[Unit]): DiagnosticCheckDefinition = {
    val postgresqlRepositoryCheck = new PostgresqlRepositoryCheck(diagnostic)

    /*
     * We need to construct a `DiagnosticCheckDefinition` that uses `postgresqlRepositoryCheck`.
     *
     * Hint: `task.unsafeToFuture()` will give you the `Future` rea-scala-diagnostics wants.
     */
    ???
  }

}

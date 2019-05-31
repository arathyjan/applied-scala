package com.reagroup.api.infrastructure.diagnostics.http4s

import cats.effect.{ContextShift, IO, Timer}

import com.reagroup.api.infrastructure.diagnostics.{CompletedDiagnostic, Diagnostic, DiagnosticCheckDefinition, DiagnosticConfig}
import org.http4s.Response
import org.http4s.dsl.Http4sDsl

class DiagnosticController(
  diagnosticConfig: DiagnosticConfig[IO]
)(implicit contextShift: ContextShift[IO], timer: Timer[IO]) extends Http4sDsl[IO] {
  private val heartbeatChecks = diagnosticConfig.heartbeatChecks
  private val diagnosticChecks = diagnosticConfig.heartbeatChecks ++ diagnosticConfig.diagnosticChecks

  def getHeartBeat: IO[Response[IO]] = {
    val checkResult: IO[CompletedDiagnostic] = new Diagnostic[IO, IO.Par]().executeChecks(heartbeatChecks)

    checkResult.flatMap { completedCheck =>
      if (completedCheck.successful) {
        Ok("OK")
      } else {
        InternalServerError(completedCheck.asText(diagnosticConfig.host))
      }
    }
  }

  def getVersion: IO[Response[IO]] = Ok(diagnosticConfig.version)

  def getHost: IO[Response[IO]] = Ok(diagnosticConfig.host)

  def getNagios: IO[Response[IO]] =
    uncachedCheck(diagnosticChecks, _.asText(diagnosticConfig.host))

  // TODO json content type
  def getDiagnosis: IO[Response[IO]] =
    uncachedCheck(diagnosticChecks, _.asJson(diagnosticConfig.host))

  def executeChecks(checks: Vector[DiagnosticCheckDefinition[IO]]): IO[Response[IO]] =
    uncachedCheck(checks, _.asJson(diagnosticConfig.host))

  private def uncachedCheck(checks: Vector[DiagnosticCheckDefinition[IO]], outputter: CompletedDiagnostic => String): IO[Response[IO]] = {
    val checkResult: IO[CompletedDiagnostic] = new Diagnostic[IO, IO.Par]().executeChecks(checks)

    checkResult.flatMap { completedCheck =>
      val output = outputter(completedCheck)

      if (completedCheck.successful) {
        Ok(output)
      } else {
        InternalServerError(output)
      }
    }
  }

}

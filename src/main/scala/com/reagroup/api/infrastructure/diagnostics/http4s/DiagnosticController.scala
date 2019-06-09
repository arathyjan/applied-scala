package com.reagroup.api.infrastructure.diagnostics.http4s

import cats.Monad
import cats.effect.IO
import io.circe.syntax._

import com.reagroup.api.infrastructure.diagnostics.{CompletedDiagnostic, Diagnostic, DiagnosticCheckDefinition, DiagnosticConfig}
import org.http4s.{EntityEncoder, MediaType, Response}
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.headers.`Content-Type`

/*
 * There's more code in this controller than the others in this course,
 * but only because it supports 6 different endpoints.
 */
class DiagnosticController(
  diagnosticExecutor: Diagnostic[IO, IO.Par],
  diagnosticConfig: DiagnosticConfig[IO]
) extends Http4sDsl[IO] {
  private val heartbeatChecks = diagnosticConfig.heartbeatChecks
  private val diagnosticChecks = diagnosticConfig.heartbeatChecks ++ diagnosticConfig.diagnosticChecks

  /*
   * This method responds with a document containing links to all the other endpoints supported by this controller.
   * The links themselves are supplied by the DiagnosticRoutes class.
   */
  def getLinks(diagnosticLinks: Vector[DiagnosticLink]): IO[Response[IO]] = Ok(diagnosticLinks.asJson)

  /*
   * The next two methods each return a simple piece of information retrieved from DiagnosticConfig.
   */

  def getVersion: IO[Response[IO]] = Ok(diagnosticConfig.version)

  def getHost: IO[Response[IO]] = Ok(diagnosticConfig.host)

  /*
   * The next three methods are the ones which execute diagnostic checks and emit responses based on their results.
   * All of them delegate to the runChecks method below. There are two ways they differ from each other:
   * which checks they run, and how the response is rendered.
   */

  /*
   * getHeartbeat only executes the heartbeat checks, returning a plain text response.
   * On success, the response simply contains "OK", otherwise it contains details of the failed checks.
   */
  def getHeartbeat: IO[Response[IO]] = runChecks(heartbeatChecks, heartbeatCheckEncoder)

  /*
   * getNagios runs all checks and returns a plain text response.
   */
  def getNagios: IO[Response[IO]] = runChecks(diagnosticChecks, textCheckEncoder)

  /*
   * getDiagnosis runs all checks and returns a JSON response.
   */
  def getDiagnosis: IO[Response[IO]] = runChecks(diagnosticChecks, jsonCheckEncoder)

  /*
   * This method does most of the work common to the three check endpoints.
   *
   * It runs the checks, producing checkResult. It then examines whether checkResult reports success
   * or failure, and decides on a 200 or 500 status accordingly.
   *
   * In http4s, an EntityEncoder is used to turn an object into a response, by producing the response body
   * and any appropriate headers (typically the Content-Type header). Each of the methods calling runChecks provides
   * one of the EntityEncoder instances defined below to produce the appropriate response.
   */
  private def runChecks(
    checks: Vector[DiagnosticCheckDefinition[IO]],
    encoder: EntityEncoder[IO, CompletedDiagnostic]
  ): IO[Response[IO]] = {
    val checkResult: IO[CompletedDiagnostic] = diagnosticExecutor.executeChecks(checks)

    checkResult.flatMap { completedCheck =>
      if (completedCheck.successful) {
        Ok(completedCheck)(Monad[IO], encoder)
      } else {
        InternalServerError(completedCheck)(Monad[IO], encoder)
      }
    }
  }

  /*
   * There are three EntityEncoder instances below, one for each of the diagnostic check endpoints.
   *
   * All of them are derived from the implementation of EntityEncoder for String which http4s itself provides.
   *
   * They use EntityEncoder's contraMap method, providing a function which renders a CompletedDiagnostic as a String.
   * The function is used to turn the EntityEncoder[IO, String] into an EntityEncoder[IO, CompletedDiagnostic],
   * which is what the runChecks method above requires.
   */

  private def textCheckEncoder: EntityEncoder[IO, CompletedDiagnostic] = {
    EntityEncoder[IO, String]
      .contramap[CompletedDiagnostic](_.asText(diagnosticConfig.host))
  }

  private def jsonCheckEncoder: EntityEncoder[IO, CompletedDiagnostic] = {
    EntityEncoder[IO, String]
      .contramap[CompletedDiagnostic](_.asJson(diagnosticConfig.host))
      .withContentType(`Content-Type`(MediaType.application.json))
  }

  private def heartbeatCheckEncoder: EntityEncoder[IO, CompletedDiagnostic] = {
    EntityEncoder[IO, String]
      .contramap(completed => if (completed.successful) {
        "OK"
      } else {
        completed.asText(diagnosticConfig.host)
      })
  }
}

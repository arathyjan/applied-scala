package com.reagroup.api.infrastructure.diagnostics.http4s

import cats.effect.IO

import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

/*
 * Just like in AppRoutes, here we apply pattern matching to the request object to decide
 * what to do, then invoke a method from DiagnosticController to respond to that request.
 */
class DiagnosticRoutes(
  controller: DiagnosticController
) extends Http4sDsl[IO] {
  def routes: HttpRoutes[IO] = HttpRoutes.of {
    case GET -> Root / "diagnostic"
       | GET -> Root / "diagnostic" / "" => controller.getLinks(diagnosticLinks)
    case GET -> Root / "diagnostic" / "status" / "heartbeat" => controller.getHeartbeat
    case GET -> Root / "diagnostic" / "status" / "nagios" => controller.getNagios
    case GET -> Root / "diagnostic" / "status" / "diagnosis" => controller.getDiagnosis
    case GET -> Root / "diagnostic" / "host" => controller.getHost
    case GET -> Root / "diagnostic" / "version" => controller.getVersion
  }

  private val diagnosticLinks: Vector[DiagnosticLink] = Vector(
    DiagnosticLink("heartbeat", "/diagnostic/status/heartbeat"),
    DiagnosticLink("nagios", "/diagnostic/status/nagios"),
    DiagnosticLink("diagnosis", "/diagnostic/status/diagnosis"),
    DiagnosticLink("host", "/diagnostic/host"),
    DiagnosticLink("version", "/diagnostic/version")
  )
}

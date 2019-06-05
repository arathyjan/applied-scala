package com.reagroup.api.infrastructure.diagnostics.http4s

import cats.effect.{ContextShift, IO, Timer}

import com.reagroup.api.infrastructure.diagnostics.DiagnosticCheckDefinition
import com.reagroup.api.infrastructure.diagnostics.DiagnosticConfig
import io.circe.Json.{fromString, obj}
import io.circe._
import io.circe.syntax._
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

class DiagnosticRoutes(
  diagnosticConfig: DiagnosticConfig[IO],
  additionalDiagnosticRoutes: Vector[DiagnosticLink],
  additionalDiagnosticChecks: Map[DiagnosticLink, Vector[DiagnosticCheckDefinition[IO]]]
)(implicit contextShift: ContextShift[IO], timer: Timer[IO]) extends Http4sDsl[IO] {
  private val additionalDiagnosticCheckUrls = additionalDiagnosticChecks.map { case (link, checks) =>
    ("/diagnostic/" + link.url, checks)
  }

  private val controller: DiagnosticController = new DiagnosticController(diagnosticConfig)

  val diagnosticLinks: Seq[DiagnosticLink] = Seq(
    DiagnosticLink("heartbeat", "status/heartbeat"),
    DiagnosticLink("nagios", "status/nagios"),
    DiagnosticLink("diagnosis", "status/diagnosis"),
    DiagnosticLink("host", "host"),
    DiagnosticLink("version", "version")
  )

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def routes(prefix: String): HttpRoutes[IO] = HttpRoutes.of {
    case req if req.pathInfo == s"$prefix/status/heartbeat" => controller.getHeartBeat
    case req if req.pathInfo == s"$prefix/status/nagios" => controller.getNagios
    case req if req.pathInfo == s"$prefix/status/diagnosis" => controller.getDiagnosis
    case req if req.pathInfo == s"$prefix/version" => controller.getVersion
    case req if req.pathInfo == s"$prefix/host" => controller.getHost
    case req if req.pathInfo == s"$prefix" || req.pathInfo == s"$prefix/" => getLinks(prefix)
    case req if additionalDiagnosticCheckUrls.contains(req.pathInfo) =>
      controller.executeChecks(additionalDiagnosticCheckUrls(req.pathInfo))
    case GET -> Root / "diagnostic" / "version" => Ok(diagnosticConfig.version)
  }

  private def getLinks(prefix: String) = {
    Ok(allPaths(prefix))
  }

  private def allPaths(prefix: String): Json = {
    val allLinks = additionalDiagnosticRoutes ++ diagnosticLinks ++ additionalDiagnosticChecks.keys

    allLinks.map(link => obj("path" -> fromString(s"$prefix/${link.url}"), "name" -> fromString(link.name))).toList.asJson
  }
}

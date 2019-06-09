package com.reagroup.api.infrastructure.diagnostics.http4s

import io.circe.Encoder
import io.circe.generic.semiauto._

final case class DiagnosticLink(name: String, path: String)

object DiagnosticLink {
  implicit val encoder: Encoder[DiagnosticLink] = deriveEncoder
}

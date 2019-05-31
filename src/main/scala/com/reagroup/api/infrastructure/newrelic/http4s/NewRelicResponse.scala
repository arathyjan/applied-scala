package com.reagroup.api.infrastructure.newrelic.http4s

import cats.effect.IO
import com.newrelic.api.agent.{ExtendedResponse, HeaderType}
import org.http4s.Response

@SuppressWarnings(Array("org.wartremover.warts.Null"))
class NewRelicResponse(response: Response[IO]) extends ExtendedResponse {
  def getContentLength: Long = response.contentLength.getOrElse(-1L)

  def getStatus: Int = response.status.code

  def getStatusMessage: String = response.status.reason

  def getContentType: String = response.contentType.map(_.value).orNull

  def getHeaderType: HeaderType = HeaderType.HTTP

  def setHeader(name: String, value: String): Unit = {
    println(s"NewRelic attempted to add header $name: $value")
  }
}

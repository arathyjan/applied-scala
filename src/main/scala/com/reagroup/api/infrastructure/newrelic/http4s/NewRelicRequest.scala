package com.reagroup.api.infrastructure.newrelic.http4s

import scala.collection.JavaConverters._

import cats.effect.IO
import com.newrelic.api.agent.{ExtendedRequest, HeaderType}
import org.http4s.Request
import org.http4s.headers.Cookie
import org.http4s.util.CaseInsensitiveString

@SuppressWarnings(Array("org.wartremover.warts.Null"))
class NewRelicRequest(request: Request[IO]) extends ExtendedRequest {
  def getMethod: String = request.method.name

  def getRequestURI: String = request.uri.path

  def getRemoteUser: String = request.remoteUser.orNull

  def getParameterNames: java.util.Enumeration[_] = request.params.keysIterator.asJavaEnumeration

  def getParameterValues(name: String): Array[String] = request.multiParams.get(name).map(_.toArray).orNull

  def getAttribute(name: String): AnyRef = null

  def getCookieValue(name: String): String = request.headers.get(Cookie).map(_.value).orNull

  def getHeaderType: HeaderType = HeaderType.HTTP

  def getHeader(name: String): String = request.headers.get(CaseInsensitiveString(name)).map(_.value).orNull
}

package com.reagroup.api.infrastructure.logging.http4s

import cats.effect.IO
import com.reagroup.api.infrastructure.logging.api.access.TransactionId.xTransactionIdHeaderName
import com.reagroup.api.infrastructure.logging.api.access.{AccessLogInfo, AccessLogRequestInfo, ReaAccessLogFormatter, TransactionId}
import org.http4s.util.CaseInsensitiveString
import org.http4s.{Header, HeaderKey, HttpApp, MessageFailure, Request, Response, Status}
import org.slf4j.{LoggerFactory, MDC}

object LoggingMiddleware {
  private val accessLog = LoggerFactory.getLogger("access")
  def apply(service: HttpApp[IO]): HttpApp[IO] = cats.data.Kleisli { req: Request[IO] =>
    val startTime = System.currentTimeMillis()

    val reqWithTransactionId = ensureTransactionIdOnRequest(req)

    service(reqWithTransactionId).attempt.flatMap {
      case Left(e: MessageFailure) =>
        e
          .toHttpResponse[IO](req.httpVersion)
          .map(response => logRequestResponseDetails(startTime, reqWithTransactionId, response))
          .flatMap(_ => IO.raiseError(e))
      case Left(e) =>
        logRequestResponseDetails(startTime, reqWithTransactionId, new Response[IO](Status.InternalServerError))
        IO.raiseError(e)
      case Right(response) =>
        logRequestResponseDetails(startTime, reqWithTransactionId, response)
        IO(response)
    }
  }

  private def ensureTransactionIdOnRequest(req: Request[IO]): Request[IO] = {
    val maybeTransactionId = req.headers.get(CaseInsensitiveString(xTransactionIdHeaderName)).map(_.value)

    val transactionId = TransactionId.generateOrSanitisedTransactionId(maybeTransactionId)

    // side effect
    MDC.put("transaction_id", transactionId)
    req.withHeaders(req.headers.put(Header(xTransactionIdHeaderName, transactionId)))
  }

  private def logRequestResponseDetails(startTime: Long, req: Request[IO], resp: Response[IO]): Unit = {
    val headers = req.headers

    def header(name: String): Option[String] = headers.get(CaseInsensitiveString(name)).map(_.value)
    def headerValue(header: HeaderKey): Option[String] = headers.get(header.name).map(_.value)

    val accessLogRequestInfo = AccessLogRequestInfo(
      startTime = startTime,
      remoteAddress = req.remote.map(_.toString),
      method = req.method.name,
      uri = req.uri.toString(),
      httpVersion = req.httpVersion.renderString,
      referer = headerValue(org.http4s.headers.Referer),
      userAgent = headerValue(org.http4s.headers.`User-Agent`),
      host = headerValue(org.http4s.headers.Host),
      xForwardedFor = header("X-Forwarded-For"),
      transactionId = header(xTransactionIdHeaderName),
      userIdentifier = req.remoteUser
    )

    val accessLogInfo = AccessLogInfo(
      request = accessLogRequestInfo,
      statusCode = resp.status.code,
      contentLength = resp.contentLength,
      responseTime = System.currentTimeMillis() - startTime
    )

    // side effect
    accessLog.info(ReaAccessLogFormatter.format(accessLogInfo))
  }
}

package com.reagroup.api.infrastructure.newrelic.http4s

import cats.data.{Kleisli, OptionT}
import cats.effect.{IO, Sync}
import com.newrelic.api.agent.{NewRelic, Segment, Token, Trace}
import fs2.Stream
import org.http4s.{EntityBody, HttpRoutes, Request, Response}

@SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
object NewRelicMiddleware {
  type OptionTIO[A] = OptionT[IO, A]

  def routes(f: PartialFunction[Request[IO], IO[Response[IO]]]): HttpRoutes[IO] = {
    apply(Kleisli(req => OptionT.fromOption(f.lift(req))))
  }

  def apply(routes: Kleisli[OptionTIO, Request[IO], IO[Response[IO]]]): HttpRoutes[IO] = {
    Kleisli(doRequest(routes))
  }

  private def doRequest(routes: Kleisli[OptionTIO, Request[IO], IO[Response[IO]]])(req: Request[IO]): OptionTIO[Response[IO]] = {
    for {
      transactionToken <- OptionT.liftF(IO(startTransaction(req)))
      response <- OptionT(routes(req).value.handleErrorWith(handleFailure(transactionToken)))
      instrumentedResponse <- OptionT.liftF(instrumentRequest(transactionToken, req.uri.path, response))
    } yield instrumentedResponse
  }

  private def instrumentRequest(token: Token, requestName: String, response: IO[Response[IO]]): IO[Response[IO]] = {
    Sync[IO].bracket(IO(startSegment(token, "ProcessRequest", requestName)))(_ => instrumentResponse(token, requestName, response))(endSegment)
  }

  private def instrumentResponse(token: Token, requestName: String, ioResponse: IO[Response[IO]]): IO[Response[IO]] = {
    for {
      response <- ioResponse.handleErrorWith(handleFailure(token))
      instrumentedResponse = response.withBodyStream(instrumentBody(token, requestName, response.body))
      _ <- IO(setWebResponse(token, instrumentedResponse))
    } yield instrumentedResponse
  }

  private def instrumentBody(token: Token, requestName: String, body: EntityBody[IO]): EntityBody[IO] = {
    Stream.bracket(IO(startSegment(token, "ResponseBody", requestName)))(endSegment)
      .flatMap(_ => body)
      .handleErrorWith(handleFailureInStream(token))
  }

  @Trace(dispatcher = true)
  private def startTransaction(request: Request[IO]): Token = {
    val transaction = NewRelic.getAgent.getTransaction
    transaction.setWebRequest(new NewRelicRequest(request))
    transaction.getToken
  }

  @Trace(async = true)
  private def startSegment(token: Token, category: String, requestName: String): Segment = {
    token.link()
    NewRelic.getAgent.getTransaction.startSegment(category, requestName)
  }

  @Trace(async = true)
  private def setWebResponse(token: Token, response: Response[IO]): Unit = {
    token.link()
    NewRelic.getAgent.getTransaction.setWebResponse(new NewRelicResponse(response))
  }

  private def endSegment(segment: Segment): IO[Unit] = IO {
    segment.end()
  }

  private def handleFailure[A](token: Token)(e: Throwable): IO[A] = {
    IO {
      token.link()
      NewRelic.noticeError(e)
    }.flatMap(_ => IO.raiseError(e))
  }

  private def handleFailureInStream[A](token: Token)(e: Throwable): Stream[IO, A] = {
    Stream.eval_(handleFailure(token)(e))
  }
}

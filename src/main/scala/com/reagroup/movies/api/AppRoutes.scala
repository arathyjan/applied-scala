package com.reagroup.movies.api

import cats.effect._
import com.reagroup.movies.api.models._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpService, Response}

class AppRoutes(handler: AppRequest => IO[AppResponse]) extends Http4sDsl[IO] {

  private val handleAppReq: IO[AppRequest] => IO[Response[IO]] = ioAppReq =>
    for {
      appReq <- ioAppReq
      appResp <- handler(appReq)
      http4sResp <- toHttp4sResponse(appResp)
    } yield http4sResp

  val openRoutes = HttpService[IO] {
    AppRequest.fromHttp4s andThen handleAppReq
  }

  private def toHttp4sResponse(appResponse: AppResponse): IO[Response[IO]] =
    appResponse match {
      case MovieResp(Some(movie)) => Ok(movie.asJson)
      case NewMovieResp(id) => Ok(id.asJson)
      case MovieResp(None) => NotFound()
      case ErrorResp(RouteNotFoundErr, _) => NotFound()
      case ErrorResp(InvalidRequestErr, _) => BadRequest()
      case ErrorResp(InternalServerErr, _) => InternalServerError()
    }

}
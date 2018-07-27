package com.reagroup.movies.api

import cats.effect._
import com.reagroup.movies.api.models._
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpService, Response}
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._

class AppRoutes(handler: AppRequest => IO[AppResponse]) extends Http4sDsl[IO] {

  val openRoutes: HttpService[IO] = HttpService[IO] {
    case req =>
      for {
        appReq <- AppRequest.fromHttp4s(req)
        appResp <- handler(appReq)
        http4sResp <- toHttp4sResponse(appResp)
      } yield http4sResp
  }

  private def toHttp4sResponse(appResponse: AppResponse): IO[Response[IO]] =
    appResponse match {
      case GetMovieResp(Some(movie)) => Ok(movie.asJson)
      case PostMovieResp(id) => Ok(id.asJson)
      case GetMovieResp(None) => NotFound()
      case ErrorResp(RouteNotFoundErr, _) => NotFound()
      case ErrorResp(InvalidRequestErr, _) => BadRequest()
      case ErrorResp(InternalServerErr, _) => InternalServerError()
    }

}
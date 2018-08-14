package com.reagroup.movies.api

import cats.effect._
import com.reagroup.movies.api.models._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpService, Response}

class AppRoutes(handler: AppRequest => IO[AppResponse]) extends Http4sDsl[IO] {

  val openRoutes = HttpService[IO] {
    case req => for {
      appReq <- AppRequest.fromHttp4s(req)
      appResp <- handler(appReq)
      http4sResp <- toHttp4sResponse(appResp)
    } yield http4sResp
  }

  private def toHttp4sResponse(appResponse: AppResponse): IO[Response[IO]] =
    appResponse match {
      case MovieResp(Some(movie)) => Ok(movie.asJson)
      case NewMovieResp(id) => Ok(id.asJson)
      case MovieResp(None) => NotFound()
      case ErrorResp(err) => err match {
        case UnknownRouteError => NotFound()
        case InvalidMovieCreationError(_) => InternalServerError(err.asJson)
        case ServerError(_) => InternalServerError(err.asJson)
      }
    }

}
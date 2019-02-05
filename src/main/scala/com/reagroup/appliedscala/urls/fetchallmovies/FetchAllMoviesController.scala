package com.reagroup.appliedscala.urls.fetchallmovies

import cats.effect.IO
import com.reagroup.appliedscala.urls.ErrorHandler
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class FetchAllMoviesController(service: FetchAllMoviesService) extends Http4sDsl[IO] {

  def apply(): IO[Response[IO]] = for {
    errorOrMovies <- service.fetchAllMovies().attempt
    resp <- errorOrMovies match {
      case Right(movies) => Ok(movies.asJson)
      case Left(e) => ErrorHandler(e)
    }
  } yield resp

}

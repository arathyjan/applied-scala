package com.reagroup.appliedscala.urls.fetchallmovies

import cats.effect.IO
import com.reagroup.appliedscala.models.Movie
import com.reagroup.appliedscala.urls.ErrorHandler
import io.circe.Json
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class FetchAllMoviesController(fetchAll: IO[Vector[Movie]]) extends Http4sDsl[IO] {

  def apply(): IO[Response[IO]] = for {
    errorOrMovies <- fetchAll.attempt
    resp <- errorOrMovies match {
      case Right(movies) => Ok(movies.map(movieToJson))
      case Left(e) => ErrorHandler(e)
    }
  } yield resp

  /**
    * The reason we aren't using an `Encoder` instance for this conversion here is because
    * we want you to write your own `Encoder` instance for the `GET movie/id` endpoint.
    * Don't want to giveaway the answer :)
    */
  def movieToJson(movie: Movie): Json =
    Json.obj("name" -> movie.name.asJson)

}

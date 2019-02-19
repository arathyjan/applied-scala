package com.reagroup.appliedscala.urls.savemovie

import cats.data.Validated._
import cats.data.ValidatedNel
import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.models.errors.InvalidNewMovieErr
import com.reagroup.appliedscala.urls.ErrorHandler
import io.circe.Json
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class SaveMovieController(saveNewMovie: NewMovieRequest => IO[ValidatedNel[InvalidNewMovieErr, MovieId]]) extends Http4sDsl[IO] {

  def apply(req: Request[IO]): IO[Response[IO]] = ???

}

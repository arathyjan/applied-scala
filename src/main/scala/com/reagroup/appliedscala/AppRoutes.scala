package com.reagroup.appliedscala

import cats.effect._
import com.reagroup.appliedscala.urls.fetchallmovies.FetchAllMoviesController
import com.reagroup.appliedscala.urls.fetchenrichedmovie.FetchEnrichedMovieController
import com.reagroup.appliedscala.urls.fetchmovie.FetchMovieController
import com.reagroup.appliedscala.urls.savemovie.SaveMovieController
import com.reagroup.appliedscala.urls.savereview.SaveReviewController
import io.circe.syntax._
import org.http4s._
import io.circe.Json

import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

class AppRoutes(fetchAllMovies: FetchAllMoviesController,
                fetchMovie: FetchMovieController,
                fetchEnrichedMovie: FetchEnrichedMovieController,
                saveMovie: SaveMovieController,
                saveReview: SaveReviewController) extends Http4sDsl[IO] {

  object OptionalBooleanMatcher extends OptionalQueryParamDecoderMatcher[Boolean]("enriched")

  val openRoutes = HttpService[IO] {
    case GET -> Root / "hello" => Ok("Hello world")
    case GET -> Root / "movies" => fetchAllMovies()
    case GET -> Root / "movies" / LongVar(id) :? OptionalBooleanMatcher(optEnriched) => if (optEnriched.contains(true)) fetchEnrichedMovie(id) else fetchMovie(id)
    case req @ POST -> Root / "movies" => saveMovie(req)
    case req @ POST -> Root / "movies" / LongVar(id) / "reviews" => saveReview(id, req)
  }

}
package com.reagroup.appliedscala

import cats.effect._
import com.reagroup.appliedscala.urls.fetchmovie.FetchMovieController
import com.reagroup.appliedscala.urls.savemovie.SaveMovieController
import com.reagroup.appliedscala.urls.savereview.SaveReviewController
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

class AppRoutes(fetchMovie: FetchMovieController, saveMovie: SaveMovieController, saveReview: SaveReviewController) extends Http4sDsl[IO] {

  val openRoutes = HttpService[IO] {
    case GET -> Root / "movies" / LongVar(id) => fetchMovie(id)
    case req@POST -> Root / "movies" => saveMovie(req)
    case req@POST -> Root / "movies" / LongVar(id) / "review" => saveReview(id, req)
  }

}
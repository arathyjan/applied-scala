package com.reagroup.listings.listingpublisher.api

import cats.effect._
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpService, Response}
import org.lyranthe.http4s.timer._
import org.lyranthe.http4s.timer.newrelic._

class AppRoutes(getMovie: String => IO[Response[IO]]) extends Http4sDsl[IO] {

  val openRoutes: HttpService[IO] = TimedService[IO]("listing-publisher-routes") {
    case GET -> Root / "movies" / id  => "v1/combined-listings/:id" -> getMovie(id)
  }

}
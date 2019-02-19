package com.reagroup.appliedscala.urls.fetchenrichedmovie

import java.net.URLEncoder

import cats.effect.IO
import com.reagroup.appliedscala.models._
import io.circe.parser.decode
import org.http4s.client.Client
import org.http4s.client.blaze._

class Http4sStarRatingsRepository {

  val httpClient: Client[IO] = Http1Client[IO]().unsafeRunSync

  def apply(movieName: String): IO[Option[StarRating]] = ???

}

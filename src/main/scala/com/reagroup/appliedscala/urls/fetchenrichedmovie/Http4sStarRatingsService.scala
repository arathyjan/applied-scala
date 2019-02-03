package com.reagroup.appliedscala.urls.fetchenrichedmovie

import java.net.URLEncoder

import cats.effect.IO
import com.reagroup.appliedscala.models._
import io.circe.parser.decode
import org.http4s.client.Client
import org.http4s.client.blaze._

class Http4sStarRatingsService extends StarRatingsRepository {

  val httpClient: Client[IO] = Http1Client[IO]().unsafeRunSync

  def fetchStarRating(movieName: String): IO[Option[StarRating]] = {
    val movieToSearch = URLEncoder.encode(movieName, "UTF-8")
    val str: IO[String] = httpClient.expect[String](s"http://www.omdbapi.com/?t=$movieToSearch&apikey=7f9b5b06")
    str.map(s => decode[StarRating](s).toOption)
  }

}

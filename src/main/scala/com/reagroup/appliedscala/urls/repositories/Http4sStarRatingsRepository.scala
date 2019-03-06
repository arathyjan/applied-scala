package com.reagroup.appliedscala.urls.repositories

import java.net.URLEncoder

import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.urls.fetchenrichedmovie.StarRating
import io.circe.parser.decode
import org.http4s.client.Client
import org.http4s.client.blaze._

class Http4sStarRatingsRepository {

  // TODO why unsafeRunSync
  val httpClient: Client[IO] = Http1Client[IO]().unsafeRunSync

  /**
    * For the purpose of this exercise, we return a `None` if we are unable to decode a `StarRating` out of the response from OMDB.
    */
  def apply(movieName: String): IO[Option[StarRating]] = {
    val movieToSearch = URLEncoder.encode(movieName, "UTF-8")
    val str: IO[String] = httpClient.expect[String](s"http://www.omdbapi.com/?t=$movieToSearch&apikey=7f9b5b06")
    ???
  }

}

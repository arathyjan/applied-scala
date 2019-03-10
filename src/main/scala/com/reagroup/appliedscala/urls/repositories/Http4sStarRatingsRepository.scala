package com.reagroup.appliedscala.urls.repositories

import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.urls.fetchenrichedmovie.StarRating
import io.circe.parser.decode
import org.http4s.Uri
import org.http4s.client.Client
import org.http4s.client.blaze._

class Http4sStarRatingsRepository(httpClient: Client[IO], apiKey: String) {

  /**
    * For the purpose of this exercise, we return a `None` if we are unable to decode a `StarRating` out of the response from OMDB.
    */
  def apply(movieName: String): IO[Option[StarRating]] = {
    val uri = Uri.uri("http://www.omdbapi.com/")
      .withQueryParam("apikey", apiKey)
      .withQueryParam("s", movieName)
    val str: IO[String] = httpClient.expect[String](uri)
    ???
  }

}

object Http4sStarRatingsRepository {

  def apply(apiKey: String): Http4sStarRatingsRepository = {
    // TODO why unsafeRunSync
    val httpClient: Client[IO] = Http1Client[IO]().unsafeRunSync

    new Http4sStarRatingsRepository(httpClient, apiKey)
  }

}

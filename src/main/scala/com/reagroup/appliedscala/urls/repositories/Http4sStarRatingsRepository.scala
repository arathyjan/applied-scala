package com.reagroup.appliedscala.urls.repositories

import cats.effect.IO
import com.reagroup.appliedscala.urls.fetchenrichedmovie.StarRating
import io.circe.parser.decode
import org.http4s.Uri
import org.http4s.client.Client

class Http4sStarRatingsRepository(httpClient: Client[IO], apiKey: String) {

  /**
    * For the purpose of this exercise, we return a `None` if we are unable to decode a `StarRating` out of the response from OMDB.
    */
  def apply(movieName: String): IO[Option[StarRating]] = {
    val uri = Uri.uri("http://www.omdbapi.com/")
      .withQueryParam("apikey", apiKey)
      .withQueryParam("t", movieName)
    val str: IO[String] = httpClient.expect[String](uri)
//    val errorOrStarRating: IO[Either[circe.Error, StarRating]] = str.map(decode[StarRating])
//    val mayBeStarRating: IO[Option[StarRating]] = errorOrStarRating.map(errorOrStarRating => errorOrStarRating.toOption)
//    mayBeStarRating
//    toOption convert Either to Option by removing the error to None

    str.map(decode[StarRating]).map(errorOrStarRating => errorOrStarRating.toOption)

  }

}

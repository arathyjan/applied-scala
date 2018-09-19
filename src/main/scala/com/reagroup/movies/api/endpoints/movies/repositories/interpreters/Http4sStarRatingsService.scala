package com.reagroup.movies.api.endpoints.movies.repositories.interpreters

import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.repositories.effects.StarRatingsRepository
import com.reagroup.movies.api.models._
import org.http4s.client.Client
import org.http4s.client.blaze._

class Http4sStarRatingsService extends StarRatingsRepository {

  val httpClient: Client[IO] = Http1Client[IO]().unsafeRunSync

  def getStarRating(movieName: String): IO[Option[StarRating]] = {
//    httpClient.expect[String]("http://localhost:8080/ratings")
    IO.pure(Some(Five))
  }

}

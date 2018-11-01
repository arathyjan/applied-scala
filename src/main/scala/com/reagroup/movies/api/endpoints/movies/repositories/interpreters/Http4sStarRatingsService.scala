package com.reagroup.movies.api.endpoints.movies.repositories.interpreters

import java.net.URLEncoder

import cats.effect.IO
import com.reagroup.movies.api.endpoints.movies.repositories.effects.StarRatingsRepository
import com.reagroup.movies.api.models._
import org.http4s.client.Client
import org.http4s.client.blaze._
import io.circe.parser.decode

class Http4sStarRatingsService extends StarRatingsRepository {

  val httpClient: Client[IO] = Http1Client[IO]().unsafeRunSync

  def getStarRating(movieName: String): IO[Option[StarRating]] = {
    val movieToSearch = URLEncoder.encode(movieName, "UTF-8")
    val str: IO[String] = httpClient.expect[String](s"http://www.omdbapi.com/?t=$movieToSearch&apikey=7f9b5b06")
    str.map(s => decode[StarRating](s) match {
      case Right(star) => Some(star)
      case Left(err) => None
    })
  }

}

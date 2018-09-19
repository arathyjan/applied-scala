package com.reagroup.movies.api.endpoints.movies.repositories.effects

import cats.effect.IO
import com.reagroup.movies.api.models.StarRating

trait StarRatingsRepository {

  def getStarRating(movieName: String): IO[Option[StarRating]]

}

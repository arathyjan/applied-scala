package com.reagroup.appliedscala.urls.fetchmovie

import cats.effect.IO
import com.reagroup.appliedscala.models.StarRating

trait StarRatingsRepository {

  def fetchStarRating(movieName: String): IO[Option[StarRating]]

}

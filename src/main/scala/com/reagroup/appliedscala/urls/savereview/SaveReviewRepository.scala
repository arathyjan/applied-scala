package com.reagroup.appliedscala.urls.savereview

import cats.effect.IO
import com.reagroup.appliedscala.models._

trait SaveReviewRepository {

  def apply(movieId: MovieId, review: ReviewToSave): IO[ReviewId]

}
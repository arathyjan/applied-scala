package com.reagroup.appliedscala.urls.savereview

import cats.data.ValidatedNel
import cats.effect.IO
import com.reagroup.appliedscala.models.errors.InvalidReviewErr
import com.reagroup.appliedscala.models.{MovieId, NewReviewRequest, ReviewId}

class SaveReviewService(repo: SaveReviewRepository) {

  def saveReview(movieId: MovieId, review: NewReviewRequest): IO[ValidatedNel[InvalidReviewErr, ReviewId]] =
    ReviewValidator.validate(review).traverse(repo.apply(movieId, _))

}
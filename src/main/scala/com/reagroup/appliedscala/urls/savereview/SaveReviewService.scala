package com.reagroup.appliedscala.urls.savereview

import cats.data.ValidatedNel
import cats.effect.IO
import com.reagroup.appliedscala.models.{MovieId, Review, ReviewId}

class SaveReviewService(repo: SaveReviewRepository) {

  def saveReview(movieId: MovieId, review: Review): IO[ValidatedNel[InvalidReviewErr, ReviewId]] =
    ReviewValidator.validate(review).traverse(repo.apply(movieId, _))

}

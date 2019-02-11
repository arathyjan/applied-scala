package com.reagroup.appliedscala.urls.savereview

import cats.data.ValidatedNel
import cats.effect.IO
import com.reagroup.appliedscala.models.errors.InvalidReviewErr
import com.reagroup.appliedscala.models.{MovieId, NewReviewRequest, ReviewId, ReviewToSave}

class SaveReviewService(saveReview: (MovieId, ReviewToSave) => IO[ReviewId]) {

  def save(movieId: MovieId, review: NewReviewRequest): IO[ValidatedNel[InvalidReviewErr, ReviewId]] =
    ReviewValidator.validate(review).traverse(saveReview.apply(movieId, _))

}

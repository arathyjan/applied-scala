package com.reagroup.appliedscala.urls.savereview

import cats.data.ValidatedNel
import cats.effect.IO
import com.reagroup.appliedscala.models.errors.InvalidReviewErr
import com.reagroup.appliedscala.models.{MovieId, NewReviewRequest, ReviewId, ReviewToSave}

class SaveReviewService(saveReview: (MovieId, ReviewToSave) => IO[ReviewId]) {

  /**
    * Before saving a `NewReviewRequest`, we want to validate the request in order to get a `ReviewToSave`.
    * Complete `NewMovieValidator`, then use it here before calling `saveMovie`.
    */
  def save(movieId: MovieId, review: NewReviewRequest): IO[ValidatedNel[InvalidReviewErr, ReviewId]] = ???

}

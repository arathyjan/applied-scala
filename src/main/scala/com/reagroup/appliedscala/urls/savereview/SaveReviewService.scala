package com.reagroup.appliedscala.urls.savereview

import cats.data.{NonEmptyList, Validated, ValidatedNel}
import cats.effect.IO
import com.reagroup.appliedscala.models.{Movie, MovieId}

class SaveReviewService(saveReview: (MovieId, ValidatedReview) => IO[ReviewId],
                        fetchMovie: MovieId => IO[Option[Movie]]) {

  /**
    * Before saving a `NewReviewRequest`, we want to check that the movie exists and then
    * validate the request in order to get a `ValidatedReview`.
    * Complete `NewReviewValidator`, then use it here before calling `saveReview`.
    *
    * You can convert `Option`s to `ValidatedNel` using `optionToValidatedNel`
    *
    */
  def save(movieId: MovieId, review: NewReviewRequest): IO[ValidatedNel[ReviewValidationError, ReviewId]] =
    ???

  private def optionToValidatedNel[A, B](o: Option[A], error: B): ValidatedNel[B, A] =
    Validated.fromOption(o, NonEmptyList.one(error))

}

package com.reagroup.appliedscala.urls.savereview

import cats.data.{NonEmptyList, Validated, ValidatedNel}
import cats.effect.IO
import cats.implicits._
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
  def save(movieId: MovieId, review: NewReviewRequest): IO[ValidatedNel[ReviewValidationError, ReviewId]] = {

    val validatedReview: ValidatedNel[ReviewValidationError, ValidatedReview] = NewReviewValidator.validate(review)

    val ioMayBeMovie: IO[Option[Movie]] = fetchMovie(movieId)
    val ioValidatedMovie: IO[ValidatedNel[ReviewValidationError, Movie]] =
      ioMayBeMovie.map(maybeMovie => optionToValidatedNel(maybeMovie, MovieDoesNotExist))

    val validatedReviewWithErrorsIO: IO[ValidatedNel[ReviewValidationError, ValidatedReview]] =
      ioValidatedMovie.map(validatedMovie => validatedMovie.productR(validatedReview))

//    val savedReviewResp: IO[Validated[NonEmptyList[ReviewValidationError], ReviewId]] =
//      validatedReviewWithErrorsIO
//        .flatMap(
//          validatedReview => validatedReview
//            .map(validReview =>
//              saveReview(movieId, validReview)
//            ).sequence
//        )

    val savedReviewResp: IO[Validated[NonEmptyList[ReviewValidationError], ReviewId]] =
      validatedReviewWithErrorsIO
        .flatMap(
          validatedReview => validatedReview
            .traverse(validReview =>
              saveReview(movieId, validReview)
            )
        )

    savedReviewResp
//
//    for {
//      mayBeMovie <- fetchMovie(movieId)
//      validatedMovie <- optionToValidatedNel(maybeMovie, MovieDoesNotExist)
//
//    } yield ???
  }


  private def optionToValidatedNel[A, B](o: Option[A], error: B): ValidatedNel[B, A] =
    Validated.fromOption(o, NonEmptyList.one(error))

}

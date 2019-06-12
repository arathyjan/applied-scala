package com.reagroup.appliedscala.urls.savereview

import cats.data.Validated._
import cats.data.ValidatedNel
import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.urls.ErrorHandler
import io.circe.Json
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class SaveReviewController(saveNewReview: (MovieId, NewReviewRequest) => IO[ValidatedNel[ReviewValidationError, ReviewId]]) extends Http4sDsl[IO] {

  def save(movieId: Long, req: Request[IO]): IO[Response[IO]] =
    for {
      newReviewReq <- req.as[NewReviewRequest]
      failureOrErrorOrReview <- saveNewReview(MovieId(movieId), newReviewReq).attempt
      resp <- failureOrErrorOrReview match {
        case Right(Valid(reviewId)) => Created(reviewId.asJson)
        case Right(Invalid(reviewValidationError)) => BadRequest(Json.obj("errors" -> reviewValidationError.asJson))
        case Left(e) => ErrorHandler(e)
      }
    } yield resp

}

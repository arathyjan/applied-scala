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

  def apply(movieId: Long, req: Request[IO]): IO[Response[IO]] =
    for {
      review <- req.as[NewReviewRequest]
      errsOrId <- saveNewReview(MovieId(movieId), review).attempt
      resp <- errsOrId match {
        case Right(Valid(reviewId)) => Created(reviewId.asJson)
        case Right(Invalid(errors)) => BadRequest(Json.obj("errors" -> errors.asJson))
        case Left(e) => ErrorHandler(e)
      }
    } yield resp

}

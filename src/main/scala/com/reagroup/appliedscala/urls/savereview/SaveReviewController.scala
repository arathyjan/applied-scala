package com.reagroup.appliedscala.urls.savereview

import cats.data.Validated._
import cats.effect.IO
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.urls.ErrorHandler
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

class SaveReviewController(service: SaveReviewService) extends Http4sDsl[IO] {

  def apply(movieId: Long, req: Request[IO]): IO[Response[IO]] =
    for {
      review <- req.as[NewReviewRequest]
      errsOrId <- service.save(MovieId(movieId), review).attempt
      resp <- errsOrId match {
        case Right(Valid(reviewId)) => Created(reviewId.asJson)
        case Right(Invalid(errors)) => BadRequest(errors.asJson)
        case Left(e) => ErrorHandler(e)
      }
    } yield resp

}

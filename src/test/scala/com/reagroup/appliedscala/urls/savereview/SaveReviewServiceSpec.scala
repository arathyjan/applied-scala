package com.reagroup.appliedscala.urls.savereview

import cats.data.NonEmptyList
import cats.effect.IO
import cats.implicits._
import com.reagroup.appliedscala.models._
import org.specs2.mutable.Specification

class SaveReviewServiceSpec extends Specification {

  "saveReview" should {

    "return errors" in {

      val repo = (movieId: MovieId, review: ValidatedReview) => IO.pure(ReviewId(12345))

      val service = new SaveReviewService(repo)

      val reviewToSave = NewReviewRequest("", "")

      val result = service.save(MovieId(12345), reviewToSave)

      result.unsafeRunSync() must_=== NonEmptyList.of(ReviewAuthorTooShort, ReviewCommentTooShort).invalid

    }

    "return saved reviewId" in {

      val repo = (movieId: MovieId, review: ValidatedReview) => IO.pure(ReviewId(12345))

      val service = new SaveReviewService(repo)

      val reviewToSave = NewReviewRequest("bob", "good movie")

      val result = service.save(MovieId(12345), reviewToSave)

      result.unsafeRunSync() must_=== ReviewId(12345).valid

    }

  }

}

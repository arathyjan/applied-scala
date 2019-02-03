package com.reagroup.appliedscala.urls.savereview

import cats.data.NonEmptyList
import cats.effect.IO
import cats.implicits._
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.models.errors.{AuthorTooShort, CommentTooShort}
import org.scalatest._

class SaveReviewServiceSpec extends FunSpec {

  describe("saveReview") {

    it("should return errors") {

      val repo = new SaveReviewRepository {
        override def apply(movieId: MovieId, review: ReviewToSave): IO[ReviewId] = ???
      }

      val service = new SaveReviewService(repo)

      val reviewToSave = NewReviewRequest("", "")

      val result = service.saveReview(MovieId(12345), reviewToSave)

      assert(result.unsafeRunSync() == NonEmptyList.of(AuthorTooShort, CommentTooShort).invalid)

    }

    it("should return saved reviewId") {

      val repo = new SaveReviewRepository {
        override def apply(movieId: MovieId, review: ReviewToSave): IO[ReviewId] = IO.pure(ReviewId(12345))
      }

      val service = new SaveReviewService(repo)

      val reviewToSave = NewReviewRequest("bob", "good movie")

      val result = service.saveReview(MovieId(12345), reviewToSave)

      assert(result.unsafeRunSync() == ReviewId(12345).valid)

    }

  }

}

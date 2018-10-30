package com.reagroup.movies.api.endpoints.movies.services.interpreters

import cats.data.Ior.Both
import cats.data.{NonEmptyList, NonEmptyVector}
import cats.effect.IO
import cats.implicits._
import com.reagroup.movies.api.endpoints.movies.repositories.effects.MoviesRepository
import com.reagroup.movies.api.endpoints.movies.services.{AuthorTooShort, CommentTooShort, MovieNameTooShort, SynopsisTooShort}
import com.reagroup.movies.api.models._
import org.scalatest._

class MoviesServiceSpec extends FunSpec {

  describe("save") {

    it("should return both errors") {

      val newMovieReq = NewMovie("", "")

      val repo = new MoviesRepository {
        override def getMovie(movieId: MovieId): IO[Option[Movie]] = ???

        override def saveMovie(movie: NewMovie): IO[MovieId] = IO.pure(MovieId(123))

        override def saveReviews(movieId: MovieId, reviews: NonEmptyVector[Review]): IO[NonEmptyVector[ReviewId]] = ???
      }

      val service = new MoviesService(repo, _ => ???)

      val actual = service.save(newMovieReq)

      assert(actual.unsafeRunSync() == NonEmptyList.of(MovieNameTooShort, SynopsisTooShort).invalid)

    }

  }

  describe("saveReviews") {

    it("should return errors AND review ids") {

      val expectedReviewIds = NonEmptyVector.of(ReviewId(1), ReviewId(2), ReviewId(3))

      val repo = new MoviesRepository {
        override def getMovie(movieId: MovieId): IO[Option[Movie]] = ???

        override def saveMovie(movie: NewMovie): IO[MovieId] = ???

        override def saveReviews(movieId: MovieId, reviews: NonEmptyVector[Review]): IO[NonEmptyVector[ReviewId]] = {
          IO.pure(expectedReviewIds)
        }
      }

      val service = new MoviesService(repo, _ => IO.pure(Some(Five)))

      val reviewsToSave = NonEmptyVector.of(Review("Author1", ""), Review("", "I liked it."), Review("Author3", "Great movie!"))

      val result = service.saveReviews(MovieId(12345), reviewsToSave)

      assert(result.unsafeRunSync() == Both(NonEmptyList.of(CommentTooShort, AuthorTooShort), expectedReviewIds))

    }

  }

}

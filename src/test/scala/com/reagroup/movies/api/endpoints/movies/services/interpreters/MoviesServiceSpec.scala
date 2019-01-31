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

        override def saveReview(movieId: MovieId, review: Review): IO[ReviewId] = ???
      }

      val service = new MoviesService(repo, _ => ???)

      val actual = service.save(newMovieReq)

      assert(actual.unsafeRunSync() == NonEmptyList.of(MovieNameTooShort, SynopsisTooShort).invalid)

    }

  }

  describe("saveReview") {

    it("should return errors") {

      val repo = new MoviesRepository {
        override def getMovie(movieId: MovieId): IO[Option[Movie]] = ???

        override def saveMovie(movie: NewMovie): IO[MovieId] = ???

        override def saveReview(movieId: MovieId, review: Review): IO[ReviewId] = ???
      }

      val service = new MoviesService(repo, _ => ???)

      val reviewToSave = Review("", "")

      val result = service.saveReview(MovieId(12345), reviewToSave)

      assert(result.unsafeRunSync() == NonEmptyList.of(AuthorTooShort, CommentTooShort).invalid)

    }

  }

}

package com.reagroup.appliedscala.urls.savemovie

import cats.data.NonEmptyList
import cats.effect.IO
import cats.implicits._
import com.reagroup.appliedscala.models._
import org.scalatest._

class SaveMovieServiceSpec extends FunSpec {

  describe("saveMovie") {

    it("should return both errors") {

      val newMovieReq = NewMovieRequest("", "")

      val repo = new SaveMovieRepository {
        override def apply(movie: MovieToSave): IO[MovieId] = ???
      }

      val service = new SaveMovieService(repo)

      val actual = service.save(newMovieReq)

      assert(actual.unsafeRunSync() == NonEmptyList.of(MovieNameTooShort, SynopsisTooShort).invalid)

    }

    it("should return saved movieId") {

      val newMovieReq = NewMovieRequest("badman returns", "nananana badman")

      val repo = new SaveMovieRepository {
        override def apply(movie: MovieToSave): IO[MovieId] = IO.pure(MovieId(123))
      }

      val service = new SaveMovieService(repo)

      val actual = service.save(newMovieReq)

      assert(actual.unsafeRunSync() == MovieId(123).valid)

    }

  }

}

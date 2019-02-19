package com.reagroup.appliedscala.urls.savemovie

import cats.data.NonEmptyList
import cats.effect.IO
import cats.implicits._
import com.reagroup.appliedscala.models._
import com.reagroup.appliedscala.models.errors.{MovieNameTooShort, SynopsisTooShort}
import org.specs2.mutable.Specification

class SaveMovieServiceSpec extends Specification {

  "saveMovie" should {

    "return both errors" in {

      val newMovieReq = NewMovieRequest("", "")

      val repo = (movie: MovieToSave) => ???

      val service = new SaveMovieService(repo)

      val actual = service.save(newMovieReq)

      actual.unsafeRunSync() == NonEmptyList.of(MovieNameTooShort, SynopsisTooShort).invalid

    }

    "return saved movieId" in {

      val newMovieReq = NewMovieRequest("badman returns", "nananana badman")

      val repo = (movie: MovieToSave) => IO.pure(MovieId(123))

      val service = new SaveMovieService(repo)

      val actual = service.save(newMovieReq)

      actual.unsafeRunSync() == MovieId(123).valid

    }

  }

}

package com.reagroup.appliedscala.urls.fetchallmovies

import cats.effect.IO
import com.reagroup.appliedscala.models._
import org.scalatest._

class FetchAllMoviesServiceSpec extends FunSpec {

  describe("fetchAllMovies") {

    it("should return all movies") {

      val movie1 = Movie("Batman Returns", "Bats are cool!", Vector.empty[Review])
      val movie2 = Movie("Titanic", "Can't sink this!", Vector.empty[Review])
      val allMovies = Vector(movie1, movie2)

      val repo = new FetchAllMoviesRepository {
        override def apply(): IO[Vector[Movie]] = {
          IO.pure(allMovies)
        }
      }

      val service = new FetchAllMoviesService(repo)

      val actual = service.fetchAllMovies()

      assert(actual.unsafeRunSync() == allMovies)

    }

  }

}

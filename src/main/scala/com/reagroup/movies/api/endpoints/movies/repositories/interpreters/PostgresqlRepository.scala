package com.reagroup.movies.api.endpoints.movies.repositories.interpreters

import cats.data.NonEmptyVector
import cats.effect.IO
import cats.implicits._
import doobie._
import doobie.implicits._
import org.postgresql.ds.PGSimpleDataSource
import com.reagroup.movies.api.endpoints.movies.repositories.effects.MoviesRepository
import com.reagroup.movies.api.models._

class PostgresqlRepository(transactor: Transactor[IO]) extends MoviesRepository {
  override def getMovie(movieId: MovieId): IO[Option[Movie]] = {
    case class MovieRow(name: String, synopsis: String, review: Option[Review])

    def toMovie(rows: Vector[MovieRow]): Option[Movie] = rows.headOption.map {
      case MovieRow(name, synopsis, _) => Movie(name, synopsis, rows.flatMap(_.review))
    }

    for {
      rows <- sql"""
        SELECT m.name, m.synopsis, r.author, r.comment
        FROM movie m
        LEFT OUTER JOIN review r ON r.movie_id = m.id
        WHERE m.id = ${movieId.value}
        ORDER BY m.id
      """.query[MovieRow].to[Vector].transact(transactor)
      movie <- IO(toMovie(rows))
    } yield movie
  }

  override def saveMovie(movie: MovieToSave): IO[MovieId] = {
    val insertMovie: ConnectionIO[MovieId] =
      for {
        movieId <- sql"""
                        INSERT INTO movie (name, synopsis) VALUES (${movie.name}, ${movie.synopsis})
                        RETURNING id
                      """.query[MovieId].unique
      } yield movieId

    insertMovie.transact(transactor)
  }

  override def saveReview(movieId: MovieId, review: ReviewToSave): IO[ReviewId] = ???
}

object PostgresqlRepository {
  def apply(env: Map[String, String]): MoviesRepository = {
    val ds = new PGSimpleDataSource()
    ds.setServerName(env("DATABASE_HOST"))
    ds.setUser(env("DATABASE_USERNAME"))
    ds.setPassword(env("DATABASE_PASSWORD"))
    ds.setDatabaseName(env("DATABASE_NAME"))
    val transactor = Transactor.fromDataSource[IO](ds)
    new PostgresqlRepository(transactor)
  }
}

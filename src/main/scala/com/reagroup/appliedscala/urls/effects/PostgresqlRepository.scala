package com.reagroup.appliedscala.urls.effects

import cats.effect.IO
import com.reagroup.appliedscala.models._
import doobie._
import doobie.implicits._
import org.postgresql.ds.PGSimpleDataSource

class PostgresqlRepository(transactor: Transactor[IO]) {

  case class MovieRow(name: String, synopsis: String, review: Option[Review])

  def fetchMovie(movieId: MovieId): IO[Option[Movie]] = {

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
    } yield toMovie(rows)
  }

  def fetchAllMovies: IO[Vector[Movie]] = {

    def toMovies(rows: Vector[MovieRow]): Vector[Movie] = rows.groupBy(r => (r.name, r.synopsis)).map {
      case ((name, synopsis), movieRows) => Movie(name, synopsis, movieRows.flatMap(_.review))
    }.toVector

    for {
      rows <- sql"""
                   SELECT m.name, m.synopsis, r.author, r.comment
                   FROM movie m
                   LEFT OUTER JOIN review r ON r.movie_id = m.id
                   ORDER BY m.id
                 """.query[MovieRow].to[Vector].transact(transactor)
    } yield toMovies(rows)

  }

  def saveMovie(movie: MovieToSave): IO[MovieId] = {
    val insertMovie: ConnectionIO[MovieId] =
      for {
        movieId <- sql"""
                        INSERT INTO movie (name, synopsis) VALUES (${movie.name}, ${movie.synopsis})
                        RETURNING id
                      """.query[MovieId].unique
      } yield movieId

    insertMovie.transact(transactor)
  }

  def saveReview(movieId: MovieId, review: ReviewToSave): IO[ReviewId] = {
    val insertMovie: ConnectionIO[ReviewId] =
      for {
        reviewId <- sql"""
                        INSERT INTO review (movie_id, author, comment) VALUES (${movieId.value}, ${review.author}, ${review.comment})
                        RETURNING id
                      """.query[ReviewId].unique
      } yield reviewId

    insertMovie.transact(transactor)
  }
}

object PostgresqlRepository {
  def apply(env: Map[String, String]): PostgresqlRepository = {
    val ds = new PGSimpleDataSource()
    ds.setServerName(env("DATABASE_HOST"))
    ds.setUser(env("DATABASE_USERNAME"))
    ds.setPassword(env("DATABASE_PASSWORD"))
    ds.setDatabaseName(env("DATABASE_NAME"))
    val transactor = Transactor.fromDataSource[IO](ds)
    new PostgresqlRepository(transactor)
  }
}

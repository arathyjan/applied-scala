package com.reagroup.appliedscala.urls.diagnostics

import java.util.concurrent.atomic.{AtomicInteger, AtomicReference}
import cats.effect.IO
import cats.effect.ContextShift
import cats.effect.Timer
import com.reagroup.api.infrastructure.diagnostics.Diagnostic
import com.reagroup.api.infrastructure.diagnostics._
import org.specs2.concurrent.ExecutionEnv
import org.specs2.matcher.FutureMatchers
import org.specs2.mutable.Specification

class PostgresqlRepositoryCheckSpec(implicit ee: ExecutionEnv) extends Specification with FutureMatchers {

  implicit val contextShift: ContextShift[IO] = IO.contextShift(ee.ec)
  implicit val timer: Timer[IO] = IO.timer(ee.ec)

  "when the diagnostic succeeds" should {
    def successfulCheck(): IO[Unit] = IO.pure(())

    "the result should indicate success" in {
      new Diagnostic[IO, IO.Par]().execute(PostgresqlRepositoryCheck(successfulCheck _)).unsafeToFuture must beEqualTo(CheckSucceeded()).await
    }
  }

  "when the diagnostic fails" should {
    val failure = new RuntimeException("check failed")

    def unsuccessfulCheck(): IO[Unit] = {
      IO.raiseError(failure)
    }

    "the result should indicate failure" in {
      new Diagnostic[IO, IO.Par]().execute(PostgresqlRepositoryCheck(unsuccessfulCheck _)).unsafeToFuture must beLike[CheckResult] {
        case CheckFailed(message, throwable) =>
          message must_=== "postgresql diagnostic failed"
          throwable must beSome(beTheSameAs[Throwable](failure))
      }.await
    }
  }

  "handling Futures safely" in {
    "the check never runs if the diagnostic isn't executed" in {
      val message = new AtomicReference[Option[String]](None)
      def check(): IO[Unit] = IO {
        message.set(Some("check() should not have been invoked"))
      }

      val checkDefinition = PostgresqlRepositoryCheck(check _)

      checkDefinition must beAnInstanceOf[DiagnosticCheckDefinition[IO]]
      message.get() must beNone
    }

    "the check runs as many times as the diagnostic is executed" in {
      val counter = new AtomicInteger(0)
      def check(): IO[Unit] = IO {
        counter.incrementAndGet()
        ()
      }

      val checkDefinition = PostgresqlRepositoryCheck(check _)

      Range(0, 10).foreach(_ => new Diagnostic[IO, IO.Par]().execute(checkDefinition))

      counter.get() must_== 10
    }
  }
}

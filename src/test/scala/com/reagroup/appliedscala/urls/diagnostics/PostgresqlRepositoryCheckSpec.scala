package com.reagroup.appliedscala.urls.diagnostics

import java.util.concurrent.atomic.{AtomicInteger, AtomicReference}
import cats.effect.IO
import com.reagroup.api.infrastructure.diagnostics.Diagnostic
import com.reagroup.api.infrastructure.diagnostics.Diagnostic._
import org.specs2.concurrent.ExecutionEnv
import org.specs2.matcher.FutureMatchers
import org.specs2.mutable.Specification

class PostgresqlRepositoryCheckSpec(implicit ee: ExecutionEnv) extends Specification with FutureMatchers {
  "when the diagnostic succeeds" should {
    def successfulCheck(): IO[Unit] = IO.pure(())

    "the result should indicate success" in {
      Diagnostic.execute(PostgresqlRepositoryCheck(successfulCheck _)) must beEqualTo(CheckSucceeded()).await
    }
  }

  "when the diagnostic fails" should {
    val failure = new RuntimeException("check failed")

    def unsuccessfulCheck(): IO[Unit] = {
      IO.raiseError(failure)
    }

    "the result should indicate failure" in {
      Diagnostic.execute(PostgresqlRepositoryCheck(unsuccessfulCheck _)) must beLike[CheckResult] {
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

      checkDefinition must beAnInstanceOf[DiagnosticCheckDefinition]
      message.get() must beNone
    }

    "the check runs as many times as the diagnostic is executed" in {
      val counter = new AtomicInteger(0)
      def check(): IO[Unit] = IO {
        counter.incrementAndGet()
        ()
      }

      val checkDefinition = PostgresqlRepositoryCheck(check _)

      Range(0, 10).foreach(_ => Diagnostic.execute(checkDefinition))

      counter.get() must_== 10
    }
  }
}

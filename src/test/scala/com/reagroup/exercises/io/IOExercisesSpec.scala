package com.reagroup.exercises.io

import cats.effect.IO
import com.reagroup.exercises.io.IOExercises._
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.FunSpec

class IOExercisesSpec extends FunSpec with TypeCheckedTripleEquals {

  describe("immediatelyExecutingIO") {
    it("should return an IO that would return number 43") {
      val result = immediatelyExecutingIO()

      assert(result.unsafeRunSync() === 43)
    }
  }

  describe("helloWorld") {
    it("should return an IO that would log 'hello world' using the `logger` provided") {
      val logger = new TestLogger
      val result = helloWorld(logger)

      assert(result.unsafeRunSync() === ())
      assert(logger.loggedMessages.toList === List("hello world"))
    }
  }

  describe("alwaysFailingTask") {
    it("should return an IO containing an Exception") {
      alwaysFailingTask().attempt.unsafeRunSync() match {
        case Left(_: Exception) => succeed
        case otherwise => fail(s"Expected a Left(Exception()) but received a $otherwise")
      }
    }
  }

  describe("logMessageOrFailIfEmpty") {
    it("should run `logger` if `msg` is not empty") {
      val logger = new TestLogger
      val msg = "message"
      val result = logMessageOrFailIfEmpty(msg, logger)

      assert(result.unsafeRunSync() === ())
      assert(logger.loggedMessages.toList === List(msg))
    }

    it("should return AppException if `msg` is empty") {
      val logger = new TestLogger
      val msg = ""
      val result = logMessageOrFailIfEmpty(msg, logger)

      assert(result.attempt.unsafeRunSync() === Left(AppException("Log must not be empty")))
      assert(logger.loggedMessages.toList === List())
    }
  }

  describe("getCurrentTempInF") {
    it("should convert the current temperature to Fahrenheit") {
      val currentTemp = IO.pure(Celsius(100))
      val result = getCurrentTempInF(currentTemp)

      assert(result.unsafeRunSync() === Fahrenheit(212))
    }
  }

  describe("getCurrentTempInFAgain") {
    it("should convert the current temperature to Fahrenheit using an external converter") {
      val currentTemp = IO.pure(Celsius(100))
      val converter = (c: Celsius) => IO(Fahrenheit(c.value * 9 / 5 + 32))
      val result = getCurrentTempInFAgain(currentTemp, converter)

      assert(result.unsafeRunSync() === Fahrenheit(212))
    }
  }

  describe("showCurrentTempInF") {
    it("should return the current temperature in a sentence") {
      val currentTemp = IO.pure(Celsius(100))
      val converter = (c: Celsius) => IO(Fahrenheit(c.value * 9 / 5 + 32))
      val result = showCurrentTempInF(currentTemp, converter)

      assert(result.unsafeRunSync() === "The temperature is 212")
    }

    it("should return an error if the converter fails") {
      val currentTemp = IO.pure(Celsius(100))
      val error = new Throwable("error")
      val converter: Celsius => IO[Fahrenheit] = _ => IO.raiseError(error)
      val result = showCurrentTempInF(currentTemp, converter)

      assert(result.unsafeRunSync() === error.getMessage)
    }
  }

  describe("mkUsernameThenPrint") {
    it("should print a username if it is not empty") {
      val username = "scalauser"
      val logger = new TestLogger
      val result = mkUsernameThenPrint(username, logger)

      assert(result.unsafeRunSync() === ())
      assert(logger.loggedMessages.toList === List(username))
    }

    it("should return UserNameError if the username is empty") {
      val username = ""
      val logger = new TestLogger
      val result = mkUsernameThenPrint(username, logger)
      
      assert(result.attempt.unsafeRunSync() === Left(UsernameError("Username cannot be empty")))
    }
  }

}


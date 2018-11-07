package com.reagroup.exercises.io

import cats.effect.IO

/**
  * These exercises are repurposed from https://github.com/lukestephenson/monix-adventures
  *
  * A value of type `IO[A]` may return a value of type `A` (or fail) when you execute it. It implies that there is a side-effect
  * that needs to be carried out before you may or may not get a value of type `A`.
  *
  * Unfortunately, by looking at the type signature alone you do not know precisely what side-effect will be carried out.
  */
object IOExercises {

  /**
    * Create an IO which returns the number 43.
    *
    * Hint: You want to look for a function in IO with the type signature A => IO[A]
    */
  def immediatelyExecutingIO(): IO[Int] =
    IO.pure(43)

  /**
    * Create an IO which when executed logs “hello world” (using `logger`)
    *
    * Remember this is merely a description of logging, nothing has been executed yet.
    *
    * Hint: You want to look for a function in IO with the type signature (=> A) => IO[A].
    * This means that the input will be lazily evaluated.
    *
    * Note: By "injecting" `logger` as a dependency to this function, we are able to use a test logger in our unit test
    * instead of relying on a mocking framework.
    */
  def helloWorld(logger: String => Unit): IO[Unit] =
    IO(logger("hello world"))

  /**
    * Create an IO which always fails with an `Exception`
    *
    * Hint: https://typelevel.org/cats-effect/datatypes/io.html#raiseerror
    */
  def alwaysFailingTask(): IO[Unit] =
    IO.raiseError(new Exception())

  /**
    * This is a data type that represents an exception in our program.
    */
  case class AppException(msg: String) extends Exception

  /**
    * If `msg` is empty, create a failing IO using `AppException` with an appropriate error message.
    *
    * Otherwise, log the message using `logger`
    */
  def logMessageOrFailIfEmpty(msg: String, logger: String => Unit): IO[Unit] =
    if (msg.nonEmpty)
      IO(logger(msg))
    else
      IO.raiseError(AppException("Log must not be empty"))

  /**
    * We're going to work with temperature next. We start off by creating tiny types for `Fahrenheit` and `Celsius`.
    * By doing this, we can differentiate between the two easily.
    */
  case class Fahrenheit(value: Int)

  case class Celsius(value: Int)

  /**
    * You're gonna need this for the next exercise.
    */
  private def cToF(c: Celsius): Fahrenheit = Fahrenheit(c.value * 9 / 5 + 32)

  /**
    * Create an IO which gets the current temperature in Celsius and if successful, converts it to Fahrenheit
    * using `cToF` defined above.
    */
  def getCurrentTempInF(currentTemp: () => IO[Celsius]): IO[Fahrenheit] =
    currentTemp().map(cToF)

  /**
    * Suppose the Celsius to Fahrenheit conversion is complex so we have decided to refactor it out to a remote
    * microservice.
    *
    * Similar to the previous exercise, create an IO which gets the current temperature in Celsius and if successful,
    * converts it to Fahrenheit by using the remote service call `converter`.
    *
    * Again, our remote service call is passed in as an input argument so we can easily unit test this function
    * without the need for a mocking framework.
    */
  def getCurrentTempInFAgain(currentTemp: () => IO[Celsius], converter: Celsius => IO[Fahrenheit]): IO[Fahrenheit] =
    for {
      c <- currentTemp()
      f <- converter(c)
    } yield f

  /**
    * Using what we just wrote above, we will convert the result into a `String` describing the temperature,
    * or in the case of a failure, we report the error as a `String`.
    *
    * Note that every IO has potential of failure. Try defer error handling until the very end of your program (here!).
    *
    * Hint: https://typelevel.org/cats-effect/datatypes/io.html#attempt
    */
  def showCurrentTempInF(currentTemp: () => IO[Celsius], converter: Celsius => IO[Fahrenheit]): IO[String] =
    getCurrentTempInFAgain(currentTemp, converter).attempt.map {
      case Right(fahrenheit) => s"The temperature is $fahrenheit"
      case Left(throwable) => throwable.getMessage
    }

  /**
    * `UsernameError` and `Username` are tiny types we are going to use for the next exercise.
    */
  case class UsernameError(value: String) extends Exception

  case class Username(value: String)

  private def mkUsername(username: String): Either[UsernameError, Username] =
    if (username.nonEmpty) Right(Username(username)) else Left(UsernameError("Username cannot be empty"))

  /**
    * Use `mkUsername` to create a `Username` and if successful print the username, otherwise fail with an error.
    */
  def mkUsernameThenPrint(username: String): IO[Unit] =
    mkUsername(username) match {
      case Right(Username(u)) => IO(println(u))
      case Left(UsernameError(msg)) => IO.raiseError(UsernameError(msg))
    }

  /**
    * Finally, we want to learn how to execute an IO. We are not going to need to do this when writing a REST API however,
    * the library will take care of the IO for you.
    *
    * Hint: https://typelevel.org/cats-effect/datatypes/io.html#unsaferunsync
    */
  def execute[A](io: IO[A]): A =
    io.unsafeRunSync()

}

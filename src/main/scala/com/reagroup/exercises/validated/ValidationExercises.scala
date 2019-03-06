package com.reagroup.exercises.validated

import cats.data.Validated
import cats.data.ValidatedNel
import cats.implicits._

/**
  * These exercises are repurposed from https://github.com/cwmyers/FunctionalTraining
  *
  * For further reading: Refer to Cats docs below. You can replace all references of `ValidatedNec` with `ValidatedNel`.
  * `Nec` stands for `NonEmptyChain`, which is very similar to `NonEmptyList`.
  * Cats implemented their own version of Scala's `List` called `Chain`, and `NonEmptyChain` is the `NonEmptyList` equivalent.
  *
  * https://typelevel.org/cats/datatypes/validated.html
  *
  * Here's an REA tech blog post on the same topic, but using Scalaz instead of Cats:
  * https://www.rea-group.com/blog/feeling-validated-a-different-way-to-validate-your-input
  */
object ValidationExercises {

  case class Person(firstName: String, lastName: String, password: String)

  sealed trait ValidationError

  case object PasswordTooShort extends ValidationError

  case object PasswordTooWeak extends ValidationError

  case class NameIsEmpty(label: String) extends ValidationError

  /**
    * If the `name` is empty, return a `NameIsEmpty(label)` in an `Invalid(NonEmptyList(...)`.
    *
    * `label` will be something like `firstName` or `lastName`.
    *
    * If the `name` is not empty, return it in a `Valid`.
    *
    * Hint: Use the `.invalidNel` and `.validNel` combinators
    */
  def nameValidation(name: String, label: String): ValidatedNel[ValidationError, String] =
    Validated.condNel(name.trim.nonEmpty, name, NameIsEmpty(label))

  /**
    * If the `password` does not contain a numeric character, return a `PasswordTooWeak`.
    *
    * Otherwise, return the `password`.
    *
    * Hint: Use `password.exists(Character.isDigit)`
    */
  def passwordStrengthValidation(password: String): ValidatedNel[ValidationError, String] =
    Validated.condNel(password.exists(Character.isDigit), password, PasswordTooWeak)

  /**
    * If the `password` length is not greater than 8 characters, return `PasswordTooShort`.
    *
    * Otherwise, return the `password`.
    */
  def passwordLengthValidation(password: String): ValidatedNel[ValidationError, String] =
    Validated.condNel(password.length > 8, password, PasswordTooShort)

  /**
    * Compose `passwordStrengthValidation` and `passwordLengthValidation` using Applicative `mapN`
    * to construct a larger `passwordValidation`.
    */
  def passwordValidation(password: String): ValidatedNel[ValidationError, String] =
    (passwordStrengthValidation(password), passwordLengthValidation(password)).mapN((p1, p2) => p2)

  /**
    * Compose `nameValidation` and `passwordValidation` to construct a function to `validatePerson`.
    */
  def validatePerson(firstName: String, lastName: String, password: String): ValidatedNel[ValidationError, Person] =
    (nameValidation(firstName, "firstName"), nameValidation(lastName, "lastName"), passwordValidation(password)).mapN(Person(_, _, _))


  /**
    * Given a list of `(firstName, lastName, password)`, return either a `List[Person]` or
    * all the `ValidationErrors` if there are any.
    */
  type FirstName = String
  type LastName = String
  type Password = String
  def validatePeople(inputs: List[(FirstName, LastName, Password)]): ValidatedNel[ValidationError, List[Person]] =
    inputs.traverse { case (x, y, z) => validatePerson(x, y, z) }

}

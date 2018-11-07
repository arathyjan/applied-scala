package com.reagroup.exercises.validated

import cats.data.Validated
import cats.data.ValidatedNel
import cats.implicits._

object ValidationExercises {

  case class Person(firstName: String, lastName: String, password: String)

  sealed trait ValidationError

  case object PasswordTooShort extends ValidationError

  case object PasswordTooWeak extends ValidationError

  case class NameIsEmpty(label: String) extends ValidationError

  def nameValidation(name: String, label: String): ValidatedNel[ValidationError, String] =
    Validated.condNel(name.trim.nonEmpty, name, NameIsEmpty(label))

  def passwordStrengthValidation(password: String): ValidatedNel[ValidationError, String] =
    Validated.condNel(password.exists(Character.isDigit), password, PasswordTooWeak)

  def passwordLengthValidation(password: String): ValidatedNel[ValidationError, String] =
    Validated.condNel(password.length > 8, password, PasswordTooShort)

  def passwordValidation(password: String): ValidatedNel[ValidationError, String] =
    passwordStrengthValidation(password) |+| passwordLengthValidation(password)

  def validatePerson(firstName: String, lastName: String, password: String): ValidatedNel[ValidationError, Person] =
    (nameValidation(firstName, "firstName"), nameValidation(lastName, "lastName"), passwordValidation(password)).mapN(Person(_, _, _))

}

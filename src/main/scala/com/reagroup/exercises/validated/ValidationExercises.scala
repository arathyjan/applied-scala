package com.reagroup.exercises.validated

import cats.data.Validated
import cats.data.ValidatedNel
import cats.implicits._

/**
  * These exercises are repurposed from https://github.com/cwmyers/FunctionalTraining
  */
object ValidationExercises {

  case class Person(firstName: String, lastName: String, password: String)

  sealed trait ValidationError

  case object PasswordTooShort extends ValidationError

  case object PasswordTooWeak extends ValidationError

  case class NameIsEmpty(label: String) extends ValidationError

  def nameValidation(name: String, label: String): ValidatedNel[ValidationError, String] = ???

  def passwordStrengthValidation(password: String): ValidatedNel[ValidationError, String] = ???

  def passwordLengthValidation(password: String): ValidatedNel[ValidationError, String] = ???

  def passwordValidation(password: String): ValidatedNel[ValidationError, String] = ???

  def validatePerson(firstName: String, lastName: String, password: String): ValidatedNel[ValidationError, Person] = ???

  def validatePeople(inputs: List[(String, String, String)]): ValidatedNel[ValidationError, List[Person]] = ???

}

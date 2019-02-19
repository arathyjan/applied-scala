package com.reagroup.exercises.validated

import org.specs2.mutable.Specification
import ValidationExercises._
import cats.data.NonEmptyList
import cats.data.Validated._

class ValidationExercisesSpec extends Specification {

  "passwordLengthValidation" should {
    "fail when the password is too short" in {
      passwordLengthValidation("crim3a") == Invalid(NonEmptyList.of(PasswordTooShort))
    }
  }

  "passwordStrengthValidation" should {
    "fail when password is too weak" in {
      passwordStrengthValidation("crimeaasd") == Invalid(NonEmptyList.of(PasswordTooWeak))
    }
  }

  "passwordValidation" should {
    "fail when the password is too short and too weak" in {
      passwordValidation("") == Invalid(NonEmptyList.of(PasswordTooWeak, PasswordTooShort))
    }
  }

  "nameValidation" should {
    "fail when given an empty name" in {
      nameValidation("", "someLabel") == Invalid(NonEmptyList.of(NameIsEmpty("someLabel")))
    }
  }

  "validatePerson" should {
    "fail when given empty name and no password" in {
      validatePerson("", "", "") == Invalid(NonEmptyList.of(NameIsEmpty("firstName"), NameIsEmpty("lastName"), PasswordTooWeak, PasswordTooShort))
    }
  }

}

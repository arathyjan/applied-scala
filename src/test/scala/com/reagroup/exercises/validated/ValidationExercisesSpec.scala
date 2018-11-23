package com.reagroup.exercises.validated

import org.scalatest.FunSpec
import ValidationExercises._
import cats.data.NonEmptyList
import cats.data.Validated._

class ValidationExercisesSpec extends FunSpec {

  describe("passwordLengthValidation") {
    it("should fail when the password is too short") {
      assert(passwordLengthValidation("crim3a") == Invalid(NonEmptyList.of(PasswordTooShort)))
    }
  }

  describe("passwordStrengthValidation") {
    it("should fail when password is too weak") {
      assert(passwordStrengthValidation("crimeaasd") == Invalid(NonEmptyList.of(PasswordTooWeak)))
    }
  }

  describe("passwordValidation") {
    it("should fail when the password is too short and too weak") {
      assert(passwordValidation("") == Invalid(NonEmptyList.of(PasswordTooWeak, PasswordTooShort)))
    }
  }

  describe("nameValidation") {
    it("should fail when given an empty name") {
      assert(nameValidation("", "someLabel") == Invalid(NonEmptyList.of(NameIsEmpty("someLabel"))))
    }
  }

  describe("validatePerson") {
    it("should fail when given empty name and no password") {
      assert(validatePerson("", "", "") == Invalid(NonEmptyList.of(NameIsEmpty("firstName"), NameIsEmpty("lastName"), PasswordTooWeak, PasswordTooShort)))
    }
  }

}

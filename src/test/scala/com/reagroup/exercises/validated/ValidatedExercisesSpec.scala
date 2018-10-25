package com.reagroup.exercises.validated

import org.scalatest.FunSpec
import ValidationExercises._
import cats.data.NonEmptyList
import org.scalatest.Matchers._
import cats.data.Validated._

final class ValidatedExercisesSpec extends FunSpec {

  val allBad = Map[String, String]()
  val goodInput = Map("firstName" -> "Vladimir", "lastName" -> "Putin", "password" -> "crimea14")
  val passwordIsTooShort = goodInput + ("password" -> "crim3a")
  val passwordNoNumbers = goodInput + ("password" -> "crimeaasd")
  val passwordNoNumbersAndTooShort = goodInput + ("password" -> "crime")
  val noFirstName = goodInput - "firstName"
  val noLastName = goodInput - "lastName"
  val emptyFirstName = goodInput + ("firstName" -> "")
  val emptyLastName = goodInput + ("lastName" -> "")

  // "Good input" in {
  //   validateInput(goodInput) should be (Person("Vladimir", "Putin", "crimea14"))
  // }

  describe("validateKey") {
    it("should fail when a key is not found") {
      validateKey("firstName", allBad) should be (Invalid(NonEmptyList.of(keyNotFound("firstName"))))
      validateKey("lastName", allBad)  should be (Invalid(NonEmptyList.of(keyNotFound("lastName"))))
      validateKey("password", allBad)  should be (Invalid(NonEmptyList.of(keyNotFound("password"))))
    }
  }

  describe("passwordLengthValidation") {
    it("should fail when the password too short") {
      passwordLengthValidation("crim3a") should be (Invalid(NonEmptyList.of(passwordTooShort)))
    }
  }

  describe("passwordStrengthValidation") {
    it("should fail when password too weak") {
      passwordStrengthValidation("crimeaasd") should be (Invalid(NonEmptyList.of(passwordTooWeak)))
    }
  }

  describe("nameValidation") {
    it("should fail when given an empty name") {
      nameValidation("", "someLabel") should be (Invalid(NonEmptyList.of(nameIsEmpty("someLabel"))))
    }
  }

  describe("validateInput") {
    describe("should fail when") {
      it("password too weak") {
        validateInput(passwordNoNumbers) should be (Invalid(NonEmptyList.of(passwordTooWeak)))
      }

      it("password too short and too weak"){
        validateInput(passwordNoNumbersAndTooShort) should be (Invalid(NonEmptyList.of(passwordTooShort, passwordTooWeak)))
      }

      it("no first name"){
        validateInput(noFirstName) should be (Invalid(NonEmptyList.of(keyNotFound("firstName"))))
      }

      it("no last name"){
        validateInput(noLastName) should be (Invalid(NonEmptyList.of(keyNotFound("lastName"))))
      }

      it("empty first name"){
        validateInput(emptyFirstName) should be (Invalid(NonEmptyList.of(nameIsEmpty("firstName"))))
      }

      it("empty last name"){
        validateInput(emptyLastName) should be (Invalid(NonEmptyList.of(nameIsEmpty("lastName"))))
      }
    }
  }
}

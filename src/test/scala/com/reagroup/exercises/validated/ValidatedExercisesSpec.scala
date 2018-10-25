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

  describe("ValidatedExercises") {
    describe("should fail when") {
      it("when a key is not found") {
        validateKey("firstName", allBad) should be (Invalid(NonEmptyList.of(keyNotFound("firstName"))))
        validateKey("lastName", allBad)  should be (Invalid(NonEmptyList.of(keyNotFound("lastName"))))
        validateKey("password", allBad)  should be (Invalid(NonEmptyList.of(keyNotFound("password"))))
      }

      it("password too short") {
        passwordLengthValidation("crim3a") should be (Invalid(NonEmptyList.of(passwordTooShort)))
      }

      it("password too weak") {
        passwordStrengthValidation("crimeaasd") should be (Invalid(NonEmptyList.of(passwordTooWeak)))
      }

      it("empty first name") {
        nameValidation("", "firstName") should be (Invalid(NonEmptyList.of(nameIsEmpty("firstName"))))
      }
    }
  }


  // "password too weak" in {
  //   validateInput(passwordNoNumbers) should beFailing(NonEmptyList(passwordTooWeak))
  // }
  // "password too short and too weak" in {
  //   validateInput(passwordNoNumbersAndTooShort) should beFailing(NonEmptyList(passwordTooShort, passwordTooWeak))
  // }
  // "no first name" in {
  //   validateInput(noFirstName) should beFailing(NonEmptyList(keyNotFound("firstName")))
  // }
  // "no last name" in {
  //   validateInput(noLastName) should beFailing(NonEmptyList(keyNotFound("lastName")))
  // }
  // "empty first name" in {
  //   validateInput(emptyFirstName) should beFailing(NonEmptyList(nameIsEmpty("firstName")))
  // }
  // "empty last name" in {
  //   validateInput(emptyLastName) should beFailing(NonEmptyList(nameIsEmpty("lastName")))
  // }

}

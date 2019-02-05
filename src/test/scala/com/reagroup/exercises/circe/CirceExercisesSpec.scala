package com.reagroup.exercises.circe

import org.scalatest.FunSpec
import CirceExercises._
import io.circe.{DecodingFailure, Json, ParsingFailure}
import io.circe.syntax._
import io.circe.literal._

class CirceExercisesSpec extends FunSpec {

  describe("strToJson") {

    it("should parse valid Json") {
      val json = json"""{"name": "scala"}"""
      val errOrJson = strToJson(json.noSpaces)
      assert(errOrJson == Right(json))
    }

    it("should return error for invalid Json") {
      val errOrJson = strToJson("""{"scala"}""")
      assert(errOrJson.isLeft)
    }

  }

  describe("personToJson") {

    it("should convert Person to Json") {
      val person = Person("scala", 20)
      val actual = personToJson(person)
      val expected = Json.obj("name" -> "scala".asJson, "age" -> 20.asJson)

      assert(actual == expected)
    }

  }

  describe("encodePerson") {

    it("should convert Person to Json") {
      val person = Person("scala", 20)
      val actual = encodePerson(person)
      val expected = Json.obj("name" -> "scala".asJson, "age" -> 20.asJson)

      assert(actual == expected)
    }

  }

  describe("encodePersonAgain") {

    it("should convert Person to Json") {
      val person = Person("scala", 20)
      val actual = encodePersonAgain(person)
      val expected = Json.obj("name" -> "scala".asJson, "age" -> 20.asJson)

      assert(actual == expected)
    }

  }

  describe("encodePersonSemiAuto") {

    it("should convert Person to Json") {
      val person = Person("scala", 20)
      val actual = encodePersonSemiAuto(person)
      val expected = Json.obj("name" -> "scala".asJson, "age" -> 20.asJson)

      assert(actual == expected)
    }

  }

  describe("jsonToPerson") {

    it("should convert valid Json to Person") {
      val json = Json.obj("name" -> "scala".asJson, "age" -> 20.asJson)
      val errOrPerson = jsonToPerson(json)

      assert(errOrPerson == Right(Person("scala", 20)))
    }

    it("should convert invalid Json to error") {
      val json = Json.obj("foo" -> "bar".asJson)
      val errOrPerson = jsonToPerson(json)

      assert(errOrPerson.isLeft)
    }

  }

  describe("decodePerson") {

    it("should convert valid Json to Person") {
      val json = Json.obj("name" -> "scala".asJson, "age" -> 20.asJson)
      val errOrPerson = decodePerson(json)

      assert(errOrPerson == Right(Person("scala", 20)))
    }

    it("should convert invalid Json to error") {
      val json = Json.obj("foo" -> "bar".asJson)
      val errOrPerson = decodePerson(json)

      assert(errOrPerson.isLeft)
    }

  }

  describe("decodePersonAgain") {

    it("should convert valid Json to Person") {
      val json = Json.obj("name" -> "scala".asJson, "age" -> 20.asJson)
      val errOrPerson = decodePersonAgain(json)

      assert(errOrPerson == Right(Person("scala", 20)))
    }

    it("should convert invalid Json to error") {
      val json = Json.obj("foo" -> "bar".asJson)
      val errOrPerson = decodePersonAgain(json)

      assert(errOrPerson.isLeft)
    }

  }

  describe("decodePersonSemiAuto") {

    it("should convert valid Json to Person") {
      val json = Json.obj("name" -> "scala".asJson, "age" -> 20.asJson)
      val errOrPerson = decodePersonSemiAuto(json)

      assert(errOrPerson == Right(Person("scala", 20)))
    }

    it("should convert invalid Json to error") {
      val json = Json.obj("foo" -> "bar".asJson)
      val errOrPerson = decodePersonSemiAuto(json)

      assert(errOrPerson.isLeft)
    }

  }

  describe("strToPerson") {

    it("should convert valid Json String to Person") {
      val jsonStr = Json.obj("name" -> "scala".asJson, "age" -> 20.asJson).noSpaces
      val errOrPerson = strToPerson(jsonStr)

      assert(errOrPerson == Right(Person("scala", 20)))
    }

    it("should convert invalid Json String to ParsingFailure") {
      val invalidJsonStr = "..."
      val errOrPerson = strToPerson(invalidJsonStr)

      errOrPerson match {
        case Left(ParsingFailure(_, _)) => succeed
        case other => fail(s"Expected ParsingFailure, but received: $other")
      }
    }

    it("should convert valid Json String that doesn't contain correct info to DecodingFailure") {
      val invalidJsonStr = """{"name": 12345}"""
      val errOrPerson = strToPerson(invalidJsonStr)

      errOrPerson match {
        case Left(DecodingFailure(_, _)) => succeed
        case other => fail(s"Expected DecodingFailure, but received: $other")
      }
    }

  }

}

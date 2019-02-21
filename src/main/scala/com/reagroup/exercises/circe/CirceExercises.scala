package com.reagroup.exercises.circe

import io.circe._
import io.circe.syntax._

object CirceExercises {

  /**
    * Json Parsing
    */

  /**
    * Why is the return type an `Either`?
    */
  def strToJson(str: String): Either[ParsingFailure, Json] = {
    import io.circe.parser._
    ???
  }

  case class Person(name: String, age: Int)

  /**
    * Encoding
    */

  def personToJson(person: Person): Json = ???

  /**
    * Try make a syntax error in the following Json document and compile.
    * What happens?
    */
  val validJson: Json = {
    import io.circe.literal._

    json"""
      {
        "someKey": "someValue",
        "anotherKey": "anotherValue"
      }
    """
  }

  def encodePerson(person: Person): Json = ???

  /**
    * Hint: Use `Encoder.forProduct2`
    */
  def encodePersonAgain(person: Person): Json = {
    val personEncoder: Encoder[Person] = ???
    person.asJson(???)
  }

  /**
    * Hint: Use `deriveEncoder`
    */
  def encodePersonSemiAuto(person: Person): Json = {
    import io.circe.generic.semiauto._

    val personEncoder: Encoder[Person] = ???
    person.asJson(???)
  }

  /**
    * Decoding
    */

  /**
    * Why is the return type an `Either`?
    */
  def jsonToPerson(json: Json): Either[DecodingFailure, Person] = ???

  def decodePerson(json: Json): Either[DecodingFailure, Person] = {
    val personDecoder: Decoder[Person] = ???

    json.as[Person](???)
  }

  /**
    * Hint: Use `Decoder.forProduct2`
    */
  def decodePersonAgain(json: Json): Either[DecodingFailure, Person] = {
    val personDecoder: Decoder[Person] = ???
    json.as[Person](???)
  }

  /**
    * Hint: Use deriveDecoder
    */
  def decodePersonSemiAuto(json: Json): Either[DecodingFailure, Person] = {
    import io.circe.generic.semiauto._

    val personDecoder: Decoder[Person] = ???
    json.as[Person](???)
  }

  /**
    * Parse and then decode
    */

  def strToPerson(str: String): Either[Error, Person] = {
    import io.circe.parser._
    import io.circe.generic.semiauto._

    implicit val personDecoder: Decoder[Person] = ???

    ???

  }

}

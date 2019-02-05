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
    parse(str)
  }

  case class Person(name: String, age: Int)

  /**
    * Encoding
    */

  def personToJson(person: Person): Json =
    Json.obj(
      "name" -> person.name.asJson,
      "age" -> person.age.asJson
    )

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

  def encodePerson(person: Person): Json = {
    val personEncoder: Encoder[Person] = (a: Person) => personToJson(a)
    person.asJson(personEncoder)
  }

  /**
    * Hint: Use `Encoder.forProduct2`
    */
  def encodePersonAgain(person: Person): Json = {
    val personEncoder: Encoder[Person] = Encoder.forProduct2("name", "age")(p => (p.name, p.age))
    person.asJson(personEncoder)
  }

  /**
    * Hint: Use `deriveEncoder`
    */
  def encodePersonSemiAuto(person: Person): Json = {
    import io.circe.generic.semiauto._

    val personEncoder: Encoder[Person] = deriveEncoder[Person]
    person.asJson(personEncoder)
  }

  /**
    * Decoding
    */

  /**
    * Why is the return type an `Either`?
    */
  def jsonToPerson(json: Json): Either[DecodingFailure, Person] = {
    val cursor = json.hcursor
    for {
      name <- cursor.get[String]("name")
      age <- cursor.get[Int]("age")
    } yield Person(name, age)
  }

  def decodePerson(json: Json): Either[DecodingFailure, Person] = {
    val personDecoder: Decoder[Person] = (c: HCursor) => for {
      name <- c.get[String]("name")
      age <- c.get[Int]("age")
    } yield Person(name, age)

    json.as[Person](personDecoder)
  }

  /**
    * Hint: Use `Decoder.forProduct2`
    */
  def decodePersonAgain(json: Json): Either[DecodingFailure, Person] = {
    val personDecoder: Decoder[Person] = Decoder.forProduct2("name", "age")((name, age) => Person(name, age))
    json.as[Person](personDecoder)
  }

  /**
    * Hint: Use deriveDecoder
    */
  def decodePersonSemiAuto(json: Json): Either[DecodingFailure, Person] = {
    import io.circe.generic.semiauto._

    val personDecoder: Decoder[Person] = deriveDecoder[Person]
    json.as[Person](personDecoder)
  }

  /**
    * Parse and then decode
    */

  def strToPerson(str: String): Either[Error, Person] = {
    import io.circe.parser._
    import io.circe.generic.semiauto._

    implicit val personDecoder: Decoder[Person] = deriveDecoder[Person]

    for {
      json <- parse(str)
      person <- json.as[Person]
    } yield person

  }

}

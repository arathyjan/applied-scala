package com.reagroup.exercises.circe

import io.circe.Decoder.Result
import io.circe._
import io.circe.generic.semiauto.deriveEncoder
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

  // TODO Add hints!
  // A method to construct an Encoder
  def encodePerson(person: Person): Json = {
    implicit val personEncoder: Encoder[Person] = (person: Person) => Json.obj(
      "name" -> person.name.asJson,
      "age" -> person.age.asJson
    )
    person.asJson
  }

  /**
    * // TODO Why should we use forProduct2 instead?
    * Hint: Use `Encoder.forProduct2`
    */
  def encodePersonAgain(person: Person): Json = {
    implicit val personEncoder: Encoder[Person] =
      Encoder.forProduct2("name", "age")(p => (p.name, p.age))
    person.asJson
  }

  /**
    * Hint: Use `deriveEncoder`
    */
  def encodePersonSemiAuto(person: Person): Json = {
    import io.circe.generic.semiauto._

    implicit val personEncoder: Encoder[Person] = deriveEncoder[Person]
    person.asJson
  }

  /**
    * Decoding
    */

  /**
    * Why is the return type an `Either`?
    */
  def jsonToPerson(json: Json): Result[Person] = {
    val cursor = json.hcursor
    val errorOrName: Result[String] = cursor.get[String]("name")
    val errorOrAge: Result[Int] = cursor.get[Int]("age")

    for {
      name <- errorOrName
      age <- errorOrAge
    } yield Person(name, age)
  }

  // TODO Typeclass coherence
  def decodePerson(json: Json): Either[DecodingFailure, Person] = {
    import cats.implicits._

    implicit val personDecoder: Decoder[Person] = (c: HCursor) =>
      (c.get[String]("name"), c.get[Int]("age")).mapN(Person.apply)

    json.as[Person]
  }

  /**
    * Hint: Use `Decoder.forProduct2`
    */
  def decodePersonAgain(json: Json): Either[DecodingFailure, Person] = {
    implicit val personDecoder: Decoder[Person] =
      Decoder.forProduct2("name", "age")(Person.apply)
    json.as[Person]
  }

  /**
    * Hint: Use deriveDecoder
    */
  def decodePersonSemiAuto(json: Json): Either[DecodingFailure, Person] = {
    import io.circe.generic.semiauto._

    implicit val personDecoder: Decoder[Person] = deriveDecoder[Person]
    json.as[Person]
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

    decode[Person](str)

  }

}

package com.reagroup.exercises.circe

import io.circe.Decoder.Result
import io.circe._
import io.circe.generic.semiauto.deriveEncoder
import io.circe.syntax._

/**
  * Circe (pronounced SUR-see, or KEER-kee in classical Greek, or CHEER-chay in Ecclesiastical Latin) is a JSON library for Scala.
  *
  * We like Circe as opposed to other libraries because it is functional, type-safe and very idiomatic.
  * It integrates very well with the Cats ecosystem.
  *
  * For more comprehensive docs on Circe:
  * https://circe.github.io/circe/
  *
  * There are 3 parts to these exercises.
  *
  * 1. Parsing (`String => Json`)
  * 2. Encoding (`A => Json`)
  * 3. Decoding (`Json => A`)
  */
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
    *
    * Hint: Use `Json.obj()`
    * Hint: Use `.asJson` to convert Scala standard types to Json
    *
    * For more comprehensive examples:
    * https://circe.github.io/circe/codecs/custom-codecs.html
    */

  def personToJson(person: Person): Json =
    ???

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

  /**
    * Create an `Encoder` instance for `Person` by implementing a function
    * `Person => Json`
    *
    * Make `personEncoder` an `implicit` to avoid having to pass the `Encoder` instance
    * into `asJson` explicitly.
    */
  def encodePerson(person: Person): Json = {
    val personEncoder: Encoder[Person] = (a: Person) => ???
    person.asJson(???)
  }

  /**
    * There is an alternate way to construct an `Encoder` instance,
    * by using `Encoder.forProduct2`.
    *
    * This may sometimes be simpler than using `Json.obj`.
    */
  def encodePersonAgain(person: Person): Json = {
    val personEncoder: Encoder[Person] = ???
    person.asJson(???)
  }

  /**
    * Sick of writing custom encoders? You can use "semiauto derivation"
    * to create an `Encoder` instance for you using a Scala feature called macros.
    *
    * The downside to this is the keys of your `Json` are now tightly coupled with
    * how you have named the fields inside `Person`
    *
    * Hint: Use `deriveEncoder`
    *
    * For more comprehensive examples:
    * https://circe.github.io/circe/codecs/semiauto-derivation.html
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
    *
    * For more comprehensive cursor docs:
    * https://circe.github.io/circe/api/io/circe/HCursor.html
    */
  def jsonToPerson(json: Json): Either[DecodingFailure, Person] = {
    val cursor = json.hcursor
    ???
  }

  /**
    * Construct a `Decoder` instance for `Person` by navigating through
    * the `Json` using an `HCursor`.
    *
    * Hint: Use `c.downField("name")` to navigate to the `"name"` field.
    * `c.downField("name").as[Int]` will navigate to the `"name"` field
    * and attempt to decode the value as an `Int`.
    *
    * Alternatively, you can use `c.get[Int]("name")` to do the same thing.
    *
    * Once you have retrieved the `name` and `age`, construct a `Person`!
    *
    * For more comprehensive examples:
    * https://circe.github.io/circe/codecs/custom-codecs.html
    */
  def decodePerson(json: Json): Either[DecodingFailure, Person] = {
    import cats.implicits._

    val personDecoder: Decoder[Person] = ???

    // This says "Turn this Json to a Person"
    json.as[Person](???)
  }

  /**
    * Use `Decoder.forProduct2` to construct a `Decoder` instance
    * for `Person`.
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
    *
    * Hint: Use `parse` and then `as[Person]`.
    *
    * Alternatively, use `decode`, which does both at the same time.
    */

  def strToPerson(str: String): Either[Error, Person] = {
    import io.circe.parser._
    import io.circe.generic.semiauto._

    val personDecoder: Decoder[Person] = ???

    decode[Person](str)(???)
  }

}

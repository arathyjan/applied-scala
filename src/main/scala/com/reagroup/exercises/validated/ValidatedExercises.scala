package com.reagroup.exercises.validated

import cats.data.Validated
import cats.data.ValidatedNel
import cats.implicits._

object ValidationExercises {

  def validateKey(key: String, input: Map[String, String]): ValidatedNel[ErrorCode, String] =
    input.get(key).fold[ValidatedNel[ErrorCode, String]](keyNotFound(key).invalidNel)(x => x.validNel)

  def nameValidation(name: String, label: String): ValidatedNel[ErrorCode, String] =
    if (name.trim.isEmpty) nameIsEmpty(label).invalidNel else name.validNel

  def passwordStrengthValidation(password: String): ValidatedNel[ErrorCode, String] =
    if (!password.exists(Character.isDigit)) passwordTooWeak.invalidNel else password.validNel

  def passwordLengthValidation(password: String): ValidatedNel[ErrorCode, String] =
    if (password.length < 8) passwordTooShort.invalidNel else password.validNel

  def validateInput(input: Map[String, String]): ValidatedNel[ErrorCode, Person] =
    (validateKey("firstName", input).andThen(nameValidation(_, "firstName")),
     validateKey("lastName", input).andThen(nameValidation(_, "lastName")),
     validateKey("password", input).andThen { password =>
      passwordLengthValidation(password) |+| passwordStrengthValidation(password)
    }).mapN(Person.apply)
}

case class Person(firstName: String, lastName: String, password: String)

sealed trait ErrorCode

case object passwordTooShort extends ErrorCode

case object passwordTooWeak extends ErrorCode

case class keyNotFound(key: String) extends ErrorCode

case class nameIsEmpty(key: String) extends ErrorCode


/*
Interesting Validator combinators
scala> val a:ValidationNel[String,String]  = "hi".successNel
a: scalaz.ValidationNel[String,String] = Success(hi)
scala> val b:ValidationNel[String,String]  = "world".successNel
b: scalaz.ValidationNel[String,String] = Success(world)
scala> val c:ValidationNel[String,String]  = "error1".failNel
c: scalaz.ValidationNel[String,String] = Failure(NonEmptyList(error1))
scala> val d:ValidationNel[String,String]  = "error2".failNel
d: scalaz.ValidationNel[String,String] = Failure(NonEmptyList(error2))
scala> a <* b
res0: scalaz.Validation[scalaz.NonEmptyList[String],String] = Success(hi)
scala> a *> b
res1: scalaz.Validation[scalaz.NonEmptyList[String],String] = Success(world)
scala> c <* d
res2: scalaz.Validation[scalaz.NonEmptyList[String],String] = Failure(NonEmptyList(error1, error2))
scala> a <* d
res3: scalaz.Validation[scalaz.NonEmptyList[String],String] = Failure(NonEmptyList(error2))
scala> a flatMap (hi => b)
res4: scalaz.Validation[scalaz.NonEmptyList[String],String] = Success(world)
scala> a flatMap (s => if (s == "hi") "hey back".successNel else "fine, be that way!".failNel)
res6: scalaz.Validation[scalaz.NonEmptyList[String],String] = Success(hey back)
scala> d flatMap (s => if (s == "hi") "hey back".successNel else "fine, be that way!".failNel)
res7: scalaz.Validation[scalaz.NonEmptyList[String],String] = Failure(NonEmptyList(error2))
scala> b flatMap (s => if (s == "hi") "hey back".successNel else "fine, be that way!".failNel)
res8: scalaz.Validation[scalaz.NonEmptyList[String],String] = Failure(NonEmptyList(fine, be that way!))
scala> a map (hi => hi + " worldz")
res5: scalaz.Validation[scalaz.NonEmptyList[String],String] = Success(hi worldz)
 */

package com.reagroup.appliedscala

import cats.data.{NonEmptyList, Validated, ValidatedNel}

package object validated {
  def optionToValidatedNel[A, B](o: Option[A], error: B): ValidatedNel[B, A] =
    Validated.fromOption(o, NonEmptyList.one(error))

  implicit class OptionOps[A](o: Option[A]) {
    def validatedNel[B](error: B): ValidatedNel[B, A] = optionToValidatedNel(o, error)
  }

}

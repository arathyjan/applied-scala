package com.reagroup.appliedscala.urls.fetchenrichedmovie

import org.scalacheck.Gen
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification

class StarRatingSpec extends Specification with ScalaCheck {

  "fromScore" should {

    "return One when score is between 0 and 24" in prop { score: Int =>

      val result = StarRating.fromScore(score)

      result must_== Some(One)

    }.setGen(Gen.chooseNum(0, 24)).noShrink

    "return Two when score is between 25 and 49" in prop { score: Int =>

      val result = StarRating.fromScore(score)

      result must_== Some(Two)

    }.setGen(Gen.chooseNum(25, 49)).noShrink

    "return Three when score is between 50 and 74" in prop { score: Int =>

      val result = StarRating.fromScore(score)

      result must_== Some(Three)

    }.setGen(Gen.chooseNum(50, 74)).noShrink

    "return Four when score is between 75 and 100" in prop { score: Int =>

      val result = StarRating.fromScore(score)

      result must_== Some(Four)

    }.setGen(Gen.chooseNum(75, 100)).noShrink

    "return None for numbers outside of 0-100" in prop { score: Int =>

      val result = StarRating.fromScore(score)

      result must beNone

    }.setGen {
      val negativeGen = Gen.chooseNum(Int.MinValue, -1)
      val over100Gen = Gen.chooseNum(101, Int.MaxValue)
      Gen.oneOf(negativeGen, over100Gen)
    }.noShrink

  }

  "show" should {

    "return One Star for One" in {

      StarRating.show(One) must_== "One Star"

    }

    "return Two Stars for Two" in {

      StarRating.show(Two) must_== "Two Stars"

    }

    "return Three Stars for Three" in {

      StarRating.show(Three) must_== "Three Stars"

    }

    "return Four Stars for Four" in {

      StarRating.show(Four) must_== "Four Stars"

    }

  }

}

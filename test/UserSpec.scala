package test

import kcacup._

import org.specs2.mutable._

object FunctionalSpec extends Specification {

  import models.User.normalizeUsername

  "normalizeUsername" should {
    "leave empty username untouched" in {
      normalizeUsername("") == ""
    }
    "leave normalized username untouched" in {
      normalizeUsername("toto") == "toto"
    }
    "lowercase" in {
      normalizeUsername("tOtO") == "toto"
    }
    "remove fancy chars" in {
      normalizeUsername("t*t-é") == "tt"
    }
    "preserve alpha chars" in {
      normalizeUsername("_t0_to") == "_t0_to"
    }
  }
}

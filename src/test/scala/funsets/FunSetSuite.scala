package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = union(s1,s2)
    val s5 = union(s2,s3)
    val s6 = union(s4,s5)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      val unionS = union(s, s3)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
      assert(contains(unionS, 3), "Union 4")
      assert(contains(unionS, 1), "Union 5")
    }
  }

  test("intersection contains only common elements") {
    new TestSets {
      val emptyS = intersect(s1, s2)
      val interS = intersect(s4, s5)
      assert(!contains(emptyS, 1), "Intersection 1")
      assert(contains(interS, 2), "Intersection 2")
    }
  }

  test("difference, elements that are in a but not in b") {
    new TestSets {
      val diffS = diff(s4, s5)
      assert(contains(diffS, 1), "difference 1")
      assert(!contains(diffS, 2), "difference 2")
    }
  }

  test("filter, subset of s for which p holds") {
    new TestSets {
      val filterS = filter(s4, x => x == 2)
      val filterS2 = filter(s6, x => x == 2 || x ==3)
      assert(contains(filterS, 2), "filter 1")
      assert(!contains(filterS, 1), "filter 2")
      assert(contains(filterS2, 3), "filter 3")
      assert(contains(filterS2, 2), "filter 4")
      assert(!contains(filterS2, 1), "filter 5")
    }
  }

  test("forall, whether p holds for all") {
    new TestSets {
      val forallS = forall(s4, x => x == 2)
      val forallS2 = forall(s6, x => x == 1 || x == 2 || x ==3)
      val forallS3 = forall(s5, x => x == 2 || x ==3)
      assert(!forallS, s"forallS: $forallS")
      assert(forallS2, s"forallS2: $forallS2")
      assert(forallS3, s"forallS3: $forallS3")
    }
  }

  test("exists, whether p holds for any") {
    new TestSets {
      val existsS = exists(s4, x => x == 2)
      val existsS2 = exists(s6, x => x == 2 || x ==3)
      val existsS3 = exists(s5, x => x ==1)
      assert(existsS, s"existsS: $existsS")
      assert(existsS2, s"existsS2: $existsS2")
      assert(!existsS3, s"existsS3: $existsS3")
    }
  }

  test("map, maps all elements") {
    new TestSets {
      val mapS = map(s4, x => x*x)
      val mapS2 = map(s6, x => x*x)
      val mapS3 = map(s5, x => x*x)
      assert(mapS(4), s"mapS: $mapS")
      assert(mapS2(1), s"mapS2: $mapS2")
      assert(!mapS3(1), s"mapS3: $mapS3")
    }
  }


}

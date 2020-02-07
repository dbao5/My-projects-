//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: (p03a )
// Files: (hashtabletest)
// Course: (cs 400, spring, and 2019)
//
// Author: (di bao)
// Email: (dbao5@wisc.edu email address)
// Lecturer's Name: (depper)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:
// Partner Email:
// Partner Lecturer's Name:
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// _x__ Write-up states that pair programming is allowed for this assignment.
// _x__ We have both read and understand the course Pair Programming Policy.
// _x__ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: (identify each person and describe their help in detail)
// Online Sources: (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
// TODO: add imports as needed

import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



/** TODO: add class header comments here */
public class HashTableTest {

  HashTableADT a = new HashTable<Integer, String>();
  HashTableADT b = new HashTable<Integer, Integer>();
  // TODO: add other fields that will be used by multiple tests

  // TODO: add code that runs before each test method
  @Before
  public void setUp() throws Exception {
    a = createInstance();
    b = createInstance2();
  }

  private HashTableADT createInstance2() {
    // TODO Auto-generated method stub
    return new HashTable<Integer, Integer>();
  }

  private HashTableADT createInstance() {
    // TODO Auto-generated method stub
    return new HashTable<Integer, String>();
  }

  // TODO: add code that runs after each test method
  @After
  public void tearDown() throws Exception {
    a = null;
    b = null;
  }

  /**
   * Tests that a HashTable returns an integer code indicating which collision resolution strategy
   * is used. REFER TO HashTableADT for valid collision scheme codes.
   */
  @Test
  public void test000_collision_scheme() {
    HashTableADT htIntegerKey = new HashTable<Integer, String>();
    int scheme = htIntegerKey.getCollisionResolution();
    if (scheme < 1 || scheme > 9)
      fail("collision resolution must be indicated with 1-9");
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that insert(null,null) throws IllegalNullKeyException
   */
  @Test
  public void test001_IllegalNullKey() {
    try {
      HashTableADT htIntegerKey = new HashTable<Integer, String>();
      htIntegerKey.insert(null, null);
      fail("should not be able to insert null key");
    } catch (IllegalNullKeyException e) {
      /* expected */ } catch (Exception e) {
      fail("insert null key should not throw exception " + e.getClass().getName());
    }
  }

  /**
   * test002_duplicatekey
   */
  @Test
  public void test002_duplicatekey() {
    try {
      HashTableADT htIntegerKey = new HashTable<Integer, String>();
      htIntegerKey.insert(2, "a");
      htIntegerKey.insert(2, "b");
      fail("should not be able to insert dup key");
    } catch (DuplicateKeyException e) {
      // TODO: handle exception

    } catch (Exception e) {
      fail("insert null key should not throw exception " + e.getClass().getName());
    }
  }

  // TODO add your own tests of your implementation
  /**
   * test003_insertone_size_is_one
   */
  @Test
  public void test003_insertone_size_is_one() {
    try {
      HashTableADT htIntegerKey = new HashTable<Integer, String>();
      htIntegerKey.insert(2, "a");
      if (htIntegerKey.numKeys() != 1) {
        fail("the numberof keys should be 1 but it is not ");
      }
    } catch (Exception e) {
    }
  }

  /**
   * test004_insert_one_remove_one_size_should_be0
   */
  @Test
  public void test004_insert_one_remove_one_size_should_be0() {
    try {
      HashTableADT htIntegerKey = new HashTable<Integer, String>();
      htIntegerKey.insert(2, "a");
      htIntegerKey.remove("a");
      if (htIntegerKey.numKeys() == 0) {
        fail("the numberof keys should be 1 but it is not ");
      }
    } catch (Exception e) {
    }
  }

  /**
   * test005_insert_one_and_get_one
   */
  @Test
  public void test005_insert_one_and_get_one() {
    try {
      HashTableADT htIntegerKey = new HashTable<Integer, String>();
      htIntegerKey.insert(2, "a");
      if (htIntegerKey.get(2) != "a") {
        fail("the numberof keys should be 1 but it is not ");
      }
    } catch (Exception e) {
    }
  }

  /**
   * test006_insert_two_remove_one_size_should_be_one
   */
  @Test
  public void test006_insert_two_remove_one_size_should_be_one() {
    try {
      HashTableADT htIntegerKey = new HashTable<Integer, String>();
      htIntegerKey.insert(2, "a");
      htIntegerKey.insert(1, "b");
      htIntegerKey.remove("a");
      if (htIntegerKey.numKeys() == 1) {
        fail("the numberof keys should be 1 but it is not ");
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  /**
   * test007_insert_two_remove_two_get_throw_null
   */
  @Test
  public void test007_insert_two_remove_two_get_throw_null() {
    try {
      HashTableADT htIntegerKey = new HashTable<Integer, String>();
      htIntegerKey.insert(2, "a");
      htIntegerKey.insert(3, "b");
      htIntegerKey.remove("a");
      htIntegerKey.remove("b");
      if (htIntegerKey.get(2) != "a") {
        fail("the numberof keys should be 1 but it is not ");
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
}

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: (p01 )
// Files: (ds_my,testds_my)
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
import javax.management.RuntimeErrorException;
import javax.xml.crypto.dsig.keyinfo.KeyValue;

/**
 * class to do some constructor method
 * 
 * @author dibao
 *
 */
public class DS_My implements DataStructureADT {
  private class KeyValuePair<K extends Comparable<K>, V> {
    // key the object
    private K key;
    // value
    private V value;

    /**
     * initialize the initial value
     * 
     * @param key the objectkey
     * @param value the value to use
     */
    private KeyValuePair(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  private KeyValuePair[] pairs;
  private int size;


  // TODO may wish to define an inner class
  // for storing key and value as a pair
  // such a class and its members should be "private"

  // Private Fields of the class
  // TODO create field(s) here to store data pairs

  /**
   * initialie variables
   */
  public DS_My() {
    // TODO Auto-generated method stub
    pairs = new KeyValuePair[1000];
    size = 0;

  }

  @Override
  /**
   * insert something into it
   */
  public void insert(Comparable k, Object v) {
    // TODO Auto-generated method stub
    if (k == null)
      throw new IllegalArgumentException("Null Key");
    if (contains(k))
      throw new RuntimeException("Duplicate Key");
    KeyValuePair newpair = new KeyValuePair(k, v);
    pairs[size] = newpair;
    size++;
  }

  @Override
  /**
   * remove special one
   */
  public boolean remove(Comparable k) {
    if (k == null)
      throw new IllegalArgumentException("null keys");
    for (int i = 0; i < size; i++) {
      if (pairs[i].key.equals(k)) {
        // pairs[i].key = null;
        pairs[i] = pairs[size - 1];
        pairs[size - 1] = null;
        // i--;
        size--;
        return true;
      }
    }
    return false;
  }

  @Override
  /**
   * check whether there is special key
   */
  public boolean contains(Comparable k) {
    // TODO Auto-generated method stub
    if (k == null)
      return false;
    for (int i = 0; i < size; i++)
      if (pairs[i].key.equals(k)) {
        return true;
      }
    return false;
  }

  @Override
  /**
   * get the special key value
   */
  public Object get(Comparable k) {
    // TODO Auto-generated method stub
    if (k == null)
      throw new IllegalArgumentException("Null Key");
    for (int i = 0; i < size; i++) {
      // if (pairs[i].key.compareTo(k) > 0)
      if (pairs[i].key.equals(k)) {
        return pairs[i].value;
      }
    }
    return null;
  }

  @Override
  /**
   * return the size of the array
   */
  public int size() {
    // TODO Auto-generated method stub

    return this.size;

  }
}

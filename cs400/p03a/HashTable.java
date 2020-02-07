//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: (p03a )
// Files: (hashtable)
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
//
/**
 * class to do hash table
 * 
 * @author dibao
 *
 * @param <K> key
 * @param <V> value
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
  private K key;
  private V value;
  private int size;
  private int initialcapacity;
  private hashNode<K, V>[] hashEntry;
  private double loadFactorThreshold;

  /**
   * hash node class to make a pair
   * 
   * @author dibao
   *
   * @param <K> key
   * @param <V> value
   */
  private static class hashNode<K, V> {// the node to store in the array
    private K key;
    private V value;
    private hashNode<K, V> nextone;

    private hashNode(K k, V v) {
      key = k;
      value = v;
    }
  }

  // TODO: ADD and comment DATA FIELD MEMBERS needed for your implementation

  // TODO: comment and complete a default no-arg constructor
  /**
   * class to initialize all the variables
   */
  public HashTable() {
    size = 0;
    loadFactorThreshold = 0.75;
    hashEntry = new hashNode[100];
  }

  // TODO: comment and complete a constructor that accepts
  // initial capacity and load factor threshold
  // threshold is the load factor that causes a resize and rehash
  /**
   * constructor to get the initial capacity and load factor threshold
   * 
   * @param initialCapacity the initial capacity for the table
   * @param loadFactorThreshold
   */
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    // check the initial value
    this.initialcapacity = initialCapacity;
    this.size = 0;
    this.loadFactorThreshold = loadFactorThreshold;
    this.hashEntry = new hashNode[initialCapacity];
  }

  /**
   * the construct to insert a key into the hash table if the key is null, throw
   * illegalnullkeyexception get the hash index and add the key use the specific method
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    // TODO Auto-generated method stub
    if (key == null)// key is null
      throw new IllegalNullKeyException();
    int hashIndex = ((Math.abs(key.hashCode()) % hashEntry.length));// use math to compute
                                                                    // hash index
    if (getLoadFactor() >= loadFactorThreshold) {// if the load factor reach the threshold // resize
      resize();
    }
    hashNode<K, V> newNode = new hashNode<K, V>(key, value);// create a new node
    if (hashEntry[hashIndex] == null) {
      hashEntry[hashIndex] = newNode;
      size++;
    } else {
      inserthelp(newNode, hashEntry[hashIndex]);
    }
  }

  /**
   * a help method to help insert
   * 
   * @param currentnode the specific node
   * @param next the next node of this node
   * @return return the last node of linked list
   * @throws DuplicateKeyException when key is dup, throw
   */
  private hashNode<K, V> inserthelp(hashNode<K, V> currentnode, hashNode<K, V> next)
      throws DuplicateKeyException {
    if (currentnode.key.equals(next.key))
      throw new DuplicateKeyException();
    if (next.nextone == null) {
      next.nextone = currentnode;
      size++;
      return next;
    }
    return inserthelp(currentnode, next.nextone);
  }


  /**
   * resize the hash table when the load factor is bigger than the threshold
   * 
   * @throws IllegalNullKeyException key is null
   * @throws DuplicateKeyException key is already exist
   */
  private void resize() throws IllegalNullKeyException, DuplicateKeyException {
    // TODO Auto-generated method stub
    hashNode<K, V>[] oldTable = hashEntry;
    // expand the capacity to the double times itself plus one
    int newCapacity = oldTable.length * 2 + 1;
    hashNode<K, V>[] newTable = new hashNode[newCapacity];
    this.hashEntry = newTable;
    for (int i = 0; i < oldTable.length; i++) {// iterate all the elements in the old array
      if (oldTable[i] != null) {// if the item is exist in the old array put it in the new one
        // put item from old table to the new one
        if (oldTable[i].nextone != null) {
          resizeHelp(oldTable[i].nextone);
        }
        insert(oldTable[i].key, oldTable[i].value);
      }
    }
  }

  /**
   * a helper method to resize the table
   * 
   * @param node the node in the linked list to re add
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   */
  private void resizeHelp(hashNode<K, V> node)
      throws IllegalNullKeyException, DuplicateKeyException {
    // TODO Auto-generated method stub
    if (node.nextone != null) {
      resizeHelp(node.nextone);
    }
    insert(node.key, node.value);
  }

  /**
   * remove the specific key
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    // TODO Auto-generated method stub
    if (key == null) {
      throw new IllegalNullKeyException();
    }
    int index = ((Math.abs(key.hashCode()) % hashEntry.length));
    if (hashEntry[index] == null)
      return false;
    if (!key.equals(hashEntry[index].key)) {
      return rHelper(key, hashEntry[index]);

    } else {
      hashEntry[index] = hashEntry[index].nextone;
      return true;
      // return false;
    }
  }

  /**
   * a help method to do the remove
   * 
   * @param key the key
   * @param node the node to remove
   * @return true when it is removed
   */
  private Boolean rHelper(K key, hashNode<K, V> node) {
    // TODO Auto-generated method stub
    if (node.nextone == null)
      return false;
    if (!node.nextone.key.equals(key))
      rHelper(key, node.nextone);

    else {
      node.nextone = node.nextone.nextone;
    }
    return true;

  }

  /**
   * get the specific key through the table
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    int hashIndex = ((Math.abs(key.hashCode()) % hashEntry.length));
    // if (key.equals(hashArray[hashIndex].key)) {
    // return hashArray[hashIndex].value;
    // }
    hashNode<K, V> temp = hashEntry[hashIndex];
    while (temp != null) {
      if (temp.key.equals(key)) {
        return temp.value;
      }
      temp = temp.nextone;
    }
    if (temp == null)
      throw new KeyNotFoundException();
    return null;
  }

  /**
   * get the size of array
   */
  @Override
  public int numKeys() {
    // TODO Auto-generated method stub
    return size;
  }

  /**
   * get the loadFactorThreshold
   */
  @Override
  public double getLoadFactorThreshold() {
    // TODO Auto-generated method stub
    return loadFactorThreshold;
  }

  /**
   * getLoadFactor
   */
  @Override
  public double getLoadFactor() {
    // TODO Auto-generated method stub
    return size / hashEntry.length;
  }

  /**
   * getCapacity
   */
  @Override
  public int getCapacity() {
    // TODO Auto-generated method stub
    if (hashEntry.length == initialcapacity) {
      return initialcapacity;
    } else {
      return hashEntry.length;
    }
  }

  /**
   * getCollisionResolution using array of linked node so return 5
   */
  @Override
  public int getCollisionResolution() {
    // TODO Auto-generated method stub
    return 5;
  }

}

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: (p02 )
// Files: (BST,BSTTESTS,AVL,AVLTESTS)
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
import java.util.ArrayList; // allowed for creating traversal lists
import java.util.List; // required for returning List<K>

// TODO: Implement a Binary Search Tree class here
public class BST<K extends Comparable<K>, V> implements BSTADT<K, V> {

  // Tip: Use protected fields so that they may be inherited by AVL
  protected BSTNode<K, V> root;
  protected int numKeys; // number of keys in BST

  // Must have a public, default no-arg constructor
  public BST() {
    root = null; // start BST
    numKeys = 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see SearchTreeADT#getPreOrderTraversal()
   */
  @Override
  /**
   * get preorder
   */
  public List<K> getPreOrderTraversal() {
    // TODO Auto-generated method stub
    ArrayList<K> res = new ArrayList<K>();// array to store the value
    return preOrder(res, root);// call the rescursive method
  }

  /**
   * help method
   * 
   * @param res arraylsit
   * @param node the node
   * @return
   */
  List<K> preOrder(ArrayList<K> res, BSTNode<K, V> node) {
    // TODO Auto-generated method stub
    if (node == null) {// node can not be null
      return res;
    }
    res.add(node.key);// add the root key first
    preOrder(res, node.left);
    preOrder(res, node.right);
    return res;
  }

  /*
   * (non-Javadoc)
   * 
   * @see SearchTreeADT#getPostOrderTraversal()
   */
  @Override
  public List<K> getPostOrderTraversal() {
    ArrayList<K> res = new ArrayList<K>();
    return postOrder(res, root);
  }

  /**
   * help method to do post order
   * 
   * @param res arraylsit to store value
   * @param node
   * @return arraylist
   */
  List<K> postOrder(ArrayList<K> res, BSTNode<K, V> node) {
    // TODO Auto-generated method stub
    if (node == null) {
      return res;
    }
    postOrder(res, node.left);
    postOrder(res, node.right);
    res.add(node.key);
    return res;
  }


  /*
   * (non-Javadoc)
   * 
   * @see SearchTreeADT#getLevelOrderTraversal()
   */
  @Override
  public List<K> getLevelOrderTraversal() {
    // TODO Auto-generated method stub
    ArrayList<K> res = new ArrayList<K>();
    ArrayList<BSTNode<K, V>> queue = new ArrayList<BSTNode<K, V>>();
    queue.add(root);
    while (!queue.isEmpty()) {
      BSTNode<K, V> node = queue.get(0);
      queue.remove(0);
      if (node == null)
        continue;
      res.add(node.key);
      queue.add(node.left);
      queue.add(node.right);
    }
    return res;
  }

  /*
   * (non-Javadoc)
   * 
   * @see SearchTreeADT#getInOrderTraversal()
   */
  @Override
  public List<K> getInOrderTraversal() {
    // TODO Auto-generated method stub
    ArrayList<K> res = new ArrayList<K>();
    return inOrder(res, root);
  }

  /**
   * help method to do inorder
   * 
   * @param res array
   * @param node root
   * @return arrayF
   */
  List<K> inOrder(ArrayList<K> res, BSTNode<K, V> node) {
    // TODO Auto-generated method stub
    if (node == null) {
      return res;
    }
    inOrder(res, node.left);
    res.add(node.key);
    inOrder(res, node.right);
    return res;
  }

  /*
   * (non-Javadoc)
   * 
   * @see DataStructureADT#insert(java.lang.Comparable, java.lang.Object)
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    // TODO Auto-generated method stub
    if (key == null)
      throw new IllegalNullKeyException();
    if (root == null) {
      root = new BSTNode<K, V>(key, value, null, null);
      numKeys++;
    } else
      insertaction(root, key, value);

  }

  /**
   * help method to do the insert
   * 
   * @param node the
   * @param key the key to insert
   * @param value the valuye
   * @throws DuplicateKeyException key exist exception
   */
  private void insertaction(BSTNode<K, V> node, K key, V value) throws DuplicateKeyException {
    // TODO Auto-generated method stub
    if (node.key == key)
      throw new DuplicateKeyException();
    if (key.compareTo(node.key) < 0) {
      if (node.left == null) {
        node.left = new BSTNode<K, V>(key, value);
        numKeys++;
      } else
        insertaction(node.left, key, value);
    } else {
      if (node.right == null) {
        node.right = new BSTNode<K, V>(key, value);
        numKeys++;
      } else
        insertaction(node.right, key, value);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see DataStructureADT#remove(java.lang.Comparable)
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {
    // TODO Auto-generated method stub
    if (key == null)
      throw new IllegalNullKeyException();
    if (!contains(key))
      throw new KeyNotFoundException();
    BSTNode<K, V> compare = doremove(root, key);
    root = compare;
    numKeys--;
    return true;
    // return false;
  }

  /**
   * help method to do the remove
   * 
   * @param node the node to remove
   * @param key the key to check
   * @return
   * @throws KeyNotFoundException there is no key exist
   */
  BSTNode<K, V> doremove(BSTNode<K, V> node, K key) throws KeyNotFoundException {
    // TODO Auto-generated method stub
    if (node == null)
      return null;
    if (key.compareTo(node.key) < 0)
      node.left = doremove(node.left, key);
    else if (key.compareTo(node.key) > 0)
      node.right = doremove(node.right, key);
    else {
      if (node.right == null)
        return node.left;
      if (node.left == null)
        return node.right;
      BSTNode<K, V> temp = node;
      node = inordersuceccor(temp.right);
      node.right = removesuceccor(temp.right);
      node.left = temp.left;
    }
    return node;
  }

  /**
   * help method to do the romove in do remove method
   * 
   * @param node the node to do sucesscor
   * @return root
   */
  BSTNode<K, V> removesuceccor(BSTNode<K, V> node) {
    if (node.left == null)
      return node.right;
    node.left = removesuceccor(node.left);
    return node;
  }

  /**
   * help method to find the left most value in the right subtree
   * 
   * @param node the node to find
   * @return return the node
   */
  BSTNode<K, V> inordersuceccor(BSTNode<K, V> node) {
    if (node.left == null)
      return node;
    else
      return inordersuceccor(node.left);
  }

  /*
   * (non-Javadoc)
   * 
   * @see DataStructureADT#get(java.lang.Comparable)
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    // TODO Auto-generated method stub
    BSTNode<K, V> compare = root;
    if (key == null)//null excepton 
      throw new IllegalNullKeyException();
    if (root == null)
      throw new KeyNotFoundException();
    while (!key.equals(compare.key)) {
      if (key.compareTo(compare.key) < 0) {
        compare = compare.left;
      } else if (key.compareTo(compare.key) > 0) {
        compare = compare.right;
      }
      if (compare == null)
        throw new KeyNotFoundException();
    }
    return compare.value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see DataStructureADT#contains(java.lang.Comparable)
   */
  @Override
  public boolean contains(K key) throws IllegalNullKeyException {
    // TODO Auto-generated method stub
    BSTNode<K, V> compare = root;
    if (key == null)
      throw new IllegalNullKeyException();
    if (root == null)
      return false;
    while (!key.equals(compare.key)) {
      if (key.compareTo(compare.key) < 0) {
        compare = compare.left;
      } else if (key.compareTo(compare.key) > 0) {
        compare = compare.right;
      }
      if (compare == null)
        return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see DataStructureADT#numKeys()
   */
  @Override
  public int numKeys() {
    // TODO Auto-generated method stub
    return numKeys;
  }

  /*
   * (non-Javadoc)
   * 
   * @see BSTADT#getKeyAtRoot()
   */
  @Override
  public K getKeyAtRoot() {
    // TODO Auto-generated method stub
    if (root == null) {
      return null;
    } else
      return root.key;
  }

  /*
   * (non-Javadoc)
   * 
   * @see BSTADT#getKeyOfLeftChildOf(java.lang.Comparable)
   */
  @Override
  public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
    // TODO Auto-generated method stub
    BSTNode<K, V> compare = root;
    if (key == null)
      throw new IllegalNullKeyException();
    if (!contains(key))
      throw new KeyNotFoundException();
    while (!key.equals(compare.key)) {
      if (key.compareTo(compare.key) < 0) {
        compare = compare.left;
      } else if (key.compareTo(compare.key) > 0) {
        compare = compare.right;
      }
    }
    return compare.left.key;
  }

  /*
   * (non-Javadoc)
   * 
   * @see BSTADT#getKeyOfRightChildOf(java.lang.Comparable)
   */
  @Override
  public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
    // TODO Auto-generated method stub
    BSTNode<K, V> compare = root;
    if (key == null)
      throw new IllegalNullKeyException();
    if (!contains(key))
      throw new KeyNotFoundException();
    while (!key.equals(compare.key)) {
      if (key.compareTo(compare.key) < 0) {
        compare = compare.left;
      } else if (key.compareTo(compare.key) > 0) {
        compare = compare.right;
      }
    }
    return compare.right.key;
  }

  /*
   * (non-Javadoc)
   * 
   * @see BSTADT#getHeight()
   */
  @Override
  public int getHeight() {
    // TODO Auto-generated method stub
    int front = -1, behind = -1;
    int parent = 0, h = 0;
    BSTNode<K, V>[] array = new BSTNode[10000];
    if (root == null) {
      return 0;
    }
    array[++behind] = root;
    BSTNode<K, V> p;
    while (front < behind) {
      p = array[++front];
      if (p.left != null) {
        array[++behind] = p.left;
      }
      if (p.right != null) {
        array[++behind] = p.right;
      }
      if (front == parent) {
        h++;
        parent = behind;
      }
    }
    return h;
  }
}

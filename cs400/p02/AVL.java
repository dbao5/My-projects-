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
import java.util.ArrayList;
import java.util.List;

// A BST search tree that maintains its balance using AVL rotations.
public class AVL<K extends Comparable<K>, V> extends BST<K, V> {

  // must add rebalancing to BST via rotate operations

  @Override
  /**
   * insert a pair of key into the avl tree
   */
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    super.insert(key, value);


    if (key.compareTo(root.key) < 0) {// compare the key and root value
      if (getHeight(root.left) - getHeight(root.right) == 2) {// not balacne
        if (key.compareTo(root.left.key) < 0)
          root = leftRotation(root);// left rotate
        else {
          root.left = rightRotation(root.left);
          root = leftRotation(root);
        }
      }
    } else if (key.compareTo(root.key) > 0) {
      if (getHeight(root.right) - getHeight(root.left) == 2) {// not balcne
        if (key.compareTo(root.right.key) > 0)
          root = rightRotation(root);// right rotate
        else {
          root.right = leftRotation(root.right);
          root = rightRotation(root);
        }
      }
    }
  }

  @Override
  /**
   * method to remove the node
   */
  public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {

    if (key.compareTo(root.key) < 0) {// key is small
      super.remove(key);//
      if (getHeight(root.right) - getHeight(root.left) == 2) {// not balacne
        BSTNode<K, V> r = root.right;// store the right node
        if (getHeight(r.left) > getHeight(r.right)) {// compare
          root.right = leftRotation(root.right);// retate
          root = rightRotation(root);
        } else
          root = rightRotation(root);
      }
    } else if (key.compareTo(root.key) > 0) {
      super.remove(key);
      if (getHeight(root.left) - getHeight(root.right) == 2) {// not balcne
        BSTNode<K, V> l = root.left;
        if (getHeight(l.right) > getHeight(l.left)) {
          root.left = rightRotation(root.left);// left retotate
          root = leftRotation(root);
        } else
          root = leftRotation(root);
      }
    } else {
      if ((root.left != null) && (root.right != null)) {
        if (getHeight(root.left) > getHeight(root.right)) {
          BSTNode<K, V> rightnode = rightmost(root.left);// find the precessor of right node
          root.key = rightnode.key;
          super.remove(rightnode.key);
        } else {
          BSTNode<K, V> leftnode = inordersuceccor(root.right);// find the left most node
          root.key = leftnode.key;
          super.remove(leftnode.key);
        }
      } else {
        root = (root.left != null) ? root.left : root.right;
      }
    }
    return true;
  }

  /**
   * help method the find the right most node
   * 
   * @param node
   * @return node
   */
  BSTNode<K, V> rightmost(BSTNode<K, V> node) {
    if (node.right == null)
      return node;
    else
      return rightmost(node.right);// recursive call
  }

  /**
   * help method the find the left most node
   */
  BSTNode<K, V> inordersuceccor(BSTNode<K, V> node) {
    if (node.left == null)
      return node;
    else
      return inordersuceccor(node.left);// recursive call
  }

  /**
   * method to find the bigger one between 2
   * 
   * @param a an integer
   * @param b another integer
   * @return bigger one
   */
  private int findmax(int a, int b) {
    return a > b ? a : b;
  }



  /**
   * method to do the left retoration
   * 
   * @param node
   * @return
   */
  private BSTNode<K, V> leftRotation(BSTNode<K, V> node) {
    BSTNode<K, V> parent;// set the root
    parent = node.left;//
    node.left = parent.right;//
    parent.right = node;

    node.height = findmax(getHeight(node.left), getHeight(node.right)) + 1;
    parent.height = findmax(getHeight(parent.left), node.height) + 1;

    return parent;
  }

  /**
   * method to do the right retotation
   * 
   * @param node
   * @return
   */
  private BSTNode<K, V> rightRotation(BSTNode<K, V> node) {
    BSTNode<K, V> parent;
    parent = node.right;
    node.right = parent.left;// set the middle one's right to the left of grandparent
    parent.left = node;

    node.height = findmax(getHeight(node.left), getHeight(node.right)) + 1;
    parent.height = findmax(getHeight(parent.right), node.height) + 1;

    return parent;
  }

  /**
   * when the bf is not <= 1 than do the reblancing
   * 
   * @return
   * @throws IllegalNullKeyException
   * @throws KeyNotFoundException
   */
  public BSTNode<K, V> rebalancing() throws IllegalNullKeyException, KeyNotFoundException {
    List<K> res = new ArrayList<K>();
    res = getInOrderTraversal();
    int size = res.size();
    root = doreblance(res, 0, size - 1);
    return root;
  }

  /**
   * help method to do the reblacning
   * 
   * @param keys
   * @param beginning the beginning of the keys
   * @param last the last one of the keys
   * @return
   * @throws IllegalNullKeyException
   * @throws KeyNotFoundException
   */
  private BSTNode<K, V> doreblance(List<K> keys, int beginning, int last)
      throws IllegalNullKeyException, KeyNotFoundException {
    // TODO Auto-generated method stub
    if (beginning > last)
      return null;
    int mid = (beginning + last) / 2;
    BSTNode<K, V> node = new BSTNode<K, V>(keys.get(mid), get(keys.get(mid)));
    node.left = doreblance(keys, beginning, mid - 1);
    node.right = doreblance(keys, mid + 1, last);
    return node;
  }

  /**
   * method to get the height like in bst
   * 
   * @param node
   * @return
   */
  public int getHeight(BSTNode<K, V> node) {
    // TODO Auto-generated method stub
    int front = -1, behind = -1;
    int parent = 0, h = 0;
    BSTNode<K, V>[] queue = new BSTNode[10000];
    if (node == null) {
      return 0;
    }
    queue[++behind] = node;
    BSTNode<K, V> p;
    while (front < behind) {
      p = queue[++front];
      if (p.left != null) {
        queue[++behind] = p.left;
      }
      if (p.right != null) {
        queue[++behind] = p.right;
      }
      if (front == parent) {
        h++;
        parent = behind;
      }
    }
    return h;
  }
}

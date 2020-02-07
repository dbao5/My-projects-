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
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


// TODO: Add tests to test that binary search tree operations work

public class BSTTest extends DataStructureADTTest {

  BST<String, String> bst;
  BST<Integer, String> bst2;

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    // The setup must initialize this class's instances
    // and the super class instances.
    // Because of the inheritance between the interfaces and classes,
    // we can do this by calling createInstance() and casting to the desired type
    // and assigning that same object reference to the super-class fields.
    dataStructureInstance = bst = createInstance();
    dataStructureInstance2 = bst2 = createInstance2();
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {
    dataStructureInstance = bst = null;
    dataStructureInstance2 = bst2 = null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see DataStructureADTTest#createInstance()
   */
  @Override
  protected BST<String, String> createInstance() {
    return new BST<String, String>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see DataStructureADTTest#createInstance2()
   */
  @Override
  protected BST<Integer, String> createInstance2() {
    return new BST<Integer, String>();
  }

  /**
   * Test that empty trees still produce a valid but empty traversal list for each of the four
   * traversal orders.
   */
  @Test
  void testBST_001_empty_traversal_orders() {
    try {

      List<String> expectedOrder = new ArrayList<String>();

      // Get the actual traversal order lists for each type
      List<String> inOrder = bst.getInOrderTraversal();
      List<String> preOrder = bst.getPreOrderTraversal();
      List<String> postOrder = bst.getPostOrderTraversal();
      List<String> levelOrder = bst.getLevelOrderTraversal();

      // UNCOMMENT IF DEBUGGING THIS TEST
      System.out.println("   EXPECTED: " + expectedOrder);
      System.out.println("   In Order: " + inOrder);
      System.out.println("  Pre Order: " + preOrder);
      System.out.println(" Post Order: " + postOrder);
      System.out.println("Level Order: " + levelOrder);

      assertEquals(expectedOrder, inOrder);
      assertEquals(expectedOrder, preOrder);
      assertEquals(expectedOrder, postOrder);
      assertEquals(expectedOrder, levelOrder);

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 002: " + e.getMessage());
    }

  }

  /**
   * Test that trees with one key,value pair produce a valid traversal lists for each of the four
   * traversal orders.
   */
  @Test
  void testBST_002_check_traversals_after_insert_one() {

    try {

      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      bst2.insert(10, "ten");
      if (bst2.numKeys() != 1)
        fail("added 10, size should be 1, but was " + bst2.numKeys());

      List<Integer> inOrder = bst2.getInOrderTraversal();
      List<Integer> preOrder = bst2.getPreOrderTraversal();
      List<Integer> postOrder = bst2.getPostOrderTraversal();
      List<Integer> levelOrder = bst2.getLevelOrderTraversal();

      // UNCOMMENT IF DEBUGGING THIS TEST
      System.out.println("   EXPECTED: " + expectedOrder);
      System.out.println("   In Order: " + inOrder);
      System.out.println("  Pre Order: " + preOrder);
      System.out.println(" Post Order: " + postOrder);
      System.out.println("Level Order: " + levelOrder);

      assertEquals(expectedOrder, inOrder);
      assertEquals(expectedOrder, preOrder);
      assertEquals(expectedOrder, postOrder);
      assertEquals(expectedOrder, levelOrder);

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 003: " + e.getMessage());
    }

  }


  /**
   * Test that the in-order traversal order is correct if the items are entered in a way that
   * creates a balanced BST
   * 
   * Insert order: 20-10-30 In-Order traversal order: 10-20-30
   */
  @Test
  void testBST_003_check_inOrder_for_balanced_insert_order() {
    // insert 20-10-30 BALANCED
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected inOrder 10 20 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10); // L
      expectedOrder.add(20); // V
      expectedOrder.add(30); // R

      // GET IN-ORDER and check
      List<Integer> actualOrder = bst2.getInOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * Test that the pre-order traversal order is correct if the items are entered in a way that
   * creates a balanced BST
   * 
   * Insert order: 20-10-30 Pre-Order traversal order: 20-10-30
   */
  @Test
  void testBST_004_check_preOrder_for_balanced_insert_order() {
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected inOrder 20 10 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(20); // L
      expectedOrder.add(10); // V
      expectedOrder.add(30); // R

      // GET pre-ORDER and check
      List<Integer> actualOrder = bst2.getPreOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 005: " + e.getMessage());
    }

  }

  /**
   * Test that the post-order traversal order is correct if the items are entered in a way that
   * creates a balanced BST
   * 
   * Insert order: 20-10-30 Post-Order traversal order: 10-30-20
   */
  @Test
  void testBST_005_check_postOrder_for_balanced_insert_order() {
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected inOrder 10 20 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10); // L
      expectedOrder.add(30); // V
      expectedOrder.add(20); // R

      // GET post-ORDER and check
      List<Integer> actualOrder = bst2.getPostOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 006: " + e.getMessage());
    }
    // TODO implement this test

  }

  /**
   * Test that the level-order traversal order is correct if the items are entered in a way that
   * creates a balanced BST
   * 
   * Insert order: 20-10-30 Level-Order traversal order: 20-10-30
   */
  @Test
  void testBST_006_check_levelOrder_for_balanced_insert_order() {
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected inOrder 10 20 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(20); // L
      expectedOrder.add(10); // V
      expectedOrder.add(30); // R

      // GET lever-ORDER and check
      List<Integer> actualOrder = bst2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 007: " + e.getMessage());
    }

  }

  /**
   * Test that the in-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 In-Order traversal order: 10-20-30
   */
  @Test
  void testBST_007_check_inOrder_for_not_balanced_insert_order() {

    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected inOrder 10 20 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10); // L
      expectedOrder.add(20); // V
      expectedOrder.add(30); // R

      // GET in-ORDER and check
      List<Integer> actualOrder = bst2.getInOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 008: " + e.getMessage());
    }

  }

  /**
   * Test that the pre-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 Pre-Order traversal order: 10-20-30
   */
  @Test
  void testBST_008_check_preOrder_for_not_balanced_insert_order() {

    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected inOrder 20 10 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10); // L
      expectedOrder.add(20); // V
      expectedOrder.add(30); // R

      // GET pre-ORDER and check
      List<Integer> actualOrder = bst2.getPreOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 009: " + e.getMessage());
    }

  }

  /**
   * Test that the post-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 Post-Order traversal order: 30-20-10
   */
  @Test
  void testBST_009_check_postOrder_for_not_balanced_insert_order() {

    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected inOrder 10 20 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(30); // L
      expectedOrder.add(20); // V
      expectedOrder.add(10); // R

      // GET post-ORDER and check
      List<Integer> actualOrder = bst2.getPostOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 010: " + e.getMessage());
    }

  }

  /**
   * Test that the level-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 Level-Order traversal order: 10-20-30 (FIXED ON 2/14/18)
   */
  @Test
  void testBST_010_check_levelOrder_for_not_balanced_insert_order() {

    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // 10 20 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10); // L
      expectedOrder.add(20); // V
      expectedOrder.add(30); // R

      // GET lever-ORDER and check
      List<Integer> actualOrder = bst2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 011: " + e.getMessage());
    }

  }


  // TODO: Add your own tests

  // Add tests to make sure that get and remove work as expected.
  /**
   * test the insert and get method
   * 
   */
  @Test
  void testBST_011_insert_two_get_one() {
    try {
      bst2.insert(10, "a");// insert 10 - 20
      bst2.insert(20, "b");

      if (bst.get("a") != "10") {// call get
        fail("get a should return 10, but it doesn't");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 012: " + e.getMessage());
    }
  }

  /**
   * test insert_one_remove_one_height_should_be_zero
   */
  @Test
  void testBST_012_insert_one_remove_one_height_should_be_zero() {
    try {
      bst2.insert(10, "a");// insert 10
      bst2.remove(10);
      if (bst.getHeight() != 0) {
        fail("remove one after insert one in it the height of tree should be 0 but it is not ");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 013: " + e.getMessage());
    }
  }

  /**
   * testBST013_check_unbalcend_tree_height
   * 
   */
  @Test
  void testBST013_check_unbalcend_tree_height() {
    try {
      bst2.insert(10, "a");// insert 10
      bst2.insert(20, "b");
      bst2.insert(30, "c");// height should be 3

      if (bst.getHeight() != 3) {
        fail("after insert three unbalcned node, the height should be 3 but it is not ");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 014: " + e.getMessage());
    }
  }

  /**
   * testBST014_check_balanced_tree_height()
   */
  @Test
  void testBST014_check_balanced_tree_height() {
    try {
      bst2.insert(20, "a");
      bst2.insert(10, "b");
      bst2.insert(30, "c");
      // height should be 2

      if (bst.getHeight() != 2) {
        fail("after insert three balcned node, the height should be 2 but it is not ");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 015: " + e.getMessage());
    }
  }

  // Does the height of the tree reflect it's actual and its expected height?
  // Use the traversal orders to check.

  // Can you insert many and still "get" them back out?
  /**
   * test insert_many_and_still_get_one
   */
  @Test
  void testBST015_insert_many_and_still_get_one() {
    try {
      bst2.insert(20, "a");
      bst2.insert(10, "b");
      bst2.insert(30, "c");
      bst2.insert(40, "d");
      bst2.insert(50, "e");
      bst2.insert(60, "f");

      if (bst.get("f") != "60") {
        fail("after insert many nodes, get method can not return correctly ");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 016: " + e.getMessage());
    }
  }

  /**
   * testBST016_test_inorder_precessor_method()F
   */
  @Test
  void testBST016_test_inorder_precessor_method() {
    try {
      bst2.insert(40, "a");
      bst2.insert(20, "b");
      bst2.insert(10, "c");
      bst2.insert(30, "d");
      bst2.insert(50, "e");

      if (!bst2.inordersuceccor(bst2.root).equals(30)) {
        fail("after insert many nodes, get method can not return correctly ");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 017: " + e.getMessage());
    }
  }

  // Does delete work?
  /**
   * testBST017_test_insert_a_node_with_two_child_and_remove_the_leaf()
   */
  @Test
  void testBST017_test_insert_a_node_with_two_child_and_remove_the_leaf() {
    try {
      bst2.insert(40, "a");
      bst2.insert(20, "b");
      bst2.insert(50, "c");
      bst2.insert(10, "d");
      bst2.insert(30, "e");
      bst2.remove(20);

      if (!bst2.getKeyOfRightChildOf(40).equals(30)) {
        fail("after delete a node with two children, the node should be its right child ");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 018: " + e.getMessage());
    }
  }

  // When the key is a leaf? node with one left child?
  // node with one right child? node with two children?
  /**
   * testBST018_delete_a_node_whith_one_child()
   * 
   */
  @Test
  void testBST018_delete_a_node_whith_one_child() {
    try {
      bst2.insert(40, "a");
      bst2.insert(20, "b");
      bst2.insert(50, "c");
      bst2.insert(10, "d");
      bst2.remove(20);

      if (!bst2.getKeyOfRightChildOf(40).equals(10)) {
        fail("after delete a node with one children, the node should be its only child ");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 019: " + e.getMessage());
    }
  }
  // Write replacement value did you choose?
  // in-order precessor? in-order successor?
  // How can you test if it is replaced correctly?



}

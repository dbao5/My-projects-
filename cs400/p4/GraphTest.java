/**
 * Filename: graphtest 
 * Project: p4 
 * Authors: DI BAO, XIAOYU LIU
 * Email: dbao5@wisc.edu, xliu725@wisc.edu
 */
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest {
  Graph graph;
  Set<String> vertice = new HashSet<String>();

  @BeforeEach
  void setUp() throws Exception {
    graph = new Graph();
  }

  @AfterEach
  void tearDown() throws Exception {
    graph = null;
    vertice.clear();
  }

  /**
   * add a single vertex
   */
  @Test
  void test00_add_a_not_null_vertex() {
    vertice.add("1");
    graph.addVertex("1");
    if (!vertice.equals(graph.getAllVertices())) {
      fail("fail to add a not null vertex");
    }
  }

  /**
   * add two vertex
   */
  @Test
  void test01_add_two_non_null_vertex() {
    vertice.add("1");
    vertice.add("2");
    graph.addVertex("1");
    graph.addVertex("2");
    if (!vertice.equals(graph.getAllVertices())) {
      fail("fail to add two non-null vertex");
    }
  }

  /**
   * add two vertex and remove one
   */
  @Test
  void test02_add_two_remove_one() {
    vertice.add("1");
    graph.addVertex("1");
    graph.addVertex("2");
    graph.removeVertex("2");

    if (!vertice.equals(graph.getAllVertices())) {
      fail("remove one node failed");
    }
  }

  /**
   * add an edge
   */
  @Test
  void test03_add_an_edge() {
    graph.addVertex("1");
    graph.addVertex("2");
    graph.addEdge("1", "2");
    if (graph.size() != 1) {
      fail("add edge failed");
    }
  }

  /**
   * add two edges
   */
  @Test
  void test04_add_two_edges() {
    graph.addVertex("1");
    graph.addVertex("2");
    graph.addVertex("3");
    graph.addEdge("1", "2");
    graph.addEdge("1", "3");

    if (graph.size() != 2) {
      fail("add two edges failed");
    }
  }

  /**
   * add one edge and remove one
   */
  @Test
  void test05_add_one_edge_remove_edge() {
    graph.addVertex("1");
    graph.addVertex("2");
    graph.addVertex("3");
    graph.addEdge("1", "2");
    graph.addEdge("1", "3");
    graph.removeEdge("1", "2");

    if (graph.size() != 1) {
      fail("remove edge failed");
    }
  }

  /**
   * check the adjvertices
   */
  @Test
  void test06_add_adjvertices() {
    graph.addVertex("1");
    graph.addVertex("2");
    graph.addVertex("3");
    graph.addEdge("1", "2");
    graph.addEdge("1", "3");


    if (graph.getAdjacentVerticesOf("1").size() != 2) {
      fail("adjacent list failed");
    }
  }

  /**
   * check the adj after remove
   */
  @Test
  void test07_remove_one_edge() {
    graph.addVertex("1");
    graph.addVertex("2");
    graph.addVertex("3");
    graph.addEdge("1", "2");
    graph.addEdge("1", "3");
    graph.removeEdge("1", "2");
    if (graph.getAdjacentVerticesOf("1").size() != 1) {
      fail("adjacent list remove failed");
    }

  }
}

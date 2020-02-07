import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Filename: Graph.java 
 * Project: p4 
 * Authors: DI BAO, XIAOYU LIU
 * Course: cs400 
 * Email: dbao5@wisc.edu, xliu725@wisc.edu
 * 
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {

  /*
   * Default no-argument constructor
   */
  // we need a inner class to make the graph node to appear on the screen
  class GraphNode {
    // Initialize all the elements that needs in the graph
    // and type of vertex is string, using array list
    private String vertexName;
    private ArrayList<GraphNode> adjacentList;

    /**
     * select a staring vertex and then create the adjacent list of this node
     * 
     * @param vertex the starting vertex
     */
    private GraphNode(String vertex) {
      this.vertexName = vertex;
      adjacentList = new ArrayList<GraphNode>();
    }

    /**
     * for each node,add an edge between them if the current node is in their adjacent list, or the
     * node is null, do nothing
     * 
     * @param V the node to think of
     */
    private void addEdge(GraphNode V) {
      if ((V != null) && (!(adjacentList.contains(V)))) {
        adjacentList.add(V);
      }
    }

    /**
     * For node v, if there is an edge between the current node and this node, then remove the node
     * if the node is null and there is no edge, do nothing
     * 
     * @param V
     */
    private void removeEdge(GraphNode V) {
      if ((V != null) && (adjacentList.contains(V))) {
        adjacentList.remove(V);
      }
    }

    /**
     * compare the two node is same or not
     * 
     * @param V the object to compare
     * @return true if the name is same and false otherwise
     */
    private boolean sameNode(Object V) {
      if (V instanceof GraphNode) {
        GraphNode B = (GraphNode) V;
        if (B.vertexName.equals(this.vertexName)) {
          return true;
        } else {
          return false;
        }
      }
      return false;
    }


  }

  private List<GraphNode> vertexList;

  public Graph() {
    vertexList = new ArrayList<Graph.GraphNode>();
  }

  /**
   * Add new vertex to the graph.
   *
   * If vertex is null or already exists, method ends without adding a vertex or throwing an
   * exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in the graph
   */
  public void addVertex(String vertex) {
    if (vertex == null)
      return;
    addVertexHelp(vertex);
  }

  private boolean contains(String vertex) {
    GraphNode check = new GraphNode(vertex);
    for (GraphNode v : vertexList) {
      if (v.vertexName.equals(check.vertexName)) {
        return true;
      }
    }
    return false;

  }

  /**
   * a help method to add the vertex to the list
   * 
   * @param node
   */
  private void addVertexHelp(String node) {
    GraphNode V = new GraphNode(node);
    if (vertexList.contains(V)) {
      return;
    } else {
      vertexList.add(V);
    }
  }

  /**
   * Remove a vertex and all associated edges from the graph.
   * 
   * If vertex is null or does not exist, method ends without removing a vertex, edges, or throwing
   * an exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in the graph
   */
  public void removeVertex(String vertex) {
    if ((vertex == null)) {
      return;
    } else if ((!contains(vertex))) {
      return;
    } else {
      removehelp(vertex);
    }
  }

  /**
   * a help method to do the remove
   * 
   * @param node the node to remove
   */
  private void removehelp(String node) {
    GraphNode V = getVertexhelp(node);
    vertexList.remove(V);
    for (GraphNode removeedge : vertexList) {
      removeedge.removeEdge(V);
    }
  }

  /**
   * Add the edge from vertex1 to vertex2 to this graph. (edge is directed and unweighted) If either
   * vertex does not exist, add vertex, and add edge, no exception is thrown. If the edge exists in
   * the graph, no edge is added and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in the graph 3. the
   * edge is not in the graph
   */
  public void addEdge(String vertex1, String vertex2) {
    try {
      addEdgehelp(vertex1, vertex2);
    } catch (Exception e) {
      return;
    }
  }

  /**
   * a help method to addEdge between this two node
   * 
   * @param begin the start node
   * @param end the end node
   */
  private void addEdgehelp(String vertex1, String vertex2) {
    if ((!contains(vertex1))) {
      addVertex(vertex1);;
    } else if (!contains(vertex2)) {
      addVertex(vertex2);
    }
    GraphNode begin = getVertexhelp(vertex1);
    GraphNode end = getVertexhelp(vertex2);
    if ((begin == null) || (end == null)) {
      return;
    } else if ((begin.sameNode(end))) {
      return;
    } else {
    }
    begin.addEdge(end);
  }

  /**
   * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed and unweighted) If
   * either vertex does not exist, or if an edge from vertex1 to vertex2 does not exist, no edge is
   * removed and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in the graph 3. the
   * edge from vertex1 to vertex2 is in the graph
   */
  public void removeEdge(String vertex1, String vertex2) {
    try {
      GraphNode begin = getVertexhelp(vertex1);
      GraphNode end = getVertexhelp(vertex2);
      removeEdgehelp(begin, end);
    } catch (Exception e) {
      return;
    }
  }

  /**
   * help method to remove edge
   * 
   * @param begin the start vertex
   * @param end end vertex
   */
  private void removeEdgehelp(GraphNode begin, GraphNode end) {
    if ((begin == null) || (end == null) || (begin.sameNode(end))) {
      return;
    } else {
      begin.removeEdge(end);
    }
  }

  /**
   * Returns a Set that contains all the vertices
   * 
   */
  public Set<String> getAllVertices() {
    Set<String> vertexSet = new HashSet<String>();
    // for all instance in the vertexlist
    // add it to the set
    for (GraphNode V : vertexList) {
      vertexSet.add(V.vertexName);
    }
    return vertexSet;
  }

  /**
   * Get all the neighbor (adjacent) vertices of a vertex
   *
   */
  public List<String> getAdjacentVerticesOf(String vertex) {
    // finf the specific vertex and make it in the form of node
    GraphNode Node = getVertexhelp(vertex);
    List<String> Adjacent = new ArrayList<String>();
    // create an array list so that the adjacent node can be added into it
    for (GraphNode node : Node.adjacentList) {
      // add the specific vertex's adjacent node into the list
      Adjacent.add(node.vertexName);
    }
    return Adjacent;
  }

  /**
   * find the vertex in the vertexList
   * 
   * @param vertex the vertex to find
   * @return if the vertex name paired, return that node and other wise return null
   */
  private GraphNode getVertexhelp(String vertex) {
    for (GraphNode v : vertexList) {
      if (v.vertexName.equals(vertex))
        return v;
    }
    return null;

  }

  /**
   * Returns the number of edges in this graph. for each node in the vertex list, if the vertex has
   * adjacent node, and the number of adjacents
   */
  public int size() {
    if (vertexList.isEmpty()) {
      return 0;
    } else {
      int size = 0;
      for (GraphNode v : vertexList) {
        size += v.adjacentList.size();
      }
      return size;
    }

  }

  /**
   * Returns the number of vertices in this graph.
   */
  public int order() {

    return this.vertexList.size();
  }
}

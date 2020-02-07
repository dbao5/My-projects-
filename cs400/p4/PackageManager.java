import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Filename: PackageManager.java 
 * Project: p4 
 * Authors: DI BAO, XIAOYU LIU
 * Email: dbao5@wisc.edu, xliu725@wisc.edu
 * 
 * PackageManager is used to process json package dependency files and provide function that make
 * that information available to other users.
 * 
 * Each package that depends upon other packages has its own entry in the json file.
 * 
 * Package dependencies are important when building software, as you must install packages in an
 * order such that each package is installed after all of the packages that it depends on have been
 * installed.
 * 
 * For example: package A depends upon package B, then package B must be installed before package A.
 * 
 * This program will read package information and provide information about the packages that must
 * be installed before any given package can be installed. all of the packages in
 * 
 * You may add a main method, but we will test all methods with our own Test classes.
 */

public class PackageManager {

  private Graph graph;

  /*
   * Package Manager default no-argument constructor.
   */
  public PackageManager() {
    graph = new Graph();

  }

  /**
   * Takes in a file path for a json file and builds the package dependency graph from it.
   * 
   * @param jsonFilepath the name of json data file with package dependency information
   * @throws FileNotFoundException if file path is incorrect
   * @throws IOException if the give file cannot be read
   * @throws ParseException if the given json cannot be parsed
   */
  public void constructGraph(String jsonFilepath)
      throws FileNotFoundException, IOException, ParseException {
    try {
      Object object = new JSONParser().parse(new FileReader(jsonFilepath));
      // create an jason object from the file
      JSONObject jasonObject = (JSONObject) object;
      // get the object arrays from the jasonobject
      JSONArray packages = (JSONArray) jasonObject.get("packages");
      // get all the verteices from the packages
      for (int i = 0; i < packages.size(); i++) {
        JSONObject vertex = (JSONObject) packages.get(i);
        String vertexName = (String) vertex.get("name");
        // add the specific vertex into the graph
        graph.addVertex(vertexName);
        JSONArray adjacentList = (JSONArray) vertex.get("dependencies");
        for (int j = 0; j < adjacentList.size(); j++) {
          String Add = (String) adjacentList.get(j);
          graph.addVertex(Add);
          graph.addEdge(vertexName, Add);
        }
      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException();
    } catch (IOException e) {
      throw new IOException();
    } catch (ParseException e) {
      throw new ParseException(0);
    }
  }

  /**
   * Helper method to get all packages in the graph.
   * 
   * @return Set<String> of all the packages
   */
  public Set<String> getAllPackages() {
    return graph.getAllVertices();
  }

  /**
   * Given a package name, returns a list of packages in a valid installation order.
   * 
   * Valid installation order means that each package is listed before any packages that depend upon
   * that package.
   * 
   * @return List<String>, order in which the packages have to be installed
   * 
   * @throws CycleException if you encounter a cycle in the graph while finding the installation
   *         order for a particular package. Tip: Cycles in some other part of the graph that do not
   *         affect the installation order for the specified package, should not throw this
   *         exception.
   * 
   * @throws PackageNotFoundException if the package passed does not exist in the dependency graph.
   */
  public List<String> getInstallationOrder(String pkg)
      throws CycleException, PackageNotFoundException {
    // if the pkg is not in the graph, throw exception
    if (!graph.getAllVertices().contains(pkg)) {
      throw new PackageNotFoundException();
    }
    // create a stack, linkedlist, and the order list to track
    Stack<String> stack = new Stack<>();
    Queue<String> visited = new LinkedList<>();
    List<String> DFSOrder = new LinkedList<>();

    stack.push(pkg);
    while (!stack.isEmpty()) {
      // if the stack is not empty, then make the current be the top of the stack
      String current = stack.pop();
      if ((graph.getAdjacentVerticesOf(current).isEmpty())
          || (visited.containsAll(graph.getAdjacentVerticesOf(current)))) {
        // if the current vertex has no adjacent vertex or all the
        // adjacent vertexs are visited
        // DFS order
        DFSOrder.add(current);
      } else {
        if (!DFSOrder.contains(current)) {
          // if the current vertex is not in the list then mark it as visited
          visited.add(current);
          DFSOrder.add(current);
          // for all the adjascent vertex of current, if the adjascent node did not be visited, then
          // push it into the stack
          for (String neibourNode : graph.getAdjacentVerticesOf(current)) {
            if (!visited.contains(neibourNode)) {
              stack.push(neibourNode);
            } else {
              // otherwise will form a cycle.
              throw new CycleException();
            }
          }
        }
      }
    }
    Collections.reverse(DFSOrder);
    DFSOrder = DFSOrder.stream().distinct().collect(Collectors.toList());
    return DFSOrder;
  }

  /**
   * Given two packages - one to be installed and the other installed, return a List of the packages
   * that need to be newly installed.
   * 
   * For example, refer to shared_dependecies.json - toInstall("A","B") If package A needs to be
   * installed and packageB is already installed, return the list ["A", "C"] since D will have been
   * installed when B was previously installed.
   * 
   * @return List<String>, packages that need to be newly installed.
   * 
   * @throws CycleException if you encounter a cycle in the graph while finding the dependencies of
   *         the given packages. If there is a cycle in some other part of the graph that doesn't
   *         affect the parsing of these dependencies, cycle exception should not be thrown.
   * 
   * @throws PackageNotFoundException if any of the packages passed do not exist in the dependency
   *         graph.
   */
  public List<String> toInstall(String newPkg, String installedPkg)
      throws CycleException, PackageNotFoundException {
    // any of the new package or installed packge does not exist will cause exception
    if ((!graph.getAllVertices().contains(newPkg))
        || (!graph.getAllVertices().contains(installedPkg))) {
      throw new PackageNotFoundException();
    }
    // store all the new package into a list and if the package already installed, remove it
    List<String> order = getInstallationOrder(newPkg);
    order.remove(installedPkg);
    for (String pkg : getInstallationOrder(installedPkg)) {
      order.remove(pkg);
    }
    return order;
  }

  /**
   * Return a valid global installation order of all the packages in the dependency graph.
   * 
   * assumes: no package has been installed and you are required to install all the packages
   * 
   * returns a valid installation order that will not violate any dependencies
   * 
   * @return List<String>, order in which all the packages have to be installed
   * @throws CycleException if you encounter a cycle in the graph
   */
  public List<String> getInstallationOrderForAllPackages() throws CycleException {
    // packages are the set of all packages and there stores all the verteices in it and the
    // insatall order is the list to add the correct order of the graph
    Set<String> packages = getAllPackages();
    List<String> vertexList = new ArrayList<String>(packages);
    List<String> installorder = new ArrayList<>();
    // the list to show the correct order
    try {

      installorder.addAll(getInstallationOrder(vertexList.get(0)));
    } catch (PackageNotFoundException e) {
      e.printStackTrace();
    }
    for (int i = 1; i < vertexList.size(); i++) {
      // travel all the vertices in the list
      try {
        // store the pair that need to install into the list
        List<String> toInstall;
        toInstall = toInstall(vertexList.get(i), vertexList.get(0));
        for (String vt : toInstall) {
          // add the vertex that not in the install order
          if (!installorder.contains(vt)) {
            installorder.add(vt);
          }
        }
      } catch (PackageNotFoundException e) {
        e.printStackTrace();
      }
    }
    return installorder;
  }

  /**
   * Find and return the name of the package with the maximum number of dependencies.
   * 
   * Tip: it's not just the number of dependencies given in the json file. The number of
   * dependencies includes the dependencies of its dependencies. But, if a package is listed in
   * multiple places, it is only counted once.
   * 
   * Example: if A depends on B and C, and B depends on C, and C depends on D. Then, A has 3
   * dependencies - B,C and D.
   * 
   * @return String, name of the package with most dependencies.
   * @throws CycleException if you encounter a cycle in the graph
   */
  public String getPackageWithMaxDependencies() throws CycleException {
    // an integer array that counts the frequency of the vertices
    int[] times = new int[getAllPackages().size()];
    Set<String> packages = getAllPackages();
    List<String> vertexList = new ArrayList<String>(packages);

    int max = 0;
    int index = 0;
    int size = getAllPackages().size();
    for (int i = 0; i < size; i++) {
      try {
        // assigin the vertex frequence of a vertex in the vertexlist to times array
        times[i] = getInstallationOrder(vertexList.get(i)).size();
        // if the number of times of a vertex is bigger than the max frequence, make the max to that
        // vertex
        if (times[i] > max) {
          max = times[i];
          index = i;
        }
      } catch (PackageNotFoundException e) {
        e.printStackTrace();
      }
    }
    return vertexList.get(index);
  }

  public static void main(String[] args) {
    System.out.println("PackageManager.main()");
  }

}

/**
 * Filename: PackageManagertest.java 
 * Project: p4 
 * Authors: DI BAO, XIAOYU LIU
 * Email: dbao5@wisc.edu, xliu725@wisc.edu
 * 
 * test the constructors in the manager class
 */
import static org.junit.Assert.fail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Class to test the PackageManager
 */
public class PackageManagerTest {
  private PackageManager packageA;

  @Before
  public void setUp() throws Exception {
    packageA = new PackageManager();
  }

  @After
  public void tearDown() throws Exception {
    packageA = null;
  }

  /**
   * Test constructGraph() method
   */
  @Test
  public void test00_ConstructGraph_a_valid_jason() {
    try {
      packageA.constructGraph("valid.json");
    } catch (FileNotFoundException e) {
      fail("fail to test constructGraph() method ");
      e.printStackTrace();
    } catch (IOException e) {
      fail("faile to test constructGraph() method ");
      e.printStackTrace();
    } catch (ParseException e) {
      fail("fail to test constructGraph() method");
      e.printStackTrace();
    }

  }

  /**
   * test a not valid json
   */
  @Test
  public void test01_ConstructGraph_not_valid_json() {
    try {
      packageA.constructGraph("invalid.jason");
      fail("construct method failed");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }


  /**
   * Test the constructGraph() method
   * 
   * 
   */
  public void test02_CycleExceptionAndCycleException()
      throws FileNotFoundException, IOException, ParseException {
    packageA.constructGraph("cyclic.json");
    try {
      packageA.getInstallationOrder("A");
      fail("Fail to test the CycleException");
    } catch (CycleException e) {
    } catch (PackageNotFoundException e) {
      fail("Fail to test the CycleException");
      e.printStackTrace();
    }
    try {
      packageA.getInstallationOrder("invalidPackage");
      fail("Fail to test the PackageNotFoundException");
    } catch (CycleException e) {
      fail("Fail to test the PackageNotFoundException");
      e.printStackTrace();
    } catch (PackageNotFoundException e) {
    }
  }

  /**
   * Test the getInstallationOrder method
   */
  @Test
  public void test03_test_GetInstallationOrder() throws FileNotFoundException, IOException,
      ParseException, CycleException, PackageNotFoundException {
    packageA.constructGraph("valid.json");
    List<String> checkList = packageA.getInstallationOrder("A");
    if (checkList.get(0).equals("C")) {
      if (!checkList.get(1).equals("D"))
        fail("getInstallationOrder) method fail");
      if (!checkList.get(2).equals("B"))
        fail("getInstallationOrder method fail");
      if (!checkList.get(3).equals("A"))
        fail("getInstallationOrder method fail");
    }

    else if (checkList.get(0).equals("D")) {
      if (!checkList.get(1).equals("C"))
        fail("getInstallationOrder() method fail");
      if (!checkList.get(2).equals("B"))
        fail("getInstallationOrder() method fail");
      if (!checkList.get(3).equals("A"))
        fail("getInstallationOrder() method fail");
    }

    else {
      fail("getInstallationOrder method fail");
    }

    packageA.constructGraph("shared_dependencies.json");
    try {
      checkList = packageA.getInstallationOrder("A");
    } catch (CycleException e) {
      return;
    }

    if (!checkList.get(0).equals("D")) {
      fail("getInstallationOrder() method fail");
    } else {
      if (checkList.get(1).equals("B")) {
        if (!checkList.get(2).equals("C"))
          fail("getInstallationOrder() method fail");
        if (!checkList.get(3).equals("A"))
          fail("getInstallationOrder() method fail");
      } else if (checkList.get(0).equals("C")) {
        if (!checkList.get(2).equals("B"))
          fail("getInstallationOrder() method fail");
        if (!checkList.get(3).equals("A"))
          fail("getInstallationOrder() method fail");
      } else {
        fail("getInstallationOrder() method fail");
      }
    }

  }

  /**
   * Test the toInstall method
   * 
   */
  @Test
  public void test04_test_ToInstall() throws FileNotFoundException, IOException, ParseException,
      CycleException, PackageNotFoundException {
    packageA.constructGraph("shared_dependencies.json");
    List<String> check = packageA.toInstall("A", "B");
    if (!check.contains("A") || !check.contains("C") || check.size() != 2)
      fail("toInstall() method fail");
  }

  /**
   * Test the getInstallationOrderForAllPackages() method
   * 
   */
  @Test
  public void test004_GetInstallationOrderForAllPackages()
      throws FileNotFoundException, IOException, ParseException, CycleException {
    packageA.constructGraph("valid.json");
    List<String> checkList = packageA.getInstallationOrderForAllPackages();
    List<String> order1 = new ArrayList<String>();
    order1.add("C");
    order1.add("D");
    order1.add("B");
    order1.add("A");
    order1.add("E");

    List<String> order2 = new ArrayList<String>();
    order2.add("D");
    order2.add("C");
    order2.add("B");
    order2.add("A");
    order2.add("E");

    List<String> order3 = new ArrayList<String>();
    order3.add("D");
    order3.add("C");
    order3.add("B");
    order3.add("E");
    order3.add("A");

    if (checkList.equals(order1)) {
      System.out.println(checkList);
    } else if (checkList.equals(order1)) {
      System.out.println(checkList);
    } else if (checkList.equals(order1)) {
      System.out.println(checkList);
    } else {
      fail("getInstallationOrderForAllPackages method failed");
    }
  }

  
}

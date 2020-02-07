
import java.util.Arrays;
import java.util.Scanner;

public class AuditableBanking {
  /**
   * Adds a transaction group to an array of transaction groups. If the allTransactions array is
   * already full then this method will do nothing other than return allTransactionCount.
   * 
   * @param newTransactions is the new transaction group being added (perfect size).
   * @param allTransactions is the collection that newTransactions is being added to (oversize).
   * @param allTransactionsCount is the number of transaction groups within allTransactions (before
   *        newTransactions is added.
   * @return the number of transaction groups within allTransactions after newTransactions is added.
   */
  public static int submitTransactions(int[] newTransactions, int[][] allTransactions,
      int allTransactionsCount) {
    if (allTransactionsCount < allTransactions.length) { // try to know if the count reach the limit
                                                         // of alltransactions length//
      allTransactions[allTransactionsCount] = newTransactions; // if count did not reach, add new
                                                               // transactions//
      ++allTransactionsCount; // increase count//
      return allTransactionsCount; // return new count//
    } else {
      return allTransactionsCount; // if reach the limit, do not add new transactions//
    }


  }
  // test here named AuditableBankingTests;

  public static int processCommand(String command, int[][] allTransactions,
      int allTransactionsCount) {

    String trans = command.trim(); // make the command no space at first and end
    char index = trans.toUpperCase().charAt(0); // adjust command and get the first character to the
                                                // upper case

    if ((index == '0') || (index == '1') || (index == '2')) {
      String[] Arr = trans.split(" "); // split with " "// submitTransaction) {
      int[] orderArr = new int[Arr.length]; // create a new integer array
      for (int l = 0; l < Arr.length; l++) { // condition
        orderArr[l] = Integer.parseInt(Arr[l]); // change string to integer
      }
      System.out.println("ENTER COMMAND " + command); // make main look like
      System.out.println(); // new line
      return submitTransactions(orderArr, allTransactions, allTransactionsCount); // submit
                                                                                  // transactions
    }
    if (index == 'B') { // if index == 'B', another method
      System.out.println("ENTER COMMAND " + command); // continue the command
      System.out.println(
          "Current Balance: " + calculateCurrentBalance(allTransactions, allTransactionsCount)); /// print
                                                                                                 /// out
                                                                                                 /// the
                                                                                                 /// balance
      System.out.println(); // new line
    }
    if (index == 'Q') { // third, if it is 'Q'
      System.out.println("ENTER COMMAND " + command); // new command
      return -1; // return
    }
    if (index == 'O') { // forth
      System.out.println("ENTER COMMAND " + command); // continue
      System.out.println("Number of Overdrafts: "
          + calculateNumberOfOverdrafts(allTransactions, allTransactionsCount)); // print out number
                                                                                 // of overdrafts
      System.out.println(); // new line
    }
    return allTransactionsCount; // return


  }


  public static int calculateCurrentBalance(int[][] allTransactions, int allTransactionsCount) { //
    int balance = 0; // Initialization balacne
    for (int i = 0; i < allTransactionsCount; i++) { // check the alltRANSACTION COUNT
      if (allTransactions[i][0] == 0) { // If the [i][o] is 0
        for (int j = 1; j < allTransactions[i].length; j++) { // for all the element in command
          if (allTransactions[i][j] == 1) { // element = 1
            balance++; // balance add 1
        
          }
          else if (allTransactions[i][j] == 0) { // if element = 0
            balance--; // balance - 1
           
          }
        }

      } else if (allTransactions[i][0] == 1) {
        for (int k = 1; k < allTransactions[i].length; k++) {
          balance = balance + allTransactions[i][k];
         
        }
      } else if (allTransactions[i][0] == 2) {
        int[]mod = {20,40,80,100};
        for (int l=0;l<4;l++) {
          balance -=allTransactions[i][l+1]*mod[l];
       
        }
      }
    }
    return balance;
  }

  public static int calculateNumberOfOverdrafts(int[][] allTransactions, int allTransactionsCount) {
    int overDraft = 0;// TODO: Implement this method
    int[] b = new int [allTransactionsCount];
    for (int o =0; o <allTransactionsCount;o++ ) {
        b[o]=calculateCurrentBalance(allTransactions,o+1);
    }
        for (int p=0;p<b.length;p++) {
         // boolean decrease =(b[p]>b[p+1])?true:false;
        //  while (decrease = true) {
            if (b[p]<0) {
              overDraft++;              
            }
          }
       //   }
    
          return overDraft;
    }
  

        //for (int i = 0; i < allTransactionsCount; i++) {
       //if (allTransactions[i][0] == 0) {
      //for (int j = 1; j < allTransactions[i].length; j++) {
     // b = b + allTransactions[i][j];
     // if (b < 0) {
     // overDraft++;
     //  }
    // }
    //  }
    // if (allTransactions[i][0] == 1) {
    // for (int k = 1; k < allTransactions[i].length; k++) {
   //   b = b + allTransactions[i][k];
   //  if (b < 0) {
   //    overDraft++;
   //  }
       // }
     // }
      //if (allTransactions[i][0] == 2) {
       // for (int l = 1; l < allTransactions[i].length; l++) {
       //   b = b + allTransactions[i][l];
        //  if (b < 0) {
        //    overDraft++;
        //  }
     //   }
    //  }
  //  }

  public static void main(String[] args) {
    Scanner scnr = new Scanner(System.in);
    String command = null;
    int[][] allTransactions = new int[10][10];
    int allTransactionsCount = 0;

    System.out.println("========== Welcome to the Auditable Banking App ==========");
    boolean finish = false;


    while (!finish) {
      System.out.println("COMMAND MENU:");
      System.out.println("  Submit a Transaction (enter sequence of integers separated by spaces)");
      System.out.println("  Show Current [B]alance");
      System.out.println("  Show Number of [O]verdrafts");
      System.out.println("  [Q]uit Program");

      if (scnr.hasNextLine()) {
        command = scnr.nextLine();
      }
      allTransactionsCount = processCommand(command, allTransactions, allTransactionsCount);
      if (allTransactionsCount == -1) {
        finish = true;
      }
    }
    System.out.println("============ Thank you for using this App!!!! ============");
  }
}



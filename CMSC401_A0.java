//Antonio Brozna

// Input:
// 3
// 6 5 2 5 8 1 3
// 8 2 8 6 7 14 9 4 1
// 5 9 8 21 2 1

// output:
// 25
// 14
// 72

// Notes:
// the first number is the number of rows
// the firs number in subsequent lines is the length of the row
// multiply the numbers at the position of the indices given at the last two numbers in the row
// Save outputs to array to print last


import java.util.Scanner;

public class CMSC401_A0 {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        //System.out.println("Hello, add # of row");

        int rowCount = sc.nextInt();                            // (3)
        int[] results = new int[rowCount];                      // create an array of size 3: rowCount[0],[1],[2]


        for(int i = 0; i < rowCount; i++) {                     // for loop for each row  
            int length = sc.nextInt();                          // scan length of row. (6)
            int[] row = new int[length];                        // create an array "row" of size length(6): row[0],[1],...,[5]

            for (int j = 0; j < length; j++) {
                 row[j] = sc.nextInt();                         // scan and add numbers to corresponding indices in row: [5],[2],[5],...[1]
            }

                int idx1 = row[row.length - 2];                 // get wanted indices from array
                int idx2 = row[row.length - 1];

                int valueOne = row[idx1 - 1];                   // find values at the indices
                int valueTwo = row[idx2 - 1];

            results[i] = valueOne * valueTwo;                   // multiply and add to results array
        }

        for (int i = 0 ; i < results.length; i++) {             // print results
        System.out.println(results[i]);
    }

    }
        
}

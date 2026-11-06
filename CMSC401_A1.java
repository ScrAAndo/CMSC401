//Antonio Brozna
//Assignment 1

// Input:
// array of positive N integers
// Output:
// 1. numbers (seperated by space) occuring more than N/4 times, if exists
// 2. -1 if no such number in array
// input read into array of integers
// no hash maps or tables
// O(N) time complexity
// Input format:
//     First line: shows number of integers in the array
//     following N lines: each contains single positive integer
// Output format:
//     A single line:
//         -1 if input arra hasa no such elment
//         X if X is the only element
//         X Y if there are two such elements where X < Y
//         X Y Z if there are threee such elements where X , Y, Z

import java.util.Scanner;

public class CMSC401_A1 {

    // Function to find all majority elements occurring > n/4 times using only arrays
    public static int[] findMajority(int[] nums) {
        int cand1 = 0, cand2 = 0, cand3 = 0;
        int count1 = 0, count2 = 0, count3 = 0;

        // Store 3 distinct elements, and if one of them repeats, increment that count, otherwise decrease count. If count reaches 0, store the next candidate in the that position
        for (int i = 0; i < nums.length; i++) {
            if (count1 > 0 && nums[i] == cand1) {
                count1++;
            } else if (count2 > 0 && nums[i] == cand2) {
                count2++;
            } else if (count3 > 0 && nums[i] == cand3) {
                count3++;
            } else if (count1 == 0) {
                cand1 = nums[i];
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = nums[i];
                count2 = 1;
            } else if (count3 == 0) {
                cand3 = nums[i];
                count3 = 1;
            } else {
                count1--;
                count2--;
                count3--;
            }
        }

        //Verify frequency for active candidates
        int[] candidates = {cand1, cand2, cand3};
        int[] counts = {count1, count2, count3};
        int threshold = nums.length / 4;

        // At most 3 elements can occur > n/4 times
        int[] tempResult = new int[3]; 
        int validCandidate = 0;

        //For each candidate and their count, first, see if their count is more than 0. If not, skip it, if true, see if it occurs more than N/4 times. If they do, put into an array 
        for (int i = 0; i < 3; i++) {
            if (counts[i] > 0) {
                int freq = 0;
                for (int num : nums) {
                    if (num == candidates[i]) {
                        freq++;
                    }
                }
                if (freq > threshold) {
                    tempResult[validCandidate] = candidates[i];
                    validCandidate++;
                }
            }
        }

        // Create an array of exact size for the valid candidates
        int[] result = new int[validCandidate];
        for (int i = 0; i < validCandidate; i++) {
            result[i] = tempResult[i];
        }

        //Bubble sort
        for (int i = 0; i < result.length - 1; i++) {
            for (int j = 0; j < result.length - i - 1; j++) {
                if (result[j] > result[j + 1]) {
                    int temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int rowCount = sc.nextInt();
        int[] array = new int[rowCount];

        for (int i = 0; i < rowCount; i++) {
            array[i] = sc.nextInt();
        }

        // Get the array of majority candidates
        int[] majorityCandidates = findMajority(array);

        // Print out all majority candidates found
        for (int i = 0; i < majorityCandidates.length; i++) {
            System.out.print(majorityCandidates[i]);
            System.out.print(" ");
        }
    }
}
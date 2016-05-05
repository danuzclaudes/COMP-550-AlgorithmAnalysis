package greedy_algorithms;

import java.util.Scanner;

/**
 * Change Coin
 *
 * Find the minimum number of coins
 * needed to change the input value
 * (an integer) into coins with
 * denominations 1, 5, and 10.
 * e.g., 2 -> 2
 * e.g., 28 -> 6
 * e.g., 20 -> 2
 */
public class Change {
    
    /**
     * Key idea: Greedy.
     * Safe move: take the max denomination coin
     *            less than current value n;
     *            continue remaining n until 0.
     * While current n is not 0,
     * choose the largest coin,
     * update n and # of changes.
     *
     * @param n
     * @return
     */
    private static int getChange(int n) {
        int numChanges = 0;
        int[] denominations = new int[] {10, 5, 1};
        while (n > 0) {
            int i = 0;
            while (i <= 2 && denominations[i] > n) i++;
            n -= denominations[i];
            numChanges++;
        }
        return numChanges;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(getChange(n));
        scanner.close();
    }
}


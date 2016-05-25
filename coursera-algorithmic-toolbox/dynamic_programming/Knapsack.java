package dynamic_programming;

import java.util.Scanner;

/**
 * Discrete knapsack problem
 * 
 * Take as much item into knapsack as possible.
 * no repetition: there is just one copy of each item and
 *                for each item can either take it or not
 *                (no fraction of an item)
 * with repetition: unlimited for each item.
 * Constraints: 1 ≤ W ≤ 10^4; 1 ≤ n ≤ 300;
 *              0 ≤ w0, ..., wn−1 ≤ 10^5
 * Try input with 200 random numbers and 10^5 as weight
 */
public class Knapsack {
    /**
     * Key Idea: with repetition;
     * Subproblem: give some knapsack of value(W),
     *             remove the ith item and W - wi still optimal;
     * Recurrence: build vi from the max of all W - wi:
     *     value(w, i) = max{ value(w − wi) + vi }, i: wi ≤ w
     * Example: W=10, n=4,
     *          w=[6, 3, 4, 2],
     *          v=[30, 14, 16, 9] -> 48
     *
     * @param W
     * @param w
     * @param v
     * @return
     */
    /* Recurrence formula example (weight = 4):
     * i=0: 6 > weight=4, ignore
     * i=1: val=t[4-3]+v1=14,
     * i=2: val=t[4-4]+v2=16,
     * i=3: val=t[4-2]+v3=t[2]+9=18 -> max
     */
    static int optimalWeight_Repetition(int W, int[] w, int[] v) {
        // bottom-up from each smaller weight W.
        int[] table = new int[W + 1];
        for(int weight = 1; weight <= W; weight++) {
            for(int i = 0; i < w.length; i++) {
                if(w[i] > weight) continue;
                int value = table[weight - w[i]] + v[i];
                table[weight] = Math.max(table[weight], value);
            }
        }
        return table[W];
    }

    /**
     * Key Idea: without repetition;
     * Subproblem: given the ith item, either take it or not;
     *             if take, (W - wi, 1..i-1) is still optimal;
     *             else, (W, 1..i-1) is still optimal.
     * Recurrence:
     *     value(w, i) = max{ value(w - wi, i - 1) + vi, value(w, i - 1) }
     * Example: W=10, n=4, w=[6, 3, 4, 2],
     *          v=[30, 14, 16, 9] -> 46
     * Example: 10 4
     *          6 3 4 2
     *          30 14 16 18 -> 48
     *
     * @param W
     * @param w
     * @param v
     * @return
     */
    /* Recurrence formula example (i=2):
     * when i=2, wi=3, always locate:
     * compare t[w-wi,last row] + vi and t[w, last row]
     *
     *    weight: ... 3  4 5  6   7  8  9...
     * i=1        ...[0] 0 0 [30] 30 30 30...
     * i=2                    30   ...  44
     * 
     * i=2, weight=6: val=max{t[3]+v2, t[6]}=max{0+14, 30}=30;
     * i=2, weight=9: val=max{t[6]+v2, t[9]}=max{30+14, 30}=44 -> max;
     */
    static int optimalWeight_NoRepetition(int W, int[] w, int[] v) {
        // bottom-up from all combinations of smaller item i and weight
        int n = w.length;
        int[][] table = new int[n + 1][W + 1];
        // if no item is chosen (i=0), value is 0;
        for(int i = 0; i <= n; i++) table[i][0] = 0;
        // if total weight is 0 (weight=0), value is 0;
        for(int j = 0; j <= W; j++) table[0][j] = 0;
        for(int i = 1; i <= n; i++) {
            for(int weight = 1; weight <= W; weight++) {
                // BZ: if total weight < wi, still regard as not taking i;
                // cannot simply ignoring as 0!
                table[i][weight] = table[i - 1][weight];
                // BZ: the ith item has index i - 1;
                // since row is from 0 to n;
                if (weight < w[i - 1]) continue;
                table[i][weight] = Math.max(
                        table[i - 1][weight - w[i - 1]] + v[i - 1],
                        table[i][weight]);
            }
        }
        return table[n][W];
    }

    static int optimalWeight(int W, int[] w) {
        // Example: 10 3 1 4 8 -> 9
        // build from all combinations of smaller i and weight;
        int n = w.length;
        int[][] table = new int[n + 1][W + 1];
        // fill in first row and first col by 0
        for (int j = 0; j <= W; j++) table[0][j] = 0;
        for (int i = 0; i <= n; i++) table[i][0] = 0;
        // either take ith: (w - wi, i - 1); or no taking: (w, i - 1)
        for (int i = 1; i <= n; i++) {
            for (int weight = 1; weight <= W; weight++) {
                // even wi exceeds, keep value of (w, i - 1) as no taking
                table[i][weight] = table[i - 1][weight];
                // BZ: the ith item has index i - 1, since 0~n
                if (weight < w[i - 1]) continue;
                table[i][weight] = Math.max(
                        table[i][weight],
                        table[i - 1][weight - w[i - 1]] + w[i - 1]);
                // vi is related with wi, so add wi into knapsack
            }
        }
        return table[n][W];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int W, n;
        W = scanner.nextInt();
        n = scanner.nextInt();
        int[] w = new int[n], v = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }
        for (int i = 0; i < n; i++) {
            v[i] = scanner.nextInt();
        }
        System.out.println(optimalWeight_Repetition(W, w, v));
        System.out.println(optimalWeight_NoRepetition(W, w, v));
        System.out.println(optimalWeight(W, w));
        scanner.close();
    }
}

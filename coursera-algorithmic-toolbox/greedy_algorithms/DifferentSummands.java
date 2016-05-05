package greedy_algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Pairwise Distinct Summands
 * 
 * Represent a given positive integer n as
 * a sum of as many pairwise distinct positive
 * integers as possible. Constraints: 1≤n≤10^9.
 * Example: 6=1+2+3.
 * Example: 8=1+2+5.
 *
 */
public class DifferentSummands {
    /**
     * Key Idea:
     * safe move: select the 1st distinct minimum integer?
     * n    i  n-i  i
     * 8  - 1 = 7 > 1 {1}
     * 7  - 2 = 5 > 2 {1,2}
     * 5  - 3 = 2 < 3, cannot have 1+2+3+2
     * 5  - 4 = 1 < 4, cannot have 1+2+4+1
     * 5  - 5 = 0     {1,2,5}
     * If n - i ≤ i, output just one summand n.
     * Otherwise output i and solve the subproblem (n − i, i + 1).
     * 
     * @param n
     * @return
     */
    private static List<Integer> optimalSummands(int n) {
        List<Integer> summands = new ArrayList<Integer>();
        int i = 1;
        while (n > 0) {
            if (n - i > i || n == i) {
                summands.add(i);
                n -= i;
                i++;
            }
            else if (n - i <= i) i++;
        }
        return summands;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
        scanner.close();
    }
}


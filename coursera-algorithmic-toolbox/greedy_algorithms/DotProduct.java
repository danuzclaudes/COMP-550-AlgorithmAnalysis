package greedy_algorithms;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Minimum Dot Product
 *
 * The goal is, given two sequences a1, a2, ..., an
 * and b1, b2, ... , bn, find a permutation π of
 * the second sequence such that the dot product
 * of a1, a2, ..., an and bπ1, bπ2, ..., bπn is minimum.
 * e.g., [1 3 -5][-2 4 1] -> -25
 * e.g., [1 3 5][2 3 4] -> 23
 */
public class DotProduct {
    /**
     * Safe move: minA * maxB
     * Prove safety: a1b1 + a2b2 - (a1b2 + a2b1)
     *               a1b1 + a2b2 - (a1bi + a2bj),
     *               where i > 1 and j != i?
     *
     * @param a     the array of first  int sequences
     * @param b     the array of second int sequences
     * @return
     */
    private static long minDotProduct(int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);
        long result = 0;
        for (int i = 0, n = a.length; i < a.length; i++) {
            result += a[i] * b[n - 1 - i];
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
        }
        System.out.println(minDotProduct(a, b));
        scanner.close();
    }
}


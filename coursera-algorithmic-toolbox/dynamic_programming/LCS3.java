package dynamic_programming;

import java.util.Scanner;

/**
 * Longest Common Subsequence of Three Sequences
 * 
 * Find the length of their longest common subsequence,
 * i.e., the largest non-negative integer p such that
 * there exist indices 1 ≤ i1 < i2 < ··· < ip ≤ n,
 * 1 ≤ j1 < j2 < ··· < jp ≤ m, 1 ≤ k1 < k2 < ··· < kp ≤ l
 * such that ai1 = bj1 = ck1, ... , aip = bjp = ckp.
 * Constraints: 1 ≤ n, m, l ≤ 100;
 *              −10^9 < ai , bi , ci < 10^9.
 * Example: 3, 1 2 3
 *          3, 2 1 3
 *          3, 1 3 5 -> 2 (1,3)
 * Example: 5, 8 3 2 1 7
 *          7, 8 2 1 3 8 10 7
 *          6, 6 8 3 1 4 7 -> 3
 */
public class LCS3 {

    /**
     * Key Idea: http://stackoverflow.com/a/5057362
     * Subproblem: give some three subsequences ending by
     *             Ai, Bj, Ck, which has optimal length of
     *             LCS(i, j, k). Either Ai=Bj=Ck or not.
     * Recurrence: LCS(i,j,k)=max{
     *     LCS(i-1,j-1,k-1) + 1 if Ai=Bj=Ck, otherwise
     *     max(LCS[i-1,j,k], LCS[i,j-1,k], LCS[i,j,k-1])
     * }
     * Bottom-up:  build up from 3d diagonal?
     * BZ: traverse all sub-cases of i=j, i=k, j=k, i!=j!=k?
     *     reduce the size of only one subproblem.
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    private static int lcs3(int[] a, int[] b, int[] c) {
        // Build a 3d table?
        int n = a.length, m = b.length, l = c.length;
        int[][][] table = new int[n + 1][m + 1][l + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                for(int k = 0; k <= l; k++) {
                    if(i == 0 || j == 0 || k == 0){
                        table[i][j][k] = 0;
                        continue;
                    }
                    if(a[i - 1] == b [j - 1] && b[j - 1] == c[k - 1]) {
                        table[i][j][k] = table[i - 1][j - 1][k - 1] + 1;
                        continue;
                    }
                    // take max from either of the 3 cases
                    int x = table[i - 1][j][k],
                        y = table[i][j - 1][k],
                        z = table[i][j][k - 1];
                    table[i][j][k] = Math.max(x, Math.max(y, z));
                }
            }
        }
        return table[n][m][l];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int an = scanner.nextInt();
        int[] a = new int[an];
        for (int i = 0; i < an; i++) {
            a[i] = scanner.nextInt();
        }
        int bn = scanner.nextInt();
        int[] b = new int[bn];
        for (int i = 0; i < bn; i++) {
            b[i] = scanner.nextInt();
        }
        int cn = scanner.nextInt();
        int[] c = new int[cn];
        for (int i = 0; i < cn; i++) {
            c[i] = scanner.nextInt();
        }
        System.out.println(lcs3(a, b, c));
        scanner.close();
    }
}


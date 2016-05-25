package dynamic_programming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Primitive Calculator from 3x, 2x, x+1.
 *
 * Given an integer n, compute the minimum
 * number of operations needed to obtain
 * the number n starting from the number 1.
 * Three operations with the current x:
 * multiply x by 2, multiply x by 3, or
 * add 1 to x.
 *
 * Example: 1 -> 0, 1
 * Example: 4 -> 2, 1 3 4
 * Example: 5 -> 3, 1 2 4 5
 * Example: 6 -> 2, 1 3 6
 * Example: 11809 -> 12
 *     1 3 9 27 81 82 164 328 656 1312
 *     3936 11808 11809
 * Example: 96234 -> 14,
 *     1 3 9 10 11 33 99 297 891 2673
 *     8019 16038 16039 48117 96234
 */
public class PrimitiveCalculator {
    /**
     * Key Idea:
     * Subproblem: given some min # of operations
     *             to obtain n: count(n), n comes
     *             from either x*2, x*3, x+1.
     * Recurrence: count(n) = min {
     *     count(n / 2) + 1
     *     count(n / 3) + 1
     *     count(n - 1) + 1
     * }
     * Bottom-up:  build from 1 to n is the same as
     *             going from n to 1
     * Backtrack:  find left with the 1st t[n] - 1
     *
     * @param n
     * @return
     */
    private static List<Integer> optimal_sequence(int n) {
        List<Integer> sequence = new ArrayList<Integer>();
        int[] table = new int[n + 1];
        table[1] = 0;
        for (int i = 2; i <= n; i++) {
            int a = (i % 2) == 0 ? table[i / 2] : Integer.MAX_VALUE;
            int b = (i % 3) == 0 ? table[i / 3] : Integer.MAX_VALUE;
            int c = table[i - 1];
            table[i] = Math.min(a, Math.min(b, c)) + 1;
        }
        sequence.add(n);
        // how to backtrack? from n to 1...
        // 1) BZ: find the first cell one step smaller?
        // 2) find if n/2, n/3, n-1 >= 1, and
        // if t[n] - 1 = t[n/2], add n/2, n/=2;
        // if t[n] - 1 = t[n/3], add n/3, n/=3;
        // if t[n] - 1 = t[n-1], add n-1, n-=1;
        while (n > 1) {  // BZ: n > 0? when n==1, falls in no cases
            if(n / 2 >= 1 && table[n] - 1 == table[n / 2]) {
                sequence.add(n / 2);
                n /= 2;
            }
            else if(n / 3 >= 1 && table[n] - 1 == table[n / 3]) {
                sequence.add(n / 3);
                n /= 3;
            }
            else if(n - 1 >= 1 && table[n] - 1 == table[n - 1]) {
                sequence.add(n - 1);
                n -= 1;
            }
        }
        Collections.reverse(sequence);
        return sequence;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimal_sequence(n);
        System.out.println(sequence.size() - 1);
        for (Integer x : sequence) {
            System.out.print(x + " ");
        }
        scanner.close();
    }
}


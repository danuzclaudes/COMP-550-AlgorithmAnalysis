package growth_rate;

import java.util.Scanner;

/**
 * The Last Digit of a Large Fibonacci Number.
 *
 * Given an integer n, find the last digit of the nth Fibonacci
 * number Fn (that is, Fn mod 10). Constraints: 0 ≤ n ≤ 10^7.
 * e.g. 3 -> 2.
 * e.g. 200 -> 5.
 * e.g. 327305 -> 5.
 *
 * Store the last digit of each Fib #, instead of Fib # itself.
 * Li % m = (Li-2 + Li-1) % m = ...
 */
public class FibonacciLastDigit {
    
    /**
     * Build each last digit from previous two.
     *
     * @param n     the nth Fibonacci number
     * @return      the last digit of the nth Fib #
     */
    private static int getFibonacciLastDigit(int n) {
        // BZ: F0 = 0, F1 = 1, but from the 0th, 1th, ..., to the Nth
        int[] last_digits = new int[n + 1];
        last_digits[0] = 0;
        last_digits[1] = 1;
        for(int i = 2; i <= n; i++) {
            last_digits[i] = (
                    last_digits[i - 2] % 10 +
                    last_digits[i - 1] % 10 ) % 10;
        }
        return last_digits[n];
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int c = getFibonacciLastDigit(n);
        System.out.println(c);
        scanner.close();
    }
}

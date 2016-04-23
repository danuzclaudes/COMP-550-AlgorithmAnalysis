package growth_rate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Huge Fibonacci Number modulo m.
 *
 * Given two integers n and m, output Fn mod m (that is,
 * the remainder of Fn when divided by m).
 * Suppose 1 ≤ n ≤ 10^18, 2 ≤ m ≤ 10^5,
 * i.e. n is huge and it's unlikely to generate Fn;
 * e.g. 2015 3 -> 1
 * e.g. 281621358815590 30524 -> 11963.
 * e.g. 62583203023936775 83199 -> 2320.
 *
 * Think small: m = 2 or m = 3
 * i        0 1 2 3 4 5 6 7  8  9  10 11 12  13  14  15
 * Fi       0 1 1 2 3 5 8 13 21 34 55 89 144 233 377 610
 * Fi % 2   0 1 1 0 1 1 0 1  1  0  1  1  0   1   1   0
 * Fi % 3   0 1 1 2 0 2 2 1  0  1  1  2  0   2   2   1
 *
 * For any integer m ≥ 2, the sequence `Fn mod m` is periodic.
 * The period always starts with "01" as `Pisano period`.
 * Compute each Fib % m instead of Fib # itself.
 */
public class FibonacciHugeModulo {

    /**
     * Key idea: build from each modulo of Fib #,
     *           instead of Fib # itself (overflow).
     * Loop until Fi % m and F[i+1] % m is "01" period again.
     *
     *   (a + b)                (mod n)
     * = (a mod n) + (b mod n)  (mod n)
     * ---------------------------------------------
     * i        ...
     * Fi % m   x y (x+y)%m?
     * Let x = Fi % m, y = F[i+1] % m;
     * then F[i+2] % m = (Fi + F[i+1]) % m
     *                 = (x + y) % m
     *                 = (x % m + y % m) % m
     *
     * @param n
     * @param m
     * @return
     */
    private static long getFibonacciHuge(long n, long m) {
        // store moduloes only in one pisano period, but size is unknown
        List<Integer> modulos = new ArrayList<>();
        modulos.add(0);
        modulos.add(1);
        int i = 0;
        // must check both current and next modular
        while(! (i > 0 && modulos.get(i) == 0 && modulos.get(i + 1) == 1)) {
            modulos.add((int) ((
                    modulos.get(  i  ) % m +
                    modulos.get(i + 1) % m ) % m));
            i++;
        }
        // i is currently pisano period; loop to Fn % i again?
        // Time-Space tradeoff: store each value in the period.
        System.out.println("pisano period=" + i);
        return modulos.get((int) (n % i));
    }

    public static long getFibonacciHuge_BZ(long n, long m) {
        /* Generate each Fibonacci # from Fi + F[i+1]? */
        long fi = 0, fj = 1;  // def fi as current Fib #, fj as next
        long i = 0, tmp = 0;
        while(! (i > 0 && fi % m == 0 && fj % m == 1)) {
            tmp = fi + fj;
            fi = fj;
            fj = tmp;
            i++;
        }
        System.out.println("pisano period=" + i + " fi=" + fi);
        // take i as the pisano period, compute Fn % i % m
        return (n % i) % m;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        long start = System.currentTimeMillis(); 
        System.out.println(getFibonacciHuge(n, m));
        long end = System.currentTimeMillis();
        System.out.println("Timing: " + (end - start));
        scanner.close();
    }
}

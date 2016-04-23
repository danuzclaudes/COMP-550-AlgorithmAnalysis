package growth_rate;

import java.util.Scanner;

/**
 * Given two integers a and b, find their least common multiple.
 *
 * Relationship between LCM and GCD:
 *     LCM(a, b) * GCD(a, b) = a * b
 * e.g. lcm(4,6)=12, gcd(4,6)=2 --- lcm * gcd = a * b
 * e.g. lcm(18,24)=72, gcd(18,24)=6 --- 72*6 = 18*24
 * e.g. 226553150 1023473145 -> 46374212988031350
 */
public class LCM {

    private static long lcm(int a, int b) {
        // BZ: a * b / <long> -> still returns int type!
        // because a * b already overflows
        return ((long) a * b) / gcd(a, b);
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        System.out.println(lcm(a, b));
        scanner.close();
    }
}

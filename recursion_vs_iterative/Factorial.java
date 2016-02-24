// PACKAGE DECLARATION GOES HERE
package recursion_vs_iterative;

/** A class containing some simple routines for computing factorials. */
public class Factorial {
    static final int MAX_TEST = 6;
    /* Input:  non-negative integer n
     * Output: n!
     * Error: returns -1
     * Recursive implementation (defined in terms of itself)  
     * 
     * Certified bug-free!!
     */
    public static int recursiveFactorial(int n) {
        // Validate input
        if (n < 0) return -1;
        if (n == 0) return 1;  // base case of recursion
        else return n*recursiveFactorial(n-1);  // recursive case
    }

    public static void main(String[] args) {
        boolean passed = true;
         
        if (!(MAX_TEST > 0)) {
            System.err.println("No tests being run!");
            passed = false;
        }
         
        for (int i = 0; i <= MAX_TEST; i++) {
            int expected = recursiveFactorial(i);
            int actual   = iterativeFactorial(i);
            if (actual != expected) {
                System.err.println("There's a problem ...");
                passed = false;
                break;
            }
        }
        if (passed)
            System.out.println("All tests passed!");
    }
}

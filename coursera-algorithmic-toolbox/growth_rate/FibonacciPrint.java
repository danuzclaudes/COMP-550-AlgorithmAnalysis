package growth_rate;

import java.util.Scanner;
/**
 * Print first N Fibonacci numbers;
 * if flag == true, increasing order;
 * if flag == false, reverse order
 */
public class FibonacciPrint {
    
    /**
     * key idea: build the ith Fibonacci # from i-1th and i-2th;
     * if flag is true, print out from the 3rd to the Nth;
     * else stop at the Nth and N-1th,
     * then build ith back from i+1th and i+2th
     *
     * @param N
     * @param flag
     */
    private static void printF(int N, boolean flag) {
        if(N <= 0) return;
        int x = 1, y = 1, current = 0;
        if(flag) System.out.print(N < 2 ? x : x + " " + y + " ");
        for(int i = 3; i <= N; i++) {
            current = x + y;
            if(flag) System.out.print(current + " ");
            x = y;
            y = current;
        }
        // stop at the Nth if is increasing order
        if(flag) return;
        // else print out the Nth and the N-1th first
        System.out.print(N < 2 ? y : y + " " + x + " ");
        for(int i = N - 2; i >= 1; i--) {
            current = y - x;
            System.out.print(current + " ");
            y = x;
            x = current;
        }
    }

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int num = s.nextInt();
        while(num-- != 0) {
            int N = s.nextInt();
            boolean flag = Boolean.parseBoolean(s.next());
            printF(N, flag);
            System.out.println();
        }
        s.close();
    }
}
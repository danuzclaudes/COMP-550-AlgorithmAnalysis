package recursion_vs_iterative;

public class FibonacciNumbers {
    ////////////////////////////////////////////////////////////////////////
    // Print first N Fibonacci numbers.
    // f(n) = f(n-1) + f(n-2)
    // when n==1 || n==2 return 1 [base case]
    // sum = f1 + f2; f1 = f2; f2 = sum;
    //    => counting: n
    //    => iteration: f1, f2 (initial value: f1=1,f2=1)
    //    => parameter: n, f1, f2
    // example: 1 1 2 3 5 8 13 21 34...
    ////////////////////////////////////////////////////////////////////////
    public void find_fibonacci_iterative(int N){
        // Iterative method:
        // f = f1 + f2
        int f1 = 1, f2 = 1, tmp = 0;
        for(int i = 0; i < N; i++){
            System.out.print(f1 + " ");
            tmp = f1;
            f1 = f2;
            f2 += tmp;
        }
    }

    public int find_fibonacci_recursive_duplicate(int n){
        // Recursive method with duplicated recursion trees:
        if(n == 1 || n == 2) return 1;
        return find_fibonacci_recursive_duplicate(n - 1) +
            find_fibonacci_recursive_duplicate(n - 2);
    }
    
    // print distinct numbers
    public void find_fibonacci_recursive(int n){
        fibonacci(n, 1, 1);
    }
    private void fibonacci(int n, int f1, int f2){
        if(n == 0) return;
        fibonacci(n - 1, f2, f1+f2);
    }

    ////////////////////////////////////////////////////////////////////////
    // Print Fibonacci series;
    // if flag == true, increasing order; if flag == false, reverse order
    ////////////////////////////////////////////////////////////////////////
    public static void fibo(int N, boolean flag){
        if(N <= 0) return;
        int x = 1, y = 1, tmp = 0, i = 0;
        // use increment i to help print length of N from [0..N-1]
        while(i++ < N){
            if(flag) System.out.print(x + " ");
            tmp = x;
            x = y;
            y = tmp + y;
        }
        // BZ: while(N-- > 0), if N==0, loop terminates, but still execute N--;
        // -> N stops at -1 and x stops at the N+1 th;
        if(! flag){
            i = 0;  // reset increment and print length of N from [0..N-1]
            while(i++ < N){
                System.out.print(y - x + " ");
                tmp = x;
                x = y - x;
                y = tmp;
            }
        }
        System.out.println();
    }

    public static void main(String[] args){
        long startTime = System.nanoTime();
        fn.find_fibonacci_recursive_duplicate(30);
        long endTime = System.nanoTime();
        System.out.println(
            "Time of Fibonacci Numbers in recursive way with 2 params: " +
            (endTime - startTime) + " secs"
        );

        startTime = System.nanoTime();
        fn.find_fibonacci_recursive(30);
        endTime = System.nanoTime();
        System.out.println(
            "Time of Fibonacci Numbers in recursive way with 1 param: " +
            (endTime - startTime) + " secs"
        );
    }
}

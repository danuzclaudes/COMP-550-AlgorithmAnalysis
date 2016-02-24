package recursion_vs_iterative;

import java.util.Scanner;
////////////////////////////////////////////////////////////////////////
// t(n) = 10*t(n-1) + a
// f(n) = f(n-1) + t(n)
// f(n) = a+aa+aaa+aaaa+aaaaa
//      = a+ f(n-1)   // bottom-up     
//      = a+ aa+ f(n-2)    
//      = a+ aa+ aaa + f(n-3)
//      = a+ aa+ aaa + aaaa+ f(n-4)
//      = a+ aa+ aaa + aaaa+ aaaaa   
// when n == 1, return aaaaa [base case]
// term = term * 10 + a; sum = sum + term; 
//     => counting: n
//     => iteration: term, sum
//     => parameter: a (i.e. constant), n (i.e. counting), term*10+a (i.e. iteration)
////////////////////////////////////////////////////////////////////////
public class ConsecutiveA {

    public static int compute_iterative(int a, int n){
        int sum = 0; 

        // while(count < n){
        //     sum = 10*sum + a; ---> error!
        //     count++;
        // }
        // sum will be a, aa, aaa,...; insead of a+aa+aaa+...
        int term = 0;
        while(n>0){
            term = term * 10 + a; // term = a, aa, aaa,...
            sum = sum + term; // sum = a + aa + aaa + ...
            n--; // n = n - 1
        }
        return sum;
    }

    public static int compute_recursive(int a, int n, int term, int sum){
        if(n == 0) return sum;
        return compute_recursive(a, n-1, term * 10 + a, sum+term);
    }

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int n = s.nextInt();
        int term = a, sum = 0;
        int result_r = compute_recursive(a,n,term,sum);
        int result_i = compute_iterative(a,n);
        System.out.println("Recursive result is: "+result_r);
        System.out.println("Iterative result is: "+result_i);
    }
}

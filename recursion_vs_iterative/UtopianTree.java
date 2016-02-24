package recursion_vs_iterative;

import java.util.Scanner;

public class UtopianTree {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int T = s.nextInt();
        for (int i=0;i<T;i++){
            int N = s.nextInt();
            int result=utopianTree(N);
            System.out.println(result);
        }
    }

    public static int utopianTree(int N){
        if(N==0) return 1;
        if((N & 1) == 0) return utopianTree(N-1)+1; // if even, UT(N)=UT(N-1)+1
        else return utopianTree(N-1)*2; // if odd, UT(N)=UT(N-1)*2
    }
}

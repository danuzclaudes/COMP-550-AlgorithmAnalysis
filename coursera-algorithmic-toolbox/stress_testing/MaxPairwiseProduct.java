package stress_testing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

public class MaxPairwiseProduct {
    public static long getMaxPairwiseProduct(int[] numbers) {
        long result = 0;
        int n = numbers.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (((long) numbers[i]) * numbers[j] > result) {
                    result = ((long) numbers[i]) * numbers[j];
                }
            }
        }
        return result;
    }

    /**
     * Find both the largest and the 2nd largest of the numbers.
     * Return the product of them.
     *
     * @param numbers
     * @return
     */
    public static long getMaxPairwiseProductFaster(int[] numbers) {
        int largest_idx = -1, second_largest_idx = -1;
        for (int i = 0; i < numbers.length; i++) {
            // BZ: `largest_idx` is -1 initially...
            if (largest_idx == -1 || numbers[i] > numbers[largest_idx])
                largest_idx = i;
        }

        for (int j = 0; j < numbers.length; j++) {
            // BZ: j is not the largest_idx; but max can be duplicate;
            // BZ: must check -1 initially...
            if (j != largest_idx && (second_largest_idx == -1 ||
                    numbers[j] > numbers[second_largest_idx]))
                second_largest_idx = j;
        }

        return ((long) numbers[largest_idx]) * numbers[second_largest_idx];
    } 

    public static void main(String[] args) {

        Random random = new Random();
        // Stress testing
        while(true) {
            // random.nextInt(max - min + 1) + min
            // stackoverflow.com/a/363692
            int n = random.nextInt(10 - 2 + 1) + 2;
            System.out.println(n);
            int[] tests = new int[n];
            for(int i = 0; i < n; i++) {
                tests[i] = random.nextInt(100000 + 1);
                System.out.print(tests[i] + " ");
            }
            System.out.println();
            long res1 = getMaxPairwiseProduct(tests);
            long res2 = getMaxPairwiseProductFaster(tests);
            if(res1 == res2)
                System.out.println("OK");
            else {
                System.out.println("Error:" + res1 + " " + res2);
                break;
            }
        }

        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }
        System.out.println(getMaxPairwiseProductFaster(numbers));
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

}
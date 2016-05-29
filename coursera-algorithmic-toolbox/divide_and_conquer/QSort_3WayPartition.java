package divide_and_conquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

public class QSort_3WayPartition {
    private static Random random = new Random();

    /**
     * Key Idea:
     * for all l ≤ k ≤ m1 − 1, A[k] < x
     * for all m1 ≤ k ≤ m2 , A[k] = x
     * for all m2 + 1 ≤ k ≤ r , A[k] > x
     */
    private static int[] partition3(int[] a, int l, int r) {
      int m1 = l;
      int m2 = l;
      int pivot = a[l];
      for (int j = l + 1; j <= r; j++) {
          if (a[j] > pivot) continue;
          if (a[j] < pivot) {
              swap(a, m1, j);
              m1++;
              
          }
          swap(a, m2 + 1, j);
          m2++;
      }
      int[] m = {m1, m2};
      return m;
    }
    private static void swap(int[] a, int i, int j) {
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }

    public static int partition2(int[] a, int l, int r) {
        int x = a[l];
        int j = l;
        for (int i = l + 1; i <= r; i++) {
            if (a[i] <= x) {
                j++;
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        int t = a[l];
        a[l] = a[j];
        a[j] = t;
        return j;
    }

    private static void randomizedQuickSort(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int k = random.nextInt(r - l + 1) + l;
        int t = a[l];
        a[l] = a[k];
        a[k] = t;
        // use partition2:
        // int m = partition2(a, l, r);
        // randomizedQuickSort(a, l, m - 1);
        // randomizedQuickSort(a, m + 1, r);
        int[] ms = partition3(a, l, r);
        int m1 = ms[0], m2 = ms[1];
        randomizedQuickSort(a, l, m1 - 1);
        randomizedQuickSort(a, m2 + 1, r);
    }

    public static void main(String[] args) {
        // Stress testing on arrays of size 10^5
        // with random values ranging from 1 to 10^9
        int tn = 100000;
        while (true) {
            int[] t = new int[tn];
            Random rdn = new Random();
            for (int i = 0; i < tn; i++) {
                t[i] = rdn.nextInt(1000000000) + 1;
            }
            randomizedQuickSort(t, 0, tn - 1);
            boolean sorted = true;
            for (int i = 1; i < tn; i++) {
                if (t[i] < t[i - 1]) {
                    System.out.println("Error:" + t[i]);
                    sorted = false;
                    break;
                }
            }
            if (! sorted) break;
            else System.out.println("Test Case OK");
        }
        
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        randomizedQuickSort(a, 0, n - 1);
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
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


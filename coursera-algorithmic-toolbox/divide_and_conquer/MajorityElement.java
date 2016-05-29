package divide_and_conquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Majority Element
 * 
 * An element of a sequence of length n is called
 * a majority element if it appears in the sequence
 * strictly more than n/2 times. Check whether an
 * input sequence contains a majority element.
 * Constraints: 1 ≤ n ≤ 10^5;
 *              0 ≤ ai ≤ 10^9 for all 0 ≤ i < n.
 * Example: 5, 2 3 9 2 2 -> 1
 * Example: 4, 1 2 3 4 -> 0
 * Example: 4, 1 2 3 1 -> 0
 * Example: 5, 2 2 2 0 0 -> 1
 * Example: 5, 0 0 2 2 2 -> 1
 * Example: 5, 2 2 1 0 0 -> 0
 * 
 * https://leetcode.com/discuss/42929
 */
public class MajorityElement {
    private static int getMajorityElement(int[] a, int left, int right) {
        if (left == right) {  // right is n, i.e. a.length
            return -1;
        }
        if (left + 1 == right) {
            return a[left];
        }
        return major(a, left, right - 1);
    }
    /**
     * Key Idea: divide and conquer.
     *           If a sequence contains a majority element,
     *           then one of its halves also contains it.
     * Split the array into 2 sub-arrays of half the size.
     * Choose majority element of each half.
     * Linear search whether it is majority.
     *
     * @param a
     * @param low
     * @param high
     * @return
     */
    private static int major(int[] a, int low, int high) {
        // Base case:  if n==1, single element is major.
        if (low == high) return a[low];
        int mid = (high - low) / 2 + low;
        int left_major  = major(a, low, mid);
        int right_major = major(a, mid + 1, high);
        if (left_major == right_major) return left_major;
        // Return the majority with larger counts
        int left_count  = getFrequency(a, left_major);
        int right_count = getFrequency(a, right_major);
        // No majority case: neither frequency > n/2...
        // BZ: check the larger of left count & right count?
        return left_count > a.length / 2 ? left_major :
            ( right_count > a.length / 2 ? right_major : -1 );
    }
    private static int getFrequency(int[] a, int major) {
        // Scan the element in a[1..n] in every recursive call,
        int count = 0;
        for (int ele : a) {
            if (ele == major) count++;
            if (count > a.length / 2) break;
        }
        return count;
    }
    
    /**
     * Key Idea: Moore Voting Algorithm.
     * Set a dummy majority, and increase count if equal.
     * Cancel out each unmatched entry and decrease count.
     * Reset a new dummy after current count is 0.
     *
     */
    public static int getMajorityElement_Linear(int[] a,
            int left, int right) {
        int majority = -1, count = 0;
        for (int i = left; i < right; i++) {
            if (count == 0) {
                majority = a[i];
                count = 1;
            }
            else if (a[i] == majority) count++;
            else if (a[i] != majority) count--;
        }
        count = 0;
        // stills needs to check if really is majority
        for (int i = left; i < right; i++) {
            if (a[i] == majority) count++;
            if (count > right / 2) return majority;
        }
        return -1;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        if (getMajorityElement(a, 0, a.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
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


package divide_and_conquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Points and Segments.
 *
 * Given # of s segments and # of p points on a line,
 * count, for each point, # of segments containing it.
 * Constraints: 1 ≤ s, p ≤ 50000;
 *              −10^8 ≤ ai ≤ bi ≤ 10^8 for all 0 ≤ i < s;
 *              −10^8 ≤ xj ≤ 10^8 for all 0 ≤ j < p.
 * Example: n=4, m=1, s=[-5 -2 3 4], e=[1 0 4 5], p=[3] -> 1
 * Example: n=3, m=1, s=[2,-4,5], e=[2,5,5],p=[5]->2
 */
public class PointsAndSegments {

    /**
     * Key Idea:
     * Mark point, left-end, right-end as p, l, r, respectively.
     * Pair the 3 types of points as (value, {p,l,r}).
     * Sort all pairs by values.
     * For each 'p', count # of segments containing it:
     *     each 'l' marks the start of segment that may wrap 'p';
     *     each 'r' to left of 'p' will cancel out a 'l';
     *     each l=p=r segment is still wrapping 'p'.
     *
     * @param starts    the left-ends of segments
     * @param ends      the right ends of segments
     * @param points    the values of points
     * @return  the count of segments containing each point
     */
    public static int[] fastCountSegments(int[] starts,
            int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        int n = starts.length, m = points.length, index = 0;
        // Build array of pairs with value and mark 'l', 'r', 'p'.
        // N segments provide 2N pairs while M points have M pairs.
        Pair[] pairs = new Pair[2 * n + m]; 
        for (int end : ends)     pairs[index++] = new Pair(end,   'r');
        for (int point : points) pairs[index++] = new Pair(point, 'p');
        for (int start : starts) pairs[index++] = new Pair(start, 'l');
        // BZ: points are sorted by values, but cnt is still unordered
        // Map each point with index? duplicate points?
        Map<Integer, List<Integer>> mapPointsToIndex = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            if(! mapPointsToIndex.containsKey(points[i]))
                mapPointsToIndex.put(points[i], new ArrayList<>());
            mapPointsToIndex.get(points[i]).add(i);
        }
        // Sort all pairs by values
        Arrays.sort(pairs, new Comparator<Pair>(){
            @Override
            public int compare(Pair p1, Pair p2) {
                // BZ: what if p == l == r?
                // sort by l->p->r, i.e. segment still contains p.
                // It is indeed fortunate that the letters 'l', 'p',
                // and 'r' are in the correct lexicographical order.
                return p1.value == p2.value ? p1.mark - p2.mark :
                    p1.value - p2.value;
            }
        });
        // Count # of segments containing each point
        int count = 0;
        for (Pair pair : pairs) {
            if (pair.mark == 'l') count++;
            if (pair.mark == 'r') count--;
            if (pair.mark == 'p') {
                // BZ: duplicate points? -> have same counts,
                // but fill in all indices with point p
                for (int i : mapPointsToIndex.get(pair.value))
                    cnt[i] = count;
            }
        }
        return cnt;
    }
    private static class Pair {
        int value;
        char mark;
        public Pair (int v, char m) {
            value = v;
            mark  = m;
        }
    }
    
    public static int[] naiveCountSegments(int[] starts,
            int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < starts.length; j++) {
                if (starts[j] <= points[i] && points[i] <= ends[j]) {
                    cnt[i]++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        // Stress testing by comparing faster and naive
        while (true) {
            Random random = new Random();
            // int limit_n = 5, limit_min = -5, limit_max = 5;
            int limit_n = 50000, limit_min = -100000000, limit_max = 100000000;
            int n = random.nextInt(limit_n - 1) + 1;
            int m = random.nextInt(limit_n - 1) + 1;
            int[] starts = new int[n];
            int[] ends = new int[n];
            int[] points = new int[m];
            for (int i = 0; i < n; i++) {
                starts[i] = random.nextInt(limit_max - limit_min + 1) + limit_min;
                ends[i] = random.nextInt(limit_max - starts[i] + 1) + starts[i];
            }
            for (int i = 0; i < m; i++) {
                points[i] = random.nextInt(limit_max - limit_min + 1) + limit_min;
            }
            int[] naive = naiveCountSegments(starts, ends, points);
            
            // Compare with naive solution and measure time latency...
            long start = System.currentTimeMillis();
            int[] mine  = fastCountSegments(starts, ends, points);
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            if (end - start > 4500) {
                System.out.println("Timeout for n=" + n + " m=" + m);
                break;
            }
            if (! Arrays.equals(naive, mine)) {
                System.out.println("Error! n=" + n + " m="+m);
                System.out.println("naive="  + Arrays.toString(naive));
                System.out.println("mine="   + Arrays.toString(mine));
                System.out.println("starts=" + Arrays.toString(starts));
                System.out.println("ends="   + Arrays.toString(ends));
                System.out.println("points=" + Arrays.toString(points));
                break;
            } 
            else {
                System.out.println("OK");
            }
        }
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        int[] starts = new int[n];
        int[] ends = new int[n];
        int[] points = new int[m];
        for (int i = 0; i < n; i++) {
            starts[i] = scanner.nextInt();
            ends[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }
        //use fastCountSegments
        int[] cnt = fastCountSegments(starts, ends, points);
        for (int x : cnt) {
            System.out.print(x + " ");
        }
        scanner.close();
    }
}

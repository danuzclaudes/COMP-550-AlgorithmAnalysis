package greedy_algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Grouping Children - Covering points by segments.
 * 
 * Organize n children into the minimum possible
 * number of groups such that the age of any
 * two children in the same group differ by at
 * most one year.
 *
 * Input: A set of n points x1 , . . . , xn ∈ R .
 * Output: The minimum number of segments of unit length
 *         needed to cover all the points.
 * Example: [38 44 54 60] -> [[38 50][54 66]]
 */
public class CoveringPoints {

    /**
     * Key idea: Greedy.
     * Safe move: cover the leftmost point with
     *            a unit segment which starts
     *            at this point.
     * While not all points are covered,
     * add a segment of unit length starting at leftmost;
     * forget all points covered in the segment;
     *
     * @param points    the array of points as children
     * @return          the array of generated segments
     */
    private static Segment[] optimalSegments(int[] points) {
        Arrays.sort(points);  // sort all points on the line
        List<Segment> listOfSegments = new ArrayList<>();
        int i = 0, n = points.length;
        while(i < n) {
            listOfSegments.add(new Segment(points[i], points[i] + 12));
            // BZ: points[i] <= points[i] + <L>? will always be true
            int right = points[i] + 12;
            while(i < n && points[i] <= right) i++;
        }
        return listOfSegments.toArray(new Segment[0]);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] points = new int[n];
        for (int i = 0; i < n; i++) {
            points[i] = scanner.nextInt();
        }
        Segment[] segments = optimalSegments(points);
        System.out.println(segments.length);
        for (Segment seg : segments) {
            System.out.print("[" + seg.start + " " + seg.end + "]");
        }
        scanner.close();
    }
    private static class Segment {
        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}

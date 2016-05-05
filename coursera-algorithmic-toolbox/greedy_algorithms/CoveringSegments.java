package greedy_algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Covering Segments by Points
 * 
 * Find a set of integers X of the minimum size
 * such that for any segment [ai, bi],
 * there is a point x ∈ X such that ai ≤ x ≤ bi.
 * Constraints: 1 ≤ n ≤ 100; 0 ≤ ai ≤ bi ≤ 10^9.
 * Example: 3, 1 3 2 5 3 6 -> 1, 3
 * Example: 4, 4 7 1 3 2 5 5 6 -> 2, 3 6
 *
 * Safe move: always select the min rightmost point.
 * Prove safety:
 *     Suppose there always exist some set of points
 *     of the minimum size, where these points are
 *     at any coordinate. Move the point to the right
 *     end. If no other right end to its left, still
 *     the covered by all segments. If there's a
 *     smaller right end to its left, choose that
 *     as selected point contained by segments and
 *     still the min number. 
 */
/* Test cases design:
 * -----Output-----
 * 1. smallest output, i.e. all stacked segments:
 *    [5,6][4,7][3,8][2,9] -> m=1, 6
 * 2. largest output, i.e. all separated segments:
 *    [1,2][3,4][5,6][7,8] -> m=n, 2 4 6 8
 * -----Input-----
 * 3. equal start and end, i.e. segments of zero length
 *    [4,4] -> m=1, 4
 * 4. ***equal start/end
 *    [3,5][3,7][3,9] -> m=1, 5
 *    [8,9][6,9][4,9] -> m=1, 9
 * 5. equal segments
 *    [2,8][2,8][2,8] -> m=1, 8
 * 6. smallest input, i.e. n = 1, just one segment
 *    [3,9] -> m=1, 9 
 * 7. largest output, i.e. n = 100?
 * 8. smallest start, i.e. ***longest possible segments
 *    [0,1000000000] -> m=1, 10^9
 * 9. largest end, i.e. segments with largest possible coordinates
 *    [1000000000, 1000000000] -> m=1, 10^9
 */
public class CoveringSegments {

    /**
     * Key idea: Greedy.
     * While not all segments are covered,
     * set the point at the min right end;
     * forget all segments that contain it.
     *
     * @param segments
     * @return
     */
    private static int[] optimalPoints(Segment[] segments) {
        // sort the segments according to their right end
        Arrays.sort(segments, new Comparator<Segment>(){
            @Override
            public int compare(Segment segment1, Segment segment2) {
                return segment1.end - segment2.end;
            }
        });

        List<Integer> listOfPoints = new ArrayList<>();
        int i = 0, n = segments.length;
        while (i < n) {
            int right = segments[i].end;
            listOfPoints.add(right);
            // BZ: must fix `right` since i is increasing
            while (i < n &&  segments[i].start <= right &&
                    right <= segments[i].end)
                i++;
        }

        int[] points = new int[listOfPoints.size()];
        for(int j = 0; j < listOfPoints.size(); j++)
            points[j] = listOfPoints.get(j);
        return points;
    }

    private static class Segment {
        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            segments[i] = new Segment(start, end);
        }
        int[] points = optimalPoints(segments);
        System.out.println(points.length);
        for (int point : points) {
            System.out.print(point + " ");
        }
        scanner.close();
    }
}

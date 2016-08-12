package spanning_trees;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Connecting Points.
 * <p>
 * Given n points on a plane, connect them with
 * segments of minimum total length such that
 * there is a path between any two points.
 * <p>
 * Example: 4  0 0 0 1 1 0 1 1 -> 3.0000000</br>
 * Example: 5  0 0 0 2 1 1 3 0 3 2 -> 7.064495102</br>
 * Example: 13  1 58 28 80 42 84 89 54 44 28 36 64 54 39 20 14 66 41 36 84 24 84 16 64 9 80
 */
public class ConnectingPoints {
    private static class DisjointSets {
        // BZ: Sets instead of Set; store all parents.
        int[] parents;
        // BZ: Rank heuristic to compress layers.
        int[] ranks;

        public DisjointSets (int n) {
            parents = new int[n];
            ranks = new int[n];
        }

        public void makeSet(int i) {
            parents[i] = i;
            ranks[i] = 1;
        }

        public int find(int i) {
            /**Find root of given node and compress tree on the path.*/
            // BZ: base case of root itself.
            if (parents[i] == i) return i;
            parents[i] = find(parents[i]);
            return parents[i];
        }

        public void union(int i, int j) {
            /**Link the "shorter" tree under the "taller" one.*/
            int r1 = find(i), r2 = find(j);
            // BZ: If same root then no need to union.
            if (r1 == r2) return;
            if (ranks[r1] < ranks[r2]) {
                parents[r1] = r2;
                // BZ: same rank for taller root.
            }
            else if (ranks[r2] < ranks[r1]) {
                parents[r2] = r1;
            }
            else {
                parents[r2] = r1;
                ranks[r1] += 1;
            }
        }
    }

    private static class Edge {
        int u, v;
        double w;
        public Edge (int u, int v, double w) {
            this.u = u; this.v = v; this.w = w;
        }
        public String toString() {
            return u + "\t" + v + "\t" + w;
        }
    }

    private static double distance(int x1, int y1, int x2, int y2) {
        /**Compute distance between points a and b.*/
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        // return new DecimalFormat("#0.0000000").format(Math.sqrt(res));
    }

    /**
     * Build up Disjoint Sets data structure.
     * Initialize each vertex as a disjoint set.
     * Permute all Edges with distance as weight.
     * Sort all edges by weights.
     * Traverse each edge in increasing order,
     * merge the vertices if they belong to different roots.
     */
    private static double minimumDistance(int[] x, int[] y) {
        double result = 0.;
        int n = x.length;
        DisjointSets sets = new DisjointSets(n);
        PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.w < e2.w ? -1 : 1;
            }
        });
        for (int i = 0; i < n; i++) sets.makeSet(i);
        // Permute all vertices to populate Edges.
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                pq.offer(new Edge(i, j, distance(x[i], y[i], x[j], y[j])));
            }
        }
        // Traverse each edge in increasing order.
        while (! pq.isEmpty()) {
            Edge lightest = pq.poll();
            System.out.println(lightest.toString());
            if (sets.find(lightest.u) != sets.find(lightest.v)) {
                sets.union(lightest.u, lightest.v);
                result += lightest.w;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        System.out.println(
            new DecimalFormat("#0.0000000").format(minimumDistance(x, y))
        );
        scanner.close();
    }
}

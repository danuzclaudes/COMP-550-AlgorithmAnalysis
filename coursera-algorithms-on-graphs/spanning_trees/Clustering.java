package spanning_trees;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Clustering.
 * <p>
 * Given n points on a plane and an integer k,
 * compute the largest possible value of d such that the
 * given points can be partitioned into k non-empty subsets
 * in such a way that the distance between any
 * two points from different subsets is at least d.
 * <p>
 * BZ: How to ensure K clusters?
 *     <pre>`MST of N nodes must have N-1 edges.`</pre>
 *     Consider meta-graph G' of K clusters as K vertices;
 *     G' is still MST with K-1 edges;</br>
 *     find the top K-1 largest edges and the smallest will be d.</br>
 * Example: 12  7 6 4 3 5 1 1 7 2 7 5 7 3 3 7 8 2 8 4 4 6 7 2 6  3</br>
 *          -> 2.828427124746</br>
 * Example: 8  3 1  1 2 4 6 9 8 9 9 8 9 3 11 4 12  4</br>
 * Example: 6  0 0 0 1 0 -1 1 0 -1 0 0 2  2 -> 1.0
 */
public class Clustering {
    private static class DisjointSets {
        // BZ: Sets instead of Set; store all parents.
        int[] parents;
        // BZ: Heuristic method to compress layers.
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
    }

    /**
     * Build Disjoint Sets.
     * Generate all edges.
     * Run Kruskal's algorithm to generate MST.
     * Find top K-1 largest edges.
     * Return the K-1 largest as d.
     *
     * @param x
     * @param y
     * @param k
     * @return
     */
    private static double clustering(int[] x, int[] y, int k) {
        int n = x.length;
        DisjointSets sets = new DisjointSets(n);
        for (int i = 0; i < n; i++) sets.makeSet(i);
        // Generate all edges and offer into PQ.
        Comparator<Edge> cmp = new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.w < e2.w ? -1 : 1;
            }
        };
        PriorityQueue<Edge> pq = new PriorityQueue<>(cmp);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                pq.offer(new Edge(i, j, distance(x[i], y[i], x[j], y[j])));
            }
        }
        // Collections.reverseOrder(cmp).
        // BZ: initial capacity, instead of fixed capacity.
        PriorityQueue<Edge> mst = new PriorityQueue<>(k - 1, Collections.reverseOrder(cmp));
        while (! pq.isEmpty()) {
            Edge lightest = pq.poll();
            // System.out.println(lightest.toString());
            if (sets.find(lightest.u) != sets.find(lightest.v)) {
                sets.union(lightest.u, lightest.v);
                mst.offer(lightest);
            }
        }
        // BZ: How to find the (K-1)th largest edge?
        for (Edge e : mst) System.out.println(e.toString());
        for (int i = 1; i <= k - 2; i++) mst.poll();
        return mst.peek().w;
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
        int k = scanner.nextInt();
        System.out.println(clustering(x, y, k));
        scanner.close();
    }
}


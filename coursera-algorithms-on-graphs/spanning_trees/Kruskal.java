package spanning_trees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/***
 * Kruskal's algorithm implementation.
 * <pre>
 * Repeatedly add the next lightest edge if no cycle:
 *     Build DisjointSet data structure.
 *     Initialize each vertex as a disjoint set.
 *     Traverse each vertex by increase weight.
 *     Merge the vertices between current edge,
 *         if not same root.
 * </pre>
 */
public class Kruskal {
    class DisjointSets {
        /**The disjoint set ADT.*/
        private int[] parents;
        private int[] ranks;

        /**
         * Construct a disjoint sets object.
         *
         * @param n The initial number of elements.
         */
        public DisjointSets (int n) {
            parents = new int[n];
            ranks = new int[n];
        }

        void makeSet(int i) {
            parents[i] = i;
            ranks[i] = 1;
        }

        int find(int i) {
            /**Find super parent and compress path.*/
            if (parents[i] == i) return i;
            // Update parent of all nodes along the path till real root.
            parents[i] = find(parents[i]);
            return parents[i];
        }

        void union(int i, int j) {
            /**Link the "shorter" tree to the "taller".*/
            int r1 = find(i), r2  = find(j);
            // BZ: i and j belongs to same set.
            if (r1 == r2) return;
            if (ranks[r1] < ranks[r2]) {
                parents[r1] = r2; // Rank of r2 is unchanged.
            }
            else if (ranks[r1] > ranks[r2]) {
                parents[r2] = r1; // Rank of r1 is unchanged.
            }
            else {
                // Link r2 under r1 while r1 will become one layer taller.
                parents[r2] = r1;
                ranks[r1] += 1;
            }
        }
    }

    class Edge {
        /**The edge of a graph.*/
        int u;
        int v;
        int w;
        public Edge (int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    public int minSpanningTree(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        /**Repeatedly add the next lightest edge if no cycle.*/
        int n = adj.length;
        // MakeSet(i)
        DisjointSets sets = new DisjointSets(n);
        for (int i = 0; i < n; i++) sets.makeSet(i);
        // Sort all edges by weight.
        PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.w - e2.w;
            }
        });
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < adj[i].size(); j++) {
                pq.offer(new Edge(i, adj[i].get(j), cost[i].get(j)));
            }
        }
        // Maintain minimum spanning tree weights. (or MST set)
        int result = 0;
        // Traverse each edge in increasing weight order. (How?)
        while (! pq.isEmpty()) {
            Edge lightestEdge = pq.poll();
            // If two vertices u and v belong to S and V-S, X + {e} is still MST.
            if (sets.find(lightestEdge.u) != sets.find(lightestEdge.v)) {
                sets.union(lightestEdge.u, lightestEdge.v);
                result += lightestEdge.w;  // Or add e into MST.
            }
        }
        return result;
    }
}

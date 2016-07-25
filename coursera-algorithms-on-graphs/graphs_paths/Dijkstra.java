package graphs_paths;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/***
 * Computing the Minimum Cost of a Flight. (Min Cost)
 * </br>
 * Given an directed graph with positive edge weights</br>
 * and with n vertices and m edges as well as two vertices</br>
 * u and v, compute the weight of a shortest path between</br>
 * u and v (that is, the minimum total weight of a path from u to v).
 */
public class Dijkstra {
    /**
     * Key Idea:
     * Merge min dist into known region R.</br>
     * Relax its adjacent edges and update dist by duplication.</br>
     * Forget about the min dist (already known).</br>
     * Reconstruct to accumulate all weights/dist.
     * </br>
     * @param adj   The list of adjacent edges.
     * @param cost  The weight of each edge.
     * @param s     The source vertex.
     * @param t     The target vertex.
     * @return      The minimum weight of a path from u to v,
     *     or −1 if there is no path.
     * </br>
     * How to relax? dist[v] > dist[u] + weight.
     * How to get weight? same index as of adj[u].
     */
    public static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        int n = adj.length;
        // Initialize dist[] by inf, except source s.
        // dist [v] will be an upper bound on the actual distance from S to v.
        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }
        dist[s] = 0;
        // Traverse each vertex outside of known region R.
        for (int j = 0; j < n; j++) {
            int u = minDistVertex(dist, visited);
            // BZ: returned minVertex is -1? unreachable from S.
            if (u == -1) continue;
            visited[u] = true;
            for (int i = 0; i < adj[u].size(); i++) {
                int v = adj[u].get(i), w = cost[u].get(i);
                if (dist[v] > dist[u] + w) {
                    dist[v] = dist[u] + w;
                    // BZ: update previous node of v as well for reconstruction?
                    prev[v] = u;
                }
            }
        }
        // BZ: no need to reconstruct.
        // After the call to algorithm, all the distances are set correctly.
        return dist[t] == Integer.MAX_VALUE ? -1 : dist[t];
    }

    public static int minDistVertex(int[] dist, boolean[] visited) {
        /**Find the vertex with min dist and not yet visited.*/
        int minDist = Integer.MAX_VALUE, minVertex = -1;
        for (int v = 0; v < dist.length; v++) {
            if (visited[v]) continue;
            if (dist[v] < minDist) minVertex = v;
            minDist = Math.min(minDist, dist[v]);
        }
        return minVertex;
    }

    /**
     * Runtime if using array: O(|V|^2).</br>
     * Runtime if using PriorityQueue: O((|V| + |E|) * log|V|).</br>
     * BZ: Priority Queue stores same integer?<ul>
     *     <li>when comparing dist[v], will stop by same v...
     *     <li>=> Wrap {v, dist-value}; sort PQ on dist-value.
     *     <li>=> Even offering same v, the dist-value differs.</ul>
     */
    private static class DistNode {
        int v;
        int dist;
        public DistNode(int v, int d) {
            this.v = v;
            this.dist = d;
        }
    }
    private static int distance_faster(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        int n = adj.length;
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        PriorityQueue<DistNode> pq = new PriorityQueue<>(new Comparator<DistNode>(){
            @Override
            public int compare(DistNode v1, DistNode v2) {
                // BZ: v1.dist - v2.dist? if v2.dist is +inf? overflow!
                return v1.dist < v2.dist ? -1 : 1;
            }
        });
        for (int i = 1; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            pq.offer(new DistNode(i, Integer.MAX_VALUE));
        }
        dist[0] = 0;
        pq.offer(new DistNode(0, 0));
        while (! pq.isEmpty()) {
            DistNode u = pq.poll();
            if (visited[u.v]) continue;
            visited[u.v] = true;
            for (int i = 0; i < adj[u.v].size(); i++) {
                int next = adj[u.v].get(i), w = cost[u.v].get(i);
                if (dist[next] > dist[u.v] + w) {
                    dist[next] = dist[u.v] + w;
                    pq.offer(new DistNode(next, dist[next]));
                }
            }
        }
        return dist[t] == Integer.MAX_VALUE ? -1 : dist[t];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println("Using array: " + distance(adj, cost, x, y));
        System.out.println("Using PQ: " + distance_faster(adj, cost, x, y));
        scanner.close();
    }
}
/**BZ: 9 15
  1 2 4
  2 3 8
  3 4 7
  4 5 9
  1 8 8
  8 7 1
  7 6 2
  6 5 10
  2 8 11
  3 9 2
  8 9 7
  7 9 6
  9 7 6
  3 6 4
  6 4 14
  1 X
 */

package bellman_ford;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Find negative cycles.
 * <p>
 * Given an directed graph with possibly negative edge weights
 * and with n vertices and m edges, check whether it contains
 * a cycle of negative weight.<p>
 * Example: 4 4 (multi-source)</br>
 *          1 2 -5 4 1 2 2 3 2 3 1 1</br>
 * Example: 4 3</br>
 *          1 2 -1 2 3 -2 3 4 -3 (0)</br>
 * Example: 3 2 (multi-source)</br>
 *          2 3 1 3 2 -2 -> BZ: -ve cycle is unreachable from S.</br>
 * Example: 5 5 (a negative loop that is not off the 0 node)</br>
 *          1 2 1 3 1 1 3 4 -1 4 5 -1 5 3 -1
 * Example: 5 4</br>
 *          1 2 1 4 1 2 2 3 2 3 1 -5<p>
 * BZ: Multiple Sources: unreachable vertex from S=1?
 *     dist[u] = inf; dist[u] + W(u, v) will overflow: +inf + 1 = -inf;
 *     will thus update dist[v] by -inf.</br>
 *     => Single Source Shortest Paths only? Solution:</br>
 *     => 1) virtual node? not good for multiple connected components;</br>
 *     => 2) <b>initialize dist[] by 0</b></br>
 * BZ: Why initialize dist by 0?</br>
 *     - After V-1 iterations, if there are negative weights in G,
 *       will have some negative values in dist[], b/c start at zero.</br>
 *     - If there is no negative cycle, the dist[] vector will only
 *       contain `the same value as the negative weight`.</br>
 *     - If there is a negative cycle, the dist[] vector will be
 *       changed by lower than the negative weight.</br>
 *     - So <b>run the Bellman-Ford algorithm with distances initialized to zero.</b>
 *       If V-th changed, then it has a negative cycle.
 */
public class NegativeCycle {
    /**
     * Key Idea:<pre>
     * Run BellmanFord which relaxes |V| - 1 times.
     * There's a -ve cycle iif the |V|-th iteration still relaxes.</pre>
     *
     * @param adj
     * @param cost
     * @return
     */
    public static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        int n = adj.length;
        int[] dist = new int[n];
        // BZ: run the Bellman-Ford algorithm with distances initialized to zero.
        for (int i = 1; i < n; i++)    dist[i] = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {  // BZ: repeat |V| - 1 times.
            for (int u = 0; u < n; u++)  // Relax on each edge.
                relax(u, adj[u], cost[u], dist);
        }
        for (int i = 0; i < n; i++) System.out.print(i + ": " + dist[i] + "\t");
        System.out.println();
        // The |V|-th iteration: above Bellman-Ford run guarantees
        // shortest distances if graph doesn't contain negative weight cycle.
        // But there's a cycle if we get a shorter path this time.
        for (int u = 0; u < n; u++) {
            if (relax(u, adj[u], cost[u], dist)) return 1;
        }
        return 0;
    }

    private static boolean relax(int u, ArrayList<Integer> adjU, ArrayList<Integer> costU, int[] dist) {
        // Relax all edges coming out from u.
        boolean distChanged = false;
        for (int i = 0; i < adjU.size(); i++) {
            int v = adjU.get(i);
            if (dist[u] != Integer.MAX_VALUE && dist[v] > dist[u] + costU.get(i)) {
                dist[v] = dist[u] + costU.get(i);
                distChanged = true;
            }
        }
        return distChanged;
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
        System.out.println(negativeCycle(adj, cost));
        scanner.close();
    }
}

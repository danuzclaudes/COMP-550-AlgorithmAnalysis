package bellman_ford;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Bellman-Ford Algorithm.
 * <p>
 * Bottom-Up:<pre>
 * It first calculates the shortest paths with at-most one edge.
 * It then calculates shortest paths with at-most 2 edges, and so on.
 * After the ith iteration of outer loop,
 *     the shortest paths with at most i edges are calculated.
 * There can be maximum |V| – 1 edges in any simple path,
 * that is why the outer loop runs |v| – 1 times.
 * </pre>
 * Example: 5 7</br>
 *          1 2 4 1 3 3 2 3 -2 2 4 4 3 4 -3 3 5 1 4 5 2</br>
 * Example: 5 8</br>
 *          2 5 2 4 2 1 2 4 2 1 2 -1 1 3 4 4 3 5 2 3 3 5 4 -3<p>
 * http://www.geeksforgeeks.org/dynamic-programming-set-23-bellman-ford-algorithm/
 */
public class BellmanFord {

    public static void shortestPath_BellmanFord(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        /**Assume there's no negative cycle in directed G.*/
        /**Traverse |V|-1 times and relax on all edges.*/
        int n = adj.length;
        int[] dist = new int[n];
        int[] prev = new int[n];
        // Initialize distances from S to all other vertices as +inf.
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }
        dist[0] = 0;
        // BZ: repeat |V| - 1 times.
        for (int i = 1; i < n; i++) {
            // Relax on each edge: |E| times.
            for (int u = 0; u < n; u++)
                relax(u, adj[u], cost[u], dist, prev);
        }
        for (int i = 0; i < n; i++) System.out.print(i + ": " + dist[i] + "\t");
    }

    private static void relax(int u, ArrayList<Integer> adjU, ArrayList<Integer> costU,
            int[] dist, int[] prev) {
        for (int idx = 0; idx < adjU.size(); idx++) {
            int v = adjU.get(idx);
            if (dist[v] > dist[u] + costU.get(idx)) {
                dist[v] = dist[u] + costU.get(idx);
                prev[v] = u;
            }
        }
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
        shortestPath_BellmanFord(adj, cost);
        scanner.close();
    }
}

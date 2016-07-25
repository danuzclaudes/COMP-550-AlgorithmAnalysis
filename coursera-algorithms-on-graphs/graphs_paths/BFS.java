package graphs_paths;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Computing the Minimum Number of Flight Segments. (Min Transfer)
 * </br>
 * Given an undirected graph with n vertices and m edges</br>
 * and two vertices u and v, compute the length of a</br>
 * shortest path between u and v (that is, the minimum</br>
 * number of edges in a path from u to v).
 */
public class BFS {
    private static int distance(ArrayList<Integer>[] adj, int s, int t) {
        /**BFS: add all vertices on same layer before next layer.*/
        int[] dist = new int[adj.length];
        for (int i = 0; i < dist.length; i++) dist[i] = Integer.MAX_VALUE;
        dist[s] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        while (! queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj[u]) {
                if (dist[v] == Integer.MAX_VALUE) {
                    queue.offer(v);
                    dist[v] = dist[u] + 1;
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
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, x, y));
        scanner.close();
    }
}

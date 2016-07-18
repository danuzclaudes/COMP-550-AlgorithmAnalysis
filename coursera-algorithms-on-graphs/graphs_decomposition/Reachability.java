package graphs_decomposition;

import java.util.ArrayList;
import java.util.Scanner;

public class Reachability {
    private static int reach(ArrayList<Integer>[] adj, int x, int y) {
        /**DFS to explore connectivity between x and y.*/
        boolean[] visited = new boolean[adj.length];
        // Start from x; no need to try all starting points.
        return dfs(adj, x, y, visited);
    }
    
    private static int dfs(ArrayList<Integer>[] adj, int src, int dst, boolean[] visited) {
        // Each vertex is mapped from 1-n to index of adj.
        if (src == dst) return 1;
        // Mark x visited.
        visited[src] = true;
        // Recursively explore all unvisited adjacent vertices.
        for (int neighbor : adj[src]) {
            if (! visited[neighbor])
                if(dfs(adj, neighbor, dst, visited) == 1) return 1;
        }
        // Mark once explored.
        // If no path-to-y thru current x, no need to explore again.
        // visited[src] = false;
        return 0;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
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
        System.out.println(reach(adj, x, y));
        scanner.close();
    }
}

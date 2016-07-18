package graphs_decomposition;

import java.util.ArrayList;
import java.util.Scanner;

public class ConnectedComponents {
    private static int numberOfComponents(ArrayList<Integer>[] adj) {
        /**Connected Components of undirected graph.*/
        boolean[] visited = new boolean[adj.length];
        int result = 1;
        for (int i = 0; i < adj.length; i++) {
            if (! visited[i]) {
                dfs(i, adj, visited);
                // After exhausted exploration, step to a new CC.
                result++;
            }
        }
        return result - 1;
    }

    private static void dfs(int vertex, ArrayList<Integer>[] adj, boolean[] visited) {
        // All connected vertices have same count of CC.
        visited[vertex] = true;
        for (int neighbor : adj[vertex]) {
            if (! visited[neighbor])
                dfs(neighbor, adj, visited);
        }
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
        System.out.println(numberOfComponents(adj));
        scanner.close();
    }
}


package graphs_decomposition;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Detect cycle in directed/undirected graph.
 * </br>
 * Example: [1->2, 2->3, 3->4, 4->2], 1.</br>
 * Example: [1->2, 1->3, 2->3], 0.</br>
 * Example: [1->2, 3->1, 2->3], 1.</br>
 */
public class Acyclicity {
    public static int acyclic(ArrayList<Integer>[] adj) {
        /**Directed graphs: if adj node is visited on recursion stack.*/
        int n = adj.length;
        boolean[] visited = new boolean[n];
        boolean[] onstack = new boolean[n];
        for (int v = 0; v < n; v++) {
            if (! visited[v]) {
                if (dfs(v, adj, visited, onstack) == 1) return 1;
            }
        }
        return 0;
    }

    private static int dfs(int vertex, ArrayList<Integer>[] adj,
            boolean[] visited, boolean[] onstack) {
        visited[vertex] = true;
        onstack[vertex] = true;
        for (int w : adj[vertex]) {
            // If adjacent node is unvisited: recursively explore.
            if (! visited[w]) {
                if (dfs(w, adj, visited, onstack) == 1) return 1;
            }
            // BZ: adjacent node is visited but not on stack: continue.
            // BZ: adjacent node is visited and on stack: found cycle.
            if (visited[w] && onstack[w]) return 1;
        }
        // All neighbors of current v have been explored, pop v from stack.
        onstack[vertex] = false;
        return 0;
    }

    public int acyclic_undirected(ArrayList<Integer>[] adj) {
        /**Undirected graphs: if adj node is back edge, except to parent.*/
        boolean[] visited = new boolean[adj.length];
        for (int v = 0; v < adj.length; v++) {
            if (visited[v]) continue;
            if (dfs_undirected(adj, v, v, visited) == 1) return 1;
        }
        return 0;
    }
    /***
     * DFS to check if adjacent vertex w is visited and not parent.
     * u-v-w: v `might try the adjacent edge back` to its parent u,
     *        so must remember current v's parent.
     *        If the adjacent w is still visited, then w is ancestor (back edge).
     * @param adj The array of lists containing adjacent edges for each vertex.
     * @param v The current node.
     * @param u The parent node of v.
     * @return  The result if this graph has cycle [1], or not [0].
     */
    private int dfs_undirected(ArrayList<Integer>[] adj, int v, int u, boolean[] visited) {
        visited[v] = true;
        for (int w : adj[v]) {
            // If adjacent node is unvisited: recursively explore.
            if (! visited[w] && dfs_undirected(adj, w, v, visited) == 1)
                return 1;
            // BZ: adjacent node is visited and is not parent: found cycle.
            if (visited[w] && w != u) return 1;
        }
        return 0;
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
        }
        System.out.println(acyclic(adj));
        scanner.close();
    }


    /**
     * O(|V|^2): DFS but still remove one source/sink at a time.
     * Performance Issue: [1->2->3->4].
     * Detect from 1 to 4. But 4,3,2,1 get unmarked again.
     * Further check 2-3-4, 3-4, 4
     */
    public static int acyclic2(ArrayList<Integer>[] adj) {
        boolean[] visited = new boolean[adj.length];
        for (int i = 0; i < adj.length; i++) {
            if (dfs(i, adj, visited) == 1) return 1;
        }
        return 0;
    }
    private static int dfs(int v, ArrayList<Integer>[] adj, boolean[] visited) {
        visited[v] = true;
        for (int neighbor : adj[v]) {
            if (visited[neighbor]) return 1;
            // BZ: if subprocess already detects a cycle.
            if (dfs(neighbor, adj, visited) == 1) return 1;
        }
        // Only mark in same longest path???
        // BZ: Unmarked nodes will retrace same path every time.
        visited[v] = false;
        return 0;
    }
}

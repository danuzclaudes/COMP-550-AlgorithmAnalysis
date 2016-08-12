package spanning_trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Prim's algorithm implementation.
 * <p>
 * Repeatedly attach a new vertex to the current tree by a lightest edge.
 * <p>
 * BZ: Why relax cost on each unvisited vertex?</br>
 *     (1) Like Dijkstra, cost[v] might be changed by closer/lighter paths.</br>
 *     (2) Dijkstra's positive cycles won't update distance;
 *         while in Prim's, v gets attached as u->v, but then v->u may overwrite u;
 *         but u cannot be attached from v.</br>
 *         => `Relax unvisited adjacent vertices only.`</br>
 * Example: 6 9  1 2 4 2 3 8 1 4 2 1 5 1 2 5 5 2 6 6 3 6 1 4 5 3 5 6 9</br>
 * Example: 3 4  1 2 8 1 3 6 2 3 1 3 2 1
 */
public class Prim {

    /**
     * Take the min cost among unvisited vertices.
     * Relax all its outgoing edges.
     * Forget about the min cost (already known).
     * Return lightest total weight by summing up all costs.
     *
     * @param adj
     * @param cost
     * @return
     */
    public static int minSpanningTree(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        int n = adj.length;
        // The cost of attaching each vertex to tree.
        int[] mst = new int[n];
        boolean[] visited = new boolean[n];
        // Initialize attaching cost as +inf.
        Arrays.fill(mst, Integer.MAX_VALUE);
        mst[0] = 0;
        // Traverse each vertex outside of MST.
        for (int i = 0; i < n; i++) {
            int u = minCostVertex(mst, visited);
            // if (u == -1) continue;  // No minimum cost vertex can be found?
            visited[u] = true;
            for (int j = 0; j < adj[u].size(); j++) {
                // BZ: `Relax unvisited adjacent vertices only.`
                int v = adj[u].get(j), c = cost[u].get(j);
                if (! visited[v] && mst[v] > c)
                    mst[v] = c;
            }
        }
        // Compute lightest total weight.
        int res = 0;
        for (int c : mst) res += c;
        return res;
    }

    private static int minCostVertex(int[] mst, boolean[] visited) {
        int u = -1, minCost = Integer.MAX_VALUE;
        for (int i = 0; i < mst.length; i++) {
            if (! visited[i] && mst[i] < minCost) {
                u = i;
                // BZ: cannot update minCost if current vertex is visited.
                minCost = Math.min(minCost, mst[i]);
            }
        }
        return u;
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
            adj[y - 1].add(x - 1);
            cost[x - 1].add(w);
            cost[y - 1].add(w);
        }
        System.out.println(minSpanningTree(adj, cost));
        scanner.close();
    }
}

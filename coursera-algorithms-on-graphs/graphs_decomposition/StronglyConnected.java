package graphs_decomposition;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Compute the number of strongly connected components.
 * </br>
 * <h3>How to get one SCC of a vertex?</h3>
 *     Consider a 'sink' vertex; explore all reachable vertices in it;
 *     the sinking component is a SCC.</br>
 * <h3>How to find the sink SCC of G?</h3>
 *     If C->C', then post(C) > post(C') [Max post is in a source CC].
 *     Find max post in a source CC of GR = same v in sink CC of G [symmetric].
 *     DFS for all source CCs in GR in postorder [scale].
 *     Reverse postorder to get a node in sink CC of G.
 *     Get rid of each sink CC as SCC.</br>
 * Example: 9 13</br> 1 4 1 5 2 1 3 1 5 2 5 3 5 8 6 2 6 7 7 8 8 9 9 8 9 4 -> 5.
 * Example: 4 4</br> 1 2 4 1 2 3 3 1 -> 2
 * Example: 5 7</br> 2 1 3 2 3 1 4 3 4 1 5 2 5 3 -> 5
 */
public class StronglyConnected {
    /**
     * Key Idea:<pre>
     * DFS for all source CCs in GR in postorder. (how?)
     * For each sink CC in G remained,
     * explore all connected vertices in CC to remove sink CC;
     * count a SCC after this removal.</pre>
     */
    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {
        // How to DFS on reverse graph? How to get GR?
        ArrayList<Integer>[] adjR = reverseGraph(adj);
        // DFS on GR for all source CCs in postorder.
        Stack<Integer> post = new Stack<>();
        boolean[] visited = new boolean[adj.length];
        for (int v = 0; v < adj.length; v++) {
            if (! visited[v]) dfs(adjR, v, visited, post);
        }
        int res = 0;
        visited = new boolean[adj.length];
        // Reverse from max post source in GR, i.e. sink CC in G.
        while (! post.isEmpty()) {
            int v = post.pop();
            // BZ: continue if this v in source CC is visited.
            if (visited[v]) continue;
            // Explore all connected from v inside the sink CC.
            dfs(adj, v, visited, null);
            res++;
        }
        return res;
    }

    private static ArrayList<Integer>[] reverseGraph(ArrayList<Integer>[] adj) {
        /**Reverse G to GR: O(V+E)*/
        ArrayList<Integer>[] res = new ArrayList[adj.length];
        // Initialize empty adjacency list for each vertex. O(V)
        for (int v = 0; v < adj.length; v++) res[v] = new ArrayList<>();
        // for each vertex v, add v into reverse list of its neighbors.
        // BZ: O(V*E)? O(V+E)? traverse each vertex and each edge once.
        for (int v = 0; v < adj.length; v++) {
            for (int w : adj[v]) {
                res[w].add(v);
            }
        }
        return res;
    }

    private static void dfs(ArrayList<Integer>[] adj, int v,
            boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        for (int w : adj[v]) {
            if (! visited[w]) {
                dfs(adj, w, visited, stack);
            }
        }
        // Postorder(): push all post vertex.
        if (stack != null) stack.push(v);
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
        System.out.println(numberOfStronglyConnectedComponents(adj));
        scanner.close();
    }
}

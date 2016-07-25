package graphs_paths;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Checking whether a Graph is Bipartite.</br>
 * </br>
 * Given an undirected graph with n vertices and m edges,
 * check whether it is bipartite.</br>
 * Check if its vertices can be colored with two colors</br>
 * (say, black and white) such that the endpoints of each</br>
 * edge have different colors.
 * </br>
 * <h3>Test Cases:</h3><ul>
 * <li>single node: [b]; single node w/ self-loop? 0.</li>
 * <li>two nodes separated: [b],[w]; [b],[b];</li>
 * <li>two nodes connected: [b-w].</li>
 * <li>3 nodes:<ul>
 *     <li>1-black [b-ww]: 3 3 1 2 2 3 1 3;</li>
 *     <li>2-black [b-w-b]: 3 2 1 2 2 3;</li></ul>
 * <li>4 nodes:<ul>
 *     <li>split [b-www]: 4 3 1 2 1 3 1 4</li>
 *     <li>split [b-www] but edge between w: 4 4 1 2 1 3 1 4 4 3</li>
 *     <li>square [b-w-b-w] but edge between w/b: 4 5 1 2 2 3 3 4 4 1 2 4</li>
 *     <li>star: [b-w-bb]: 4 3 1 2 2 3 2 4</li>
 *     <li>star: [w-b-ww] but edge between b: 4 4 1 2 2 3 2 4 1 4</li></ul>
 * </ul>
 */
public class Bipartite {
    /**
     * Key Idea: BFS.</br>
     * Mark layers by black/white alternatively: flip color to next.</br>
     * If next is visited and same color, found an edge w/ same color.
     */
    private static int bipartite(ArrayList<Integer>[] adj) {
        int[] dist = new int[adj.length];
        boolean[] color = new boolean[adj.length];
        for (int i = 0; i < adj.length; i++) dist[i] = Integer.MAX_VALUE;
        dist[0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (! queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj[u]) {
                if (dist[v] == Integer.MAX_VALUE) {
                    queue.offer(v);
                    dist[v] = dist[u] + 1;
                    color[v] = ! color[u];
                }
                // V is visited and same color w/ u, no bipartite.
                else if (color[v] == color[u]) return 0;
            }
        }
        return 1;
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
        System.out.println(bipartite(adj));
        scanner.close();
    }
}

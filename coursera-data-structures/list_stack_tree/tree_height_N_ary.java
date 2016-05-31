package list_stack_tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Compute tree height.
 * <p>
 * Given an arbitrary tree, not necessarily a binary tree.
 * The height of a (rooted) tree is the maximum depth of a
 * node, or the maximum distance from a leaf to the root.
 * Constraints: 1 ≤ n ≤ 10^5
 * Example: 10, 9 7 5 5 2 9 9 9 2 -1 -> 4
 */
public class tree_height_N_ary {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public class TreeHeight {
        int n;
        int parent[];
        // Flag on if reading keyboard input or files in a directory
        boolean readFiles = true;

        void read() throws IOException {
            File folder = new File("/home/chongrui/Downloads/pa1/pa1-tree");
            if (! folder.exists()) {
                FastScanner in = new FastScanner();
                n = in.nextInt();
                parent = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = in.nextInt();
                }
                readFiles = false;
                return;
            }
            File[] files = folder.listFiles();
            Arrays.sort(files, new Comparator<File>(){
                @Override
                public int compare (File f1, File f2) {
                    return f1.getName().compareTo(f2.getName());
                }
            });
            for (File test : files) {
                if (test.getName().equals("report")) continue;
                System.out.print(test.getName() + ": ");
                FileReader input_stream = new FileReader(test);
                BufferedReader reader = new BufferedReader(input_stream);
                this.n = Integer.parseInt(reader.readLine());
                this.parent = new int[n];
                String[] in = reader.readLine().split(" ");
                if (in.length != n) {
                    System.out.println("Input numbers error: n="
                            + n + " in=" + Arrays.toString(in));
                    continue;
                }
                for (int i = 0; i < n; i++) {
                    parent[i] = Integer.parseInt(in[i]);
                }
                this.measure_and_print();
                reader.close();
            }
        }
        int computeHeight_naive() {
            // Replace this code with a faster implementation
            int maxHeight = 0;
            for (int vertex = 0; vertex < n; vertex++) {
                int height = 0;
                for (int i = vertex; i != -1; i = parent[i])
                    height++;
                maxHeight = Math.max(maxHeight, height);
            }
            return maxHeight;
        }

        /**
         * Key Idea:
         * How to identify root? -1 in the cell...
         * How to identify leaf? missing in parent array:
         *     parent[i] is node i's parent,
         *     i.e. an internal node;
         *     e.g. -1 0 4 0 3, 1 & 2 are leaves
         * How to identify deepest leaf? trace to upper level:
         *     while current node is not root,
         *     count the path length,
         *     and go to its parent: i = parent[i]
         *
         * @return  the height of tree, i.e. path length from
         *          deepest leaf to root + 1
         */
        int computeHeight_fast() {
            int maxHeight = 0, root = -1;
            // Identify root first? each node i stops at p[i]=-1=root
            for (int j = 0; j < n; j++) {
                /* BZ: change outer iterator in both outer and inner loop? */
                int currentHeight = 0, i = j;
                while (parent[i] != root) {
                    i = parent[i];
                    currentHeight++;
                }
                maxHeight = Math.max(maxHeight, currentHeight);
            }
            return maxHeight + 1;
        }

        /**
         * Helper function to measure timing of fast algorithm and
         * compare the result of naive one.
         */
        private void measure_and_print () {
            long start = System.currentTimeMillis();
            int fast   = computeHeight_fast();
            long end   = System.currentTimeMillis();
            int naive  = computeHeight_naive(); // int naive = fast;
            System.out.println((naive == fast ? "OK" :
                    "Error!") + " naive=" + naive + " fast=" + fast + 
                    " timing=" + (end - start));
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new tree_height_N_ary().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        TreeHeight tree = new TreeHeight();
        tree.read();
        if (! tree.readFiles) {
            tree.measure_and_print();
        }
    }
}

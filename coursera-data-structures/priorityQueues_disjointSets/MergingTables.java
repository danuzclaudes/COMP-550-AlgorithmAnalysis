package priorityQueues_disjointSets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Merging tables
 * <p>
 * Give n tables, where the ith table
 * has ri rows initially. Each table can
 * have a symbolic link to another table.
 * Perform m operations to traverse the
 * path of symbolic links of i and j, copy
 * all the rows from table i to table j,
 * then clear the table source i and put a
 * symbolic link to j only. Print the max
 * size among all n tables.
 * <p>
 * Example:     Example:
 * 5 5          6 4
   1 1 1 1 1    10 0 5 0 3 3
   3 5: 2       6 5
   2 4: 2       5 4
   1 4: 3       4 3
   5 4: 5       6 6
   5 3: 5
 * Example: A big random test with 100000 tables
 *          and 99999 merge operations.
 */
public class MergingTables {
    private final InputReader reader;
    private final OutputWriter writer;

    public MergingTables(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new MergingTables(reader, writer).run();
        writer.writer.flush();
    }

    /**
     * Key Idea:
     * Maintain counts/ranks/parents for all tables.
     * Initially each table points to itself.
     * Try all m merges to union(i, j).
     * If find(i)==find(j), return.
     * Link the root w/ lower rank to the other.
     * Increase the rank if both's ranks are same.
     * Accumulate r[rd] += r[rs], and only the rd.
     * clear rank of real source, update global max
     */
    class Table {
        // The symbolic link to the destination root;
        // initially pointing to itself
        Table parent;
        // rank[i] is the height of the subtree whose root is i
        // BZ: link the lower rank to the larger to compress
        int rank;
        // The count of rows in the table
        int numberOfRows;

        Table(int numberOfRows) {
            this.numberOfRows = numberOfRows;
            rank = 0;
            parent = this;
        }
        /**Find super parent and compress path.
         * Update parent of all nodes
         * along the path till real root.
         * Find(i):
         *     if i!= parent[i]:
         *         parent[i] = Find(parent[i])
         *     return parent[i]
         * BZ: use `if` instead of `while`...
         * BZ: compress the whole path to the root,
         *     not just the first edge of this path
         * BZ: StackOverflow if too deep
         */
        /* Recursive implementation:
         * Table getParent() {
         *     if (this != parent)
         *         parent = parent.getParent();
         *     return parent;
         * }
         */
        Table getParent() {
            // Approach the super root
            Table superRoot = this, i = this;
            while (superRoot != superRoot.parent) {
                superRoot = superRoot.parent;
            }
            while (i != superRoot) {
                Table oldParent = i.parent;
                i.parent = superRoot;
                i = oldParent;
            }
            return superRoot;
        }
    }

    int maximumNumberOfRows = -1;

    void merge(Table destination, Table source) {
        Table realDestination = destination.getParent();
        // System.out.println("Find D OK");
        Table realSource = source.getParent();
        // System.out.println("Find S OK");
        if (realDestination == realSource) {
            return;
        }
        // merge two components here
        // use rank heuristic
        // update maximumNumberOfRows
        if (realSource.rank <= realDestination.rank) {
            // BZ: set source or realSource?
            realSource.parent = realDestination;
            realDestination.numberOfRows += realSource.numberOfRows;
            realSource.numberOfRows = 0;
        }
        else {
            realDestination.parent = realSource;
            realSource.numberOfRows += realDestination.numberOfRows;
            realDestination.numberOfRows = 0;
        }
        if (realSource.rank == realDestination.rank)
            realDestination.rank++;  // since S is now under D
        maximumNumberOfRows = Math.max(
                Math.max(maximumNumberOfRows, realSource.numberOfRows),
                realDestination.numberOfRows);
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();
        Table[] tables = new Table[n];
        for (int i = 0; i < n; i++) {
            int numberOfRows = reader.nextInt();
            tables[i] = new Table(numberOfRows);
            // Maintain the global max during initialization
            maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
        }
        for (int i = 0; i < m; i++) {
            int destination = reader.nextInt() - 1;
            int source = reader.nextInt() - 1;
            merge(tables[destination], tables[source]);
            writer.printf("%d\n", maximumNumberOfRows);
        }
    }


    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}

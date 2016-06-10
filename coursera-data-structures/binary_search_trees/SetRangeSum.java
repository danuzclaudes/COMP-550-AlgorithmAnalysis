package binary_search_trees;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Set with range sums
 * <p>
 * Implement a data structure to store a set S of integers
 * with operations:
 * add(i) - add integer i (if it was there already, no change).
 * del(i) - remove integer (if no such element, no change).
 * find(i) - check whether i is in the set S or not.
 * sum(l, r) - output the sum of all elements v in S such that l ≤ v ≤ r.
 */
public class SetRangeSum {

    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;

    // Splay tree implementation

    // Vertex of a splay tree
    class Vertex {
        int key;
        // Sum of all the keys in the subtree - remember to update
        // it after each operation that changes the tree.
        long sum;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(int key, long sum, Vertex left, Vertex right, Vertex parent) {
            this.key = key;
            this.sum = sum;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    void update(Vertex v) {
        if (v == null) return;
        v.sum = v.key + (v.left != null ? v.left.sum : 0) + (v.right != null ? v.right.sum : 0);
        if (v.left != null) {
            v.left.parent = v;
        }
        if (v.right != null) {
            v.right.parent = v;
        }
    }

    void smallRotation(Vertex v) {
        Vertex parent = v.parent;
        if (parent == null) {
            return;
        }
        Vertex grandparent = v.parent.parent;
        if (parent.left == v) {
            Vertex m = v.right;
            v.right = parent;
            parent.left = m;
        } else {
            Vertex m = v.left;
            v.left = parent;
            parent.right = m;
        }
        update(parent);
        update(v);
        v.parent = grandparent;
        if (grandparent != null) {
            if (grandparent.left == parent) {
                grandparent.left = v;
            } else {
                grandparent.right = v;
            }
        }
    }

    void bigRotation(Vertex v) {
        if (v.parent.left == v && v.parent.parent.left == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else {
            // Zig-zag
            smallRotation(v);
            smallRotation(v);
        }
    }

    // Makes splay of the given vertex and returns the new root.
    Vertex splay(Vertex v) {
        if (v == null) return null;
        while (v.parent != null) {
            if (v.parent.parent == null) {
                smallRotation(v);
                break;
            }
            bigRotation(v);
        }
        return v;
    }

    class VertexPair {
        Vertex left;
        Vertex right;
        VertexPair() {
        }
        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }
    }

    // Searches for the given key in the tree with the given root
    // and calls splay for the deepest visited node after that.
    // Returns pair of the result and the new root.
    // If found, result is a pointer to the node with the given key.
    // Otherwise, result is a pointer to the node with the smallest
    // bigger key (next value in the order).
    // If the key is bigger than all keys in the tree,
    // then result is null.
    VertexPair find(Vertex root, int key) {
        Vertex v = root;
        Vertex last = root;
        Vertex next = null;
        while (v != null) {
            if (v.key >= key && (next == null || v.key < next.key)) {
                next = v;
            }
            last = v;
            if (v.key == key) {
                break;
            }
            if (v.key < key) {
                v = v.right;
            } else {
                v = v.left;
            }
        }
        root = splay(last);
        return new VertexPair(next, root);
    }

    VertexPair split(Vertex root, int key) {
        VertexPair result = new VertexPair();
        VertexPair findAndRoot = find(root, key);
        root = findAndRoot.right;
        result.right = findAndRoot.left;
        if (result.right == null) {
            result.left = root;
            return result;
        }
        result.right = splay(result.right);
        result.left = result.right.left;
        result.right.left = null;
        if (result.left != null) {
            result.left.parent = null;
        }
        update(result.left);
        update(result.right);
        return result;
    }

    Vertex merge(Vertex left, Vertex right) {
        if (left == null) return right;
        if (right == null) return left;
        while (right.left != null) {
            right = right.left;
        }
        right = splay(right);
        right.left = left;
        update(right);
        return right;
    }

    // Code that uses splay tree to solve the problem

    Vertex root = null;

    void insert(int x) {
        Vertex left = null;
        Vertex right = null;
        Vertex new_vertex = null;
        VertexPair leftRight = split(root, x);
        left  = leftRight.left;
        right = leftRight.right;
        if (right == null || right.key != x) {
            new_vertex = new Vertex(x, x, null, null, null);
        }
        root = merge(merge(left, new_vertex), right);
        debug(root, "after +, tree=");

    }

    void erase(int x) {
        // TODO: Implement erase yourself
        //out.println("del:"+x);
        VertexPair pair = find(root, x);
        root = pair.right;
        /**BZ: RETURN THE NEW ROOT OF SUBTREE */
        root = delete(x, root);
        /**BZ: MUST UPDATE PARENT LINKS AFTER DELETION */
        if (root != null) root.parent = null;
        debug(root, "after -, tree=");
    }

    private Vertex delete(int x, Vertex root) {
        if (root == null) return root;
        if (x < root.key)
            root.left  = delete(x, root.left);
        else if (x > root.key)
            /**BZ: DELETE STH DOES NOT EXIST? */
            root.right = delete(x, root.right);
        /**BZ: ELSE IF OR IF? */
        else if (root.left == null)
            return root.right;
        else if (root.right == null)
            return root.left;
        else {
            // Two children case.
            // Find Next() of R in R.Right.
            // Replace R's key by its Next;
            // Promote Next's right subtree to Next.
            // out.println(root.key);
            Vertex next = next(root);
            assert(next.left == null);
            root.key = next.key;
            promote(next.right, next);
        }
        return root;
    }
    private Vertex next(Vertex node) {
        /**Return the next larger node */
        if (node == null) return null;
        if (node.right != null) {
            node = node.right;
            while (node.left != null) node = node.left;
            return node;
        }
        Vertex parent = node.parent;
        while (parent != null && parent.key <= node.key)
            parent = parent.parent;
        return parent;
    }
    private void promote(Vertex child, Vertex node) {
        /**Promote child to replace node */
        assert(node.parent != null);   // Assert that Parent must exist
        // BZ: node may have null child
        if (child != null) child.parent = node.parent;
        if (node == node.parent.left) node.parent.left = child;
        else node.parent.right = child;
        update(node.parent);
        node.parent = null;
    }

    boolean find(int x) {
        if (root == null) return false;
        Vertex node = root;
        while (node != null) {
            if (node.key == x) return true;
            else if (x < node.key) node = node.left;
            else node = node.right;
        }
        splay(node);
        return false;
    }

    long sum(int from, int to) {
        if (DEBUGGING) out.println("s: fr="+from+" to="+to);
        debug(root, "before sum, tree=");
        VertexPair leftMiddle = split(root, from);
        Vertex left = leftMiddle.left;
        Vertex middle = leftMiddle.right;
        debug(root, "after split, tree=");
        debug(left, "\tleft=");
        debug(middle, "\tmiddle=");
        VertexPair middleRight = split(middle, to + 1);
        middle = middleRight.left;
        Vertex tmp = middle;
        Vertex right = middleRight.right;
        debug(tmp, "\ttmp=");
        debug(right, "\tright=");
        long ans = 0;
        // TODO: Complete the implementation of sum
        while (middle != null && middle.left != null)
            middle = middle.left;
        while (middle != null && middle.key <= to) {
            if (middle.key >= from) ans += middle.key;
            middle = next(middle);
        }
        /**BZ: must merge all the trees back after sum? */
        /**BZ: the current middle has already been null */
        root = merge(merge(left, tmp), right);
        debug(root, "after sum, tree=");
        return ans;
    }

    private void debug(Vertex root, String str) {
        if (! DEBUGGING) return;
        out.print("\t" + str);
        printTree(root);
        if(stress_testing) System.out.println();
        else out.println();
    }
    private void printTree(Vertex root) {
        if (root == null) return;
        printTree(root.left);
        out.print(root.key + "->");
        printTree(root.right);
    }

    public static final int MODULO = 1000000001;
    public static final boolean DEBUGGING = false;
    public static boolean stress_testing = false;
    void solve() throws IOException {
        int n = nextInt();
        int last_sum_result = 0;
        for (int i = 0; i < n; i++) {
            char type = nextChar();
            switch (type) {
            case '+' : {
                int x = nextInt();
                if (DEBUGGING) out.print("+" + ((x + last_sum_result) % MODULO) +":\t");
                insert((x + last_sum_result) % MODULO);
            } break;
            case '-' : {
                int x = nextInt();
                if (DEBUGGING) out.print("-" + ((x + last_sum_result) % MODULO) +":\t");
                erase((x + last_sum_result) % MODULO);
            } break;
            case '?' : {
                int x = nextInt();
                if (DEBUGGING) out.print("?" + ((x + last_sum_result) % MODULO) +":\t");
                out.println(find((x + last_sum_result) % MODULO) ? "Found" : "Not found");
            } break;
            case 's' : {
                int l = nextInt();
                int r = nextInt();
                long res = sum((l + last_sum_result) % MODULO, (r + last_sum_result) % MODULO);
                if (DEBUGGING) out.print("res=");
                out.println(res);
                last_sum_result = (int)(res % MODULO);
            }
            }
        }
    }

    /**
     * Stress testing by reading test cases from file.
     * @throws IOdException 
     */
    private void stressSolve() throws IOException {
        File folder = new File("/home/chongrui/Downloads/pa4-rangesum");
        // Stress Testing
        File[] files = folder.listFiles();
        Arrays.sort(files, new Comparator<File>(){
            @Override
            public int compare (File f1, File f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });
        for (File test : files) {
            root = null;  // BZ: renew root
            out.print(test.getName() + ": ");
            FileReader input_stream = new FileReader(test);
            BufferedReader reader = new BufferedReader(input_stream);
            int n = Integer.parseInt(reader.readLine());
            int last_sum_result = 0;
            Set<Integer> set = new HashSet<>();
            String line = null;
            for(int i = 0; i < n; i++) {
                line = reader.readLine();
                char type = line.charAt(0);
                switch (type) {
                case '+' : {
                    int x = Integer.parseInt(line.split(" ")[1]);
                    x = (x + last_sum_result) % MODULO;
                    set.add(x);
                    insert(x);
                } break;
                case '-' : {
                    int x = Integer.parseInt(line.split(" ")[1]);
                    x = (x + last_sum_result) % MODULO;
                    set.remove(x);
                    erase(x);
                } break;
                case '?' : {
                    int x = Integer.parseInt(line.split(" ")[1]);
                    x = (x + last_sum_result) % MODULO;
                    assert find(x) == set.contains(x) :
                        "Error! ?(" + x + ") is unmatching";
                } break;
                case 's' : {
                    int l = Integer.parseInt(line.split(" ")[1]);
                    int r = Integer.parseInt(line.split(" ")[2]);
                    int from = (l + last_sum_result) % MODULO;
                    int to   = (r + last_sum_result) % MODULO;
                    long res      = sum(from, to);
                    long resNaive = naiveRangeSum(from, to, set);
                    assert res == resNaive: "Error! \"s " + from + " " + to +
                            "\": res=" + res + " resNaive=" + resNaive;
                    last_sum_result = (int)(res % MODULO);
                } break;
                }
            }
            out.println("OK");
            reader.close();
        }
    }
    /**
     * Create a HashSet and use it for answering queries
     * such as insertion , deletions and searching 
     * @return
     */
    private long naiveRangeSum(int from, int to, Set<Integer> set) {
        long res = 0;
        for (int num : set) if (from <= num && num <= to) res += num;
        return res;
    }

    SetRangeSum() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        if (stress_testing) stressSolve();
        else solve();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(DEBUGGING ? "DEBUGGING MODE ENABLED...":
                "DEBUGGING MODE DISABLED...");
        String loc = "/home/chongrui/Downloads/pa4-rangesum";
        if (new File(loc).exists()) stress_testing = true;

        System.out.println(stress_testing ? "Running stress testing cases...":
            "Please type in the input data:");
        new SetRangeSum();
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
    char nextChar() throws IOException {
        return nextToken().charAt(0);
    }
}

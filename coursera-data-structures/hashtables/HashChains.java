package hashtables;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * HashSet - Separate Chaining
 * <p>
 * Note: Polynomial hash function for strings.</br>
 * Note: Take negative numbers (mod p): (−2)%5 != 3%5;
 *       <b>add p to the result and take modulo p again</b>:
 *       <pre>x ← ((a%p) + p)%p instead of just x ← a%p</pre>
 * Example: m=5 n=5
 *          add world add HellO del world
 *          del world check 4 -> HellO + \n
 */
public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private List<String> elems;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;

    // Separate Chaining to occupy O(n+m) space
    private List<String>[] hashtable;

    @SuppressWarnings("unchecked")
    public HashChains () {
        hashtable = new List[multiplier];
        for (int i = 0; i < multiplier; i++) {
            hashtable[i] = new ArrayList<String>();
        }
    }

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int) hash % bucketCount;
        // hashval = (hashval % prime + prime) % prime; in case <0
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        out.flush();
    }

    public void processQuery_naive(Query query) {
        switch (query.type) {
        case "add":
            if (!elems.contains(query.s))
                elems.add(0, query.s);
            break;
        case "del":
            if (elems.contains(query.s))
                elems.remove(query.s);
            break;
        case "find":
            writeSearchResult(elems.contains(query.s));
            break;
        case "check":
            for (String cur : elems)
                if (hashFunc(cur) == query.ind)
                    out.print(cur + " ");
            out.println();
            // Uncomment the following if you want to play with the program interactively.
            // out.flush();
            break;
        default:
            throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQuery(Query query) {
        switch (query.type) {
        case "add":
            if (! hashtable[hashFunc(query.s)].contains(query.s))
                hashtable[hashFunc(query.s)].add(0, query.s);
            break;
        case "del":
            if (hashtable[hashFunc(query.s)].contains(query.s))
                hashtable[hashFunc(query.s)].remove(query.s);
            break;
        case "find":
            writeSearchResult(hashtable[hashFunc(query.s)].contains(query.s));
            break;
        case "check":
            for (String cur : hashtable[query.ind])
                out.print(cur + " ");
            out.println();
            out.flush();
            break;
        default:
            throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        elems = new ArrayList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}

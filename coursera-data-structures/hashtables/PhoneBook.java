package hashtables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Phone book manager.
 * <p>
 * Query contact names by phone numbers.</br>
 * add number name: adds a person with name name
 *                  and phone number number to the
 *                  phone book; overwrite name if
 *                  exists.</br>
 * del number:  erase a person with number from
 *              the phone book. ignore if there
 *              is no such person.</br>
 * find number: looks for a person with phone
 *              number number; reply with “not
 *              found” if there is no such person.</br>
 * Constraints: 1 ≤ N ≤ 10^5;
 *              all phone numbers have no more
 *              than 7 digits;
 *              all names have length at most 15
 */
public class PhoneBook {

    private FastScanner in = new FastScanner();
    // Keep list of all existing (i.e. not deleted yet) contacts.
    private List<Contact> contacts = new ArrayList<>();

    // Separate Chaining to occupy O(n+m) space
    private List<Contact>[] hashtable;
    // Cardinality of hash table
    private int m = 101;
    // BZ: after choosing a hash function,
    // must fix it throughout the algorithm
    private int a = -1, b = -1;
    
    private static Random random = new Random();
    
    // Store outputs of naive/fast results
    private static List<String> resFast, resNaive;

    @SuppressWarnings("unchecked")
    public PhoneBook () {
        // Implementation of separate chaining
        // BZ:
        hashtable = new List[m];
        for (int i = 0; i < m; i++) {
            hashtable[i] = new ArrayList<Contact>();
        }
    }

    public static void main(String[] args) {
        new PhoneBook().processQueries();
    }

    private Query readQuery() {
        String type = in.next();
        int number = in.nextInt();
        if (type.equals("add")) {
            String name = in.next();
            return new Query(type, name, number);
        } else {
            return new Query(type, number);
        }
    }

    private void writeResponse(String response) {
        System.out.println(response);
    }

    public void processQuery_naive(Query query) {
        if (query.type.equals("add")) {
            // if we already have contact with such number,
            // we should rewrite contact's name
            boolean wasFound = false;
            for (Contact contact : contacts)
                if (contact.number == query.number) {
                    contact.name = query.name;
                    wasFound = true;
                    break;
                }
            // otherwise, just add it
            if (!wasFound)
                contacts.add(new Contact(query.name, query.number));
        } else if (query.type.equals("del")) {
            for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); )
                if (it.next().number == query.number) {
                    it.remove();
                    break;
                }
        } else {
            String response = "not found";
            for (Contact contact: contacts)
                if (contact.number == query.number) {
                    response = contact.name;
                    break;
                }
            writeResponse(response);
            if (resNaive != null) resNaive.add(response);
        }
    }

    /**
     * Key Idea: Map integer (phone #s) into string.
     * Build a hash table of m chaining contacts.
     * Choose a hash function from Universal Family.
     *
     * @param query
     */
    public void processQuery(Query query) {
        if (query.type.equals("add")) {
            insert(query);
        }
        else if (query.type.equals("del")) {
            remove(query);
        }
        else {
            String response = search(query);
            writeResponse(response);
            if (resFast != null) resFast.add(response);
        }
    }
    /**
     * Chain Table[hash(number)] might contain the name;
     * If list already contains, overwrite name.
     * else append the new contact.
     *
     * @param query
     */
    private void insert(Query query) {
        int index = hash(query.number);
        for (Contact contact : hashtable[index]) {
            if (contact.number == query.number) {
                contact.name = query.name;
                return;
            }
        }
        hashtable[index].add(new Contact(query.name, query.number));
    }
    /**
     * If chain T[h(number)] contains the name, remove it;
     * else do nothing
     *
     * @param query
     */
    private void remove(Query query) {
        // Iterator pattern of deletion during traversal
        int index = hash(query.number);
        Iterator<Contact> iter = hashtable[index].iterator();
        while (iter.hasNext()) {
            Contact contact = iter.next();
            if (contact.number == query.number) {
                iter.remove();
                return;
            }
        }
    }
    /**
     * If chain T[h(number)] contains the name, return it;
     * return not found
     *
     * @param query
     * @return      the number mapped by contact's number
     */
    private String search(Query query) {
        int index = hash(query.number);
        for (Contact contact : hashtable[index]) {
            if (contact.number == query.number) {
                return contact.name;
            }
        }
        return "not found";
    }
    /**
     * Universal Hash Family.
     * Select a random hash function h,
     * from ((ax + b) mod p ) mod m,
     * where 1 <= a <= p-1, 0 <= b <= p-1.
     *
     * @param number
     * @return
     */
    private int hash(int number) {
        int p = nextPrime(10000000);
        int bound = p - 1;
        // BZ: Hash function must be deterministic
        if (a == -1) a = (random.nextInt(bound) + 1) % p;
        if (b == -1) b = (random.nextInt(bound + 1)) % p;
        // Overflow: return ((a * number + b) % p) % m
        // (A + B) mod C = (A mod C + B mod C) mod C
        // (A * B) mod C = (A mod C * B mod C) mod C
        int hashval = (((a * (number % p)) % p + (b % p)) % p) % m;
        // BZ: underflow case; put back into range of table
        if (hashval < 0) hashval += m;
        return hashval;
    }
    /**
     * Why choose big prime p > 10^L?
     *
     * @param n 10 to the power of L.
     * @return  a prime number larger than or equal to n.
     */
    private int nextPrime(int n) {
        if (n % 2 == 0) n++;
        while (! isPrime(n)) n += 2;  // BZ: why +=2?
        return n;
    }
    private static boolean isPrime( int n ){
        if( n % 2 == 0 ) return false;
        // BZ: ignore all even numbers and check till sqrt(n)
        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 ) return false;
        return true;
    }

    public void processQueries() {
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i)
            processQuery(readQuery());
    }

    static class Contact {
        String name;
        int number;

        public Contact(String name, int number) {
            this.name = name;
            this.number = number;
        }
    }

    static class Query {
        String type;
        String name;
        int number;

        public Query(String type, String name, int number) {
            this.type = type;
            this.name = name;
            this.number = number;
        }

        public Query(String type, int number) {
            this.type = type;
            this.number = number;
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

// -----HashTable_QuadraticProbing ADT-----
// boolean contains(T x)
// void insert(T x)
// void remove(T x)
// int hash(T x)
// void rehash()
public class HashTable_QuadraticProbing<T>{
    private static class HashEntry<T>{
        private T key;
        // BZ: extra flag to denote if cur cell is deleted;
        // but even deleted, still a bridge further probes
        private boolean active;
        public HashEntry(T x, boolean active){
            this.key = x;
            this.active = active;
        }
    }
    private HashEntry<T>[] table;
    private int size;
    public HashTable_QuadraticProbing(){
        this.size = 0;
        this.table = new HashEntry[101];
    }
    public boolean contains(T x){
        // compute index i of first slot x;
        // forward i till the first match or null/deleted;
        // return if it stops at non-null and active
        int i = hash(x);
        i = findSlot(i, x);
        return table[i] != null && table[i].active;
    }
    public boolean insert(T x){
        // compute index i of first slot x;
        // forward i till the first null/deleted;
        // fill in x if stops at null or deleted;
        // if duplicate, return;
        int i = hash(x);
        i = findSlot(i, x);
        if(table[i] != null && table[i].active) return false;
        table[i] = new HashEntry<T>(x, true);
        // BZ: rehash as soon as table is half full for quadratic probing
        if(++size == table.length / 2) rehash();
        return true;
    }
    public void remove(T x){
        // compute index i of first slot x;
        // forward i till the first match or null/deleted;
        // if stop at null or already deleted, return;
        // mark cell deleted to bridge further probes
        int i = hash(x);
        i = findSlot(i, x);
        if(table[i] == null || ! table[i].active) return;
        table[i].active = false;
        this.size--; // BZ: decrease size
    }
    private int findSlot(int i, T x){
        // try each cell i^2 (0,1,4,9) away from start;
        // while next trial not null and unmatch, forward i;
        int offset = 1;
        while(table[i] != null && ! table[i].key.equals(x)){
            // BZ: still needs i + 1 before offset+=2
            i = (i + offset) % table.length;
            offset += 2;
        }
        return i;
    }
    private int hash(T x){
        int hashval = x.hashCode();
        hashval %= table.length;
        if(hashval < 0) hashval += table.length;
        return hashval;
    }
    private void rehash(){
        // create a double-sized array;
        // insert each non-null entry again to the new size array;
        HashEntry<T>[] tmp = table; // entries are HashEntry<T> type;
        table = new HashEntry[nextPrime(2 * table.length + 1)];
        for(HashEntry<T> entry : tmp){
            // BZ: don't copy deleted; since re-insert each entry,
            // no need to keep deleted cells for further probes
            if(entry == null || ! entry.active) continue;
            insert(entry.key);  // BZ: insert(entry)?
        }
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime( int n ){
        if( n % 2 == 0 ) n++;
        while(! isPrime(n)) n += 2;
        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n ){
        if( n == 2 || n == 3 ) return true;
        if( n == 1 || n % 2 == 0 ) return false;
        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 ) return false;
        return true;
    }

    // Simple main
    public static void main( String [ ] args ){
        HashTable_QuadraticProbing<String> H = new HashTable_QuadraticProbing<>( );    
        long startTime = System.currentTimeMillis( );        
        final int NUMS = 2000000;
        final int GAP  =   37;
        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS ) H.insert( ""+i );
        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            if( H.insert( ""+i ) ) System.out.println( "OOPS!!! " + i );
        for( int i = 1; i < NUMS; i+= 2 ) H.remove( ""+i );
        for( int i = 2; i < NUMS; i+=2 )
            if( !H.contains( ""+i ) ) System.out.println( "Find fails " + i );
        for( int i = 1; i < NUMS; i+=2 )
            if( H.contains( ""+i ) ) System.out.println( "OOPS!!! " +  i  );

        long endTime = System.currentTimeMillis( );
        System.out.println( "Elapsed time: " + (endTime - startTime) );
    }
}

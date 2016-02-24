// -----HashTable_SeparateChaining ADT-----
// boolean contains(T x)
// void insert(T x)
// void remove(T x)
// int hash(T x)
// void rehash()
import java.util.List;
import java.util.LinkedList;
public class HashTable_SeparateChaining<T>{
    private List<T>[] table;
    private int size;
    public HashTable_SeparateChaining(){
        this.size = 0;
        // BZ: cannot create generic type array: LinkedList<T>[]/List<T>[]; 
        // new LinkedList[N] or new List[N] would work;
        // must initialize each cell after new List[N];
        table = new LinkedList[101];
        for(int i = 0; i < table.length; i++){
            table[i] = new LinkedList<T>();
        }
    }
    public boolean contains(T x){
        int i = this.hash(x);
        return this.table[i].contains(x);
    }
    public void insert(T x){
        int i = this.hash(x);
        if(table[i].contains(x)) return;
        table[i].add(x);
        // BZ: rehash after an insertion
        if(++this.size >= table.length) rehash();
    }
    public void remove(T x){
        int i = this.hash(x);
        if(! table[i].contains(x)) return;
        table[i].remove(x);
        this.size--;  // BZ: decrease size
    }
    private int hash(T x){
        // working only for objects of type T that have hashCode and equals
        int hashval = x.hashCode();
        // ***BZ: must modular by table size
        hashval %= this.table.length;
        // overflow case; put back into range of table
        if(hashval < 0) hashval += this.table.length;
        return hashval;
    }
    private void rehash(){
        // for separate chaining, rehash as soon as table is full
        if(this.size < this.table.length) return;
        List<T>[] tmp = this.table;
        this.table = new List[nextPrime(table.length * 2 + 1)];
        // BZ: must initialize each cell after new List[N];
        for(int i = 0; i < table.length; i++){
            table[i] = new LinkedList<T>();
        }
        // BZ: for(int i = 0; ...) table[i] = tmp[i]? cannot just copy entry
        // ***insert each key again w/ new hash val under new table size
        for(List<T> list : tmp){
            for(T key : list) this.insert(key);
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
    public static void main( String [ ] args ){
        HashTable_SeparateChaining<Integer> H = new HashTable_SeparateChaining<>( );
        long startTime = System.currentTimeMillis( );      
        final int NUMS = 2000000;
        final int GAP  =   37;
        System.out.println( "Checking... (no more output means success)" );
        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS ) H.insert( i );
        for( int i = 1; i < NUMS; i+= 2 ) H.remove( i );  // remove all odds
        for( int i = 2; i < NUMS; i+=2 )
            if( !H.contains( i ) )
                System.out.println( "Find fails " + i );
        for( int i = 1; i < NUMS; i+=2 )
            if( H.contains( i ) )
                System.out.println( "OOPS!!! " +  i  );     
        long endTime = System.currentTimeMillis( );
        System.out.println( "Elapsed time: " + (endTime - startTime) );
    }
}

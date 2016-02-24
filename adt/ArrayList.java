// -----ArrayList Impl-----
// -----Collections ADT-----
// int size();
// boolean isEmpty();
// void clear();
// boolean add(T x);
// boolean contains(T x);
// Iterator<T> iterator();
// -----List ADT-----
// void add(int index, T x);
// T get(int index);
// T remove(int index);
// T set(int index, T x);
// supports ensureCapacity
import java.util.Iterator;
public class ArrayList<T> implements Iterable<T>{  // BZ: ArrayList IS-A Interable
    private static final int capacity = 20;
    private T[] array;
    private int size;
    public ArrayList(){
        this.size = 0;
        ensureCapacity(capacity);
    }
    
    public int size(){ return this.size; }
    public boolean isEmpty(){ return this.size == 0; }
    public void clear(){
        this.size = 0;
        ensureCapacity(capacity);
    }
    public void ensureCapacity(int newCapacity){
        // BZ: copy to tmp and then array = tmp?
        // must store old values as tmp and copy into new array
        if(newCapacity < this.size) return;
        T[] tmp = array;
        array = (T[]) new Object[newCapacity];
        for(int i = 0; i < size; i++){
            array[i] = tmp[i];
        }
    }
    
    public boolean add(T x){
        this.add(this.size(), x);
        return true;
    }
    public void add(int index, T x){
        // BZ: ensureCapacity iif size reaching array length;
        // instead of reaching capacity!
        if(this.size() == this.array.length){
            // BZ: +1 for size == 0 case;
            ensureCapacity(this.size() * 2 + 1);
        }
        for(int i = size; i > index; i--){
            array[i] = array[i - 1];
        }
        this.array[index] = x;
        this.size++;  // BZ: increase size!
    }
    
    public boolean contains(T x){
        // BZ: if target x is null, must search null entry in array;
        // else check matching by equals() operation;
        for(int i = 0; i < size; i++){
            if (x == null && array[i] == null ||
                x != null && x.equals(array[i])) return true;
        }
        return false;
    }
    public T get(int index){
        if(index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException();
        return array[index];
    }
    
    public T remove(int index){
        T tmp = array[index];
        for(int i = index; i < size - 1; i++){
            array[i] = array[i + 1];
        }
        // BZ: must decrease size;
        array[size-- - 1] = null;  // clear to let GC do its work
        return tmp;
    }
    
    public T set(int index, T x){
        if(index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException();
        T tmp = array[index];
        array[index] = x;
        return tmp;
    }
    
    public Iterator<T> iterator(){
        // BZ: no generic types for private class?
        // would hide type T of outer class
        return new ArrayListIterator();
    }
    
    private class ArrayListIterator implements Iterator<T>{
        // BZ: OuterClass.this.field/method;
        // OuterClass.staticField/staticMethod
        private int index = 0;
        @Override
        public boolean hasNext(){
            return index < ArrayList.this.size();
        }
        
        @Override
        public T next(){
            if(! hasNext()) throw new java.util.NoSuchElementException();
            return ArrayList.this.array[index++];
        }
        
        @Override
        public void remove(){
            // BZ: remove(index--)? remove last returned entry by a next()
            ArrayList.this.remove(--index);
        }
    }
    
    public static void main( String [ ] args ){
        ArrayList<Integer> lst = new ArrayList<>( );
        for( int i = 0; i < 10; i++ ) lst.add( i );
        for( int i = 20; i < 30; i++ ) lst.add( 0, i );
        lst.remove( 0 );
        lst.remove( lst.size( ) - 1 );
        System.out.println( lst );
    }
    /**
     * Returns a String representation of this collection.
     */
    public String toString(){
        StringBuilder sb = new StringBuilder( "[ " );
        for( T x : this ) sb.append( x + " " );
        sb.append( "]" );
        return new String( sb );
    }
}

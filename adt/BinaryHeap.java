// -----BinaryHeap ADT-----
// int size()
// boolean isEmpty()
// T min()
// void insert(T x)
// T deleteMin()
//void buildHeap()
// BinaryHeap(T[] array)
// BinaryHeap(int capacity)
// support ensureCapacity()
public class BinaryHeap<T extends Comparable<T>>{
    private int size;
    private T[] heap;
    public BinaryHeap(int capacity){
        this.size = 0;
        heap = (T[]) new Comparable[capacity];
    }
    public BinaryHeap(T[] array){
        this.size = 0;
        heap = (T[]) new Comparable[array.length + 1];
        for(int i = 0; i < array.length; i++){
            heap[i + 1] = array[i];
        }
        buildHeap();
    }
    public int size(){ return this.size; }
    public boolean isEmpty(){ return this.size() == 0; }
    public T min(){
        if(isEmpty()) throw new IndexOutOfBoundsException();
        return this.heap[1];
    }
    public void insert(T x){
        // expand array if size reaching capacity-1;
        // append x at end slot; swim it up while < parent;
        if(this.size == this.heap.length - 1)
            ensureCapacity(size * 2 + 1);
        heap[++size] = x;
        int k = size;
        while(k > 1 && x.compareTo(heap[k / 2]) < 0){
            swap(heap, k, k / 2);
            k /= 2;  // bubble up one level
        }
    }
    private void ensureCapacity(int newCapacity){
        if(newCapacity < this.size) return;
        T[] tmp = this.heap;
        this.heap = (T[]) new Comparable[newCapacity];
        for(int i = 0; i < tmp.length; i++)
            this.heap[i] = tmp[i];
    }
    public T deleteMin(){
        // swap root w/ entry at pos size and decrease slot;
        // sink the root and return the original root
        if(isEmpty()) throw new java.util.NoSuchElementException();
        T tmp = this.heap[1];
        swap(heap, 1, size);
        heap[size--] = null;
        sink(1);
        return tmp;
    }
    private void sink(int k){
        // while cur k has at least 1 child,
        // find the smaller child; break if cur < smaller child;
        // swap cur w/ the smaller child;
        // traverse down to the child position;
        int j = 0;
        while(k * 2 <= size){
            j = k * 2;
            if(j + 1 <= size && heap[j + 1].compareTo(heap[j]) < 0) j++;
            if(heap[k].compareTo(heap[j]) < 0) break;
            swap(heap, k, j);
            k = j;
        }
    }
    private void swap(T[] heap, int i, int j){
        T tmp = heap[i]; heap[i] = heap[j]; heap[j] = tmp;
    }
    private void buildHeap(){
        // build heap from the first inner node having child;
        // sink each node
        for(int i = this.size / 2; i >= 1; i--)
            this.sink(i);
    }
    public static void main( String [] args ){
        int numItems = 10000;
        BinaryHeap<Integer> h = new BinaryHeap<>(10);
        int i = 37;
        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            h.insert( i );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
    }
}

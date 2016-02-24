// -----PriorityQueue ADT-----
// int size()
// boolean isEmpty()
// T min()
// void enqueue(T x)
// T dequeue()
public class PriorityQueue<T extends Comparable<T>>{  // BZ: T must also be Comparable
    private BinaryHeap<T> heap;
    public PriorityQueue(int capacity){
        heap = new BinaryHeap<>(capacity);
    }
    public int size(){ return heap.size(); }
    public boolean isEmpty(){ return heap.isEmpty(); }
    public T min(){ return heap.min(); }
    public void enqueue(T x){ heap.insert(x); }
    public T dequeue(){ return heap.deleteMin(); }
}

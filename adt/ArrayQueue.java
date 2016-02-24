// -----ArrayQueue ADT-----
// int size()
// boolean isEmpty()
// void enqueue(T x)
// T peek()
// T dequeue()
// support circular queue
public class ArrayQueue<T>{
    private T[] queue = (T[]) new Object[10];
    private int head = 0;
    private int tail = 0;
    public int size(){
        // BZ: return tail - head? since circular, t may < h;
        // BZ: tail would always point at the next empty slot
        return head <= tail ? tail - head :
            tail + queue.length - head;
    }
    
    public boolean isEmpty(){ return this.head == this.tail; }
    public boolean isFull(){
        // reserve one empty slot as sentinel of full: (t + 1)%n = h
        return (this.tail + 1) % queue.length == this.head;
    }
    public void enqueue(T x){
        if(this.isFull()){ System.out.println("Queue is full."); return; }
        this.queue[tail] = x;
        this.tail = (this.tail + 1) % queue.length;  // BZ: tail++?
    }
    
    public T peek(){
        if(this.isEmpty()) return null;
        return queue[head];
    }
    
    public T dequeue(){
        if(this.isEmpty()) return null;
        T tmp = queue[head];
        queue[head] = null;
        head = (head + 1) % queue.length;
        return tmp;
    }
}

// -----ListQueue ADT-----
// int size()
// boolean isEmpty()
// void enqueue(T x)
// T peek()
// T dequeue()
public class ListQueue<T>{
    private static class ListNode<T>{
        private T val;
        private ListNode<T> prev;
        private ListNode<T> next;
        public ListNode(T x, ListNode<T> prev, ListNode<T> next){
            this.val = x;
            this.prev = prev;
            this.next = next;
        }
    }
    private int size;
    private ListNode<T> head;
    private ListNode<T> tail;
    public ListQueue(){
        this.size = 0;
        this.head = new ListNode<T>(null, null, null);
        this.tail = new ListNode<T>(null, head, null);
        head.next = tail;
    }
    public int size(){ return this.size; }
    public boolean isEmpty(){ return this.head.next == this.tail; }
    public void enqueue(T x){
        // insert before tail;
        ListNode<T> tmp = new ListNode<T>(x, tail.prev, tail);
        tmp.prev.next = tmp;
        tail.prev = tmp;
        this.size++;
    }
    
    public T peek(){
        if(this.isEmpty()) return null;
        return head.next.val;
    }
    public T dequeue(){
        if(this.isEmpty()) return null;
        ListNode<T> tmp = this.head.next;
        this.head.next = tmp.next;
        tmp.next.prev = this.head;
        tmp.next = null; tmp.prev = null;
        return tmp.val;
    }
}

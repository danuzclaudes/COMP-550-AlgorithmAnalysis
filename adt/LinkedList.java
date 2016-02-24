// -----LinkedList Impl-----
// -----Collections ADT-----
// int size()
// boolean isEmpty()
// boolean contains(T x)
// boolean add(T x)
// Iterator<T> iterator()
// -----List ADT-----
// void add(int index, T x)
// T get(int index)
// T remove(int index)
// T set(int index, T x)
import java.util.Iterator;
public class LinkedList<T> implements Iterable<T>{
    private static class ListNode<T>{
        private T val;
        private ListNode<T> prev;
        private ListNode<T> next;
        public ListNode(T x, ListNode<T> p, ListNode<T> n){
            this.val = x;
            this.prev = p;
            this.next = n;
        }
    }
    private ListNode<T> head, tail;
    private int size;
    // BZ: maintain # of changes to list since construction
    private int modCount;
    public LinkedList(){
        doClear();
    }
    public int size(){ return this.size; }
    public boolean isEmpty(){ return this.size == 0; }
    public void clear(){
        doClear();
    }
    private void doClear(){
        this.size = 0;
        this.head = new ListNode<T>(null, null, null);
        this.tail = new ListNode<T>(null, head, null);
        this.head.next = this.tail;

        this.modCount = 0;  // BZ: reset modCount?
    }
    public boolean add(T x){
        add(this.size(), x);
        return true;
    }
    public void add(int index, T x){
        // delegate add(i, x) to insert before the node at i
        // BZ: add(x)->addBefore(tail), get tail node without error
        // addBefore(this.getNode(index))?
        addBefore(this.getNode(index, 0, this.size()), x);
    }
    public void addBefore(ListNode<T> node, T x){
        ListNode<T> tmp = new ListNode<T>(x, node.prev, node);
        tmp.prev.next = tmp;
        node.prev = tmp;
        this.size++;
        this.modCount++;
    }

    public T get(int index){
        // BZ: get(i)->getNode(index), i==N would overflow!
        // return getNode(index).val;
        return getNode(index, 0, this.size() - 1).val;
    }
    public ListNode<T> getNode(int index, int low, int high){
    	// *****BZ: why must define low..high for getNode?
        // for add(x)->addBefore(getNode(N)), correct but overflow for get(i);
        // for get(i)->getNode(i), if index == this.size(), would return null;
        if(index < low || index > high)
            throw new IndexOutOfBoundsException();
        if(index < this.size() / 2){
            ListNode<T> p = this.head.next;
            for(int i = 0; i < index; i++) p = p.next;
            return p;
        }
        else{
            // BZ: start from tail.prev? if index==N, start and stop at tail;
            ListNode<T> p = this.tail;
            for(int i = this.size(); i > index; i--) p = p.prev;
            return p;
        }
    }
    public T remove(int index){
        return this.remove(this.getNode(index, 0, this.size() - 1));
    }
    public T remove(ListNode<T> node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
        this.size--;
        this.modCount++;
        return node.val;
    }
    public T set(int index, T x){
        if(index < 0 || index >= this.size())
            throw new IndexOutOfBoundsException();
        ListNode<T> p = this.getNode(index, 0, this.size() - 1);
        T tmp = p.val;
        p.val = x;
        return tmp;
    }

    public Iterator<T> iterator(){
        return new LinkedListIterator();
    }
    private class LinkedListIterator implements Iterator<T>{
        // maintain current node whose data will be returned by a call of next()
        private ListNode<T> current = head.next;
        // maintain flag to denote if can remove only after a call of next()
        private boolean flag = false;
        // store current modCount as expected when iterator is created;
        private int expectedCount = modCount;

        @Override
        public boolean hasNext(){
            return current != tail;
        }
        @Override
        public T next(){
            if(! hasNext()) throw new java.util.NoSuchElementException();
            // BZ: concurrent modification if current count != stored count
            if(expectedCount != modCount)
                throw new java.util.ConcurrentModificationException();
            T tmp = current.val;
            current = current.next;
            flag = true;
            return tmp;
        }
        @Override
        public void remove(){
            if(expectedCount != modCount)
                throw new java.util.ConcurrentModificationException();
            if(! flag) throw new IllegalStateException();
            // *****BZ: remove(current)? current points to next node;
            LinkedList.this.remove(current.prev);
            expectedCount++;
            flag = false;
        }
    }

    public static void main( String [ ] args ){
        LinkedList<Integer> lst = new LinkedList<>( );
        for( int i = 0; i < 10; i++ ) lst.add( i );
        for( int i = 20; i < 30; i++ ) lst.add( 0, i );
        lst.remove( 0 );
        lst.remove( lst.size( ) - 1 );
        System.out.println( lst );

        java.util.Iterator<Integer> itr = lst.iterator( );
        while( itr.hasNext( ) ){
            itr.next( );
            itr.remove( );
            System.out.println( lst );
        }
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

// -----ArrayStack ADT-----
// boolean isEmpty()
// int size()
// void push(T x)
// T peek()
// T pop()
// T max()
public class ArrayStack<T extends Comparable<T>>{
    private static int capacity = 100;
    private int top = 0;
    private T[] stack = (T[]) new Object[capacity];
    private T[] maxStack = (T[]) new Object[capacity];
    
    public int size(){ return this.top; }
    public boolean isEmpty(){ return this.top == 0; }
    public void push(T x){
        if(top == this.stack.length) throw new StackOverflowError();
        // BZ: to compare T type objects, T must extend Comparable<T>
        stack[top] = x;
        if(this.isEmpty()) maxStack[top] = x;
        else maxStack[top] = x.compareTo(maxStack[top - 1]) > 0 ?
            x : maxStack[top - 1];
        top++;
    }
    public T peek(){
        if(this.isEmpty()) throw new java.util.EmptyStackException();
        return stack[top - 1];
    }
    public T pop(){
        if(this.isEmpty()) throw new java.util.EmptyStackException();
        T tmp = stack[--top];
        stack[top] = null;
        maxStack[top] = null;
        return tmp;
    }
    public T max(){
        return top == 0 ? null : maxStack[top - 1];
    }
}

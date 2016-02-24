// -----BST ADT-----
// boolean isEmpty()
// boolean contains(T x)
// T findMin()
// T findMax()
// void insert(T x)
// void remove(T x)  // no duplicate
// void printTree()
public class BST<T extends Comparable<T>>{  // BZ: T implements Comparable?
    private static class TreeNode<T>{
        private T val;
        private TreeNode<T> left, right;
        public TreeNode(T x, TreeNode<T> l, TreeNode<T> r){
            this.val = x;
            this.left = l;
            this.right = r;
        }
    }
    
    private TreeNode<T> root;
    public BST(){
        this.root = null;
    }
    public boolean isEmpty(){ return this.root == null; }
    public boolean contains(T x){
        return contains(this.root, x);
    }
    private boolean contains(TreeNode<T> root, T x){
        if(root == null) return x == null;
        int cmp = x.compareTo(root.val);
        if(cmp == 0) return true;
        else if(cmp > 0) return contains(root.right, x);
        else return contains(root.left, x);
    }
    
    public T findMin(){
        // delegate to findMinNode
        if(this.isEmpty()) return null;
        return findMin(root).val;
    }
    private TreeNode<T> findMin(TreeNode<T> root){
        if(root == null) return null;  // BZ: cannot have null.val
        while(root.left != null) root = root.left;
        return root;
    }
    public T findMax(){
        if(this.isEmpty()) return null;
        return findMax(root).val;
    }
    private TreeNode<T> findMax(TreeNode<T> root){
        if(root == null) return null;  // BZ: cannot have null.val
        while(root.right != null) root = root.right;
        return root;
    }
    
    public void insert(T x){
        // BZ: return insert(root, x)? void type + must update root
        this.root = insert(root, x);
    }
    private TreeNode<T> insert(TreeNode<T> root, T x){
        if(root == null) return new TreeNode<T>(x, null, null);
        // BZ: must compare x with current root val
        int cmp = x.compareTo(root.val);
        if(cmp == 0) return root;  // don't allow duplicate
        // BZ: must update left/right subtree after recursion
        else if(cmp < 0) root.left = insert(root.left, x);
        else root.right = insert(root.right, x);
        return root;
    }
    
    public void remove(T x){
        this.root = remove(root, x);
    }
    private TreeNode<T> remove(TreeNode<T> root, T x){
        // search the node to delete first;
        // no child or one child, replace by the null/non-null child;
        // two children case, find min in right subtree and replace root val;
        // recursively delete the rightMin in right subtree
        if(root == null) return null;
        int cmp = x.compareTo(root.val);
        if(cmp < 0) root.left = remove(root.left, x);
        else if(cmp > 0) root.right = remove(root.right, x);
        // replace root by null or its single child
        else if(root.left == null || root.right == null)
            return root.left == null ? root.right : root.left;
        // BZ: must use if..elif..elif..else; cannot delete if cmp != 0
        else{
            // two children case
            // BZ: replace cur root's val by right min's val
            root.val = findMin(root.right).val;
            root.right = remove(root.right, root.val);
        }
        return root;
    }
    
    public static void main( String [] args ){
        BST<Integer> t = new BST<>( );
        final int NUMS = 4000;
        final int GAP  =   37;
        System.out.println( "Checking... (no more output means success)" );
        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS ) t.insert( i );
        for( int i = 1; i < NUMS; i+= 2 ) t.remove( i );
        if( NUMS < 40 ) t.printTree( );
        if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
            System.out.println( "FindMin or FindMax error!" );

        for( int i = 2; i < NUMS; i+=2 )
             if( !t.contains( i ) )
                 System.out.println( "Find error1!" );

        for( int i = 1; i < NUMS; i+=2 ){
            if( t.contains( i ) )
                System.out.println( "Find error2!" );
        }
    }
    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    public void printTree( ){
        if( isEmpty( ) ) System.out.println( "Empty tree" );
        else printTree( root );
    }
    private void printTree( TreeNode<T> t ){
        if( t != null ){
            printTree( t.left );
            System.out.println( t.val );
            printTree( t.right );
        }
    }
}

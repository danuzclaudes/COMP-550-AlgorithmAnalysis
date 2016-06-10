package binary_search_trees;
import java.util.ArrayList;
import java.util.List;

/**
 * Splay Tree Implementation.
 * <p>
 * Find(x)</br>
 * Next(node): return the next largest key; extra `parent` pointer</br>
 * RangeSearch(x, y): find the closest node to x</br>
 * Insert(x)</br>
 * Delete(x)</br>
 * Split(x, root)</br>
 * Merge(r1, r2)</br>
 * MergeWithRoot(r1, r2, Root)</br>
 * OrderStatistic(Root, k): extra field `sum`</br>
 * Splay(node)</br>
 * SplayFind(x)</br>
 * SplayInsert(x)</br>
 * SplayDelete(x)</br>
 * SplaySplit(Root, x)</br>
 * SplayMerge(r1, r2)</br>
 *
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable<T>> {
    /**BZ: extends Comparable<T> */
    class TreeNode<X> {
        X key;
        /* BZ: TreeNode<T> type */
        TreeNode<X> left, right;
        /* Declare parent field for Next() */
        TreeNode<X> parent;
        public TreeNode(X x) {
            key = x;
        }
    }

    private TreeNode<T> root;

    public TreeNode<T> find(T x, TreeNode<T> root) {
        /** Find node with key in the tree or return null */
        if (root == null) return null;
        if (root.key == x) return root;
        else if (x.compareTo(root.key) < 0){
            /* Missing key and stop before reaching a null pointer*/
            if (root.left != null) return find(x, root.left);
            else return root;
        }
        else {
            if (root.right != null) return find(x, root.right);
            else return root;
        }
    }

    /**
     * Key Idea: must add `parent` field;</br>
     * 1. next node is the left-most node of right subtree;</br>
     * 2. next node is the first parent > x;</br>
     * 3. next node does not exist (current is right-most).
     * @param node
     * @return
     */
    public TreeNode<T> next(TreeNode<T> node) {
        if (node == null) return null;
        // Return the left descendant of right child.
        if (node.right != null) {
            node = node.right;
            ///////////////////////////////////////////
            while (node.left != null) node = node.left;
            return node;
            ///////////////////////////////////////////
            // if N.Left = null: return N
            // else: return LeftDescendant(N.left)
            ///////////////////////////////////////////
        }
        // Return the first ancestor to right of node.
        TreeNode<T> pNode = node.parent;
        // BZ: case 3), till root not found? parent is null
        while (pNode != null && pNode.key.compareTo(node.key) < 0)
            pNode = pNode.parent;
        return pNode;
    }

    /**
     * Key Idea:</br>
     * Search for the 1st element in the range.
     * Iterate thru nodes until too big.
     * Append as long as >= x.
     * @param root
     * @param x
     * @param y
     * @return The list of nodes with key between x and y.
     */
    public List<TreeNode<T>> rangeSearch(TreeNode<T> root, T x, T y) {
        List<TreeNode<T>> list = new ArrayList<>();
        // Search for the 1st element but stop before null, if not found
        TreeNode<T> node = find(x, root);
        /**BZ: while x <= node <= y? Find() might return node < x. */
        /**BZ: node might be null */
        while (node != null && node.key.compareTo(y) <= 0) {
            if (node.key.compareTo(x) >= 0) list.add(node);
            node = next(node);
        }
        return list;
    }

    public void insert(T x, TreeNode<T> root) {
        TreeNode<T> node = find(x, root);
        if (node.key == x) return;
        // Add new node with key k as child of P
        TreeNode<T> newNode = new TreeNode<T>(x);
        /**BZ: MUST UPDATE PARENT LINKS AFTER INSERTION */
        newNode.parent = node;
        if (x.compareTo(node.key) <= 0)
            node.left = newNode;
        else
            node.right = newNode;
    }

    public void insert(T x){
        // BZ: return insert(root, x)? void type + must update root
        this.root = insert_helper(root, x);
    }
    private TreeNode<T> insert_helper(TreeNode<T> root, T x){
        if(root == null) return new TreeNode<T>(x);
        // BZ: must compare x with current root val
        int cmp = x.compareTo(root.key);
        if(cmp == 0) return root;  // don't allow duplicate
        // BZ: must update left/right subtree after recursion
        else if(cmp < 0) root.left = insert_helper(root.left, x);
        else root.right = insert_helper(root.right, x);
        return root;
    }

    /**
     * Key Idea: fill the gap by the Next();</br>
     * 1. node has no child:  return null to N's parent</br>
     * 2. node has one child: return the only child</br>
     * 3. node has 2 children:
     * <li>find the Next of node;</br>
     * <li>replace N's key by Next's key;
     * <li>promote Next.Right to replace Next => delete(Next)
     * @param x
     */
    public void delete(T x) {
        root = delete(x, root);
        /**BZ: MUST UPDATE PARENT LINK OF ROOT, IIF NOT NULL */
        if (root != null) root.parent = null;
    }
    private TreeNode<T> delete(T x, TreeNode<T> node) {
        if (node == null) return null;
        int cmp = x.compareTo(node.key);
        if (cmp < 0)
            /**BZ: DELETE STH DOES NOT EXIST? */
            // return delete(x, node.left);
            root.left = delete(x, root.left);
        else if (cmp > 0)
            // return delete(x, node.right);
            root.right = delete(x, root.right);
        /**BZ: ELSE IF OR IF? */
        else if (node.left == null)
            return node.right;
        else if (node.right == null)
            return node.left;
        else {
            // Two children case.
            // Find Next() of R in R.Right.
            // Replace R's key by its Next;
            // Promote Next's right subtree to Next.
            // out.println(root.key);
            TreeNode<T> next = next(node);
            assert(next.left == null);
            node.key = next.key;  // Replace N by its next.
            // BZ: left-most might have right subtree
            promote(next.right, next);
        }
        return root;
    }

    /**
     * How to bypass N? Link C to P + Link P to C.
     * Why parent must not be empty?
     * @param child
     * @param node
     * @return
     */
    private void promote(TreeNode<T> child, TreeNode<T> node) {
        /**Promote `child` to replace `node` */ 
        assert(node.parent != null);
        // Link C to P: child might be null
        if (child != null) child.parent = node.parent;
        // Link P to C
        /**BZ: MUST UPDATE PARENT LINKS AFTER INSERTION */
        if (node == node.parent.left) node.parent.left = child;
        else node.parent.right = child;
        node.parent = null;
    }

    /**
     * Key Idea:
     * <pre>Search for x + Merge subtrees on the path.</pre>
     *
     * @param root
     * @param x
     * @return Two trees, one with elements ≤ x,
     *     one with elements > x .
     */
    public TreeNode<T>[] split(TreeNode<T> root, T x) {
        @SuppressWarnings("unchecked")
        TreeNode<T>[] res = new TreeNode[2];
        if (root == null) return res;
        int cmp = x.compareTo(root.key);
        if (cmp == 0) {
            res[0] = root.left;
            res[1] = root.right;
        }
        // Recursively search left subtree (x is in left subtree).
        else if (cmp < 0) {
            res = split(root.left, x);
            // After backtrack, merge R.Right with R2
            res[1] = mergeWithRoot(root.right, res[1], root);
        }
        // Recursively search right subtree.
        else {
            res = split(root.right, x);
            // Merge left subtree with R2
            res[0] = mergeWithRoot(root.left, res[0], root);
        }
        return res;
    }

    /**
     * Key Idea:</br>
     * Find the right-most node of t1 as new root.
     * Promote root's left child to excluding it.
     * Merge t1, t2 to the new root.
     * 
     * @param t1
     * @param t2
     * @return
     */
    public TreeNode<T> merge(TreeNode<T> t1, TreeNode<T> t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        assert(t1 != null && t2 != null);
        // Get new root by removing largest element of left subtree.
        TreeNode<T> root = t1;
        while (root.right != null) root = root.right;
        assert(root.right == null);
        /**BZ: right-most might have left subtree */
        promote(root.left, root);
        mergeWithRoot(t1, t2, root);
        return root;
    }

    public TreeNode<T> mergeWithRoot(
            TreeNode<T> t1, TreeNode<T> t2, TreeNode<T> root) {
        root.left  = t1;
        root.right = t2;
        t1.parent  = root;
        t2.parent  = root;
        return root;
    }

    public TreeNode<T> orderStatistic(TreeNode<T> root, int k) {
        return null;
    }
}

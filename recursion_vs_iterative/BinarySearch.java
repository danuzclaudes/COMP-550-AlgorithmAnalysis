package recursion_vs_iterative;

/**
 * Splitting Element Analysis on BinarySearch, MSort, QSort
 ****************************************************************************
 * Binary Search:
 * Note: one element still needs to checked.
 * Note: mid is not included to subarray [b/c not match, no need to be further checked]
 *      low<=high:
 *        mid = ... // divide
 *        if   key>a[mid], search mid+1 to high
 *        elif key<a[mid], search low to mid-1
 *        else matched,    return mid
 ****************************************************************************
 * Mergesort:
 * Note: for sorting, one element no needs to be sorted [base case]
 * Note: mid is included into left subarray [b/c still needs to be sorted]
 *      low<high:
 *        // divide
 *        mid = ... 
 *        // conquer:
 *        mergesort(A,low,mid)
 *        mergesort(A,mid+1,high)
 *        // combine
 *        merge(A,low,mid,high) 
***************************************************************************
* Qsort:
* Note: pivot is not included into subarrays 
* [b/c pivot is in its correct position after Partition step --> order statistics]
*      low<high:
*         // divide
*         q = partition(A,low,high)
*         // conquer:
*         qsort(A,low,q-1)
*         qsort(A,q+1,high)
*         // no combine
***************************************************************************/
public class BinarySearch {
    /** 
     * Binary Search in iterative way
     * @param a
     * @param key
     * @return
     */
    public int binary_search_iterative(int[] a, int key){
        int low = 0, high = a.length-1;
        while(low <= high){ // note: allow one-element case
            int mid = low+(high-low)/2;
            // note: splitting point should not be counted to next round
            if(key > a[mid]) low = mid + 1;
            else if(key < a[mid]) high = mid - 1; // note: if...else if V.S. if..if
            else return mid;
        }
        return -1;
    }
    
    /** 
     * Binary Search in recursive way
     * @param a
     * @param key
     * @return
     */
    public int binary_search_recursive(int[] a, int key){
        int p = 0, q = a.length-1;
        return binary_search_recursive(a, key, p, q);
    } 
    private int binary_search_recursive(int[] a, int key, int p, int q){
        if(p > q) return -1; // base case: only one element?
        // p==r, i.e. only one element still needs to be compared with the key
        int mid = p+(q-p)/2;
        if(key > a[mid]) return binary_search_recursive(a, key, mid+1, q);
        else if(key < a[mid]) return binary_search_recursive(a, key, p, mid-1);
        else return mid;
    }

    public static void main(String[] args) {
        // int[] a = {1, 2, 4, 5, 8, 10, 12};
        int[] a = {23, 50, 10, 99, 18, 23, 98, 84, 11, 
                   10, 48, 77, 13, 54, 98, 77, 77, 68};
        BinarySearch bs = new BinarySearch();
        int result = bs.binary_search_iterative(a, 55);
        System.out.println(result);

    }

}

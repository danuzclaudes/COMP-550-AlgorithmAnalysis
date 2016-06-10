
# ALGORITHM ANALYSIS and DATA STRUCTURES
This repository follows the course of Algorithm Analysis.

## adt: 
+ Java implementation for class of COMP 410 Data Structures
  - ArrayList.java
  - ArrayQueue.java
  - ArrayStack.java
  - BinaryHeap.java
  - BST.java
  - HashTable_QuadraticProbing.java
  - HashTable_SeparateChaining.java
  - LinkedList.java
  - ListQueue.java
  - PriorityQueue.java

+ Review for Data structures in Java by weiss
  - comp410-data-structure-review.md

## coursera-algorithmic-toolbox
+ dynamic programming
  - EditDistance.java: Compute the Edit Distance Between Two Strings
  - `Knapsack.java`:
    - Discrete Knapsack (with repetition)
    - Discrete Knapsack (without repetition)
  - LCS3.java: Longest Common Subsequence of Three Sequences
  - `PlacingParentheses.java`: Maximize the Value of an Arithmetic Expression
  - `PrimitiveCalculator.java`
+ greedy
  - Change.java: Changing money optimally
  - CoveringPoints.java: Grouping Children/Covering points by segments
  - `CoveringSegments.java`: Covering Segments by Points
    - Test cases design example
    - **BUG: while f(i) <= f(i) + c -> dead loop (since always true; must fix right)**
  - `DifferentSummands.java`: Pairwise Distinct Summands
  - DotProduct.java: Minimum Dot Product
  - `FractionalKnapsack.java`: Find the max value of fractions of items to fit the knapsack
+ divide and conquer
  - BinarySearch.java
  - Inversions.java
  - `MajorityElement.java`: Check if sequence has a majority element
  - `PointsAndSegments.java`: Count for each point # segments containing it
  - QSort-3WayPartition.java: Quick Sort with equal entries; 3-way partitioning
+ growth rate/asymptotic notation
  - `FibonacciHugeModulo.java`: Huge Fibonacci Number modulo m.
  - FibonacciLastDigit.java: Find the Last Digit of a Large Fibonacci Number
  - FibonacciPrint.java: Print first N Fibonacci numbers
  - LCM.java: Least Common Multiple; **lcm * gcd = a * b**
  - NumArrayGCD.java: Compute GCD for an array of integers
+ stress testing
  - `MaxPairwiseProduct.java`: Stress test example
  - RandomRange.java

## coursera-data-structures
+ list stack tree
  - check-brackets.java: Check if brackets are balanced
  - `process-packages.java`: Network packet processing simulation.
    - A request waits after the last one in queue, or immediately by arrival if idle
  - `tree-height-N-children.java`:
    - arbitrary tree, not necessarily a binary tree
    - **Height(tree) is the distance from the deepest leaf to root**
    - **BUG: outer-loop iterator i changed through inner-loop? -> must fix it by next iteration**
+ priority queues, disjoint sets
  - BuildHeap.java: Convert an array into min-heap, with 0-index
  - `ParallelJobQueue.java`: Simulate to process jobs in parallel; wait for the 1st free thread
  - MergingTables.java: Simulate merge operations with tables in a database.
+ hashtables
  - HashChains.java: Build a HashSet using separate chaining
  - PhoneBook.java: Query contact names by phone numbers; Universal Hash Family
  - `RabinKarp.java`:
    - GetOccurrences():
      - Traverse all substrings of size |P|. If hash(P) != hash(S), not match; o.w., check if equal.
    - PrecomputeHashes():
      - Hashes for all substrings of size |P|
      - H[i] denotes the hash of i: 0..|T|-|P| -> array H's size is |T|-|P|+1
      - Compute the hash of last substring i=|T|-|P| by Polynomial hash family
      - Generate x^|P|
      - `H[i] = (x*H[i + 1] + T[i] - T[i + |P|]*x^|P|) mod p`
    - NextPrime() & IsPrime():
      - Must choose `p >> |P|*|T|` to ignore false alarms
      - To test whether a number is prime or not, try dividing it by numbers from 2 to sqrt(n).
    - `HashFuction(): Polynomial hash family`
      - Must choose a big prime and multiplier;
      - `h = (S[i] + h * x) % p`
      - Integer ovreflow: store into long type
    - Take modular with negative numbers: `(T[i] - ...) mod p`
      - add p to the result and take modulo p again: `int x = ((a - b) % p + p) % p`
+ binary search trees
  - BinarySearchTree.java: basic implementation of BST ADT.
    - Update parent/left/right links after each operation with the subtree
  - `SetRangeSum.java`: Splay Tree impl of set with range sums.
    - erase(x):
      - **BUG: MUST DETACH ROOT'S PARENT LINK AFTER DELETION, iif not null`**.
      - **BUG: MUST UPDATE LEFT/RIGHT SUBTREES AFTER BACKTRACK`: DELETE NON-EXISTING CASE**
    - find(x): Stop before null for RangeSearch()
    - next(node): Note case that next larger does not exist.
    - rangeSearch(x,y):
      - Search for the left bound and iterate thru nodes until too big, OR overflow before right bound
  - `tree_orders`:
    - Inorder: push all nodes on path to left-most, then repeat thru right subtree.
    - PostOrder: **Mirror of PreOrder**

## java-docs
+ Java Docs from Oracle

## recursion-vs-iterative
+ Comparasion on recursive methods with iterative
  - Binary Search
  - ConsecutiveTermSum
  - Factorial
  - Fibonacci numbers
  - GCD
  - UtopianTree

## basic-library-example
+ This program supports the following functions:
  - load books/movies data from disk;
  - Check in a book/movie
  - Check out a book/movie
  - Add a new book/movie
  - Display all books
  - Display all movies
  - Query books through keywords
  - Query movies through keywords

## background-timer-toy
Toy example of a Java multi-threaded timer with UI.

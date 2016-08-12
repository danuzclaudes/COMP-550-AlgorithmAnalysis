
# ALGORITHM ANALYSIS and DATA STRUCTURES
This repository follows the course of Algorithm Analysis.  I've also published my source
code for coursera courses of "Algorithm Toolbox", "Data Structures" and "Algorithms on
Graphs". **The code were not uploaded for online judgement.** However, most programs have
either implemented **stress testing** or tested against examples/cases from the forum. If
you find any errors or test cases that break online judgement, **please send out an issue
on GitHub.** All information like test cases or bugs are welcomed.

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
      - Traverse all substrings of size |P|. If hash(P) != hash(S), not match; o.w., check
        if equal.
    - PrecomputeHashes():
      - Hashes for all substrings of size |P|
      - H[i] denotes the hash of i: 0..|T|-|P| -> array H's size is |T|-|P|+1
      - Compute the hash of last substring i=|T|-|P| by Polynomial hash family
      - Generate x^|P|
      - `H[i] = (x*H[i + 1] + T[i] - T[i + |P|]*x^|P|) mod p`
    - NextPrime() & IsPrime():
      - Must choose `p >> |P|*|T|` to ignore false alarms
      - To test whether a number is prime or not, try dividing it by numbers from 2 to
        sqrt(n).
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
      - **BUG: MUST UPDATE LEFT/RIGHT SUBTREES AFTER BACKTRACK`: DELETE NON-EXISTING
        CASE**
    - find(x): Stop before null for RangeSearch()
    - next(node): Note case that next larger does not exist.
    - rangeSearch(x,y):
      - Search for the left bound and iterate thru nodes until too big, OR overflow before
        right bound
  - `tree_orders`:
    - Inorder: push all nodes on path to left-most, then repeat thru right subtree.
    - PostOrder: **Mirror of PreOrder**

## coursera-algorithms-on-graphs
+ graphs_decomposition
  - Acyclicity.java: Detect cycle in unweighted graphs.
    - Undirected: DFS to mark explored and check if the adjacent vertex is visited and not
      parent.
    - Directed: DFS to check if the adjacent vertex is on recursion stack.
  - ConnectedComponents.java: Count # of CCs.
  - `Reachability.java`: DFS to explore connectivity between x and y.
  - `StronglyConnected.java`: Count # of SCCs.
    - Remove all vertices in sink CC and continue on next sink CC.
    - How to find sink CC in G? source CC in G', i.e. max post orders.
    - DFS all vertices in GR in postorder; expore each sink CC in reversed postorder.
  - TopoSort.java: `Topological Sort = DFS + reverse postorder`.

+ graphs_paths
  - BFS.java: add vertices on same layer before next layer.
    - `dist[i] stores the layer number.`
  - `Bipartite.java`: if next adjacent is same color, found an edge w/ same color.
  - `Dijkstra.java`: merge min-distance vertex into visited.
    - Assumne +ve weights and may have cycles.
    - `dist[i] stores the distance from S to each vertex i.`
    - Forget about the min-dist once merged (already known).

+ bellman_ford
  - `BellmanFord.java`: relax all edges by |V| - 1 times.
    - Assume single source S + negative weights but no negative cycles.
    - `dist[i] stores the shortest distance to each vertex on ith iteration.`
  - NegativeCycle.java: check whether there's negative cycle in G.
    - Run BF |V| - 1 times and check if dist[] changed on |V|-th.
    - BZ: `Multiple sources may overflow +inf: dist[u] + w`? fill by 0?
  - ShortestPaths.java: check whether the negative cycle can be reached from S.
    - Record all vertices changed on |V|-th BF; BFS from the set and they have no shortest
      paths.

+ spanning_trees
  - `Kruskal.java`: Disjoint Sets
    - Build Disjoint Sets data structure and add the lightest edge if there's no cycle.
  - `Prim.java`:
    - Attach the min-cost vertex into tree.
    - `cost[i]/mst[i] stores the cost of attaching each vertex i into tree.`
    - Forget about the min-mst once attached.

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

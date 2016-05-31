
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
    - BUG: while f(i) <= f(i) + c -> dead loop (since always true; must fix right)
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
  - LCM.java: Least Common Multiple; lcm * gcd = a * b
  - NumArrayGCD.java: Compute GCD for an array of integers
+ stress testing
  - `MaxPairwiseProduct.java`: Stress test example
  - RandomRange.java

## coursera-data-structures
+ list-stack-tree
  - check-brackets.java: Check if brackets are balanced
  - `process-packages.java`:
    - Network packet processing simulation.
    - Store request before processing...until finishes
    - Start time of a request is at least its arrival time Ai
    - A request starts after last popped-out request finishes, or immediately if idle
  - `tree-height-N-children.java`:
    - arbitrary tree, not necessarily a binary tree
    - Height(tree) is the distance from the deepest leaf to root
    - Trace to upper level and count on the way...
    - parent[i] helps identifying a leaf + trace upwards
    - BUG: outer-loop iterator i changed through inner-loop? -> must fix it by next iteration

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

package dynamic_programming;

public class LCS2 {

    /**
     * Key Idea:
     * Subproblem: give some two subsequences ending by
     *             Ai, Bj, which has optimal length of
     *             LCS(i, j). The last letter can be
     *             either Ai=Bj or not.
     * Recurrence: LCS(i,j)=max{
     *     LCS(i-1,j-1) + 1 if Ai=Bj, otherwise
     *     max(LCS[i-1,j], LCS[i,j-1])
     * }
     * note: reduce the size of either one subproblem
     */

}

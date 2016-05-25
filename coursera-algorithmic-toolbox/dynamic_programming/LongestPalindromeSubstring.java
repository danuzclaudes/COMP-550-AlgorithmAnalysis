package dynamic_programming;

public class LongestPalindromeSubstring {
    /**
     * key idea: dp; T: O(N^2) S: O(N^2)
     * table[i][j] denotes substr[i..j] is a palindrome;
     * table[i][j] is true iif match && table[i + 1][j - 1] is true;
     * `traverse all size of substr from 1 to n`;
     * try each substr start at i w/ n - size letters left;
     * continue if unmatch; mark true if size <= 2;
     * if is palindrome, update max size and start;
     *
     * @param str
     * @return
     */
    public static String longestPalindrome(String str){
        if(str == null || str.length() == 0) return "";
        int n = str.length(), maxSize = 0, maxStart = 0;
        boolean[][] table = new boolean[n][n];
        for(int size = 1; size <= n; size++){
            for(int i = 0; i <= n - size; i++){
                int j = i + size - 1;
                if(str.charAt(i) != str.charAt(j)) continue;
                if(size <= 2 || table[i + 1][j - 1]) table[i][j] = true;
                if(table[i][j]){
                    maxSize = Math.max(maxSize, size);
                    maxStart = i;
                }
            }
        }
        return str.substring(maxStart, maxStart + maxSize);
    }
}

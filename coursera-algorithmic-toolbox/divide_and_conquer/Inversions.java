package divide_and_conquer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Inversions {

    /**
     * Key Idea: MergeSort
     * When A[i] > B[j], since all [i..h1] > B[j] and
     * sits left of B[j], count them as inversions.
     *
     * @param a     The input array.
     * @param b     The temporary array to store merged entries;
     *              if b is declared locally for each recursive
     *              call of merge, there'll be `logN` temporary
     *              arrays; but there only needs to be one array
     *              active and to be passed into recursive calls.
     *              Use the same portion of b as input array a.
     * @param left  The left bounds that is active in a
     * @param right The right bounds
     * @return      The total # of inversions
     */
    private static long getNumberOfInversions(int[] a, int[] b, int left, int right) {
        long numberOfInversions = 0;
        if (right <= left + 1) {
            return numberOfInversions;
        }
        int ave = (left + right) / 2;
        numberOfInversions += getNumberOfInversions(a, b, left, ave);
        numberOfInversions += getNumberOfInversions(a, b, ave, right);
        // Merge two halves and accumulate counts from subproblems
        numberOfInversions += merge(a, b, left, ave - 1, ave, right - 1);
        return numberOfInversions;
    }
    private static long merge(int[] a, int[] b, int l1, int h1, int l2, int h2) {
        int count = 0;
        // BZ: use same portion of temporary array as the input array a
        int i = l1, j = l2, index = l1;
        while (i <= h1 && j <= h2) {  // ensure neither half is exhausted
            if (a[i] <= a[j])
                b[index++] = a[i++];  // BZ: must increase pointer once chosen
            else {
                // # of inversions = # of entries in [i..h1]? []
                count += h1 - i + 1;
                b[index++] = a[j++];
            }
        }
        // append the rest of non-empty entries to merged space
        for (int idx = i; idx <= h1; idx++) b[index++] = a[idx];
        for (int idx = j; idx <= h2; idx++) b[index++] = a[idx];
        // replace original array a by merged space b
        for (int idx = l1; idx <= h2; idx++) {
            a[idx] = b[idx];
        }
        return count;
    }

    public static void main(String[] args) {
        try {
            // String filename = "test-inversions-medium.txt";  // 2372
            String filename = "test-inversions-large.txt";  // 2507223936
            BufferedReader br = new BufferedReader(new FileReader(
                    "./divide_and_conquer/" + filename));
            String line = null;
            int n = Integer.parseInt(br.readLine()), index = 0;
            int[] a = new int[n];
            while ((line = br.readLine()) != null) {
                a[index++] = Integer.parseInt(line);
            }
            int[] b = new int[n];
            System.out.println(getNumberOfInversions(a, b, 0, a.length));
            br.close();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }

        /* Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        scanner.close();*/
    }
}


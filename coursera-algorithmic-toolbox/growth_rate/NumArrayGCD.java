package growth_rate;

/**
 * Compute GCD for an array of integers.
 *
 * Lemma: a = r + b * q, d can divide both a and b
 * => gcd(a, b) = gcd(b, r) = gcd(a, r)
 */
public class NumArrayGCD {
    
    /**
     * key idea: keep previous pair's gcd as divisor of current pair;
     * if there's any entry is 0, return 0.
     *
     * @param nums
     * @return
     */
	public static int gcd(int[] nums){
		if(nums == null || nums.length == 0 || nums[0] == 0) return 0;
		else if(nums.length == 1) return nums[0];
		// BZ: int res = 0? initially m=n[1], n=[0];
		int res = nums[0];
		for(int i = 1; i < nums.length; i++){
			// BZ: current num is 0, return 0;
			if(nums[i] == 0) return 0;
			// BZ: computeGCD(nums[i], nums[i - 1])?
			// should keep last pair's gcd as divisor for current num!
			res = computeGCD(nums[i], res);
		}
		return res;
	}
	/**
	 * If m can be divided by n, return n;
	 * set n as m, remainder as n, compute until n can divide m;
	 * Runtime: log(mn)
	 * 
	 * @param m
	 * @param n
	 * @return
	 */
	private static int computeGCD(int m, int n){
		int r = 0;
		while((r = m % n) != 0){
			m = n;
		 	n = r;
		}
		return n;
	}

	public static int gcd2(int[] nums) {
		if (nums == null || nums.length == 0 || nums[0] == 0) return 0;
		if (nums.length == 1) return nums[0];
		int rst = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] == 0) return 0;
			// m is the dividend, rst is the divisor
			int m = nums[i];
			while (m % rst != 0) {
				int tmp = rst;
				rst = m % rst;
				m = tmp;
			}
		}
		return rst;
	}
	
	public static void main(String[] args) {
		System.out.println(gcd(new int[]{12, 18, 24, 32}));
		System.out.println(gcd(new int[]{1, 5}));
		System.out.println(gcd(new int[]{22, 31}));
	}
}
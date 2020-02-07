public class Helper  {

	/**
	 * Class constructor.
	 */
	private Helper() {

	}

	/**
	 * This method is used to check if a number is prime or not
	 *
	 * @param x A positive integer number
	 * @return boolean True if x is prime; Otherwise, false
	 */
	public static boolean isPrime(int x) {
		if (x == 1) {
			return false;
		} else if (x == 2) {
			return true;
		} else {
			for (int i = 2; i < x; i++) {
				if (x % i == 0) {
					return false;
				}
			}
			return true;
		}
	}


	/**
	 * This method is used to get the largest prime factor
	 *
	 * @param x A positive integer number
	 * @return int The largest prime factor of x
	 */
	public static int getLargestPrimeFactor(int x) {
		int largest = -1;
		while (x % 2 == 0){
			largest = 2;
			x/=2;
		}
		for (int i = 3; i <= Math.sqrt(x);i+=2){
			while (x % i == 0){
				largest = i;
				x = x / i;
			}
		}
		if (x > 2){
			largest = x;
		}
		return largest;
	}
}
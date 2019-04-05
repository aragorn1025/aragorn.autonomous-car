package aragorn.autonomous.car.zold.math.operation;

import java.security.InvalidParameterException;
import java.util.Scanner;

/**
 * {@code MathOperation} is a library within useful math function.
 * 
 * @author Aragorn
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Math.html">Math</a>
 */
public class MathOperation {

	/**
	 * {@code Scanner} for {@code valueInRange(double, double)}
	 */
	private static Scanner scanner;

	/**
	 * Tell if value is in the range.<br>
	 * <br>
	 * The range must in the format of (left notation)(left value)(comma)(right value)(right notation).<br>
	 * - left notation: must be {@code '('}(is smaller than) or {@code '['}(is smaller than or equals to)<br>
	 * - left value: must be {@code "-infinity"}, {@code "-inf"} or real number smaller than the right value<br>
	 * - comma: comma with space or without space <br>
	 * - right value: must be {@code "+infinity"}, {@code "+inf"} or real number larger than the left value<br>
	 * - right notation: must be {@code ')'}(is larger than) or {@code ']'}(is larger than or equals to)<br>
	 * 
	 * @param value
	 *     the value to check
	 * @param range
	 *     the range to check
	 * @return true if the value is in the range
	 */
	public static boolean valueInRange(double value, String range) {
		char[] c = range.replaceAll(" ", "").toCharArray();
		boolean isLEquals, isREquals;
		if (c[0] == '[') {
			isLEquals = true;
		} else if (c[0] == '(') {
			isLEquals = false;
		} else {
			throw new InvalidParameterException("Range must start with '[' or '('.");
		}
		if (c[c.length - 1] == ']') {
			isREquals = true;
		} else if (c[c.length - 1] == ')') {
			isREquals = false;
		} else {
			throw new InvalidParameterException("Range must end with ']' or ')'.");
		}
		scanner = new Scanner((new String(c, 1, c.length - 2)).replaceAll(",", " "));
		if (!scanner.hasNext())
			throw new InvalidParameterException("Range must contain with only two value.");
		String lString = scanner.next().toLowerCase();
		if (!scanner.hasNext())
			throw new InvalidParameterException("Range must contain with only two value.");
		String rString = scanner.next().toLowerCase();
		if (scanner.hasNext())
			throw new InvalidParameterException("Range must contain with only two value.");
		double lValue = 0, rValue = 0;
		if (lString.equals("-infinity") || lString.equals("-inf")) {
			lValue = Double.NEGATIVE_INFINITY;
		} else {
			scanner = new Scanner(lString);
			if (scanner.hasNextDouble()) {
				lValue = scanner.nextDouble();
			} else {
				throw new InvalidParameterException("The first value must be -infinity, -inf or real number.");
			}
		}
		if (rString.equals("+infinity") || rString.equals("+inf")) {
			rValue = Double.POSITIVE_INFINITY;
		} else {
			scanner = new Scanner(rString);
			if (scanner.hasNextDouble()) {
				rValue = scanner.nextDouble();
			} else {
				throw new InvalidParameterException("The second value must be +infinity, +inf or real number.");
			}
		}
		if (lValue > rValue)
			throw new InvalidParameterException("The first value must be larger or equal then the second value.");
		if (isLEquals) {
			if (value == lValue) {
				return true;
			}
		}
		if (isREquals) {
			if (value == rValue) {
				return true;
			}
		}
		if (lValue < value && value < rValue) {
			return true;
		} else {
			return false;
		}
	}
}
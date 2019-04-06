package aragorn.autonomous.car.math.operation;

import java.security.InvalidParameterException;
import java.util.Scanner;

/**
 * {@code MathOperation} is a library within useful math function.
 * 
 * @author Aragorn
 */
public class MathOperation {

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
		range = range.replaceAll(" ", "");
		if (range.charAt(0) != '[' && range.charAt(0) != '(')
			throw new InvalidParameterException("Range must start with '[' or '('.");
		if (range.charAt(range.length() - 1) != '[' && range.charAt(range.length() - 1) != '(')
			throw new InvalidParameterException("Range must end with ']' or ')'.");
		boolean is_leftt_equals = (range.charAt(0) == '[');
		boolean is_right_equals = (range.charAt(range.length() - 1) == ']');

		if (range.length() - range.replaceAll(",", "").length() != 1)
			throw new InvalidParameterException("Range must contain with only two value.");
		Scanner range_scanner = new Scanner(range.substring(1, range.length() - 2).replaceAll(",", " "));
		String leftt_expression = range_scanner.next().toLowerCase();
		String right_expression = range_scanner.next().toLowerCase();
		range_scanner.close();
		double leftt_value = 0, right_value = 0;
		if (leftt_expression.equals("-infinity") || leftt_expression.equals("-inf")) {
			leftt_value = Double.NEGATIVE_INFINITY;
		} else {
			Scanner left_string_scanner = new Scanner(leftt_expression);
			if (left_string_scanner.hasNextDouble()) {
				leftt_value = left_string_scanner.nextDouble();
				left_string_scanner.close();
			} else {
				left_string_scanner.close();
				throw new InvalidParameterException("The first value must be -infinity, -inf or real number.");
			}
		}
		if (right_expression.equals("+infinity") || right_expression.equals("+inf")) {
			right_value = Double.POSITIVE_INFINITY;
		} else {
			Scanner right_string_scanner = new Scanner(right_expression);
			if (right_string_scanner.hasNextDouble()) {
				right_value = right_string_scanner.nextDouble();
				right_string_scanner.close();
			} else {
				right_string_scanner.close();
				throw new InvalidParameterException("The second value must be +infinity, +inf or real number.");
			}
		}
		if (leftt_value > right_value)
			throw new InvalidParameterException("The first value must be larger or equal then the second value.");
		if (is_leftt_equals && value == leftt_value)
			return true;
		if (is_right_equals && value == right_value)
			return true;
		return (leftt_value < value && value < right_value);
	}
}
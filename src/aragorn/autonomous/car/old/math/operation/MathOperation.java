package aragorn.autonomous.car.old.math.operation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
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
	 * Returns the trigonometric cosine of an angle. Special cases:<br>
	 * - If the argument is NaN or an infinity, then the result is NaN.
	 * 
	 * @param angdeg
	 *            an angle, in degrees
	 * @return the cosine of the degree
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Math.html#cos(double)">Math.cos(double)</a>
	 */
	public static double cos(double angdeg) {
		return Math.cos(Math.toRadians(angdeg));
	}

	/**
	 * Return the determinant value of a 2 times 2 matrix.
	 * 
	 * @param a_1_1
	 *            the element at the first row and the first column
	 * @param a_2_1
	 *            the element at the second row and the first column
	 * @param a_1_2
	 *            the element at the first row and the second column
	 * @param a_2_2
	 *            the element at the second row and the second column
	 * @return the value of the determinant, that is (a_1_1 * a_2_2 - a_1_2 * a_2_1)
	 */
	public static double determinant(double a_1_1, double a_2_1, double a_1_2, double a_2_2) {
		if (Double.isNaN(a_1_1)) {
			throw new InvalidParameterException("Input parameter for determinant a_1_1 should not be NaN.");
		} else if (Double.isNaN(a_2_1)) {
			throw new InvalidParameterException("Input parameter for determinant a_2_1 should not be NaN.");
		} else if (Double.isNaN(a_1_2)) {
			throw new InvalidParameterException("Input parameter for determinant a_1_2 should not be NaN.");
		} else if (Double.isNaN(a_2_2)) {
			throw new InvalidParameterException("Input parameter for determinant a_2_2 should not be NaN.");
		}
		return a_1_1 * a_2_2 - a_1_2 * a_2_1;
	}

	/**
	 * Find the largest value of the array form {@code fromIndex} to {@code toIndex}.
	 * 
	 * @param array
	 *            the reference of the array
	 * @param fromIndex
	 *            the starting index, {@code fromIndex} including
	 * @param toIndex
	 *            the ending index, {@code toIndex} excluding
	 * @return the largest value of the array form fromIndex to toIndex
	 */
	public static double getArrayMax(double[] array, int fromIndex, int toIndex) {
		if (array == null) {
			throw new NullPointerException("Array is null at MathOperation.getMax(double[], int, int)");
		}
		if (array.length == 0 || toIndex > array.length || fromIndex >= toIndex) {
			throw new IndexOutOfBoundsException("Index of of bounds at MathOperation.getMax(double[], int, int)");
		}
		double val = array[fromIndex];
		for (int i = fromIndex + 1; i < toIndex; i++) {
			val = Math.max(val, array[i]);
		}
		return val;
	}

	/**
	 * Find the largest value of the array form {@code fromIndex} to {@code toIndex}.
	 * 
	 * @param array
	 *            the reference of the array
	 * @param fromIndex
	 *            the starting index, {@code fromIndex} including
	 * @param toIndex
	 *            the ending index, {@code toIndex} excluding
	 * @return the largest value of the array form fromIndex to toIndex
	 */
	public static <N extends Number> double getArrayMax(ArrayList<N> array, int fromIndex, int toIndex) {
		if (array == null) {
			throw new NullPointerException("Array is null at MathOperation.getMax(double[], int, int)");
		}
		if (array.size() == 0 || toIndex > array.size() || fromIndex >= toIndex) {
			throw new IndexOutOfBoundsException("Index of of bounds at MathOperation.getMax(double[], int, int)");
		}
		double val = array.get(fromIndex).doubleValue();
		for (int i = fromIndex + 1; i < toIndex; i++) {
			val = Math.max(val, array.get(i).doubleValue());
		}
		return val;
	}

	/**
	 * Find the smallest value of the array form {@code fromIndex} to {@code toIndex}.
	 * 
	 * @param array
	 *            the reference of the array
	 * @param fromIndex
	 *            the starting index, {@code fromIndex} including
	 * @param toIndex
	 *            the ending index, {@code toIndex} excluding
	 * @return the smallest value of the array form fromIndex to toIndex
	 */
	public static double getArrayMin(double[] array, int fromIndex, int toIndex) {
		if (array == null) {
			throw new NullPointerException("Array is null at MathOperation.getMin(double[], int, int)");
		}
		if (array.length == 0 || toIndex > array.length || fromIndex >= toIndex) {
			throw new IndexOutOfBoundsException("Index of of bounds at MathOperation.getMin(double[], int, int)");
		}
		double val = array[fromIndex];
		for (int i = fromIndex + 1; i < toIndex; i++) {
			val = Math.min(val, array[i]);
		}
		return val;
	}

	/**
	 * Find the smallest value of the array form {@code fromIndex} to {@code toIndex}.
	 * 
	 * @param array
	 *            the reference of the array
	 * @param fromIndex
	 *            the starting index, {@code fromIndex} including
	 * @param toIndex
	 *            the ending index, {@code toIndex} excluding
	 * @return the smallest value of the array form fromIndex to toIndex
	 */
	public static <N extends Number> double getArrayMin(ArrayList<N> array, int fromIndex, int toIndex) {
		if (array == null) {
			throw new NullPointerException("Array is null at MathOperation.getMin(double[], int, int)");
		}
		if (array.size() == 0 || toIndex > array.size() || fromIndex >= toIndex) {
			throw new IndexOutOfBoundsException("Index of of bounds at MathOperation.getMin(double[], int, int)");
		}
		double val = array.get(fromIndex).doubleValue();
		for (int i = fromIndex + 1; i < toIndex; i++) {
			val = Math.min(val, array.get(i).doubleValue());
		}
		return val;
	}

	/**
	 * Find the largest value of the parameters.
	 * 
	 * @param a1
	 *            the first parameter
	 * @param a2
	 *            the first parameter
	 * @param ax
	 *            the other parameters
	 * @return the largest parameter among the input parameters
	 */
	public static double max(double a1, double a2, double... ax) {
		double val = Math.max(a1, a2);
		for (int i = 0; i < ax.length; i++) {
			val = Math.max(val, ax[i]);
		}
		return val;
	}

	/**
	 * Find the smallest value of the parameters.
	 * 
	 * @param a1
	 *            the first parameter
	 * @param a2
	 *            the first parameter
	 * @param ax
	 *            the other parameters
	 * @return the smallest parameter among the input parameters
	 */
	public static double min(double a1, double a2, double... ax) {
		double val = Math.min(a1, a2);
		for (int i = 0; i < ax.length; i++) {
			val = Math.min(val, ax[i]);
		}
		return val;
	}

	/**
	 * Returns the trigonometric sine of an angle. Special cases:<br>
	 * - If the argument is NaN or an infinity, then the result is NaN.<br>
	 * - If the argument is zero, then the result is a zero with the same sign as the argument.
	 * 
	 * @param angdeg
	 *            an angle, in degrees
	 * @return the sine of the degree
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Math.html#sin(double)">Math.sin(double)</a>
	 */
	public static double sin(double angdeg) {
		return Math.sin(Math.toRadians(angdeg));
	}

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
	 *            the value to check
	 * @param range
	 *            the range to check
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
		if (!scanner.hasNext()) {
			throw new InvalidParameterException("Range must contain with only two value.");
		}
		String lString = scanner.next().toLowerCase();
		if (!scanner.hasNext()) {
			throw new InvalidParameterException("Range must contain with only two value.");
		}
		String rString = scanner.next().toLowerCase();
		if (scanner.hasNext()) {
			throw new InvalidParameterException("Range must contain with only two value.");
		}
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
		if (lValue > rValue) {
			throw new InvalidParameterException("The first value must be larger or equal then the second value.");
		}
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

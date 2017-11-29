package javar.lfrivera.util;

import javar.lfrivera.entity.ComparisonTypeEnum;

/**
 * Class that allows to compare two elements.
 * 
 * @author lfrivera
 * @param <T>
 *            The type of the elements to compare.
 */
public class Comparator<T> {

	/**
	 * The type of the comparison.
	 */
	private ComparisonTypeEnum type;

	public Comparator(ComparisonTypeEnum type) {
		this.type = type;
	}

	/**
	 * Allows to compare two elements.
	 * 
	 * @param obj1
	 *            The first element to compare.
	 * @param obj2
	 *            The second element to compare.
	 * @return Boolean value that describes the result of the comparison.
	 */
	public boolean compare(T obj1, T obj2) {

		Boolean result = null;

		if (obj1 instanceof Integer) {

			int n1 = (int) obj1;
			int n2 = (int) obj2;

			result = compareNumbers(n1, n2);

		}

		if (obj1 instanceof Double) {

			double n1 = (double) obj1;
			double n2 = (double) obj2;

			result = compareNumbers(n1, n2);

		}

		if (obj1 instanceof Long) {

			long n1 = (long) obj1;
			long n2 = (long) obj2;

			result = compareNumbers(n1, n2);

		}

		if (obj1 instanceof Float) {

			float n1 = (float) obj1;
			float n2 = (float) obj2;

			result = compareNumbers(n1, n2);

		}

		if (obj1 instanceof String) {

			String s1 = (String) obj1;
			String s2 = (String) obj2;

			result = compareStrings(s1, s2);

		}

		if (obj1 instanceof Boolean) {

			int i1 = ((boolean) obj1) == true ? 1 : 0;
			int i2 = ((boolean) obj2) == true ? 1 : 0;

			result = compareNumbers(i1, i2);

		}

		if (result == null) {

			throw new UnsupportedOperationException("Could not compare those elements.");

		} else {
			return result;
		}
	}

	/**
	 * Allows to compare two numbers.
	 * 
	 * @param n1
	 *            The first number to compare.
	 * @param n2
	 *            The second number to compare.
	 * @return Boolean value that describes the result of the comparison.
	 */
	private Boolean compareNumbers(double n1, double n2) {

		Boolean result;

		switch (type) {

		case EQUAL_TO:

			result = n1 == n2;

			break;

		case NOT_EQUAL_TO:

			result = (n1 != n2);

			break;

		case GREATER_THAN:

			result = (n1 > n2);

			break;

		case GREATER_THAN_EQUAL_TO:

			result = (n1 >= n2);

			break;

		case LESS_THAN:

			result = (n1 < n2);

			break;

		case LESS_THAN_EQUAL_TO:

			result = (n1 <= n2);

			break;

		default:

			result = null;

			break;

		}

		return result;
	}

	/**
	 * Allows to compare two strings.
	 * 
	 * @param s1
	 *            The first String to compare.
	 * @param s2
	 *            The second String to compare.
	 * @return Boolean value that describes the result of the comparison.
	 */
	private Boolean compareStrings(String s1, String s2) {

		Boolean result;

		switch (type) {

		case EQUAL_TO:

			result = (s1.equals(s2));

			break;

		case NOT_EQUAL_TO:

			result = (!s1.equals(s2));

			break;

		case GREATER_THAN:

			result = (s1.length() > s2.length());

			break;

		case GREATER_THAN_EQUAL_TO:

			result = (s1.length() >= s2.length());

			break;

		case LESS_THAN:

			result = (s1.length() < s2.length());

			break;

		case LESS_THAN_EQUAL_TO:

			result = (s1.length() <= s2.length());

			break;

		default:

			result = null;

			break;

		}

		return result;

	}

}

package javar.lfrivera.entity;

/**
 * This class allows to represent an R DataFrame in Java.
 * 
 * @author lfrivera
 *
 */
public class Dataframe {

	/**
	 * The raw representation (Objects matrix) of the dataframe.
	 */
	private Object[][] rawRepresentation;

	/**
	 * Constructor of the class.
	 * 
	 * @param rawRepresentation
	 */
	public Dataframe(Object[][] rawRepresentation) {
		this.rawRepresentation = rawRepresentation;
	}

	/**
	 * Allows to print the first n rows of the dataframe.
	 * 
	 * @param n
	 *            The first n rows of the dataframe.
	 */
	private void realizeHead(int n) {

		for (int i = 0; i <= n; i++) {
			for (int j = 0; j < rawRepresentation[i].length; j++) {
				System.out.print(rawRepresentation[i][j] + " ");
			}
			System.out.println();
		}

	}

	/**
	 * Allows to obtain the raw representation of the dataframe.
	 * 
	 * @return The raw representation of the dataframe.
	 */
	public Object[][] getRawRepresentation() {
		return rawRepresentation;
	}

	/**
	 * Allows to print the complete dataframe.
	 */
	public void print() {

		for (int i = 0; i < rawRepresentation.length; i++) {
			for (int j = 0; j < rawRepresentation[i].length; j++) {
				System.out.print(rawRepresentation[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Allows to print 5% of the datafrane,
	 */
	public void head() {

		int subRows = (int) (rawRepresentation.length * 0.05);
		realizeHead(subRows);
	}

	/**
	 * Allows to print the first n rows of the dataframe.
	 * 
	 * @param n
	 *            The first n rows of the dataframe.
	 */
	public void head(int n) {
		realizeHead(n);
	}

}

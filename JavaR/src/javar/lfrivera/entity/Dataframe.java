package javar.lfrivera.entity;

import java.util.ArrayList;

import javar.lfrivera.util.Comparator;

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
	 * Allows to obtain a column from the dataframe.
	 * 
	 * @param column
	 *            The index of the column.
	 * @return The column found.
	 */
	private Object[] realizeGetColumn(int column) {

		Object[] columnArray;

		try {

			columnArray = new Object[nrow()];
			for (int i = 1; i <= columnArray.length; i++) {

				columnArray[i - 1] = rawRepresentation[i][column];

			}

		} catch (Exception e) {
			columnArray = null;
		}

		return columnArray;

	}

	/**
	 * Allows to determine the data type of a column.
	 * 
	 * @param index
	 *            The name of the column.
	 * @return The data type of the column.
	 */
	@SuppressWarnings("rawtypes")
	private Class realizeTypeOfColumn(int index) {

		Class c = null;

		try {

			String first = (String) rawRepresentation[1][index];

			try {

				Integer.parseInt(first);
				c = Integer.class;

			} catch (Exception e) {

				try {

					Double.parseDouble(first);
					c = Double.class;

				} catch (Exception e1) {

					try {

						Long.parseLong(first);
						c = Long.class;

					} catch (Exception e2) {

						try {

							Float.parseFloat(first);
							c = Float.class;

						} catch (Exception e3) {

							if (first.equalsIgnoreCase("TRUE") || first.equalsIgnoreCase("FALSE")
									|| first.equalsIgnoreCase("T") || first.equalsIgnoreCase("F")) {

								c = Boolean.class;

							} else {

								c = String.class;

							}

						}

					}

				}

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		return c;

	}

	/**
	 * Allows to determine whether a column is numeric.
	 * 
	 * @param index
	 *            The column to be evaluated.
	 */
	private boolean realizeIsColNumeric(int index) {
		
		Class<?> type = typeOfColumn(index);
		
		return (type == Integer.class || type == Float.class || type == Double.class || type == Long.class);
		
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

	/**
	 * Allows to obtain the number of rows in the dataframe.
	 * 
	 * @return The number of rows in the dataframe.
	 */
	public int nrow() {
		return rawRepresentation.length - 1;
	}

	/**
	 * Allows to obtain the number of columns in the dataframe.
	 * 
	 * @return The number of rows in the dataframe.
	 */
	public int ncol() {
		return rawRepresentation[0].length;
	}

	/**
	 * Allows to append a new row in the dataframe.
	 * 
	 * @param row
	 *            The row to be added.
	 * @return Boolean value that describes the operation success.
	 */
	public boolean rbind(Object[] row) {

		boolean success = true;

		try {

			Object[][] newRaw = new Object[nrow() + 2][ncol()];

			for (int i = 0; i < newRaw.length; i++) {
				for (int j = 0; j < newRaw[i].length; j++) {

					if (i < rawRepresentation.length) {
						newRaw[i][j] = rawRepresentation[i][j];

					} else {
						newRaw[i][j] = row[j];
					}

				}
			}
			rawRepresentation = newRaw;

		} catch (Exception e) {

			success = false;

		}

		return success;

	}

	/**
	 * Allows to append a new column in the dataframe.
	 * 
	 * @param column
	 *            The column to be added.
	 * @param columnName
	 *            The name of the new column.
	 * 
	 * @return Boolean value that describes the operation success.
	 */
	public boolean cbind(Object[] column, String columnName) {

		boolean success = true;

		try {

			Object[] aux = new Object[column.length + 1];
			aux[0] = columnName;
			for (int i = 1; i < aux.length; i++) {

				aux[i] = column[i - 1];

			}

			column = aux;

			Object[][] newRaw = new Object[nrow() + 1][ncol() + 1];

			for (int i = 0; i < rawRepresentation.length; i++) {
				for (int j = 0; j < rawRepresentation[i].length; j++) {

					newRaw[i][j] = rawRepresentation[i][j];

				}
			}

			for (int i = 0; i < column.length; i++) {
				newRaw[i][newRaw.length] = column[i];
			}

			rawRepresentation = newRaw;

		} catch (Exception e) {
			success = false;

		}

		return success;

	}

	/**
	 * Allows to obtain the names of the dataframe columns.
	 * 
	 * @return A set of names of dataframe columns.
	 */
	public String[] colnames() {

		String[] names;

		try {

			names = new String[ncol()];
			for (int i = 0; i < names.length; i++) {
				names[i] = (String) rawRepresentation[0][i];
			}

		} catch (Exception e) {
			names = null;
		}

		return names;

	}

	/**
	 * Allows to change the name of every column in the dataset.
	 * 
	 * @param newNames
	 *            The set of new names for the columns.
	 * @return Boolean value that describes the operation success.
	 */
	public boolean colnames(String[] newNames) {

		boolean success = true;

		try {

			for (int i = 0; i < newNames.length; i++) {
				rawRepresentation[0][i] = newNames[i];
			}

		} catch (Exception e) {
			success = false;
		}

		return success;

	}

	/**
	 * Allows to obtain the name of a column from its index.
	 * 
	 * @param index
	 *            The index of the column.
	 * @return The name of a column
	 */
	public String colnames(int index) {

		String name;

		try {

			name = (String) rawRepresentation[0][index];

		} catch (Exception e) {
			name = null;
		}

		return name;

	}

	/**
	 * Allows to obtain the index of a column from its name.
	 * 
	 * @param name
	 *            The name of the column.
	 * @return The index of the column.
	 */
	public int colnames(String name) {

		int index = -1;

		try {

			boolean search = true;

			for (int i = 0; i < ncol() && search; i++) {

				if (((String) rawRepresentation[0][i]).equals(name)) {
					search = false;
					index = i;
				}

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		return index;

	}

	/**
	 * Allows to change the name of a column.
	 * 
	 * @param index
	 *            The index of the column.
	 * @param newName
	 *            The new name for the column.
	 * @return Boolean value that describes the operation success.
	 */
	public boolean colnames(int index, String newName) {

		boolean success = true;

		try {

			rawRepresentation[0][index] = newName;

		} catch (Exception e) {
			success = false;
		}

		return success;

	}

	/**
	 * Allows to change the name of a column.
	 * 
	 * @param name
	 *            The actual name of the column.
	 * @param newName
	 *            The new name of the column.
	 * @return Boolean value that describes the operation success.
	 */
	public boolean colnames(String name, String newName) {
		return colnames(colnames(name), newName);
	}

	/**
	 * Allows to determine the data type of a column.
	 * 
	 * @param name
	 *            The name of the column.
	 * @return The data type of the column.
	 */
	@SuppressWarnings("rawtypes")
	public Class typeOfColumn(String name) {
		return realizeTypeOfColumn(colnames(name));
	}

	/**
	 * Allows to determine the data type of a column.
	 * 
	 * @param index
	 *            The name of the column.
	 * @return The data type of the column.
	 */
	@SuppressWarnings("rawtypes")
	public Class typeOfColumn(int index) {
		return realizeTypeOfColumn(index);
	}

	/**
	 * Allows to determine whether a column is numeric.
	 * 
	 * @param colName
	 *            The name of the column.
	 * @return true if the column is numeric.
	 */
	public boolean isColNumeric(String colName) {
		return realizeIsColNumeric(colnames(colName));
	}

	/**
	 * Allows to determine whether a column is numeric.
	 * 
	 * @param colIndex
	 *            The column index.
	 * @return true if the column is numeric.
	 */
	public boolean isColNumeric(int colIndex) {
		return realizeIsColNumeric(colIndex);
	}

	/**
	 * Allows to obtain a column.
	 * 
	 * @param index
	 *            The index of the column.
	 * @return The column found.
	 */
	public Object[] getColumn(int index) {
		return realizeGetColumn(index);
	}

	/**
	 * Allows to obtain a column.
	 * 
	 * @param columnName
	 *            The name of the column.
	 * @return The column found.
	 */
	public Object[] getColumn(String columnName) {
		return realizeGetColumn(colnames(columnName));
	}

	/**
	 * Allows to obtain an entire row.
	 * 
	 * @param index
	 *            The index of the row.
	 * @return The row found.
	 */
	public Object[] getRow(int index) {

		Object[] row;

		try {

			row = rawRepresentation[index + 1];

		} catch (Exception e) {
			row = null;
		}

		return row;

	}

	/**
	 * Allows to obtain a set of rows that meet defined conditions.
	 * 
	 * @param conditions
	 *            The conditions for selecting the rows.
	 * @return A set of rows that meet defined conditions.
	 */
	public Integer[] getRowsWhere(CompareCondition[] conditions) {

		Integer[] rows = null;

		try {

			ArrayList<Integer> aux = new ArrayList<Integer>();

			for (int i = 1; i <= nrow(); i++) {

				Boolean correct = true;

				for (int c = 0; c < conditions.length && correct; c++) {

					CompareCondition condition = conditions[c];

					int columnIndex = colnames(condition.getColumnName());

					if (typeOfColumn(columnIndex) == Integer.class) {

						Integer obj1 = Integer.parseInt((String) rawRepresentation[i][columnIndex]);
						Integer obj2 = Integer.parseInt(condition.getExpectedValue());
						Comparator<Integer> comparator = new Comparator<Integer>(condition.getType());
						correct = comparator.compare(obj1, obj2);

					}

					if (typeOfColumn(condition.getColumnName()) == Double.class) {

						Double obj1 = Double.parseDouble((String) rawRepresentation[i][columnIndex]);
						Double obj2 = Double.parseDouble(condition.getExpectedValue());
						Comparator<Double> comparator = new Comparator<Double>(condition.getType());
						correct = comparator.compare(obj1, obj2);

					}

					if (typeOfColumn(condition.getColumnName()) == Long.class) {

						Long obj1 = Long.parseLong((String) rawRepresentation[i][columnIndex]);
						Long obj2 = Long.parseLong(condition.getExpectedValue());
						Comparator<Long> comparator = new Comparator<Long>(condition.getType());
						correct = comparator.compare(obj1, obj2);
					}

					if (typeOfColumn(condition.getColumnName()) == Float.class) {

						Float obj1 = Float.parseFloat((String) rawRepresentation[i][columnIndex]);
						Float obj2 = Float.parseFloat(condition.getExpectedValue());
						Comparator<Float> comparator = new Comparator<Float>(condition.getType());
						correct = comparator.compare(obj1, obj2);

					}

					if (typeOfColumn(condition.getColumnName()) == String.class) {

						String obj1 = (String) rawRepresentation[i][columnIndex];
						String obj2 = condition.getExpectedValue();
						Comparator<String> comparator = new Comparator<String>(condition.getType());
						correct = comparator.compare(obj1, obj2);

					}

					if (typeOfColumn(condition.getColumnName()) == Boolean.class) {

						Boolean obj1 = Boolean.parseBoolean((String) rawRepresentation[i][columnIndex]);
						Boolean obj2 = Boolean.parseBoolean(condition.getExpectedValue());
						Comparator<Boolean> comparator = new Comparator<Boolean>(condition.getType());
						correct = comparator.compare(obj1, obj2);

					}
				}

				if (correct) {
					aux.add(i - 1);
				}

			}

			if (!aux.isEmpty()) {
				rows = aux.toArray(new Integer[aux.size()]);
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		return rows;

	}
	
	/**
	 * Allows to obtain a value from an attribute of a column.
	 * 
	 * @param rowIndex The row selected.
	 * @param attributeName The column/attribute name.
	 * @return The searched value from a specific column of a row.
	 */
	public Object getAttributeValueFromRow(int rowIndex, String attributeName) {
		return rawRepresentation[rowIndex+1][colnames(attributeName)];
	}

}

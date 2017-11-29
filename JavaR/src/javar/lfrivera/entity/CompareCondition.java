package javar.lfrivera.entity;

/**
 * This class represents a comparison condition.
 * 
 * @author lfrivera
 *
 */
public class CompareCondition {

	/**
	 * The column where the comparison will occur.
	 */
	private String columnName;
	
	/**
	 * The expected value in the column.
	 */
	private String expectedValue;
	
	/**
	 * The comparison type.
	 */
	private ComparisonTypeEnum type;

	/**
	 * The constructor of the class.
	 * 
	 * @param columnName 
	 * @param expectedValue
	 * @param type
	 */
	public CompareCondition(String columnName, String expectedValue, ComparisonTypeEnum type) {
		super();
		this.columnName = columnName;
		this.expectedValue = expectedValue;
		this.type = type;
	}

	/**
	 * Allows to obtain the column name.
	 * 
	 * @return The column name.
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * Allows to obtain the expected value.
	 * 
	 * @return The expected value.
	 */
	public String getExpectedValue() {
		return expectedValue;
	}

	/**
	 * Allows to obtain the type.
	 * 
	 * @return The type.
	 */
	public ComparisonTypeEnum getType() {
		return type;
	}
	
	
	
	
	
}

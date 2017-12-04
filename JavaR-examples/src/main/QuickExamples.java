package main;

import java.io.File;

import javar.lfrivera.entity.CompareCondition;
import javar.lfrivera.entity.ComparisonTypeEnum;
import javar.lfrivera.entity.Dataframe;
import javar.lfrivera.entity.ReturnTypeEnum;
import javar.lfrivera.util.R2JavaHelper;

/**
 * This class allows to test the JavaR-related classes.
 * 
 * @author lfrivera
 *
 */
public class QuickExamples {
	
	/**
	 * Entry point for the examples.
	 * 
	 * @param args Arguments.
	 */
	public static void main(String args[])
	{
		try {
			
			System.out.println("---------------------------");
			testAsInteger();
			System.out.println("---------------------------");
			testDataframe();
			System.out.println("---------------------------");
		
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			
		}
	}
	
	/**
	 * 
	 * Allows to test the Int return type.
	 * 
	 * @throws Exception An exception is thrown when Java is unable to connect with Rserve.
	 */
	private static void testAsInteger() throws Exception
	{
		File script = new File("../JavaR-examples/scripts/Palindrome.R");
		String[] params = {"ojo"};
		String function = "palindrome";
		int result =  (int) R2JavaHelper.getInstance().callScriptFunction(script, ReturnTypeEnum.INTEGER,function,params);
		System.out.println("Int: " + result);
	}
	
	/**
	 * Allows to test the dataframe transformation.
	 * 
	 * @throws Exception An exception is thrown when Java is unable to connect with Rserve.
	 */
	private static void testDataframe() throws Exception
	{
		File script = new File("../JavaR-examples/scripts/DaframeGeneration.R");
		String function = "framing";
		String[] params = null;
		Dataframe dataframe = (Dataframe) R2JavaHelper.getInstance().callScriptFunction(script, ReturnTypeEnum.DATAFRAME,function,params);
		
		System.out.println("print:");
		dataframe.print();
		
		System.out.println("head:");
		dataframe.head(2);
		
		System.out.println("nrow:");
		System.out.println(dataframe.nrow());
		
		System.out.println("ncol:");
		System.out.println(dataframe.ncol());
		
		System.out.println("rbind:");
		//New row
		Object[] row = {"4","d","TRUE","4.","W"};
		dataframe.rbind(row);
		dataframe.print();
		
		System.out.println("cbind:");
		//New column
		Object[] col = {"h","i","j","k"};
		dataframe.cbind(col,"F");
		dataframe.print();
		
		System.out.println("colnames():");
		printArray(dataframe.colnames());
		
		System.out.println("colnames(String[]):");
		String[] names = {"G","H","I","J","K","L"};
		dataframe.colnames(names);
		dataframe.head(1);
		
		System.out.println("colnames(int):");
		System.out.println(dataframe.colnames(1));
		
		System.out.println("colnames(String):");
		System.out.println(dataframe.colnames("K"));
		
		System.out.println("colnames(int,String):");
		dataframe.colnames(1,"Q");
		dataframe.head(1);
		
		System.out.println("colnames(String,String):");
		dataframe.colnames("J","R");
		dataframe.head(1);
		
		System.out.println("getColumn(int):");
		printArray(dataframe.getColumn(1));
		
		System.out.println("getColumn(String):");
		printArray(dataframe.getColumn("R"));
		
		System.out.println("getRow(int):");
		dataframe.print();
		printArray(dataframe.getRow(0));
		
		System.out.println("typeOfColumn:");
		System.out.println("---");
		dataframe.print();
		System.out.println("---");
		System.out.println(dataframe.typeOfColumn(1));
		
		System.out.println("getRowsWhere:");
		System.out.println("---");
		dataframe.print();
		System.out.println("---");
		CompareCondition[] conditions = {
				//new CompareCondition("Q", "b", ComparisonTypeEnum.NOT_EQUAL_TO),
				new CompareCondition("L", "k", ComparisonTypeEnum.NOT_EQUAL_TO),
				//new CompareCondition("G", "2", ComparisonTypeEnum.GREATER_THAN_EQUAL_TO)
				//new CompareCondition("I", "FALSE", ComparisonTypeEnum.EQUAL_TO)
				};
		printArray(dataframe.getRowsWhere(conditions));
		
		System.out.println("getAttributeValueFromRow:");
		System.out.println(dataframe.getAttributeValueFromRow(0, "R"));
	}
	
	/**
	 * Allows to print an array.
	 * @param array The array to print.
	 */
	private static void printArray(Object[] array) {
		for(Object o:array) {
			System.out.println(o.toString());
		}
	}
	
}

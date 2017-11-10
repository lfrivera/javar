package main;

import java.io.File;

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
			
			testAsInteger();
			System.out.println("---------------------------");
			testDataframe();
		
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
		int result =  (int) R2JavaHelper.getInstance().callScriptFunction(script, ReturnType.INTEGER,function,params);
		System.out.println("Int: " + result);
	}
	
	/**
	 * Allows to test the dataframe transformation.
	 * 
	 * @throws Exception An exception is thrown when Java is unable to connect with Rserve.
	 */
	private static void testDataframe() throws Exception
	{
		File script = new File("/home/amelia/Repositories/Git/javar/JavaR-examples/scripts/DaframeGeneration.R");
		String function = "framing";
		String[] params = null;
		Object[][] dataframe = (Object[][]) R2JavaHelper.getInstance().callScriptFunction(script, ReturnType.DATAFRAME,function,params);
		printDataframe(dataframe);
	}
	
	/**
	 * Allows to print a dataframe on console.
	 * 
	 * @param dataframe The dataframe.
	 */
	private static void printDataframe(Object[][] dataframe)
	{
		for (int i = 0; i < dataframe.length; i++) {
		    for (int j = 0; j < dataframe[i].length; j++) {
		        System.out.print(dataframe[i][j] + " ");
		    }
		    System.out.println();
		}
	}
	
}

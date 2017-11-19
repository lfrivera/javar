package main;

import java.io.File;

import javar.lfrivera.entity.Dataframe;
import javar.lfrivera.util.R2JavaHelper;
import javar.lfrivera.util.ReturnType;

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
		File script = new File("../JavaR-examples/scripts/DaframeGeneration.R");
		String function = "framing";
		String[] params = null;
		Dataframe dataframe = (Dataframe) R2JavaHelper.getInstance().callScriptFunction(script, ReturnType.DATAFRAME,function,params);
		System.out.println("Full dataframe:");
		dataframe.print();
		System.out.println("Head:");
		dataframe.head(2);
	}
	
}

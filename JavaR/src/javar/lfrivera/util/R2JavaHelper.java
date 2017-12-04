package javar.lfrivera.util;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import javar.lfrivera.entity.Dataframe;
import javar.lfrivera.entity.PrintHeaderEnum;
import javar.lfrivera.entity.ReturnTypeEnum;

/**
 * This Singleton-based class allows Java to interact with R.
 * 
 * @author lfrivera
 *
 */
public class R2JavaHelper {

	/**
	 * Unique instance of the class.
	 */
	private static R2JavaHelper instance;

	/**
	 * Connection to the RServe daemon.
	 */
	private RConnection rconnect;

	/**
	 * The console printer.
	 */
	private ConsolePrinter printer;

	/**
	 * Private constructor of the class.
	 * 
	 * @throws Exception
	 *             An exception is thrown when Java is unable to connect to
	 *             RServe.
	 */
	private R2JavaHelper() throws Exception {

		printer = new ConsolePrinter(PrintHeaderEnum.R2JAVA_HELPER);

		RServeStarter.getInstance().startRserve();

		try {
			TimeUnit.SECONDS.sleep(1);
			rconnect = new RConnection();

		} catch (RserveException e) {

			throw new Exception(e.getMessage());

		}

	}

	/**
	 * Allows to obtain the unique instance of the class.
	 * 
	 * @return Unique instance of the class.
	 * @throws Exception
	 *             An exception is thrown when Java is unable to connect to
	 *             RServe.
	 */
	public static R2JavaHelper getInstance() throws Exception {

		if (instance == null) {
			instance = new R2JavaHelper();
		}

		return instance;

	}

	/**
	 * Allows to transform the script path to a specific format for Rserve.
	 * 
	 * @param path
	 *            The path to transform.
	 * @return The transformed path (depending on OS).
	 */
	public String transformScriptPath(String path) {

		if(OsDetector.getInstance().getCurrentOS().equals(OsDetector.WIN_OS)) {
			
			char[] array = path.toCharArray();
			
			StringBuilder builder = new StringBuilder("");
			
			for(char c:array){
				
				if(c == 47 || c == 92) {
					builder.append(File.separator);
					builder.append(File.separator);
				}
				else
				{
					builder.append(c);
				}
				
			}
			
			path = builder.toString();
			
		}
		
		return path;
	}

	/**
	 * Allows to generate the definition of the function to be called.
	 * 
	 * @param functionName
	 *            The name of the function.
	 * @param parameters
	 *            The parameters of the function.
	 * @return The definition of the function.
	 */
	private String constructFunctionDefinition(String functionName, String[] parameters) {

		StringBuilder definition = new StringBuilder();

		definition.append(functionName);

		definition.append("(");

		boolean first = true;

		if (parameters != null) {

			for (String param : parameters) {

				String realParam = "'" + param + "'";

				if (first) {

					definition.append(realParam);

					first = false;
				} else {

					definition.append("," + realParam);

				}

			}

		}

		definition.append(")");

		return definition.toString();

	}

	/**
	 * Allows to evaluate an R script.
	 * 
	 * @param scriptPath
	 *            The path of the script to evaluate.
	 * @param functionName
	 *            The name of the function.
	 * @param parameters
	 *            The parameters of the function.
	 * @return The result of the script evaluation.
	 * @throws Exception
	 *             An exception is thrown when Java is unable to connect to
	 *             RServe.
	 * 
	 */
	private REXP evalScriptFunction(String scriptPath, String functionName, String[] parameters) throws Exception {

		try {

			scriptPath = transformScriptPath(scriptPath);

			printer.print("Executing " + scriptPath + "script...");

			String source = "source(\"" + scriptPath + "\")";
			
			printer.print("Performing eval: " + source + "...");
			
			rconnect.eval(source);
			
			printer.print("Eval executed successfully.");

			String function = constructFunctionDefinition(functionName, parameters);
			
			printer.print("Performing eval: " + function + "...");
			
			REXP response = rconnect.eval(function);
			
			printer.print("Eval executed successfully.");

			printer.print("Script executed successfully.");

			return response;

		} catch (RserveException e) {

			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Allows to transform a R dataframe into a Object Matrix.
	 * 
	 * @param list
	 *            The list to be transformed.
	 * @return The dataframe in its Java representation.
	 * @throws REXPMismatchException
	 *             REXP exception.
	 */
	private Dataframe transformDataframe(RList list) throws REXPMismatchException {

		int rows = list.at(0).length();
		int cols = list.capacity();

		Object[][] dataframe = new Object[rows + 1][cols];

		Vector<?> names = list.names;

		for (int i = 0; i < names.size(); i++) {
			dataframe[0][i] = names.get(i);
		}

		for (int c = 0; c < cols; c++) {
			String[] column = list.at(c).asStrings();

			for (int r = 0; r < rows; r++) {

				dataframe[r + 1][c] = column[r];

			}

		}

		return new Dataframe(dataframe);

	}

	/**
	 * Allows to call a R script and obtain a response.
	 * 
	 * @param scriptFile
	 *            The script file to be executed.
	 * @param rType
	 *            The expected return type.
	 * @param scriptPath
	 *            The path of the script to evaluate.
	 * @param functionName
	 *            The name of the function.
	 * @param parameters
	 *            The parameters of the function.
	 * @return The result from calling the script.
	 * @throws Exception
	 *             An exception is thrown when Java is unable to connect to
	 *             RServe or when a casting is not possible.
	 * 
	 */
	public Object callScriptFunction(File scriptFile, ReturnTypeEnum rType, String functionName, String[] parameters)
			throws Exception {

		String absolutePath = scriptFile.getAbsolutePath();

		REXP result = evalScriptFunction(absolutePath, functionName, parameters);

		Object response;

		switch (rType) {

		case BYTES:
			response = result.asBytes();
			break;

		case DOUBLE:
			response = result.asDouble();
			break;

		case DOUBLE_MATRIX:
			response = result.asDoubleMatrix();
			break;

		case DOUBLE_ARRAY:
			response = result.asDoubles();
			break;

		case FACTOR:
			response = result.asFactor().asStrings();
			break;

		case INTEGER:
			response = result.asInteger();
			break;

		case INTEGER_ARRAY:
			response = result.asIntegers();
			break;

		case RLIST:
			response = result.asList();
			break;

		case NATIVE_JAVA_OBJECT:
			response = result.asNativeJavaObject();
			break;

		case STRING:
			response = result.asString();
			break;

		case STRING_ARRAY:
			response = result.asStrings();
			break;

		case DATAFRAME:
			response = transformDataframe(result.asList());
			break;

		default:
			response = null;

		}

		return response;

	}

	/**
	 * Allows to stop the Rserve server,
	 * 
	 * @throws Exception
	 *             An exception is thrown when Java is unable to connect to
	 *             RServe.
	 * 
	 */
	public void stopServer() throws Exception {

		try {

			rconnect.serverShutdown();

		} catch (RserveException e) {

			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Allows to kill the process associated with Rserve (linux).
	 * 
	 * @throws IOException
	 *             A throwable exception.
	 * @throws InterruptedException
	 *             A throwable exception.
	 */
	public void killServer() throws IOException, InterruptedException {

		Process p = Runtime.getRuntime().exec("killall Rserve");

		p.waitFor();

	}

}

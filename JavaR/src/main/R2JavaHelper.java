package main;

import java.io.File;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

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
	 * Private constructor of the class.
	 * 
	 * @throws RserveException
	 *             An exception is thrown when Java is unable to connect to
	 *             RServe.
	 */
	private R2JavaHelper() throws RserveException {

		rconnect = new RConnection();

	}

	/**
	 * Allows to obtain the unique instance of the class.
	 * 
	 * @return Unique instance of the class.
	 * @throws RserveException
	 *             An exception is thrown when Java is unable to connect to
	 *             RServe.
	 */
	public static R2JavaHelper getInstance() throws RserveException {

		if (instance == null) {
			instance = new R2JavaHelper();
		}

		return instance;

	}

	/**
	 * Allows to evaluate an R script.
	 * 
	 * @param scriptPath
	 *            The path of the script to evaluate.
	 * @return The result of the script evaluation.
	 * @throws RserveException
	 *             An exception is thrown when Java is unable to connect to
	 *             RServe.
	 */
	private REXP evalScript(String scriptPath) throws RserveException {

		return rconnect.eval(scriptPath);

	}
	
	public 
	

	/**
	 * Allows to stop the Rserve server,
	 * 
	 * @throws RserveException
	 *             An exception is thrown when Java is unable to connect to
	 *             RServe.
	 */
	public void stopServer() throws RserveException {

		rconnect.serverShutdown();

	}

}

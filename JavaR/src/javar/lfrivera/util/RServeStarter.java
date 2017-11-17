package javar.lfrivera.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This Singleton-based class allows to start Rserve.
 * 
 * @author lfrivera
 *
 */
class RServeStarter {

	/**
	 * The name of the component on the console.
	 */
	private final String COMPONENT_NAME_ON_CONSOLE = ">RServeStarter:";
	
	/**
	 * Unique instance of the class.
	 */
	private static RServeStarter instance;

	// OS constants.
	private final String WIN_OS = "Windows";
	private final String LIN_OS = "Linux";
	private final String MAC_OS = "Mac";

	/**
	 * The current operative system.
	 */
	private String currentOS;

	/**
	 * Private constructor of the class.
	 */
	private RServeStarter() {

		detectOS();

	}

	/**
	 * Allows to obtain the unique instance of the class.
	 * 
	 * @return Unique instance of the class.
	 */
	protected static RServeStarter getInstance() {

		if (instance == null) {
			instance = new RServeStarter();
		}

		return instance;
	}

	/**
	 * Allows to detect the current operative system of the user.
	 */
	private void detectOS() {
		// Based on:
		// https://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/

		String os = System.getProperty("os.name").toLowerCase();

		if (os.indexOf("win") >= 0) {
			currentOS = WIN_OS;
		}

		if (os.indexOf("mac") >= 0) {
			currentOS = MAC_OS;
		}

		if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0) {
			currentOS = LIN_OS;
		}
	}

	/**
	 * Allows to determine whether the Rserver daemon is running.
	 * 
	 * @return Running status.
	 * @throws InterruptedException
	 *             An exception is thrown when a error occurs in the console.
	 * @throws IOException
	 *             An exception is thrown when there is an error with the reading
	 *             process from console..
	 */
	private boolean isRunningServer() throws InterruptedException, IOException {

		boolean response;

		switch (currentOS) {

		case WIN_OS:

			response = isRunningServerWindows();

			break;

		case LIN_OS:

			response = isRunningServerLinux();

			break;

		case MAC_OS:

			response = isRunningServerMac();

			break;

		default:

			response = false;

			break;
		}

		return response;

	}

	private boolean isRunningServerWindows() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Allows to detect if Rserve is already running on linux.
	 * 
	 * @return Running status.
	 * @throws InterruptedException
	 *             An exception is thrown when a error occurs in the console.
	 * @throws IOException
	 *             An exception is thrown when there is an error with the reading
	 *             process from console.
	 */
	private boolean isRunningServerLinux() throws InterruptedException, IOException {

		// Based on:
		// https://stackoverflow.com/questions/44246794/how-to-start-rserve-automatically-from-java-in-windows

		// check the runtime environment to see if there's an active Rserve running
		String existingRserve = "";
		Process p = Runtime.getRuntime().exec("pidof Rserve");
		p.waitFor();
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		existingRserve = in.readLine();
		
		return existingRserve != null;
		
	}

	private boolean isRunningServerMac() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Allows to start Rserve on windows systems.
	 * 
	 * @throws Exception
	 *             An exception is thrown when Rserve could not be started.
	 */
	private void startOnWindows() throws Exception {

		// This code is based on:
		// https://stackoverflow.com/questions/44246794/how-to-start-rserve-automatically-from-java-in-windows

		// Process p = Runtime.getRuntime().exec("Rscript -e \"library(Rserve);
		// Rserve()\"");
		// p.waitFor();

	}

	/**
	 * Allows to start Rserve on linux systems.
	 * 
	 * @throws Exception
	 *             An exception is thrown when Rserve could not be started.
	 */
	private void startOnLinux() throws Exception {

		// This code is based on:
		// https://stackoverflow.com/questions/32373372/how-to-start-rserve-automatically-from-java

		// run the Unix ""R CMD RServe --vanilla"" command
		// using the Runtime exec method:
		
		System.out.println(COMPONENT_NAME_ON_CONSOLE + " Starting Rserve on linux...");
		
		Process p = Runtime.getRuntime().exec("R CMD Rserve --vanilla");
		p.waitFor();
		
		System.out.println(COMPONENT_NAME_ON_CONSOLE + " Rserve started.");

	}

	/**
	 * Allows to start Rserve on MAc systems.
	 * 
	 * @throws Exception
	 *             An exception is thrown when Rserve could not be started.
	 */
	private void startOnMac() throws Exception {
		throw new UnsupportedOperationException();
	}

	/**
	 * Allows to start the Rserve daemon.
	 * 
	 * @throws Exception
	 *             An exception is thrown when Rserve could not be started.
	 */
	public void startRserve() throws Exception {

		if(!isRunningServer()) {
			
			switch (currentOS) {

			case WIN_OS:
				startOnWindows();
				break;

			case LIN_OS:
				startOnLinux();
				break;

			case MAC_OS:
				startOnMac();
				break;

			}
			
		}

	}

}

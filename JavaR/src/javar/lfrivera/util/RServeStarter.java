package javar.lfrivera.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.rosuda.REngine.Rserve.RConnection;

/**
 * This Singleton-based class allows to start Rserve.
 * 
 * @author lfrivera
 *
 */
class RServeStarter {

	/**
	 * Unique instance of the class.
	 */
	private static RServeStarter instance;

	/**
	 * The class that allows to print a message on console.
	 */
	private static ConsolePrinter printer;

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
		printer = new ConsolePrinter(PrintHeaderEnum.RSERVE_STARTER);
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
	 *             An exception is thrown when there is an error with the
	 *             reading process from console..
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

		printer.print("Rserve already started on " + currentOS + ": " + response);

		return response;

	}

	private boolean isRunningServerWindows() {

		boolean response;

		try {

			RConnection c = new RConnection();
			c.close();

			response = true;

		} catch (Exception e) {

			response = false;

		}

		return response;

	}

	/**
	 * Allows to detect if Rserve is already running on linux.
	 * 
	 * @return Running status.
	 * @throws InterruptedException
	 *             An exception is thrown when a error occurs in the console.
	 * @throws IOException
	 *             An exception is thrown when there is an error with the
	 *             reading process from console.
	 */
	private boolean isRunningServerLinux() throws InterruptedException, IOException {

		// Based on:
		// https://stackoverflow.com/questions/44246794/how-to-start-rserve-automatically-from-java-in-windows

		// check the runtime environment to see if there's an active Rserve
		// running
		String existingRserve = "";
		Process p = Runtime.getRuntime().exec("pidof Rserve");
		p.waitFor();
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		existingRserve = in.readLine();

		return existingRserve != null;

	}

	/**
	 * Allows to detect if Rserve is already running on Mac.
	 * 
	 * @return Running status.
	 * @throws InterruptedException
	 *             An exception is thrown when a error occurs in the console.
	 * @throws IOException
	 *             An exception is thrown when there is an error with the
	 *             reading process from console.
	 */
	private boolean isRunningServerMac() throws InterruptedException, IOException {

		return isRunningServerLinux();

	}

	/**
	 * Allows to start Rserve on windows systems.
	 * 
	 * @throws Exception
	 *             An exception is thrown when Rserve could not be started.
	 */
	private void startOnWindows() throws Exception {

		// This code is based on:
		// https://github.com/s-u/REngine/blob/master/Rserve/test/StartRserve.java

		String installPath = null;

		Process rp = Runtime.getRuntime().exec("reg query HKLM\\Software\\R-core\\R");

		StreamHog regHog = new StreamHog(rp.getInputStream(), true);

		rp.waitFor();

		regHog.join();

		installPath = regHog.getInstallPath();

		if (installPath == null) {

			printer.print(
					"ERROR: can not find path to R. Make sure reg is available and R was installed with registry settings.");

		} else {

			String rServePath = installPath + "\\bin\\R.exe";
			
			@SuppressWarnings("unused")
			Process p = Runtime.getRuntime().exec("\""+rServePath+"\" -e \"library(Rserve);Rserve(FALSE"+",args='"+"--no-save --slave"+"')\" "+"--no-save --slave");
		}

	}

	/**
	 * Allows to start Rserve on linux systems.
	 * 
	 * @throws Exception
	 *             An exception is thrown when Rserve could not be started.
	 */
	private void startOnLinux() throws Exception {

		// This code is based on:
		// https://github.com/s-u/REngine/blob/master/Rserve/test/StartRserve.java.

		Process p = Runtime.getRuntime()
				.exec(new String[] { "/bin/sh", "-c", "echo 'library(Rserve);Rserve(FALSE" + ",args=\""
						+ "--no-save --slave" + "\")'|" + getRinstallationPathUnix() + " " + "--no-save --slave" });

		printer.print("Starting Rserve on " + currentOS + "...");

		// we need to fetch the output - some platforms will die if you don't

		@SuppressWarnings("unused")
		StreamHog errorHog = new StreamHog(p.getErrorStream(), false);

		@SuppressWarnings("unused")
		StreamHog outputHog = new StreamHog(p.getInputStream(), false);

		p.waitFor();

		printer.print("Rserve started.");
	}

	/**
	 * Allows to start Rserve on Mac systems.
	 * 
	 * @throws Exception
	 *             An exception is thrown when Rserve could not be started.
	 */
	private void startOnMac() throws Exception {

		startOnLinux();
	}

	/**
	 * Allows to obtain the R installation path.
	 * 
	 * @return The R installation path.
	 */
	private String getRinstallationPathUnix() {
		String path = null;

		// Based on:
		// https://github.com/s-u/REngine/blob/master/Rserve/test/StartRserve.java.

		String possiblePaths[] = { "/Library/Frameworks/R.framework/Resources/bin/R", "/usr/local/lib/R/bin/R",
				"/usr/lib/R/bin/R", "/usr/local/bin/R", "/sw/bin/R", "/usr/common/bin/R", "/opt/bin/R" };

		boolean exists = false;

		for (int i = 0; i < possiblePaths.length && exists == false; i++) {

			String actual = possiblePaths[i];

			File file = new File(actual);

			exists = file.exists();

			if (exists) {

				path = actual;

			}

		}

		return path;
	}

	/**
	 * Allows to start the Rserve daemon.
	 * 
	 * @throws Exception
	 *             An exception is thrown when Rserve could not be started.
	 */
	public void startRserve() throws Exception {

		if (!isRunningServer()) {

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

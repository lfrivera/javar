package main;

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
	 * Allows to start Rserve on windows systems.
	 */
	private void startOnWindows() {
		throw new UnsupportedOperationException();
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
		Process p = Runtime.getRuntime().exec("R CMD Rserve --vanilla");

		p.waitFor();
		
	}

	/**
	 * Allows to start Rserve on MAc systems.
	 */
	private void startOnMac() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Allows to start the Rserve daemon.
	 * 
	 * @throws Exception
	 *             An exception is thrown when Rserve could not be started.
	 */
	public void startRserve() throws Exception {

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

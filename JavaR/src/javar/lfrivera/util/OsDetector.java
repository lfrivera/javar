package javar.lfrivera.util;

/**
 * This class allows to detect the current operative system. Implements the
 * Singleton pattern.
 * 
 * @author lfrivera
 *
 */
public class OsDetector {

	/**
	 * The unique instance of the class.
	 */
	private static OsDetector instance;

	/**
	 * The current operative system.
	 */
	private String currentOS;

	// OS constants.
	public static final String WIN_OS = "Windows";
	public static final String LIN_OS = "Linux";
	public static final String MAC_OS = "Mac";

	/**
	 * Constructor of the class.
	 */
	private OsDetector() {
		detectOS();
	}

	/**
	 * Allows to obtain the unique instance of the class.
	 * 
	 * @return The unique instance of the class.
	 */
	public static OsDetector getInstance() {

		if (instance == null) {
			instance = new OsDetector();
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
	 * Allows to know the current operative system.
	 * @return The current operative system.
	 */
	public String getCurrentOS() {
		return currentOS;
	}
	
	
}

package javar.lfrivera.util;

/**
 * This class allows to print on console.
 * 
 * @author lfrivera
 *
 */
final class ConsolePrinter {

	/**
	 * The class that is printing a message on cosole.
	 */
	private PrintHeaderEnum printer;

	/**
	 * Constructor of the class.
	 * 
	 * @param printer
	 *            The class that is printing a message on cosole.
	 */
	public ConsolePrinter(PrintHeaderEnum printer) {
		this.printer = printer;
	}
	
	/**
	 * Allows to print a message on cosole.
	 * 
	 * @param message The message to print.
	 */
	public void print(String message) {
		System.out.println(printer.getHeader()+message);
	}

}

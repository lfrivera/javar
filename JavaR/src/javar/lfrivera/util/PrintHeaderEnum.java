package javar.lfrivera.util;

/**
 * This enum allows to specify the possible headers when printing from a component.
 * 
 * @author lfrivera
 *
 */
public enum PrintHeaderEnum {

	R2JAVA_HELPER(">R2JavaHelper: "),
	RSERVE_STARTER(">RServeStarter: "),
	STREAM_HOG(">StreamHog: ")
	;
	
	/**
	 * The header field.
	 */
	private String header;
	
	/**
	 * Constructor of the enum.
	 */
	PrintHeaderEnum(String header) {
		this.header = header;
	}

	public String getHeader() {
		return header;
	}
	
	
}

package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
public class InvalidChoiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6710843953481309936L;
	/**
	 * Creates a new instance of this Exception using default message
	 */
	public InvalidChoiceException() {
		super("Invalid Choice");
	}
	/**
	 * Creates a new instance of this Exception using different message
	 * @param message The message to be used
	 */
	public InvalidChoiceException(String message) {
		super(message);
	}
	
}

package com.jpr242.one;

public class InvalidChoiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6710843953481309936L;

	public InvalidChoiceException() {
		super("Invalid Choice");
	}
	
	public InvalidChoiceException(String message) {
		super(message);
	}
	
}

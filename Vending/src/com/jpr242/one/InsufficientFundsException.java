package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
public class InsufficientFundsException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8962194835363909554L;
	/**
	 * Creates a new instance of this Exception using the default message
	 */
	public InsufficientFundsException() {
		super ("Insufficient Funds");
	}
	/**
	 * Creates a new instance of this Exception using a different string
	 * @param message The message
	 */
	public InsufficientFundsException(String message) {
		super (message);
	}
	
}

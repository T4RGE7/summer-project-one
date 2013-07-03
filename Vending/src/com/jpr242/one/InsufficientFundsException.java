package com.jpr242.one;

public class InsufficientFundsException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8962194835363909554L;

	public InsufficientFundsException() {
		super ("Insufficient Funds");
	}
	
	public InsufficientFundsException(String message) {
		super (message);
	}
	
}

package com.jpr242.one;

public class InsufficientFundsException extends Exception{
	
	public InsufficientFundsException() {
		super ("Insufficient Funds");
	}
	
	public InsufficientFundsException(String message) {
		super (message);
	}
	
}

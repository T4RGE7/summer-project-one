package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.io.Serializable;
import java.util.ArrayList;

public class Container implements Serializable{
	private ArrayList<VendingMachine> machines;
	private ArrayList<String> recieptNames;
	
	public Container() {
		machines = new ArrayList<VendingMachine>();
		recieptNames = new ArrayList<String>();
	}
	
	public void addVendingMachine() {
		
	}
	
	public void addRecieptFileName(String fileName) {
		
	}
	
}

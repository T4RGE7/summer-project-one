package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.io.Serializable;
import java.util.ArrayList;

public class Container implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8957317979348744215L;
	private ArrayList<VendingMachine> machines;
	private ArrayList<String> recieptNames;
	/**
	 * Creates a new Container to hold vending machines and some reciepts
	 */
	public Container() {
		this.machines = new ArrayList<VendingMachine>();
		this.recieptNames = new ArrayList<String>();
	}
	/**
	 * Adds one more vending machine, max 6
	 * @param id the Vending Machine's unique sequential id number
	 * @param runNumber the current run number
	 */
	public void addVendingMachine(int id, int runNumber) {
		
		if (this.machines.size() < 6) {
			this.machines.add(new VendingMachine(id, runNumber));
		}
		
	}
	/**
	 * Adds one more reciepr file name
	 * @param fileName name of the reciept file added includes .txt
	 */
	public void addRecieptFileName(String fileName) {
		
		this.recieptNames.add(fileName);
		
	}
	/**
	 * Returns all of the vending machines contained
	 * @return ArrayList of all Vending Machines
	 */
	public ArrayList<VendingMachine> getMachines() {
		return machines;
	}
	/**
	 * Sets all of the vending machines contained (Shouldn't be used)
	 * @param machines ArrayList of all Vending Machines
	 */
	public void setMachines(ArrayList<VendingMachine> machines) {
		this.machines = machines;
	}
	/**
	 * Returns all of the reciept names contained
	 * @return ArrayList of all Reciept names
	 */
	public ArrayList<String> getRecieptNames() {
		return recieptNames;
	}
	/**
	 * Sets all of the reciept names contained
	 * @param recieptNames ArrayList of all Reciept names
	 */
	public void setRecieptNames(ArrayList<String> recieptNames) {
		this.recieptNames = recieptNames;
	}
	
	
	
}

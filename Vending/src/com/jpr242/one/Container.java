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
	
	public Container() {
		this.machines = new ArrayList<VendingMachine>();
		this.recieptNames = new ArrayList<String>();
	}
	
	public void addVendingMachine(int id) {
		
		if (this.machines.size() < 6) {
			this.machines.add(new VendingMachine(id));
		}
		
	}
	
	public void addRecieptFileName(String fileName) {
		
		this.recieptNames.add(fileName);
		
	}

	public ArrayList<VendingMachine> getMachines() {
		return machines;
	}

	public void setMachines(ArrayList<VendingMachine> machines) {
		this.machines = machines;
	}

	public ArrayList<String> getRecieptNames() {
		return recieptNames;
	}

	public void setRecieptNames(ArrayList<String> recieptNames) {
		this.recieptNames = recieptNames;
	}
	
	
	
}

package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
public class MachineButton extends Button{

	private int id;
	private VendingMachine machine;
	/**
	 * Creates a new Machine Button
	 * @param id unique button id
	 * @param machine the vending machine this button is attached to
	 */
	public MachineButton(int id, VendingMachine machine) {
		this.id = id;
		this.machine = machine;
	}
	/**
	 * Returns the unique id of this button
	 * @return int id
	 * 
	 */
	public int getId() {
		return id;
	}
	/**
	 * Sets the unique id of this button
	 * @return id the button's new id
	 * 
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Returns the vending machine attached to this button
	 * @return VendingMachine object
	 * 
	 */
	public VendingMachine getMachine() {
		return machine;
	}
	/**
	 * Sets the vending machine attached to this button
	 * @param VendingMachine object
	 * 
	 */
	public void setMachine(VendingMachine machine) {
		this.machine = machine;
	}
	
	
}

package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
public class DispenserButton extends Button{

	private int id;
	private Dispenser disp;
	/**
	 * Creates a new DispenserButton object
	 * @param id the unique integer id of the button
	 * @param machine the dispenser which the button is linked to
	 */
	public DispenserButton(int id, Dispenser machine) {
		this.id = id;
		this.disp = machine;
	}
	/**
	 * Returns the id of this button
	 * @return int id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Sets the id of this button
	 * @param id int id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Returns the Dispenser attached to this button
	 * @return Dispenser
	 */
	public Dispenser getDispenser() {
		return disp;
	}
	/**
	 * Sets the Dispenser attached to this button
	 * @param disp Dispenser to be linked
	 */
	public void setMachine(Dispenser disp) {
		this.disp = disp;
	}
	
	
}

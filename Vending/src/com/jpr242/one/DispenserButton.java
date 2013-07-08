package com.jpr242.one;

public class DispenserButton extends Button{

	private int id;
	private Dispenser disp;
	
	public DispenserButton(int id, Dispenser machine) {
		this.id = id;
		this.disp = machine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Dispenser getDispenser() {
		return disp;
	}

	public void setMachine(Dispenser disp) {
		this.disp = disp;
	}
	
	
}

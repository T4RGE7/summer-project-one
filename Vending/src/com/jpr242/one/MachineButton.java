package com.jpr242.one;

public class MachineButton extends Button{

	private int id;
	private VendingMachine machine;
	
	public MachineButton(int id, VendingMachine machine) {
		this.id = id;
		this.machine = machine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public VendingMachine getMachine() {
		return machine;
	}

	public void setMachine(VendingMachine machine) {
		this.machine = machine;
	}
	
	
}

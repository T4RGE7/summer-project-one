package com.jpr242.one;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

public class Customer {

	private Container lobby;
	private int id, runNum;
	private double money;
	private VendingMachine currentMachine;
	private Dispenser dispenser;
	private FoodInfo item;
	private Random rand;
	
	public Customer(int id, int runNum, Container lobby) {		
		this.rand = new Random();
		this.id = id;
		this.runNum = runNum;
		this.money = ((double)rand.nextInt(10) + 1) + rand.nextInt(100)/100.0;
		//reloadContainer();
		this.lobby = lobby;
		changeMachine();
	}
	
	public void reloadContainer() {
		ObjectInputStream oIS;
		try {
			oIS = new ObjectInputStream(new FileInputStream("run" + this.runNum + "/container.dat"));
			this.lobby = (Container) oIS.readObject();
			oIS.close();
		} catch (IOException e) {
			System.err.println("Customer Unable to Find Lobby");
		} catch (ClassNotFoundException e) {
			System.err.println("Container Not Up to Date, Run the Setup Again");
		}
	}
	
	
	public String runCommand() {
		int commandNumber = rand.nextInt(100);
		try {
			if (commandNumber < 10) {
				this.currentMachine.powerToggle();
				System.out.println("Machine turned " + (this.currentMachine.isOn() ? "on" : "off"));
			} else if (commandNumber < 30) {
				insertFunds();
				System.out.println("More money inserted");
			} else if (commandNumber < 40) {
				changeMachine();
				System.out.println("Machine Changed");
			} else if (commandNumber < 50) {
				changeFoodItem();
				System.out.println("Food Item Selection Changed");
			} else if (commandNumber < 60) {
				this.currentMachine.returnMoney();
				System.out.println("Leaving Early");
				this.currentMachine.setInUse(false);
				return "left early";
			} else if (commandNumber < 90) {
				if (purchase()) {
					this.currentMachine.setInUse(false);
					return "success";
				}
			} else {
				//do nothing
			}
		
		} catch (NullPointerException e) {
			changeMachine();
		}
		
		
		return "continue";
	}
	
	private void changeMachine() {
		boolean checkAll = false;
		
		outer:for (int i = 0 ; i < this.lobby.getMachines().size(); i++) {
			if (this.lobby.getMachines().get(i).isOn()) {
				checkAll = true;
				break outer;
			}
		}

		
		if (this.currentMachine != null) {
			this.currentMachine.setInUse(false);
			this.money += this.currentMachine.returnMoney();
		}
		this.currentMachine = null;
		if (!checkAll) {
			this.currentMachine = this.lobby.getMachines().get(this.rand.nextInt(this.lobby.getMachines().size()));;
			this.currentMachine.setOn(true);
		}
		while (this.currentMachine == null) {
			try {
				this.currentMachine = this.lobby.getMachines().get(this.rand.nextInt(this.lobby.getMachines().size()));
				if (this.currentMachine.isInUse()) {
					this.currentMachine = null;
				}
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Invalid Machine Selection");
				this.currentMachine = null;
				//return;
			}
		}
		this.currentMachine.setInUse(true);
		changeFoodItem();
		insertFunds();
	}
	
	private void changeFoodItem() {
		this.dispenser = null;
		this.item = null;
		boolean check = false;
		outer:for (int i = 0; i < this.currentMachine.getDispensers().size(); i++) {
			if (!this.currentMachine.getDispensers().get(i).getDispenserContents().isEmpty()) {
				check = true;
				break outer;
			}
		}
		
		if (!check) {
			return;
		}
		
		while (this.dispenser == null || this.item == null) {
			try{
				this.dispenser = this.currentMachine.getDispensers().get(rand.nextInt(this.currentMachine.getDispensers().size()));	
				if (this.dispenser.getDispenserContents().isEmpty()) {
					throw new IndexOutOfBoundsException();
				}
				this.item = this.dispenser.getDispenserContents().peekFirst();
				if (this.item == null || this.dispenser == null) {
					throw new IndexOutOfBoundsException();
				}
			} catch (IndexOutOfBoundsException e) {
				this.dispenser = null;
				this.item = null;
				System.err.println("Invalid Choice, Dispenser Empty");
				return;
			}
	
		}
	}
	
	private void insertFunds() {
//		if (this.money < 1) {
//			return;
//		}
		double temp = 0;
		try {
			temp = rand.nextInt((int)(this.money) - 1) + 1;
		} catch (IllegalArgumentException e) {
			temp = (int) this.money;
		}
		this.money -= temp;
		this.currentMachine.addMoney(temp);
	}
	
	private boolean purchase() {
		return this.currentMachine.purchase(this.currentMachine.getDispensers().indexOf(this.dispenser));
	}
	
}

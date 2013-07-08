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
	private FoodInformation item;
	private Random rand;
	
	public Customer(int id, int runNum) {		
		this.rand = new Random();
		this.id = id;
		this.runNum = runNum;
		this.money = ((double)rand.nextInt(10) + 1) + rand.nextInt(100)/100.0;
		reloadContainer();
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
			} else if (commandNumber < 30) {
				insertFunds();
			} else if (commandNumber < 40) {
				changeMachine();
			} else if (commandNumber < 50) {
				changeFoodItem();
			} else if (commandNumber < 60) {
				return "done";
			} else if (commandNumber < 90) {
				
			}
		
		} catch (NullPointerException e) {
			changeMachine();
		}
		
		
		return "";
	}
	
	private void changeMachine() {
		if (this.currentMachine != null) {
			this.currentMachine.setInUse(false);
			this.money += this.currentMachine.returnMoney();
		}
		this.currentMachine = null;
		while (this.currentMachine == null) {
			try {
				this.currentMachine = this.lobby.getMachines().get(this.rand.nextInt(this.lobby.getMachines().size()));
				if (this.currentMachine.isInUse()) {
					this.currentMachine = null;
				}
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Invalid Machine Selection");
				this.currentMachine = null;
			}
		}
		this.currentMachine.setInUse(true);
		changeFoodItem();
	}
	
	private void changeFoodItem() {
		this.dispenser = null;
		this.item = null;
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
			}
	
		}
	}
	
	private void insertFunds() {
		double temp = rand.nextInt((int)(this.money) - 1) + 1;
		this.money -= temp;
		this.currentMachine.addMoney(temp);
	}
	
	private void purchase() {
		this.currentMachine.purchase(this.currentMachine.getDispensers().indexOf(this.dispenser));
	}
	
}

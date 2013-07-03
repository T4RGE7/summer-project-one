package com.jpr242.one;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class VendingMachine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4190860892494128146L;
	private double moneyIn;
	private ArrayList<Dispenser> dispensers;
	private boolean on;
	private ArrayList<String> recieptNames;
	private int id;
	
	
	public VendingMachine(int id) {
		this.id = id;
		this.on = true;
		this.moneyIn = 0;
		this.dispensers = new ArrayList<Dispenser>();
		
		int numOfDispensers = new Random().nextInt(5) + 20;
		
		for (int i = 0; i < numOfDispensers; i++) {
			this.dispensers.add(new Dispenser(-1));
		}
		
		recieptNames = new ArrayList<String>();
	}
	
	public String summary() {
		String toReturn = "";
		
		int drinkCount, snackCount;
		drinkCount = snackCount = 0;

		for (Dispenser tempDispenser : this.dispensers) {
			if (tempDispenser.getType().equalsIgnoreCase("drink") && !tempDispenser.getDispenserContents().isEmpty()) {
				drinkCount++;
			} else if (tempDispenser.getType().equalsIgnoreCase("snack") && !tempDispenser.getDispenserContents().isEmpty()) {
				snackCount++;
			}
		}
		
		toReturn += drinkCount + " Drinks and " + snackCount + " Snacks Avalible";
		
		return toReturn;
	}
	
	
	
	public boolean purchase(int dispenserNumber) {
		boolean toReturn = false;
		
		
		if (!this.dispensers.get(dispenserNumber).getDispenserContents().isEmpty()){
			if (this.moneyIn - this.dispensers.get(dispenserNumber).getPrice() >= 0) {
				this.dispensers.get(dispenserNumber).getDispenserContents().pollFirst();
				toReturn = true;
				//throw Exception;
				this.moneyIn -= this.dispensers.get(dispenserNumber).getPrice();
			} else {
				System.err.println("Error Insufficient Funds");
			}
		} else {
			System.err.println("Error Durring Purchase");
		}
		
		return toReturn;
	}
	
	public String displayDispensers() {
		String toReturn = "\n";
		
		for (int i = 0; i < this.dispensers.size(); i++) {
			Dispenser tempDispenser = this.dispensers.get(i);
			if (!tempDispenser.getDispenserContents().isEmpty()) {
				FoodInformation temp = tempDispenser.getDispenserContents().peekFirst();
				String test = temp.getName();
				toReturn += (i + 1) + ") " + tempDispenser.getDispenserContents().peekFirst().getName() + ", " + tempDispenser.getType() + ", $" + tempDispenser.getPrice() + " (" + tempDispenser.getDispenserContents().size() + ")" + "\n" ;
			}
		}
		
		return toReturn;
	}
	
	public void addMoney(double insert) {
		this.moneyIn += insert;
	}


	public double getMoneyIn() {
		return moneyIn;
	}


	public void setMoneyIn(double moneyIn) {
		this.moneyIn = moneyIn;
	}


	public ArrayList<Dispenser> getDispensers() {
		return dispensers;
	}


	public void setDispensers(ArrayList<Dispenser> dispensers) {
		this.dispensers = dispensers;
	}


	public boolean isOn() {
		return on;
	}


	public void setOn(boolean on) {
		this.on = on;
	}

	public ArrayList<String> getRecieptNames() {
		return recieptNames;
	}

	public void setRecieptNames(ArrayList<String> recieptNames) {
		this.recieptNames = recieptNames;
	}
	
	
	
	
}

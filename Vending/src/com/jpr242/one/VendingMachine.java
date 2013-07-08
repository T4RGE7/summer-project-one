package com.jpr242.one;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
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
	private int runNumber;
	private boolean inUse;
	private ArrayList<Integer> quantitySold;
	private double totalSales;
	private final DecimalFormat df = new DecimalFormat("#0.00");
	
	
	public VendingMachine(int id, int runNumber) {
		this.runNumber = runNumber;
		this.id = id;
		this.on = true;
		this.moneyIn = 0;
		this.dispensers = new ArrayList<Dispenser>();
		this.inUse = false;
		this.quantitySold = new ArrayList<Integer>();
		
		int numOfDispensers = new Random().nextInt(5) + 20;
		
		for (int i = 0; i < numOfDispensers; i++) {
			this.dispensers.add(new Dispenser(-1));
			this.quantitySold.add(0);
		}
		
		
		
		
		recieptNames = new ArrayList<String>();
		this.totalSales = 0;
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
	
	public void printReciept(String info) {
		String date = new Date().toString();
		String fileName ="run" + this.runNumber + "/reciepts/machine" + this.id + "reciept" + date.replace(":", "_").replace(" ", "_") + System.currentTimeMillis() + ".txt";
		PrintWriter printer;
		try {
			printer = new PrintWriter(fileName);
			printer.println(date);
			printer.println(info);
			printer.close();
			this.recieptNames.add(fileName);
		} catch (FileNotFoundException e) {
			System.err.println("Unable to Write Reciept");
		}
	}
	
	public boolean purchase(int dispenserNumber) {
		boolean toReturn = false;
		
		if (!on) {
			return false;
		}
		
		if (!this.dispensers.get(dispenserNumber).getDispenserContents().isEmpty()){
			double price;
			if (this.moneyIn - (price = this.dispensers.get(dispenserNumber).getPrice()) >= 0) {
				FoodInfo temp = this.dispensers.get(dispenserNumber).getDispenserContents().pollFirst();
				toReturn = true;
				//throw Exception;
				//this.moneyIn -= price;
				String toPrint = "";
				//toPrint += new Date().toString() + "\n";
				toPrint += "Machine " + this.id + "\n";
				toPrint += "Dispenser " + dispenserNumber + "/n";
				toPrint += "Item Purchased\tSubtotal\n";
				toPrint += temp.getName() + "\t$" + df.format(this.dispensers.get(dispenserNumber).getPrice()) + "\n\n";
				toPrint += "Money in:\t$" + df.format(this.moneyIn) + "/n";
				toPrint += "Total:\t$" + df.format(price) + "/n";
				toPrint += "Change:\t$" + df.format(this.moneyIn - price);
				printReciept(toPrint);
				if (this.moneyIn > price) {
					System.out.println("Your change is: $" + df.format(this.moneyIn - price));
					this.quantitySold.set(dispenserNumber, this.quantitySold.get(dispenserNumber) + 1);
					this.totalSales += price;
				}
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
				FoodInfo temp = tempDispenser.getDispenserContents().peekFirst();
				String test = temp.getName();
				toReturn += (i + 1) + ") " + tempDispenser.getDispenserContents().peekFirst().getName() + ", " + tempDispenser.getType() + ", $" + tempDispenser.getPrice() + " (" + tempDispenser.getDispenserContents().size() + ")" + "\n" ;
			}
		}
		
		return toReturn;
	}
	
	public void addMoney(double insert) {
		this.moneyIn += insert;
	}
	
	public void turnOff() {
		this.on = false;
		writeContents();
		
	}
	
	public void powerToggle() {
		if (this.on) {
			//turn off
			this.on = false;
			writeContents();
		} else {
			//turn on
			this.on = true;
			
			
		}
	}

	public void writeContents() {
		PrintWriter printer;
		ObjectOutputStream oOS;
		
		try {
			printer = new PrintWriter("run" + this.runNumber + "/Machine" + this.id + "Inventory.txt");
			printer.println("Machine " + this.id);
			printer.println(new Date().toString());
			for (int i = 0; i < this.dispensers.size(); i++) {
				Dispenser temp = this.dispensers.get(i);
				printer.println(i + ")" +temp.getDispenserContents().size() + "," + temp.getDispenserContents().peekFirst().getName() + " @ $" + df.format(temp.getPrice()) + "," + temp.getDispenserContents().peekFirst().getNutritionInfo());
			}
			printer.close();
			printer = new PrintWriter("run" + this.runNumber + "/Machine" + this.id + "Sales.txt");
			printer.println("Machine " + this.id);
			printer.println(new Date().toString());
			for (int i = 0; i < this.dispensers.size(); i++) {
				Dispenser temp = this.dispensers.get(i);
				printer.println(this.quantitySold.get(i) + " " + temp.getDispenserContents().peekFirst().getName() + " sold @ $" + df.format(temp.getPrice()) + " = $" + df.format(temp.getPrice() * this.quantitySold.get(i)));
			}
			printer.println("Total Sales = " + this.totalSales);
			printer.close();
		} catch (IOException e) {
			System.err.println("Unable to Write Contents to Text File.");
		}
		try {
			oOS = new ObjectOutputStream(new FileOutputStream("run" + this.runNumber + "/Machine" + this.id + ".dat"));
			oOS.writeObject(this);
			oOS.flush();
			oOS.close();
		} catch (IOException e) {
			System.err.println("Unable to Write Object");
		}
	}
	
	public double returnMoney() {
		double temp = this.moneyIn;
		this.moneyIn = 0.0;
		return temp;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRunNumber() {
		return runNumber;
	}

	public void setRunNumber(int runNumber) {
		this.runNumber = runNumber;
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	
	
	
	
}

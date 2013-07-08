package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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
	private int startHour, endHour;
	
	/**
	 * Creates a new VendingMachine object
	 * @param id	takes the vending machine's unique id number [0-5]
	 * @param runNumber	takes the run folder number for saving purposes
	 */
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
		
		this.startHour = new Random().nextInt(6) + 2;
		this.endHour = new Random().nextInt(4) + 20;
		
		
		recieptNames = new ArrayList<String>();
		this.totalSales = 0;
	}
	/**
	 * Returns a String showing the number of snacks and drinks available from this dispenser
	 * @return A two line string
	 */
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
	/**
	 * Prints a text receipt
	 * @param info A string with multiple lines containing identifying information about the transaction it describes 
	 */
	public void printReciept(String info) {
		String date = new Date().toString();
		String fileName ="run" + this.runNumber + "/reciepts/machine" + (this.id + 1) + "reciept" + date.replace(":", "_").replace(" ", "_") + System.currentTimeMillis() + ".txt";
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
	/**
	 * Tries to purchase an item
	 * @param dispenserNumber Will try to purchase the first item from the dispenser
	 * @return true if successful, false if not
	 */
	public boolean purchase(int dispenserNumber) {
		boolean toReturn = false;
		this.isOn();
		if (!on) {
			return false;
		}
		
		try {
			//used to have an !
			if (this.dispensers.get(dispenserNumber).canBuy()){
				double price;
				if (this.moneyIn - (price = this.dispensers.get(dispenserNumber).getPrice()) >= 0) {
					FoodInfo temp = this.dispensers.get(dispenserNumber).getDispenserContents().pollFirst();
					toReturn = true;
					//throw Exception;
					//this.moneyIn -= price;
					String toPrint = "";
					//toPrint += new Date().toString() + "\n";
					toPrint += "Machine " + (this.id + 1)+ "\n";
					toPrint += "Dispenser " + (dispenserNumber + 1) + "\n";
					toPrint += "Item Purchased\tSubtotal\n";
					toPrint += temp.getName() + "\t$" + df.format(this.dispensers.get(dispenserNumber).getPrice()) + "\n\n";
					toPrint += "Money in:\t$" + df.format(this.moneyIn) + "\n";
					toPrint += "Total:\t$" + df.format(price) + "\n";
					toPrint += "Change:\t$" + df.format(this.moneyIn - price);
					printReciept(toPrint);
					if (this.moneyIn > price) {
						System.out.println("Your change is: $" + df.format(this.moneyIn - price));
					}
					this.moneyIn = 0;
					this.quantitySold.set(dispenserNumber, this.quantitySold.get(dispenserNumber) + 1);
					this.totalSales += price;
					
				} else {
					throw new InsufficientFundsException();
				}
			}
		} catch (InsufficientFundsException e) {
			System.err.println("ERROR: You have not inserted enough money");
			return false;
		} catch (InvalidChoiceException e) {
			System.err.println("ERROR: Dispenser is empty");
			return false;
		} catch (IndexOutOfBoundsException e) {
			System.err.println("ERROR: Invalid Choice(Dispenser is empty)");
			return false;
		}
		
//		if (!this.dispensers.get(dispenserNumber).getDispenserContents().isEmpty()){
//			double price;
//			if (this.moneyIn - (price = this.dispensers.get(dispenserNumber).getPrice()) >= 0) {
//				FoodInfo temp = this.dispensers.get(dispenserNumber).getDispenserContents().pollFirst();
//				toReturn = true;
//				//throw Exception;
//				//this.moneyIn -= price;
//				String toPrint = "";
//				//toPrint += new Date().toString() + "\n";
//				toPrint += "Machine " + this.id + "\n";
//				toPrint += "Dispenser " + dispenserNumber + "/n";
//				toPrint += "Item Purchased\tSubtotal\n";
//				toPrint += temp.getName() + "\t$" + df.format(this.dispensers.get(dispenserNumber).getPrice()) + "\n\n";
//				toPrint += "Money in:\t$" + df.format(this.moneyIn) + "/n";
//				toPrint += "Total:\t$" + df.format(price) + "/n";
//				toPrint += "Change:\t$" + df.format(this.moneyIn - price);
//				printReciept(toPrint);
//				if (this.moneyIn > price) {
//					System.out.println("Your change is: $" + df.format(this.moneyIn - price));
//					this.quantitySold.set(dispenserNumber, this.quantitySold.get(dispenserNumber) + 1);
//					this.totalSales += price;
//				}
//			} else {
//				System.err.println("Error Insufficient Funds");
//			}
//		} else {
//			System.err.println("Error Durring Purchase");
//		}
		
		return toReturn;
	}
	/**
	 * Check to make sure the this machine has the correct inventory
	 * @return true if the inventory is correct, false if not
	 */
	public boolean verifyInventory() {
		boolean toReturn = true;
		for (int i = 0; i < this.dispensers.size(); i++) {
			if (this.dispensers.get(i).getInitial() - this.quantitySold.get(i) != this.getDispensers().get(i).getDispenserContents().size()) {
				System.err.println("Inventory Problem");
				toReturn = false;
			}
		}
		return toReturn;
	}
	/**
	 * Returns a String showing the number of snacks or drinks avalable from this all dispensers as well as price, name etc.
	 * @return A multi-line String showing information about all dispensers
	 */
	public String displayDispensers() {
		String toReturn = "\n";
		this.isOn();
		for (int i = 0; i < this.dispensers.size(); i++) {
			Dispenser tempDispenser = this.dispensers.get(i);
			if (!tempDispenser.getDispenserContents().isEmpty()) {
				FoodInfo temp = tempDispenser.getDispenserContents().peekFirst();
				String test = temp.getName();
				toReturn += (i + 1) + ") " + tempDispenser.getDispenserContents().peekFirst().getName() + ", " + tempDispenser.getType() + ", $" + df.format(tempDispenser.getPrice()) + " (" + tempDispenser.getDispenserContents().size() + ")" + "\n" ;
			}
		}
		
		return toReturn;
	}
	/**
	 * Adds the specified amount of money into the machine
	 * @param insert the amount of money to add
	 */
	public void addMoney(double insert) {
		this.isOn();
		this.moneyIn += insert;
	}
	/**
	 * Turns the machine off, writes the state of the machine using writeContents();
	 * @see writeContents()
	 */
	public void turnOff() {
		this.on = false;
		this.isOn();
		writeContents();
		
	}
	/**
	 * Switches the power from on to off or visa-versa
	 */
	public void powerToggle() {
		this.isOn();
		if (this.on) {
			//turn off
			this.on = false;
			writeContents();
		} else {
			//turn on
			this.on = true;
			VendingMachine moreTemp = this;
			try {
				VendingMachine temp = (VendingMachine) new ObjectInputStream(new FileInputStream("run" + this.runNumber + "/Machine" + (this.getId() + 1) + ".dat")).readObject();
				moneyIn = temp.getMoneyIn();
				dispensers = temp.getDispensers();
				on = true;
				recieptNames = temp.getRecieptNames();
				id = temp.getId();
				//private int runNumber;
				//private boolean inUse;
				quantitySold = temp.getQuantitySold();
				totalSales = temp.getTotalSales();
				//private final DecimalFormat df = new DecimalFormat("#0.00");
				//private int startHour, endHour;
				
				
			} catch (IOException e) {
				System.err.println("Unable to read file, will use older backup instead");
			} catch (ClassNotFoundException e) {
				System.err.println("Class has been modified");
			} catch (NullPointerException e) {
				System.err.println("Data corrupted, aborting and resorting to backup");
				moneyIn = moreTemp.getMoneyIn();
				dispensers = moreTemp.getDispensers();
				on = true;
				recieptNames = moreTemp.getRecieptNames();
				id = moreTemp.getId();
				//private int runNumber;
				//private boolean inUse;
				quantitySold = moreTemp.getQuantitySold();
			}
			this.verifyInventory();
			
			
		}
	}
	
	/*
	 * Loads a save when starting the gui
	 */
	public void start() {
		VendingMachine moreTemp = this;
		try {
			VendingMachine temp = (VendingMachine) new ObjectInputStream(new FileInputStream("run" + this.runNumber + "/Machine" + (this.getId() + 1) + ".dat")).readObject();
			moneyIn = temp.getMoneyIn();
			dispensers = temp.getDispensers();
			on = temp.isOn();
			recieptNames = temp.getRecieptNames();
			id = temp.getId();
			//private int runNumber;
			//private boolean inUse;
			quantitySold = temp.getQuantitySold();
			totalSales = temp.getTotalSales();
			//private final DecimalFormat df = new DecimalFormat("#0.00");
			//private int startHour, endHour;
			
			
		} catch (IOException e) {
			System.err.println("Unable to read file, will use older backup instead");
		} catch (ClassNotFoundException e) {
			System.err.println("Class has been modified");
		} catch (NullPointerException e) {
			System.err.println("Data corrupted, aborting and resorting to backup");
			moneyIn = moreTemp.getMoneyIn();
			dispensers = moreTemp.getDispensers();
			on = true;
			recieptNames = moreTemp.getRecieptNames();
			id = moreTemp.getId();
			//private int runNumber;
			//private boolean inUse;
			quantitySold = moreTemp.getQuantitySold();
		}
	}
	/**
	 * Called to write the contents and sales of the machine when turned off
	 */
	public void writeContents() {
		PrintWriter printer;
		ObjectOutputStream oOS;
		
		try {
			printer = new PrintWriter("run" + this.runNumber + "/Machine" + (this.id + 1) + "Inventory.txt");
			printer.println("Machine " + this.id);
			printer.println(new Date().toString());
			for (int i = 0; i < this.dispensers.size(); i++) {
				Dispenser temp = this.dispensers.get(i);
				if (!temp.getDispenserContents().isEmpty()){
					printer.println(i + ")" +temp.getDispenserContents().size() + "," + temp.getDispenserContents().peekFirst().getName() + " @ $" + df.format(temp.getPrice()) + "," + temp.getDispenserContents().peekFirst().getNutritionInfo());
			
				} else {
					printer.println("Empty!");
				}
			}
			printer.close();
			printer = new PrintWriter("run" + this.runNumber + "/Machine" + (this.id + 1) + "Sales.txt");
			printer.println("Machine " + this.id);
			printer.println(new Date().toString());
			for (int i = 0; i < this.dispensers.size(); i++) {
				Dispenser temp = this.dispensers.get(i);
				printer.println(this.quantitySold.get(i) + " " + temp.getName() + " sold @ $" + df.format(temp.getPrice()) + " = $" + df.format(temp.getPrice() * this.quantitySold.get(i)));
			}
			printer.println("Total Sales = " + df.format(this.totalSales));
			printer.close();
		} catch (IOException e) {
			System.err.println("Unable to Write Contents to Text File.");
		}
		try {
			oOS = new ObjectOutputStream(new FileOutputStream("run" + this.runNumber + "/Machine" + (this.id + 1)+ ".dat"));
			oOS.writeObject(this);
			oOS.flush();
			oOS.close();
		} catch (IOException e) {
			System.err.println("Unable to Write Object");
		}
	}
	/**
	 * Releases all money from this machine represented by moneyIn
	 * @return Returns the amount of money being released
	 */
	public double returnMoney() {
		double temp = this.moneyIn;
		this.moneyIn = 0.0;
		return temp;
	}
	/**
	 * Returns the amount of money held by this machine
	 * @return The amount of money held by this machine
	 */
	public double getMoneyIn() {
		return moneyIn;
	}
	/**
	 * Set the amount of money held by this machine, not called typically
	 * @param moneyIn The amount of money to be held by this machine
	 */
	public void setMoneyIn(double moneyIn) {
		this.moneyIn = moneyIn;
	}

	/**
	 * Returns the ArrayList of Dispensers contained by this machine
	 * @return Dispensers as an ArrayList
	 * 
	 */
	public ArrayList<Dispenser> getDispensers() {
		return dispensers;
	}

	/**
	 * Sets the ArrayList of Dispensers contained by this machine
	 * @param Dispensers as an ArrayList
	 * 
	 */
	public void setDispensers(ArrayList<Dispenser> dispensers) {
		this.dispensers = dispensers;
	}

	/**
	 * Checks various requirements for the machine being on
	 * @return true if on, false if not
	 * 
	 */
	public boolean isOn() {
		boolean hasStock = false;
		outer:for (int i = 0; i < this.dispensers.size(); i++) {
			if (!this.dispensers.get(i).getDispenserContents().isEmpty()) {
				hasStock = true;
				break outer;
			}
		}
		if (!hasStock || !this.on) {
			on = false;
			return false;
		}
		Date temp = new Date();
		if (temp.getHours() < this.startHour || temp.getHours() > this.endHour) {
			this.on = false;
			return false;
		}
		return on;
	}

	/**
	 * Sets the on state
	 * @param on the future state
	 */
	public void setOn(boolean on) {
		this.on = on;
	}
	/**
	 * Returns the ArrayList of Reciept names contained by this machine
	 * @return Reciept names as an ArrayList
	 * 
	 */
	public ArrayList<String> getRecieptNames() {
		return recieptNames;
	}
	/**
	 * Sets the ArrayList of Reciept names contained by this machine
	 * @param Reciept names as an ArrayList
	 * 
	 */
	public void setRecieptNames(ArrayList<String> recieptNames) {
		this.recieptNames = recieptNames;
	}
	/**
	 * Returns the unique id of this machine
	 * @return Id number as an int
	 * 
	 */
	public int getId() {
		return id;
	}
	/**
	 * Sets the unique id of this machine
	 * @param id unique id number as an int
	 * 
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Returns the run number of container
	 * @return run number as an int
	 * 
	 */
	public int getRunNumber() {
		return runNumber;
	}
	/**
	 * Sets the run number of this machine (Shouldn't be used)
	 * @param run number as an int
	 * 
	 */
	public void setRunNumber(int runNumber) {
		this.runNumber = runNumber;
	}
	/**
	 * Returns if the machine is being used
	 * @return true if being used, false if not
	 * 
	 */
	public boolean isInUse() {
		return inUse;
	}
	/**
	 * Sets if the machine is being used
	 * @param inUse true if being used, false if not
	 * 
	 */
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	
	/**
	 * Gets the quantity sold
	 * @return ArrayList of Integers
	 */
	public ArrayList<Integer> getQuantitySold() {
		return this.quantitySold;
	}
	
	/**
	 * Gets the total sales for this machine
	 * @return double total sales
	 */
	public double getTotalSales() {
		return this.totalSales;
	}
}

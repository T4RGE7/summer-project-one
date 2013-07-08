package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;


public class Dispenser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8881136851858909286L;
	private LinkedBlockingDeque<FoodInfo> dispenserContents;	
	private double price;
	private String[] infoString;
	private final int numberOfChoices = 2;
	private String type, name, nutritionFacts;
	private int initial;
	/**
	 * Creates a new empty dispenser object
	 */
	public Dispenser() {
//		this = new Dispenser(new Random().nextInt(10) + 20);
	}
	/**
	 * Creates a new dispenser object with the 20 or more items in stock if initialNumber is higher
	 * @param initialNumber stock to be added at start, will not be less than 20
	 */
	public Dispenser(int initialNumber) {
		
		dispenserContents = new LinkedBlockingDeque<FoodInfo>();
		
		if (initialNumber <= 20) {
			initialNumber = 20 + new Random().nextInt(10);
		}
		this.initial = initialNumber;
		randomFood();
		
		this.price = (double) new Random().nextInt(7) + 1;
		
		for (int i = 0; i < initialNumber; i++) {
			if (this.type.equalsIgnoreCase("Snack")) {
				dispenserContents.addLast(new Snack(this.infoString, System.currentTimeMillis() + (long) 2*7*24*60*60*1000));
			} else if (this.type.equalsIgnoreCase("Drink")) {
				dispenserContents.addLast(new Drink(this.infoString, System.currentTimeMillis() + (long) 2*7*24*60*60*1000));
			}
		} this.name = dispenserContents.peekFirst().getName();
		this.nutritionFacts = dispenserContents.peekFirst().getNutritionInfo();
	}
	/**
	 * Gets a random food ready for stocking
	 */
	private void randomFood() {
		
		FoodList listOfFood = null;
		
		try {
			ObjectInputStream listIn = new ObjectInputStream(new FileInputStream(new File("foodList.dat")));
		
			listOfFood = (FoodList) listIn.readObject();
			
			listIn.close();
		
		} catch (FileNotFoundException e) {
			System.err.println("Food List Not Found.");
		} catch (IOException e) {
			System.err.println("File Corrupted.");
		} catch (ClassNotFoundException e) {
			System.err.println("Project Incomplete.");
		}
		
		//make random number
		int randomNumber = new Random().nextInt(numberOfChoices);
		
		if (listOfFood != null) {
			switch (randomNumber) {
				case 0:
					this.infoString = getRandomInfo(listOfFood.getSnacks());
					this.type = "Snack";
					break;
				case 1:
					this.infoString = getRandomInfo(listOfFood.getDrinks());
					this.type = "Drink";
					break;
				default:
					System.err.println("Check Random Number Generator Seed in Dispenser");
			}
		}
//		for (String str : infoString) {
//			System.out.println(str);
//		}
//		
	}
	/**
	 * Returns the price in .00 format
	 * @return A string withouth the $ but carring two trailing decimals
	 */
	public String getPriceForPrinting() {
		return new DecimalFormat("#0.00").format(this.price);
	}
	/**
	 * Selects a random information blob
	 * @param correctFoodType a list of posibilities
	 * @return A string for generating the stock
	 */
	private String[] getRandomInfo(ArrayList<String[]> correctFoodType) {
		return correctFoodType.get(new Random().nextInt(correctFoodType.size()));
	}
	/**
	 * Returns the Stock
	 * @return The dispenser contents
	 */
	public LinkedBlockingDeque<FoodInfo> getDispenserContents() {
		return dispenserContents;
	}
	/**
	 * Sets the Stock
	 * @param dispenserContents Sets the dispenser contents
	 */
	public void setDispenserContents(
			LinkedBlockingDeque<FoodInfo> dispenserContents) {
		this.dispenserContents = dispenserContents;
	}
	/**
	 * Returns the price of this dispenser
	 * @return double The dispenser's price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * Sets the price of this dispenser
	 * @param price The dispenser's price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * Returns the info seed of this dispenser
	 * @return String[] the dispenser's information
	 */
	public String[] getInfoString() {
		return infoString;
	}
	/**
	 * Sets the info seed of this dispenser (too late to do anything)
	 * @param infoString the dispenser's information
	 */
	public void setInfoString(String[] infoString) {
		this.infoString = infoString;
	}
	/**
	 * Returns the type of this dispenser
	 * @return String the dispenser's type
	 */
	public String getType() {
		return type;
	}
	/**
	 * Sets the type of this dispenser
	 * @param type String the dispenser's type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Returns the number of type choices
	 * @return int probably 2 ;)
	 */
	public int getNumberOfChoices() {
		return numberOfChoices;
	}
	/**
	 * Calculates whether a despenser can be bought from
	 * @throws InvalidChoiceException when an item cannot be purchased
	 * @return true if an item can be purchased
	 */
	public boolean canBuy() throws InvalidChoiceException{
		if (!this.dispenserContents.isEmpty()) {
			return true;
		} else {
			throw new InvalidChoiceException();
		}
	}
	/**
	 * Returns the name of items stocked
	 * @return String stock's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of items stocked
	 * @param name String stock's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns the nutrition fact of items stocked
	 * @return String nutrition facts
	 */
	public String getNutritionFacts() {
		return nutritionFacts;
	}
	/**
	 * Sets the nutrition fact of items stocked (String only)
	 * @param nutritionFacts String nutrition facts
	 */
	public void setNutritionFacts(String nutritionFacts) {
		this.nutritionFacts = nutritionFacts;
	}
	/**
	 * Returns the initial number of items stocked at creation
	 * @return int initial number
	 */
	public int getInitial() {
		return initial;
	}

	/**
	 * Don't change
	 */
	private void setInitial(int initial) {
		this.initial = initial;
	}
	
	
}

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
	private String type;
	
	public Dispenser() {
//		this = new Dispenser(new Random().nextInt(10) + 20);
	}
	
	public Dispenser(int initialNumber) {
		
		dispenserContents = new LinkedBlockingDeque<FoodInfo>();
		
		if (initialNumber <= 20) {
			initialNumber = 20 + new Random().nextInt(10);
		}
		
		randomFood();
		
		this.price = (double) new Random().nextInt(3) + 1;
		
		for (int i = 0; i < initialNumber; i++) {
			if (this.type.equalsIgnoreCase("Snack")) {
				dispenserContents.addLast(new Snack(this.infoString, System.currentTimeMillis() + (long) 2*7*24*60*60*1000));
			} else if (this.type.equalsIgnoreCase("Drink")) {
				dispenserContents.addLast(new Drink(this.infoString, System.currentTimeMillis() + (long) 2*7*24*60*60*1000));
			}
		}
	}
	
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
	
	public String getPriceForPrinting() {
		return new DecimalFormat("#0.00").format(this.price + "");
	}
	
	private String[] getRandomInfo(ArrayList<String[]> correctFoodType) {
		return correctFoodType.get(new Random().nextInt(correctFoodType.size()));
	}

	public LinkedBlockingDeque<FoodInfo> getDispenserContents() {
		return dispenserContents;
	}

	public void setDispenserContents(
			LinkedBlockingDeque<FoodInfo> dispenserContents) {
		this.dispenserContents = dispenserContents;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String[] getInfoString() {
		return infoString;
	}

	public void setInfoString(String[] infoString) {
		this.infoString = infoString;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumberOfChoices() {
		return numberOfChoices;
	}
	
	public boolean canBuy() throws InvalidChoiceException{
		if (!this.dispenserContents.isEmpty()) {
			return true;
		} else {
			throw new InvalidChoiceException();
		}
	}
}

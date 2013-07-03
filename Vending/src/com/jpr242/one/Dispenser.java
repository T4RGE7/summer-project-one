package com.jpr242.one;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;


public class Dispenser {
	private LinkedBlockingDeque<FoodInformation> dispenserContents;	
	private double price;
	private String[] infoString;
	private final int numberOfChoices = 2;
	
	public Dispenser() {}
	
	public Dispenser(int initialNumber) {
		
		dispenserContents = new LinkedBlockingDeque<FoodInformation>();
		
		for (int i = 0; i < initialNumber; i++) {
			
		}
	}
	
	private void randomFood() {
		
		FoodList listOfFood = null;
		
		try {
			ObjectInputStream listIn = new ObjectInputStream(new FileInputStream(new File("foodList.dat")));
		
			listOfFood = (FoodList) listIn.readObject();
		
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
					break;
				case 1:
					this.infoString = getRandomInfo(listOfFood.getDrinks());
					break;
				default:
					System.err.println("Check Random Number Generator Seed in Dispenser");
			}
		}
		
	}
	
	private String[] getRandomInfo(ArrayList<String[]> correctFoodType) {
		return correctFoodType.get(new Random().nextInt(correctFoodType.size()));
	}
}

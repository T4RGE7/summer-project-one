package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class FoodList implements Serializable{
	private ArrayList<String[]> snacks;
	private ArrayList<String[]> drinks;
	
	public FoodList() {
		Scanner reader = null;
		
		snacks = new ArrayList<String[]>();
		drinks = new ArrayList<String[]>();
		
		try {
			reader = new Scanner(new File("snackList.txt"));
			
			while (reader.hasNextLine()) {
				snacks.add(reader.nextLine().split(","));
			}
		} catch (FileNotFoundException e) {
			System.err.println("Snack List Not Found.");
		}
		reader.close();
		
		try {
			reader = new Scanner(new File("drinkList.txt"));
			
			while (reader.hasNextLine()) {
				drinks.add(reader.nextLine().split(","));
			}
		} catch (FileNotFoundException e) {
			System.err.println("Drink List Not Found.");
		}
		reader.close();
	}

	public ArrayList<String[]> getSnacks() {
		return snacks;
	}

	public void setSnacks(ArrayList<String[]> snacks) {
		this.snacks = snacks;
	}

	public ArrayList<String[]> getDrinks() {
		return drinks;
	}

	public void setDrinks(ArrayList<String[]> drinks) {
		this.drinks = drinks;
	}
	
	
}

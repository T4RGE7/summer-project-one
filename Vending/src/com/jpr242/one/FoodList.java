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
	/**
	 * 
	 */
	private static final long serialVersionUID = 7227044176959713945L;
	private ArrayList<String[]> snacks;
	private ArrayList<String[]> drinks;
	/**
	 * Creates a new instance of this object used to hold the different snacks and drinks
	 */
	public FoodList() {
		Scanner reader = null;
		
		snacks = new ArrayList<String[]>();
		drinks = new ArrayList<String[]>();
		
		try {
			reader = new Scanner(new File("snackList.txt"));
			
			while (reader.hasNextLine()) {
				snacks.add(reader.nextLine().split(","));
				//
				//System.out.println(snacks.get(snacks.size() - 1)[0]);
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
	/**
	 * Returns the ArrayList of Snacks Contained
	 * @return All snacks
	 * 
	 */
	public ArrayList<String[]> getSnacks() {
		return snacks;
	}
	/**
	 * Sets the ArrayList of Snacks Contained
	 * @param snacks All the snacks
	 * 
	 */
	public void setSnacks(ArrayList<String[]> snacks) {
		this.snacks = snacks;
	}
	/**
	 * Returns the ArrayList of Drinks Contained
	 * @return All drinks
	 * 
	 */
	public ArrayList<String[]> getDrinks() {
		return drinks;
	}
	/**
	 * Sets the ArrayList of Drinks Contained
	 * @param drinks All the drinks
	 * 
	 */
	public void setDrinks(ArrayList<String[]> drinks) {
		this.drinks = drinks;
	}
	
	
}

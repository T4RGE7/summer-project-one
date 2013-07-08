package com.jpr242.one;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Driver {
	
	private static Container container;
	private static ArrayList<VendingMachine> machines;
	private static VendingMachine singleMachine;
	private static ArrayList<Dispenser> dispensers;
	private static Dispenser singleDispenser;
	private static FoodInfo foodItem;
	private static int countSave;

	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		
		machines = null;
		singleMachine = null;
		dispensers = null;
		singleDispenser = null;
		foodItem = null;
		container = openSave();
		
		machines = container.getMachines();
		
		boolean localRunning = true;
		
		System.out.println("Welcome to the Vending Machines!");
		
		for (int i = 0; i < machines.size(); i++) {
			machines.get(i).writeContents();
			System.out.println("Machine " + (i + 1) + ") " + machines.get(i).summary());
		}
		
		for (int i = 0; i < 1000; i++) {
			Customer temp = new Customer(i, countSave, container);
			String returned = temp.runCommand();
			while (returned.equalsIgnoreCase("continue")) {
				returned = temp.runCommand();
			}
			System.out.println("Customer " + i +": " + returned);
		}
		
		for (int i = 0; i < machines.size(); i++) {
			machines.get(i).writeContents();
			System.out.println("Machine " + (i + 1) + ") " + machines.get(i).summary());
		}
		System.exit(0);
		
		
//		try {
//			if (true) {
//				throw new IOException();
//			}
//			
//		} catch (IOException e) {
//			System.err.println("caught");
//		}
//		
		
	main:while (localRunning) {
			if (foodItem != null) {
				// item trying to purchase
				boolean inItem = true;
				do{
					System.out.print("Wound you like to purchase the " + foodItem.getName() + " (buy), look at its nutrition information (look) or go back (back)?: ");
					String tempInput = stdin.nextLine();
					
					if (tempInput.equalsIgnoreCase("buy")) {
						if (singleMachine.purchase(singleMachine.getDispensers().indexOf(singleDispenser))) {
							System.out.println("Your purchase was successful");
							foodItem = null;
							singleDispenser = null;
							dispensers = null;
							singleMachine = null;
							inItem = false;
						} else {
						}
					} else if (tempInput.equalsIgnoreCase("look")) {
						System.out.println(foodItem.getNutritionInfo() + ", $" + singleDispenser.getPrice());
					} else if (tempInput.equalsIgnoreCase("back")) {
						foodItem = null;
						singleDispenser = null;
						inItem = false;
					}
				} while (inItem);
				
			} else if (singleMachine != null) {
				// picking dispenser
				boolean goBack = false;
				do {
					System.out.print("Would you like to insert money (insert) look at the items avalible (look) select an item (select), or go back (back)?: ");
					String temp = stdin.nextLine();
					
					if (temp.equalsIgnoreCase("insert")) {
						System.out.print("How much money would you like to insert?: ");
						double tempInsert = stdin.nextDouble();
						stdin.nextLine();
						
						if (tempInsert > 0) {
							singleMachine.addMoney(tempInsert);
						}
					} else if (temp.equalsIgnoreCase("look")) {
						System.out.println(singleMachine.displayDispensers());
					} else if (temp.equalsIgnoreCase("select")) {
						System.out.print("Which item would you like to select?: ");
						int tempSelection = stdin.nextInt();
						stdin.nextLine();
						try {
							singleDispenser = singleMachine.getDispensers().get(tempSelection - 1);
							foodItem = singleDispenser.getDispenserContents().peekFirst();
						} catch (IndexOutOfBoundsException e) {
							System.err.println("Invalid Selection, Try Again.");
						}
					} else if (temp.equalsIgnoreCase("back")) {
						foodItem = null;
						singleDispenser = null;
						dispensers = null;
						singleMachine = null;
						goBack = true;
					}
				} while (foodItem == null && !goBack);
			} else if (machines != null) {
				System.out.println();
				for (int i = 0; i < machines.size(); i++) {
					System.out.println("Machine " + (i + 1) + ") " + machines.get(i).summary());
				}
					do {
						System.out.print("\nWhich Machine would you like to look at? (1-" + (machines.size()) + ") or would you like to exit (exit): ");
//						int tempSelection = stdin.nextInt();
//						stdin.nextLine();
						int tempSelection = -1;
						String tempIn = stdin.nextLine();
						if (tempIn.equalsIgnoreCase("exit")) {
							break main;
						} else {
							try{
								tempSelection = Integer.parseInt(tempIn);
							} catch (NumberFormatException e) {
								//System.err.println("Invalid Selection");
							}
						}
						try {
							singleMachine = machines.get(tempSelection - 1);
						} catch (IndexOutOfBoundsException e) {
							System.err.println("Invalid Selection, Try Again.");
							singleMachine = null;
						}
					} while (singleMachine == null);
					
			}
		}
		
		saveState();
	}
	
	public static void saveState() {
		ObjectOutputStream oOS;
		
		try{
			oOS = new ObjectOutputStream(new FileOutputStream("run" + countSave + "/container.dat"));
			oOS.writeObject(container);
			oOS.close();
		} catch (IOException e) {
			System.err.println("Unable to Save");
		}
	}
	public static Container openSave() {
		boolean found = false;
		int count = 0;
		
		while (!found){
			Path target = Paths.get("run" + count);
			boolean fileFound = Files.exists(target, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
			if (!fileFound) {
				found = true;
			} else {
				count++;
			}
		}
		count--;
		System.out.println("Setup #" + count);
		countSave = count;
		try {
			return (Container)(new ObjectInputStream(new FileInputStream("run" + count + "/container.dat")).readObject());
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Unable to Open Saved State");
		}
		return null;
	}
	
}

package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

public class Setup {
	
	private Container container;
	
	public static void main(String[] args) {
		int temp = Integer.valueOf(args[0]);
		int numberOfMachines = (temp <= 2 ? (new Random().nextInt(5) + 2) : temp);
		
		
		
		for (int i = 0; i < numberOfMachines; i++) {
			
		}
	}
	
	public static void downloadLists() {
		PrintWriter printer = null;
		
		try {
			printer = new PrintWriter("foodList.txt");
			
			//from website
			try {
				Scanner website = new Scanner(new URL("http://jimisthebest.cs.net/csc202/foodList.html").openStream());
				
				while (website.hasNextLine()) {
					printer.println(website.nextLine());
				}
				
			} catch (IOException e) { //from backup
				System.out.println("Not Able to Access Snack List From Internet.\nSwitching to Backup Panic List.");
				printer.println("Pi,300,12,20,10");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Unable to Create Snack File.");
		}
		printer.close();
		
		try {
			printer = new PrintWriter("drinklist.txt");
			
			//from website
			try {
				Scanner website = new Scanner(new URL("http://jimisthebest.cs.net/csc202/drinkList.html").openStream());
				
				while (website.hasNextLine()) {
					printer.println(website.nextLine());
				}
				
			} catch (IOException e) { //from backup
				System.out.println("Not Able to Access Drink List From Internet.\nSwitching to Backup Panic List.");
				printer.println("Coke,200,15,20,5");
				printer.println("Pepsi,250,20,20,10");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Unable to Create Drink File");
		}
		
		
	}
	
}

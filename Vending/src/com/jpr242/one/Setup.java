package com.jpr242.one;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

public class Setup {
	
	public static void main(String[] args) {
		int temp = Integer.valueOf(args[0]);
		int numberOfMachines = (temp <= 2 ? (new Random().nextInt(5) + 2) : temp);
		
		
		
		for (int i = 0; i < numberOfMachines; i++) {
			
		}
	}
	
	public static void downloadLists() {
		PrintWriter printer;
		
		try {
			printer = new PrintWriter("foodList.txt");
			
			//from website
			try {
				Scanner website = new Scanner(new URL("http://jimisthebest.cs.net/csc202/foodList.html").openStream());
				
			} catch (IOException e) {
				System.out.println("Not Able to Access Online List.\nSwitching to Backup Panic List.");
				printer.println("Pi,")
			}
		} catch (FileNotFoundException e) {
			System.out.println("Unable to Create File.");
		}
		
		
	}
	
}

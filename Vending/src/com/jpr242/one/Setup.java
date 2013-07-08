package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class Setup {
	
	private static Container container;
	private static FoodList foodList;
	
	public static void main(String[] args) {
		int temp = -1;
		
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[0]);
		}
		
		if (args != null && args.length > 0) {
			temp = Integer.valueOf(args[0]);
		}
		
		int numberOfMachines = (temp <= 2 ? (new Random().nextInt(5) + 2) : temp);
		
		downloadLists();
		//make food list
		foodList = new FoodList();
		//write foodList
		
		try {
			ObjectOutputStream foodListOut = new ObjectOutputStream(new FileOutputStream("foodList.dat"));
			foodListOut.writeObject(foodList);
			foodListOut.close();
		} catch (IOException e) {
			System.err.println("Unable to Create Food List");
		}
		
		
		container = new Container();
		

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
		
	//	Path target = Paths.get("run" + count);
		for (int i = 0; i < numberOfMachines; i++) {
			container.addVendingMachine(i, count);
		}
		
		try {
			//temp?
			Runtime.getRuntime().exec("mkdir run" + count);
			long lastTime = System.currentTimeMillis() + 1000;
			while (System.currentTimeMillis() < lastTime) {
				//wait 5 seconds
			}
			Runtime.getRuntime().exec("mkdir run" + count + "/reciepts");
			lastTime = System.currentTimeMillis() + 1000;
			while (System.currentTimeMillis() < lastTime) {
				//wait 5 seconds
			}
			/*Files.createDirectory(target, (FileAttribute[]) null);*/
			ObjectOutputStream oOS = new ObjectOutputStream(new FileOutputStream("run" + count + "/container.dat"));
			oOS.writeObject(container);
			oOS.flush();
			oOS.close();
		} catch (IOException e) {
			System.err.println("Error, Unable to Create Folder.");
			System.err.println(e.getMessage());
		}
		
	}
	
	public static void downloadLists() {
		PrintWriter printer = null;
		
		try {
			printer = new PrintWriter("snackList.txt");
			
			//from website
			try {
				Scanner website = new Scanner(new URL("http://jimisthebest.cs.net/csc202/foodList.html").openStream());
				
				while (website.hasNextLine()) {
					printer.println(website.nextLine());
				}
				
				website.close();
				
			} catch (IOException e) { //from backup
				System.err.println("Not Able to Access Snack List From Internet.\nSwitching to Backup Panic List.");
				printer.println("Pi,300,12,20,10");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Unable to Create Snack File.");
		}
		printer.close();
		
		try {
			printer = new PrintWriter("drinkList.txt");
			
			//from website
			try {
				Scanner website = new Scanner(new URL("http://jimisthebest.cs.net/csc202/drinkList.html").openStream());
				
				while (website.hasNextLine()) {
					printer.println(website.nextLine());
				}
				
				website.close();
				
			} catch (IOException e) { //from backup
				System.err.println("Not Able to Access Drink List From Internet.\nSwitching to Backup Panic List.");
				printer.println("Coke,200,15,20,5");
				printer.println("Pepsi,250,20,20,10");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Unable to Create Drink File");
		}
		printer.close();
		
	}
	
}

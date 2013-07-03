package com.jpr242.one;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Driver {
	
	private static Container container;

	public static void main(String[] args) {
		container = openSave();
		System.out.println("Welcome to the Vending Machines!");
		container.getMachines();
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
		
		try {
			return (Container)(new ObjectInputStream(new FileInputStream("run" + count + "/container.dat")).readObject());
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Unable to Open Saved State");
		}
		return null;
	}
	
}

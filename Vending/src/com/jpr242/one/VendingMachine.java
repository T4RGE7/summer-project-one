package com.jpr242.one;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class VendingMachine {

	private double moneyIn;
	private ArrayList<Dispenser> dispensers;
	private boolean on;
	
	
	public VendingMachine() {
		this.on = true;
		this.moneyIn = 0;
		this.dispensers = new ArrayList<Dispenser>();
		
		int numOfDispensers = new Random().nextInt(5) + 20;
		
		for (int i = 0; i < numOfDispensers; i++) {
			this.dispensers.add(new Dispenser(-1));
		}
	}
	
	
}

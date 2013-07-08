package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.io.Serializable;

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Drink extends FoodInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1347050739095760055L;
	/**
	 * Creates a new Drink object
	 * @param input	takes an array of length 5 and converts it into information about the drink
	 * @param expireTime	takes a long representing the expiration time in milliseconds 
	 */
	public Drink(String[] input, long expireTime) {
		if (input.length == 5) {
			this.name = input[0];
			// this.price = price;
			this.calories = Integer.valueOf(input[1]);
			this.fat = Integer.valueOf(input[2]);
			this.sugar = Integer.valueOf(input[3]);
			this.carbs = Integer.valueOf(input[4]);
			this.type = "Drink";
			this.expires = expireTime;
			
		}

	}

}

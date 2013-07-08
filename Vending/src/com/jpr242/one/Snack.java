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

public class Snack extends FoodInfo implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 388678973292073885L;
	/**
	 * Creates a new Snack object
	 * @param input The String array of length 5 used to generate fields
	 * @param expireTime the experiation time of this snack in millis
	 * 
	 */
	public Snack(String[] input, long expireTime) {
		//System.out.println(input.length);
		if (input.length == 5) {
			this.name = input[0];
			// this.price = price;
			this.calories = Integer.valueOf(input[1]);
			this.fat = Integer.valueOf(input[2]);
			this.sugar = Integer.valueOf(input[3]);
			this.carbs = Integer.valueOf(input[4]);
			this.type = "Snack";
			this.expires = expireTime;
		}
	}
	
}

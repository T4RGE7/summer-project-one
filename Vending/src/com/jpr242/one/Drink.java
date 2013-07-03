package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Drink extends FoodInformation {

	public Drink(String[] input, long expireTime) {
		if (input.length == 6) {
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

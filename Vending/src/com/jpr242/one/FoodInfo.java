package com.jpr242.one;

import java.io.Serializable;

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class FoodInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6889449006114452885L;
	protected String name;
	protected String type;
	//private double price;
	protected int calories, fat, sugar, carbs;
	protected long expires;
	protected boolean snack, drink;

	public FoodInfo() {}
	
	public FoodInfo(String name, /*double price,*/ int calories, int fat, int sugar, int carbs, String type, long expireTimeInMills) {
		this.name = name;
	//	this.price = price;
		this.calories = calories;
		this.fat = fat;
		this.sugar = sugar;
		this.carbs = carbs;
		this.type = type;
		this.expires = expireTimeInMills;
		setTypeBoolean();
	}
	
	public String getNutritionInfo() {
		String toReturn = "";
		toReturn += "Calories: " + this.calories + ", Fat: " + this.fat + ", Sugar: " + this.sugar + ", Carbs: " + this.carbs;
		return toReturn;
	}
	
	public void setTypeBoolean() {
		if (this.type.equalsIgnoreCase("snack")) {
			this.snack = true;
			this.drink = false;
		} else if (this.type.equalsIgnoreCase("drink")) {
			this.snack = false;
			this.drink = true;
		} else {
			this.snack = false;
			this.drink = false;
		}
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getFat() {
		return fat;
	}

	public void setFat(int fat) {
		this.fat = fat;
	}

	public int getSugar() {
		return sugar;
	}

	public void setSugar(int sugar) {
		this.sugar = sugar;
	}

	public int getCarbs() {
		return carbs;
	}

	public void setCarbs(int carbs) {
		this.carbs = carbs;
	}
	
	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

	public boolean isSnack() {
		if (this.type.equalsIgnoreCase("Snack")) {
			snack = true;
		}
		return snack;
	}

	public void setSnack(boolean snack) {
		this.snack = snack;
	}

	public boolean isDrink() {
		if (this.type.equalsIgnoreCase("Drink")) {
			drink = true;
		}
		return drink;
	}

	public void setDrink(boolean drink) {
		this.drink = drink;
	}

}

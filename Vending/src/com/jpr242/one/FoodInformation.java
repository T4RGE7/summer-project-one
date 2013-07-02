package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
public class FoodInformation {
	
	private String name, type;
	//private double price;
	private int calories, fat, sugar, carbs;
	private long expires;

	public FoodInformation() {}
	
	public FoodInformation(String name, /*double price,*/ int calories, int fat, int sugar, int carbs, String type, long expireTimeInMills) {
		this.name = name;
	//	this.price = price;
		this.calories = calories;
		this.fat = fat;
		this.sugar = sugar;
		this.carbs = carbs;
		this.type = type;
		this.expires = expireTimeInMills;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public String getNutritionInfo() {
		String toReturn = "";
		toReturn += this.name + " - " + this.type + ", Calories: " + this.calories + ", Fat: " + this.fat + ", Sugar: " + this.sugar + ", Carbs: " + this.carbs;
		return toReturn;
	}
	
}

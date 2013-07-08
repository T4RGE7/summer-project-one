package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.io.Serializable;

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
	/**
	 * Creates a new Empty food object, never called from here
	 */
	public FoodInfo() {}
	/**
	 * Creates a new FoodInfo object, never called directly from here
	 */
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
	/**
	 * Returns a String of all the nutrition information
	 * @return String containing nutrition information
	 */
	public String getNutritionInfo() {
		String toReturn = "";
		toReturn += "Calories: " + this.calories + ", Fat: " + this.fat + ", Sugar: " + this.sugar + ", Carbs: " + this.carbs;
		return toReturn;
	}
	/**
	 * Sets the type of food from strings already contained in the object
	 */
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
	
	/**
	 * Returns name of this product
	 * @return String name of product
	 * 
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets name of this product
	 * @param name String name of product
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns type of this product
	 * @return String type of product
	 * 
	 */
	public String getType() {
		return type;
	}
	/**
	 * Sets type of this product
	 * @param type String type of product
	 * 
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Returns calories of this product
	 * @return int number of calories
	 * 
	 */
	public int getCalories() {
		return calories;
	}
	/**
	 * Sets calories of this product
	 * @param calories int number of calories
	 * 
	 */
	public void setCalories(int calories) {
		this.calories = calories;
	}
	/**
	 * Returns fat of this product
	 * @return int number of fat
	 * 
	 */
	public int getFat() {
		return fat;
	}
	/**
	 * Sets fat of this product
	 * @param fat int number of fat
	 * 
	 */
	public void setFat(int fat) {
		this.fat = fat;
	}
	/**
	 * Returns sugar of this product
	 * @return int number of sugar
	 * 
	 */
	public int getSugar() {
		return sugar;
	}
	/**
	 * Sets sugar of this product
	 * @param sugar int number of sugar
	 * 
	 */
	public void setSugar(int sugar) {
		this.sugar = sugar;
	}
	/**
	 * Returns carbs of this product
	 * @return int number of carbs
	 * 
	 */
	public int getCarbs() {
		return carbs;
	}
	/**
	 * Sets carbs of this product
	 * @param carbs int number of carbs
	 * 
	 */
	public void setCarbs(int carbs) {
		this.carbs = carbs;
	}
	/**
	 * Returns expiration time of this product
	 * @return long experation time
	 * 
	 */
	public long getExpires() {
		return expires;
	}
	/**
	 * Sets expiration time of this product
	 * @return expires long experation time
	 * 
	 */
	public void setExpires(long expires) {
		this.expires = expires;
	}
	/**
	 * Returns if the item is a snack
	 * @return boolean true if snack false if not
	 * 
	 */
	public boolean isSnack() {
		if (this.type.equalsIgnoreCase("Snack")) {
			snack = true;
		}
		return snack;
	}
	/**
	 * Sets if the item is a snack
	 * @param snack boolean true if snack false if not
	 * 
	 */
	public void setSnack(boolean snack) {
		this.snack = snack;
	}
	/**
	 * Returns if the item is a drink
	 * @return boolean true if drink false if not
	 * 
	 */
	public boolean isDrink() {
		if (this.type.equalsIgnoreCase("Drink")) {
			drink = true;
		}
		return drink;
	}
	/**
	 * Sets if the item is a drink
	 * @param drink boolean true if drink false if not
	 * 
	 */
	public void setDrink(boolean drink) {
		this.drink = drink;
	}

}

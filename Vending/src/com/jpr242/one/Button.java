package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Button {

	private int x1, x2, y1, y2, width, height;
	/**
	 * Creates an empty button
	 */
	public Button() {
		
	}
	/**
	 * Never used, creates generic button
	 * @param x1 top left x coordinate
	 * @param y1 top left y coordinate
	 * @param width distance to second x coordinate
	 * @param height distance to second y coordinate
	 */
	public Button(int x1, int y1, int width, int height) {
		this.x1 = x1;
		this.y1 = y1;
		this.height = height;
		this.width = width;
		this.x2 = x1 + width;
		this.y2 = y1 + height;
	}
	/**
	 * Never used, sets button boundaries
	 * @param x1 top left x coordinate
	 * @param y1 top left y coordinate
	 * @param width distance to second x coordinate
	 * @param height distance to second y coordinate
	 */
	public void setArea(int x1, int y1, int width, int height) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1 + width;
		this.y2 = y1 + height;
	}
	/**
	 * Checks to see if a click's coordinates are inside of the button's area
	 * @param x left mouse x coordinate
	 * @param y left mouse y coordinate
	 */
	public boolean isInside(double x, double y) {
		if (x > x2) {
			return false;
		}
		if (x < x1) {
			return false;
		}
		if (y > y2) {
			return false;
		}
		if (y < y1) {
			return false;
		}
		return true;
	}
	/**
	 * Returns top left x
	 * @return int top left x
	 */
	public int getX1() {
		return x1;
	}
	/**
	 * Sets top left x
	 * @param x1 int top left x
	 */
	public void setX1(int x1) {
		this.x1 = x1;
	}
	/**
	 * Returns bottom right x
	 * @return int bottom right x
	 */
	public int getX2() {
		return x2;
	}
	/**
	 * 
	 * Sets bottom right x
	 * @param x2 int bottom right x
	 */
	public void setX2(int x2) {
		this.x2 = x2;
	}
	/**
	 * Returns top left y
	 * @return int top left y
	 */
	public int getY1() {
		return y1;
	}
	/**
	 * Sets top left y
	 * @param y1 int top left y
	 */
	public void setY1(int y1) {
		this.y1 = y1;
	}
	/**
	 * Returns bottom right y
	 * @return int bottom right y
	 */
	public int getY2() {
		return y2;
	}
	/**
	 * Sets bottom right y
	 * @param y2 int bottom right y
	 */
	public void setY2(int y2) {
		this.y2 = y2;
	}
	/**
	 * Returns width of area
	 * @return int width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Sets width of area, doesn't modify coordinates
	 * @param width int width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * Returns Height of area, doesn't modify coordinates
	 * @return int width
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Sets Height of area, doesn't modify coordinates
	 * @param height int height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
}

package com.jpr242.one;

public class Button {

	private int x1, x2, y1, y2, width, height;
	
	public Button() {
		
	}
	
	public Button(int x1, int y1, int width, int height) {
		this.x1 = x1;
		this.y1 = y1;
		this.height = height;
		this.width = width;
		this.x2 = x1 + width;
		this.y2 = y1 + height;
	}
	
	public void setArea(int x1, int y1, int width, int height) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1 + width;
		this.y2 = y1 + height;
	}
	
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

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}

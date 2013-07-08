package com.jpr242.one;

public class Button {

	private int x1, x2, y1, y2;
	
	public Button() {
		
	}
	
	public Button(int x1, int y1, int width, int height) {
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
	
}

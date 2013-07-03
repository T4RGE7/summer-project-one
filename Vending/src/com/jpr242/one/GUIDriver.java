package com.jpr242.one;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.PrintWriter;

public class GUIDriver{

	private JimsCanvas canvas;

	private Container container;
	private int screenWidth, screenHeight;

	private PrintWriter printer;
	
	private boolean lookingAtMachines, lookingAtDispensers;
	private int machine, dispenser;
	
	public GUIDriver() {
		Rectangle device = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
		int width = (int) device.getWidth();
		int height = (int) device.getHeight();
		int dim = 35;
		width = (width / dim);
		height = (height / dim);
		width *= dim;
		height *= dim;

		screenWidth = width;
		screenHeight = height;
		canvas = new JimsCanvas(width, height, this);

	}

	public JimsCanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(JimsCanvas canvas) {
		this.canvas = canvas;
	}


	public void mouseAction(float x, float y, int button, boolean pressed) {

		if (button == 3) {
			
		} else if (button == 1) {
			
		}

	}

	public void draw(Graphics2D g, float elapsedTime) {
		
	}

	public static void main(String[] args) throws Exception {
		GUIDriver simulator = new GUIDriver();
		simulator.play();
	}

	/**
	 * 
	 */
	public void play() {
		canvas.setupAndDisplay();
		// display.setupAndDisplay(true);
	}
}

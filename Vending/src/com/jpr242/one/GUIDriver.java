package com.jpr242.one;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class GUIDriver{

	private JimsCanvas canvas;

	private static Container container;
	private int screenWidth, screenHeight;

	private PrintWriter printer;
	
	private boolean lookingAtMachines, lookingAtDispensers;
	private int machine, dispenser;
	
	//private static Container container;
	private static ArrayList<VendingMachine> machines;
	private static VendingMachine singleMachine;
	private static ArrayList<Dispenser> dispensers;
	private static Dispenser singleDispenser;
	private static FoodInfo foodItem;
	private static int countSave;
	
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
		canvas = new JimsCanvas(700, 700, this);

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

	public void draw(Graphics2D g) {
		canvas.drawExitButton(g);
		if (false) {
			
		} else if (singleMachine == null) {
			for (int i = 0; i < machines.size(); i++) {
				canvas.drawVendingMachine(g, i, machines.get(i).isOn());
			}
		}
	}

	public static void main(String[] args) throws Exception {
Scanner stdin = new Scanner(System.in);
		
		machines = null;
		singleMachine = null;
		dispensers = null;
		singleDispenser = null;
		foodItem = null;
		container = openSave();
		
		machines = container.getMachines();
		
		boolean localRunning = true;
		boolean gui = true;
		
		System.out.print("Would you like to run the GUI (gui) or the Demo (demo)?: ");
		if (new Scanner(System.in).nextLine().equalsIgnoreCase("demo")) {
			gui = false;
		}
		
		System.out.println("Welcome to the Vending Machines!");
		
		for (int i = 0; i < machines.size(); i++) {
			machines.get(i).writeContents();
		//	System.out.println("Machine " + (i + 1) + ") " + machines.get(i).summary());
		}
		
		if (!gui) {
			
			for (int i = 0; i < machines.size(); i++) {
				//machines.get(i).writeContents();
				System.out.println("Machine " + (i + 1) + ") " + machines.get(i).summary());
			}
			
			int customerCount = 0;
			while (customerCount < 10000) {
				Customer temp = new Customer(customerCount, countSave, container);
				String returned = temp.runCommand();
				while (returned.equalsIgnoreCase("continue")) {
					returned = temp.runCommand();
				}
				System.out.println("Customer " + customerCount +": " + returned);
				customerCount++;
			}
			
			for (int i = 0; i < machines.size(); i++) {
				machines.get(i).writeContents();
				System.out.println("Machine " + (i + 1) + ") " + machines.get(i).summary());
			}
			try {
				ObjectOutputStream oOS = new ObjectOutputStream(new FileOutputStream("run" + countSave + "/container.dat"));
				oOS.writeObject(container);
				oOS.flush();
				oOS.close();
			} catch (IOException e) {
				System.err.println("Unable to write file");
			}
			System.exit(0);
		}
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

	public static void saveState() {
		ObjectOutputStream oOS;
		
		try{
			oOS = new ObjectOutputStream(new FileOutputStream("run" + countSave + "/container.dat"));
			oOS.writeObject(container);
			oOS.close();
		} catch (IOException e) {
			System.err.println("Unable to Save");
		}
	}
	public static Container openSave() {
		boolean found = false;
		int count = 0;
		
		while (!found){
			Path target = Paths.get("run" + count);
			boolean fileFound = Files.exists(target, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
			if (!fileFound) {
				found = true;
			} else {
				count++;
			}
		}
		count--;
		System.out.println("Setup #" + count);
		countSave = count;
		try {
			return (Container)(new ObjectInputStream(new FileInputStream("run" + count + "/container.dat")).readObject());
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Unable to Open Saved State");
		}
		return null;
	}
}

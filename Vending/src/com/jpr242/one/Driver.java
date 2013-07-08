package com.jpr242.one;
/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Driver extends JComponent implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1826422240887621592L;
	private static Container container;
	private static ArrayList<VendingMachine> machines;
	private static VendingMachine singleMachine;
	private static ArrayList<Dispenser> dispensers;
	private static Dispenser singleDispenser;
	private static FoodInfo foodItem;
	private static int countSave;
	private static final DecimalFormat df = new DecimalFormat("#0.00");
	private static ArrayList<MachineButton> machineButtons;
	private static ArrayList<DispenserButton> dispenserButtons;
	private static ArrayList<MachineButton> insertButtons;
	private static ArrayList<MachineButton> buyButtons;
	private static MachineButton randomMachine, randomPower, randomMoney, randomDispenser, exit;
	private boolean successful = false;
	private long successTime;
	/**
	 * Creates a new Driver Object initializing buttons and adding a mouse listener
	 */
	public Driver(){
		setMachineButtons();
		setRandomMachineButton();
		setRandomPowerButton();
		setPowerButton();
//		System.out.println(machineButtons.get(1).getX1());
//		System.out.println(machineButtons.get(1).getX2());
//		System.out.println(machineButtons.get(1).getY1());
//		System.out.println(machineButtons.get(1).getY2());
		
//		setDispenserButtons();
	//	dispenserButtons = new ArrayList<Button>();
		this.addMouseListener(this);
		
	}
	/**
	 * Called like a draw() method to put items on screen
	 */
	public void paintComponent(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
//		g.setColor(Color.black);
//		g.drawString(System.currentTimeMillis() + "", 100, 100);

		drawUpperRightButton(g, randomMachine);
		drawUpperRightButton(g, randomPower);
		
		if (successful) {
			successTime = System.currentTimeMillis() + 10000;
			successful = false;
			foodItem = null;
//			g.setColor(Color.BLACK);
//			g.drawString("Purchase successful! Please Collect Your Reciept, Change and Item", 405, 650);
//			while(System.currentTimeMillis() < successTime) {
//				System.out.println("loop");
//			}
		}
		if (System.currentTimeMillis() < successTime) {
			g.setColor(Color.green);
			g.fillRect(200, 600, 500, 100);
			g.setColor(Color.black);
			g.drawString("Purchase successful! Please Collect Your Reciept, (Change) and Item", 205, 650);
		}
		
		if (foodItem != null) {
			
			drawFoodItem(g);
			drawBuyButton(g);
			try{
				if (singleDispenser.getPrice() > singleMachine.getMoneyIn()) {
					//System.out.println("HERE");
					throw new InsufficientFundsException();
				}
				} catch (InsufficientFundsException e) {
					g.setColor(Color.red);
					g.drawString("<- You must insert more money", 405, 650);
				}
		}
			
		if(singleMachine != null) {
			drawUpperRightButton(g, randomMoney);
			drawUpperRightButton(g, randomDispenser);
			for (int i = 0; i < dispenserButtons.size(); i++) {
	//			System.out.println(i);
				drawDispensers(g, dispenserButtons.get(i));
			}
			for (int i = 0; i < insertButtons.size(); i++) {
				drawInsertButtons(g);
			}
		} 
		for (int i = 0; i < machineButtons.size(); i++) {
			drawMachines(g, machineButtons.get(i));
		}
		long lastTime = System.nanoTime() + 500;
		
		
		
		drawPowerButton(g, exit);
		while (System.nanoTime() < lastTime){}
		
		repaint();
	}
  
	/**
	 * Called to Start the main driver
	 */
	public static void main(String[] args) {
		machines = null;
		singleMachine = null;
		dispensers = null;
		singleDispenser = null;
		foodItem = null;
		container = openSave();
		
		machines = container.getMachines();
	//	Thread shutdown = new Thread();
	//	shutdown.
	//	Runtime.getRuntime().addShutdownHook(new Thread());
		
		System.out.print("Would you like to run the GUI (gui) or the Demo (demo)?: ");
		boolean gui = true;
		if (new Scanner(System.in).nextLine().equalsIgnoreCase("demo")) {
			gui = false;
		}
		
		System.out.println("Welcome to the Vending Machines!");
		
		for (int i = 0; i < machines.size(); i++) {
			machines.get(i).start();
		//	machines.get(i).writeContents();
		//	System.out.println("Machine " + (i + 1) + ") " + machines.get(i).summary());
		}
		
		if (!gui) {
			
			for (int i = 0; i < machines.size(); i++) {
				//machines.get(i).writeContents();
				System.out.println("Machine " + (i + 1) + ") " + machines.get(i).summary());
			}
			
			int customerCount = 0;
			while (customerCount < 10000) {
				if (new Random().nextInt(100) > 30){
				Customer temp = new Customer(customerCount, countSave, container);
			//	if (new Random().nextInt(100) > 30){
				String returned = temp.runCommand();
				while (returned.equalsIgnoreCase("continue")) {
					returned = temp.runCommand();
				}
				System.out.println("Customer " + customerCount +": " + returned);
				customerCount++;
				} else {
					//Do nothing
					System.currentTimeMillis();
				}
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

		
		JFrame frame = new JFrame("Test Frame");
		frame.setSize(700, 700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Driver button = new Driver();  // Initialize the component.
		frame.getContentPane().add(button);      // Place the component on the application
		
		frame.setVisible(true);
	}
	/**
	 * Sets the food item for bottom right
	 */
	public static void setFoodItem() {
		foodItem = singleDispenser.getDispenserContents().peekFirst();
	}
	/**
	 * Sets up power button
	 */
	public static void setPowerButton() {
		MachineButton temp = new MachineButton(-5, null);
		temp.setX1(675);
		temp.setX2(700);
		temp.setY1(0);
		temp.setY2(25);
		temp.setWidth(25);
		temp.setHeight(25);
		exit = temp;
	}
	/**
	 * Draws the power button in top right
	 * @param g The graphics object being used throughout
	 * @param b The power button as a MachineButton object
	 */
	public static void drawPowerButton(Graphics g, MachineButton b) {
		g.setColor(Color.red);
		g.fillRect(b.getX1(), b.getY1(), b.getWidth(), b.getHeight());
		g.setColor(Color.white);
		g.drawString("X", b.getX1() + 5, b.getY1() + 15);
		
	}
	/**
	 * Sets up the Buttons representing the machines on the left
	 */
	public static void setMachineButtons() {
		machineButtons = new ArrayList<MachineButton>();
		for (int i = 0; i < machines.size(); i++) {
			MachineButton temp = new MachineButton(i, machines.get(i));
		//	temp.setArea(0, 30*i, 100, 20);
			temp.setX1(0);
			temp.setX2(100);
			temp.setY1(30*i);
			temp.setY2(30*i + 20);
			temp.setWidth(100);
			temp.setHeight(20);
			machineButtons.add(temp);
		}
	}
	/**
	 * Sets up buttons in bottom left corner for working with money
	 */
	public static void setInsertButtons() {
		insertButtons = new ArrayList<MachineButton>();
		MachineButton insert1 = new MachineButton(-1, singleMachine);
		MachineButton insert5 = new MachineButton(-2, singleMachine);
		MachineButton returner = new MachineButton(-3, singleMachine);
		
		insert1.setX1(00);
		insert1.setX2(100);
		insert1.setY1(500);
		insert1.setY2(550);
		insert1.setWidth(100);
		insert1.setHeight(50);
		
		insert5.setX1(00);
		insert5.setX2(100);
		insert5.setY1(560);
		insert5.setY2(610);
		insert5.setWidth(100);
		insert5.setHeight(50);
	
		returner.setX1(00);
		returner.setX2(100);
		returner.setY1(620);
		returner.setY2(670);
		returner.setWidth(100);
		returner.setHeight(50);
		
		insertButtons.add(insert1);
		insertButtons.add(insert5);
		insertButtons.add(returner);
		
		
		
	}
	/**
	 * Sets up buy button, called when an item is selected
	 */
	public static void setBuyButton() {
		buyButtons = new ArrayList<MachineButton>();
		MachineButton temp = new MachineButton(-4, singleMachine);
		
		temp.setX1(290);
		temp.setX2(390);
		temp.setY1(620);
		temp.setY2(670);
		temp.setWidth(100);
		temp.setHeight(50);
		
		buyButtons.add(temp);
	}
	/**
	 * Sets up dispenser buttons for middle column
	 */
	public static void setDispenserButtons() {
		dispenserButtons = new ArrayList<DispenserButton>();
		for (int i = 0; i < singleMachine.getDispensers().size(); i++) {
			DispenserButton temp = new DispenserButton(i, singleMachine.getDispensers().get(i));
		//	temp.setArea(0, 30*i, 100, 20);
			temp.setX1(110);
			temp.setX2(260);
			temp.setY1(18*i);
			temp.setY2(18*i + 20);
			temp.setWidth(150);
			temp.setHeight(16);
			dispenserButtons.add(temp);
		}
	}
	
	//randomMachine, randomPower, randomMoney, randomDispenser;
	/**
	 * Sets button to randomly toggle power
	 */
	public static void setRandomPowerButton() {
		MachineButton temp = new MachineButton(-5, null);
		temp.setX1(500);
		temp.setX2(650);
		temp.setY1(0);
		temp.setY2(50);
		temp.setWidth(150);
		temp.setHeight(50);
		randomPower = temp;
	}
	/**
	 * Sets button to select a random machine
	 */
	public static void setRandomMachineButton() {
		MachineButton temp = new MachineButton(-6, null);
		temp.setX1(500);
		temp.setX2(650);
		temp.setY1(55);
		temp.setY2(105);
		temp.setWidth(150);
		temp.setHeight(50);
		randomMachine = temp;
	}
	/**
	 * Sets button to enter random money
	 */
	public static void setRandomMoneyButton() {
		MachineButton temp = new MachineButton(-7, null);
		temp.setX1(500);
		temp.setX2(650);
		temp.setY1(110);
		temp.setY2(160);
		temp.setWidth(150);
		temp.setHeight(50);
		randomMoney = temp;
	}
	/**
	 * Sets button to select a random item
	 */
	public static void setRandomDispenserButton() {
		MachineButton temp = new MachineButton(-8, null);
		temp.setX1(500);
		temp.setX2(650);
		temp.setY1(165);
		temp.setY2(215);
		temp.setWidth(150);
		temp.setHeight(50);
		randomDispenser = temp;
	}
	/**
	 * Draws the random buttons
	 * @param g Graphics object used throughtou
	 * @param b Machine buttons passed one at a time
	 */
	public static void drawUpperRightButton(Graphics g, MachineButton b) {
		g.setColor(Color.white);
		g.fillRect(b.getX1(), b.getY1(), b.getWidth(), b.getHeight());
		g.setColor(Color.black);
		String toDraw[] = {"Random Power","Random Machine","Random Item","Random Money"};
		int toDrawNum = 0;
		switch(b.getId()) {
		case -8: toDrawNum = 2;
		break;
		case -7: toDrawNum = 3;
		break;
		case -6: toDrawNum = 1;
		break;
		default: toDrawNum = 0;
		}
		g.drawString(toDraw[toDrawNum], b.getX1() + 5, b.getY1() + 25);
	}
	/**
	 * Saves container object bytes
	 */
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
	/**
	 * Opens container bytes and reads
	 */
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
	/**
	 * Draws the random buttons
	 * @param g Graphics object used throughout
	 * @param button Machine buttons passed one at a time
	 */
	public void drawMachines(Graphics g, MachineButton button) {
			if (!button.getMachine().isOn()){
				g.setColor(Color.gray);
			} else {
				g.setColor(Color.white);
			}
		//	System.out.println(button.getX1());
			g.fillRect(button.getX1(), button.getY1(), button.getWidth(), button.getHeight());
			g.setColor(Color.black);
			g.drawString("Machine " + (button.getId() + 1), button.getX1() + 10, button.getY1() + 15);
	}
	/**
	 * Draws the buttons in bottom left dealing with money
	 * @param g Graphics object used throughout
	 * 
	 */
	public void drawInsertButtons(Graphics g) {
		String names[] = {"Insert $1", "Insert $5", "Return Money"};
		for (int i = 0; i < insertButtons.size(); i++) {
			if (singleMachine.isOn()){
				g.setColor(Color.white);
			} else {
				g.setColor(Color.gray);
			}
			if (names[i].equalsIgnoreCase("Return Money") && singleMachine.getMoneyIn() <= 0) {
				g.setColor(Color.gray);
			}
			g.fillRect(insertButtons.get(i).getX1(), insertButtons.get(i).getY1(), insertButtons.get(i).getWidth(), insertButtons.get(i).getHeight());
			g.setColor(Color.black);
			g.drawString(names[i], insertButtons.get(i).getX1() + 10, insertButtons.get(i).getY1() + 25);
		}
		g.setColor(Color.white);
		g.drawString("$" + df.format(insertButtons.get(0).getMachine().getMoneyIn()), 5, 450);
	}
	/**
	 * Draws the dispensers once a machine is selected
	 * @param g Graphics object used throughout
	 * @param button Dispenser buttons passed one at a time
	 */
	public void drawDispensers(Graphics g, DispenserButton button) {
		if (button.getDispenser().getDispenserContents().isEmpty()){
			g.setColor(Color.gray);
		} else {
			g.setColor(Color.white);
		}
	//	System.out.println(button.getX1());
		g.fillRect(button.getX1(), button.getY1(), button.getWidth(), button.getHeight());
		g.setColor(Color.black);
		g.drawString(button.getDispenser().getName() + " $" + button.getDispenser().getPriceForPrinting() + " (" + button.getDispenser().getDispenserContents().size() + ")", button.getX1() + 10, button.getY1() + 15);
	}
	/**
	 * Draws the food information in bottom right
	 * @param g Graphics object used throughout
	 */
	public void drawFoodItem(Graphics g) {
		if (singleDispenser.getDispenserContents().isEmpty()) {
			g.setColor(Color.gray);
		} else {
			g.setColor(Color.white);
		}
		g.fillRect(400, 400, 300, 300);
		g.setColor(Color.black);
		//g.drawString(foodItem.getNutritionInfo(), 410, 410);
		String toDraw = foodItem.getName() + ",$" + singleDispenser.getPriceForPrinting() + "," + foodItem.getNutritionInfo();
		String[] drawableArray = toDraw.split(",");
		for (int i = 0; i < drawableArray.length; i++) {
			g.drawString(drawableArray[i], 415, 415 + 20*i);
		}
	}
	/**
	 * Draws the buy button
	 * @param g Graphics object used throughout
	 *
	 */
	public void drawBuyButton(Graphics g) {
		if (singleDispenser.getDispenserContents().isEmpty() || singleMachine.getMoneyIn() < singleDispenser.getPrice() || !singleMachine.isOn()) {
			g.setColor(Color.gray);
		} else {
			g.setColor(Color.white);
		}
		g.fillRect(buyButtons.get(0).getX1(), buyButtons.get(0).getY1(), buyButtons.get(0).getWidth(), buyButtons.get(0).getHeight());
		g.setColor(Color.black);
		g.drawString("BUY", buyButtons.get(0).getX1() + 25, buyButtons.get(0).getY1() + 25);
	}
	/**
	 * Draws a success message
	 * @param g Graphics object used throughout
	 */
	public void drawSuccess(Graphics g) {
		System.out.println("Drawing");
		g.setColor(Color.pink);
		g.drawString("TEST", 600, 600);
		Font temp = g.getFont();
		AffineTransform newT = new AffineTransform();
		newT.scale(5, 5);
		g.setColor(Color.green);
		g.setFont(g.getFont().deriveFont(newT));
		g.drawString("Success!!", 600, 600);
		
		g.setFont(temp);
	}
	/**
	 * Checks for left mouse clicks
	 * @param x the x coordinate mouse click
	 * @param y the y coordinate mouse click
	 */
	public void checkButtons(double x, double y) {
	//	System.out.println(machineButtons.size());
		if (machineButtons != null){
		for (int i = 0; i < machineButtons.size(); i++) {
			if (machineButtons.get(i).isInside(x, y)) {
				singleMachine = machines.get(i);
				setRandomMoneyButton();
				setRandomDispenserButton();
				dispensers = null;
				singleDispenser = null;
				foodItem = null;
				//System.out.println("YAY " + i);
				setDispenserButtons();
				setInsertButtons();
				return;
			}
		}
		}
		if (dispenserButtons != null){
		for (int i = 0; i < dispenserButtons.size(); i++) {
			if (dispenserButtons.get(i).isInside(x, y)) {
				singleDispenser = singleMachine.getDispensers().get(i);
				foodItem = null;
				//System.out.println("YAY " + i);
				setFoodItem();
				setBuyButton();
				
				return;
			}
		}
		}
		if (insertButtons != null && singleMachine.isOn()){
		for (int i = 0; i < insertButtons.size(); i++) {
			if (insertButtons.get(i).isInside(x, y)) {
				if (i == 0) {
					singleMachine.addMoney(1);
				} else if (i == 1) {
					singleMachine.addMoney(5);
				} else if (i == 2) {
					singleMachine.returnMoney();
				}
			}
		}
		}
		
		//buy
		if (buyButtons != null){
		for (int i = 0; i < buyButtons.size(); i++) {
//			System.out.println(i);
//			System.out.println(buyButtons.get(i).getX1() + "," + buyButtons.get(i).getY1());
//			System.out.println(buyButtons.get(i).getX2() + "," + buyButtons.get(i).getY2());
			if (buyButtons.get(i).isInside(x, y)) {
//				System.out.println("Got here");
//				System.out.println(buyButtons.get(i).getX1() + "," + buyButtons.get(i).getY1());
//				System.out.println(buyButtons.get(i).getX2() + "," + buyButtons.get(i).getY2());
				
				try {
					if (singleDispenser.canBuy() && singleDispenser.getPrice() <= singleMachine.getMoneyIn()) {
						if (singleMachine.purchase(singleMachine.getDispensers().indexOf(singleDispenser))) {
						//	System.out.println("YAY");
							successful = true;
						} else {
						//	System.out.println("BOO");
						}
						
					}
					return;
					
				} catch (InvalidChoiceException e) {
					System.err.println("You Cannot Buy This Item");
				}
			}
		}
		}
		//randomMachine, randomPower, randomMoney, randomDispenser;
		if (randomMachine != null) {
			if (randomMachine.isInside(x, y)) {
				singleMachine = machines.get(new Random().nextInt(machines.size()));
				setRandomMoneyButton();
				setRandomDispenserButton();
				dispensers = null;
				singleDispenser = null;
				foodItem = null;
				//System.out.println("YAY ");
				setDispenserButtons();
				setInsertButtons();
				return;
			}
		}
		if (randomPower != null) {
			if (randomPower.isInside(x, y)) {
				for (int i = 0; i < machines.size(); i++) {
					if (new Random().nextBoolean()) {
						machines.get(i).powerToggle();
					}
				}
				setRandomMoneyButton();
				setRandomDispenserButton();
				dispensers = null;
				singleDispenser = null;
				foodItem = null;
				return;
			}
			
		}
		if (randomMoney != null && singleMachine != null && singleMachine.isOn()) {
			if (randomMoney.isInside(x, y)) {
				singleMachine.setMoneyIn(new Random().nextInt(20));
				return;
			}
		}
		if (randomDispenser != null && singleMachine != null) {
			if (randomDispenser.isInside(x, y)) {
				singleDispenser = singleMachine.getDispensers().get(new Random().nextInt(singleMachine.getDispensers().size()));
				foodItem = null;
				//System.out.println("YAY ");
				setFoodItem();
				setBuyButton();
				
				return;
			}
		}
		if (exit.isInside(x, y)) {
			for (int i = 0; i < machines.size(); i++) {
				machines.get(i).writeContents();
			}
			saveState();
			System.exit(0);
		}
	}
	/**
	 * Checks for right mouse clicks
	 * @param x the x coordinate mouse click
	 * @param y the y coordinate mouse click
	 */
	public void checkPowerClick(double x, double y) {
		if (machineButtons != null){
			for (int i = 0; i < machineButtons.size(); i++) {
				if (machineButtons.get(i).isInside(x, y)) {
					machines.get(i).powerToggle();
					if (singleMachine != null) {
					setRandomMoneyButton();
					setRandomDispenserButton();
					}
					dispensers = null;
					singleDispenser = null;
					foodItem = null;
					if (singleMachine != null) {
					//System.out.println("YAY " + i);
					setDispenserButtons();
					setInsertButtons();
					}
					return;
				}
			}
			}
	}
	/**
	 * Checks for complete action of mouse button
	 * @param e the mouseEvent object
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	/**
	 * Checks for mouse button being depressed
	 * @param e the mouseEvent object
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		double x = e.getX();
		double y = e.getY();
		//System.out.println((int)x + "," + (int)y);
		if (e.getButton() == 1) {
			checkButtons(x, y);
		}
		if (e.getButton() == 3) {
			checkPowerClick(x, y);
		}
	}
	/**
	 * Checks for mouse button being released
	 * @param e the mouseEvent object
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		
	}
	/**
	 * Checks for mouse entering window
	 * @param e the mouseEvent object
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Checks for mouse leaving window
	 * @param e the mouseEvent object
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
package com.jpr242.one;

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
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class FancyButton extends JComponent implements MouseListener{
	
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
	private boolean successful = false;
	private long successTime;
	
	public FancyButton(){
		setMachineButtons();
//		System.out.println(machineButtons.get(1).getX1());
//		System.out.println(machineButtons.get(1).getX2());
//		System.out.println(machineButtons.get(1).getY1());
//		System.out.println(machineButtons.get(1).getY2());
		
//		setDispenserButtons();
	//	dispenserButtons = new ArrayList<Button>();
		this.addMouseListener(this);
		
	}
  
	public void paintComponent(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
//		g.setColor(Color.black);
//		g.drawString(System.currentTimeMillis() + "", 100, 100);
		if (false) {
			
		}
		
		if (foodItem != null) {
			drawFoodItem(g);
			drawBuyButton(g);
		}
			
		if(singleMachine != null) {
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
		
		if (successful) {
			successTime = System.nanoTime() + 5000;
			successful = false;
		}
		if (System.nanoTime() < successTime) {
			drawSuccess(g);
		}
		
		while (System.nanoTime() < lastTime){}
		
		repaint();
	}
  
  
	public static void main(String[] args) {
		machines = null;
		singleMachine = null;
		dispensers = null;
		singleDispenser = null;
		foodItem = null;
		container = openSave();
		
		machines = container.getMachines();
		
		System.out.print("Would you like to run the GUI (gui) or the Demo (demo)?: ");
		boolean gui = true;
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

		
		JFrame frame = new JFrame("Test Frame");
		frame.setSize(700, 700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FancyButton button = new FancyButton();  // Initialize the component.
		frame.getContentPane().add(button);      // Place the component on the application
		
		frame.setVisible(true);
	}
	
	public static void setFoodItem() {
		foodItem = singleDispenser.getDispenserContents().peekFirst();
	}
	
	
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
	
	public void drawMachines(Graphics g, MachineButton button) {
			if (!button.getMachine().isOn()){
				g.setColor(Color.gray);
			} else {
				g.setColor(Color.white);
			}
		//	System.out.println(button.getX1());
			g.fillRect(button.getX1(), button.getY1(), button.getWidth(), button.getHeight());
			g.setColor(Color.black);
			g.drawString("Machine " + button.getId(), button.getX1() + 10, button.getY1() + 15);
	}
	
	public void drawInsertButtons(Graphics g) {
		String names[] = {"Insert $1", "Insert $5", "Return Money"};
		for (int i = 0; i < insertButtons.size(); i++) {
			g.setColor(Color.white);
			g.fillRect(insertButtons.get(i).getX1(), insertButtons.get(i).getY1(), insertButtons.get(i).getWidth(), insertButtons.get(i).getHeight());
			g.setColor(Color.black);
			g.drawString(names[i], insertButtons.get(i).getX1() + 10, insertButtons.get(i).getY1() + 25);
		}
		g.setColor(Color.white);
		g.drawString("$" + df.format(insertButtons.get(0).getMachine().getMoneyIn()), 5, 450);
	}
	
	public void drawDispensers(Graphics g, DispenserButton button) {
		if (button.getDispenser().getDispenserContents().isEmpty()){
			g.setColor(Color.gray);
		} else {
			g.setColor(Color.white);
		}
	//	System.out.println(button.getX1());
		g.fillRect(button.getX1(), button.getY1(), button.getWidth(), button.getHeight());
		g.setColor(Color.black);
		g.drawString(button.getDispenser().getName() + " $" + button.getDispenser().getPriceForPrinting(), button.getX1() + 10, button.getY1() + 15);
	}
	
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
	
	public void drawBuyButton(Graphics g) {
		if (singleDispenser.getDispenserContents().isEmpty() || singleMachine.getMoneyIn() < singleDispenser.getPrice()) {
			g.setColor(Color.gray);
		} else {
			g.setColor(Color.white);
		}
		g.fillRect(buyButtons.get(0).getX1(), buyButtons.get(0).getY1(), buyButtons.get(0).getWidth(), buyButtons.get(0).getHeight());
		g.setColor(Color.black);
		g.drawString("BUY", buyButtons.get(0).getX1() + 25, buyButtons.get(0).getY1() + 25);
	}
	
	public void drawSuccess(Graphics g) {
		System.out.println("Drawing");
		g.setColor(Color.pink);
		g.drawString("TEST", 500, 500);
		Font temp = g.getFont();
		AffineTransform newT = new AffineTransform();
		newT.scale(5, 5);
		g.setColor(Color.green);
		g.setFont(g.getFont().deriveFont(newT));
		g.drawString("Success!!", 600, 600);
		
		g.setFont(temp);
	}
	
	public void checkButtons(double x, double y) {
		System.out.println(machineButtons.size());
		for (int i = 0; i < machineButtons.size(); i++) {
			if (machineButtons.get(i).isInside(x, y)) {
				singleMachine = machines.get(i);
				dispensers = null;
				singleDispenser = null;
				foodItem = null;
				System.out.println("YAY " + i);
				setDispenserButtons();
				setInsertButtons();
				return;
			}
		}
		for (int i = 0; i < dispenserButtons.size(); i++) {
			if (dispenserButtons.get(i).isInside(x, y)) {
				singleDispenser = singleMachine.getDispensers().get(i);
				foodItem = null;
				System.out.println("YAY " + i);
				setFoodItem();
				setBuyButton();
				
				return;
			}
		}
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
		
		//buy
		if (buyButtons != null){
		for (int i = 0; i < buyButtons.size(); i++) {
			System.out.println(i);
			System.out.println(buyButtons.get(i).getX1() + "," + buyButtons.get(i).getY1());
			System.out.println(buyButtons.get(i).getX2() + "," + buyButtons.get(i).getY2());
			if (buyButtons.get(i).isInside(x, y)) {
				System.out.println("Got here");
				System.out.println(buyButtons.get(i).getX1() + "," + buyButtons.get(i).getY1());
				System.out.println(buyButtons.get(i).getX2() + "," + buyButtons.get(i).getY2());
				
				try {
					if (singleDispenser.canBuy() && singleDispenser.getPrice() <= singleMachine.getMoneyIn()) {
						if (singleMachine.purchase(singleMachine.getDispensers().indexOf(singleDispenser))) {
							System.out.println("YAY");
							successful = true;
						} else {
							System.out.println("BOO");
						}
						
					}
					return;
					
				} catch (InvalidChoiceException e) {
					System.err.println("You Cannot Buy This Item");
				}
			}
		}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		double x = e.getX();
		double y = e.getY();
		System.out.println((int)x + "," + (int)y);
		if (e.getButton() == 1) {
			checkButtons(x, y);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
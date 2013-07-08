package com.jpr242.one;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JimsCanvas extends JPanel implements MouseListener{

	private static final long serialVersionUID = 1L;


	private int width;
	private int height;
	
	private int squaresX;
	private int squaresY;
	private int offset;

	private long lastTime;
	
	private GUIDriver field;

	
	private BufferedImage soldierPic[] = new BufferedImage[7], zombPic[] = new BufferedImage[4], mines[] = new BufferedImage[2], grenade, bag, wall, num[] = new BufferedImage[10];

	
public JimsCanvas(int w, int h, GUIDriver field, int widthOffset) {
		offset = widthOffset;
		width = w;
		height = h;
		field = field;
		lastTime = -1L;
		try {
			for (int i = 0; i < mines.length; i++) {
				mines[i] = ImageIO.read(new File("Mine" + i + ".png"));
			}
			grenade = ImageIO.read(new File("Grenade.png"));
			wall = ImageIO.read(new File("Wall.png"));
			
			
			
		} catch (Exception e) {
			System.err.println("Images not found in project directory!");
		}
	}
	
	public JimsCanvas(int w, int h, GUIDriver field) {
		
		width = w;
		height = h;
		field = field;
		lastTime = -1L;
		try {
			for (int i = 0; i < soldierPic.length; i++) {
				soldierPic[i] = ImageIO.read(new File("Soldier" + i + ".png"));
			}
			for (int i = 0; i < zombPic.length; i++) {
				zombPic[i] = ImageIO.read(new File("Zombie" + i + ".png"));
			}
			for (int i = 0; i < mines.length; i++) {
				mines[i] = ImageIO.read(new File("Mine" + i + ".png"));
			}
			for (int i = 0; i < num.length; i++) {
				num[i] = ImageIO.read(new File(i+".png"));
			}
			grenade = ImageIO.read(new File("Grenade.png"));
			wall = ImageIO.read(new File("Wall.png"));
			bag = ImageIO.read(new File("Bag.png"));
			
		} catch (Exception e) {
			System.err.println("Images not found in project directory!");
		}
	}

	
	public void setupAndDisplay(boolean heh) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JScrollPane(this));
		frame.setSize(width, height);
		frame.setLocation(offset, 0);
		frame.setVisible(true);
	}

	public void setupAndDisplay() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JScrollPane(this));
		frame.setSize(width, height);
		frame.setLocation(0, 0);
		frame.setVisible(true);
		this.addMouseListener(this);
	//	this.addMouseMotionListener(this);
		//this.addKeyListener(this);
	//	this.field.setDimensions(soldierPic[0].getWidth(), soldierPic[0].getHeight(), zombPic[0].getWidth(), zombPic[0].getHeight());
	}

	protected void paintComponent(Graphics graphics) {
		//boolean firstCall = (lastTime == -1L);
		long elapsedTime = System.nanoTime() + 500;
		//lastTime = System.nanoTime();
		graphics.setColor(Color.gray);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.white);
		field.draw((Graphics2D) graphics);
		while (System.nanoTime() < elapsedTime) {}
		repaint();
	}
	
	public void drawNumber(Graphics2D graphics, int x, int y, double size1, double size2, int number){
		Image temp = num[number];
		AffineTransform old = new AffineTransform();
		
		old.translate(x, y);
		old.scale(size1, size2);
		old.translate(-temp.getWidth(null)/2, -temp.getHeight(null));
		
		graphics.drawImage(temp, old, null);
	}
	
	
	public void drawExplosion(Graphics2D graphics, int x, int y, int size, double fractionOfRed) {
		Color gray = Color.gray;
		Color red = Color.red;
		
		int blueDif = red.getBlue()-gray.getBlue();
		int redDif = red.getRed()-gray.getRed();
		int greenDif = red.getGreen()-gray.getGreen();
		
		blueDif *= fractionOfRed;
		redDif *= fractionOfRed;
		greenDif *= fractionOfRed;
		
		
		
		graphics.setColor(new Color(redDif + gray.getRed(), greenDif + gray.getGreen(), blueDif + gray.getBlue()));
		graphics.fillOval(x-size/2, y-size/2, size, size);
	}
	
	public void drawBag(Graphics2D graphics, int x, int y, int scaleX, int scaleY) {
		AffineTransform old = new AffineTransform();
		old.translate(x, y);
		
		old.scale(scaleX, scaleY);
		old.translate(-bag.getWidth(null)/2, -bag.getHeight(null)/2);
		
		
		graphics.drawImage(bag, old, null);
	/*	graphics.setColor(Color.pink);
		graphics.fillOval(x, y, 10, 10);*/
	}
	
	public void drawWall(Graphics2D graphics, int x, int y) {
		graphics.drawImage(wall, null, x, y);
	}
	
	public void drawMine(Graphics2D graphics, int x, int y, float time, boolean test, int scale) {
		BufferedImage temp = mines[0];
		if(time%1.5 < 1.25 || test) {
			temp = mines[1];
		}
		AffineTransform old = new AffineTransform();
		old.translate(x, y);
		
		old.scale(scale, scale);
		old.translate(-temp.getWidth(null)/2, -temp.getHeight(null)/2);
		graphics.drawImage(temp, old, null);
	}
	
	public void drawGrenade(Graphics2D graphics, int x, int y, double rotation, int scale) {
		AffineTransform old = new AffineTransform();
		
		//old.scale(scale, scale);
		old.translate(x, y);
		old.rotate(rotation);
		
		old.scale(scale, scale);
		old.translate(-grenade.getWidth(null)/2, -grenade.getHeight(null)/2);
		
		graphics.drawImage(grenade, old, null);
		
	}
	
	

	public void drawZombie(Graphics2D graphics, int x, int y, double rotation, float time, double size) {
		//System.out.println("Drawn");
		//System.out.print(graphics);
		//graphics.drawImage(zombPic[0], null, x, y);
		AffineTransform old = new AffineTransform();
		Image temp = zombPic[0];
		
		int j = 2;
		if (time%.9 < .5) {
			j = 1;
		}
	
		temp = zombPic[j];
	
	old.translate(x, y);
		old.rotate(rotation);
		old.scale(size, size);
		old.translate(-temp.getWidth(null)/2, -temp.getHeight(null)/2);
		
		graphics.drawImage(temp, old, null);
		
	}
	
	public void drawHero(Graphics2D graphics, int x, int y, double rotation, float time, boolean moving, String action, float actionTime) {
		
		AffineTransform old = new AffineTransform();
		Image temp = soldierPic[0];
		
		int j = 2;
		if (time%.5 < .25) {
			j = 1;
		}
		
		temp = soldierPic[j];
		
		if(!moving) {
			temp = soldierPic[0];
		}
		
		if(action.equalsIgnoreCase("grenade")) {
			rotation -= Math.PI;
			if (actionTime%.4 > .3) {
				temp = soldierPic[3];
			} else if (actionTime%.4 > .2) {
				temp = soldierPic[4];
			} else if (actionTime%.4 > .1) {
				temp = soldierPic[5];
			} else {
				temp = soldierPic[6];
			}
		}
		
		old.translate(x, y);
		old.rotate(rotation);
		old.translate(-temp.getWidth(null)/2, -temp.getHeight(null)/2);

		graphics.drawImage(temp, old, null);
		//graphics.dispose();

	}

	public void drawVendingMachine(Graphics graphics, int num, boolean on) {
		int x = (num%3)*200;
		int y = (num < 3 ? 50 : 350);
		int width = 190;
		int height = 290;
		Color toDraw = (on ? Color.gray : Color.black);
		graphics.setColor(toDraw);
		graphics.fillRect(x, y, width, height);
	}
	
	public void drawExitButton(Graphics graphics) {
		graphics.setColor(Color.yellow);
		graphics.fillRect(0,0, 100, 100);
		graphics.setColor(Color.green);
		graphics.drawString("Exit", 50, 50);
	}

	/**
	 * Whenever the mouse is moved on the canvas, this method gets called.
	 */
	public void mouseMoved(MouseEvent e) {
	//	field.mouseAction((float) e.getX(), (float) e.getY(), -1, false);
	}

	/**
	 * Whenever the mouse is clicked on the Field, this method gets
	 * called.
	 */
	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		field
		.mouseAction((float) e.getX(), (float) e.getY(), e.getButton(), true);
	}

	public void mouseReleased(MouseEvent e) {
		field
		.mouseAction((float) e.getX(), (float) e.getY(), e.getButton(), false);
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void drawString(Graphics2D graphics, String toDraw, int x, int y){
		graphics.drawString(toDraw, x, y);
	}

}

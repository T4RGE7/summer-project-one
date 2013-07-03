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

public class JimsCanvas extends JPanel implements MouseListener,
		MouseMotionListener{

	private static final long serialVersionUID = 1L;


	private int width;
	private int height;
	
	private int squaresX;
	private int squaresY;
	private int offset;

	private long lastTime;
	
	private GUIDriver field;
	
public JimsCanvas(int w, int h, GUIDriver field, int widthOffset) {
		
	offset = widthOffset;
		width = w;
		height = h;
		field = field;
		lastTime = -1L;
		
	}
	
	public JimsCanvas(int w, int h, GUIDriver field) {
		
		width = w;
		height = h;
		field = field;
		lastTime = -1L;

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
		this.addMouseMotionListener(this);
	}

	protected void paintComponent(Graphics graphics) {
		boolean firstCall = (lastTime == -1L);
		long elapsedTime = System.nanoTime() - lastTime;
		lastTime = System.nanoTime();
		graphics.setColor(Color.cyan);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.white);
		field.draw((Graphics2D) graphics, (firstCall ? 0.0f
				: (float) elapsedTime / 1e9f));
		repaint();
	}

	/**
	 * Whenever the mouse is moved on the TurkeyField, this method gets called.
	 */
	public void mouseMoved(MouseEvent e) {
		field.mouseAction((float) e.getX(), (float) e.getY(), -1, false);
	}

	/**
	 * Whenever the mouse is clicked on the TurkeyField, this method gets
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

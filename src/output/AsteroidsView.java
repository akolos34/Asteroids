/**
 * PROGRAM NAME: AsteroidsView.java
 * AUTHOR(S): Kolos, Andrew
 * 			  Hurley, James
 * DATE CREATED: Nov. 11, 2012
 * LAST MODIFIED: Nov. 25, 2012
 * PURPOSE: This class is instantiated to be a view object
 *          for an Asteroids game. It will receive data from
 *          the main Model class and output it to an Applet
 *          window.
 * NOTES: extends Applet
 * 		  implements IOutput
 */

package output;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import main.v2;
import sprites.StarryBackground;
import sprites.*;

public class AsteroidsView extends Canvas implements IOutput, Runnable {

	private static final long serialVersionUID = 1L; // eclipse wants me to

	BufferedImage bimg; // BufferedImage for the output

	Image img; // The primary image to be displayed to the user

	Graphics dbg; // Graphics Object used in rendering.

	public boolean running = false;

	Thread thread;
	
	BufferStrategy bs;
	
	int numStars;
	StarryBackground stars;
	
	v2 m;
	
	Ship ship;
	
	public void start() {
		
		numStars = getWidth() * getHeight() / 1000; // how many stars
		stars = new StarryBackground(numStars, getSize());
		
		if (running) {
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	/*
	 * @see java.applet.Applet#init() Sets up the output.
	 */
	public void init() {
		
		
		start();		
		
	}

	/*
	 * Changes the dimensions of the OUTPUT frame
	 */
	public void setSize(int width, int height) {
		
	}


	/*
	 * Gets the graphics being used
	 
	public Graphics getGraphics() {

		return img.getGraphics();
	}
	 */



	/*
	 * @see output.IOutput#getImage()
	 */
	public Image getImage() {
		return img;
	}

	@Override
	public void run() {
		while (running) {
			render();
			try {
				thread.sleep(16);
			} catch (Exception e) {}
		}
	}

	public void render() {
		
		createBufferStrategy(2);
		bs = getBufferStrategy();
		
		setBackground(Color.BLACK);
		Graphics g = bs.getDrawGraphics();
		
		// draw stars
		g.setColor(Color.WHITE);
		for (int i = 0; i < numStars; i++) {
			g.drawLine(stars.getXAtIndex(i), stars.getYAtIndex(i),
					stars.getXAtIndex(i), stars.getYAtIndex(i));
		}
		
		// draw level counter
		g.setColor(Color.GREEN);
		g.drawString("Level " + m.level, 20, 20);
		g.drawString("Score: " + m.score, 400, 20);
		
		// draw player missles
		for (int i = 0; i < m.numMissles; i++)
			m.missles[i].draw(g);
		
		// draw asteroids
		for (int i = 0; i < m.numAsteroids; i++)
			m.asteroids[i].draw(g);
		
		// draw player
		ship = m.ship;
		ship.draw(g);
		
		bs.show();
		update(g);
		bs.dispose();
	}
	
	/*
	 * @see output.IOutput#update()
	 */
	@Override
	public void update(Graphics g) {
		
		if (img == null) {
			img = createImage(getWidth(), getHeight());
			dbg = img.getGraphics();
			return;
		}
				
		paint(dbg);
				
		g.drawImage(img, 0, 0, null);
		
	}

	@Override
	public void update(v2 m) {
		this.m = m;
	}

}

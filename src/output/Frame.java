package output;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;


/* Frame receives finalized output instructions from OutputManager
 * and updates it to the applet
 */
public class Frame extends Applet implements Runnable {
	
	Thread thread;
	long startTime, endTime, framePeriod;
	
	Dimension dim; // stores the size of the back buffer
	Image img;	// back buffer object
	Graphics g; // used to draw on the back buffer;
	
	/*
	 * @see java.applet.Applet#init()
	 * is called when the Applet is first created
	 * 
	 */
	public void init() {
		resize(500, 500); // resizes applet to desired size
		startTime = 0;
		endTime = 0;
		framePeriod=25;
		thread = new Thread(this);
		thread.start(); // start the thread running
	}
	
	/*
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paint(Graphics gfx) {
		
		
		AsteroidsView out = new AsteroidsView();
		
		gfx.setColor(Color.red); // set color for testing
		gfx.fillOval(0, 0, 50, 50); // draw a red circle for debug purposes\
		
	}
	
	/*
	 * @see java.awt.Container#update(java.awt.Graphics)
	 */
	public void update(Graphics gfx) {
		paint(gfx); // call paint without clearing the screen
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}

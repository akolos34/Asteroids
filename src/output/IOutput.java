/**
 * PROGRAM NAME: IView.java
 * AUTHOR(S): Kolos, Andrew
 * 			  Hurley, James
 * DATE CREATED: Nov. 11, 2012
 * LAST MODIFIED: Nov. 19, 2012
 * PURPOSE: This interface will be used as in output interface for the
 * 			Asteroids game. It will contain methods for handling rendered
 * 			data sent from the model to a class that implements IView.
 * 			The class will then output the data to the user.
 * NOTES: N/A
 */
package output;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import main.v2;

public interface IOutput extends Runnable {
	
	
	/*
	 * Updates the data for output before printing it to the device
	 */
	public void update(v2 manager);
	
	/*
	 * Changes the dimensions of the display window.
	 */
	public void setSize(int w, int h);

	/* 
	 * Paints the data to the output device
	 */
	public void paint(Graphics gfx);
	
	/*
	 * Gets the Graphics object used in the output
	 */
	public Graphics getGraphics();
	
	/*
	 * Gets the current Image of the screen
	 */
	public Image getImage();
	
}

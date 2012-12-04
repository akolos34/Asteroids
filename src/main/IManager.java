/**
 * PROGRAM NAME: IManager.java
 * AUTHOR(S): Kolos, Andrew
 * 			  Hurley, James
 * DATE CREATED: Nov. 27, 2012
 * LAST MODIFIED: Nov. 27, 2012
 * PURPOSE: This interface will be used as a Model interface
 * 			for the Asteroids game. It contains methods 
 * 			necessary for running a instance of the game.
 * NOTES: extends Runnable.
 */

package main;

public interface IManager extends Runnable {
	
	/*
	 * starts the game
	 */
	public void start();
		
	
	/*
	 * First method called upon starting. Initializes
	 * all necessary variables and objects.
	 */
	public void init();
	
	
	/*
	 * Updates field information to be used in calculations
	 * and output. It will call other class methods to update
	 * of the objects.
	 */
	public void update();
	
	/*
	 * Main run loop.
	 */
	/**
	void run(){}
	 */
	
	
}

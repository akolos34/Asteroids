/**
 * PROGRAM NAME: IController.java
 * AUTHOR(S): Kolos, Andrew
 * 			  Hurley, James
 * DATE CREATED: Nov. 11, 2012
 * LAST MODIFIED: Nov. 19, 2012
 * PURPOSE: This interface will be used as in input interface for the
 * 			Asteroids game. It will contain methods for handling button
 * 			presses from an input device and mapping these input actions
 * 			to actions in a game. Input classes that implement IController
 * 			will manipulate the process model for game or program.
 * NOTES: extends KeyListener.
 */


package input;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import main.IManager;
import main.v2;


public interface IController extends KeyListener {
	// 'key' is interchangable for 'button,' depending on the input device
	// However, if using another input device, the controller class for 
	// the other device will have to simulate key presses (KeyEvent)
	
	/*
	 * Sets up default GameActions and maps
	 */
	public void init();
	
	/*
	 * Maps a GameAction to a specific key. The key codes are
	 * defined in java.awt.KeyEvent. If the key already has
	 * a GameAction mapped to it, the new GameAction overwrites
	 * it.
	*/
	public void mapToButton(GameAction gameAction, int keyCode);
	
	/*
	 * Clears all mapped key/button actions to this GameAction.
	 */
	public void clearMap(GameAction gameAction);
	
	/*
	 * Returns what GameAction is mapped to the key.
	 */
	public GameAction getKeyAction(KeyEvent e);
	
	/* 
	 * Returns a List of names of keys mapped to this GameAction
	 * Each entry in the List is a String.
	 */
	public List getMaps(GameAction e);
	

	/*
	 * Resets all GameActions so they appear like they haven't
	 * been pressed.
	 */
	public void resetAllGameActions();
	
	/*
	 * Manipulate() is called by the KeyPressed() or keyReleased() methods from
	 * the KeyListener interface. This will be the method that manipulates the
	 * Model (MVC manner).
	 */
	public void Manipulate(v2 manager);
	
}
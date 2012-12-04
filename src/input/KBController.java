/**
 * PROGRAM NAME: KBController.java
 * AUTHOR(S): Kolos, Andrew
 * 			  Hurley, James
 * DATE CREATED: Nov. 11, 2012
 * LAST MODIFIED: Nov. 25, 2012
 * PURPOSE: This class is instantiated to be a controller object
 *          for an Asteroids game. It contains GameActions for each
 *          button that controls the game. It responds to key presses
 *          and manipulates the game based on the action the user takes
 *          and responds to it accordingly.
 *          Key Mappings can be adjusted and are stored in an array
 *          of key codes to allow for more flexibility, such as the
 *          ability to have multiple Keys bound to the same Action.
 * NOTES: implements IController which extends KeyListener
 */

package input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import main.IManager;
import main.v2;

public class KBController implements IController {

	/*
	 * GameActions (player abilities)
	 */

	GameAction forwardThruster = new GameAction("forwardThruster");
	GameAction backwardThruster = new GameAction("backwardThruster");
	GameAction fire = new GameAction("fire");
	GameAction warp = new GameAction("warp");
	GameAction rotateLeft = new GameAction("rotateLeft");
	GameAction rotateRight = new GameAction("rotateRight");

	public GameAction pause = new GameAction("pause",
			GameAction.DETECT_INITIAL_PRESS_ONLY);
	public GameAction start = new GameAction("start",
			GameAction.DETECT_INITIAL_PRESS_ONLY);
	public GameAction exit = new GameAction("exit",
			GameAction.DETECT_INITIAL_PRESS_ONLY);
	
	/*
	 * Sets default keybinds
	 */
	@Override
	public void init() {

		mapToKey(forwardThruster, KeyEvent.VK_UP);
		mapToKey(backwardThruster, KeyEvent.VK_DOWN);
		mapToKey(fire, KeyEvent.VK_SPACE);
		mapToKey(warp, KeyEvent.VK_W);
		mapToKey(rotateLeft, KeyEvent.VK_LEFT);
		mapToKey(rotateRight, KeyEvent.VK_RIGHT);
		mapToKey(pause, KeyEvent.VK_P);
		mapToKey(start, KeyEvent.VK_S);
		mapToKey(exit, KeyEvent.VK_ESCAPE);
	}

	/*
	 * key codes are defined in java.awt.KeyEvent. according to Google, most
	 * codes are less than 600.
	 */

	public KBController(Component comp) {
		//register key listener
		comp.addKeyListener(this);
		comp.setFocusTraversalKeysEnabled(false);
		
		init(); // set up default keybinds
	}

	int numOfActiveKeys; // number of buttons used to control the player

	private static final int NUM_KEY_CODES = 600;

	/*
	 * Since the GameAction class already has a name and associated key code we
	 * don't need to make parallel arrays for storing the key binds. Instead, we
	 * just have one aray, and there is an index for each key-code. When we want
	 * to map a key, we assign the value of the GameAction at the index that is
	 * the key-code. By doing this, any given number of keys can be mapped to
	 * perform the same behavior.
	 */
	private GameAction[] keyActions = new GameAction[NUM_KEY_CODES];

	/*
	 * Maps a GameAction to a specific key. The key codes are defined in
	 * java.awt.KeyEvent. If the key already has a GameAction mapped to it, the
	 * new GameAction overwrites it.
	 */
	public void mapToKey(GameAction gameAction, int keyCode) {
		keyActions[keyCode] = gameAction;
	}

	/*
	 * Clears all mapped key actions to this GameAction.
	 */
	public void clearMap(GameAction gameAction) {
		// clear the array
		for (int i = 0; i < keyActions.length; i++) {
			if (keyActions[i] == gameAction) {
				keyActions[i] = null;
			}
		}

		gameAction.reset();

	}

	/*
	 * Gets a List of names of keys mapped to this GameAction Each entry in the
	 * List is a String.
	 */
	public List getMaps(GameAction gCode) {
		ArrayList list = new ArrayList();

		for (int i = 0; i < keyActions.length; i++) {
			if (keyActions[i] == gCode) {
				list.add(getKeyName(i));
			}
		}

		return list; // returns the map

	}

	/*
	 * Resets all GameActions so they appear like they haven't been pressed.
	 */
	public void resetAllGameActions() {
		for (int i = 0; i < keyActions.length; i++) {
			if (keyActions[i] != null) {
				keyActions[i].reset();
			}
		}
	}

	/*
	 * Gets the name of a key code.
	 */
	public static String getKeyName(int keyCode) {
		return KeyEvent.getKeyText(keyCode);
	}

	/*
	 * Returns the GameAction associated mapped to the KeyCode Returns null if
	 * no GameAction is mapped to that key
	 */
	public GameAction getKeyAction(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode < keyActions.length) {
			return keyActions[keyCode];
		} else {
			return null;
		}
	}
	
	// returns the GameAction bound to that key
	public GameAction getKeyAction(int keyCode) {
		if (keyCode < keyActions.length) {
			return keyActions[keyCode];
		}
		else {
			return null;
		}
	}

	/*
	 * Presses a GameAction if it exists within the array.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		GameAction gameAction = getKeyAction(e);
		if (gameAction != null) {
			keyActions[e.getKeyCode()].press();
		}
		
		int keyCode = e.getKeyCode();
		System.out.println("Pressed: " + e.getKeyText(keyCode));
		
		// make sure the key is not processed for anything else
		e.consume();
	}

	/*
	 * Signal the GameAction to be released
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		GameAction gameAction = getKeyAction(e);
		if (gameAction != null) {
			keyActions[e.getKeyCode()].release();
		}
		// make sure the key isn't processed for anything else
		e.consume();
	}

	/*
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		e.consume();
	}

	/*
	 * Maps a GameAction to a specific key. The key codes are defined in
	 * java.awt.KeyEvent. If the key already has a GameAction mapped to it, the
	 * new GameAction overwrites it.
	 */
	@Override
	public void mapToButton(GameAction gameAction, int keyCode) {
		keyActions[keyCode] = gameAction;
	}

	@Override
	public void Manipulate(v2 m) {
		// pressed
		if (pause.isPressed()) {
			// these first two lines allow the asteroids to move
			// while the player chooses when to enter the game.
			// This happens when the player is starting a new ship
			if (!m.ship.isActive() && !m.paused)
				m.ship.setActive(true);
			else {
				m.paused = !m.paused;
				if (m.paused)
					m.ship.setActive(false);
				else
					m.ship.setActive(true);
			}
		}
		
		if (forwardThruster.isPressed()){
			m.ship.setAccelerating(true);
			System.out.println("hi");}
		else 
			m.ship.setAccelerating(false);
		if (rotateLeft.isPressed())
			m.ship.setTurningLeft(true);
		else
			m.ship.setTurningLeft(false);
			
		if (rotateRight.isPressed())
			m.ship.setTurningRight(true);
		else
			m.ship.setTurningRight(false);
		
		// not pressed
	}
}

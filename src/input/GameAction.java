/**
 * PROGRAM NAME: GameAction.java
 * AUTHOR(S): Kolos, Andrew
 * 			  Hurley, James
 * DATE CREATED: Nov. 13, 2012
 * LAST MODIFIED: Nov. 20, 2012
 * PURPOSE: When instantiated, a GameAction object represents
 * 			a button on an input device. It consists of a name
 * 			for the GameAction object itself and a state of the
 * 			button (i.e. pressed, released, waiting to be pressed)
 * USE:		Make a GameAction to represent an action a player can
 * 			take in a game (i.e. GameAction jump =
 * 			new GameAction("Jump", 5);
 */
package input;

public class GameAction {

	/*
	 * Normal behavior. The isPressed() method returns true as long as the key
	 * is held down.
	 */
	public static final int NORMAL = 0;

	/*
	 * Initial press behavior. The isPressed() method returns true only after
	 * the key is first pressed, and not again until the key is released and
	 * pressed again.
	 */
	public static final int DETECT_INITIAL_PRESS_ONLY = 1;

	/*
	 * Different states of a button.
	 */
	private static final int STATE_RELEASED = 0;
	private static final int STATE_PRESSED = 1;
	private static final int STATE_WAITING_FOR_RELEASE = 2;

	private String name; // the name of the GameAction
	private int behavior; // enum for the behavior of the action

	private int state; // the current state of the button (pressed, released)
	private int amount; // # of times a button was pressed in a certain time
						// period
						// (since last reset)

	/*
	 * Create a new GameAction with the NORMAL behavior.
	 */
	public GameAction(String name) {
		this(name, NORMAL);
	}

	/*
	 * Create a new GameAction with the specified behavior and name.
	 */
	public GameAction(String name, int behavior) {
		this.name = name;
		this.behavior = behavior;
		reset();
	}

	/*
	 * Gets the name of this GameAction
	 */
	public String getName() {
		return name;
	}

	/*
	 * Resets this GameAction so that it appears like it hasn't been pressed.
	 */
	public void reset() {
		state = STATE_RELEASED;
		amount = 0;
	}

	/*
	 * Taps this GameAction. Same as calling press() followed by release().
	 */

	public void tap() {
		press();
		release();
	}

	/*
	 * Signals that the key was pressed.
	 */
	public synchronized void press() {
		press(1);
	}

	/*
	 * Signals that the key was pressed a specific # of times
	 */
	public synchronized void press(int amount) {
		if (state != STATE_WAITING_FOR_RELEASE) {
			this.amount += amount;
			state = STATE_PRESSED;
		}
	}

	/*
	 * Signals that the key was released
	 */
	public synchronized void release() {
		state = STATE_RELEASED;
	}

	/*
	 * Returns whether the key was pressed or not since last checked.
	 */
	public synchronized boolean isPressed() {
		return (getAmount() != 0);
	}

	/*
	 * This is the number of times the key was pressed since it was last
	 * checked.
	 */
	public synchronized int getAmount() {
		int returnVal = amount;

		if (returnVal != 0) {
			if (state == STATE_RELEASED) {
				amount = 0;
			} else if (behavior == DETECT_INITIAL_PRESS_ONLY) {
				state = STATE_WAITING_FOR_RELEASE;
				amount = 0;
			}
		}
		return returnVal;
	}
}
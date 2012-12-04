/**
 * PROGRAM NAME: AsteroidsGameManager.java
 * AUTHOR(S): Kolos, Andrew
 * 			  Hurley, James
 * DATE CREATED: Nov. 11, 2012
 * LAST MODIFIED: Nov. 25, 2012
 * PURPOSE: This class runs the Asteroids game, processes data between
 * 			the Controller and View, and contains the game data.
 * 			The objects in the game will be in this class.
 * NOTES: implements Runnable
 * 		  this is the main class
 */

package main;

import input.GameAction;
import input.KBController;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import output.AsteroidsView;
import sprites.Missle;
import sprites.Ship;
import util.AsteroidsGameConfig;
import util.GameTimer;
import util.ScoreCounter;

public class AsteroidsGameManager implements IManager {

	Thread thread, loadThread; // Threads
	
	private AsteroidsView out;
	private KBController in;
	private AsteroidsGameConfig config;
	private GameTimer timer;

	/*
	 * GameActions for this game
	 */

	Dimension gdim; // represents the dimensions of the game

	boolean loaded = false;
	boolean running;
	boolean playing;
	boolean paused;

	// Game data
	ScoreCounter score;
	ScoreCounter highScore;
	ScoreCounter extraShipScore;
	ScoreCounter newUfoScore;

	// Sprite objects
	Ship ship;
	Missle[] missles = new Missle[config.max_missles];
	// Asteroid[] asteroids = new Asteroid[config.max_asteroids];

	// Ship data
	int shipsRemaining; // # of ships left in game, including current ship
	int shipCounter; // timer counter for ship explosion
	int warpCounter; // timer counter for warp

	// UFO data

	// Missle data
	int photonIndex; // Index to next available photon sprite
	long photonTime; // Time value used to keep firing rate constant

	// Asteroid data
	boolean[] asteroidIsSmall = new boolean[config.max_asteroids];
	int asteroidsCounter;
	double asteroidsSpeed;
	int asteroidsRemaining;

	// Explosion data

	// Sound??

	// Generate background of stars

	
	public void init() {

		// set up input manager
		

		// set up game config
		config = new AsteroidsGameConfig();
		
		// load resources
		out = new AsteroidsView();
		out.setVisible(true);
		
		// load field
		shipsRemaining = config.max_ships;
		asteroidsSpeed = config.min_asteroid_speed;
		//extraShipScore.setScore(config.newShipPoints);
		// new UFO score

		// initialize ship and everthing else

		// start timer
		timer = new GameTimer(config.desiredFPS);
		timer.start();
		running = true;
		paused = false;
		thread = new Thread(this);
		thread.start();
		//gdim.setSize(500, 500);
	}

	// private void checkInput(long elapsedTime) {	}

	public void endGame() {
		playing = false;
		// stop everything
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		if (!loaded && loadThread == null) {
			loadThread = new Thread(this);
			loadThread.start();
		}
	}

	/*
	 * Signals the game loop that it's time to quit
	 */
	@SuppressWarnings("deprecation")
	public void stop() {
		running = false;
		if (thread != null) {
			thread.stop();
			thread = null;
		}
		if (loadThread != null) {
			loadThread.stop();
			loadThread = null;
		}
		// stop everything
	}

	/*
	 * Runs through until stop() is called.
	 */
	public void run() {

		int i, j; // make life easier

		long startTime = System.currentTimeMillis();
		long currTime = startTime;

		/*
		 * Run thread for loading stuff, like sounds
		 * 
		 * if (!loaded && Thread.currentTread() == loadThread) { loadSounds();
		 * loaded = true; loadThread.stop(); }
		 */

		// main loop

		while (running) {
			long elapsedTime = System.currentTimeMillis() - currTime;
			currTime += elapsedTime;

			if (!paused) {
				// Update and process everything
				update(elapsedTime);

				//scoreUpdate();

				// updateScreen(); [repaint]

			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException ex) {
			}
		}
	}
	/*
	private void scoreUpdate() {
		if (score.getScore() > highScore.getScore())
			highScore = score;
		if (score.getScore() > extraShipScore.getScore()) {
			extraShipScore.add(config.newShipPoints);
			shipsRemaining++;
		}
		// UFO here later
	}
	*/
	/*
	 * Updates the state of the game/animation based on the amount of elapsed
	 * time that has passed.
	 */
	public void update(long elapsedTime) {

		if (!playing)
			return;

		/*
		 * Ship is exploding, advane the countdown or create a new ship if it is
		 * done exploding. The new ship ship is added as though it were in
		 * hyperspace. If there are no more ships left, end game.
		 */
		else {
			if (--shipCounter <= 0) {
				if (shipsRemaining > 0) {
					// initShip();
				//	ship.warpCounter = config.hyper_count;
				} else
					endGame();
			}
		}

	}

	

	public void stopShip() {

	}
	
	public void Draw() {
		out.show();
	}

	@Override
	public void update() {
	}

}

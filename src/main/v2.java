package main;

import input.IController;
import input.KBController;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import output.AbstractView;
import output.AsteroidsView;
import sprites.Asteroid;
import sprites.Missle;
import sprites.Ship;
import util.FunBox;

public class v2 extends JFrame implements IManager {
	private static final long serialVersionUID = 1L;	
	
	Thread thread;
	
	long endTime, startTime, framePeriod;
	
	public Dimension size;
	
	public int score;
	public int highScore;
	public int extraShipScore; // one-up score
	public int lives = 1; // YOLO
	
	public boolean paused;
	public boolean running = false;
	
	public Ship ship;
	
	public Missle[] missles;	// stores the array of missles
	public int numMissles;		// stores the number of missles in the array
	public boolean firing;		// true if the ship is currently firing	
	
	public Asteroid[] asteroids; // the array of asteroids
	public int numAsteroids;	  // the number of asteroids currently in the array
	double astRadius, minAstVel, maxAstVel;	// values used to create asteroids
	int astNumHits, astNumSplit;
	
	public int level = 0;	// current level number
	
	AbstractView view;
	IController controller;
	
	// Constructors:
	
	v2() {
		
		
		super("Asteroids"); // name the window
		
		// Sizing stuff
		setSizes();
		
		// initMenuBar(); TODO:
		
		setLocationRelativeTo(null); // places window at center of screen
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		view = new AsteroidsView();	// instantiate View object
		
		Container con = this.getContentPane();
		con.add(view); // this.add(view) would work too
				
		setVisible(true); 		// make this window visible visible
		view.setVisible(true);	// make the View inside the window visible
	
		// output components are loaded, time to load input
		controller = new KBController(this);
			
	}
	
	/*
	 * starts the game
	 */
	public void start() {
		init();
		
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		
	}
		
	/*
	 * First method called upon starting. Initializes
	 * all necessary variables and objects.
	 */
	public void init() {
		framePeriod = 25;
		
		missles = new Missle[50];	// allocate the space for the array
		
		numAsteroids = 0;
		level = 0;	// will be incremented to 1 when first level is set up
		astRadius = 60;	// values used to create the asteroids
		minAstVel = 10;
		maxAstVel = 20;
		astNumHits = 3;
		astNumSplit = 2;
		running = true;	
		
		setUpNextLevel();
		
		view.update(this);
		view.start();			// start the view object
		
		
		
	}
	
	public void setUpNextLevel() {	// stars a new level with one more asteroid
		
		level++;
		
		// create a new, inactive ship centered on the screen
		// values can be adjusted
		ship = new Ship(250, 250, 0, .35, .98, .1, 10); // instantiate ship
		
		numMissles = 0;	// no missiles in the field to start with
		paused = false;
		firing = false;	// the ship is not firing
		//create an array large enough to hold the largest number of
		// asteroids possible on this level (plus one because the split
		// asteroids are created first, then the original one is deleted).
		// The level number is equal to the number of asteroids at it's start.
		asteroids = new Asteroid[level * (int) Math.pow(astNumSplit, astNumHits - 1) + 1];
		
		numAsteroids = level;
		// create asteroids in random spots on the screen
		for (int i = 0; i < numAsteroids; i++)
			asteroids[i] = new Asteroid(Math.random()*size.width,
					Math.random()*size.height, astRadius, minAstVel,
					maxAstVel, astNumHits, astNumSplit);
	}
	
	/*
	 * Updates field information to be used in calculations
	 * and output. It will call other class methods to update
	 * of the objects.
	 */
	public void update() {
		// update field info
		
		ship.move(size.width, size.height);
		
		for(int i = 0; i < numMissles ;i++) { 
			missles[i].move(size.width,size.height); 
			//removes shot if it has gone for too long
			//without hitting anything
			if(missles[i].getTimeLeft()<=0){ 
				//shifts all the next missles up one 
				//space in the array
				deleteMissle(i); 
				i--; // move the outer loop back one so 
				// the shot shifted up is not skipped
				} 
			} 
			
		//move asteroids and check for collisions
			updateAsteroids();	
		
		render(); // get our view object updated
	}

	/*
	 * Main run loop.
	 */
	@Override
	public void run() {
		System.out.println("running : " + running);
		while (running) {
			// mark start time
			startTime = System.currentTimeMillis();
			
			controller.Manipulate(this);
			
			update();
						
			// pause for predefined amt of time minus the time
			// it took to update the screen
			try {
				// mark end time
				endTime = System.currentTimeMillis();
				// do not stop for a negative amount of time
				if (framePeriod - (endTime-startTime) > 0) {
					Thread.sleep(framePeriod - (endTime- startTime));
				}
			} catch (InterruptedException e) {}
		}
	}
	
	/*
	 * Calls for view object to get data
	 */
	public void render() {
		view.update(this);
	}
	
	private void deleteMissle(int index) {
		// delete shot and move all missles it up in the array
		numMissles--;
		for(int i = index; i < numMissles; i++)
			missles[i] = missles[i+1];
		missles[numMissles] = null;
	}
	
	private void deleteAsteroid(int index) {
		// delete asteroid and shift ones after it up in the array
		numAsteroids--;
		for(int i = index; i < numAsteroids; i++)
			asteroids[i] = asteroids[i+1];
		asteroids[numAsteroids] = null;
	}
	
	private void addAsteroid(Asteroid ast) {
		// adds the asteroid passed in to the end of the array
		asteroids[numAsteroids] = ast;
		numAsteroids++;
	}
	
	private void updateAsteroids() {
		
		if (numAsteroids == 0) {
			setUpNextLevel();
			return;
		}
		
		for (int i = 0; i < numAsteroids; i++) {
			
			// move each asteroid
			asteroids[i].move(size.width, size.height);
			// check for collisions with the ship, restart the
			// level if the ship gets hit
			if (asteroids[i].shipCollision(ship)) {
				level--;	// restart this level
				lives--;   // decrement lives
				numAsteroids = 0;
				setUpNextLevel();
				return;
			}
			// check for collisions with any of the missles
			for (int j = 0; j < numMissles; j++) {
				if (asteroids[i].missleCollision(missles[j])) {
					// if the shot hit an asteroid, delete that shot
					deleteMissle(j);
					// split the asteroid up if needed
					if(asteroids[i].getHitPoints() > 1) {
						for (int k = 0; k < asteroids[i].getNumSplit(); k++)
							addAsteroid(asteroids[i].createSplitAsteroid(minAstVel, maxAstVel));
					}
					// delete the original asteroid
					deleteAsteroid(i);
					j = numMissles;	// break out of the inner loop: it has already been
									// hit, so don't need to check for collision with other missles
					i--;	// don't skip asteroid shifted back into the deleted asteroid's pos.
				}
			}
		} 
	}
	
	JMenuBar menuBar;
	JMenu fileMenu, viewMenu;
	
	JMenuItem menuReset;
	JMenuItem menuExit;
	JMenuItem menuSettings;
	
	JMenuItem resetAction;
	JMenuItem exitAction;
	JMenuItem settingAction;
	
	private void initMenuBar() {
		
		// Create the menu bar
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.MAGENTA);
		
		setJMenuBar(menuBar);
		
		// Build first menu
		fileMenu = new JMenu("File");
		viewMenu = new JMenu("View");
		
		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		
		menuReset = new JMenuItem("Reset");
		menuExit = new JMenuItem("Exit");
		menuSettings = new JMenuItem("Settings");
		
		fileMenu.add(menuReset);
		fileMenu.add(menuExit);
		fileMenu.add(menuSettings);
		
		
	}
	
	/*
	 * Sizes JFrame sizes and the size of the game field
	 */
	private void setSizes() {
		
		size = new Dimension(500, 500);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		setResizable(false);
	}
}

package util;

public class AsteroidsGameConfig {

	public static int framePeriod; // time in between frames
	public static int FPS;	// Frames per second
	public static int desiredFPS;	// Desired frame rate for the game

	public static int max_missles; // max # of sprites for missles,
	public static int max_asteroids; // asteroids, and flying debris.
	public static int max_debris;	// max number of explosion debris on the field

	public static int scrap_count;
	public static int hyper_count;	// number of times ship can warp
	public static int missle_count;

	public static int min_asteroid_sides; // min number of sides on an asteroid
	public static int max_asteroid_sides; // max number of sides on an asteroid
	public static int min_asteroid_size;  // min size of an asteroid
	public static int max_asteroid_size;  // max size of an asteroid
	public static double min_asteroid_speed;
	public static double max_asteroid_speed;
	public static double max_asteroid_spin;

	public static int newShipPoints;
	public static int newUfoPoints;

	public static int max_ships; // Starting # of ships per game.

	public static int ufo_passes; // Number of passes for UFO per appearance

	public AsteroidsGameConfig() {
		framePeriod = 15;
		FPS = Math.round(1000 / framePeriod);
		desiredFPS = 60;

		max_missles = 8;
		max_asteroids = 8;
		max_debris = 40;

		scrap_count = 2 * FPS;
		hyper_count = 3 * FPS;
		missle_count = 4 * FPS;

		min_asteroid_sides = 6;
		max_asteroid_sides = 16;

		min_asteroid_size = 20;
		max_asteroid_size = 40;

		min_asteroid_speed = 40.0 / FPS;
		max_asteroid_speed = 240.0 / FPS;

		max_asteroid_spin = Math.PI / FPS;

		max_ships = 3;
		ufo_passes = 3;
	}
}

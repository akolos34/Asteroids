package sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import util.FunBox;

public class Asteroid {

	double x, y, dx, dy;
	double radius;	// used to know the center of the Asteroid (wrapping)
	
	int hitPoints;	// number of missles it takes to destroy an Asteroid
	int numSplit;	// number of Asteroids this Asteroid will split into
	
	int minAstSides, maxAstSides, minAstSize, maxAstSize;

	public Polygon borderPolygon;

	public Asteroid(double x, double y, double radius, double minVelocity,
			double maxVelocity, int hitPoints, int numSplit) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.hitPoints = hitPoints; // number of missiles needed to destroy this
		this.numSplit = numSplit; // number of smaller asteroids it breaks up
									// into when shot

		// calculates a random direction and a random velocity between
		// minVelocity and maxVelocity
		double vel = minVelocity + Math.random() * (maxVelocity - minVelocity), dir = 2
				* Math.PI * Math.random(); // random direction

		dx = vel * Math.cos(dir);
		dy = vel * Math.sin(dir);

		createAsteroidPolygon();
	}

	public void createAsteroidPolygon() {
		// create shape for asteroids
		int side;
		double theta, r;
		int x, y;

		borderPolygon = new Polygon();
		side = minAstSides
				+ (int) (Math.random() * (maxAstSides - minAstSides));
		for (int j = 0; j < side; j++) {
			theta = 2 * Math.PI / side * j;
			r = minAstSize + (int) (Math.random() * (maxAstSize - minAstSize));
			x = (int) -Math.round(r * Math.sin(theta));
			y = (int) Math.round(r * Math.cos(theta));
			borderPolygon.addPoint(x, y);
		}
	}

	public void move(int fieldWidth, int fieldHeight) {

		x += dx; // move the asteroid
		y += dy;

		// wrap around code allowing the asteroid to go off the screen
		// to a distance equal to its radius before entering on the other
		// side. Otherwise, it would go halfway off the field, then disappear
		// and reapper halfway on the other side of the field.
		if (x < -radius)
			x += fieldWidth + 2 * radius;
		else if (x > fieldWidth + radius)
			x -= fieldWidth + 2 * radius;
		if (y < -radius)
			y += fieldHeight + 2 * radius;
		else if (y > fieldHeight + radius)
			y -= fieldHeight + 2 * radius;
	}

	public boolean shipCollision(Ship ship) {
		
		/*
		 * Check to see if this Asteroid's Polygon contains a point from the
		 * Ship's Polygon.
		 */
		for (int i = 0; i < ship.borderPolygon.npoints; i++) {
			if (this.borderPolygon.contains(ship.borderPolygon.xpoints[i],
					ship.borderPolygon.ypoints[i])){
				FunBox.p("Hit the ship!");
				return true;}
		}

		/*
		 * Now, check to see if the Ship's polygon contains a point from this
		 * Asteroid's polygon.
		 */
		for (int i = 0; i < this.borderPolygon.npoints; i++)
			if (ship.borderPolygon.contains(this.borderPolygon.xpoints[i],
					this.borderPolygon.ypoints[i])){
				FunBox.p("Hit the ship!");
				return true;}
		
		return false;
	}

	public boolean missleCollision(Missle missle) {
		
		/*
		 * check to see if this missle had entered this asteroid
		 */
		for (int i = 0; i < borderPolygon.npoints; i++) {
			if (this.borderPolygon.contains(missle.x, missle.y)) {
				FunBox.p("Hit a missle!");
				return true;
			}
		}
		
		return false;
	}

	// may be redone
	public Asteroid createSplitAsteroid(double minVelocity, double maxVelocity) {
		// when this asteroid is hit by a missle, this method is called
		// numSplit times by AsteroidsGameManager to create numSplit smaller
		// asteroids. Diving the radius by sqrt(numSplit) makes the sum of the
		// areas
		// taken up by the smaller asteroids equal to the area of this asteroid
		// Each smaller asteroid has one less hit left before being completely
		// destroyed.
		return new Asteroid(x, y, radius / Math.sqrt(numSplit), minVelocity,
				maxVelocity, hitPoints - 1, numSplit);
	}

	public void draw(Graphics g) {
		
		g.setColor(Color.GRAY); // set color for the asteroid
		
		g.fillPolygon(borderPolygon);
		// finishing drawing later
	}

	public int getHitPoints() {
		
		return hitPoints;
	}

	public int getNumSplit() {
		
		return numSplit;
	}

}

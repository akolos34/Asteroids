package sprites;

import java.awt.Dimension;
import java.awt.Point;

public class StarryBackground {

	// Fields

	int numStars;
	Dimension dim;
	Point[] stars;

	// Constructors

	public StarryBackground(int numStars, Dimension dim) {
		this.numStars = numStars;
		this.dim = dim;
		stars = new Point[numStars];
		createStars();
	}

	public StarryBackground(int numStars, int fieldWidth, int fieldHeight) {
		this.numStars = numStars;
		this.dim.height = fieldHeight;
		this.dim.width = fieldWidth;
		stars = new Point[numStars];
		createStars();
	}

	// Methods

	/*
	 * A star is simply a random point on the field that will be painted
	 */
	public void createStars() {
		for (int i = 0; i < numStars; i++) {
			stars[i] = new Point(randomInt(dim.width), randomInt(dim.height));
		}
	}
	
	public Point[] getStars() {
		return stars;
	}
	
	public int getXAtIndex(int i) {
		return stars[i].x;
	}
	
	public int getYAtIndex(int i) {
		return stars[i].y;
	}
	// purely to shorten code
	public int randomInt(int arg) {
		return (int) (Math.random() * arg);
	}
}

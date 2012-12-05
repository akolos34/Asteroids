package sprites;

import java.awt.Color;
import java.awt.Graphics;

import util.FunBox;

public class Missle {

	final double missleSpeed = 15; // the speed at which the missles move (pix per frame)

	double x, y, dx, dy; // variables for movement

	int timeLeft; // used to make the shot disappear if it doesn't hit anything

	public Missle(double x, double y, double angle, double ship_dy,
			double ship_dx, int timeLeft) {

		this.x = x;
		this.y = y;

		// add the velocity of the ship to the shot velocity
		// (so the shot's velocity is relative to the ship's velocity)
		dx = missleSpeed * Math.cos(angle) + ship_dx;
		dy = missleSpeed * Math.sin(angle) + ship_dy;

		// the number of frames the missle will last for
		// before disappearing if it does not hit anything
		this.timeLeft = timeLeft;

	}

	public void move(int fieldWidth, int fieldHeight) {
		timeLeft--;

		x += dx;
		y += dy;

		if (x < 0)
			x += fieldWidth;
		else if (x > fieldWidth)
			x -= fieldWidth;
		if (y < 0)
			y += fieldHeight;
		else if (y > fieldHeight)
			y -= fieldHeight;
	}
	
	public void draw(Graphics g) {
		g.setColor(FunBox.randomColor_Blues()); // set shot color
		
		g.fillRect( (int) x + 2, (int) y - 2, 4, 4);
		
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}

}

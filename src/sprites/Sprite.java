package sprites;

import java.awt.Polygon; // important
import java.awt.event.KeyEvent;

public class Sprite {

	// Fields:

	  public int width;          // Dimensions of the graphics area
	  public int height;

	  public Polygon shape;             // Base sprite shape, centered at the origin (0,0)
	  public boolean active;            // Is active on the field
	  public double  angle;             // Current angle of rotation
	  public double  dAngle;        // Change in angle of rotation
	  public double  x;             	// Current position on screen
	  public double y;
	  public double  dX, dY;    // Amount to change the screen position.
	  public Polygon sprite;            // Final location and shape after 

	  public Sprite() { // constructor

	    this.shape = new Polygon();
	    this.active = false;
	    this.angle = 0.0;
	    this.dAngle = 0.0;
	    this.x = 0.0;
	    this.y = 0.0;
	    this.dX = 0.0;
	    this.dY = 0.0;
	    this.sprite = new Polygon();
	  }

	  // Methods

	  public boolean advance() {

	    boolean wrapped;

	    /* Update the rotation and position of the sprite based on the delta
	     values. If the sprite moves off the edge of the screen, it is wrapped
	     around to the other side and true is returned.
	    */

	    this.angle += this.dAngle; // update angle
	    if (this.angle < 0)	// keep angle positive
	      this.angle += 2 * Math.PI;
	    if (this.angle > 2 * Math.PI)
	      this.angle -= 2 * Math.PI;
	    
	    wrapped = false;
	    
	    this.x += this.dX;
	    if (this.x < -width / 2) {
	      this.x += width;
	      wrapped = true;
	    }
	    if (this.x > width / 2) {
	      this.x -= width;
	      wrapped = true;
	    }
	    this.y -= this.dY;
	    if (this.y < -height / 2) {
	      this.y += height;
	      wrapped = true;
	    }
	    if (this.y > height / 2) {
	      this.y -= height;
	      wrapped = true;
	    }

	    return wrapped;
	  }

	  public void render() {

	    int i;

	    // Render the sprite's shape and location by rotating it's base shape and
	    // moving it to it's proper screen position.

	    this.sprite = new Polygon();
	    for (i = 0; i < this.shape.npoints; i++)
	      this.sprite.addPoint((int) Math.round(this.shape.xpoints[i] * Math.cos(this.angle) + this.shape.ypoints[i] * Math.sin(this.angle)) + (int) Math.round(this.x) + width / 2,
	                           (int) Math.round(this.shape.ypoints[i] * Math.cos(this.angle) - this.shape.xpoints[i] * Math.sin(this.angle)) + (int) Math.round(this.y) + height / 2);
	  }

	  public boolean isColliding(Sprite s) {


	    // Determine if one sprite overlaps with another
	    
	    int i; //index variable
	    
	    // if the argued sprite has a point inside this sprite, then there is a collision
	    for (i = 0; i < s.sprite.npoints; i++) {
	      if (this.sprite.contains(s.sprite.xpoints[i], s.sprite.ypoints[i]))
	        return true;
	    }
	    
	    // check current sprite too
	    for (i = 0; i < this.sprite.npoints; i++) {
	      if (s.sprite.contains(this.sprite.xpoints[i], this.sprite.ypoints[i]))
	        return true;
	  	}
	    
	    return false; // otherwise return false
	    
	  }
	  
	public boolean isActive() {
		return active;
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

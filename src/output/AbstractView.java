package output;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

import main.v2;

public abstract class AbstractView extends Canvas implements IOutput {

	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public abstract void update(v2 manager);

	@Override
	public abstract void setSize(int w, int h);

	@Override
	public abstract void paint(Graphics gfx);

	@Override
	public abstract Graphics getGraphics();

	@Override
	public abstract Image getImage();

	public abstract void start();

}

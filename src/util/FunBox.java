package util;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class FunBox {
	
	static Random rand = new Random();
	
	public static Color randomColor() {
		
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		
		Color randomColor = new Color(r, g, b);
		randomColor = randomColor.brighter();
	
		return randomColor;
	}
	
	public static Color randomPastelColor() {
		
		int R = (int)(Math.random()*256);
		int G = (int)(Math.random()*256);
		int B = (int)(Math.random()*256);
		Color color = new Color(R, G, B); //random color, but can be bright or dull

		// to get rainbow, pastel colors
		Random random = new Random();
		final float hue = random.nextFloat();
		final float saturation = 0.3f; //1.0 for brilliant, 0.0 for dull
		final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
		color = Color.getHSBColor(hue, saturation, luminance);
		
		return color;
	}
	
	public static void setRandomPastelColor(Graphics gfx) {
		
		gfx.setColor(randomPastelColor());
	}
	
	public static Color randomColor_RedToYellow() {
		
		float r = 1;
		float g = rand.nextFloat();
		float b = 0;
		
		Color color = new Color(r, g, b);
		return color;
	}
}

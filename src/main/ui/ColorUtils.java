package main.ui;

import java.awt.Color;

public class ColorUtils {
	
	public static Color BACKGROUND = new Color(106, 145, 184);
	public static Color CALCULATION_BACKGROUND = new Color(107, 107, 107);
	
	public static Color getHoverColor(Color original) {
		Color result = null;
		if(isBright(original)) {
			result = original.darker();
		}else {
			result = original.brighter();
		}
		return result;
	}
	
	public static boolean isBright(Color color) {
		return getBrightness(color) > 130;
	}
	
	private static int getBrightness(Color color) {
		return (int) Math.sqrt(
			      color.getRed() * color.getRed() * .241 +
			      color.getGreen() * color.getGreen() * .691 +
			      color.getBlue() * color.getBlue() * .068);
	}
	
	

}

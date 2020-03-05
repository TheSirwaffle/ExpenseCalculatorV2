package main.ui;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

public class FontUtils {
	
	public static final Font FONT = new Font("Bookman Old Style", Font.BOLD, 18);
	
	public static Font getUnderlinedFont() {
		Map attributes = FONT.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		Font font = FONT.deriveFont(attributes);
		return font;
	}

}

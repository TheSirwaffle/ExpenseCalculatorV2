package main.ui;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class WindowPlacer {
	
	public static Point getCenterLocation(JDialog jdialog) {
		JFrame frame = MainWindow.getFrame(); 
		Dimension size = frame.getSize();
		Dimension dialogSize = jdialog.getSize();
		Point frameLocation = frame.getLocation();
		Point toAdd = new Point(size.width/2 - dialogSize.width/2, size.height/2 - dialogSize.height/2);
		return new Point(frameLocation.x + toAdd.x, frameLocation.y + toAdd.y);
	}

}

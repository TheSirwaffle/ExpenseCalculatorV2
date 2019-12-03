package main.ui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class ColoredButton extends JButton {
	
	private Color normal;
	private Color hover;
	
	public ColoredButton(Color normal, Color hover, String text) {
		this.setText(text);
		this.normal = normal;
		this.hover = hover;
		addMouseListener(mouseListener);
	}
	
	private MouseListener mouseListener = new MouseAdapter() {
	
		@Override
		public void mouseEntered(MouseEvent e) {
			setBackground(hover);
		}
		
		public void mouseExited(MouseEvent e) {
			setBackground(normal);
		};
	};

}

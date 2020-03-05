package main.ui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class ColoredButton extends JButton {
	
	private Color normal;
	private Color hover;
	
	public ColoredButton(Color normal, String text) {
		this.setText(text);
		this.normal = normal;
		this.hover = ColorUtils.getHoverColor(normal);
		addMouseListener(mouseListener);
		setupColors();
		setFocusable(false);
		setBorder(createBorder());
	}
	
	private void setupColors() {
		setBackground(normal);
		if(ColorUtils.isBright(normal)) {
			setForeground(Color.BLACK);
		}else {
			setForeground(Color.WHITE);
		}
	}
	
	private Border createBorder() {
		Border inner = BorderFactory.createEmptyBorder(4, 15, 4, 15);
		Border outer = BorderFactory.createLineBorder(Color.black);
		return BorderFactory.createCompoundBorder(outer, inner);
	}
	
	private MouseListener mouseListener = new MouseAdapter() {
	
		@Override
		public void mouseEntered(MouseEvent e) {
			setBackground(hover);
			setBorder(createBorder());
		}
		
		public void mouseExited(MouseEvent e) {
			setBackground(normal);
			setBorder(createBorder());
		};
	};

}

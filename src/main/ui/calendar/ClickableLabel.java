package main.ui.calendar;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ClickableLabel extends JLabel {
	
	private List<ActionListener> actionListeners;
	
	public ClickableLabel(int width, int height, String text) {
		this.setText(text);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		setBounds(width, height);
		actionListeners = new ArrayList<>();
		addMouseListener(mouseListener);
	}

	private void setBounds(int width, int height) {
		Dimension dimension = new Dimension(width, height);
		setPreferredSize(dimension);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
	}
	
	public void addActionListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
	}
	
	private MouseListener mouseListener = new MouseAdapter() {
		
		public void mouseClicked(java.awt.event.MouseEvent e) {
			for(ActionListener listener : actionListeners) {
				ActionEvent event = new ActionEvent(ClickableLabel.this, 0, getText());
				listener.actionPerformed(event);
			}
		};
	
		public void mouseEntered(java.awt.event.MouseEvent e) {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		};
		
		public void mouseExited(java.awt.event.MouseEvent e) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		};
		
	};

}

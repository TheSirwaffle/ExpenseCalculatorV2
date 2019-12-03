package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Day extends UIContent {
	
	private static final Font FONT = new Font("Helvetica", Font.PLAIN, 13);
	private static final Color REGULAR = new Color(46, 99, 50);
	private static final Color HOVER = new Color(63, 140, 69);
	
	private int day;

	public Day(int day) {
		super(new BorderLayout());
		this.day = day;
	}

	@Override
	protected void setupPanel(JPanel panel) {
		if(day != DaysView.EMPTY_DAY) {
			JPanel center = new JPanel();
			center.addMouseListener(mouseListener);
			center.setBackground(REGULAR);
			JLabel label = createLabel();
			panel.add(center, BorderLayout.CENTER);
			center.add(label, BorderLayout.CENTER);
			Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLineBorder(Color.BLACK));
			panel.setBorder(border);
		}
	}
	
	

	private JLabel createLabel() {
		JLabel label = new JLabel(""+day);
		label.setFont(FONT);
		label.setForeground(Color.white);
		return label;
	}
	
	private MouseListener mouseListener = new MouseAdapter() {
		
		@Override
		public void mouseEntered(MouseEvent e) {
			e.getComponent().setBackground(HOVER);
			
		}
		
		public void mouseExited(MouseEvent e) {
			e.getComponent().setBackground(REGULAR);
		};
	};
	
	

}

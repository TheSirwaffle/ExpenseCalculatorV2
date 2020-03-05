package main.ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.ui.ColorUtils;
import main.ui.UIContent;

public class Day extends UIContent {
	
	private static final Font FONT = new Font("Helvetica", Font.PLAIN, 13);
	private static final Color REGULAR = new Color(73, 95, 130);
	private static final Color HOVER = new Color(99, 126, 171);
	private static final Color DISABLED = new Color(47, 61, 84);
	private static final Color SELECTED = new Color(96, 114, 145);
	
	private int day;
	private Consumer<Day> onClicked;
	private boolean isDisabled;
	private boolean isSelected;
	private JPanel center;

	public Day(int day, Consumer<Day> onClicked) {
		super(new BorderLayout());
		this.day = day;
		this.onClicked = onClicked;
	}
	
	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
		getPanel();
		reColor();
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		reColor();
	}
	
	private void reColor() {
		if(isDisabled) {
			center.setBackground(DISABLED);
		}else if(isSelected) {
			center.setBackground(SELECTED);
		}else if(!isSelected) {
			center.setBackground(REGULAR);
		}
	}

	@Override
	protected void setupPanel(JPanel panel) {
		panel.setBackground(ColorUtils.BACKGROUND);
		if(day != DaysView.EMPTY_DAY) {
			center = new JPanel();
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
	
	public int getDay() {
		return day;
	}
	
	private MouseListener mouseListener = new MouseAdapter() {
		
		@Override
		public void mouseEntered(MouseEvent e) {
			if (!isDisabled) {
				center.setBackground(HOVER);
			}
		}
	
		public void mouseExited(MouseEvent e) {
			if(isSelected) {
				center.setBackground(SELECTED);
			}
			if(!isDisabled && !isSelected) {
				center.setBackground(REGULAR);
			}
		};
		
		public void mousePressed(MouseEvent e) {
			if(day != DaysView.EMPTY_DAY && !isDisabled) {
				onClicked.accept(Day.this);
			}
		};
		
		
	};
	
	

}

package main.ui.budget;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.budget.BudgetItemType;
import main.budget.CalendarDay;
import main.ui.ColoredButton;

public class DaySelectButton {

	private ColoredButton button;
	private BudgetItemType type;
	private CalendarDay day;
	
	public DaySelectButton(String text, BudgetItemType type) {
		button = new ColoredButton(Color.white, text);
		button.addActionListener(actionListener);
		this.type = type;
	}
	
	public JButton getButton() {
		return button;
	}
	
	public void setDay(CalendarDay day) {
		this.day = day;
		alterButtonText();
	}
	
	public CalendarDay getDay() {
		return day;
	}
	
	private void alterButtonText() {
		if(day != null) {
			button.setText(day.toString());
		}
	}
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			CalendarDaySelector selector = new CalendarDaySelector();
			selector.view(type);
			day = selector.getDay();
			alterButtonText();
		}
	};
}

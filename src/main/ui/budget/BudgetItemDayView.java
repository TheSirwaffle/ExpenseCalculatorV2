package main.ui.budget;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.budget.BudgetItemType;
import main.budget.CalendarDay;
import main.ui.UIContent;

public class BudgetItemDayView extends UIContent {
	
	private List<DaySelectButton> buttons;
	private JPanel buttonPanel;

	public BudgetItemDayView() {
		super(new BorderLayout());
		buttons = new ArrayList<>();
	}

	@Override
	protected void setupPanel(JPanel panel) {
		JLabel label = new JLabel("Day:");
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		panel.add(label, BorderLayout.WEST);
		panel.add(buttonPanel, BorderLayout.EAST);
	}
	
	public void onBudgetItemTypeChanged(BudgetItemType type) {
		getPanel().setVisible(type.getRequiredDays() > 0);
		resetButtons();
		for(int i=0; i<type.getRequiredDays(); i++) {
			addButton(type);
		}
	}
	
	private void addButton(BudgetItemType type) {
		String buttonText = "Select Day " + (buttons.size() + 1);
		DaySelectButton button = new DaySelectButton(buttonText, type);
		buttons.add(button);
		buttonPanel.add(Box.createHorizontalStrut(10));
		buttonPanel.add(button.getButton());
		buttonPanel.repaint();
		buttonPanel.revalidate();
	}
	
	private void resetButtons() {
		buttonPanel.removeAll();
		buttons.clear();
	}
	
	public void setSelectedDays(List<CalendarDay> days) {
		for(int i=0; i<buttons.size(); i++) {
			DaySelectButton button = buttons.get(i);
			button.setDay(days.get(i));
		}
	}
	
	public List<CalendarDay> getSelectedDays() {
		List<CalendarDay> days = new ArrayList<>();
		for(DaySelectButton button : buttons) {
			CalendarDay day = button.getDay();
			days.add(day);
		}
		return days;
	}
	

}

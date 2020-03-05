package main.ui.calendar;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import main.budget.CalendarDay;
import main.ui.DayListener;
import main.ui.UIContent;

public class CalendarView extends UIContent {
	
	private DaysView view;
	private MonthButtonPanel monthPanel;
	
	public CalendarView(DayListener listener) {
		super(new BorderLayout(), listener);
	}
	
	@Override
	protected void setupPanel(JPanel panel) {
		view = new DaysView(getArg(0));
		monthPanel = new MonthButtonPanel(this::onClick, this::onSelected);
		panel.add(monthPanel.getPanel(), BorderLayout.NORTH);
		panel.add(view.getPanel(), BorderLayout.CENTER);
	}
	
	public void disablePastDays() {
		getPanel();
		view.disablePastDays();
	}
	
	public void addDayListener(DayListener listener) {
		view.addListener(listener);
	}
	
	private void onClick(Direction direction) {
		if(direction == Direction.BACK) {
			view.moveBackwardOneMonth();
		}else if(direction == Direction.FORWARD) {
			view.moveForwardOneMonth();
		}
		monthPanel.changeMonth(view.getMonth(), view.getYear());
	}
	
	public void onSelected(CalendarInfo info) {
		view.setTime(info.getMonth(), info.getYear());
	}
	
	public void setSelectedDays(List<CalendarDay> selectedDays) {
		view.setSelectedDays(selectedDays);
	}

}

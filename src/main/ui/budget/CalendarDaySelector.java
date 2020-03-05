package main.ui.budget;

import java.awt.Dialog.ModalityType;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JPanel;

import main.budget.BudgetItemType;
import main.budget.CalendarDay;
import main.budget.DaySelectionType;
import main.ui.DayListener;
import main.ui.WindowPlacer;
import main.ui.calendar.CalendarView;
import main.ui.calendar.DaysView;

public class CalendarDaySelector {
	
	private JDialog dialog;
	private CalendarDay day;
	private BudgetItemType type;
	
	public CalendarDaySelector() {
		dialog = new JDialog();
		dialog.setSize(500,  500);
	}
	
	public void view(BudgetItemType type) {
		this.type = type;
		day = null;
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setContentPane(createPanel(listener));
		Point location = WindowPlacer.getCenterLocation(dialog);
		dialog.setLocation(location);
		dialog.setVisible(true);
	}
	
	public CalendarDay getDay() {
		return day;
	}
	
	private JPanel createPanel(DayListener listener) {
		JPanel panel = null;
		DaySelectionType selectType = type.getType();
		if(selectType == DaySelectionType.MONTH) {
			DaysView view = new DaysView(listener);
			panel = view.getPanel();
		}else if(selectType == DaySelectionType.YEAR || selectType == DaySelectionType.ONCE) {
			CalendarView view = new CalendarView(listener);
			panel = view.getPanel();
		}else {
			panel = new JPanel();
		}
		return panel;
	}
	
	private DayListener listener = new DayListener() {
		
		@Override
		public void onClick(CalendarDay calendarDay) {
			 day = calendarDay;
			 day.setType(type);
			 dialog.setVisible(false);
		}
	};

}

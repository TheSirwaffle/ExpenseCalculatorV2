package main.ui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DaysView extends UIContent {
	
	public static int EMPTY_DAY = -1;
	
	private List<Day> days;
	private int year;
	private int month;

	public DaysView() {
		super(new GridLayout(7, 7));
		days = new ArrayList<>();
		month = Calendar.FEBRUARY;
		year = 2019;
	}

	@Override
	protected void setupPanel(JPanel panel) {
		addDaysOfWeek(panel);
		addDays(panel);
	}
	
	private void addDaysOfWeek(JPanel panel) {
		panel.add(createLabel("Su"));
		panel.add(createLabel("Mo"));
		panel.add(createLabel("Tu"));
		panel.add(createLabel("We"));
		panel.add(createLabel("Th"));
		panel.add(createLabel("Fr"));
		panel.add(createLabel("Sa"));
	}
	
	private JLabel createLabel(String text) {
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
	
	private void addDays(JPanel panel) {
		Calendar calendar = setupCalendar();
		int numEmptyDays = determineNumberOfEmptyDays(calendar);
		int total = 0;
		for(int i=0; i<numEmptyDays; i++) {
			Day day = new Day(EMPTY_DAY);
			days.add(day);
			panel.add(day.getPanel());
			total++;
		}
		for(int i=0; i<calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			Day day = new Day(i+1);
			days.add(day);
			panel.add(day.getPanel());
			total++;
		}
		for(int i=total; i<42; i++) {
			Day day = new Day(EMPTY_DAY);
			days.add(day);
			panel.add(day.getPanel());
		}
	}
	
	private Calendar setupCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month); 
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		this.month = month;
		this.year = year;
		return calendar;
	}
	
	private int determineNumberOfEmptyDays(Calendar calendar) {
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		return day - 1;
	}
	
	public void moveBackwardOneMonth() {
		removeDays();
		changeMonth(-1);
		addDays(this.getPanel());
	}
	
	public void moveForwardOneMonth() {
		removeDays();
		changeMonth(1);
		addDays(this.getPanel());
	}
	
	private void removeDays() {
		JPanel panel = getPanel();
		for(Day day : days) {
			panel.remove(day.getPanel());
		}
		days.clear();
	}
	
	private void changeMonth(int alter) {
		int newMonth = month + alter;
		int newYear = year;
		if(newMonth < Calendar.JANUARY) {
			newMonth = Calendar.DECEMBER;
			newYear -= 1;
		}else if (newMonth > Calendar.DECEMBER) {
			newMonth = Calendar.JANUARY;
			newYear += 1;
		}
		this.month = newMonth;
		this.year = newYear;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}

}

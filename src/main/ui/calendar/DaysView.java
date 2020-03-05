package main.ui.calendar;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.budget.CalendarDay;
import main.ui.ColorUtils;
import main.ui.DayListener;
import main.ui.UIContent;

public class DaysView extends UIContent {
	
	public static int EMPTY_DAY = -1;
	
	private List<Day> days;
	private int year;
	private int month;
	private List<DayListener> listeners;
	private List<CalendarDay> selectedDays;
	private boolean disablePastDays;

	public DaysView(DayListener listener) {
		super(new GridLayout(7, 7));
		disablePastDays = false;
		days = new ArrayList<>();
		this.listeners = new ArrayList<>();
		this.selectedDays = new ArrayList<>();
		listeners.add(listener);
		setupCurentDate();
	}
	
	private void setupCurentDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
	}
	
	public void addListener(DayListener listener) {
		listeners.add(listener);
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
		panel.setBackground(ColorUtils.BACKGROUND);
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
			Day day = new Day(EMPTY_DAY, onDaySelected());
			days.add(day);
			panel.add(day.getPanel());
			total++;
		}
		for(int i=0; i<calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			Day day = new Day(i+1, onDaySelected());
			days.add(day);
			if(shouldDayBeDisabled(day)) {
				day.setDisabled(true);
			}
			panel.add(day.getPanel());
			total++;
		}
		for(int i=total; i<42; i++) {
			Day day = new Day(EMPTY_DAY, onDaySelected());
			days.add(day);
			panel.add(day.getPanel());
		}
	}
	
	private boolean shouldDayBeDisabled(Day day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day.getDay());
		long timeForDay = calendar.getTimeInMillis();
		long now = System.currentTimeMillis();
		return timeForDay < now && disablePastDays;
	}
	
	private Calendar setupCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month); 
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar;
	}
	
	private int determineNumberOfEmptyDays(Calendar calendar) {
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		return day - 1;
	}
	
	public void setTime(int month, int year) {
		this.month = month;
		this.year = year;
		removeDays();
		addDays(this.getPanel());
		refreshSelectedDays();
	}
	
	public void disablePastDays() {
		disablePastDays = true;
		removeDays();
		addDays(this.getPanel());
	}
	
	public void moveBackwardOneMonth() {
		removeDays();
		changeMonth(-1);
		addDays(this.getPanel());
		refreshSelectedDays();
	}
	
	public void moveForwardOneMonth() {
		removeDays();
		changeMonth(1);
		addDays(this.getPanel());
		refreshSelectedDays();
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
	
	private Consumer<Day> onDaySelected() {
		Consumer<Day> onPicked = (day -> {
			CalendarDay calendarDay = new CalendarDay(day.getDay(), month, year);
			for(DayListener listener : listeners) {
				listener.onClick(calendarDay);
			}
		});
		return onPicked;
	}
	
	public void setSelectedDays(List<CalendarDay> selectedDays) {
		this.selectedDays = selectedDays;
		refreshSelectedDays();
	}
	
	private void refreshSelectedDays() {
		for(Day day : days) {
			if(day.getDay() != EMPTY_DAY) {
				day.setSelected(false);
				CalendarDay calendarDay = new CalendarDay(day.getDay(), month, year);
				if(selectedDays.contains(calendarDay)) {
					day.setSelected(true);
				}
			}
		}
	}

}

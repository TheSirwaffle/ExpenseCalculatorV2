package main.ui.calculate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

import main.budget.BudgetItemType;
import main.budget.CalendarDay;
import main.ui.DayListener;

public class CalendarDaySelector implements DayListener  {
	
	private CalendarDay firstDay;
	private CalendarDay lastDay;
	private Consumer<List<CalendarDay>> onDaysSelected;

	@Override
	public void onClick(CalendarDay day) {
		if(firstDay != null && lastDay != null) {
			resetDays();
		}
		if(firstDay == null) {
			firstDay = day;
		}else if(lastDay == null) {
			if(isDayBeforeFirstDay(day)) {
				lastDay = firstDay;
				firstDay = day;
			}else {
				lastDay = day;
			}
			if(onDaysSelected != null) {
				onDaysSelected.accept(getAllSelectedDays());
			}
		}
	}
	
	private boolean isDayBeforeFirstDay(CalendarDay day) {
		int sinceEpoch = getDaySinceEpoch(day);
		int firstDaySinceEpoch = getDaySinceEpoch(firstDay);
		return sinceEpoch < firstDaySinceEpoch;
	}
	
	public void setOnDaysSelected(Consumer<List<CalendarDay>> onDaysSelected) {
		this.onDaysSelected = onDaysSelected;
	}
	
	public List<CalendarDay> getAllSelectedDays() {
		List<CalendarDay> allDays = new ArrayList<>();
		int first = getDaySinceEpoch(firstDay); 
		int last = (lastDay != null) ? getDaySinceEpoch(lastDay) : first;
		int current = first;
		while(current < last + 1) {
			CalendarDay day = getCalendarDayForDay(current);
			allDays.add(day);
			current++;
		}
		return allDays;
	}
	
	private int getDaySinceEpoch(CalendarDay day) {
		Calendar calendar = createCalendarForDay(day);
		long millis = calendar.getTimeInMillis();
		long days = Duration.of(millis, ChronoUnit.MILLIS).toDays();
		return (int) days;
	}
	
	private CalendarDay getCalendarDayForDay(int day) {
		long millis = Duration.of(day, ChronoUnit.DAYS).toMillis() + Duration.of(3, ChronoUnit.HOURS).toMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return createCalendarDay(calendar);
	}
	
	private CalendarDay createCalendarDay(Calendar current) {
		int day = current.get(Calendar.DAY_OF_MONTH);
		int month = current.get(Calendar.MONTH);
		int year = current.get(Calendar.YEAR);
		CalendarDay calendarDay = new CalendarDay(day, month, year);
		calendarDay.setType(BudgetItemType.YEARLY);
		return calendarDay;
	}
	
	private Calendar createCalendarForDay(CalendarDay day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, day.getYear());
		calendar.set(Calendar.MONTH, day.getMonth());
		calendar.set(Calendar.DAY_OF_MONTH, day.getDay());
		return calendar;
	}
	
	private void resetDays() {
		firstDay = null;
		lastDay = null;
	}

}

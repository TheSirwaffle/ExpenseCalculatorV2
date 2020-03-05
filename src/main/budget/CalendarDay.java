package main.budget;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarDay implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final int UNUSED = -1;
	
	private int day;
	private int year;
	private int month;
	private BudgetItemType type;
	
	public CalendarDay(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public void setType(BudgetItemType type) {
		this.type = type;
//		alterDataForType();
	}
	
	public int getDay() {
		return day;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public boolean isSameDay(CalendarDay otherDay) {
		boolean isSameDay = dayMatches(otherDay);
		if(doesValueMatter(DaySelectionType.MONTH)) {
			isSameDay = isSameDay && valuesMatch(month, otherDay.month);
		}
		if(doesValueMatter(DaySelectionType.YEAR)) {
			isSameDay = isSameDay && valuesMatch(year, otherDay.year);
		}
		return isSameDay;
	}
	
	private boolean dayMatches(CalendarDay otherDay) {
		boolean valuesMatch = valuesMatch(day, otherDay.day);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, otherDay.getDay());
		calendar.set(Calendar.YEAR, otherDay.year);
		calendar.set(Calendar.MONTH, otherDay.month);
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		boolean isLaterThanLastDay = isLaterThanLastDay(otherDay, lastDay);
		boolean isWeekly = isWeekly();
		boolean weekMatches = (doesDayOfWeekMatch(calendar)); 
		return (!isWeekly && valuesMatch || isLaterThanLastDay ) || (isWeekly && weekMatches);
	}
	
	private boolean isLaterThanLastDay(CalendarDay otherDay, int lastDay) {
		boolean isLastDayOfMonth = lastDay == otherDay.day;
		boolean isLaterThanLastDay = isLastDayOfMonth && day > otherDay.day;
		return isLaterThanLastDay;
	}
	
	private boolean doesDayOfWeekMatch(Calendar calendar) {
		boolean isWeekly = false;
		if(type != null && type.isWeekly()) {
			Calendar myDay = Calendar.getInstance();
			myDay.set(Calendar.DAY_OF_MONTH, day);
			int dayOfWeek = myDay.get(Calendar.DAY_OF_WEEK);
			isWeekly = dayOfWeek == calendar.get(Calendar.DAY_OF_WEEK);
		}
		return isWeekly;
	}
	
	private boolean isWeekly() {
		return (type != null && type.isWeekly());
	}
	
	private boolean valuesMatch(int first, int second) {
		return first == second || first == UNUSED;
	} 
	
	private boolean doesValueMatter(DaySelectionType selectType) {
		boolean matters = (type == null);
		if(type != null) {
			DaySelectionType mySelectType = type.getType();
			matters = mySelectType.doesValueMatter(selectType);
		}
		return matters;
	}
	
	@Override
	public String toString() {
		String result = "";
		switch(type) {
		case SEMI_MONTHLY:
		case MONTHLY:
			result = concatValues("dd");
			break;
		case ONE_TIME:
			result = concatValues("MMM dd, yyyy");
			break;
		case SEMI_WEEKLY:
		case WEEKLY:
			result = concatValues("EEEE");
			break;
		case YEARLY:
			result = concatValues("MMMM dd");
			break;
		}
		return result;
	}
	
	private String concatValues(String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(calendar.getTime());
	}
	
	@Override
	public int hashCode() {
		String toString = "" + day + "" + month + "" + year;
		return toString.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if(obj instanceof CalendarDay) {
			CalendarDay otherDay = (CalendarDay) obj;
			isEqual = day == otherDay.day && month == otherDay.month && year == otherDay.year;
		}
		return isEqual;
	}

}

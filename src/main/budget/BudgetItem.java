package main.budget;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class BudgetItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private double cost;
	private List<CalendarDay> days;
	private BudgetItemType type;
	private boolean isNegative;
	
	public BudgetItem(String name, double cost, CalendarDay day, BudgetItemType type) {
		this(name, cost, Collections.singletonList(day), type);
	}
	
	public BudgetItem(String name, double cost, List<CalendarDay> days, BudgetItemType type) {
		this.name = name;
		this.cost = cost;
		this.days = days;
		this.type = type;
		this.isNegative = false;
	}

	public BudgetItemType getItemType() {
		return type;
	}
	
	public boolean shouldApply(CalendarDay day) {
		boolean doesApply = type == BudgetItemType.SPREAD;
		for ( CalendarDay calendarDay : days) {
			doesApply = doesApply || calendarDay.isSameDay(day);
		}
		return doesApply;
	}
	
	public double getCost() {
		return cost;
	}
	
	public String getName() {
		return name;
	}
	
	public List<CalendarDay> getDays() {
		return days;
	}
	
	public boolean isNegative() {
		return isNegative;
	}
	
	public void flagNegative() {
		isNegative = true;
	}
	
	
}

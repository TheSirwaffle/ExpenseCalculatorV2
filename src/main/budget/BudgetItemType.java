package main.budget;

public enum BudgetItemType {
	MONTHLY("Monthly", DaySelectionType.MONTH, 1),
	SEMI_MONTHLY("Semi-Monthly", DaySelectionType.MONTH, 2),
	WEEKLY("Weekly", DaySelectionType.MONTH, 1),
	SEMI_WEEKLY("Semi-Weekly", DaySelectionType.MONTH, 2),
	YEARLY("Yearly", DaySelectionType.YEAR, 1),
	ONE_TIME("One Time", DaySelectionType.ONCE, 1),
	SPREAD("Spread", DaySelectionType.NONE, 0);
	
	private String name;
	private DaySelectionType type;
	private int requiredDays;
	
	private BudgetItemType(String name, DaySelectionType type, int requiredDays) {
		this.name = name;
		this.type = type;
		this.requiredDays = requiredDays;
	}
	
	public DaySelectionType getType() {
		return type;
	}
	
	public int getRequiredDays() {
		return requiredDays;
	}
	
	public boolean isWeekly() {
		boolean result = false;
		switch(this) {
		case SEMI_WEEKLY:
		case WEEKLY:
			result = true;
			break;
		}
		return result;
	}
	
	@Override
	public String toString() {
		return name;
	}
}

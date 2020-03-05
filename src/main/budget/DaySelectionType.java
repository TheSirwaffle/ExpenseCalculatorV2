package main.budget;

public enum DaySelectionType {
	
	NONE(0),
	MONTH(1),
	YEAR(2),
	ONCE(3);
	
	private int order;
	
	private DaySelectionType(int order) {
		this.order = order;
	}
	
	public boolean doesValueMatter(DaySelectionType other) {
		return this.order > other.order;
	}
}

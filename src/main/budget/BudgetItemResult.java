package main.budget;

public class BudgetItemResult implements Comparable<BudgetItemResult> {
	
	private BudgetItem item;
	private double result;
	
	public BudgetItemResult(BudgetItem item, double result) {
		this.item = item;
		this.result = result;
	}
	
	public BudgetItem getItem() {
		return item;
	}
	
	public double getResult() {
		return result;
	}
	
	public void addResult(double toAdd) {
		result += toAdd;
	}

	@Override
	public int compareTo(BudgetItemResult o) {
		return Double.compare(o.result, result) ;
	}

}

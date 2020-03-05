package main.budget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BudgetCalculator {
	
	private List<BudgetItem> items;
	private List<CalendarDay> selectedDays;
	private List<BudgetItemResult> results;
	private double start;
	
	public BudgetCalculator(List<BudgetItem> items, List<CalendarDay> selectedDays, double start) {
		this.items = items;
		this.selectedDays = selectedDays;
		this.start = start;
		this.results = new ArrayList<>();
	}
	
	public double getTotal() {
		double budgetTotal = getBudgetItemTotals();
		double total = budgetTotal + start;
		total = DoubleLimiter.limitToTwoDigitsAfterPeriod(total);
		return total;
	}
	
	public List<BudgetItemResult> getSortedBudgetItemResults() {
		Collections.sort(results);
		return results;
	} 
	
	private double getBudgetItemTotals() {
		double total = 0.0;
		for(CalendarDay day : selectedDays) {
			for(BudgetItem item : items) {
				if(item.shouldApply(day)) {
					double cost = getActualCost(item);
					total += cost;
					addCostToItemResult(item, cost);
				}
			}
		}
		return total;
	}
	
	private double getActualCost(BudgetItem item) {
		double cost = item.getCost();
		if(item.isNegative()) {
			cost *= -1;
		}
		if(item.getItemType() == BudgetItemType.SPREAD) {
			cost /= 30.0d;
			cost = DoubleLimiter.limitToTwoDigitsAfterPeriod(cost);
		}
		return cost;
	}
	
	private void addCostToItemResult(BudgetItem item, double cost) {
		for(BudgetItemResult result : results) {
			if(result.getItem().equals(item)) {
				result.addResult(cost);
				return;
			}
		}
		results.add(new BudgetItemResult(item, cost));
	}

}

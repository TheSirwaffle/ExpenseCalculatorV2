package main.ui;

import java.util.List;

import main.budget.BudgetItem;

public interface BudgetItemFetcher {
	
	public List<BudgetItem> getItems();

}

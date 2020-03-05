package main.budget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class BudgetCalculatorTest {
	
	private static CalendarDay day = new CalendarDay(1, Calendar.JANUARY, -1);
	private static BudgetItem item = new BudgetItem("test item", 100.0, new CalendarDay(1, -1, -1), BudgetItemType.MONTHLY);
	
	@Test
	public void testBudgetTotals() {
		List<BudgetItem> items = new ArrayList<>();
		items.add(item);
		List<CalendarDay> days = new ArrayList<>();
		days.add(day);
		BudgetCalculator calculator = new BudgetCalculator(items, days, 50);
		double result = calculator.getTotal();
		Assert.assertEquals(150.0d, result, .01);
	} 
	
	@Test
	public void testBudgetTotalsWithMiss() {
		List<BudgetItem> items = new ArrayList<>();
		items.add(item);
		List<CalendarDay> days = new ArrayList<>();
		days.add(new CalendarDay(25, -1, -1));
		BudgetCalculator calculator = new BudgetCalculator(items, days, 50);
		double result = calculator.getTotal();
		Assert.assertEquals(50.0d, result, .01d);
	}
	
	@Test
	public void testSpreadValue() {
		List<BudgetItem> items = new ArrayList<>();
		BudgetItem spreadItem = new BudgetItem("spread", 300, new ArrayList<>(), BudgetItemType.SPREAD);
		items.add(spreadItem);
		List<CalendarDay> days = new ArrayList<>();
		for(int i=0; i<15; i++) {
			CalendarDay day = new CalendarDay(i+1, -1, -1);
			days.add(day);
		}
		BudgetCalculator calculator = new BudgetCalculator(items, days, 0.0);
		double result = calculator.getTotal();
		Assert.assertEquals(150.0d, result, .1d);
	}

}
 
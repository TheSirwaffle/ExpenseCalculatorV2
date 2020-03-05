package main.budget;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

public class BudgetItemTest {
	private static int JANUARY = Calendar.JANUARY;
	private static CalendarDay TEST_DAY_ONE = new CalendarDay(1, JANUARY, 1);
	private static CalendarDay END_OF_FEBRUARY = new CalendarDay(28, Calendar.FEBRUARY, 2019);
	
	@Test
	public void testCalendarDayMatches() {
		 CalendarDay day = new CalendarDay(1, JANUARY, 1);
		 Assert.assertEquals(true, day.isSameDay(TEST_DAY_ONE));
	}
	
	@Test
	public void testComplexCalendarDayMatches() {
		CalendarDay day = new CalendarDay(1, JANUARY, -1);
		Assert.assertEquals(true, day.isSameDay(TEST_DAY_ONE));
		CalendarDay day2 = new CalendarDay(1, -1, -1);
		Assert.assertEquals(true, day2.isSameDay(TEST_DAY_ONE));
	}
	
	@Test
	public void lastDayTest() {
		CalendarDay day = new CalendarDay(30, -1, -1);
		Assert.assertEquals(true, day.isSameDay(END_OF_FEBRUARY));
		CalendarDay day2 = new CalendarDay(31, -1, -1);
		Assert.assertEquals(true, day2.isSameDay(END_OF_FEBRUARY));
	}
	
	@Test
	public void wontTriggerTest() {
		CalendarDay day = new CalendarDay(25, -1, -1);
		Assert.assertEquals(false, day.isSameDay(END_OF_FEBRUARY));
	}
	
	@Test
	public void weeklyItemTest() {
		CalendarDay day = new CalendarDay(11, Calendar.FEBRUARY, 2020);
		day.setType(BudgetItemType.WEEKLY);
		CalendarDay nextWeek = new CalendarDay(18, Calendar.FEBRUARY, 2020);
		nextWeek.setType(BudgetItemType.WEEKLY);
		CalendarDay nextMonthSameWeekday = new CalendarDay(17, Calendar.MARCH, 2020);
		nextMonthSameWeekday.setType(BudgetItemType.WEEKLY);
		CalendarDay sameDayDifferentWeekday = new CalendarDay(11, Calendar.MARCH, 2020);
		sameDayDifferentWeekday.setType(BudgetItemType.WEEKLY);
		Assert.assertEquals(true, day.isSameDay(nextWeek));
		Assert.assertEquals(true, day.isSameDay(nextMonthSameWeekday));
		Assert.assertEquals(false, day.isSameDay(sameDayDifferentWeekday));
	}
	
	@Test
	public void testBasicBudgetItem() {
		CalendarDay day = new CalendarDay(1, -1, -1);
		BudgetItem item = new BudgetItem("test item", 100, day, BudgetItemType.MONTHLY);
		Assert.assertEquals(true, item.shouldApply(TEST_DAY_ONE));
	}

}

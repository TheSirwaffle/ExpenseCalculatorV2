package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import main.budget.CalendarDay;
import main.ui.budget.BudgetView;
import main.ui.calculate.CalculationView;
import main.ui.calculate.CalendarDaySelector;
import main.ui.calendar.CalendarView;

public class MainWindow {
	
	private static JFrame frame;
	private static CalendarDaySelector selector;
	private static CalendarView calendar;
	
	public static void init(int width, int height) {
		frame = new JFrame("Expense Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		JPanel content = new JPanel(new BorderLayout());
		selector = new CalendarDaySelector();
		calendar = new CalendarView(selector);
		calendar.disablePastDays();
		calendar.addDayListener(dayListener);
		BudgetView budgetView = new BudgetView();
		CalculationView calculation = new CalculationView(selector, budgetView);
		JPanel center = new JPanel(new BorderLayout());
		center.add(calendar.getPanel(), BorderLayout.CENTER);
		center.add(budgetView.getPanel(), BorderLayout.SOUTH);
		content.add(center, BorderLayout.CENTER);
		content.add(calculation.getPanel(), BorderLayout.EAST);
		frame.setContentPane(content);
		frame.setBackground(Color.black);
		paintComponents(frame.getComponents());
	}
	
	private static void paintComponents(Component[] components) {
		if(components.length > 0) {
			for(Component component : components) {
				if(component instanceof JComponent) {
					JComponent jcomponent = (JComponent) component;
					paintComponents(jcomponent.getComponents());
					if(jcomponent.getBackground() == UIManager.getColor("Panel.background")) {
//						jcomponent.setBackground(ColorUtils.BACKGROUND);
					}
				}
			}
		}
	}
	
	public static void show() {
		frame.setVisible(true); 
	}
	
	public static JFrame getFrame() {
		return frame;
	}
	
	private static DayListener dayListener = new DayListener() {
		
		@Override
		public void onClick(CalendarDay day) {
			List<CalendarDay> selectedDays = selector.getAllSelectedDays();
			calendar.setSelectedDays(selectedDays);
		}
	};

}

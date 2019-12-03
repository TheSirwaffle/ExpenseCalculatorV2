package main.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class CalendarView extends UIContent {
	
	private DaysView view;
	private MonthButtonPanel monthPanel;
	
	public CalendarView() {
		super(new BorderLayout());
	}
	
	@Override
	protected void setupPanel(JPanel panel) {
		view = new DaysView();
		monthPanel = new MonthButtonPanel(this::onClick);
		panel.add(monthPanel.getPanel(), BorderLayout.NORTH);
		panel.add(view.getPanel(), BorderLayout.CENTER);
	}
	
	private void onClick(Direction direction) {
		if(direction == Direction.BACK) {
			view.moveBackwardOneMonth();
		}else if(direction == Direction.FORWARD) {
			view.moveForwardOneMonth();
		}
		monthPanel.changeMonth(view.getMonth(), view.getYear());
	}

}

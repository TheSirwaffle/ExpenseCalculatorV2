package main.ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import main.ui.ColorUtils;
import main.ui.FontUtils;
import main.ui.UIContent;

public class MonthComboBox extends UIContent {
	

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
	
	private JComboBox<String> monthBox;
	private CalendarInfo calendarInfo;
	private Consumer<CalendarInfo> onSelected;
	private boolean updating;
	
	public MonthComboBox(Consumer<CalendarInfo> onSelected) {
		super(new BorderLayout());
		this.onSelected = onSelected;
		updating = false;
	}
	
	@Override
	protected void setupPanel(JPanel panel) {
		monthBox = new JComboBox<String>();
		monthBox.setFont(FontUtils.FONT);
		monthBox.setBackground(ColorUtils.BACKGROUND);
		monthBox.setBorder(BorderFactory.createLineBorder(Color.black));
		monthBox.addItemListener(itemListener);
		setToCurrentMonth();
		panel.add(monthBox, BorderLayout.CENTER);
	}
	
	private void setToCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		calendarInfo = new CalendarInfo(month, year);
		safeUpdate();
		changeMonth(month, year);
	}
	
	public void changeMonth(int month, int year) {
		calendarInfo = new CalendarInfo(month, year);
		String viewString = createTextVersion(month, year);
		monthBox.setSelectedItem(viewString);
		monthBox.setBackground(ColorUtils.BACKGROUND);
		monthBox.repaint();
	}
	
	private String createTextVersion(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		String viewString = dateFormat.format(calendar.getTime());
		return viewString;
	}
	
	private void safeUpdate() {
		updating = true;
		updateComboBoxValues();
		updating = false;
	}
	
	private void updateComboBoxValues() {
		monthBox.removeAllItems();
		int month = calendarInfo.getMonth();
		int year = calendarInfo.getYear();
		int startingYear = year - 1;
		for(int i=0; i < 25; i++) {
			int monthToUse = (month + i) % 12;
			int yearToUse = (int) ((month + i) / 12 ) + startingYear;
			String viewString = createTextVersion(monthToUse, yearToUse);
			monthBox.addItem(viewString);
		}
		monthBox.setSelectedItem(createTextVersion(month, year));
	}
	
	private ItemListener itemListener = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(!updating) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("Selected: " + e.getItem().toString());
					determineCalendarInfo(e.getItem().toString());
					safeUpdate();
					onSelected.accept(calendarInfo);
				}
			}
		}
		
		private void determineCalendarInfo(String selected) {
			try {
				Date date = dateFormat.parse(selected);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int month = calendar.get(Calendar.MONTH);
				int year = calendar.get(Calendar.YEAR);
				calendarInfo = new CalendarInfo(month, year);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
	};

}

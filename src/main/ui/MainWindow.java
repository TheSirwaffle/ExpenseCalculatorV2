package main.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow {
	
	private JFrame frame;
	
	public MainWindow(int width, int height) {
		frame = new JFrame("Expense Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		JPanel content = new JPanel(new BorderLayout());
		CalendarView calendar = new CalendarView();
		content.add(calendar.getPanel(), BorderLayout.CENTER);
		frame.setContentPane(content);
	}
	
	public void show() {
		frame.setVisible(true);
	}

}

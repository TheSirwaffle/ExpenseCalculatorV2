package main.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MonthButtonPanel extends UIContent {
	
	private static final Font FONT = new Font("Helvetica", Font.BOLD, 15);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM YYYY");
	
	private Consumer<Direction> onClick;
	private JLabel label;
	
	public MonthButtonPanel(Consumer<Direction> onClick) {
		super(new GridLayout(1, 5));
		this.onClick = onClick;
	}

	@Override
	protected void setupPanel(JPanel panel) {
		JPanel centerPanel = new JPanel(new BorderLayout());
		ClickableLabel backButton = createDirectionButton(Direction.BACK);
		label = new JLabel("February 2019");
		label.setFont(FONT);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		ClickableLabel forwardButton = createDirectionButton(Direction.FORWARD);
		centerPanel.add(backButton, BorderLayout.WEST);
		centerPanel.add(label, BorderLayout.CENTER);
		centerPanel.add(forwardButton, BorderLayout.EAST);
		panel.add(Box.createHorizontalStrut(10));
		panel.add(Box.createHorizontalStrut(10));
		panel.add(centerPanel);
		panel.add(Box.createHorizontalStrut(10));
		panel.add(Box.createHorizontalStrut(10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
	}
	
	private ClickableLabel createDirectionButton(Direction direction) {
		ClickableLabel label = new ClickableLabel(40, 20, direction.getText());
		label.setFocusable(false);
		label.addActionListener(actionListener);
		return label;
	}
	
	public void changeMonth(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		String viewString = dateFormat.format(calendar.getTime());
		label.setText(viewString);
		label.repaint();
	}
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String text = e.getActionCommand();
			Direction direction = Direction.fromText(text);
			onClick.accept(direction);
		}
	};

}

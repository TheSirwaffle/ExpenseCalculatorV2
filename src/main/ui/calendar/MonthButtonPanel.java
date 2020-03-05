package main.ui.calendar;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.ui.ColorUtils;
import main.ui.ColoredButton;
import main.ui.FontUtils;
import main.ui.UIContent;

public class MonthButtonPanel extends UIContent {
	
	private Consumer<Direction> onClick;
	private Consumer<CalendarInfo> onSelected;
	private MonthComboBox monthBox;
	
	public MonthButtonPanel(Consumer<Direction> onClick, Consumer<CalendarInfo> onSelected) {
		super(new GridBagLayout(), onSelected);
		this.onClick = onClick;
	}

	@Override
	protected void setupPanel(JPanel panel) {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
		ColoredButton backButton = createDirectionButton(Direction.BACK);
		createLabel();
		ColoredButton forwardButton = createDirectionButton(Direction.FORWARD);
		centerPanel.add(backButton);
		centerPanel.add(Box.createHorizontalStrut(5));
		centerPanel.add(monthBox.getPanel());
		centerPanel.add(Box.createHorizontalStrut(5));
		centerPanel.add(forwardButton);
		panel.add(centerPanel);
		centerPanel.setBackground(ColorUtils.BACKGROUND);
		panel.setBackground(ColorUtils.BACKGROUND);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
	}
	
	private void createLabel() {
		monthBox = new MonthComboBox(getArg(0));
		Dimension size = new Dimension(250, 34);
		JPanel panel = monthBox.getPanel();
		panel.setPreferredSize(size);
		panel.setMinimumSize(size);
		panel.setMaximumSize(size);
	}
	
	private ColoredButton createDirectionButton(Direction direction) {
		ColoredButton button = new ColoredButton(ColorUtils.BACKGROUND, direction.getText());
		button.setFocusable(false);
		button.setFont(FontUtils.FONT);
		button.addActionListener(actionListener);
		return button;
	}
	
	public void changeMonth(int month, int year) {
		monthBox.changeMonth(month, year);
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

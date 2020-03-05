package main.ui.calculate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.PlainDocument;

import main.budget.BudgetCalculator;
import main.budget.BudgetItem;
import main.budget.BudgetItemResult;
import main.budget.CalendarDay;
import main.ui.BudgetItemFetcher;
import main.ui.ColorUtils;
import main.ui.FontUtils;
import main.ui.UIContent;
import main.ui.budget.MoneyFilter;

public class CalculationView extends UIContent {
	
	private CalendarDaySelector daySelector;
	private BudgetItemFetcher budgetItemFetcher;
	private JTextField initialMoney;
	private JLabel result;
	private JPanel breakdown;
	
	public CalculationView(CalendarDaySelector daySelector, BudgetItemFetcher budgetItemFetcher) {
		super(new BorderLayout());
		this.daySelector = daySelector; 
		this.daySelector.setOnDaysSelected(this::onDaysSelected);
		this.budgetItemFetcher = budgetItemFetcher;
	}

	@Override
	protected void setupPanel(JPanel panel) { 
		JPanel north = createInitialMoney();
		JPanel result = createResultPanel();
		panel.add(north, BorderLayout.NORTH);
		panel.add(result, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));
		panel.setBackground(ColorUtils.BACKGROUND);
 	}
	
	private JPanel createInitialMoney() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Starting Money:");
		label.setForeground(Color.black);
		label.setFont(FontUtils.FONT);
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
		createInitialMoneyField();
		panel.add(label, BorderLayout.WEST);
		panel.add(initialMoney, BorderLayout.EAST);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		panel.setBackground(ColorUtils.BACKGROUND);
		return panel;
	}
	
	private void createInitialMoneyField() {
		initialMoney = new JTextField("0.0", 8);
		initialMoney.setFont(FontUtils.FONT);
		Border outer = BorderFactory.createLineBorder(Color.black);
		Border inner = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		initialMoney.setBorder(BorderFactory.createCompoundBorder(outer, inner));
		initialMoney.setBackground(Color.white);
		initialMoney.setForeground(Color.black);
		PlainDocument doc = (PlainDocument) initialMoney.getDocument();
		doc.setDocumentFilter(new MoneyFilter());
	}
	
	private JPanel createResultPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		breakdown = new JPanel();
		breakdown.setLayout(new BoxLayout(breakdown, BoxLayout.Y_AXIS));
		JScrollPane scroll = new JScrollPane(breakdown);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(createTotalPanel(), BorderLayout.NORTH);
		scroll.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		panel.add(scroll, BorderLayout.CENTER);
		scroll.setBackground(ColorUtils.CALCULATION_BACKGROUND);
		breakdown.setBackground(ColorUtils.CALCULATION_BACKGROUND);
		Border inner = BorderFactory.createLineBorder(Color.black);
		Border outer = BorderFactory.createEmptyBorder(20, 0, 5, 0);
		panel.setBorder(BorderFactory.createCompoundBorder(outer, inner));
		panel.setBackground(ColorUtils.BACKGROUND);
		return panel;
	}
	
	private JPanel createTotalPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Total: ");
		label.setForeground(Color.black);
		result = new JLabel("0.0");
		result.setForeground(Color.black);
		label.setFont(FontUtils.FONT);
		result.setFont(FontUtils.FONT);
		result.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		panel.add(label, BorderLayout.WEST);
		panel.add(result, BorderLayout.EAST);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 0));
		panel.setBackground(ColorUtils.CALCULATION_BACKGROUND);
		return panel;
	}
	
	private double getInitialMoney() {
		String text = initialMoney.getText();
		double startingMoney = 0.0;
		try {
			startingMoney = Double.parseDouble(text);
		}catch (NumberFormatException e) {}
		return startingMoney;
	}
	
	private void onDaysSelected(List<CalendarDay> days) {
		List<BudgetItem> items = budgetItemFetcher.getItems();
		BudgetCalculator calculator = new BudgetCalculator(items, days, getInitialMoney());
		result.setText("" + calculator.getTotal());
		addBudgetItemResults(calculator.getSortedBudgetItemResults());
		result.revalidate();
		result.repaint();
	}
	
	private void addBudgetItemResults(List<BudgetItemResult> budgetItemResults) {
		breakdown.removeAll();
		if(budgetItemResults.size() > 0) {
			for(BudgetItemResult result : budgetItemResults) {
				BudgetItem item = result.getItem();
				double cost = result.getResult();
				JPanel panel = createItemResultPanel(item, cost);
				breakdown.add(panel);
			}
		}
		breakdown.revalidate();
		breakdown.repaint();
	}
	
	private JPanel createItemResultPanel(BudgetItem item, double result) {
		JPanel panel = new JPanel(new BorderLayout());
		Font font = FontUtils.FONT.deriveFont(15);
		JLabel name = new JLabel(item.getName());
		name.setFont(font);
		panel.add(name, BorderLayout.WEST);
		JLabel cost = new JLabel("" + result);
		cost.setFont(font);
		name.setForeground(Color.black);
		cost.setForeground(Color.black);
		name.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		cost.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		panel.add(cost, BorderLayout.EAST);
		Dimension size = new Dimension(525, 25);
		panel.setPreferredSize(new Dimension(100, 25));
		panel.setMinimumSize(new Dimension(100, 25));
		panel.setMaximumSize(size);
		panel.setBackground(ColorUtils.CALCULATION_BACKGROUND);
		return panel;
	}

}

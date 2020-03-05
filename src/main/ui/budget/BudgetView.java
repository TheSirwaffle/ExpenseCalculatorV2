package main.ui.budget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import main.budget.BudgetItem;
import main.budget.FullBudgetInfo;
import main.ui.BudgetItemFetcher;
import main.ui.ColorUtils;
import main.ui.ColoredButton;
import main.ui.DialogResult;
import main.ui.UIContent;
import main.ui.WindowPlacer;

public class BudgetView extends UIContent implements BudgetItemFetcher {
	
	private JPanel incomePanel;
	private JPanel expensePanel;
	private ArrayList<BudgetItem> budgetItems;

	public BudgetView() {
		super(new GridLayout(1,  2));
		budgetItems = new ArrayList<>();
	}
	
	private void load() {
		FullBudgetInfo info = FullBudgetInfo.load();
		for(BudgetItem item : info.getIncomes()) {
			BudgetLineItem lineItem = new BudgetLineItem(item, this::editItem, this::deleteItem);
			if(item.isNegative()) {
				addItemToExpenses(lineItem);
			} else {
				addItemToIncomes(lineItem);
			}
		}
	}

	@Override
	protected void setupPanel(JPanel panel) {
		incomePanel = new JPanel();
		expensePanel = new JPanel();
		JPanel income = createSide("Income", incomePanel);
		JPanel expense = createSide("Expense", expensePanel);
		panel.add(income);
		panel.add(expense);
		load();
	}
	
	private JPanel createSide(String text, JPanel scrolling) {
		JPanel side = new JPanel();
		side.setBackground(ColorUtils.BACKGROUND);
		side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		ColoredButton button = createButton("Add " + text);
		buttonPanel.add(button);
		buttonPanel.setBackground(ColorUtils.BACKGROUND);
		side.add(buttonPanel);
		scrolling.setLayout(new BoxLayout(scrolling, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(scrolling);
		Dimension size = new Dimension(1920, 200);
		scrollPane.setPreferredSize(size);
		scrollPane.setMinimumSize(size);
		scrollPane.setMaximumSize(size);
		scrollPane.setBackground(ColorUtils.BACKGROUND);
		scrolling.setBackground(ColorUtils.CALCULATION_BACKGROUND);
		side.add(scrollPane);
		Border inner = BorderFactory.createLineBorder(Color.black);
		Border outer = BorderFactory.createEmptyBorder(2, 5, 5, 5);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(outer, inner));
		return side;
	}
	
	private ColoredButton createButton(String text) {
		ColoredButton button = new ColoredButton(Color.DARK_GRAY, text);
		button.setActionCommand(text);
		button.addActionListener(actionListener);
		return button;
	}
	
	private void addItemToIncomes(BudgetLineItem item) {
		budgetItems.add(item.getItem());
		incomePanel.add(item.getPanel());
		incomePanel.revalidate();
		incomePanel.repaint();
	}
	
	private void addItemToExpenses(BudgetLineItem lineItem) {
		BudgetItem item = lineItem.getItem();
		item.flagNegative();
		budgetItems.add(item);
		expensePanel.add(lineItem.getPanel());
		expensePanel.revalidate();
		expensePanel.repaint();
	}
	
	private void save() {
		FullBudgetInfo info = new FullBudgetInfo(budgetItems);
		FullBudgetInfo.save(info);
	}
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			BudgetItemDialog dialog = new BudgetItemDialog();
			JDialog jdialog = dialog.getDialog();
			Point point = WindowPlacer.getCenterLocation(jdialog);
			jdialog.setLocation(point); 
			jdialog.setVisible(true);
			jdialog.toFront();
			BudgetItem item = dialog.getBudgetItem();
			if(item != null) {
				BudgetLineItem lineItem = new BudgetLineItem(item, BudgetView.this::editItem, BudgetView.this::deleteItem);
				if(e.getActionCommand().equals("Add Income")) {
					addItemToIncomes(lineItem);
					save();
				}else if(e.getActionCommand().equals("Add Expense")) {
					addItemToExpenses(lineItem);
					save();
				}
			}
		}
		
	};
	
	private void editItem(BudgetLineItem lineItem) {
		BudgetItemDialog dialog = new BudgetItemDialog(lineItem.getItem());
		setupDialog(dialog);
		if(dialog.getResult() == DialogResult.ADD) {
			budgetItems.remove(lineItem.getItem());
			budgetItems.add(dialog.getBudgetItem());
			lineItem.setItem(dialog.getBudgetItem());
			save();
		}
	}
	
	private void setupDialog(BudgetItemDialog dialog) {
		JDialog jdialog = dialog.getDialog();
		Point point = WindowPlacer.getCenterLocation(jdialog);
		jdialog.setLocation(point); 
		jdialog.setVisible(true);
		jdialog.toFront();
	}
	
	private void deleteItem(BudgetLineItem lineItem) {
		BudgetItem item = lineItem.getItem();
		int result = JOptionPane.showConfirmDialog(getPanel(), "Are you sure you want to delete " + item.getName() + "?", "Delete Item?", JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION) {
			removeItem(lineItem);
			save();
		}
	}
	
	private void removeItem(BudgetLineItem item) {
		budgetItems.remove(item.getItem());
		incomePanel.remove(item.getPanel());
		incomePanel.revalidate();
		incomePanel.repaint();
		expensePanel.remove(item.getPanel());
		expensePanel.revalidate();
		expensePanel.repaint();
	}
	 
	@Override
	public List<BudgetItem> getItems() {
		return budgetItems;
	}}

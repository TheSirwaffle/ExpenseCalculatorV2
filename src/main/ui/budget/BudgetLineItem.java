package main.ui.budget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.budget.BudgetItem;
import main.ui.ColorUtils;
import main.ui.ColoredButton;
import main.ui.UIContent;

public class BudgetLineItem extends UIContent {
	
	private BudgetItem item;
	private Consumer<BudgetLineItem> onEdit;
	private Consumer<BudgetLineItem> onDelete;
	private JLabel name;
	private JLabel cost;
	
	public BudgetLineItem(BudgetItem item, Consumer<BudgetLineItem> onEdit, Consumer<BudgetLineItem> onDelete) {
		super(new BorderLayout());
		this.item = item;
		this.onEdit = onEdit;
		this.onDelete = onDelete;
	}

	@Override
	protected void setupPanel(JPanel panel) {
		JPanel left = createLeftPanel();
		JPanel right = createRightPanel();
		left.setBackground(ColorUtils.CALCULATION_BACKGROUND);
		right.setBackground(ColorUtils.CALCULATION_BACKGROUND);
		panel.add(left, BorderLayout.CENTER);
		panel.add(right, BorderLayout.EAST);
		resize(panel);
	}
	
	private JPanel createLeftPanel() {
		JPanel left = new JPanel(new BorderLayout());
		name = new JLabel(item.getName());
		cost = new JLabel("" + item.getCost());
		name.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		cost.setForeground(Color.white);
		cost.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
		name.setForeground(Color.white);
		left.add(name, BorderLayout.WEST);
		left.add(cost, BorderLayout.EAST);
		return left;
	}
	
	private JPanel createRightPanel() {
		JPanel right = new JPanel(new BorderLayout());
		JPanel edit = createButtonPanel("Edit", Color.white);
		JPanel delete = createButtonPanel("Delete", new Color(199, 99, 99));
		right.add(edit, BorderLayout.WEST);
		right.add(delete, BorderLayout.EAST);
		return right;
	}
	
	private JPanel createButtonPanel(String text, Color color) {
		JPanel panel = new JPanel(new BorderLayout());
		ColoredButton button = new ColoredButton(color, text);
		button.setActionCommand(text);
		button.addActionListener(actionListener);
		panel.add(button, BorderLayout.WEST);
		panel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 5));
		panel.setBackground(ColorUtils.CALCULATION_BACKGROUND);
		return panel;
	}
	
	private void resize(JPanel panel) {
		panel.setPreferredSize(new Dimension(400, 25));
		panel.setMinimumSize(new Dimension(200, 25));
		panel.setMaximumSize(new Dimension(1920, 25));
	}
	
	public BudgetItem getItem() {
		return item;
	}
	
	public void setItem(BudgetItem item) {
		this.item = item;
		name.setText(item.getName());
		cost.setText(item.getCost() + "");
	}
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Edit")) {
				onEdit.accept(BudgetLineItem.this);
			}else if(e.getActionCommand().equals("Delete")) {
				onDelete.accept(BudgetLineItem.this);
			}
		}
	};

}

package main.ui.budget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import main.budget.BudgetItem;
import main.budget.BudgetItemType;
import main.ui.ColoredButton;
import main.ui.DialogResult;

public class BudgetItemDialog {
	
	private static final Dimension SIZE = new Dimension(325, 25);
	private JDialog dialog;
	private JTextField name;
	private JTextField cost;
	private JComboBox<BudgetItemType> box;
	private DialogResult result;
	private BudgetItemDayView dayView;
	private BudgetItem item;
	private ColoredButton confirm;
	
	public BudgetItemDialog() {
		dialog = new JDialog();
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dayView = new BudgetItemDayView();
		JPanel panel = setupPanel();
		dialog.setContentPane(panel);
		dialog.setSize(350, 200);
	}
	
	public BudgetItemDialog(BudgetItem item) {
		this();
		this.item = item;
		name.setText(item.getName());
		cost.setText(item.getCost() + "");
		box.setSelectedItem(item.getItemType());
		dayView.setSelectedDays(item.getDays());
		confirm.setText("Save");
	}
	
	private JPanel setupPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
		createTypeField(northPanel);
		createNameField(northPanel);
		createCostField(northPanel);
		createDaysField(northPanel);
		JPanel southPanel = createButtonPanel();
		panel.add(northPanel, BorderLayout.NORTH);
		panel.add(southPanel, BorderLayout.SOUTH);
		return panel;
	}
	
	private void createTypeField(JPanel panel) {
		box = new JComboBox<BudgetItemType>();
		box.setBackground(Color.white);
		box.addActionListener(actionListener);
		for(BudgetItemType type : BudgetItemType.values()) {
			box.addItem(type);
		}
		JPanel comboPanel = createInternalPanel("Frequency", box);
		panel.add(Box.createVerticalStrut(5));
		panel.add(comboPanel);
	}
	
	private void createNameField(JPanel panel) {
		name = new JTextField(20);
		JPanel namePanel = createInternalPanel("Name", name);
		panel.add(namePanel);
	}
	
	private void createCostField(JPanel panel) {
		cost = new JTextField(20);
		PlainDocument doc = (PlainDocument) cost.getDocument();
		doc.setDocumentFilter(new MoneyFilter());
		JPanel costPanel = createInternalPanel("Cost", cost);
		panel.add(costPanel);
	}
	
	private void createDaysField(JPanel panel) {
		JPanel dayPanel = dayView.getPanel();
		resizeComponent(dayPanel);
		panel.add(dayPanel);
	}
	
	private JPanel createInternalPanel(String labelText, JComponent component) {
		JPanel internalPanel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(labelText +":");
		resizeComponent(internalPanel);
		internalPanel.add(label, BorderLayout.WEST);
		internalPanel.add(component, BorderLayout.EAST);
		internalPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		return internalPanel;
	}
	
	private JPanel createButtonPanel() {
		confirm = createButton("Add");
		ColoredButton cancel = createButton("Cancel"); 
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(confirm, BorderLayout.WEST);
		buttonPanel.add(cancel, BorderLayout.EAST); 
		resizeComponent(buttonPanel);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		return buttonPanel;
	}
	
	private ColoredButton createButton(String text) {
		ColoredButton button = new ColoredButton(Color.white, text);
		button.setActionCommand(text);
		button.addActionListener(actionListener);
		return button;
	}
	
	private void resizeComponent(JComponent component) {
		component.setPreferredSize(SIZE);
		component.setMaximumSize(SIZE);
		component.setMinimumSize(SIZE);
	}
	
	public JDialog getDialog() {
		return dialog;
	}
	
	public DialogResult getResult() {
		return result;
	}
	
	public BudgetItem getBudgetItem() {
		return item;
	}
	
	private BudgetItem generateBudgetItem() {
		String itemName = name.getText().trim();
		double itemCost = getItemCost(); 
		BudgetItemType itemType = (BudgetItemType) box.getSelectedItem();
		BudgetItem item = new BudgetItem(itemName, itemCost, dayView.getSelectedDays(), itemType);
		return item;
	}
	
	private double getItemCost() {
		double result = -1;
		try {
			result = Double.parseDouble(cost.getText());
		}catch (NumberFormatException e) {}
		return result;
	}
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Add")) {
				if(isValid()) {
					item = generateBudgetItem();
					finish(DialogResult.ADD);
				}
			}else if(e.getActionCommand().equals("Cancel")) {
				item = null;
				finish(DialogResult.CANCEL);
			}else if(e.getSource() == box) {  
				dayView.onBudgetItemTypeChanged((BudgetItemType) box.getSelectedItem());
			}
		}
		
		private boolean isValid() {
			BudgetItem item = generateBudgetItem();
			boolean isNameValid = !item.getName().isEmpty();
			boolean isCostValid = item.getCost() >= 0;
			boolean areDaysValid = !item.getDays().contains(null);
			popValidationDialogs(isNameValid, isCostValid, areDaysValid);
			return isNameValid && isCostValid && areDaysValid;
		}
		
		private void popValidationDialogs(boolean isNameValid, boolean isCostValid, boolean areDaysValid) {
			StringBuilder builder = new StringBuilder();
			if(!isNameValid) {
				builder.append("You must enter a name.\n");
			}
			if(!isCostValid) {
				builder.append("You must enter a cost.\n");
			}
			if(!areDaysValid) {
				builder.append("You need to select some days.\n");
			}
			if(!builder.toString().isEmpty()) {
				JOptionPane.showMessageDialog(BudgetItemDialog.this.dialog, builder.toString(), "", JOptionPane.ERROR_MESSAGE, null);
			}
		}
		
		private void finish(DialogResult result) {
			BudgetItemDialog.this.result = result;
			dialog.setVisible(false);
		}
	};
}

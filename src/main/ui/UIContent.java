package main.ui;

import java.awt.LayoutManager;

import javax.swing.JPanel;

public abstract class UIContent {
	
	private JPanel panel;
	private LayoutManager layout;
	private Object[] args;
	
	public UIContent(LayoutManager layout, Object... args) {
		this.layout = layout;
		this.args = args;
	}
	
	protected <T> T getArg(int index) {
		return (T) args[index];
	}
	
	public JPanel getPanel() {
		if(panel == null) {
			panel = new JPanel(layout);
			setupPanel(panel);
		}
		return panel;
	}
	
	protected abstract void setupPanel(JPanel panel);
	

}

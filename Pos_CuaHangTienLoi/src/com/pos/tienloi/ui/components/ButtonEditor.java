package com.pos.tienloi.ui.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class ButtonEditor extends DefaultCellEditor implements ActionListener {
	private JButton button;
	private String label;
	private boolean isPushed;
	private int row;

	private ActionListener listener;
	private String action; // EDIT / DELETE

	public ButtonEditor(JCheckBox checkBox, String label, String action, ActionListener listener) {
		super(checkBox);
		this.label = label;
		this.listener = listener;
		this.action = action;

		button = new JButton(label);
		button.addActionListener(this);
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.row = row;
		isPushed = true;
		return button;
	}

	@Override
	public Object getCellEditorValue() {
		return label;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fireEditingStopped();

		if (listener != null) {
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, action + ":" + row));
		}
	}
}
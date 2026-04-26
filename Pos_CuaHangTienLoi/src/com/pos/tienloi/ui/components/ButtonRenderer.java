package com.pos.tienloi.ui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer {
	private String text;
	private Icon icon;
	private final Color NORMAL_COLOR = Color.decode("#EAF4FF");
	private final Color TEXT_Color = Color.decode("#1F3A5F");
	private final Color HOVER_COLOR = Color.decode("#4A90E2");

	public ButtonRenderer(String text) {
		this.text = text;
		this.icon = null;
		init();
	}

	public ButtonRenderer(Icon icon) {
		this.icon = icon;
		this.text = "";
		init();
	}

	public ButtonRenderer(String text, Icon icon) {
		this.text = text;
		this.icon = icon;
		init();
	}

	private void init() {
		setOpaque(true);
		setHorizontalAlignment(CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		setText(text); // có thể để "" nếu chỉ muốn icon
		setIcon(icon); // set icon tại đây

		if (isSelected) {
			setBackground(table.getSelectionBackground());
		} else {
			setBackground(TEXT_Color); // hoặc NORMAL_COLOR
		}

		return this;
	}
}
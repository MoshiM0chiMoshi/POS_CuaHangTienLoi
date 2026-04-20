package com.pos.tienloi.ui.components;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText("Edit"); 
        return this;
    }
}
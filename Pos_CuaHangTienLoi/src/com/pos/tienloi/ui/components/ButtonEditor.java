package com.pos.tienloi.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox, String label) {
        super(checkBox);
        this.label = label;
        button = new JButton();
        button.setOpaque(true);
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // Bắt buộc phải có để báo cho bảng biết đã click xong
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, 
            boolean isSelected, int row, int column) {
        button.setText(label);
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            // NƠI NÀY LÀ ĐỂ CODE HIỆN FORM EDIT
            // Tạm thời dùng JOptionPane để test, sau này thay bằng JDialog của bạn
            JOptionPane.showMessageDialog(button, "Mở Panel Edit cho UI!");
            
            // Ví dụ:
            // EditDialog dialog = new EditDialog();
            // dialog.setVisible(true);
        }
        isPushed = false;
        return label;
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
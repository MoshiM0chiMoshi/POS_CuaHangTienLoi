package com.pos.tienloi.ui.components;

import javax.swing.*;
import java.awt.*;

public class SideBar extends JPanel {

	public SideBar() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(318, 800));
		setBackground(new Color(107, 61, 36, 1));

		// Tạo logo
		ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/logo.png"));
		Image imgLogo = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		JLabel logoLabel = new JLabel(new ImageIcon(imgLogo));
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		add(Box.createVerticalStrut(1)); // khoảng cách trên
		add(logoLabel);
		add(Box.createVerticalStrut(5)); // khoảng cách dưới

		add(createButton("Dashboard"));
		add(Box.createVerticalStrut(16));
		add(createButton("Khách Hàng"));
		add(Box.createVerticalStrut(16));
		add(createButton("Sản phẩm"));
		add(Box.createVerticalStrut(16));
		add(createButton("Hóa Đơn"));
		add(Box.createVerticalStrut(16));
		add(createButton("Nhân Viên"));
		add(Box.createVerticalStrut(150));
		add(createButton("Log Out"));

	}

	private JButton createButton(String Text) {
		JButton btn = new JButton(Text);
		
	

		
		
		btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btn.setForeground(new Color(255, 255, 255, 1));
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.setMaximumSize(new Dimension(264, 52));
		btn.setFocusPainted(false);
		return btn;

	}

	public static void main(String[] args) {
		new SideBar().setVisible(true);

	}

}

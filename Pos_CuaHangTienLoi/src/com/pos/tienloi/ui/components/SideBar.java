package com.pos.tienloi.ui.components;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class SideBar extends JPanel {

	private final Color NORMAL_COLOR = Color.decode("#EAF4FF");
	private final Color TEXT_Color = Color.decode("#1F3A5F");
	private final Color HOVER_COLOR = Color.decode("#4A90E2");

	public SideBar() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(318, 800));
		setBackground(NORMAL_COLOR);

		// Tạo logo
		ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/logo.png"));
		Image imgLogo = logoIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		JLabel logoLabel = new JLabel(new ImageIcon(imgLogo));
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		add(Box.createVerticalStrut(1)); // khoảng cách trên
		add(logoLabel);

		add(createButton("Dashboard"));
		add(Box.createVerticalStrut(10));
		add(createButton("Khách Hàng"));
		add(Box.createVerticalStrut(10));
		add(createButton("Sản phẩm"));
		add(Box.createVerticalStrut(10));
		add(createButton("Hóa Đơn"));
		add(Box.createVerticalStrut(10));
		add(createButton("Phiếu Đặt Hàng"));
		add(Box.createVerticalStrut(10));
		add(createButton("Nhân Viên"));
		add(Box.createVerticalStrut(80));
		add(createButton("Log Out"));

	}

	private JButton createButton(String Text) {
		JButton btn = new JButton(Text);

		btn.setFont(new Font("Segoe UI", Font.BOLD, 30));
		btn.setForeground(TEXT_Color);
		btn.setBackground(NORMAL_COLOR);

		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.setMaximumSize(new Dimension(264, 52));

		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setOpaque(true);



		// Hover effect
		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btn.setBackground(HOVER_COLOR);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btn.setBackground(NORMAL_COLOR);
			}
		});

		return btn;

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test Sidebar");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 800);

		frame.add(new SideBar());

		frame.setVisible(true);
	}

}

package com.pos.tienloi.ui;

import javax.swing.*;

import com.pos.tienloi.ui.components.SideBar;

import java.awt.*;

public class MainFrame extends JFrame {
	private CardLayout cardLayout;
	private JPanel contentPanel;

	public MainFrame() {
		setTitle("Pos - Cửa Hàng Tiện Lợi");
		setSize(1300, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());



		cardLayout = new CardLayout();
		contentPanel = new JPanel(cardLayout);

		contentPanel.add(new FrmDashBoard(), "dashboard");
		contentPanel.add(new FrmKhachHang(), "khachhang");

		
		add(contentPanel, BorderLayout.CENTER);
	}

	public void showPanel(String name) {
		cardLayout.show(contentPanel, name);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
	}
}
package com.pos.tienloi.ui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pos.tienloi.ui.MainFrame;

public class SideBar extends JPanel {

	private final Color NORMAL_COLOR = Color.decode("#EAF4FF");
	private final Color TEXT_Color = Color.decode("#1F3A5F");
	private final Color HOVER_COLOR = Color.decode("#4A90E2");

	private MainFrame mainFrame;

	public SideBar(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(318, 800));
		setBackground(NORMAL_COLOR);

		ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/logo.png"));
		Image imgLogo = logoIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		JLabel logoLabel = new JLabel(new ImageIcon(imgLogo));
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(Box.createVerticalStrut(1));

		JButton logo = createButton(new ImageIcon(imgLogo), "", "lapdon");
		logo.setMaximumSize(new Dimension(250, 250));
		add(logo);

		add(createButton(null, "Dashboard", "dashboard"));
		add(Box.createVerticalStrut(10));
		add(createButton(null, "Khách Hàng", "khachhang"));
		add(Box.createVerticalStrut(10));
		add(createButton(null, "Sản phẩm", "sanpham"));
		add(Box.createVerticalStrut(10));
		add(createButton(null, "Hóa Đơn", "hoadon"));
		add(Box.createVerticalStrut(10));
		add(createButton(null, "Phiếu Đặt Hàng", "phieudat"));
		add(Box.createVerticalStrut(10));
		add(createButton(null, "List Nhân Viên", "qlnhanvien"));
		add(Box.createVerticalStrut(80));
		add(createButton(null, "Log Out", "logout"));

	}

	private JButton createButton(ImageIcon img, String text, String panelName) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 30));
		btn.setForeground(TEXT_Color);
		btn.setBackground(NORMAL_COLOR);

		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.setMaximumSize(new Dimension(264, 52));

		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setOpaque(true);
		btn.setMargin(new java.awt.Insets(0, 0, 0, 0));

		if (panelName.equalsIgnoreCase("logout")) {
			btn.addActionListener(e -> handleLogout());
		}

		if (img != null) {
			btn.setIcon(img);
			btn.setHorizontalTextPosition(JButton.CENTER);
			btn.setVerticalTextPosition(JButton.BOTTOM);
		}

		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btn.setBackground(HOVER_COLOR);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btn.setBackground(NORMAL_COLOR);
			}
		});

		if (!panelName.isBlank()) {
			btn.addActionListener(e -> mainFrame.showPanel(panelName));
		}

		return btn;
	}

	private void handleLogout() {
		int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất không?",
				"Xác nhận đăng xuất", javax.swing.JOptionPane.YES_NO_OPTION);

		if (confirm == javax.swing.JOptionPane.YES_OPTION) {
			// Đóng MainFrame
			mainFrame.dispose();

			new com.pos.tienloi.ui.FrmDangNhap().setVisible(true);
		}
	}

}
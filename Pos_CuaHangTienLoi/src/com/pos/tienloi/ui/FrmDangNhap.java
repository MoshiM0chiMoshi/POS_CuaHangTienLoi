package com.pos.tienloi.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.pos.tienloi.dao.TaiKhoan_Dao;
import com.pos.tienloi.ui.components.ImagePanel;

public class FrmDangNhap extends JFrame {

	private JSplitPane splitPane;
	private JPanel leftPanel, rightPanel, formPanel;
	private JTextField userTxt;
	private JPasswordField passwordTxt;
	private JButton loginBtn;
	private JLabel title;
	private JPanel rowA, rowB, rowC, rowHeader;
	// Khai báo DAO để kiểm tra thông tin
	private TaiKhoan_Dao taiKhoanDao = new TaiKhoan_Dao();

	public FrmDangNhap() {
		setTitle("Đăng Nhập - Pos");
		setSize(1440, 1024);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		initUI();
	}

	public void initUI() {
		leftPanel = new ImagePanel("/images/pos_Banner.png");
		leftPanel.setOpaque(false);
		leftPanel.setPreferredSize(new Dimension(720, 1024));

		// Right Panel
		rightPanel = new JPanel();
		rightPanel.setOpaque(true);
		rightPanel.add(Box.createVerticalStrut(900));
		rightPanel.setBackground(new Color(250, 245, 232));

		title = new JLabel("Welcome back!");
		title.setFont(new Font("Segoe UI", Font.BOLD, 42));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setForeground(Color.BLACK);

		// form Panel;
		formPanel = new JPanel();
		formPanel.setOpaque(false);
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

		// Tài Khoản Field
		userTxt = new JTextField();
		userTxt.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		userTxt.setPreferredSize(new Dimension(320, 58));
		userTxt.setMaximumSize(new Dimension(320, 58));

		// Password
		passwordTxt = new JPasswordField();
		passwordTxt.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		passwordTxt.setPreferredSize(new Dimension(320, 58));
		passwordTxt.setMaximumSize(new Dimension(320, 58));

		// Button
		loginBtn = new JButton("Login");
		loginBtn.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		loginBtn.setPreferredSize(new Dimension(380, 60));
		loginBtn.setMaximumSize(new Dimension(380, 60));
		loginBtn.setBackground(new Color(255, 178, 61));
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setFocusPainted(false);
		loginBtn.setBorder(new EmptyBorder(10, 20, 10, 20));

		// Tạo icon
		ImageIcon userIcon = new ImageIcon(getClass().getResource("/images/User.png"));
		Image imgUser = userIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

		ImageIcon lockIcon = new ImageIcon(getClass().getResource("/images/lock.png"));
		Image imgLock = lockIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

		// Khởi Tạo Jpannel
		rowHeader = new JPanel();
		rowA = new JPanel();
		rowB = new JPanel();
		rowC = new JPanel();

		// Set nền của pannel bằng false
		rowA.setOpaque(false);
		rowB.setOpaque(false);
		rowC.setOpaque(false);
		rowHeader.setOpaque(false);

		// add components vào các pannel
		rowHeader.add(title);
		rowA = buildInputRow(new ImageIcon(imgUser), userTxt);
		rowB = buildInputRow(new ImageIcon(imgLock), passwordTxt);
		rowC.add(loginBtn);

		formPanel.add(rowHeader);
		formPanel.add(Box.createVerticalStrut(20));
		formPanel.add(rowA);
		formPanel.add(Box.createVerticalStrut(20));
		formPanel.add(rowB);
		formPanel.add(Box.createVerticalStrut(20));
		formPanel.add(rowC);

		rightPanel.add(formPanel);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setOpaque(false);
		splitPane.setResizeWeight(0.3);
		splitPane.setDividerSize(0);
		add(splitPane, BorderLayout.CENTER);

		// Trong initUI(), thêm ActionListener cho loginBtn
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = userTxt.getText().trim();
				String password = new String(passwordTxt.getPassword());

				// Kiểm tra logic đăng nhập qua DAO[cite: 3]
				// Giả sử bạn đã bổ sung hàm kiemTraDangNhap như hướng dẫn trước
				if (taiKhoanDao.kiemTraDangNhap(username, password) != null) {
					JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");

					// 1. Khởi tạo màn hình chính
					SwingUtilities.invokeLater(() -> {
						MainFrame mainApp = new MainFrame();
						mainApp.setVisible(true);
					});

					// 2. Đóng màn hình đăng nhập hiện tại
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu sai!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private JPanel buildInputRow(Icon icon, JComponent field) {
		JPanel row = new JPanel();
		row.setOpaque(false);
		row.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
		row.setMaximumSize(new Dimension(380, 58));

		JLabel iconLabel = new JLabel(icon);
		iconLabel.setPreferredSize(new Dimension(30, 30));

		row.add(iconLabel);
		row.add(field);
		return row;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new FrmDangNhap().setVisible(true));
	}

}
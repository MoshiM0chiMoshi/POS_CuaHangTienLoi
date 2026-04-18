package com.pos.tienloi.ui;

import javax.swing.*;
import javax.swing.border.*;

import com.pos.tienloi.ui.components.ImagePanel;

import java.awt.*;
import java.net.URL;

public class FrmDangNhap extends JFrame {

	private JSplitPane splitPane;
	private JPanel leftPanel, rightPanel, formPanel;
	private JTextField userTxt;
	private JPasswordField passwordTxt;
	private JButton loginBtn;
	private JLabel title;
	private JPanel rowA, rowB, rowC, rowHeader, rowD;

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
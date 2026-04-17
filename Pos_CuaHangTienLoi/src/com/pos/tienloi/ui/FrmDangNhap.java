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
    	leftPanel.setBackground(new Color(250, 245, 232));
    	leftPanel.setPreferredSize(new Dimension(720, 1024));
    	
    	//Right Panel
    	rightPanel = new JPanel();
    	rightPanel.setOpaque(true);
    	rightPanel.add(title = new JLabel("Welcome back!"));
    	rightPanel.setBackground(new Color(250, 245, 232));
    	title.setFont(new Font("Segoe UI", Font.BOLD,42));
    	title.setAlignmentX(Component.CENTER_ALIGNMENT);
    	
    	//form Panel;
    	formPanel = new JPanel();
    	formPanel.setOpaque(false);
    	
    	
    	splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
    	splitPane.setOpaque(false);
		splitPane.setResizeWeight(0.3);
		splitPane.setDividerSize(0);
		add(splitPane,BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
          SwingUtilities.invokeLater(() -> new FrmDangNhap().setVisible(true));
	}

}
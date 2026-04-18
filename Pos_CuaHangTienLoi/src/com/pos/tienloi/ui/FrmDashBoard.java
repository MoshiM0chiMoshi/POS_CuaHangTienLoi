package com.pos.tienloi.ui;

import javax.swing.*;

import com.pos.tienloi.ui.components.SideBar;

import java.awt.*;

public class FrmDashBoard extends JFrame {
	private JPanel northPanel, northLeft, northRight;
	private JPanel mainPanel, centerPanel, centerNorthPanel, centerMainPanel;
	private JPanel centerMainLeftPanel, centerMainRightPanel;
	private JPanel leftJPanel;
	private JLabel titleNorth;
	private JTextField search;
	private JLabel banChayTitle;

	public FrmDashBoard() {
		setTitle("Pos - Cửa Hàng Tiện Lợi");
		setLayout(new BorderLayout());
		add(new SideBar(), BorderLayout.WEST);

		setSize(1300, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		initUI();

	}

	public void initUI() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.white);

		// North Panel
		northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));


		titleNorth = new JLabel("Dashboard");
		titleNorth.setFont(new Font("Segoe UI", Font.BOLD, 50));
		titleNorth.setAlignmentX(Component.LEFT_ALIGNMENT);

		northPanel.add(Box.createHorizontalStrut(25));
		northPanel.add(Box.createVerticalStrut(60));
		northPanel.add(titleNorth);

		// Center Panel
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		// Bên trong Center Panel - North Panel
		centerNorthPanel = new JPanel();
		centerNorthPanel.setLayout(new BoxLayout(centerNorthPanel, BoxLayout.X_AXIS));
		centerNorthPanel.setBorder(BorderFactory.createEmptyBorder(-20, 20, 10, 20));

		centerNorthPanel.add(createCard("Total Customers", "50"));
		centerNorthPanel.add(Box.createHorizontalStrut(15));

		centerNorthPanel.add(createCard("Total Products", "30"));
		centerNorthPanel.add(Box.createHorizontalStrut(15));
		centerNorthPanel.add(createCard("Total Orders", "150"));
		
		// Bên trong Center Panel nhưng chiếm ở dưới - Center Main Panel
		centerMainPanel = new JPanel();
		centerMainPanel.setLayout(new BorderLayout(20,20));
		centerMainPanel.setBorder(
			    BorderFactory.createEmptyBorder(0, 20, 20, 20)
			);
		
		
		// Bên trong centerMainPanel -  Panel bên trái
		centerMainLeftPanel = new JPanel();
		centerMainLeftPanel.setLayout(new BoxLayout(centerMainLeftPanel, BoxLayout.Y_AXIS));
		centerMainLeftPanel.setBorder(BorderFactory.createCompoundBorder(
		        BorderFactory.createLineBorder(new Color(220, 220, 220)),
		        BorderFactory.createEmptyBorder(15, 20, 15, 15)
		));
		centerMainLeftPanel.setBackground(Color.WHITE);
		
		banChayTitle = new JLabel("Sản phẩm bán chạy");
		banChayTitle.setFont(new Font("Segoe UI", Font.BOLD, 25));
		
		JLabel mainItem = new JLabel("Cake");
		mainItem.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		String[] items = {"Cake", "Bread", "Cookie", "Pastry"};
		
		centerMainLeftPanel.add(banChayTitle);
		centerMainLeftPanel.add(Box.createVerticalStrut(15));
		for(String x : items) {
			JLabel lbl = new JLabel(x);
			lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
			centerMainLeftPanel.add(lbl);
			centerMainLeftPanel.add(Box.createVerticalStrut(10));
		}
		centerMainLeftPanel.add(Box.createVerticalStrut(15));
		
		
		
		
		
		// add Jpanel
		centerMainPanel.add(centerMainLeftPanel,BorderLayout.WEST);
		centerPanel.add(centerNorthPanel, BorderLayout.NORTH);
		centerPanel.add(centerMainPanel,BorderLayout.CENTER);
		mainPanel.add(centerPanel);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		

		add(mainPanel, BorderLayout.CENTER);
	}

	private JPanel createCard(String title, String value) {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		card.setPreferredSize(new Dimension(200, 200));
		card.setBackground(new Color(245, 245, 245));
		card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)),
				BorderFactory.createEmptyBorder(10, 15, 10, 15)));

		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		JLabel lblValue = new JLabel(value);
		lblValue.setFont(new Font("Segoe UI", Font.BOLD, 22));

		card.add(lblTitle);
		card.add(Box.createVerticalStrut(5));
		card.add(lblValue);

		return card;
	}
	
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new FrmDashBoard().setVisible(true));
	}
}

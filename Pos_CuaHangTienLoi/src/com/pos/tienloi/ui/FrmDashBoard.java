package com.pos.tienloi.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.pos.tienloi.dao.HoaDon_Dao;
import com.pos.tienloi.dao.PhieuDat_Dao;
import com.pos.tienloi.model.HoaDon;
import com.pos.tienloi.model.PhieuDatHang;

public class FrmDashBoard extends JPanel {
	private JPanel northPanel, northLeft, northRight;
	private JPanel mainPanel, centerPanel, centerNorthPanel, centerMainPanel;
	private JPanel centerMainLeftPanel, centerMainRightPanel;
	private JPanel leftJPanel;
	private JLabel titleNorth;
	private JTextField search;
	private JLabel banChayTitle;
	private HoaDon_Dao hdDao = new HoaDon_Dao();
	private PhieuDat_Dao pdDao = new PhieuDat_Dao();
	private DefaultTableModel modelHoaDon, modelPhieuDat;
	private JTable tableHoaDon, tablePhieuDat;

	private final Color NORMAL_COLOR = Color.decode("#EAF4FF");
	private final Color TEXT_Color = Color.decode("#1F3A5F");
	private final Color HOVER_COLOR = Color.decode("#4A90E2");

	public FrmDashBoard() {
		setLayout(new BorderLayout());
		setSize(1300, 800);

		initUI();

	}

	public void initUI() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		// North Panel
		northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

		// Panel ngang chứa title và logo
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setBackground(Color.WHITE);

		titleNorth = new JLabel("Dashboard");
		titleNorth.setFont(new Font("Segoe UI", Font.BOLD, 50));
		titleNorth.setForeground(TEXT_Color);

		ImageIcon userIcon = new ImageIcon(getClass().getResource("/images/User.png"));
		Image imgUser = userIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		JLabel userLogo = new JLabel(new ImageIcon(imgUser));

		// trái - phải
		headerPanel.add(titleNorth, BorderLayout.WEST);
		headerPanel.add(userLogo, BorderLayout.EAST);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 50));

		// thêm vào northPanel
		northPanel.add(Box.createVerticalStrut(60));
		northPanel.add(headerPanel);

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
		centerMainPanel.setLayout(new BorderLayout(20, 0));
		centerMainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

		// Bên trong centerMainPanel - Panel bên trái
		centerMainLeftPanel = new JPanel();
		centerMainLeftPanel.setLayout(new BoxLayout(centerMainLeftPanel, BoxLayout.Y_AXIS));
		centerMainLeftPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(TEXT_Color),
				BorderFactory.createEmptyBorder(15, 20, 15, 15)));
		centerMainLeftPanel.setBackground(Color.WHITE);

		// Sản phẩm bán chạy
		banChayTitle = new JLabel("Sản phẩm bán chạy");
		banChayTitle.setFont(new Font("Segoe UI", Font.BOLD, 25));

		JLabel mainItem = new JLabel("Cake");
		mainItem.setFont(new Font("Segoe UI", Font.BOLD, 18));

		String[] items = { "Cake", "Bread", "Cookie", "Pastry" };

		centerMainLeftPanel.add(banChayTitle);
		centerMainLeftPanel.add(Box.createVerticalStrut(15));
		for (String x : items) {
			JLabel lbl = new JLabel(x);
			lbl.setFont(new Font("Segoe UI", Font.BOLD, 25));
			centerMainLeftPanel.add(lbl);
			centerMainLeftPanel.add(Box.createVerticalStrut(15));
		}
		centerMainLeftPanel.add(Box.createVerticalStrut(15));

		// Bên trong centerMainPanel - Panel bên phải
		centerMainRightPanel = new JPanel();
		centerMainRightPanel.setLayout(new BoxLayout(centerMainRightPanel, BoxLayout.Y_AXIS));
		centerMainRightPanel.setOpaque(false);

		// Dữ liệu mẫu cho table
		String[] cols1 = { "Mã HD", "Ngày Lập", "Tổng Tiền" };

		JPanel recentInvoicePanel = createTableCard1("Hóa đơn gần đây", cols1);

		String[] cols2 = { "Mã PĐ", "Ngày Đặt", "Trạng Thái" };

		JPanel recentPhieuDat = createTableCard2("Phiếu đặt hàng mới", cols2);

		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, centerMainLeftPanel, centerMainRightPanel);
		pane.setResizeWeight(0.1);
		pane.setDividerSize(10);
		pane.setEnabled(false);

		// add Jpanel
		centerMainRightPanel.add(recentInvoicePanel);
		centerMainRightPanel.add(Box.createVerticalStrut(10));
		centerMainRightPanel.add(recentPhieuDat);
		centerMainPanel.add(pane);

		// màu nền cho các jPanel

		pane.setBackground(Color.white);
		mainPanel.setBackground(Color.white);
		centerPanel.setBackground(Color.white);
		northPanel.setOpaque(false);
		centerNorthPanel.setOpaque(false);

		centerMainLeftPanel.setBackground(Color.WHITE);

		centerPanel.add(centerNorthPanel, BorderLayout.NORTH);
		centerPanel.add(centerMainPanel, BorderLayout.CENTER);
		mainPanel.add(centerPanel);
		mainPanel.add(northPanel, BorderLayout.NORTH);

		add(mainPanel, BorderLayout.CENTER);
		loadDataHoaDon();
		loadDataPhieuDat();
	}

	private JPanel createCard(String title, String value) {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		card.setPreferredSize(new Dimension(0, 200));
		card.setBackground(new Color(245, 245, 245));
		card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(TEXT_Color),
				BorderFactory.createEmptyBorder(10, 15, 10, 15)));

		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));

		JLabel lblValue = new JLabel(value);
		lblValue.setFont(new Font("Segoe UI", Font.BOLD, 22));

		card.add(lblTitle);
		card.add(Box.createVerticalStrut(5));
		card.add(lblValue);

		return card;
	}

	private JPanel createTableCard1(String title, String[] columns) {
		JPanel card = new JPanel(new BorderLayout(0, 10));
		card.setBackground(Color.WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(TEXT_Color),
				BorderFactory.createEmptyBorder(15, 30, 15, 15)));

		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
		card.add(lblTitle, BorderLayout.NORTH);

		modelHoaDon = new DefaultTableModel(columns, 0);

		tableHoaDon = new JTable(modelHoaDon);
		tableHoaDon.setRowHeight(30);
		tableHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		tableHoaDon.setSelectionBackground(HOVER_COLOR);
		tableHoaDon.setShowGrid(true);
		tableHoaDon.setGridColor(new Color(220, 220, 220));
		tableHoaDon.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		tableHoaDon.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(tableHoaDon);
		scrollPane.setPreferredSize(new Dimension(0, 200)); // hoặc 200-250
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		card.add(scrollPane, BorderLayout.CENTER);

		return card;
	}

	private JPanel createTableCard2(String title, String[] columns) {
		JPanel card = new JPanel(new BorderLayout(0, 10));
		card.setBackground(Color.WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(TEXT_Color),
				BorderFactory.createEmptyBorder(15, 30, 15, 15)));

		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
		card.add(lblTitle, BorderLayout.NORTH);

		modelPhieuDat = new DefaultTableModel(columns, 0);

		tablePhieuDat = new JTable(modelPhieuDat);
		tablePhieuDat.setRowHeight(30);
		tablePhieuDat.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		tablePhieuDat.setSelectionBackground(HOVER_COLOR);
		tablePhieuDat.setShowGrid(true);
		tablePhieuDat.setGridColor(new Color(220, 220, 220));
		tablePhieuDat.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		tablePhieuDat.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(tablePhieuDat);
		scrollPane.setPreferredSize(new Dimension(0, 200)); // hoặc 200-250
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		card.add(scrollPane, BorderLayout.CENTER);

		return card;
	}

	private void loadDataHoaDon() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		modelHoaDon.setRowCount(0);

		ArrayList<HoaDon> listhd = hdDao.get3HoaDonGanNhat();
		for (HoaDon hd : listhd) {

			String[] rowData = { hd.getMaHoaDon(), sdf.format(hd.getNgayLap()), hd.getTongTien() + "", };
			modelHoaDon.addRow(rowData);
		}
		tableHoaDon.setModel(modelHoaDon);

	}

	private void loadDataPhieuDat() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		modelPhieuDat.setRowCount(0);

		ArrayList<PhieuDatHang> listpd = pdDao.get3PhieuDatGanNhat();
		for (PhieuDatHang pd : listpd) {

			String[] rowData = { pd.getMaPhieuDat(), sdf.format(pd.getNgayDat()), pd.getTrangThai().name() };
			modelPhieuDat.addRow(rowData);
		}
		tablePhieuDat.setModel(modelPhieuDat);

	}
}

package com.pos.tienloi.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.pos.tienloi.dao.HoaDon_Dao;
import com.pos.tienloi.model.HoaDon;
import com.pos.tienloi.ui.components.ButtonEditor;
import com.pos.tienloi.ui.components.ButtonRenderer;
import com.pos.tienloi.ui.components.PlaceholderTextField;

public class FrmHoaDon extends JPanel implements ActionListener {
	private JPanel northPanel, mainPanel, centerPanel;
	private JLabel titleNorth;
	private JButton themBtn;
	private JSplitPane splitPane;
	private JPanel headerPanel2Left, headerPanel2Right;
	private PlaceholderTextField searchNorth;
	private JTable table;
	private DefaultTableModel model;

	private final Color NORMAL_COLOR = Color.decode("#EAF4FF");
	private final Color TEXT_Color = Color.decode("#1F3A5F");
	private final Color HOVER_COLOR = Color.decode("#4A90E2");

	public FrmHoaDon() {

		setLayout(new BorderLayout());

		setSize(1300, 800);

		initUi();

	}

	public void initUi() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.white);

		// North Panel
		northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

		// Panel ngang chứa title và logo - Hàng 1 trong NorthPanel
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setBackground(Color.WHITE);

		// Panel ngang chứa Search box và Button thêm - Hàng 2 trong NorthPanel;
		JPanel headerPanel2 = new JPanel();
		headerPanel2.setLayout(new BorderLayout());
		headerPanel2Left = new JPanel();
		headerPanel2Right = new JPanel();

		// Tạo ra Filter
		ImageIcon filterIcon = new ImageIcon(getClass().getResource("/images/filterArrow.png"));

		// Button filter Date
		JButton btnDate = new JButton("Date");
		btnDate.setIcon(filterIcon);
		btnDate.setHorizontalTextPosition(SwingConstants.LEFT);
		btnDate.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnDate.setForeground(TEXT_Color);
		btnDate.setBackground(NORMAL_COLOR);
		btnDate.setFocusPainted(false);
		btnDate.setOpaque(true);
		btnDate.setPreferredSize(new Dimension(105, 55));
		btnDate.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Button filter Status
		JButton btnFilter = new JButton("Status");
		btnFilter.setIcon(filterIcon);
		btnFilter.setHorizontalTextPosition(SwingConstants.LEFT);
		btnFilter.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnFilter.setForeground(TEXT_Color);
		btnFilter.setBackground(NORMAL_COLOR);
		btnFilter.setFocusPainted(false);
		btnFilter.setOpaque(true);
		btnFilter.setPreferredSize(new Dimension(105, 55));
		btnFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Place holder cho filter của popup Menu
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(new JMenuItem("Active"));
		popupMenu.add(new JMenuItem("Inactive"));

		btnFilter.addActionListener(e -> {

			popupMenu.show(btnFilter, 0, btnFilter.getHeight());
		});

		// Khai báo - customer - thêm cách components
		searchNorth = new PlaceholderTextField("Tìm kiếm Phiếu Đặt...");
		searchNorth.setColumns(35);
		searchNorth.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		headerPanel2Left.add(searchNorth, BorderLayout.WEST);
		searchNorth.setPreferredSize(new Dimension(250, 60));
		headerPanel2Right = new JPanel(new BorderLayout());

		headerPanel2Left.add(btnDate);
		headerPanel2Left.add(btnFilter);

		// Sử dụng jsplit panel để chia hàng 2 ra trái phải
		splitPane = new JSplitPane(splitPane.HORIZONTAL_SPLIT, headerPanel2Left, headerPanel2Right);
		splitPane.setResizeWeight(0.1);
		splitPane.setDividerSize(0);
		headerPanel2.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 20));

		titleNorth = new JLabel("Hóa Đơn");
		titleNorth.setFont(new Font("Segoe UI", Font.BOLD, 50));
		titleNorth.setForeground(TEXT_Color);

		// Tạo Image Icon User
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
		headerPanel2.add(splitPane);
		northPanel.add(headerPanel2);

		// Center Panel
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		JPanel centerMainPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		centerMainPanel.setBackground(Color.white);

		String[] cols2 = { "Mã HD", "Khách Hàng", "Ngày Lập", "Trạng Thái", "Tổng Tiền", "Hình Thức", "Nhân Viên",
				"Detail	" };
		Object[][] data2 = {};
		centerMainPanel = createTableCard(cols2, data2);
		centerMainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 15));

		// add Panel
		centerPanel.add(centerMainPanel);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		add(mainPanel);

		northPanel.setOpaque(false);
		headerPanel.setOpaque(false);
		headerPanel2Left.setOpaque(false);
		splitPane.setBorder(null);
		splitPane.setOpaque(false);
		headerPanel2.setOpaque(false);
		headerPanel2Right.setOpaque(false);
		loadData();
		searchNorth.addActionListener(e -> timPhieuDat());
	}

	private JPanel createTableCard(String[] columns, Object[][] data) {
		JPanel card = new JPanel(new BorderLayout(0, 10));
		card.setBackground(Color.WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(TEXT_Color),
				BorderFactory.createEmptyBorder(15, 30, 15, 15)));

		model = new DefaultTableModel(data, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 7 || column == 8;
			}
		};

		table = new JTable(model);
		table.setRowHeight(30);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		table.setSelectionBackground(HOVER_COLOR);
		table.setShowGrid(true);
		table.setGridColor(TEXT_Color);

		table.setShowHorizontalLines(true);
		table.getTableHeader().setBackground(NORMAL_COLOR);
		table.getTableHeader().setForeground(TEXT_Color);
		table.setShowVerticalLines(false);

		// Tạo ra Filter
		ImageIcon detailIcon = new ImageIcon(getClass().getResource("/images/detail.png"));
		Image imgDetail = detailIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

		ButtonRenderer btnEdit = new ButtonRenderer(new ImageIcon(imgDetail));
		btnEdit.setBackground(Color.red);
		btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnEdit.setFocusPainted(false);
		btnEdit.setBorder(null);
		btnEdit.setOpaque(true);

		table.getColumnModel().getColumn(7).setCellRenderer(btnEdit);
		table.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), "Detail", "DETAIL", this));
		table.getColumnModel().getColumn(7).setPreferredWidth(65);
		table.getColumnModel().getColumn(7).setMaxWidth(65);

		table.getColumnModel().getColumn(0).setMaxWidth(70);
		table.getColumnModel().getColumn(0).setMinWidth(70);

		table.getColumnModel().getColumn(2).setMaxWidth(120);
		table.getColumnModel().getColumn(2).setMinWidth(120);

		table.getColumnModel().getColumn(4).setMaxWidth(100);
		table.getColumnModel().getColumn(4).setMinWidth(100);

		table.getColumnModel().getColumn(3).setMaxWidth(80);
		table.getColumnModel().getColumn(3).setMinWidth(80);

		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		table.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBackground(Color.WHITE);

		scrollPane.setPreferredSize(new Dimension(0, 200)); // hoặc 200-250
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		card.add(scrollPane, BorderLayout.CENTER);

		return card;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	public void loadData() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		model.setRowCount(0);
		HoaDon_Dao hdDao = new HoaDon_Dao();
		ArrayList<HoaDon> listhd = hdDao.getallHoaDon();
		for (HoaDon hd : listhd) {
			String tenKH = (hd.getKhachHang() != null) ? hd.getKhachHang().getTenKhachHang() : "Khách lẻ";
			String tenNV = (hd.getNhanVien() != null) ? hd.getNhanVien().getTenNV() : "Không rõ";

			String[] rowData = { hd.getMaHoaDon(), tenKH, sdf.format(hd.getNgayLap()), hd.getTrangThai() + "",
					hd.getTongTien() + "", hd.getPhuongThuc() + "", tenNV };
			model.addRow(rowData);
		}
		table.setModel(model);

	}

	public void timPhieuDat() {
		String ma = searchNorth.getText().trim();
		if (ma.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hóa đơn");
			searchNorth.requestFocus();
			return;
		}
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			String maTrongBang = model.getValueAt(i, 0).toString(); // cột 0 là mã hóa đơn

			if (ma.equalsIgnoreCase(maTrongBang)) {

				table.setRowSelectionInterval(i, i);

				table.scrollRectToVisible(table.getCellRect(i, 0, true));

				table.requestFocus();

				return;
			}
		}
		JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn");
		searchNorth.requestFocus();
	}

}

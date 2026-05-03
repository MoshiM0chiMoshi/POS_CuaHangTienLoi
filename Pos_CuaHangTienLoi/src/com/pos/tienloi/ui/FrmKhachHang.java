package com.pos.tienloi.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.pos.tienloi.dao.KhachHang_Dao;
import com.pos.tienloi.model.KhachHang;
import com.pos.tienloi.ui.components.ButtonEditor;
import com.pos.tienloi.ui.components.ButtonRenderer;
import com.pos.tienloi.ui.components.PlaceholderTextField;
import com.pos.tienloi.ui.dialogs.FrmThemKhachHang;

public class FrmKhachHang extends JPanel implements ActionListener {
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
	private final Color DELETE_COLOR = Color.decode("#F4B400");
	private TableRowSorter sorter;

	public FrmKhachHang() {
		setLayout(new BorderLayout());
		setSize(1300, 800);
		initUI();

	}

	public void initUI() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.white);

		// North Panel

		northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

		// Panel ngang chứa title và logo
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setBackground(Color.WHITE);

		JPanel headerPanel2 = new JPanel();
		headerPanel2.setLayout(new BorderLayout());
		headerPanel2Left = new JPanel();
		headerPanel2Right = new JPanel();

		searchNorth = new PlaceholderTextField("Tìm kiếm khách hàng...");
		searchNorth.setColumns(35);
		searchNorth.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		headerPanel2Left.add(searchNorth);
		searchNorth.setPreferredSize(new Dimension(200, 60));
		headerPanel2Right = new JPanel(new BorderLayout());

		headerPanel2Right.add(themBtn = new JButton("Thêm Khách Hàng"), BorderLayout.EAST);
		themBtn.setPreferredSize(new Dimension(200, 55));
		themBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
		themBtn.setForeground(TEXT_Color);
		themBtn.setBackground(NORMAL_COLOR);
		themBtn.setFocusPainted(false);
		themBtn.setOpaque(true);

		splitPane = new JSplitPane(splitPane.HORIZONTAL_SPLIT, headerPanel2Left, headerPanel2Right);

		splitPane.setResizeWeight(0.1);
		splitPane.setDividerSize(0);
		headerPanel2.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 20));

		titleNorth = new JLabel("Khách Hàng");
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
		headerPanel2.add(splitPane);
		northPanel.add(headerPanel2);

		// Center Panel
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		JPanel centerMainPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		centerMainPanel.setBackground(Color.white);

		String[] cols2 = { "SDT", "Tên", "Số Hóa Đơn", "Điểm tích Lũy", "Sửa" };
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

		themBtn.addActionListener(this);
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
				return column == 4;
			}
		};

		table = new JTable(model);
		table.setRowHeight(30);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		table.setSelectionBackground(HOVER_COLOR);
		table.setShowGrid(true);
		table.setGridColor(TEXT_Color);
		table.setShowGrid(false);
		table.setShowHorizontalLines(true);
		table.getTableHeader().setBackground(NORMAL_COLOR);
		table.getTableHeader().setForeground(TEXT_Color);

		ButtonRenderer btnEdit = new ButtonRenderer("Edit");
		btnEdit.setBackground(NORMAL_COLOR);
		btnEdit.setForeground(DELETE_COLOR);
		btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnEdit.setFocusPainted(false);
		btnEdit.setBorder(null);
		btnEdit.setOpaque(false);

		table.getColumnModel().getColumn(4).setCellRenderer(btnEdit);
		table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), "Edit", "EDIT", this));
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(4).setMaxWidth(100);
		table.getColumnModel().getColumn(4).setMinWidth(100);

		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		table.getTableHeader().setReorderingAllowed(false);
		sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
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
		String cmd = e.getActionCommand();
		if (cmd.startsWith("EDIT")) {
			int row = Integer.parseInt(cmd.split(":")[1]);
			xuLyEdit(row);
		} else if (e.getSource().equals(themBtn)) {
			moFormThem();
		}
	}

	private void xuLyEdit(int row) {
		int modelRow = table.convertRowIndexToModel(row);

		String sdt = table.getModel().getValueAt(modelRow, 0).toString();
		String ten = table.getModel().getValueAt(modelRow, 1).toString();
		String soHoaDon = table.getModel().getValueAt(modelRow, 2).toString();
		String diemTichLuy = table.getModel().getValueAt(modelRow, 3).toString();

		Window parentWindow = SwingUtilities.getWindowAncestor(this);
		FrmThemKhachHang dialog = new FrmThemKhachHang(parentWindow, true);
		dialog.setKhachHangData(sdt, ten, soHoaDon, diemTichLuy);
		dialog.setVisible(true);

		if (dialog.isSaved()) {
			try {

				String newTen = dialog.getTxtTen();
				int newSoHoaDon = Integer.parseInt(dialog.gettxtSoHoaDon());
				int newDiem = Integer.parseInt(dialog.gettxtDiemTichLuy());

				KhachHang kh = new KhachHang(sdt, newTen, newDiem, newSoHoaDon);
				KhachHang_Dao dao = new KhachHang_Dao();
				boolean result = dao.update(kh);

				if (result) {
					model.setValueAt(newTen, modelRow, 1);
					model.setValueAt(newSoHoaDon, modelRow, 2);
					model.setValueAt(newDiem, modelRow, 3);

					JOptionPane.showMessageDialog(this, "Cập nhật thành công");
				} else {
					JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ");

			}
		}
	}

	private void moFormThem() {
		Window parentWindow = SwingUtilities.getWindowAncestor(this);
		FrmThemKhachHang dialog = new FrmThemKhachHang(parentWindow);
		dialog.setVisible(true);

		if (dialog.isSaved()) {
			String ten = dialog.getTxtTen();
			String sdt = dialog.getTxtSDT();
			int soHoaDon;
			int diemTichLuy;
			try {
				soHoaDon = Integer.parseInt(dialog.gettxtSoHoaDon());
				diemTichLuy = Integer.parseInt(dialog.gettxtDiemTichLuy());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Số phải là số nguyên!");
				return;
			}
			// 🔥 1. Tạo object
			KhachHang kh = new KhachHang(sdt, ten, diemTichLuy, soHoaDon);

			// 🔥 2. Gọi DAO
			KhachHang_Dao dao = new KhachHang_Dao();
			boolean result = dao.create(kh);

			// 🔥 3. Xử lý kết quả
			if (result) {
				model.addRow(new Object[] { sdt, ten, soHoaDon, diemTichLuy, "Edit" });
				JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
			} else {
				JOptionPane.showMessageDialog(this, "Thêm thất bại!");
			}
		}
	}

	public void loadData() {
		KhachHang_Dao khDao = new KhachHang_Dao();
		model.setRowCount(0);
		ArrayList<KhachHang> dskh = khDao.getallKhachHang();
		for (KhachHang kh : dskh) {
			String[] rowData = { kh.getSdt(), kh.getTenKhachHang(), kh.getSoHoaDon() + "", kh.getDiemTichLuy() + "" };
			model.addRow(rowData);
		}
		table.setModel(model);
	}

	public void timPhieuDat() {
		String sdt = searchNorth.getText().trim();
		if (sdt.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại");
			searchNorth.requestFocus();
			sorter.setRowFilter(null);
			return;
		}
		try{
            String filter = "(?i)"+sdt;
            sorter.setRowFilter(RowFilter.regexFilter(filter,0));
            if (table.getRowCount()==0){
                JOptionPane.showMessageDialog(this,"Không tìm thấy");
                sorter.setRowFilter(null);
            }
        }catch (Exception e){
            sorter.setRowFilter(null);
        }
	}
}

package com.pos.tienloi.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.pos.tienloi.dao.NhanVien_Dao;
import com.pos.tienloi.dao.TaiKhoan_Dao;
import com.pos.tienloi.model.NhanVien;
import com.pos.tienloi.ui.components.ButtonEditor;
import com.pos.tienloi.ui.components.ButtonRenderer;
import com.pos.tienloi.ui.components.PlaceholderTextField;
import com.pos.tienloi.ui.dialogs.FrmThemNhanVien;

public class FrmQuanLyNhanVien extends JPanel implements ActionListener {
	private JPanel northPanel, mainPanel, centerPanel;
	private JLabel titleNorth;
	private JButton themBtn;
	private JSplitPane splitPane;
	private JPanel headerPanel2Left, headerPanel2Right;
	private PlaceholderTextField searchNorth;
	private JTable table;
	private DefaultTableModel model;
	private NhanVien_Dao nvDao;
	private TaiKhoan_Dao tkDao;

	private final Color NORMAL_COLOR = Color.decode("#EAF4FF");
	private final Color TEXT_Color = Color.decode("#1F3A5F");
	private final Color HOVER_COLOR = Color.decode("#4A90E2");
	private final Color EDIT_COLOR = Color.decode("#F4B400");
	private final Color DELETE_COLOR = Color.decode("#DC3545");

	public FrmQuanLyNhanVien() {
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

		JButton btnFilter = new JButton("Roles");
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
		popupMenu.add(new JMenuItem("Admin"));
		popupMenu.add(new JMenuItem("Staff"));

		btnFilter.addActionListener(e -> {

			popupMenu.show(btnFilter, 0, btnFilter.getHeight());
		});

		// Khai báo - customer - thêm cách components
		searchNorth = new PlaceholderTextField("Tìm kiếm theo mã NV...");
		searchNorth.setColumns(35);
		searchNorth.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		headerPanel2Left.add(searchNorth, BorderLayout.WEST);
		searchNorth.setPreferredSize(new Dimension(250, 60));
		headerPanel2Right = new JPanel(new BorderLayout());
		headerPanel2Right.add(themBtn = new JButton("Thêm Nhân Viên"), BorderLayout.EAST);
		headerPanel2Left.add(btnFilter);

		themBtn.setPreferredSize(new Dimension(200, 55));
		themBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
		themBtn.setForeground(TEXT_Color);
		themBtn.setBackground(NORMAL_COLOR);
		themBtn.setFocusPainted(false);
		themBtn.setOpaque(true);

		// Sử dụng jsplit panel để chia hàng 2 ra trái phải
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, headerPanel2Left, headerPanel2Right);
		splitPane.setResizeWeight(0.1);
		splitPane.setDividerSize(0);
		headerPanel2.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 20));

		titleNorth = new JLabel("Danh Sách Nhân Viên");
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

		String[] cols2 = { "Mã NV", "Tên NV", "SĐT", "Vai Trò", "Sửa", "Xóa" };
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

		// Thêm action
		themBtn.addActionListener(this);
		loadTable();
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
				return column == 4 || column == 5;
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

		ButtonRenderer btnEdit = new ButtonRenderer("Edit");
		btnEdit.setBackground(NORMAL_COLOR);
		btnEdit.setForeground(EDIT_COLOR);
		btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnEdit.setFocusPainted(false);
		btnEdit.setBorder(null);
		btnEdit.setOpaque(false);

		ButtonRenderer btnDelete = new ButtonRenderer("Delete");
		btnDelete.setBackground(NORMAL_COLOR);
		btnDelete.setForeground(DELETE_COLOR);
		btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnDelete.setFocusPainted(false);
		btnDelete.setBorder(null);
		btnDelete.setOpaque(false);

		table.getColumnModel().getColumn(4).setCellRenderer(btnEdit);
		table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), "Edit", "EDIT", this));
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setMaxWidth(80);

		table.getColumnModel().getColumn(5).setCellRenderer(btnDelete);
		table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "Delete", "DELETE", this));
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setMaxWidth(80);

		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		table.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBackground(Color.WHITE);

		scrollPane.setPreferredSize(new Dimension(0, 200));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		card.add(scrollPane, BorderLayout.CENTER);

		return card;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.startsWith("EDIT")) {
			int row = Integer.parseInt(cmd.split(":")[1]);
			xuLyEdit(row);

		} else if (cmd.startsWith("DELETE")) {
			int row = Integer.parseInt(cmd.split(":")[1]);
			xuLyXoa(row);
		} else if (e.getSource().equals(themBtn)) {
			moFormThem();
		}

	}

	private void xuLyEdit(int row) {
		int modelRow = table.convertRowIndexToModel(row);

		String ma = table.getModel().getValueAt(modelRow, 0).toString();
		String ten = table.getModel().getValueAt(modelRow, 1).toString();
		String sdt = table.getModel().getValueAt(modelRow, 2).toString();
		String vaiTro = table.getModel().getValueAt(modelRow, 3).toString();

		Window parentWindow = SwingUtilities.getWindowAncestor(this);
		FrmThemNhanVien dialog = new FrmThemNhanVien(parentWindow, true);
		dialog.setNhanVienData(ma, ten, sdt, vaiTro);

		dialog.setVisible(true);

		if (dialog.isSaved()) {
			model.setValueAt(dialog.getTxtMa(), modelRow, 0);
			// TODO: gọi service update
			model.setValueAt(dialog.getTxtTen(), modelRow, 1);
			model.setValueAt(dialog.getTxtSDT(), modelRow, 2);
			model.setValueAt(dialog.getCbxVaiTro(), modelRow, 3);
			System.out.println("Update: " + ma);
		}
	}

	private void xuLyXoa(int row) {
		int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận",
				JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			int modelRow = table.convertRowIndexToModel(row);
			String ma = table.getModel().getValueAt(modelRow, 0).toString();

			// TODO: DAO.delete(ma)
			model.removeRow(modelRow);
		}
	}

	private void moFormThem() {
		Window parentWindow = SwingUtilities.getWindowAncestor(this);
		FrmThemNhanVien dialog = new FrmThemNhanVien(parentWindow);
		dialog.setVisible(true);
		if (dialog.isSaved()) {
			String ma = dialog.getTxtMa();
			String ten = dialog.getTxtTen();
			String sdt = dialog.getTxtSDT();
			String mk = dialog.getTxtMatKhau();
			String vaiTro = dialog.getCbxVaiTro();
			NhanVien nv = new NhanVien(ma, ten, sdt);
			nvDao = new NhanVien_Dao();
			tkDao = new TaiKhoan_Dao();
			if (nvDao.themNhanVien(nv)) {
				if (tkDao.themTaiKhoan(ma, mk, vaiTro)) {
					model.addRow(new Object[] { ma, ten, sdt, vaiTro, "Edit", "Delete" });
					JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
				} else {
					JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công nhưng lỗi tạo tài khoản");
				}

			} else {
				JOptionPane.showMessageDialog(this, "Thêm thất bại! Vui lòng kiểm tra lại (có thể trùng Mã NV).", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	public void loadTable() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		NhanVien_Dao dao = new NhanVien_Dao();
		ArrayList<Object[]> ds = dao.getDuLieuNhanVienFull();

		for (Object[] row : ds) {
			model.addRow(row);
		}
		table.setModel(model);
	}

	public void timPhieuDat() {
		String ma = searchNorth.getText().trim();
		if (ma.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên!");
			searchNorth.requestFocus();
			return;
		}
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			String maTrongBang = model.getValueAt(i, 0).toString(); // cột 0 là mã NV

			if (ma.equalsIgnoreCase(maTrongBang)) {

				table.setRowSelectionInterval(i, i);

				table.scrollRectToVisible(table.getCellRect(i, 0, true));

				table.requestFocus();

				return;
			}
		}
		JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
		searchNorth.requestFocus();
	}

}

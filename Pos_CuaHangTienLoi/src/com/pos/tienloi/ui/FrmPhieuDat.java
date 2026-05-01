package com.pos.tienloi.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import com.pos.tienloi.dao.PhieuDat_Dao;
import com.pos.tienloi.model.PhieuDatHang;
import com.pos.tienloi.model.TrangThaiPhieuDat;
import com.pos.tienloi.ui.components.ButtonEditor;
import com.pos.tienloi.ui.components.ButtonRenderer;
import com.pos.tienloi.ui.components.PlaceholderTextField;

public class FrmPhieuDat extends JPanel implements ActionListener, MouseListener {
	private JPanel northPanel, mainPanel, centerPanel;
	private JLabel titleNorth;

	private JSplitPane splitPane;
	private JPanel headerPanel2Left, headerPanel2Right;
	private PlaceholderTextField searchNorth;
	private JTable table;
	private DefaultTableModel model;
	private PhieuDat_Dao pdDao = new PhieuDat_Dao();

	private final Color NORMAL_COLOR = Color.decode("#EAF4FF");
	private final Color TEXT_Color = Color.decode("#1F3A5F");
	private final Color HOVER_COLOR = Color.decode("#4A90E2");
	private JButton capNhat, huyDon;

	public FrmPhieuDat() {

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
		searchNorth.setColumns(30);
		searchNorth.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		headerPanel2Left.add(searchNorth, BorderLayout.WEST);
		searchNorth.setPreferredSize(new Dimension(250, 60));
		headerPanel2Right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));

		headerPanel2Left.add(btnDate);
		headerPanel2Left.add(btnFilter);

		headerPanel2Right.add(capNhat = new JButton("Trạng Thái"));
		headerPanel2Right.add(huyDon = new JButton("Hủy Đơn"));

		capNhat.setPreferredSize(new Dimension(150, 60));
		capNhat.setFont(new Font("Segoe UI", Font.BOLD, 18));
		capNhat.setForeground(TEXT_Color);
		capNhat.setBackground(NORMAL_COLOR);
		capNhat.setFocusPainted(false);
		capNhat.setOpaque(true);

		huyDon.setPreferredSize(new Dimension(150, 60));
		huyDon.setFont(new Font("Segoe UI", Font.BOLD, 18));
		huyDon.setForeground(TEXT_Color);
		huyDon.setBackground(NORMAL_COLOR);
		huyDon.setFocusPainted(false);
		huyDon.setOpaque(true);

		// Sử dụng jsplit panel để chia hàng 2 ra trái phải
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, headerPanel2Left, headerPanel2Right);
		splitPane.setResizeWeight(0.1);
		splitPane.setDividerSize(0);
		headerPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

		titleNorth = new JLabel("Phiếu Đặt Hàng");
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

		String[] cols2 = { "Mã Phiếu", "Khách Hàng", "Ngày Đặt", "Địa Chỉ", "Tổng Tiền", "Trạng Thái", "Nhân Viên",
				"Detail" };
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
		capNhat.addActionListener(this);
		huyDon.addActionListener(this);
		searchNorth.addActionListener(e -> timPhieuDat());
		loadData();
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
		table.getColumnModel().getColumn(7).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setMaxWidth(80);

		;

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
		Object o = e.getSource();
		if (o.equals(capNhat)) {
			suaTrangThai();
		} else if (o.equals(huyDon)) {
			huyPhieuDat();
		}

	}

	public void suaTrangThai() {
		int viewRow = table.getSelectedRow();
		if (viewRow == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu đặt!");

		}
		int modelRow = table.convertRowIndexToModel(viewRow);
		String ma = model.getValueAt(modelRow, 0).toString();
		if (ma == null)
			return;
		TrangThaiPhieuDat[] options = { TrangThaiPhieuDat.DANG_XU_LY, TrangThaiPhieuDat.HOAN_TAT };
		TrangThaiPhieuDat trangThai = (TrangThaiPhieuDat) JOptionPane.showInputDialog(this, "Chọn trạng thái:",
				"Cập nhật trạng thái", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (trangThai == null)
			return;
		if (pdDao.updateTrangThai(ma, trangThai)) {
			JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
			loadData();
		} else {
			JOptionPane.showMessageDialog(this, "Thất bại!");
		}

	}

	private void huyPhieuDat() {
		int viewRow = table.getSelectedRow();
		if (viewRow == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu đặt!");

		}
		int modelRow = table.convertRowIndexToModel(viewRow);
		String maPhieuDat = model.getValueAt(modelRow, 0).toString();
		if (maPhieuDat == null)
			return;
		if (maPhieuDat == null)
			return;

		int confirm = JOptionPane.showConfirmDialog(this, "Hủy phiếu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

		if (confirm != JOptionPane.YES_OPTION)
			return;

		if (pdDao.huyPhieuDat(maPhieuDat)) {
			JOptionPane.showMessageDialog(this, "Đã chuyển sang CANCELLED");
			loadData();
		}
	}

	public void loadData() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		model.setRowCount(0);

		ArrayList<PhieuDatHang> listpd = pdDao.getallPhieuDat();
		for (PhieuDatHang pd : listpd) {
			String tenKH = (pd.getKhachHang() != null) ? pd.getKhachHang().getTenKhachHang() : "Khách lẻ";
			String tenNV = (pd.getNhanVien() != null) ? pd.getNhanVien().getTenNV() : "Không rõ";
			String[] rowData = { pd.getMaPhieuDat(), tenKH, sdf.format(pd.getNgayDat()), pd.getDiaChi(),
					pd.getTongTien() + "", pd.getTrangThai() + "", tenNV };
			model.addRow(rowData);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

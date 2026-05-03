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
import java.io.File;
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

import com.pos.tienloi.dao.SanPham_Dao;
import com.pos.tienloi.model.DanhMuc;
import com.pos.tienloi.model.SanPham;
import com.pos.tienloi.model.Thue;
import com.pos.tienloi.ui.components.ButtonEditor;
import com.pos.tienloi.ui.components.ButtonRenderer;
import com.pos.tienloi.ui.components.PlaceholderTextField;
import com.pos.tienloi.ui.dialogs.FrmThemSanPham;

public class FrmSanPham extends JPanel implements ActionListener {
	private JPanel northPanel, mainPanel, centerPanel;
	private JLabel titleNorth;
	private JButton themBtn;
	private JSplitPane splitPane;
	private JPanel headerPanel2Left, headerPanel2Right;
	private PlaceholderTextField searchNorth;
	private JTable table;
	private DefaultTableModel model;
	private SanPham_Dao spDao = new SanPham_Dao();

	private final Color NORMAL_COLOR = Color.decode("#EAF4FF");
	private final Color TEXT_Color = Color.decode("#1F3A5F");
	private final Color HOVER_COLOR = Color.decode("#4A90E2");
	private final Color EDIT_COLOR = Color.decode("#F4B400");
	private final Color DELETE_COLOR = Color.decode("#DC3545");
	private TableRowSorter sorter;

	public FrmSanPham() {

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
		ImageIcon filterIcon = new ImageIcon(getClass().getResource("/images/Filter.png"));
		Image imgFilter = filterIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

		JButton btnFilter = new JButton(new ImageIcon(imgFilter));
		btnFilter.setFocusPainted(false);
		btnFilter.setBorderPainted(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Khai báo - customer - thêm cách components
		searchNorth = new PlaceholderTextField("Tìm kiếm Sản Phẩm...");
		searchNorth.setColumns(35);
		searchNorth.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		headerPanel2Left.add(searchNorth);
		searchNorth.setPreferredSize(new Dimension(250, 60));
		headerPanel2Right = new JPanel(new BorderLayout());
		headerPanel2Right.add(themBtn = new JButton("Thêm Sản Phẩm"), BorderLayout.EAST);
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

		titleNorth = new JLabel("Sản Phẩm");
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

		// Hình ảnh của sản phẩm
		ImageIcon imgSanPham1 = new ImageIcon(new ImageIcon(getClass().getResource("/images/sanPham1.png")).getImage()
				.getScaledInstance(50, 50, Image.SCALE_SMOOTH));

		String[] cols2 = { "Hình", "Mã", "Tên", "Tồn kho", "Giá", "Thuế (%)", "Danh mục", "Sửa", "Xóa", "path" };
		Object[][] data2 = { { imgSanPham1, "SP001", "Tokai Teio", 100, 50000, 8, "Anime", null, null, "" } };
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

		// Action
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
				return column == 7 || column == 8;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 0)
					return ImageIcon.class;
				return Object.class;
			}
		};

		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		table.setSelectionBackground(HOVER_COLOR);
		table.setShowGrid(true);
		table.setGridColor(TEXT_Color);
		table.setRowHeight(60);

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

		table.getColumnModel().getColumn(7).setCellRenderer(btnEdit);
		table.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), "Edit", "EDIT", this));
		table.getColumnModel().getColumn(7).setPreferredWidth(70);
		table.getColumnModel().getColumn(7).setMaxWidth(70);

		table.getColumnModel().getColumn(8).setCellRenderer(btnDelete);
		table.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox(), "Delete", "DELETE", this));
		table.getColumnModel().getColumn(8).setPreferredWidth(70);
		table.getColumnModel().getColumn(8).setMaxWidth(70);

		table.getColumnModel().getColumn(5).setMaxWidth(65);
		table.getColumnModel().getColumn(5).setMinWidth(65);
		table.getColumnModel().getColumn(5).setMaxWidth(65);

		table.getColumnModel().getColumn(3).setMaxWidth(70);
		table.getColumnModel().getColumn(3).setMinWidth(70);
		table.getColumnModel().getColumn(3).setMaxWidth(70);

		table.getColumnModel().getColumn(1).setMaxWidth(70);
		table.getColumnModel().getColumn(1).setMinWidth(70);
		table.getColumnModel().getColumn(1).setMaxWidth(70);

		table.getColumnModel().getColumn(0).setMaxWidth(70);
		table.getColumnModel().getColumn(0).setMinWidth(70);
		table.getColumnModel().getColumn(0).setMaxWidth(70);

		table.getColumnModel().getColumn(4).setMaxWidth(80);
		table.getColumnModel().getColumn(4).setMinWidth(80);
		table.getColumnModel().getColumn(4).setMaxWidth(80);

		// Ẩn cột path đi
		table.getColumnModel().getColumn(9).setMinWidth(0);
		table.getColumnModel().getColumn(9).setMaxWidth(0);
		table.getColumnModel().getColumn(9).setWidth(0);

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
		Object o = e.getSource();
		String cmd = e.getActionCommand();
		if (cmd.startsWith("EDIT")) {
			int row = Integer.parseInt(cmd.split(":")[1]);
			xuLyEdit(row);

		} else if (cmd.startsWith("DELETE")) {
			int row = Integer.parseInt(cmd.split(":")[1]);
			xuLyXoa(row);
		} else if (o.equals(themBtn)) {
			moFormThem();
		}
	}

	private void xuLyXoa(int row) {
		int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận.",
				JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			int modelRow = table.convertRowIndexToModel(row);

			if (modelRow > 0) {
				String ma = table.getModel().getValueAt(modelRow, 1).toString();
				if (spDao.softDelete(ma)) {
					loadData();

				}
			}

		}
	}

	private void xuLyEdit(int row) {
		int modelRow = table.convertRowIndexToModel(row);

		// 1. Lấy dữ liệu từ Table (lúc này các cột 5, 6 đang là String/Float)
		String path = (String) table.getModel().getValueAt(modelRow, 9);
		String ma = table.getModel().getValueAt(modelRow, 1).toString();
		String ten = table.getModel().getValueAt(modelRow, 2).toString();
		String tonkho = table.getModel().getValueAt(modelRow, 3).toString();
		String gia = table.getModel().getValueAt(modelRow, 4).toString();

		// Lưu ý: Cột 5 và 6 trên bảng đang hiển thị Text, ta sẽ xử lý chọn lại trong
		// Dialog sau
		String tenThue = table.getModel().getValueAt(modelRow, 5).toString();
		String tenDM = table.getModel().getValueAt(modelRow, 6).toString();

		Window parentWindow = SwingUtilities.getWindowAncestor(this);
		FrmThemSanPham dialog = new FrmThemSanPham(parentWindow, true);

		// Truyền dữ liệu vào Dialog
		dialog.setSanPhamData(path, ma, ten, tonkho, gia, tenThue, tenDM);
		dialog.setVisible(true);

		if (dialog.isSaved()) {
			try {
				// 2. Lấy dữ liệu mới từ Dialog
				String pathMoi = dialog.getDuongDanAnh();
				String tenMoi = dialog.getTenSP();
				int tonMoi = Integer.parseInt(dialog.getTonKho());
				float giaMoi = Float.parseFloat(dialog.getGia());
				Thue thueMoi = dialog.getThue();
				DanhMuc dmMoi = dialog.getDanhMuc();

				// 3. Tạo đối tượng và CẬP NHẬT DATABASE
				SanPham spMoi = new SanPham(pathMoi, ma, tenMoi, tonMoi, giaMoi, thueMoi, dmMoi);
				SanPham_Dao dao = new SanPham_Dao();

				// Bạn cần thêm hàm update trong SanPham_Dao (xem hướng dẫn bên dưới)
				boolean isUpdated = dao.update(spMoi);

				if (isUpdated) {
					// 4. Cập nhật lại giao diện Table
					if (pathMoi != null && !pathMoi.isBlank()) {
						ImageIcon icon = new ImageIcon(pathMoi);
						Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
						model.setValueAt(new ImageIcon(img), modelRow, 0);
						model.setValueAt(pathMoi, modelRow, 9);
					}

					model.setValueAt(tenMoi, modelRow, 2);
					model.setValueAt(tonMoi, modelRow, 3);
					model.setValueAt(giaMoi, modelRow, 4);
					model.setValueAt(Math.round(thueMoi.getMucThue() * 100), modelRow, 5);
					model.setValueAt(dmMoi.getTenDanhMuc(), modelRow, 6);

					JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
				} else {
					JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Lỗi dữ liệu khi cập nhật!");
			}
		}
	}

	private void moFormThem() {
		Window parentWindow = SwingUtilities.getWindowAncestor(this);
		FrmThemSanPham dialog = new FrmThemSanPham(parentWindow);
		dialog.setVisible(true);

		if (dialog.isSaved()) {
			try {
				// ===== LẤY DỮ LIỆU =====
				String ma = dialog.getMaSP();
				String ten = dialog.getTenSP();
				int tonKho = Integer.parseInt(dialog.getTonKho());
				float gia = Float.parseFloat(dialog.getGia());
				String anh = dialog.getDuongDanAnh();

				DanhMuc dm = dialog.getDanhMuc(); // ✅ đã là object
				Thue thue = dialog.getThue(); // ✅ đã là object

				// ===== TẠO OBJECT =====
				SanPham sp = new SanPham(anh, ma, ten, tonKho, gia, thue, dm);

				// ===== GỌI DAO =====
				SanPham_Dao dao = new SanPham_Dao();
				boolean kq = dao.create(sp);

				if (kq) {
					// ===== HIỂN THỊ TABLE =====
					ImageIcon icon = new ImageIcon(anh);
					Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

					model.addRow(new Object[] { new ImageIcon(img), ma, ten, tonKho, gia, thue.getMucThue(), // hiển thị
																												// %
							dm.getTenDanhMuc(), // hiển thị tên
							null, null, anh });

					JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
				} else {
					JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!");
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Lỗi dữ liệu!");
				e.printStackTrace();
			}
		}
	}

	private ImageIcon loadIcon(String fileName) {
		if (fileName == null || fileName.isBlank())
			return null;

		ImageIcon temp = null;

		// 1. Thử tải ảnh từ Resource của project (dành cho các file có sẵn như
		// /images/sanPham1.png)
		// Đảm bảo không bị dư dấu '/' ở đầu
		String resourcePath = fileName.startsWith("/") ? fileName : "/" + fileName;
		java.net.URL imgURL = getClass().getResource(resourcePath);

		if (imgURL != null) {
			temp = new ImageIcon(imgURL);
		} else {
			// 2. Nếu không có trong Resource, tải ảnh trực tiếp từ đường dẫn tuyệt đối trên
			// ổ cứng
			File imgFile = new File(fileName);
			if (imgFile.exists()) {
				temp = new ImageIcon(fileName);
			}
		}

		// Nếu vẫn không tìm thấy ảnh (file bị xóa hoặc đổi tên) thì trả về null
		if (temp == null) {
			return null;
		}

		Image img = temp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

	private void loadData() {
		SanPham_Dao spDao = new SanPham_Dao();
		ArrayList<SanPham> ds = spDao.getAllSanPham();

		model.setRowCount(0);

		for (SanPham sp : ds) {
			ImageIcon icon = loadIcon(sp.getHinhAnh());

			Object mucThue = sp.getThue() != null ? Math.round(sp.getThue().getMucThue() * 100) : "";
			Object tenDanhMuc = sp.getDanhMuc() != null ? sp.getDanhMuc().getTenDanhMuc() : "";

			Object[] rowData = { icon, sp.getMaSP(), sp.getTenSP(), sp.getSoLuongTon(), sp.getGiaBan(), mucThue,
					tenDanhMuc, null, null, sp.getHinhAnh() };

			model.addRow(rowData);
		}
	}

	public void timPhieuDat() {
		String ma = searchNorth.getText().trim();
		if (ma.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm");
			searchNorth.requestFocus();
			sorter.setRowFilter(null);
			return;
		}
		try{
            String filter = "(?i)"+ma;
            sorter.setRowFilter(RowFilter.regexFilter(filter,1));
            if (table.getRowCount()==0){
                JOptionPane.showMessageDialog(this,"Không tìm thấy sản phẩm");
                sorter.setRowFilter(null);
            }
        }catch (Exception e){
            sorter.setRowFilter(null);
        }
	}

}

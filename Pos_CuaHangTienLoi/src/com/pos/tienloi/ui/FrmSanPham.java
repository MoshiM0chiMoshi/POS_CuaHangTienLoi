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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.pos.tienloi.ui.components.ButtonEditor;
import com.pos.tienloi.ui.components.ButtonRenderer;
import com.pos.tienloi.ui.components.PlaceholderTextField;
import com.pos.tienloi.ui.dialogs.FrmThemNhanVien;
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

	private final Color NORMAL_COLOR = Color.decode("#EAF4FF");
	private final Color TEXT_Color = Color.decode("#1F3A5F");
	private final Color HOVER_COLOR = Color.decode("#4A90E2");
	private final Color EDIT_COLOR = Color.decode("#F4B400");
	private final Color DELETE_COLOR = Color.decode("#DC3545");

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

		String[] cols2 = { "Hình", "Mã", "Tên", "Tồn kho", "Giá", "Thuế (%)", "Danh mục", "Sửa", "Xóa" };
		Object[][] data2 = { { imgSanPham1, "SP001", "Tokai Teio", 100, 50000, 8, "Anime", null, null } };
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
					return ImageIcon.class; // cột hình
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
		table.getColumnModel().getColumn(7).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setMaxWidth(80);

		table.getColumnModel().getColumn(8).setCellRenderer(btnDelete);
		table.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox(), "Delete", "DELETE", this));
		table.getColumnModel().getColumn(8).setPreferredWidth(80);
		table.getColumnModel().getColumn(8).setMaxWidth(80);

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
        String cmd = e.getActionCommand();
        if (cmd.startsWith("EDIT")) {
            int row = Integer.parseInt(cmd.split(":")[1]);
            xuLyEdit(row);

        } else if (cmd.startsWith("DELETE")) {
            int row = Integer.parseInt(cmd.split(":")[1]);
            xuLyXoa(row);
        }
		else if (o.equals(themBtn)) {
			moFormThem();
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

    private void xuLyEdit(int row) {
        int modelRow = table.convertRowIndexToModel(row);
        //Thiếu hình IMAGE

        String ma = table.getModel().getValueAt(modelRow, 1).toString();
        String ten = table.getModel().getValueAt(modelRow, 2).toString();
        String tonkho = table.getModel().getValueAt(modelRow,3).toString();
        String gia = table.getModel().getValueAt(modelRow,4).toString();
        String thue = table.getModel().getValueAt(modelRow,5).toString();
        String danhMuc = table.getModel().getValueAt(modelRow,6).toString();
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        FrmThemSanPham dialog = new FrmThemSanPham(parentWindow);
        dialog.setSanPhamData(ma,ten,tonkho,gia,thue,danhMuc);

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            model.setValueAt(dialog.getMaSP(),modelRow,1);
            // TODO: gọi service update
            model.setValueAt(dialog.getTenSP(), modelRow, 2);
            model.setValueAt(dialog.getTonKho(), modelRow, 3);
            model.setValueAt(dialog.getGia(), modelRow, 4);
            model.setValueAt(dialog.getThue(), modelRow, 5);
            model.setValueAt(dialog.getDanhMuc(), modelRow, 6);
        }
    }
	private void moFormThem() {
		Window parentWindow = SwingUtilities.getWindowAncestor(this);
		FrmThemSanPham dialog = new FrmThemSanPham(parentWindow);
		dialog.setVisible(true);
		if (dialog.isSaved()) {
			// refresh table
            String ten = dialog.getTenSP();
            String ma = dialog.getMaSP();
            int tonkho = Integer.parseInt(dialog.getTonKho());
            int gia = Integer.parseInt(dialog.getGia());
            Double thue = Double.parseDouble(dialog.getThue());
            String danhmuc = dialog.getDanhMuc();
            String anh = dialog.getDuongDanAnh();
            //SỬA PHẦN IMGICON

//            ImageIcon imgSanPham = new ImageIcon(new ImageIcon(getClass().getResource(anh)).getImage()
//                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            model.addRow(new Object[]{anh,ma,ten,tonkho,gia,thue,danhmuc});
		}
	}
}

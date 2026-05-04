package com.pos.tienloi.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.pos.tienloi.dao.ChiTietPhieuDat_Dao;
import com.pos.tienloi.model.ChiTietPhieuDat;

public class FrmChiTietPhieuDat extends JDialog {
	private JTable detailTable;
	private DefaultTableModel detailModel;
	private JLabel lblTongTien;
	private String maPhieuDat;
	private ChiTietPhieuDat_Dao ctDao = new ChiTietPhieuDat_Dao();
	private final Color TEXT_Color = Color.decode("#1F3A5F");

	// Constructor nhận vào frame cha và mã phiếu cần xem
	public FrmChiTietPhieuDat(Window owner, String maPhieuDat) {
		super(owner, "Chi Tiết Phiếu Đặt: " + maPhieuDat, ModalityType.APPLICATION_MODAL);
		this.maPhieuDat = maPhieuDat;

		initUI();
		loadData();
	}

	private void initUI() {
		setSize(700, 450);
		setLocationRelativeTo(getOwner());
		setLayout(new BorderLayout());

		// Panel tiêu đề
		JPanel pnlTitle = new JPanel();
		JLabel lblTitle = new JLabel("DANH SÁCH SẢN PHẨM");
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTitle.setForeground(TEXT_Color);
		pnlTitle.add(lblTitle);
		add(pnlTitle, BorderLayout.NORTH);

		// Bảng chi tiết
		String[] columns = { "Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền" };
		detailModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Không cho chỉnh sửa trực tiếp trên bảng
			}
		};
		detailTable = new JTable(detailModel);
		detailTable.setRowHeight(25);
		detailTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

		add(new JScrollPane(detailTable), BorderLayout.CENTER);

		// Panel Footer
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
		lblTongTien = new JLabel("Tổng cộng: 0 VNĐ");
		lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTongTien.setForeground(Color.RED);

		JButton btnClose = new JButton("Đóng");
		btnClose.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnClose.addActionListener(e -> dispose());

		pnlSouth.add(lblTongTien);
		pnlSouth.add(btnClose);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	private void loadData() {
		ArrayList<ChiTietPhieuDat> list = ctDao.getChiTietByMaHD(maPhieuDat);
		double tongTien = 0;

		for (ChiTietPhieuDat ct : list) {
			double thanhTien = ct.getSoLuongDat() * ct.getDonGiaDat();
			tongTien += thanhTien;
			Object[] row = { ct.getSanPham().getTenSP(), ct.getSoLuongDat(),
					String.format("%,.0f VNĐ", ct.getDonGiaDat()), String.format("%,.0f VNĐ", thanhTien) };
			detailModel.addRow(row);
		}

		lblTongTien.setText("Tổng cộng: " + String.format("%,.0f VNĐ", tongTien));
	}
}
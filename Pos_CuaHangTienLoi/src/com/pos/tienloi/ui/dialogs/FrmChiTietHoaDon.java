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

import com.pos.tienloi.dao.ChiTietHoaDon_Dao;
import com.pos.tienloi.model.ChiTietHoaDon;

public class FrmChiTietHoaDon extends JDialog {
	private JTable detailTable;
	private DefaultTableModel detailModel;
	private JLabel lblTongTien;
	private String maHoaDon;
	private ChiTietHoaDon_Dao ctDao = new ChiTietHoaDon_Dao();
	private final Color TEXT_Color = Color.decode("#1F3A5F");

	public FrmChiTietHoaDon(Window owner, String maHoaDon) {
		super(owner, "Chi Tiết Hóa Đơn: " + maHoaDon, ModalityType.APPLICATION_MODAL);
		this.maHoaDon = maHoaDon;
		initUI();
		loadData();
	}

	private void initUI() {
		setSize(700, 450);
		setLocationRelativeTo(getOwner());
		setLayout(new BorderLayout());

		JPanel pnlTitle = new JPanel();
		JLabel lblTitle = new JLabel("CHI TIẾT MẶT HÀNG");
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTitle.setForeground(TEXT_Color);
		pnlTitle.add(lblTitle);
		add(pnlTitle, BorderLayout.NORTH);

		String[] columns = { "Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền" };
		detailModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		detailTable = new JTable(detailModel);
		detailTable.setRowHeight(25);
		add(new JScrollPane(detailTable), BorderLayout.CENTER);

		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
		lblTongTien = new JLabel("Tổng cộng: 0 VNĐ");
		lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTongTien.setForeground(Color.RED);

		JButton btnClose = new JButton("Đóng");
		btnClose.addActionListener(e -> dispose());

		pnlSouth.add(lblTongTien);
		pnlSouth.add(btnClose);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	private void loadData() {
		ArrayList<ChiTietHoaDon> list = ctDao.getChiTietByMaHD(maHoaDon);
		double tongTien = 0;
		for (ChiTietHoaDon ct : list) {
			double thanhTien = ct.getSoLuong() * ct.getDonGia();
			tongTien += thanhTien;
			Object[] row = { ct.getSanPham() != null ? ct.getSanPham().getTenSP() : "N/A", ct.getSoLuong(),
					String.format("%,.0f VNĐ", ct.getDonGia()), String.format("%,.0f VNĐ", thanhTien) };
			detailModel.addRow(row);
		}
		lblTongTien.setText("Tổng cộng: " + String.format("%,.0f VNĐ", tongTien));
	}
}
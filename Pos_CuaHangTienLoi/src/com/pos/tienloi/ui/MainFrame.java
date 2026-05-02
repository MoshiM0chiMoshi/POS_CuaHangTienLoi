package com.pos.tienloi.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.pos.tienloi.ui.components.SideBar;

public class MainFrame extends JFrame {
	private CardLayout cardLayout;
	private JPanel contentPanel;

	public MainFrame() {
		setTitle("Pos - Cửa Hàng Tiện Lợi");
		setSize(1300, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		// Sidebar bên trái
		add(new SideBar(this), BorderLayout.WEST);

		// Khu vực đổi màn hình
		cardLayout = new CardLayout();
		contentPanel = new JPanel(cardLayout);
		FrmLapDon frmLapDon = new FrmLapDon();
		FrmHoaDon frmHoaDon = new FrmHoaDon();
		FrmPhieuDat frmPhieuDat = new FrmPhieuDat();
		FrmKhachHang frmKhachHang = new FrmKhachHang();
		frmLapDon.setOrderSuccessListener(new FrmLapDon.OnOrderSuccessListener() {
			@Override
			public void onSuccess() {
				frmHoaDon.loadData();
				frmKhachHang.loadData();
				frmPhieuDat.loadData();

			}
		});

		contentPanel.add(new FrmLapDon(), "lapdon");
		contentPanel.add(new FrmDashBoard(), "dashboard");
		contentPanel.add(new FrmKhachHang(), "khachhang");
		contentPanel.add(new FrmSanPham(), "sanpham");
		contentPanel.add(new FrmHoaDon(), "hoadon");
		contentPanel.add(new FrmPhieuDat(), "phieudat");
		contentPanel.add(new FrmQuanLyNhanVien(), "qlnhanvien");

		add(contentPanel, BorderLayout.CENTER);

		// màn hình mặc định
		showPanel("dashboard");
	}

	public void showPanel(String name) {
		cardLayout.show(contentPanel, name);
	}

	public static void main(String[] args) {

		try {
			com.pos.tienloi.connectDB.ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> new FrmDangNhap().setVisible(true));
	}
}
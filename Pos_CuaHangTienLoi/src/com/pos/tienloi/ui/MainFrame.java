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
	
	// 1. CHỈNH SỬA: Đưa frmDashBoard ra làm biến toàn cục để các hàm khác có thể gọi được
	private FrmDashBoard frmDashBoard; 

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
		FrmQuanLyNhanVien frmQuanLyNhanVien = new FrmQuanLyNhanVien();
		
		
		frmDashBoard = new FrmDashBoard(); 
		FrmSanPham frmSanPham = new FrmSanPham();

		frmLapDon.setOrderSuccessListener(new FrmLapDon.OnOrderSuccessListener() {
			@Override
			public void onSuccess() {
				frmHoaDon.loadData();
				frmKhachHang.loadData();
				frmPhieuDat.loadData();
				

				frmDashBoard.refreshData(); 
			}
		});

		frmSanPham.setProductChangeListener(new FrmSanPham.OnProductChangeListener() {
			@Override
			public void onChange() {
				frmLapDon.loadProductsToUI("");
				
				
				frmDashBoard.refreshData(); 
			}
		});

		contentPanel.add(frmLapDon, "lapdon");
		contentPanel.add(frmDashBoard, "dashboard");
		contentPanel.add(frmKhachHang, "khachhang");
		contentPanel.add(frmSanPham, "sanpham");
		contentPanel.add(frmHoaDon, "hoadon");
		contentPanel.add(frmPhieuDat, "phieudat");
		contentPanel.add(frmQuanLyNhanVien, "qlnhanvien");

		add(contentPanel, BorderLayout.CENTER);


		showPanel("dashboard");
	}

	public void showPanel(String name) {
		cardLayout.show(contentPanel, name);
		
		
		if (name.equals("dashboard") && frmDashBoard != null) {
			frmDashBoard.refreshData();
		}
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
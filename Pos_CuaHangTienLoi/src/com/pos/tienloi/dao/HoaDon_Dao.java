package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.HoaDon;
import com.pos.tienloi.model.KhachHang;
import com.pos.tienloi.model.NhanVien;
import com.pos.tienloi.model.PTTT;
import com.pos.tienloi.model.TrangThaiHoaDon;

public class HoaDon_Dao {
	public ArrayList<HoaDon> getallHoaDon() {
		ArrayList<HoaDon> dshd = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		ChiTietHoaDon_Dao cthdDao = new ChiTietHoaDon_Dao();

		String sql = "SELECT hd.*, kh.tenKhachHang, nv.tenNV " + "FROM HoaDon hd "
				+ "LEFT JOIN KhachHang kh ON hd.sdtKhachHang = kh.sdt " + "LEFT JOIN NhanVien nv ON hd.maNV = nv.maNV";

		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

			while (rs.next()) {
				HoaDon hd = new HoaDon();

				hd.setMaHoaDon(rs.getString("maHoaDon"));
				hd.setNgayLap(rs.getDate("ngayLap"));

				String trangThaiStr = rs.getString("trangThai");
				if (trangThaiStr != null && !trangThaiStr.isEmpty()) {
					hd.setTrangThai(TrangThaiHoaDon.valueOf(trangThaiStr));
				}

				String phuongThucStr = rs.getString("phuongThuc");
				if (phuongThucStr != null && !phuongThucStr.isEmpty()) {
					hd.setPhuongThuc(PTTT.valueOf(phuongThucStr));
				}

				// --- MAP THÔNG TIN KHÁCH HÀNG ---
				KhachHang kh = new KhachHang();

				kh.setSdt(rs.getString("sdtKhachHang"));

				kh.setTenKhachHang(rs.getString("tenKhachHang"));
				hd.setKhachHang(kh);

				// --- MAP THÔNG TIN NHÂN VIÊN ---
				NhanVien nv = new NhanVien();

				nv.setMaNV(rs.getString("maNV"));

				nv.setTenNV(rs.getString("tenNV"));
				hd.setNhanVien(nv);
				hd.setListChiTietHoaDon(cthdDao.getChiTietByMaHD(hd.getMaHoaDon()));

				dshd.add(hd);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}
}
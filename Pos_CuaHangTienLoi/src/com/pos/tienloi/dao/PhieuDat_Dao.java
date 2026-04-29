package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.KhachHang;
import com.pos.tienloi.model.NhanVien;
import com.pos.tienloi.model.PhieuDatHang;
import com.pos.tienloi.model.TrangThaiPhieuDat;

public class PhieuDat_Dao {

	public ArrayList<PhieuDatHang> getallPhieuDat() {
		ArrayList<PhieuDatHang> dspd = new ArrayList<PhieuDatHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "Select pd.*, kh.tenKhachHang, nv.tenNV " + "FROM PhieuDatHang pd "
				+ "LEFT JOIN KhachHang kh ON pd.sdtKhachHang = kh.sdt " + "LEFT JOIN NhanVien nv ON pd.maNV = nv.maNV";
		ChiTietPhieuDat_Dao chiTiet = new ChiTietPhieuDat_Dao();

		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				PhieuDatHang pd = new PhieuDatHang();
				pd.setMaPhieuDat(rs.getString("maPhieuDat"));
				pd.setNgayDat(rs.getDate("ngayDat"));
				pd.setDiaChi(rs.getString("diaChi"));

				String trangThaiStr = rs.getString("trangThai");
				if (trangThaiStr != null && !trangThaiStr.isBlank()) {
					pd.setTrangThai(TrangThaiPhieuDat.valueOf(trangThaiStr));
				}

				KhachHang kh = new KhachHang();
				kh.setSdt(rs.getString(1));
				kh.setTenKhachHang(rs.getString("tenKhachHang"));
				pd.setKhachHang(kh);

				NhanVien nv = new NhanVien();
				nv.setMaNV(rs.getString("maNV"));
				nv.setTenNV(rs.getString("tenNV"));
				pd.setNhanVien(nv);
				pd.setListChiTietPhieu(chiTiet.getChiTietByMaHD(pd.getMaPhieuDat()));

				dspd.add(pd);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dspd;

	}

}

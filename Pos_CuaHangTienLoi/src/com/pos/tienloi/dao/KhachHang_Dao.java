package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.KhachHang;

public class KhachHang_Dao {

	public ArrayList<KhachHang> getallKhachHang() {
		ArrayList<KhachHang> dskh = new ArrayList<KhachHang>();
		String sql = "Select * from KhachHang";
		Connection con = ConnectDB.getInstance().getConnection();
		if (con == null) {
			throw new IllegalStateException("Chưa kết nối được db");
		}

		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

			while (rs.next()) {
				KhachHang kh = new KhachHang();
				kh.setSdt(rs.getString("sdt"));
				kh.setTenKhachHang(rs.getString("tenKhachHang"));
				kh.setDiemTichLuy(rs.getInt("diemTichLuy"));
				kh.setSoHoaDon(rs.getInt("soHoaDon"));
				dskh.add(kh);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dskh;
	}

	public boolean create(KhachHang kh) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		if (con == null) {
			throw new IllegalStateException("Chưa kết nối DB");
		}
		String sql = "INSERT INTO KhachHang (sdt, tenKhachHang, diemTichLuy, soHoaDon) VALUES (?, ?, ?, ?)";
		int n = 0;
		try (PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, kh.getSdt());
			ps.setString(2, kh.getTenKhachHang());
			ps.setInt(3, kh.getDiemTichLuy());
			ps.setInt(4, kh.getSoHoaDon());

			n = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public boolean update(KhachHang kh) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		if (con == null) {
			throw new IllegalStateException("Chưa kết nối DB");
		}
		String sql = "update KhachHang set tenKhachHang=?, diemTichLuy=?, soHoaDon=? where sdt=?";
		int n = 0;
		try (PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, kh.getTenKhachHang());
			ps.setInt(2, kh.getDiemTichLuy());
			ps.setInt(3, kh.getSoHoaDon());
			ps.setString(4, kh.getSdt());
			n = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

}

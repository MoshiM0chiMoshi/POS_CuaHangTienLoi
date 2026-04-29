package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.ChiTietHoaDon;
import com.pos.tienloi.model.SanPham;

public class ChiTietHoaDon_Dao {

	// Hàm lấy danh sách chi tiết hóa đơn theo mã hóa đơn
	public ArrayList<ChiTietHoaDon> getChiTietByMaHD(String maHoaDon) {
		ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		String sql = "SELECT * FROM ChiTietHoaDon WHERE maHoaDon = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, maHoaDon);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ChiTietHoaDon ct = new ChiTietHoaDon();
				ct.setDonGia(rs.getFloat("donGia"));
				ct.setSoLuong(rs.getInt("soLuong"));

				SanPham sp = new SanPham();

				dsCTHD.add(ct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsCTHD;
	}
}
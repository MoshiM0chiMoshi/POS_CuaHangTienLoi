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

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
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

}

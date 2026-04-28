package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.DanhMuc;

public class DanhMuc_Dao {

	public ArrayList<DanhMuc> getallDanhMuc() {
		ArrayList<DanhMuc> dsdanhmuc = new ArrayList<DanhMuc>();
		String sql = "Select * from DanhMuc";
		Connection con = ConnectDB.getInstance().getConnection();
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

			while (rs.next()) {
				DanhMuc dm = new DanhMuc();
				dm.setMaDanhMuc(rs.getString(1));
				dm.setTenDanhMuc(rs.getString(2));
				dsdanhmuc.add(dm);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsdanhmuc;
	}

}

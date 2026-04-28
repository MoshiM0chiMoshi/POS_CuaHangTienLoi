package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.NhanVien;

public class NhanVien_Dao {
	ArrayList<NhanVien> dsnv;
	NhanVien nv;

	public NhanVien_Dao() {
		dsnv = new ArrayList<NhanVien>();
		nv = new NhanVien();
	}

	public ArrayList<NhanVien> getallNhanVien() {
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql = "Select * from NhanVien";
			java.sql.Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String manv = rs.getString(1);
				String tennv = rs.getString(2);
				String sdt = rs.getString(3);

				NhanVien nv = new NhanVien(manv, tennv, sdt);
				dsnv.add(nv);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsnv;
	}

}

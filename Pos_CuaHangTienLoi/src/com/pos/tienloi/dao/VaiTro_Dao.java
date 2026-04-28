package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.VaiTro;

public class VaiTro_Dao {

	public ArrayList<VaiTro> getallVaiTro() {
		ArrayList<VaiTro> ds = new ArrayList<>();
		String sql = "SELECT * FROM VaiTro";

		Connection con = ConnectDB.getInstance().getConnection();
		if (con == null) {
			throw new IllegalStateException("Chưa kết nối database");
		}

		try (Statement statement = con.createStatement(); ResultSet rs = statement.executeQuery(sql)) {

			while (rs.next()) {
				String ma = rs.getString(1);
				String ten = rs.getString(2);
				String mota = rs.getString(3);
				ds.add(new VaiTro(ma, ten, mota));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ds;
	}

}

package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.Thue;

public class Thue_Dao {

	public ArrayList<Thue> getallSanPham() {
		ArrayList<Thue> dsThue = new ArrayList<Thue>();
		String sql = "Select * from Thue";
		Connection con = ConnectDB.getInstance().getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Thue thue = new Thue();
				thue.setMaThue(rs.getString("maThue"));
				thue.setTenThue(rs.getString("tenThue"));
				thue.setMucThue(rs.getFloat("mucThue"));
				dsThue.add(thue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dsThue;
	}
}

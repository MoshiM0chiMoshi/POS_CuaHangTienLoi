package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.DanhMuc;
import com.pos.tienloi.model.SanPham;
import com.pos.tienloi.model.Thue;

public class SanPham_Dao {

	public ArrayList<SanPham> getAllSanPham() {
		ArrayList<SanPham> dssp = new ArrayList<SanPham>();
		Connection con = ConnectDB.getInstance().getConnection();
		String sql = """
				    SELECT sp.*, dm.maDanhMuc, dm.tenDanhMuc, t.maThue, t.mucThue
				    FROM SanPham sp
				    JOIN DanhMuc dm ON sp.maDanhMuc = dm.maDanhMuc
				    JOIN Thue t ON sp.maThue = t.maThue
				""";
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				DanhMuc dm = new DanhMuc();
				dm.setMaDanhMuc(rs.getString("maDanhMuc"));
				dm.setTenDanhMuc(rs.getString("tenDanhMuc"));
				Thue thue = new Thue();
				thue.setMaThue(rs.getString("maThue"));
				thue.setMucThue(rs.getFloat("mucThue"));

				SanPham sp = new SanPham(rs.getString("hinhAnh"), rs.getString("maSP"), rs.getString("tenSP"),
						rs.getInt("soLuongTon"), rs.getDouble("giaBan"), thue, dm);

				dssp.add(sp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dssp;
	}

}

package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.NhanVien;

public class NhanVien_Dao {

	public ArrayList<NhanVien> getallNhanVien() {
		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
		String sql = "Select * from NhanVien";
		Connection con = ConnectDB.getInstance().getConnection();
		if (con == null) {
			throw new IllegalStateException("Chưa kết nối database");
		}

		try (java.sql.Statement statement = con.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			while (rs.next()) {
				String manv = rs.getString("maNV");
				String tennv = rs.getString("tenNV");
				String sdt = rs.getString("sdt");

				NhanVien nv = new NhanVien(manv, tennv, sdt);
				dsnv.add(nv);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsnv;
	}

	public ArrayList<Object[]> getDuLieuNhanVienFull() {
		ArrayList<Object[]> ds = new ArrayList<>();

		String sql = """
				    SELECT nv.maNV, nv.tenNV, nv.sdt, vt.tenVaiTro
				    FROM NhanVien nv
				    LEFT JOIN TaiKhoan tk ON nv.maNV = tk.maNV
				    LEFT JOIN VaiTro vt ON tk.maVaiTro = vt.maVaiTro
				""";

		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Object[] row = { rs.getString("maNV"), rs.getString("tenNV"), rs.getString("sdt"),
						rs.getString("tenVaiTro") };
				ds.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

}

package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.NhanVien;
import com.pos.tienloi.model.TaiKhoan;
import com.pos.tienloi.model.VaiTro;

public class TaiKhoan_Dao {

	public ArrayList<TaiKhoan> getallTaiKhoan() {
		ArrayList<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
		String sql = """
				SELECT tk.*, vt.tenVaiTro, nv.tenNV
				FROM TaiKhoan tk
				LEFT JOIN VaiTro vt ON tk.maVaiTro = vt.maVaiTro
				LEFT JOIN NhanVien nv ON tk.maNV = nv.maNV
				""";
		Connection con = ConnectDB.getInstance().getConnection();
		if (con == null) {
			throw new IllegalStateException("Chưa kết nối được database");
		}

		try (java.sql.Statement statement = con.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				NhanVien nv = new NhanVien();
				nv.setTenNV(rs.getString("tenNV"));

				VaiTro vt = new VaiTro();
				vt.setTenVaiTro(rs.getString("tenVaiTro"));

				TaiKhoan tk = new TaiKhoan();
				tk.setNhanVien(nv);
				tk.setMatKhau(rs.getString("matKhau"));
				tk.setVaiTro(vt);

				dstk.add(tk);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dstk;
	}

	public boolean themTaiKhoan(String maNV, String matKhau, String tenVaiTro) {
		String sql = """
				INSERT INTO TaiKhoan (maNV, matKhau, maVaiTro)
				VALUES (?, ?, (SELECT maVaiTro FROM VaiTro WHERE tenVaiTro = ?))
				""";
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement pst = null;

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, maNV);
			ps.setString(2, matKhau);
			ps.setString(3, tenVaiTro);

			int n = ps.executeUpdate();
			return n > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean capNhatTaiKhoan(String maNV, String matKhau, String tenVaiTro) {

		String sql = """
				UPDATE TaiKhoan
				SET matKhau = ?, maVaiTro = (SELECT maVaiTro FROM VaiTro WHERE tenVaiTro = ?)
				WHERE maNV = ?
				""";

		try (Connection con = ConnectDB.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, matKhau);
			ps.setString(2, tenVaiTro);
			ps.setString(3, maNV);

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}

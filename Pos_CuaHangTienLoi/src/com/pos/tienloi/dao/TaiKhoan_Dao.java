package com.pos.tienloi.dao;

import java.sql.Connection;
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
				    SELECT tk.maNV, tk.matKhau, tk.maVaiTro,
				           nv.tenNV, nv.sdt,
				           vt.tenVaiTro, vt.moTa
				    FROM TaiKhoan tk
				    JOIN NhanVien nv ON tk.maNV = nv.maNV
				    JOIN VaiTro vt ON tk.maVaiTro = vt.maVaiTro
				""";
		Connection con = ConnectDB.getInstance().getConnection();
		if (con == null) {
			throw new IllegalStateException("Chưa kết nối được database");
		}

		try (java.sql.Statement statement = con.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
			if (rs.next()) {
				NhanVien nv = new NhanVien();
				nv.setMaNV(rs.getString("maNV"));
				nv.setTenNV(rs.getString("tenNV"));
				nv.setSdt(rs.getString("sdt"));

				VaiTro vt = new VaiTro();
				vt.setMaVaiTro(rs.getString("maVaiTro"));
				vt.setTenVaiTro(rs.getString("tenVaiTro"));
				vt.setMoTa(rs.getString("moTa"));

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

}

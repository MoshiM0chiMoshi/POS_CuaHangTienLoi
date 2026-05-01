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
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = """
				    SELECT sp.*, dm.maDanhMuc, dm.tenDanhMuc, t.maThue, t.mucThue
				    FROM SanPham sp
				    JOIN DanhMuc dm ON sp.maDanhMuc = dm.maDanhMuc
				    JOIN Thue t ON sp.maThue = t.maThue
				    WHERE sp.trangThai = 1
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
						rs.getInt("soLuongTon"), rs.getFloat("giaBan"), thue, dm);

				dssp.add(sp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dssp;
	}

	public boolean create(SanPham sp) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		if (con == null) {
			throw new IllegalStateException("Không thể kết nối được DB");
		}
		String sql = "INSERT INTO SanPham (maSP, tenSP, soLuongTon, giaBan, maThue, maDanhMuc, hinhAnh) VALUES (?, ?, ?, ?, ?, ?, ?)";
		int n = 0;
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, sp.getMaSP());
			ps.setString(2, sp.getTenSP());
			ps.setInt(3, sp.getSoLuongTon());
			ps.setDouble(4, sp.getGiaBan());
			ps.setString(5, sp.getThue().getMaThue());
			ps.setString(6, sp.getDanhMuc().getMaDanhMuc());
			ps.setString(7, sp.getHinhAnh());

			n = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;

	}

	public boolean update(SanPham sp) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "UPDATE SanPham SET tenSP=?, soLuongTon=?, giaBan=?, maThue=?, maDanhMuc=?, hinhAnh=? WHERE maSP=?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, sp.getTenSP());
			ps.setInt(2, sp.getSoLuongTon());
			ps.setDouble(3, sp.getGiaBan());
			ps.setString(4, sp.getThue().getMaThue());
			ps.setString(5, sp.getDanhMuc().getMaDanhMuc());
			ps.setString(6, sp.getHinhAnh());
			ps.setString(7, sp.getMaSP());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean softDelete(String maSP) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		String sql = "UPDATE SanPham SET trangThai = 0 WHERE maSP = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, maSP);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}

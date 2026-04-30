package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.ChiTietHoaDon;
import com.pos.tienloi.model.SanPham;

public class ChiTietHoaDon_Dao {

	public ArrayList<ChiTietHoaDon> getChiTietByMaHD(String maHoaDon) {
		ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		String sql = "SELECT ct.*, sp.tenSP, sp.giaBan, sp.hinhAnh " + "FROM ChiTietHoaDon ct "
				+ "JOIN SanPham sp ON ct.maSP = sp.maSP " + "WHERE ct.maHoaDon = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, maHoaDon);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ChiTietHoaDon ct = new ChiTietHoaDon();
				ct.setDonGia(rs.getFloat("donGia"));
				ct.setSoLuong(rs.getInt("soLuong"));

				SanPham sp = new SanPham();
				sp.setMaSP(rs.getString("maSP"));
				sp.setTenSP(rs.getString("tenSP"));
				sp.setGiaBan(rs.getFloat("giaBan"));
				sp.setHinhAnh(rs.getString("hinhAnh"));

				dsCTHD.add(ct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsCTHD;
	}

	public boolean create(ChiTietHoaDon ct, String maHD) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "INSERT INTO ChiTietHoaDon(maHoaDon, maSP, soLuong, donGia) VALUES (?, ?, ?, ?)";
		int n = 0;
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, maHD);
			ps.setString(2, ct.getSanPham().getMaSP());
			ps.setInt(3, ct.getSoLuong());
			ps.setFloat(4, (float) ct.getDonGia());

			n = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;

	}

	public boolean createList(List<ChiTietHoaDon> dsCT, String maHD) {
		for (ChiTietHoaDon ct : dsCT) {
			// Gọi lại hàm create lẻ mà bạn đã viết ở trên
			if (!create(ct, maHD)) {
				return false; // Nếu có 1 dòng lỗi thì báo thất bại
			}
		}
		return true;
	}
}
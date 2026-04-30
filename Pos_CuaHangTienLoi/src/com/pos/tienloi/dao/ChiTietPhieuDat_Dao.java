package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.ChiTietPhieuDat;
import com.pos.tienloi.model.SanPham;

public class ChiTietPhieuDat_Dao {

	public ArrayList<ChiTietPhieuDat> getChiTietByMaHD(String maPhieuDat) {
		ArrayList<ChiTietPhieuDat> dsCTPD = new ArrayList<>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		String sql = "SELECT ct.*, sp.tenSP, sp.giaBan, sp.hinhAnh " + "FROM ChiTietPhieuDat ct "
				+ "JOIN SanPham sp ON ct.maSP = sp.maSP " + "WHERE ct.maPhieuDat = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, maPhieuDat);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ChiTietPhieuDat ct = new ChiTietPhieuDat();
				ct.setDonGiaDat(rs.getFloat("donGiaDat"));
				ct.setSoLuongDat(rs.getInt("soLuongDat"));

				SanPham sp = new SanPham();
				sp.setMaSP(rs.getString("maSP"));
				sp.setTenSP(rs.getString("tenSP"));
				sp.setGiaBan(rs.getFloat("giaBan"));
				sp.setHinhAnh(rs.getString("hinhAnh"));
				ct.setSanPham(sp);

				dsCTPD.add(ct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsCTPD;
	}

	public boolean create(ChiTietPhieuDat pd, String maPD) {
		Connection con = ConnectDB.getConnection();
		String sql = "INSERT INTO ChiTietPhieuDat(maPhieuDat, maSP, soLuongDat, donGiaDat) VALUES (?, ?, ?, ?)";
		int n = 0;

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, maPD);
			ps.setString(2, pd.getSanPham().getMaSP());
			ps.setInt(3, pd.getSoLuongDat());
			ps.setDouble(4, pd.getDonGiaDat());

			n = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public boolean createList(List<ChiTietPhieuDat> dsPD, String maPD) {
		for (ChiTietPhieuDat ct : dsPD) {
			// Gọi lại hàm create lẻ mà bạn đã viết ở trên
			if (!create(ct, maPD)) {
				return false; // Nếu có 1 dòng lỗi thì báo thất bại
			}
		}
		return true;
	}

}

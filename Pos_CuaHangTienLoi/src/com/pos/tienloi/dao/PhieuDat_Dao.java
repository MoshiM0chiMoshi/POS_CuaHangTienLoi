package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.KhachHang;
import com.pos.tienloi.model.NhanVien;
import com.pos.tienloi.model.PhieuDatHang;
import com.pos.tienloi.model.TrangThaiPhieuDat;

public class PhieuDat_Dao {

	public ArrayList<PhieuDatHang> getallPhieuDat() {
		ArrayList<PhieuDatHang> dspd = new ArrayList<PhieuDatHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "Select pd.*, kh.tenKhachHang, nv.tenNV " + "FROM PhieuDatHang pd "
				+ "LEFT JOIN KhachHang kh ON pd.sdtKhachHang = kh.sdt " + "LEFT JOIN NhanVien nv ON pd.maNV = nv.maNV";
		ChiTietPhieuDat_Dao chiTiet = new ChiTietPhieuDat_Dao();

		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				PhieuDatHang pd = new PhieuDatHang();
				pd.setMaPhieuDat(rs.getString("maPhieuDat"));
				pd.setNgayDat(rs.getDate("ngayDat"));
				pd.setDiaChi(rs.getString("diaChi"));

				String trangThaiStr = rs.getString("trangThai");
				if (trangThaiStr != null && !trangThaiStr.isBlank()) {
					pd.setTrangThai(TrangThaiPhieuDat.valueOf(trangThaiStr));
				}

				KhachHang kh = new KhachHang();
				kh.setSdt(rs.getString(1));
				kh.setTenKhachHang(rs.getString("tenKhachHang"));
				pd.setKhachHang(kh);

				NhanVien nv = new NhanVien();
				nv.setMaNV(rs.getString("maNV"));
				nv.setTenNV(rs.getString("tenNV"));
				pd.setNhanVien(nv);
				pd.setListChiTietPhieu(chiTiet.getChiTietByMaHD(pd.getMaPhieuDat()));

				dspd.add(pd);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dspd;

	}

	public boolean create(PhieuDatHang pd) {
		int n = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		if (con == null) {
			throw new IllegalStateException("Phiếu đặt hàng Dao không kết nối được");
		}
		String sql = "INSERT INTO PhieuDatHang (maPhieuDat, ngayDat, tongTien, diaChi, trangThai, maNV, sdtKhachHang) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, pd.getMaPhieuDat());
			ps.setDate(2, new java.sql.Date(pd.getNgayDat().getTime()));
			ps.setFloat(3, pd.getTongTien());
			ps.setString(4, pd.getDiaChi());
			ps.setString(5, pd.getTrangThai() != null ? pd.getTrangThai().name() : null);
			ps.setString(6, pd.getNhanVien() != null ? pd.getNhanVien().getMaNV() : null);
			ps.setString(7, pd.getKhachHang() != null ? pd.getKhachHang().getSdt() : null);
			n = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return n > 0;
	}

	public String generateNextMaPD() {
		String nextMaPD = "PD001";
		String sql = "SELECT TOP 1 maPhieuDat FROM PhieuDatHang ORDER BY maPhieuDat DESC";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = ConnectDB.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			if (rs.next()) {
				String maxMaPD = rs.getString("maPhieuDat");
				if (maxMaPD != null && maxMaPD.length() > 2) {
					String soHienTaiStr = maxMaPD.substring(2);
					int soHienTai = Integer.parseInt(soHienTaiStr);
					int soTiepTheo = soHienTai + 1;
					nextMaPD = String.format("PD%03d", soTiepTheo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nextMaPD;

	}

	public boolean updateTrangThai(String maPhieuDat, TrangThaiPhieuDat trangThaiMoi) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		int n = 0;

		String sql = "UPDATE PhieuDatHang SET trangThai = ? WHERE maPhieuDat = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, trangThaiMoi.name()); // QUAN TRỌNG
			ps.setString(2, maPhieuDat);
			n = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public boolean huyPhieuDat(String maPhieuDat) {
		return updateTrangThai(maPhieuDat, TrangThaiPhieuDat.DA_HUY);
	}
}

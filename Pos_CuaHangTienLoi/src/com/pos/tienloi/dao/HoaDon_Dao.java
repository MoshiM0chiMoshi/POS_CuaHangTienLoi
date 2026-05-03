package com.pos.tienloi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pos.tienloi.connectDB.ConnectDB;
import com.pos.tienloi.model.HoaDon;
import com.pos.tienloi.model.KhachHang;
import com.pos.tienloi.model.NhanVien;
import com.pos.tienloi.model.PTTT;
import com.pos.tienloi.model.TrangThaiHoaDon;

public class HoaDon_Dao {
	public ArrayList<HoaDon> getallHoaDon() {
		ArrayList<HoaDon> dshd = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		ChiTietHoaDon_Dao cthdDao = new ChiTietHoaDon_Dao();

		String sql = "SELECT hd.*, kh.tenKhachHang, nv.tenNV " + "FROM HoaDon hd "
				+ "LEFT JOIN KhachHang kh ON hd.sdtKhachHang = kh.sdt " + "LEFT JOIN NhanVien nv ON hd.maNV = nv.maNV";

		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

			while (rs.next()) {
				HoaDon hd = new HoaDon();

				hd.setMaHoaDon(rs.getString("maHoaDon"));
				hd.setNgayLap(rs.getDate("ngayLap"));

				String trangThaiStr = rs.getString("trangThai");
				if (trangThaiStr != null && !trangThaiStr.isEmpty()) {
					hd.setTrangThai(TrangThaiHoaDon.valueOf(trangThaiStr));
				}

				String phuongThucStr = rs.getString("phuongThuc");
				if (phuongThucStr != null && !phuongThucStr.isEmpty()) {
					hd.setPhuongThuc(PTTT.valueOf(phuongThucStr));
				}

				// --- MAP THÔNG TIN KHÁCH HÀNG ---

				String sdt = rs.getString("sdtKhachHang");
				if (sdt != null && !sdt.trim().isEmpty()) {
					KhachHang kh = new KhachHang();
					kh.setSdt(sdt);
					kh.setTenKhachHang(rs.getString("tenKhachHang"));
					hd.setKhachHang(kh);
				} else {
					hd.setKhachHang(null);
				}
				// --- MAP THÔNG TIN NHÂN VIÊN ---
				NhanVien nv = new NhanVien();

				nv.setMaNV(rs.getString("maNV"));

				nv.setTenNV(rs.getString("tenNV"));
				hd.setNhanVien(nv);
				hd.setListChiTietHoaDon(cthdDao.getChiTietByMaHD(hd.getMaHoaDon()));

				dshd.add(hd);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dshd;
	}

	public boolean create(HoaDon hd) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		if (con == null) {
			System.out.println("Connection is null");
			return false;
		}

		try {
			if (con.isClosed()) {
				System.out.println("Connection đã bị đóng");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		String sql = "INSERT INTO HoaDon (maHoaDon, ngayLap, tongTien, phuongThuc, trangThai, maNV, sdtKhachHang) VALUES (?, ?, ?, ?, ?, ?, ?)";
		int n = 0;

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, hd.getMaHoaDon());
			ps.setDate(2, new java.sql.Date(hd.getNgayLap().getTime()));
			ps.setFloat(3, (float) hd.getTongTien());
			ps.setString(4, hd.getPhuongThuc() != null ? hd.getPhuongThuc().name() : null);
			ps.setString(5, hd.getTrangThai() != null ? hd.getTrangThai().name() : null);
			ps.setString(6, hd.getNhanVien() != null ? hd.getNhanVien().getMaNV() : null);
			ps.setString(7, hd.getKhachHang() != null ? hd.getKhachHang().getSdt() : null);

			n = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public String generateNextMaHD() {
		String nextMaHD = "HD001";
		String sql = "SELECT TOP 1 maHoaDon FROM HoaDon ORDER BY maHoaDon DESC";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = ConnectDB.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			if (rs.next()) {
				String maxMaHD = rs.getString("maHoaDon");
				if (maxMaHD != null && maxMaHD.length() > 2) {
					String soHienTaiStr = maxMaHD.substring(2);
					int soHienTai = Integer.parseInt(soHienTaiStr);
					int soTiepTheo = soHienTai + 1;
					nextMaHD = String.format("HD%03d", soTiepTheo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nextMaHD;
	}

	public ArrayList<HoaDon> get3HoaDonGanNhat() {
		ArrayList<HoaDon> dshd = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		ChiTietHoaDon_Dao cthdDao = new ChiTietHoaDon_Dao();

		String sql = "SELECT TOP 3 hd.*, kh.tenKhachHang, nv.tenNV " + "FROM HoaDon hd "
				+ "LEFT JOIN KhachHang kh ON hd.sdtKhachHang = kh.sdt " + "LEFT JOIN NhanVien nv ON hd.maNV = nv.maNV "
				+ "ORDER BY hd.ngayLap DESC, hd.maHoaDon DESC";

		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				HoaDon hd = new HoaDon();

				hd.setMaHoaDon(rs.getString("maHoaDon"));
				hd.setNgayLap(rs.getDate("ngayLap"));

				String trangThaiStr = rs.getString("trangThai");
				if (trangThaiStr != null && !trangThaiStr.isEmpty()) {
					hd.setTrangThai(TrangThaiHoaDon.valueOf(trangThaiStr));
				}

				// Chi tiết hóa đơn
				hd.setListChiTietHoaDon(cthdDao.getChiTietByMaHD(hd.getMaHoaDon()));

				dshd.add(hd);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dshd;
	}

	public int getTotalHoaDon() {
		int total = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT COUNT(*) FROM HoaDon";
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

}
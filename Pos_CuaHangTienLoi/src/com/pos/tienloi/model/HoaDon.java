package com.pos.tienloi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDon {
	private String maHoaDon;
	private Date ngayLap;
	private double tongTien;
	private PTTT phuongThuc;
	private TrangThaiHoaDon trangThai;
	private NhanVien nhanVien;
	private KhachHang khachHang;
	private List<ChiTietHoaDon> listChiTietHoaDon;

	public HoaDon() {
		this.listChiTietHoaDon = new ArrayList<>();
	}

	public HoaDon(String maHoaDon, Date ngayLap, double tongTien, PTTT phuongThuc, TrangThaiHoaDon trangThai,
			NhanVien nhanVien, KhachHang khachHang, List<ChiTietHoaDon> listChiTietHoaDon) {
		this.maHoaDon = maHoaDon;
		this.ngayLap = ngayLap;
		this.tongTien = tongTien;
		this.phuongThuc = phuongThuc;
		this.trangThai = trangThai;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.listChiTietHoaDon = (listChiTietHoaDon != null) ? listChiTietHoaDon : new ArrayList<>();
	}

	public double tinhTongTien() {
		double tong = 0;
		for (ChiTietHoaDon ct : listChiTietHoaDon) {
			tong += ct.tinhThanhTien();
		}
		this.tongTien = tong;
		return tong;
	}

	public void themChiTiet(ChiTietHoaDon chiTietHoaDon) {
		if (chiTietHoaDon != null) {
			this.listChiTietHoaDon.add(chiTietHoaDon);
			tinhTongTien();
		}
	}

	public void capNhatTrangThai(TrangThaiHoaDon tr) {
		if (tr == null) {
			throw new IllegalArgumentException("Trạng thái không hợp lệ");
		}
		switch (this.trangThai) {
		case Pending:
			if (tr == TrangThaiHoaDon.Paid || tr == TrangThaiHoaDon.Cancelled) {
				this.trangThai = tr;
			} else {
				throw new IllegalStateException("Không thể chuyển từ Pending sang " + tr);
			}
			break;
		case Paid:
			throw new IllegalStateException("Hóa đơn đã thanh toán không thể thay đổi trạng thái");
		case Cancelled:
			throw new IllegalStateException("Hóa đơn đã hủy không thể thay đổi trạng thái");
		default:
			throw new IllegalStateException("Trạng thái không hợp lệ");
		}

	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	public PTTT getPhuongThuc() {
		return phuongThuc;
	}

	public void setPhuongThuc(PTTT phuongThuc) {
		this.phuongThuc = phuongThuc;
	}

	public TrangThaiHoaDon getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(TrangThaiHoaDon trangThai) {
		this.trangThai = trangThai;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public List<ChiTietHoaDon> getListChiTietHoaDon() {
		return listChiTietHoaDon;
	}

	public void setListChiTietHoaDon(List<ChiTietHoaDon> listChiTietHoaDon) {
		this.listChiTietHoaDon = listChiTietHoaDon;
	}

	@Override
	public String toString() {
		return "HoaDon{" + "maHoaDon='" + maHoaDon + '\'' + ", ngayLap=" + ngayLap + ", tongTien=" + tongTien
				+ ", phuongThuc=" + phuongThuc + ", trangThai=" + trangThai + ", nhanVien=" + nhanVien + ", khachHang="
				+ khachHang + ", listChiTietHoaDon=" + listChiTietHoaDon + '}';
	}
}
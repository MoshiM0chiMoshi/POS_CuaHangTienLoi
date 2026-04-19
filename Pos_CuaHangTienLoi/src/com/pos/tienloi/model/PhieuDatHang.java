package com.pos.tienloi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuDatHang {
	private String maPhieuDat;
	private Date ngayDat;
	private double tongTien;
	private TrangThaiPhieuDat trangThai;
	private NhanVien nhanVien;
	private KhachHang khachHang;
	private List<ChiTietPhieuDat> listChiTietPhieu;

	public PhieuDatHang() {
		this.listChiTietPhieu = new ArrayList<>();
	}

	public PhieuDatHang(String maPhieuDat, double tongTien, Date ngayDat, TrangThaiPhieuDat trangThai,
			NhanVien nhanVien, KhachHang khachHang, List<ChiTietPhieuDat> listChiTietPhieu) {
		this.maPhieuDat = maPhieuDat;
		this.ngayDat = ngayDat;
		this.tongTien = tongTien;
		this.trangThai = trangThai;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.listChiTietPhieu = (listChiTietPhieu != null) ? listChiTietPhieu : new ArrayList<>();
	}

	public double tinhTongTien() {
		double tong = 0;
		for (ChiTietPhieuDat ct : listChiTietPhieu) {
			tong += ct.tinhThanhTien();
		}
		this.tongTien = tong;
		return tong;
	}
	
	 public void capNhatTongTien() {
	        tinhTongTien();
	    }

	public void capNhatTrangThai(TrangThaiPhieuDat tr) {
		if (tr == null) {
			throw new IllegalArgumentException("Trạng thái không hợp lệ");
		}
		switch (this.trangThai) {
		case CHO_DUYET:
			if (tr == TrangThaiPhieuDat.DA_HUY || tr == TrangThaiPhieuDat.DANG_XU_LY) {
				this.trangThai = tr;
			} else {
				throw new IllegalStateException("Không thể chuyển từ chờ duyệt sang " + tr);
			}
			break;
		case DANG_XU_LY:
			if (tr == TrangThaiPhieuDat.DA_HUY || tr == TrangThaiPhieuDat.HOAN_TAT) {
				this.trangThai = tr;

			} else {
				throw new IllegalStateException("Không thể chuyển từ đang xử lý sang " + tr);
			}
			break;
		  case HOAN_TAT:
	            throw new IllegalStateException("Phiếu đã hoàn tất, không thể thay đổi");

	        case DA_HUY:
	            throw new IllegalStateException("Phiếu đã hủy, không thể thay đổi");

	        default:
				throw new IllegalStateException("Trạng thái hiện tại không hợp lệ");

			}
	}

	public double getTongTien() {
		return tongTien;
	}

	public String getMaPhieuDat() {
		return maPhieuDat;
	}

	public void setMaPhieuDat(String maPhieuDat) {
		this.maPhieuDat = maPhieuDat;
	}

	public Date getNgayDat() {
		return ngayDat;
	}

	public void setNgayDat(Date ngayDat) {
		this.ngayDat = ngayDat;
	}

	public TrangThaiPhieuDat getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(TrangThaiPhieuDat trangThai) {
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

	public List<ChiTietPhieuDat> getListChiTietPhieu() {
		return listChiTietPhieu;
	}

	public void setListChiTietPhieu(List<ChiTietPhieuDat> listChiTietPhieu) {
		this.listChiTietPhieu = listChiTietPhieu;
	}

	@Override
	public String toString() {
		return "PhieuDatHang{" + "maPhieuDat='" + maPhieuDat + '\'' + ", ngayDat=" + ngayDat + ", trangThai="
				+ trangThai + ", nhanVien=" + nhanVien + ", khachHang=" + khachHang + ", listChiTietPhieu="
				+ listChiTietPhieu + '}';
	}
}
package com.pos.tienloi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuDatHang {
	private String maPhieuDat;
	private KhachHang khachHang;
	private Date ngayDat;
	private String diaChi;
	private float tongTien;
	private TrangThaiPhieuDat trangThai;
	private NhanVien nhanVien;

	private List<ChiTietPhieuDat> listChiTietPhieu;

	public PhieuDatHang() {
		this.maPhieuDat = "";
		this.khachHang = null;
		this.ngayDat = new Date();
		this.diaChi = "";
		this.tongTien = 0;
		this.trangThai = null;
		this.nhanVien = null;
		this.listChiTietPhieu = new ArrayList<>();
	}

	public PhieuDatHang(String maPhieuDat, KhachHang khachHang, Date ngayDat, String diaChi, float tongTien,
			TrangThaiPhieuDat trangThai, NhanVien nhanVien, List<ChiTietPhieuDat> listChiTietPhieu) {
		super();
		this.maPhieuDat = maPhieuDat;
		this.khachHang = khachHang;
		this.ngayDat = ngayDat;
		this.diaChi = diaChi;
		this.tongTien = tongTien;
		this.trangThai = trangThai;
		this.nhanVien = nhanVien;
		this.listChiTietPhieu = listChiTietPhieu;
	}

	public double tinhTongTien() {
		tinhTongTien();
		return tongTien;

	}

	public void capNhatTongTien() {

		float tong = 0;
		if (listChiTietPhieu != null) {
			for (ChiTietPhieuDat ct : listChiTietPhieu) {
				if (ct != null) {
					tong += ct.tinhThanhTien();
				}
			}
		}
		this.tongTien = tong;

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

	public float getTongTien() {
		return tongTien;
	}

	public String getMaPhieuDat() {
		return maPhieuDat;
	}

	public void setMaPhieuDat(String maPhieuDat) {
		this.maPhieuDat = (maPhieuDat == null) ? "" : maPhieuDat.trim();
	}

	public Date getNgayDat() {
		return ngayDat;
	}

	public void setNgayDat(Date ngayDat) {
		this.ngayDat = (ngayDat == null) ? new Date() : new Date(ngayDat.getTime());
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
		this.listChiTietPhieu = (listChiTietPhieu == null) ? new ArrayList<>() : new ArrayList<>(listChiTietPhieu);
		capNhatTongTien();
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	@Override
	public String toString() {
		return "PhieuDatHang{" + "maPhieuDat='" + maPhieuDat + '\'' + ", ngayDat=" + ngayDat + ", trangThai="
				+ trangThai + ", nhanVien=" + nhanVien + ", khachHang=" + khachHang + ", listChiTietPhieu="
				+ listChiTietPhieu + '}';
	}
}
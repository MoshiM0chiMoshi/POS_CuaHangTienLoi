package com.pos.tienloi.model;

public class SanPham {
	private String hinhAnh;
	private String maSP;
	private String tenSP;
	private int soLuongTon;
	private float giaBan;
	private Thue thue;
	private DanhMuc danhMuc;

	public SanPham() {
	}

	public SanPham(String hinhAnh, String maSP, String tenSP, int soLuongTon, float giaBan, Thue thue,
			DanhMuc danhMuc) {
		super();
		this.hinhAnh = hinhAnh;
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.soLuongTon = soLuongTon;
		this.giaBan = giaBan;
		this.thue = thue;
		this.danhMuc = danhMuc;
	}

	public void tangSoLuong(int soLuong) {
		if (soLuong > 0) {
			this.soLuongTon += soLuong;
		}
	}

	public boolean kiemTraTonKho(int soLuongYeuCau) {
		return soLuongYeuCau > 0 && this.soLuongTon >= soLuongYeuCau;
	}

	public void giamTonKho(int soLuongBan) {
		if (soLuongBan <= 0) {
			throw new IllegalArgumentException("Số lượng bán phải lớn hơn 0");
		}
		if (soLuongBan > soLuongTon) {
			throw new IllegalArgumentException("Không đủ tồn kho");
		}
		this.soLuongTon -= soLuongBan;
	}

	public double tinhTienThue() {
		return giaBan * thue.getMucThue() / 100;
	}

	public double tinhGiaSauThue() {
		return giaBan + tinhTienThue();
	}

	public String getMaSP() {
		return maSP;
	}

	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}

	public String getTenSP() {
		return tenSP;
	}

	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}

	public double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(float giaBan) {
		this.giaBan = giaBan;
	}

	public int getSoLuongTon() {
		return soLuongTon;
	}

	public void setSoLuongTon(int soLuongTon) {
		if (soLuongTon < 0) {
			throw new IllegalArgumentException("Số lượng tồn không được âm");
		}
		this.soLuongTon = soLuongTon;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	public DanhMuc getDanhMuc() {
		return danhMuc;
	}

	public void setDanhMuc(DanhMuc danhMuc) {
		this.danhMuc = danhMuc;
	}

	public Thue getThue() {
		return thue;
	}

	public void setThue(Thue thue) {
		this.thue = thue;
	}

	@Override
	public String toString() {
		return "SanPham{" + "maSP='" + maSP + '\'' + ", tenSP='" + tenSP + '\'' + ", giaBan=" + giaBan + ", soLuongTon="
				+ soLuongTon + ", hinhAnh='" + hinhAnh + '\'' + ", danhMuc=" + danhMuc + ", thue=" + thue + '}';
	}
}
package com.pos.tienloi.model;

public class ChiTietPhieuDat {
	private PhieuDatHang phieuDat;
	private SanPham sanPham;
	private int soLuongDat;
	private float donGiaDat;

	public ChiTietPhieuDat() {
	}

	public ChiTietPhieuDat(PhieuDatHang phieuDat, SanPham sanPham, int soLuongDat, float donGiaDat) {
		super();
		this.phieuDat = phieuDat;
		this.sanPham = sanPham;
		this.soLuongDat = soLuongDat;
		this.donGiaDat = donGiaDat;
	}

	public double tinhThanhTien() {
		return donGiaDat * soLuongDat;
	}

	public PhieuDatHang getPhieuDat() {
		return phieuDat;
	}

	public void setPhieuDat(PhieuDatHang phieuDat) {
		this.phieuDat = phieuDat;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public int getSoLuongDat() {
		return soLuongDat;
	}

	public void setSoLuongDat(int soLuongDat) {
		this.soLuongDat = soLuongDat;
	}

	public double getDonGiaDat() {
		return donGiaDat;
	}

	public void setDonGiaDat(float donGiaDat) {
		this.donGiaDat = donGiaDat;
	}

	@Override
	public String toString() {
		return "ChiTietPhieuDat [phieuDat=" + (phieuDat != null ? phieuDat.getMaPhieuDat() : "null") + ", sanPham="
				+ sanPham + ", soLuongDat=" + soLuongDat + ", donGiaDat=" + donGiaDat + "]";
	}

}
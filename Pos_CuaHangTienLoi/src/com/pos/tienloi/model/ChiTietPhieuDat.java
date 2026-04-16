package com.pos.tienloi.model;

public class ChiTietPhieuDat {
    private PhieuDatHang phieuDat;
    private SanPham sanPham;
    private int soLuongDat;

    public ChiTietPhieuDat() {
    }

    public ChiTietPhieuDat(PhieuDatHang phieuDat, SanPham sanPham, int soLuongDat) {
        this.phieuDat = phieuDat;
        this.sanPham = sanPham;
        this.soLuongDat = soLuongDat;
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

    @Override
    public String toString() {
        return "ChiTietPhieuDat{" +
                "phieuDat=" + phieuDat +
                ", sanPham=" + sanPham +
                ", soLuongDat=" + soLuongDat +
                '}';
    }
}
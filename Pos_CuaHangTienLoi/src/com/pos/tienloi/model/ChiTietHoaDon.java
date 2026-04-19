package com.pos.tienloi.model;

public class ChiTietHoaDon {
    private SanPham sanPham;
    private HoaDon hoaDon;
    private int soLuong;
    private double donGia;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(SanPham sanPham, HoaDon hoaDon, int soLuong, double donGia) {
        this.sanPham = sanPham;
        this.hoaDon = hoaDon;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public double tinhThanhTien() {
        return soLuong * donGia;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

 // Trong lớp ChiTietHoaDon
    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "sanPham=" + sanPham + // Lưu ý: Đảm bảo SanPham cũng không in ngược lại ChiTietHoaDon
                ", maHoaDon=" + (hoaDon != null ? hoaDon.getMaHoaDon() : "null") +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                '}';
    }
}
package com.pos.tienloi.model;

public class KhachHang {
    private String sdt;
    private String tenKhachHang;
    private int diemTichLuy;
    private int soHoaDon;

    public KhachHang() {
    }

    public KhachHang(String sdt, String tenKhachHang, int diemTichLuy, int soHoaDon) {
        this.sdt = sdt;
        this.tenKhachHang = tenKhachHang;
        this.diemTichLuy = diemTichLuy;
        this.soHoaDon = soHoaDon;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public int getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(int diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    public int getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(int soHoaDon) {
        this.soHoaDon = soHoaDon;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "sdt='" + sdt + '\'' +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", diemTichLuy=" + diemTichLuy +
                ", soHoaDon=" + soHoaDon +
                '}';
    }
}
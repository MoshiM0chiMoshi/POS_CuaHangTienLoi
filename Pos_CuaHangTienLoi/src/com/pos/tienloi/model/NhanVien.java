package com.pos.tienloi.model;

public class NhanVien {
    private String maNV;
    private String tenNV;
    private String sdt;

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, String sdt) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.sdt = sdt;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNV='" + maNV + '\'' +
                ", tenNV='" + tenNV + '\'' +
                ", sdt='" + sdt + '\'' +
                '}';
    }
}
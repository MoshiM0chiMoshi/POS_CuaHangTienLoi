package com.pos.tienloi.model;

public class TaiKhoan {
    private NhanVien nhanVien;
    private String MK;
    private VaiTro vaiTro;

    public TaiKhoan() {
    }

    public TaiKhoan(NhanVien nhanVien, String MK, VaiTro vaiTro) {
        this.nhanVien = nhanVien;
        this.MK = MK;
        this.vaiTro = vaiTro;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getMK() {
        return MK;
    }

    public void setMK(String MK) {
        this.MK = MK;
    }

    public VaiTro getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(VaiTro vaiTro) {
        this.vaiTro = vaiTro;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "nhanVien=" + nhanVien +
                ", MK='" + MK + '\'' +
                ", vaiTro=" + vaiTro +
                '}';
    }
}
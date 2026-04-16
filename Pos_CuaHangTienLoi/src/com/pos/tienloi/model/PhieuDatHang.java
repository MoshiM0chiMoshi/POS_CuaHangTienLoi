package com.pos.tienloi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuDatHang {
    private String maPhieuDat;
    private Date ngayDat;
    private TrangThaiPhieuDat trangThai;
    private NhanVien nhanVien;
    private KhachHang khachHang;
    private List<ChiTietPhieuDat> listChiTietPhieu;

    public PhieuDatHang() {
        this.listChiTietPhieu = new ArrayList<>();
    }

    public PhieuDatHang(String maPhieuDat, Date ngayDat, TrangThaiPhieuDat trangThai,
                        NhanVien nhanVien, KhachHang khachHang, List<ChiTietPhieuDat> listChiTietPhieu) {
        this.maPhieuDat = maPhieuDat;
        this.ngayDat = ngayDat;
        this.trangThai = trangThai;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.listChiTietPhieu = (listChiTietPhieu != null) ? listChiTietPhieu : new ArrayList<>();
    }

    public void themChiTiet(ChiTietPhieuDat chiTietPhieuDat) {
        if (chiTietPhieuDat != null) {
            this.listChiTietPhieu.add(chiTietPhieuDat);
        }
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
        return "PhieuDatHang{" +
                "maPhieuDat='" + maPhieuDat + '\'' +
                ", ngayDat=" + ngayDat +
                ", trangThai=" + trangThai +
                ", nhanVien=" + nhanVien +
                ", khachHang=" + khachHang +
                ", listChiTietPhieu=" + listChiTietPhieu +
                '}';
    }
}
package com.pos.tienloi.model;

import java.io.Serializable;
import java.util.Objects;

public class TaiKhoan implements Serializable {
	private NhanVien nhanVien; // Dùng làm username thông qua nhanVien.getMaNV()
	private String matKhau;
	private VaiTro vaiTro;

	public TaiKhoan() {
	}

	public TaiKhoan(NhanVien nhanVien, String matKhau, VaiTro vaiTro) {
		this.nhanVien = nhanVien;
		this.matKhau = matKhau;
		this.vaiTro = vaiTro;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public VaiTro getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(VaiTro vaiTro) {
		this.vaiTro = vaiTro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nhanVien);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaiKhoan other = (TaiKhoan) obj;
		return Objects.equals(nhanVien, other.nhanVien);
	}

	@Override
	public String toString() {
		return "TaiKhoan{" + "nhanVien=" + (nhanVien != null ? nhanVien.getMaNV() : "null") + ", matKhau='***'"
				+ ", vaiTro=" + (vaiTro != null ? vaiTro.getTenVaiTro() : "null") + '}';
	}
}
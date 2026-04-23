package com.pos.tienloi.model;

import java.util.Objects;

public class TaiKhoan {
	private String maNV; // dùng làm username
	private String matKhau;
	private String maVaiTro;

	// dùng khi JOIN
	private NhanVien nhanVien;
	private VaiTro vaiTro;

	public TaiKhoan() {
	}

	public TaiKhoan(String maNV, String matKhau, String maVaiTro) {
		this.maNV = maNV;
		this.matKhau = matKhau;
		this.maVaiTro = maVaiTro;
	}

	public TaiKhoan(String maNV, String matKhau, String maVaiTro, NhanVien nhanVien, VaiTro vaiTro) {
		this.maNV = maNV;
		this.matKhau = matKhau;
		this.maVaiTro = maVaiTro;
		this.nhanVien = nhanVien;
		this.vaiTro = vaiTro;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getMaVaiTro() {
		return maVaiTro;
	}

	public void setMaVaiTro(String maVaiTro) {
		this.maVaiTro = maVaiTro;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public VaiTro getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(VaiTro vaiTro) {
		this.vaiTro = vaiTro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maNV);
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
		return Objects.equals(maNV, other.maNV);
	}

	@Override
	public String toString() {
		return "TaiKhoan{" + "maNV='" + maNV + '\'' + ", matKhau='***'" + ", maVaiTro='" + maVaiTro + '\''
				+ ", nhanVien=" + nhanVien + ", vaiTro=" + vaiTro + '}';
	}
}
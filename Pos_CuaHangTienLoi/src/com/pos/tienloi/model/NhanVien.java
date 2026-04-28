package com.pos.tienloi.model;

import java.io.Serializable;
import java.util.Objects;

public class NhanVien implements Serializable {
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
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNV, other.maNV);
	}

	@Override
	public String toString() {
		return "NhanVien{" + "maNV='" + maNV + '\'' + ", tenNV='" + tenNV + '\'' + ", sdt='" + sdt + '\'' + '}';
	}
}
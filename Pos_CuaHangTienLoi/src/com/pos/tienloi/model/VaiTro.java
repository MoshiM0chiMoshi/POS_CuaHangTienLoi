package com.pos.tienloi.model;

import java.io.Serializable;
import java.util.Objects;

public class VaiTro implements Serializable {
	private String maVaiTro;
	private String tenVaiTro;
	private String moTa;

	public VaiTro() {
	}

	public VaiTro(String maVaiTro, String tenVaiTro, String moTa) {
		this.maVaiTro = maVaiTro;
		this.tenVaiTro = tenVaiTro;
		this.moTa = moTa;
	}

	public String getMaVaiTro() {
		return maVaiTro;
	}

	public void setMaVaiTro(String maVaiTro) {
		this.maVaiTro = maVaiTro;
	}

	public String getTenVaiTro() {
		return tenVaiTro;
	}

	public void setTenVaiTro(String tenVaiTro) {
		this.tenVaiTro = tenVaiTro;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maVaiTro);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VaiTro other = (VaiTro) obj;
		return Objects.equals(maVaiTro, other.maVaiTro);
	}

	@Override
	public String toString() {
		return "VaiTro{" + "maVaiTro='" + maVaiTro + '\'' + ", tenVaiTro='" + tenVaiTro + '\'' + ", moTa='" + moTa
				+ '\'' + '}';
	}
}
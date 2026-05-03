package com.pos.tienloi.model;

public class KhachHang {
	private String sdt;
	private String tenKhachHang;
	private int diemTichLuy;
	private int soHoaDon;

	public KhachHang() {
		this.sdt = "";
		this.tenKhachHang = "Khách lẻ";
		this.diemTichLuy = 0;
		this.soHoaDon = 0;
	}

	public KhachHang(String sdt, String tenKhachHang, int diemTichLuy, int soHoaDon) {
		this();
		setSdt(sdt);
		setTenKhachHang(tenKhachHang);
		setDiemTichLuy(diemTichLuy);
		setSoHoaDon(soHoaDon);
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		if (sdt == null) {
			throw new IllegalArgumentException("Số điện thoại không được null");
		}
		this.sdt = sdt.trim();
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		if (tenKhachHang == null || tenKhachHang.trim().isEmpty()) {
			this.tenKhachHang = "Khách lẻ";
		} else {
			this.tenKhachHang = tenKhachHang.trim();
		}
	}

	public int getDiemTichLuy() {
		return diemTichLuy;
	}

	public void setDiemTichLuy(int diemTichLuy) {
		if (diemTichLuy < 0) {
			throw new IllegalArgumentException("Điểm tích lũy không được âm");
		}
		this.diemTichLuy = diemTichLuy;
	}

	public int getSoHoaDon() {
		return soHoaDon;
	}

	public void setSoHoaDon(int soHoaDon) {
		if (soHoaDon < 0) {
			throw new IllegalArgumentException("Số hóa đơn không được âm");
		}
		this.soHoaDon = soHoaDon;
	}

	@Override
	public String toString() {
		return "KhachHang{" + "sdt='" + sdt + '\'' + ", tenKhachHang='" + tenKhachHang + '\'' + ", diemTichLuy="
				+ diemTichLuy + ", soHoaDon=" + soHoaDon + '}';
	}
}
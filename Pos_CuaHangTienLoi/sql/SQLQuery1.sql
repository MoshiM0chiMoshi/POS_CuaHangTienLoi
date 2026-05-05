-- TẠO CƠ SỞ DỮ LIỆU
CREATE DATABASE CuaHangTienLoi_DB;
GO

USE CuaHangTienLoi_DB;
GO



CREATE TABLE VaiTro (
    maVaiTro VARCHAR(20) PRIMARY KEY,
    tenVaiTro NVARCHAR(50) NOT NULL,
    moTa NVARCHAR(255)
);

CREATE TABLE NhanVien (
    maNV VARCHAR(20) PRIMARY KEY,
    tenNV NVARCHAR(100) NOT NULL,
    sdt VARCHAR(15)
);

CREATE TABLE DanhMuc (
    maDanhMuc VARCHAR(20) PRIMARY KEY,
    tenDanhMuc NVARCHAR(100) NOT NULL
);

CREATE TABLE Thue (
    maThue VARCHAR(20) PRIMARY KEY,
    tenThue NVARCHAR(50) NOT NULL,
    mucThue FLOAT NOT NULL -- Lưu dưới dạng 0.08, 0.1, v.v.
);

CREATE TABLE KhachHang (
    sdt VARCHAR(15) PRIMARY KEY, -- Dùng số điện thoại làm khóa chính
    tenKhachHang NVARCHAR(100) NOT NULL,
    diemTichLuy INT DEFAULT 0,
    soHoaDon INT DEFAULT 0
);


CREATE TABLE TaiKhoan (
    -- maNV vừa là khóa chính của bảng TaiKhoan, vừa tham chiếu đến NhanVien
    -- Điều này đồng nghĩa maNV chính là Tên Đăng Nhập
    maNV VARCHAR(20) PRIMARY KEY, 
    matKhau VARCHAR(255) NOT NULL,
    maVaiTro VARCHAR(20),
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    FOREIGN KEY (maVaiTro) REFERENCES VaiTro(maVaiTro)
);

CREATE TABLE SanPham (
    maSP VARCHAR(20) PRIMARY KEY,
    tenSP NVARCHAR(255) NOT NULL,
    giaBan FLOAT NOT NULL,
    soLuongTon INT NOT NULL,
    hinhAnh VARCHAR(255),
    maDanhMuc VARCHAR(20),
    maThue VARCHAR(20),
    FOREIGN KEY (maDanhMuc) REFERENCES DanhMuc(maDanhMuc),
    FOREIGN KEY (maThue) REFERENCES Thue(maThue)
);



CREATE TABLE HoaDon (
    maHoaDon VARCHAR(20) PRIMARY KEY,
    ngayLap DATETIME NOT NULL DEFAULT GETDATE(),
    tongTien FLOAT NOT NULL,
    phuongThuc VARCHAR(20) CHECK (phuongThuc IN ('tienMat', 'chuyenKhoan', 'vi_Dien_Tu')),
    trangThai VARCHAR(20) CHECK (trangThai IN ('Pending', 'Paid', 'Cancelled')),
    maNV VARCHAR(20),
    sdtKhachHang VARCHAR(15), -- Khóa ngoại liên kết với sdt của KhachHang
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    FOREIGN KEY (sdtKhachHang) REFERENCES KhachHang(sdt)
);

CREATE TABLE PhieuDatHang (
    maPhieuDat VARCHAR(20) PRIMARY KEY,
    ngayDat DATETIME NOT NULL DEFAULT GETDATE(),
    tongTien FLOAT NOT NULL,
    diaChi VARCHAR(50),
    trangThai VARCHAR(20) CHECK (trangThai IN ('CHO_DUYET', 'DANG_XU_LY', 'HOAN_TAT', 'DA_HUY')),
    maNV VARCHAR(20),
    sdtKhachHang VARCHAR(15),
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    FOREIGN KEY (sdtKhachHang) REFERENCES KhachHang(sdt)
);



CREATE TABLE ChiTietHoaDon (
    maHoaDon VARCHAR(20),
    maSP VARCHAR(20),
    soLuong INT NOT NULL,
    donGia FLOAT NOT NULL,
    PRIMARY KEY (maHoaDon, maSP), -- Khóa chính ghép từ 2 khóa ngoại
    FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
    FOREIGN KEY (maSP) REFERENCES SanPham(maSP)
);

CREATE TABLE ChiTietPhieuDat (
    maPhieuDat VARCHAR(20),
    maSP VARCHAR(20),
    soLuongDat INT NOT NULL,
    donGiaDat FLOAT NOT NULL,
    PRIMARY KEY (maPhieuDat, maSP), -- Khóa chính ghép từ 2 khóa ngoại
    FOREIGN KEY (maPhieuDat) REFERENCES PhieuDatHang(maPhieuDat),
    FOREIGN KEY (maSP) REFERENCES SanPham(maSP)
);


-- 1. Chèn dữ liệu bảng VaiTro
INSERT INTO VaiTro (maVaiTro, tenVaiTro, moTa) VALUES
('VT001', N'Quản lý', N'Toàn quyền hệ thống'),
('VT002', N'Nhân viên bán hàng', N'Thực hiện thanh toán và tư vấn'),
('VT003', N'Nhân viên kho', N'Quản lý hàng hóa và nhập kho');

-- 2. Chèn dữ liệu bảng NhanVien (Mã NV + 3 chữ số, SĐT đầu 03, 05, 09)
INSERT INTO NhanVien (maNV, tenNV, sdt) VALUES
('NV001', N'Nguyễn Văn An', '0912345678'),
('NV002', N'Trần Thị Bình', '0356789123'),
('NV003', N'Lê Hoàng Nam', '0582233445'),
('NV004', N'Phạm Minh Thư', '0905566778');

-- 3. Chèn dữ liệu bảng TaiKhoan
INSERT INTO TaiKhoan (maNV, matKhau, maVaiTro) VALUES
('NV001', 'admin123', 'VT001'),
('NV002', 'nv002pass', 'VT002'),
('NV003', 'nv003pass', 'VT003'),
('NV004', 'nv004pass', 'VT002');

-- 4. Chèn dữ liệu bảng DanhMuc
INSERT INTO DanhMuc (maDanhMuc, tenDanhMuc) VALUES
('DM01', N'Thực phẩm tươi sống'),
('DM02', N'Đồ uống'),
('DM03', N'Hóa mỹ phẩm'),
('DM04', N'Đồ ăn nhẹ');

-- 5. Chèn dữ liệu bảng Thue
INSERT INTO Thue (maThue, tenThue, mucThue) VALUES
('VAT0', N'Không thuế', 0.0),
('VAT8', N'Thuế giá trị gia tăng 8%', 0.08),
('VAT10', N'Thuế giá trị gia tăng 10%', 0.1);

-- 6. Chèn dữ liệu bảng KhachHang (SĐT đầu 03, 05, 09)
INSERT INTO KhachHang (sdt, tenKhachHang, diemTichLuy, soHoaDon) VALUES
('0987654321', N'Nguyễn Kỳ Duyên', 150, 5),
('0398887776', N'Trần Quốc Toản', 50, 2),
('0562233441', N'Lý Hải Đăng', 10, 1),
('0945554443', N'Võ Thị Sáu', 200, 8);

-- 7. Chèn dữ liệu bảng SanPham
INSERT INTO SanPham (maSP, tenSP, giaBan, soLuongTon, hinhAnh, maDanhMuc, maThue) VALUES
('SP001', N'Sữa tươi Vinamilk 1L', 32000, 50, 'images/sua_vinamilk.jpg', 'DM02', 'VAT8'),
('SP002', N'Bánh mì Sandwich', 15000, 20, 'images/sandwich.jpg', 'DM01', 'VAT8'),
('SP003', N'Nước rửa chén Sunlight', 25000, 30, 'images/sunlight.jpg', 'DM03', 'VAT10'),
('SP004', N'Snack khoai tây Lay''s', 12000, 100, 'images/lays.jpg', 'DM04', 'VAT8'),
('SP005', N'Nước ngọt Coca Cola 320ml', 10000, 150, 'images/coca_cola.jpg', 'DM02', 'VAT8'),
('SP006', N'Nước suối Aquafina 500ml', 5000, 200, 'images/aquafina.jpg', 'DM02', 'VAT8'),
('SP007', N'Trà Ô Long Tea+ Plus', 12000, 80, 'images/tra_olong.jpg', 'DM02', 'VAT8'),
('SP008', N'Bia Tiger lon 330ml', 18000, 240, 'images/bia_tiger.jpg', 'DM02', 'VAT10');

-- 8. Chèn dữ liệu bảng HoaDon
INSERT INTO HoaDon (maHoaDon, ngayLap, tongTien, phuongThuc, trangThai, maNV, sdtKhachHang) VALUES
('HD001', '2026-04-20 08:30:00', 44000, 'tienMat', 'Paid', 'NV002', '0987654321'),
('HD002', '2026-04-21 14:15:00', 25000, 'chuyenKhoan', 'Paid', 'NV004', '0398887776');

-- 9. Chèn dữ liệu bảng ChiTietHoaDon
INSERT INTO ChiTietHoaDon (maHoaDon, maSP, soLuong, donGia) VALUES
('HD001', 'SP001', 1, 32000),
('HD001', 'SP004', 1, 12000),
('HD002', 'SP003', 1, 25000);

-- 10. Chèn dữ liệu bảng PhieuDatHang
INSERT INTO PhieuDatHang (maPhieuDat, ngayDat, tongTien, diaChi, trangThai, maNV, sdtKhachHang) VALUES
('PD001', '2026-04-25 09:00:00', 64000, '123 Le Loi, Q1', 'HOAN_TAT', 'NV001', '0945554443'),
('PD002', '2026-04-27 10:00:00', 30000, '456 Nguyen Hue, Q1', 'CHO_DUYET', 'NV002', '0562233441');

-- 11. Chèn dữ liệu bảng ChiTietPhieuDat
INSERT INTO ChiTietPhieuDat (maPhieuDat, maSP, soLuongDat, donGiaDat) VALUES
('PD001', 'SP001', 2, 32000),
('PD002', 'SP002', 2, 15000);

ALTER TABLE SanPham
ADD trangThai BIT NOT NULL DEFAULT 1;



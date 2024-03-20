GO
USE [duan1];
GO

--thêm sửa xóa bảng khách hàng
	CREATE PROCEDURE ThemKhachHang
    @TenKH nvarchar(50),
    @SDT nvarchar(12),
    @Email nvarchar(100),
    @DiaChi nvarchar(200),
    @NgaySinh date
    --@VaiTro nvarchar(20),
    --@TenTaiKhoan nvarchar(50),
    --@MatKhau nvarchar(50)
	AS
	BEGIN
		INSERT INTO KhachHang (TenKH, SDT, Email, DiaChi, NgaySinh)
		VALUES ( @TenKH, @SDT, @Email, @DiaChi, @NgaySinh);
	END;

	DROP PROCEDURE IF EXISTS SuaKhachHang;

	CREATE PROCEDURE SuaKhachHang
    --@MaKH INT,
    @TenKH nvarchar(50),
    @SDT nvarchar(12),
    @Email nvarchar(100),
    @DiaChi nvarchar(200),
    @NgaySinh date
    --@VaiTro nvarchar(20),
    --@TenTaiKhoan nvarchar(50),
    --@MatKhau nvarchar(50)
	AS
	BEGIN
		UPDATE KhachHang
		SET 
			SDT = @SDT,
			Email = @Email,
			DiaChi = @DiaChi,
			NgaySinh = @NgaySinh
			--VaiTro = @VaiTro,
			--TenTaiKhoan = @TenTaiKhoan,
			--MatKhau = @MatKhau
		WHERE TenKH = @TenKH;
	END;

	CREATE PROCEDURE XoaKhachHang
    @TenKH nvarchar(50)
	AS
	BEGIN
		DELETE FROM KhachHang
		WHERE TenKH = @TenKH;
	END;

	--thêm sửa xóa bảng nhân viên
	CREATE PROCEDURE ThemNhanVien
    @MaNV nvarchar(10),
    @TenNV nvarchar(50),
    @TenTaiKhoan nvarchar(50),
    @MatKhau nvarchar(50),
    @SDT nvarchar(10),
    @Email nvarchar(100),
    @DiaChi nvarchar(200),
    @VaiTro nvarchar(20),
    @NgaySinh date
AS
BEGIN
    INSERT INTO NhanVien (MaNV, TenNV, TenTaiKhoan, MatKhau, SDT, Email, DiaChi, VaiTro, NgaySinh)
    VALUES (@MaNV, @TenNV, @TenTaiKhoan, @MatKhau, @SDT, @Email, @DiaChi, @VaiTro, @NgaySinh)
END


	CREATE PROCEDURE SuaNhanVien
    @MaNV nvarchar(10),
    @TenNV nvarchar(50),
    @TenTaiKhoan nvarchar(50),
    @MatKhau nvarchar(50),
    @SDT nvarchar(10),
    @Email nvarchar(100),
    @DiaChi nvarchar(200),
    @VaiTro nvarchar(20),
    @NgaySinh date
	AS
	BEGIN
		UPDATE NhanVien
		SET TenNV = @TenNV,
			TenTaiKhoan = @TenTaiKhoan,
			MatKhau = @MatKhau,
			SDT = @SDT,
			Email = @Email,
			DiaChi = @DiaChi,
			VaiTro = @VaiTro,
			NgaySinh = @NgaySinh
		WHERE MaNV = @MaNV
	END


	CREATE PROCEDURE XoaNhanVien
    @MaNV nvarchar(10)
	AS
	BEGIN
		DELETE FROM NhanVien
		WHERE MaNV = @MaNV
	END

	--thêm sửa xóa bảng hóa đơn
	CREATE PROCEDURE ThemHoaDon
    @MaHD nvarchar(10),
    @PhuongThucTT nvarchar(100),
    @TongThanhTien float,
    @NgayTao date,
	@TrangThai nvarchar(100),
    @MaKH INT,
    @MaNV nvarchar(10),
    @MaKM nvarchar(10),
    @MaGH nvarchar(10)
	AS
	BEGIN
		INSERT INTO HoaDon (MaHD, PhuongThucTT, TongThanhTien, NgayTao, TrangThai ,MaKH, MaNV, MaKM, MaGH)
		VALUES (@MaHD, @PhuongThucTT, @TongThanhTien, @NgayTao, @TrangThai,@MaKH, @MaNV, @MaKM, @MaGH)
	END

	DROP PROCEDURE IF EXISTS CapNhatHoaDon;

	CREATE PROCEDURE CapNhatHoaDon
    @MaHD nvarchar(10),
    @PhuongThucTT nvarchar(100),
    @TongThanhTien float,
    @NgayTao date,
	@TrangThai nvarchar(100),
    @MaKH INT,
    @MaNV nvarchar(10),
    @MaKM nvarchar(10),
    @MaGH nvarchar(10)
	AS
	BEGIN
		UPDATE HoaDon
		SET PhuongThucTT = @PhuongThucTT,
			TongThanhTien = @TongThanhTien,
			NgayTao = @NgayTao,
			TrangThai = @TrangThai,
			MaKH = @MaKH,
			MaNV = @MaNV,
			MaKM = @MaKM,
			MaGH = @MaGH
		WHERE MaHD = @MaHD
	END

	CREATE PROCEDURE XoaHoaDon
    @MaHD nvarchar(10)
	AS
	BEGIN
		DELETE FROM HoaDon
		WHERE MaHD = @MaHD
	END

	--thêm sửa xóa bảng giỏ hàng
	CREATE PROCEDURE ThemGioHang
    @MaGH nvarchar(10),
    @GhiChu nvarchar(200)
	AS
	BEGIN
		INSERT INTO GioHang (MaGH,GhiChu)
		VALUES (@MaGH, @GhiChu)
	END

	CREATE PROCEDURE CapNhatGioHang
    @MaGH nvarchar(10),
    @GhiChu nvarchar(200)
	AS
	BEGIN
		UPDATE GioHang
		SET GhiChu = @GhiChu
		WHERE MaGH = @MaGH
	END

	CREATE PROCEDURE XoaGioHang
    @MaGH nvarchar(10)
	AS
	BEGIN
		DELETE FROM GioHang
		WHERE MaGH = @MaGH
	END

	--thêm sửa xóa bảng giohangchitiet
	CREATE PROCEDURE ThemChiTietGioHang
    @MaGH nvarchar(10),
    @MaS nvarchar(10),
    @SoLuong int
	AS
	BEGIN
		INSERT INTO ChiTietGioHang (MaGH, MaS, SoLuong)
		VALUES (@MaGH, @MaS, @SoLuong)
	END	

	CREATE PROCEDURE SuaChiTietGioHang
    @MaGH nvarchar(10),
    @MaS nvarchar(10),
    @SoLuong int
	AS
	BEGIN
		UPDATE ChiTietGioHang
		SET SoLuong = @SoLuong
		WHERE MaGH = @MaGH AND MaS = @MaS
	END

	CREATE PROCEDURE XoaChiTietGioHang
		@MaGH nvarchar(10),
		@MaS nvarchar(10)
	AS
	BEGIN
		DELETE FROM ChiTietGioHang
		WHERE MaGH = @MaGH AND MaS = @MaS
	END

	--thêm sửa xóa bảng sách
	CREATE PROCEDURE ThemSach
    @MaS nvarchar(10),
    @TenS nvarchar(50),
    @TacGia nvarchar(100),
    @DonGia float,
    @NhaXuatBan nvarchar(100),
    @SoLuong int,
    @TenDM nvarchar(100),
    @TenTL nvarchar(100)
AS
BEGIN
    DECLARE @MaDM nvarchar(10);
    DECLARE @MaTL nvarchar(10);
    SELECT @MaDM = MaDM FROM DanhMucSach WHERE TenDM = @TenDM;
    SELECT @MaTL = MaTL FROM TheLoai WHERE TenTL = @TenTL;
    INSERT INTO Sach (MaS, TenS, TacGia, DonGia, NhaXuatBan, SoLuong, MaDM, MaTL)
    VALUES (@MaS, @TenS, @TacGia, @DonGia, @NhaXuatBan, @SoLuong, @MaDM, @MaTL);
END


CREATE PROCEDURE CapNhatSach
    @MaS nvarchar(10),
    @TenS nvarchar(50),
    @TacGia nvarchar(100),
    @DonGia float,
    @NhaXuatBan nvarchar(100),
    @SoLuong int,
    @TenDM nvarchar(100),
    @TenTL nvarchar(100)
AS
BEGIN
    DECLARE @MaDM nvarchar(10);
    DECLARE @MaTL nvarchar(10);
    SELECT @MaDM = MaDM FROM DanhMucSach WHERE TenDM = @TenDM;
    SELECT @MaTL = MaTL FROM TheLoai WHERE TenTL = @TenTL;
    -- Kiểm tra xem có tồn tại mã thể loại và mã danh mục không
    IF @MaTL IS NOT NULL AND @MaDM IS NOT NULL
    BEGIN
        -- Nếu tồn tại, cập nhật thông tin của sách
        UPDATE Sach
        SET TenS = @TenS,
            TacGia = @TacGia,
            DonGia = @DonGia,
            NhaXuatBan = @NhaXuatBan,
            SoLuong = @SoLuong,
            MaDM = @MaDM,
            MaTL = @MaTL
        WHERE MaS = @MaS;
    END
END

CREATE PROCEDURE XoaSach
    @MaS nvarchar(10)
AS
BEGIN
    -- Xóa các dòng từ bảng ChiTietGioHang có tham chiếu đến sách cần xóa
    DELETE FROM ChiTietGioHang WHERE MaS = @MaS;

    -- Sau đó, xóa cuốn sách từ bảng Sach
    DELETE FROM Sach WHERE MaS = @MaS;
END


--thêm sửa xóa bảng khuyến mãi
CREATE PROCEDURE ThemKhuyenMai
    @MaKM nvarchar(10),
    @TenKM nvarchar(100),
    @LoaiKhuyenMai nvarchar(100),
    @GiaTriKhuyenMai nvarchar(100),
    @DieuKienApDung nvarchar(100),
    @HieuLuc date,
	@TrangThai nvarchar(100)
AS
BEGIN
    INSERT INTO KhuyenMai (MaKM, TenKM, LoaiKhuyenMai, GiaTriKhuyenMai, DieuKienApDung, HieuLuc, TrangThai)
    VALUES (@MaKM, @TenKM, @LoaiKhuyenMai, @GiaTriKhuyenMai, @DieuKienApDung, @HieuLuc, @TrangThai)
END

CREATE PROCEDURE CapNhatKhuyenMai
    @MaKM nvarchar(10),
    @TenKM nvarchar(100),
    @LoaiKhuyenMai nvarchar(100),
    @GiaTriKhuyenMai nvarchar(100),
    @DieuKienApDung nvarchar(100),
    @HieuLuc date,
	@TrangThai nvarchar(100)
AS
BEGIN
    UPDATE KhuyenMai
    SET TenKM = @TenKM,
        LoaiKhuyenMai = @LoaiKhuyenMai,
        GiaTriKhuyenMai = @GiaTriKhuyenMai,
        DieuKienApDung = @DieuKienApDung,
        HieuLuc = @HieuLuc,
		TrangThai = @TrangThai
    WHERE MaKM = @MaKM
END

CREATE PROCEDURE XoaKhuyenMai
    @MaKM nvarchar(10)
AS
BEGIN
    -- Xóa tất cả các hóa đơn có tham chiếu đến khuyến mãi cần xóa
    DELETE FROM HoaDon
    WHERE MaKM = @MaKM
    
    -- Sau đó xóa khuyến mãi
    DELETE FROM KhuyenMai
    WHERE MaKM = @MaKM
END


--thêm sửa xóa bảng Kho nhập
CREATE PROCEDURE ThemKhoNhap
    @MaKN nvarchar(10),
    @TenKN nvarchar(100),
    @SDT nvarchar(10),
    @DiaChi nvarchar(200),
    @MaSoThue nvarchar(20)
AS
BEGIN
    INSERT INTO KhoNhap (MaKN, TenKN, SDT, DiaChi, MaSoThue)
    VALUES (@MaKN, @TenKN, @SDT, @DiaChi, @MaSoThue)
END

CREATE PROCEDURE CapNhatKhoNhap
    @MaKN nvarchar(10),
    @TenKN nvarchar(100),
    @SDT nvarchar(10),
    @DiaChi nvarchar(200),
    @MaSoThue nvarchar(20)
AS
BEGIN
    UPDATE KhoNhap
    SET TenKN = @TenKN,
        SDT = @SDT,
        DiaChi = @DiaChi,
        MaSoThue = @MaSoThue
    WHERE MaKN = @MaKN
END

CREATE PROCEDURE XoaKhoNhap
    @MaKN nvarchar(10)
AS
BEGIN
    DELETE FROM KhoNhap
    WHERE MaKN = @MaKN
END

--thêm sửa xóa bảng Thể loại
CREATE PROCEDURE ThemTheLoai
    @MaTL nvarchar(10),
    @TenTL nvarchar(100)
AS
BEGIN
    INSERT INTO TheLoai (MaTL, TenTL)
    VALUES (@MaTL, @TenTL)
END

CREATE PROCEDURE CapNhatTheLoai
    @MaTL nvarchar(10),
    @TenTL nvarchar(100)
AS
BEGIN
    UPDATE TheLoai
    SET TenTL = @TenTL
    WHERE MaTL = @MaTL
END

CREATE PROCEDURE XoaTheLoai
    @MaTL nvarchar(10)
AS
BEGIN
    DELETE FROM TheLoai
    WHERE MaTL = @MaTL
END

--thêm sửa xóa bảng Phiếu Nhập
CREATE PROCEDURE ThemPhieuNhap
    @MaPN nvarchar(10),
    @NgayNhap date,
    @MaNV nvarchar(10),
    @MaKN nvarchar(10),
    @MaPNCT nvarchar(10)
AS
BEGIN
    INSERT INTO PhieuNhap (MaPN, NgayNhap, MaNV, MaKN, MaPNCT)
    VALUES (@MaPN, @NgayNhap, @MaNV, @MaKN, @MaPNCT);
END;


CREATE PROCEDURE SuaPhieuNhap
    @MaPN nvarchar(10),
    @NgayNhap date,
    @MaNV nvarchar(10),
    @MaKN nvarchar(10),
    @MaPNCT nvarchar(10)
AS
BEGIN
    UPDATE PhieuNhap
    SET NgayNhap = @NgayNhap,
        MaNV = @MaNV,
        MaKN = @MaKN,
        MaPNCT = @MaPNCT
    WHERE MaPN = @MaPN;
END;


CREATE PROCEDURE XoaPhieuNhap
    @MaPN nvarchar(10)
AS
BEGIN
    DELETE FROM PhieuNhap
    WHERE MaPN = @MaPN;
END;


--thêm sửa xóa bảng Phiếu Nhập chi tiết
CREATE PROCEDURE ThemPhieuNhapChiTiet
    @MaPNCT nvarchar(10),
    @MaS nvarchar(10),
    @SoLuong int,
    @DonGia float,
    @ThanhTien float,
    @GhiChu nvarchar(200)
AS
BEGIN
    INSERT INTO PhieuNhapChiTiet (MaPNCT, MaS, SoLuong, DonGia, ThanhTien, GhiChu)
    VALUES (@MaPNCT, @MaS, @SoLuong, @DonGia, @ThanhTien, @GhiChu);
END;


CREATE PROCEDURE SuaPhieuNhapChiTiet
    @MaPNCT nvarchar(10),
    @MaS nvarchar(10),
    @SoLuong int,
    @DonGia float,
    @ThanhTien float,
    @GhiChu nvarchar(200)
AS
BEGIN
    UPDATE PhieuNhapChiTiet
    SET MaS = @MaS,
        SoLuong = @SoLuong,
        DonGia = @DonGia,
        ThanhTien = @ThanhTien,
        GhiChu = @GhiChu
    WHERE MaPNCT = @MaPNCT;
END;



CREATE PROCEDURE XoaPhieuNhapChiTiet
    @MaPNCT nvarchar(10)
AS
BEGIN
    DELETE FROM PhieuNhapChiTiet
    WHERE MaPNCT = @MaPNCT;
END;


--thêm sửa xóa bảng Danh Mục Sách
CREATE PROCEDURE ThemDanhMucSach
    @MaDM nvarchar(10),
    @TenDM nvarchar(100)
AS
BEGIN
    INSERT INTO DanhMucSach (MaDM, TenDM)
    VALUES (@MaDM, @TenDM)
END

CREATE PROCEDURE CapNhatDanhMucSach
    @MaDM nvarchar(10),
    @TenDM nvarchar(100)
AS
BEGIN
    UPDATE DanhMucSach
    SET TenDM = @TenDM
    WHERE MaDM = @MaDM
END

CREATE PROCEDURE XoaDanhMucSach
    @MaDM nvarchar(10)
AS
BEGIN
    DELETE FROM DanhMucSach
    WHERE MaDM = @MaDM
END
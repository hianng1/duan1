CREATE DATABASE [duan1];

GO
USE [duan1];
GO


GO
/****** Object:  Table [dbo].[KhachHang] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhachHang](
	[MaKH] INT IDENTITY(1,1) NOT NULL,
	[TenKH] [nvarchar](50)  NULL,
	[SDT] [nvarchar](12) NOT NULL,
	[Email] [nvarchar](100)  NULL,
	[DiaChi] [nvarchar](200)  NULL,
	[NgaySinh] [date]  NULL,
	[VaiTro] [nvarchar](20) NULL,
	[TenTaiKhoan] [nvarchar](50) NULL,
	[MatKhau] [nvarchar](50)  NULL,
 CONSTRAINT [PK_KhachHang] PRIMARY KEY CLUSTERED 
(
	[MaKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[NhanVien] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[MaNV] [nvarchar](10) NOT NULL,
	[TenNV] [nvarchar](50)  NULL,
	[TenTaiKhoan] [nvarchar](50) NOT NULL,
	[MatKhau] [nvarchar](50) NOT NULL,
	[SDT] [nvarchar](10) NOT NULL, 
	[Email] [nvarchar](100)  NULL,
	[DiaChi] [nvarchar](200) NULL,
	[VaiTro] [nvarchar](20) NOT NULL,
	[NgaySinh] [date]  NULL,
 CONSTRAINT [PK_NhanVien] PRIMARY KEY CLUSTERED 
(
	[MaNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

-- chưa nhập bảng hoadon
GO
/****** Object:  Table [dbo].[HoaDon] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDon](
	[MaHD] [nvarchar](10) NOT NULL,
	[PhuongThucTT] [nvarchar](100) NOT NULL,
	[TongThanhTien] [float] NOT NULL,
	[NgayTao] [date] NOT NULL,
	[TrangThai] [nvarchar](100) NOT NULL,
	[MaKH] INT NOT NULL,
	[MaNV] [nvarchar](10) NOT NULL,
	[MaKM] [nvarchar](10) NULL,
	[MaGH] [nvarchar](10) NOT NULL,
 CONSTRAINT [PK_HoaDon] PRIMARY KEY CLUSTERED 
(
	[MaHD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[SanPham] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sach](
	[MaS] [nvarchar](10) NOT NULL,
	[TenS] [nvarchar](50) NOT NULL,
	[TacGia] [nvarchar](100) NOT NULL,
	[DonGia] [float] NOT NULL, 
	[NhaXuatBan] [nvarchar](100) NOT NULL,
	[SoLuong] int not null,
	[MaDM] [nvarchar](10) NULL,
	[MaTL] [nvarchar](10) NULL,
 CONSTRAINT [PK_Sach] PRIMARY KEY CLUSTERED 
(
	[MaS] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[HoaDon] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GioHang](
	[MaGH] [nvarchar](10) NOT NULL,
	[GhiChu] [nvarchar](200) NOT NULL,
 CONSTRAINT [PK_GioHang] PRIMARY KEY CLUSTERED 
(
	[MaGH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[ChiTietGioHang](
	[MaGH] nvarchar(10) NOT NULL,
	[MaS] nvarchar(10) NOT NULL,
	[SoLuong] int NOT NULL,

  CONSTRAINT [PK_ChiTietGioHang] PRIMARY KEY CLUSTERED
(
[MaGH],
[MaS]
),

CONSTRAINT [FK_ChiTietGioHang_GioHang] FOREIGN KEY (MaGH) REFERENCES GioHang(MaGH),
CONSTRAINT [FK_ChiTietGioHang_Sach] FOREIGN KEY (MaS) REFERENCES Sach(MaS)
) ON [PRIMARY]


GO
/****** Object:  Table [dbo].[KhuyenMai] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhuyenMai](
	[MaKM] [nvarchar](10) NOT NULL,
	[TenKM] [nvarchar](100) NOT NULL,
	[LoaiKhuyenMai] [nvarchar](100) NOT NULL,
	[GiaTriKhuyenMai] [nvarchar](100) NOT NULL,
	[DieuKienApDung] [nvarchar](100) NOT NULL,
	[HieuLuc] [date] NOT NULL,
	[TrangThai] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_KhuyenMai] PRIMARY KEY CLUSTERED 
(
	[MaKM] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[KhoNhap] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhoNhap](
	[MaKN] [nvarchar](10) NOT NULL,
	[TenKN] [nvarchar](100) NOT NULL,
	[SDT] [nvarchar](10) NOT NULL, 
	[DiaChi] [nvarchar](200) NOT NULL,
	[MaSoThue] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_KhoNhap] PRIMARY KEY CLUSTERED 
(
	[MaKN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[KhoNhap] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TheLoai](
	[MaTL] [nvarchar](10) NOT NULL,
	[TenTL] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_TheLoai] PRIMARY KEY CLUSTERED 
(
	[MaTL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[KhoNhap] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuNhap](
	[MaPN] [nvarchar](10) NOT NULL,
	[NgayNhap] [date] NOT NULL,
	[MaNV] [nvarchar](10) NOT NULL,
	[MaKN] [nvarchar](10) NOT NULL,
	[MaPNCT] [nvarchar](10) NOT NULL
 CONSTRAINT [PK_PhieuNhap] PRIMARY KEY CLUSTERED 
(
	[MaPN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[PhieuNhapChiTiet](
    [MaPNCT] [nvarchar](10) NOT NULL,
    [MaS] [nvarchar](10) NOT NULL,
    [SoLuong] [int] NOT NULL,
    [DonGia] [float] NOT NULL,
    [ThanhTien] [float] NOT NULL,
    [GhiChu] [nvarchar](200) NOT NULL,
    CONSTRAINT [PK_PhieuNhapChiTiet] PRIMARY KEY CLUSTERED ([MaPNCT]),
);
GO
/****** Object:  Table [dbo].[KhoNhap] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DanhMucSach](
	[MaDM] [nvarchar](10) NOT NULL,
	[TenDM] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_DanhMucSach] PRIMARY KEY CLUSTERED 
(
	[MaDM] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

ALTER TABLE [dbo].[HoaDon]
ADD CONSTRAINT [FK_HoaDon_GioHang] 
FOREIGN KEY ([MaGH]) REFERENCES [dbo].[GioHang]([MaGH]);

ALTER TABLE [dbo].[HoaDon]
ADD CONSTRAINT [FK_HoaDon_KhachHang] 
FOREIGN KEY ([MaKH]) REFERENCES [dbo].[KhachHang]([MaKH]);

ALTER TABLE [dbo].[HoaDon]
ADD CONSTRAINT [FK_HoaDon_NhanVien] 
FOREIGN KEY ([MaNV]) REFERENCES [dbo].[NhanVien]([MaNV]);

ALTER TABLE [dbo].[HoaDon]
ADD CONSTRAINT [FK_HoaDon_KhuyenMai] 
FOREIGN KEY ([MaKM]) REFERENCES [dbo].[KhuyenMai]([MaKM]);

ALTER TABLE [dbo].[PhieuNhapChiTiet]
ADD CONSTRAINT [FK_PhieuNhapChiTiet_SanPham] 
FOREIGN KEY ([MaS]) REFERENCES [dbo].[Sach]([MaS]);

ALTER TABLE [dbo].[Sach]
ADD CONSTRAINT [FK_Sach_TheLoai] 
FOREIGN KEY ([MaTL]) REFERENCES [dbo].[TheLoai]([MaTL]);

ALTER TABLE [dbo].[Sach]
ADD CONSTRAINT [FK_Sach_DanhMucSach] 
FOREIGN KEY ([MaDM]) REFERENCES [dbo].[DanhMucSach]([MaDM]);

ALTER TABLE [dbo].[PhieuNhap]
ADD CONSTRAINT [FK_PhieuNhap_NhanVien] 
FOREIGN KEY ([MaNV]) REFERENCES [dbo].[NhanVien]([MaNV]);

ALTER TABLE [dbo].[PhieuNhap]
ADD CONSTRAINT [FK_PhieuNhap_KhoNhap] 
FOREIGN KEY ([MaKN]) REFERENCES [dbo].[KhoNhap]([MaKN]);

ALTER TABLE [dbo].[PhieuNhap]
ADD CONSTRAINT [FK_PhieuNhap_PhieuNhapChiTiet] 
FOREIGN KEY ([MaPNCT]) REFERENCES [dbo].[PhieuNhapChiTiet]([MaPNCT]);

SET IDENTITY_INSERT [KhachHang] ON;


	INSERT INTO [dbo].[KhachHang] ([MaKH], [TenKH], [SDT], [Email], [DiaChi], [NgaySinh], [VaiTro], [TenTaiKhoan], [MatKhau])
	VALUES 
    (1, N'Nguyễn Văn A', N'0123456789', N'nguyenvana@email.com', N'123 Đường ABC, Quận 1, TP.HCM', N'1990-05-15', N'Khách hàng', N'Hoangle1', N'pass123'),
    (2, N'Trần Thị B', N'0987654321', N'tranthib@email.com', N'456 Đường XYZ, Quận 2, TP.HCM', N'1985-08-20', N'Khách hàng', N'tranthib', N'pass123'),
    (3, N'Lê Minh C', N'0369842571', N'leminhc@email.com', N'789 Đường KLM, Quận 3, TP.HCM', N'1995-12-10', N'Khách hàng', N'leminhc', N'pass123'),
	(4, N'Trần Cẩm Vân', N'0987658732', N'vantran1@email.com', N'123 Đường Trần Hưng Đạo, Quận 1, TP.HCM', N'1995-04-12', N'Khách hàng', N'vantran1', N'pass123'),
	(5, N'Trần Minh Công', N'0987658983', N'tranminhcong2@email.com', N'456 Đường Nguyễn Văn Linh, Quận 7, TP.HCM', N'1988-09-25', N'Khách hàng', N'tranminhcong2', N'pass123'),
	(6, N'Nguyễn Thị Hồng', N'0987658908', N'hongthinguyen1@email.com', N'789 Đường Lê Lai, Quận 3, TP.HCM', N'1992-11-08', N'Khách hàng', N'hongthinguyen1', N'pass123'),
	(7, N'Trần Quang Phúc', N'0987656785', N'phucpro@email.com', N'101 Đường Bùi Thị Xuân, Quận Phú Nhuận, TP.HCM', N'1989-02-17', N'Khách hàng', N'phucpro', N'pass123'),
	(8, N'Lê Hồng Bàng', N'0987655632', N'bangdz@email.com', N'234 Đường Cách Mạng Tháng Tám, Quận 10, TP.HCM', N'1993-06-29', N'Khách hàng', N'bangdz', N'pass123'),
	(9, N'Trần Văn Tuấn', N'0987651234', N'tuancaothu@email.com', N'567 Đường Hoàng Văn Thụ, Quận Tân Bình, TP.HCM', N'1987-10-04', N'Khách hàng', N'tuancaothu', N'pass123'),
	(10, N'Trần Thị Bánh', N'0987656784', N'banhngon@email.com', N'111 Đường Hòa Bình, Quận Bình Thạnh, TP.HCM', N'1991-03-18', N'Khách hàng', N'banhngon', N'pass123');

SET IDENTITY_INSERT [KhachHang] OFF;
	INSERT INTO [dbo].[NhanVien] ([MaNV], [TenNV], [TenTaiKhoan] ,[MatKhau], [SDT], [Email], [DiaChi], [VaiTro], [NgaySinh])
	VALUES 
    (N'NV001', N'Nguyễn Văn A',N'nva', N'pass123', N'0123456789', N'nva@email.com', N'123 Đường ABC, Quận 1, TP.HCM', N'Quản trị viên', N'1990-05-15'),
    (N'NV002', N'Trần Thị B', N'ttb' , N'pass456', N'0987654321', N'ttb@email.com', N'456 Đường XYZ, Quận 2, TP.HCM', N'Nhân viên', N'1985-08-20'),
    (N'NV003', N'Lê Minh C',N'lmc' ,N'pass789', N'0369842571', N'lmc@email.com', N'789 Đường KLM, Quận 3, TP.HCM', N'Nhân viên', N'1995-12-10'),
	(N'NV004', N'Trần Văn Cấn',N'tvc52' ,N'pass345', N'0987655632', N'tvc52@email.com', N'56 Đường Lê Lợi, Quận Hoàn Kiếm, Hà Nội', N'Nhân viên', N'1999-05-23'),
	(N'NV005', N'Trần Cần Giuộc',N'tcg123' ,N'admin1', N'0987658732', N'tcg123@email.com', N'89 Đường Hồ Tùng Mậu, Quận Cầu Giấy, Hà Nội', N'Quản trị viên', N'1990-11-15'),
	(N'NV006', N'Nguyễn Văn Khang',N'nvk242' ,N'password1', N'0369842571', N'nvk242@email.com', N'23 Đường Nguyễn Trãi, Quận Thanh Xuân, Hà Nội', N'Nhân viên', N'1988-08-04'),
	(N'NV007', N'Lê Văn Bính',N'lvb5123' ,N'khongbiet1', N'0987656784', N'lvb5123@email.com', N'45 Đường Phạm Hùng, Quận Nam Từ Liêm, Hà Nội', N'Nhân viên', N'1994-02-19'),
	(N'NV008', N'Trần Anh Hào',N'tah5124' ,N'bmo123', N'0987658908', N'tah5124@email.com', N'78 Đường Đê La Thành, Quận Ba Đình, Hà Nội', N'Nhân viên', N'1996-07-11'),
	(N'NV009', N'Nguyễn Song Long',N'nsl452' ,N'songlong1', N'0987651234', N'nsl452@email.com', N'34 Đường Lạc Long Quân, Quận Tây Hồ, Hà Nội', N'Nhân viên', N'1989-09-28'),
	(N'NV010', N'Nguyễn Hải An',N'nha635' ,N'jianng1', N'0987655632', N'nha635@email.com', N'67 Đường Hoàng Quốc Việt, Quận Cầu Giấy, Hà Nội', N'Nhân viên', N'1997-04-06');

	INSERT INTO [dbo].[DanhMucSach] ([MaDM], [TenDM])
	VALUES 
    ('DM001', N'Khoa Học'),
    ('DM002', N'Văn Học'),
    ('DM003', N'Lịch Sử'),
    ('DM004', N'Toán Học'),
    ('DM005', N'Nghệ Thuật');

	INSERT INTO [dbo].[TheLoai] ([MaTL], [TenTL])
	VALUES 
    ('TL001', N'Tiểu Thuyết'),
    ('TL002', N'Kinh Tế'),
    ('TL003', N'Tâm Lý Học'),
    ('TL004', N'Huyền Bí'),
    ('TL005', N'Thiếu Nhi');

	INSERT INTO [dbo].[KhuyenMai] ([MaKM], [TenKM], [LoaiKhuyenMai], [GiaTriKhuyenMai], [DieuKienApDung], [HieuLuc], [TrangThai])
	VALUES 
    ('KM001', N'Giảm Giá Mùa Hè', N'Giảm giá', N'15%', N'Đơn hàng trên 500,000 VND', N'2023-06-15', N'Hết hiệu lực'),
    ('KM002', N'Mua 1 Tặng 1', N'Tặng phẩm', N'1 sản phẩm', N'Áp dụng cho sản phẩm A', N'2023-07-02', N'Hết hiệu lực'),
    ('KM003', N'Flash Sale', N'Giảm giá', N'20%', N'Chỉ trong 24 giờ', N'2023-08-10',N'Hết hiệu lực'),
    ('KM004', N'Thẻ Giảm Giá', N'Giảm giá', N'10%', N'Sử dụng thẻ thành viên', N'2023-09-05',N'Hết hiệu lực'),
    ('KM005', N'Combo Khuyến Mãi', N'Tặng phẩm', N'1 sản phẩm', N'Mua combo A + B', N'2023-10-20',N'Hết hiệu lực');

	INSERT INTO [dbo].[Sach] ([MaS], [TenS], [TacGia], [DonGia], [NhaXuatBan], [SoLuong], [MaDM], [MaTL])
	VALUES 
    ('S001', N'Những Ngày Thứ Ba', N'Nguyễn Nhật Ánh', 150000, N'Kim Đồng', 100, 'DM001', 'TL001'),
    ('S002', N'Số Đỏ', N'Nguyễn Hồng', 120000, N'NXB Trẻ', 100, 'DM002', 'TL001'),
    ('S003', N'Dế Mèn Phiêu Lưu Ký', N'Tô Hoài', 95000, N'Nhã Nam', 100, 'DM003', 'TL005'),
    ('S004', N'Harry Potter và Hòn Đá Phù Thủy', N'J.K. Rowling', 200000, N'NXB Trẻ', 100, 'DM002', 'TL001'),
    ('S005', N'Bí Mật Của Lòng Đất', N'Tô Hoài', 80000, N'Kim Đồng', 100, 'DM003', 'TL002'),
    ('S006', N'Những Kẻ Mộng Mơ', N'Haruki Murakami', 180000, N'NXB Văn Học', 100, 'DM004', 'TL002'),
    ('S007', N'Doraemon', N'Fujiko F. Fujio', 100000, N'NXB Trẻ', 100, 'DM005', 'TL005'),
    ('S008', N'Cây Cam Ngọt của Tôi', N'Nguyễn Nhật Ánh', 130000, N'Kim Đồng', 100, 'DM001', 'TL001'),
    ('S009', N'Lão Hạc', N'Nam Cao', 85000, N'NXB Văn Học', 100, 'DM004', 'TL003'),
    ('S010', N'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', N'Nguyễn Nhật Ánh', 160000, N'NXB Trẻ', 100, 'DM002', 'TL001');

	INSERT INTO [dbo].[GioHang] ([MaGH],[GhiChu])
	VALUES 
    ('GH001', N'Sách giảm giá 10%'),
    ('GH002', N'Mua 3 sách, giảm giá 5%'),
    ('GH003', N'Sách mới phát hành'),
    ('GH004', N'Combo sách giảm giá 20%'),
    ('GH005', N'Sách khuyến mãi, mua 1 tặng 1'),
    ('GH006', N'Sách nổi tiếng của Haruki Murakami'),
    ('GH007', N'Doraemon cho trẻ em'),
    ('GH008', N'Sách giảm giá 10%'),
    ('GH009', N'Sách cổ điển của Nam Cao'),
    ('GH010', N'Combo sách giảm giá 15%');

	INSERT INTO [dbo].[HoaDon] ([MaHD], [PhuongThucTT], [TongThanhTien], [NgayTao], [TrangThai], [MaKH], [MaNV], [MaKM], [MaGH])
	VALUES 
    ('HD001', N'Thanh toán khi nhận hàng', 300000, N'2023-03-01',N'Đang Giao' ,1, 'NV001', 'KM001', 'GH001'),
    ('HD002', N'Chuyển khoản', 450000, N'2023-03-02',N'Đang Giao' , 2, 'NV002', 'KM002', 'GH002'),
    ('HD003', N'Thanh toán khi nhận hàng', 600000, N'2023-03-03', N'Đang Giao', 3, 'NV003', 'KM003', 'GH003'),
    ('HD004', N'Chuyển khoản', 250000, N'2023-03-04', N'Đang Giao',4, 'NV001', 'KM004', 'GH004'),
    ('HD005', N'Thanh toán khi nhận hàng', 800000, N'2023-03-05', N'Đang Giao',5, 'NV002', 'KM005', 'GH005'),
    ('HD006', N'Chuyển khoản', 350000, N'2023-03-06', N'Đang Giao',1, 'NV003', 'KM001', 'GH006'),
    ('HD007', N'Thanh toán khi nhận hàng', 700000, N'2023-03-07', N'Đang Giao',2, 'NV001', 'KM002', 'GH007'),
    ('HD008', N'Chuyển khoản', 550000, N'2023-03-08', N'Đang Giao',3, 'NV002', 'KM003', 'GH008'),
    ('HD009', N'Thanh toán khi nhận hàng', 400000, N'2023-03-09', N'Hoàn thành',4, 'NV003', 'KM004', 'GH009'),
    ('HD010', N'Chuyển khoản', 900000, N'2023-03-10', N'Hoàn thành',5, 'NV001', 'KM005', 'GH010');

	INSERT INTO [dbo].[KhoNhap] ([MaKN], [TenKN], [SDT], [DiaChi], [MaSoThue])
	VALUES 
    ('KN001', N'Kho Hàng A', '0123456789', N'23 Đường Hoàng Văn Thụ, Phường 1, Đà Lạt', 'MS12345'),
    ('KN002', N'Kho Hàng B', '0987654321', N'56 Đường Lê Duẩn, Phường Trung Dũng, Biên Hòa, Đồng Nai', 'MS56789'),
    ('KN003', N'Kho Hàng C', '0369842571', N'67 Đường Nguyễn Ái Quốc, Phường Bửu Long, Biên Hòa, Đồng Nai', 'MS98765'),
    ('KN004', N'Kho Hàng D', '0543216789', N'87 Đường Nguyễn Du, Phường 5, Đà Lạt', 'MS54321'),
    ('KN005', N'Kho Hàng E', '0765432109', N'34 Đường Hải Thượng Lãn Ông, Phường 9, Đà Lạt', 'MS67890');

	INSERT INTO [dbo].[PhieuNhapChiTiet] ([MaPNCT], [MaS], [SoLuong], [DonGia], [ThanhTien], [GhiChu])
	VALUES 
    ('PNCT011', 'S001', 5, 150000, 750000, N'Sách giảm giá 10%'),
    ('PNCT012', 'S002', 3, 120000, 360000, N'Mua 3 sách, giảm giá 5%'),
    ('PNCT013', 'S003', 2, 95000, 190000, N'Sách mới phát hành'),
    ('PNCT014', 'S004', 4, 200000, 800000, N'Combo sách giảm giá 20%'),
    ('PNCT015', 'S005', 1, 80000, 80000, N'Sách khuyến mãi, mua 1 tặng 1'),
    ('PNCT016', 'S006', 2, 180000, 360000, N'Sách nổi tiếng của Haruki Murakami'),
    ('PNCT017', 'S007', 3, 100000, 300000, N'Doraemon cho trẻ em'),
    ('PNCT018', 'S008', 2, 130000, 260000, N'Sách giảm giá 10%'),
    ('PNCT019', 'S009', 1, 85000, 85000, N'Sách cổ điển của Nam Cao'),
    ('PNCT020', 'S010', 5, 160000, 800000, N'Combo sách giảm giá 15%');

	INSERT INTO [dbo].[PhieuNhap] ([MaPN], [NgayNhap], [MaNV], [MaKN], [MaPNCT])
	VALUES 
    ('PN001', '2023-03-01', 'NV001', 'KN001', 'PNCT011'),
    ('PN002', '2023-03-02', 'NV002', 'KN002', 'PNCT012'),
    ('PN003', '2023-03-03', 'NV003', 'KN003', 'PNCT013'),
    ('PN004', '2023-03-04', 'NV001', 'KN004', 'PNCT014'),
    ('PN005', '2023-03-05', 'NV002', 'KN005', 'PNCT015'),
    ('PN006', '2023-03-06', 'NV003', 'KN001', 'PNCT016'),
    ('PN007', '2023-03-07', 'NV001', 'KN002', 'PNCT017'),
    ('PN008', '2023-03-08', 'NV002', 'KN003', 'PNCT018'),
    ('PN009', '2023-03-09', 'NV003', 'KN004', 'PNCT019'),
    ('PN010', '2023-03-10', 'NV001', 'KN005', 'PNCT020');

	INSERT INTO ChiTietGioHang (MaGH, MaS, SoLuong)
	VALUES 
	('GH001', 'S001', 2),  
    ('GH001', 'S002', 1),
	('GH001', 'S003', 1),
	('GH001', 'S004', 1),
	('GH001', 'S005', 1),
	('GH002', 'S001', 2),  
    ('GH002', 'S002', 1),
	('GH002', 'S003', 1),
	('GH002', 'S005', 1),
	('GH003', 'S001', 2),  
	('GH003', 'S003', 1),
	('GH003', 'S004', 1),
	('GH004', 'S005', 1),
	('GH004', 'S004', 1),
	('GH004', 'S003', 1),
	('GH004', 'S001', 2),
	('GH005', 'S001', 1),
	('GH005', 'S002', 1),
	('GH005', 'S007', 1),
	('GH005', 'S006', 1),
	('GH005', 'S005', 1),
	('GH005', 'S009', 1),
	('GH006', 'S010', 1),
	('GH006', 'S001', 1),
	('GH006', 'S002', 1),
	('GH007', 'S001', 1),
	('GH007', 'S002', 1),
	('GH008', 'S010', 1),
	('GH008', 'S009', 1),
	('GH008', 'S008', 1),
	('GH009', 'S005', 1),
	('GH009', 'S004', 1),
	('GH009', 'S003', 1),
	('GH009', 'S002', 1),
	('GH010', 'S010', 1),
	('GH010', 'S007', 1),
	('GH010', 'S005', 1),
	('GH010', 'S004', 1);




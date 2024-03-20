/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doituong;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Dell
 */
public class hoadon {
    private String MaHD;
    private String PhuongThucTT;
    private String TongThanhTien;
    private Date NgayTao;
    private String TrangThai;
    private String TenKh;
    private String MaKH;
    private String MaNV;
    private String MaKM;
    private String MaGH;

    public hoadon(String MaHD, Date NgayTao, String TrangThai, String TenKh, String MaGH) {
        this.MaHD = MaHD;
        this.NgayTao = NgayTao;
        this.TrangThai = TrangThai;
        this.TenKh = TenKh;
        this.MaGH = MaGH;
    }
    public static Date parseNgayTao(String ngaySinhString) {
        try {
            // Chuyển đổi chuỗi ngày từ dd-MM-yyyy thành Date
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            return inputFormat.parse(ngaySinhString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        }
    }
    public String formatNgayTao() {
        try {
            // Chuyển đổi ngày từ Date thành dd-MM-yyyy
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            return outputFormat.format(NgayTao);
        } catch (Exception e) {
            e.printStackTrace();
            return ""; // Trả về chuỗi rỗng nếu có lỗi
        }
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getPhuongThucTT() {
        return PhuongThucTT;
    }

    public void setPhuongThucTT(String PhuongThucTT) {
        this.PhuongThucTT = PhuongThucTT;
    }

    public String getTongThanhTien() {
        return TongThanhTien;
    }

    public void setTongThanhTien(String TongThanhTien) {
        this.TongThanhTien = TongThanhTien;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getTenKh() {
        return TenKh;
    }

    public void setTenKh(String TenKh) {
        this.TenKh = TenKh;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getMaKM() {
        return MaKM;
    }

    public void setMaKM(String MaKM) {
        this.MaKM = MaKM;
    }

    public String getMaGH() {
        return MaGH;
    }

    public void setMaGH(String MaGH) {
        this.MaGH = MaGH;
    }
    
}


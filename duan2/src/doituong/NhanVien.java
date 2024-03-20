package doituong;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NhanVien {
    private String maNV;
    private String tenNV;
    private String sdt;
    private Date ngaySinh;

    public NhanVien(String maNV, String tenNV, String sdt, Date ngaySinh) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.sdt = sdt;
        this.ngaySinh = ngaySinh;
    }

    // Getter và setter cho các thuộc tính

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

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String formatNgaySinh() {
        try {
            // Chuyển đổi ngày từ Date thành dd-MM-yyyy
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            return outputFormat.format(ngaySinh);
        } catch (Exception e) {
            e.printStackTrace();
            return ""; // Trả về chuỗi rỗng nếu có lỗi
        }
    }

    public static Date parseNgaySinh(String ngaySinhString) {
        try {
            // Chuyển đổi chuỗi ngày từ dd-MM-yyyy thành Date
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            return inputFormat.parse(ngaySinhString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        }
    }
}

package doituong;

/**
 *
 * @author Dell
 */
import java.util.Date;

public class KhachHang {
    private String tenKH;
    private Date ngaySinh;
    private String sdt;
    private String email;

    public KhachHang(String tenKH, Date ngaySinh, String sdt, String email) {
        this.tenKH = tenKH;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.email = email;
    }

    // Getter và setter cho các thuộc tính

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

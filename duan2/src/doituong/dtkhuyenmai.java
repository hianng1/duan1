package doituong;

import java.util.Date;

public class dtkhuyenmai {
    private String maKM;
    private String tenKM;
    private String loaiKM;
    private String giaTriKM;
    private String DieuKienApDung;
    private Date HieuLuc;
    private String TrangThai;

    public dtkhuyenmai(String maKM, String tenKM, String loaiKM, String giaTriKM, String DieuKienApDung, Date HieuLuc, String TrangThai) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.loaiKM = loaiKM;
        this.giaTriKM = giaTriKM;
        this.DieuKienApDung = DieuKienApDung;
        this.HieuLuc = HieuLuc;
        this.TrangThai = TrangThai;
    }

    public String getDieuKienApDung() {
        return DieuKienApDung;
    }

    public void setDieuKienApDung(String DieuKienApDung) {
        this.DieuKienApDung = DieuKienApDung;
    }

    

    public dtkhuyenmai() {
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public String getLoaiKM() {
        return loaiKM;
    }

    public void setLoaiKM(String loaiKM) {
        this.loaiKM = loaiKM;
    }

    public String getGiaTriKM() {
        return giaTriKM;
    }

    public void setGiaTriKM(String giaTriKM) {
        this.giaTriKM = giaTriKM;
    }

    public Date getHieuLuc() {
        return HieuLuc;
    }

    public void setHieuLuc(Date HieuLuc) {
        this.HieuLuc = HieuLuc;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    
}

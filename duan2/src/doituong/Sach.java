package doituong;

public class Sach {
    private String maSach;
    private String tenSach;
    private String theLoai;
    private float donGia;

    public Sach(String maSach, String tenSach, String theLoai, float donGia) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.donGia = donGia;
    }

    // Getter và setter cho các thuộc tính

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }
}

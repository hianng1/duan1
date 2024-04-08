/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Khachhang;

import Khachhang.Bangnhanvien;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tandm
 */
public class ThongKeDoanhThu extends javax.swing.JFrame {

    public static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=duan1;encrypt=true;trustServerCertificate=true;";
    public static String user = "sa";
    public static String password = "123";
    public static float doanhthungay;
    public static float doanhthuthang;
    public static float doanhthunam;
    public static int tongdonhang = 0;

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dburl, user, password);
    }

    /**
     * Creates new form ThongKeDoanhThu
     */
    public ThongKeDoanhThu() {
        initComponents();
        setLocationRelativeTo(null);
        updateThongkeFromDatabase(3);
        countHoaDon();

        String chuoiTongDonHang = String.valueOf(tongdonhang);
        jlb1.setText(chuoiTongDonHang);
        sumTotalToday();
        String doanhThuNgayString = String.valueOf(doanhthungay);

        // Đặt giá trị vào JLabel jlb2
        jlb2.setText(doanhThuNgayString);
        sumTotalThisMonth();
        String doanhthuthangString = String.valueOf(doanhthuthang);

        // Đặt giá trị vào JLabel jlb2
        jlb3.setText(doanhthuthangString);
        sumTotalThisYear();
        String doanhthunamString = String.valueOf(doanhthunam);

        // Đặt giá trị vào JLabel jlb2
        jlb4.setText(doanhthunamString);
    }

    public static float sumTotalThisYear() {

        // Lấy ngày đầu tiên và ngày cuối cùng của năm hiện tại
        LocalDate firstDayOfYear = LocalDate.now().withDayOfYear(1);
        LocalDate lastDayOfYear = LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear());

        // Tạo một chuỗi SQL để chọn các hóa đơn trong năm hiện tại
        String query = "SELECT SUM(TongThanhTien) AS total FROM HoaDon WHERE NgayTao BETWEEN ? AND ?";

        try (Connection connection = DriverManager.getConnection(dburl, user, password); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Thiết lập tham số cho câu truy vấn
            preparedStatement.setDate(1, Date.valueOf(firstDayOfYear));
            preparedStatement.setDate(2, Date.valueOf(lastDayOfYear));

            // Thực hiện truy vấn
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    doanhthunam = resultSet.getFloat("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doanhthunam;
    }

    public static float sumTotalThisMonth() {

        // Lấy ngày đầu tiên và ngày cuối cùng của tháng hiện tại
        YearMonth yearMonth = YearMonth.now();
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        // Tạo một chuỗi SQL để chọn các hóa đơn trong tháng hiện tại
        String query = "SELECT SUM(TongThanhTien) AS total FROM HoaDon WHERE NgayTao BETWEEN ? AND ?";

        try (Connection connection = DriverManager.getConnection(dburl, user, password); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Thiết lập tham số cho câu truy vấn
            preparedStatement.setDate(1, Date.valueOf(firstDayOfMonth));
            preparedStatement.setDate(2, Date.valueOf(lastDayOfMonth));

            // Thực hiện truy vấn
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    doanhthuthang = resultSet.getFloat("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doanhthuthang;
    }

    public static int countHoaDon() {

        String query = "SELECT COUNT(DISTINCT NgayTao) AS total FROM HoaDon";

        try (Connection connection = DriverManager.getConnection(dburl, user, password); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                tongdonhang = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongdonhang;
    }

    public static float sumTotalToday() {

        // Lấy ngày hiện tại
        LocalDate today = LocalDate.now();

        // Tạo một chuỗi SQL để chọn các hóa đơn trong ngày hiện tại
        String query = "SELECT SUM(TongThanhTien) AS total FROM HoaDon WHERE NgayTao = ?";

        try (Connection connection = DriverManager.getConnection(dburl, user, password); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Thiết lập tham số cho câu truy vấn
            preparedStatement.setDate(1, Date.valueOf(today));

            // Thực hiện truy vấn
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    doanhthungay = resultSet.getFloat("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doanhthungay;
    }

//    private void updateThongkeFromDatabase(int thangCuThe) {
//        try (Connection connection = getConnection()) {
//            String query = "SELECT CONCAT(YEAR(HD.NgayTao), '/' ,MONTH(HD.NgayTao), '/', DAY(HD.NgayTao)) AS ThangNgay, "
//                    + "CTGH.SoLuong, S.DonGia, CTGH.SoLuong * S.DonGia AS ThanhTien "
//                    + "FROM HoaDon HD "
//                    + "INNER JOIN GioHang GH ON HD.MaGH = GH.MaGH "
//                    + "INNER JOIN ChiTietGioHang CTGH ON GH.MaGH = CTGH.MaGH "
//                    + "INNER JOIN Sach S ON CTGH.MaS = S.MaS "
//                    + "WHERE MONTH(HD.NgayTao) = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setInt(1, thangCuThe);
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    DefaultTableModel model = (DefaultTableModel) tblthongke.getModel();
//                    model.setRowCount(0); // Xóa tất cả các dòng hiện tại trong bảng
//
//                    while (resultSet.next()) {
//                        // Lấy dữ liệu từ ResultSet
//                        String thangNgay = resultSet.getString("ThangNgay");
//                        int soLuong = resultSet.getInt("SoLuong");
//                        float donGia = resultSet.getFloat("DonGia");
//                        float thanhTien = resultSet.getFloat("ThanhTien");
//
//                        // Thêm dòng mới từ dữ liệu vào bảng
//                        model.addRow(new Object[]{
//                            thangNgay,
//                            soLuong,
//                            donGia,
//                            thanhTien
//                        });
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    private void updateThongkeFromDatabase(int thangCuThe) {
        String query2 = "SELECT GH.MaGH, SUM(CTGH.SoLuong) AS TongSoLuong "
                + "FROM GioHang GH "
                + "INNER JOIN ChiTietGioHang CTGH ON GH.MaGH = CTGH.MaGH "
                + "GROUP BY GH.MaGH";

        try (Connection connection = getConnection()) {
            // Thực hiện query 2
            try (PreparedStatement preparedStatement = connection.prepareStatement(query2)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Lưu kết quả của query 2 vào một HashMap để sử dụng sau này
                    Map<String, Integer> tongSoLuongMap = new HashMap<>();
                    while (resultSet.next()) {
                        String maGH = resultSet.getString("MaGH");
                        int tongSoLuong = resultSet.getInt("TongSoLuong");
                        tongSoLuongMap.put(maGH, tongSoLuong);
                    }

                    // Query 1: Lấy thông tin từ HoaDon và tính toán tổng số lượng và thành tiền
                    String query1 = "SELECT CONCAT(YEAR(HD.NgayTao), '/', MONTH(HD.NgayTao), '/', DAY(HD.NgayTao)) AS ThangNgay, "
                            + "GH.MaGH, CTGH.SoLuong, CTGH.SoLuong * S.DonGia AS ThanhTien "
                            + "FROM HoaDon HD "
                            + "INNER JOIN GioHang GH ON HD.MaGH = GH.MaGH "
                            + "INNER JOIN ChiTietGioHang CTGH ON GH.MaGH = CTGH.MaGH "
                            + "INNER JOIN Sach S ON CTGH.MaS = S.MaS "
                            + "WHERE MONTH(HD.NgayTao) = ?";
                    try (PreparedStatement preparedStatement2 = connection.prepareStatement(query1)) {
                        preparedStatement2.setInt(1, thangCuThe);
                        try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
                            DefaultTableModel model = (DefaultTableModel) tblthongke.getModel();
                            model.setRowCount(0); // Xóa tất cả các dòng hiện tại trong bảng

                            String lastMaGH = null; // Lưu trữ mã giỏ hàng của dòng trước đó
                            int tongSoLuong = 0; // Biến để tính tổng số lượng của mỗi mã giỏ hàng
                            float tongGiaBan = 0; // Biến để tính tổng giá bán của mỗi mã giỏ hàng

                            // Kiểm tra xem ResultSet có dữ liệu không trước khi truy cập
                            if (!resultSet2.isBeforeFirst()) {
                                System.out.println("ResultSet is empty.");
                                return; // Hoặc thực hiện hành động phù hợp với trường hợp ResultSet trống
                            }

                            while (resultSet2.next()) {
                                String maGH = resultSet2.getString("MaGH");
                                int soLuong = resultSet2.getInt("SoLuong");
                                float thanhTien = resultSet2.getFloat("ThanhTien");

                                if (!maGH.equals(lastMaGH)) {
                                    // Nếu gặp mã giỏ hàng mới, thêm dòng vào bảng cho mã giỏ hàng trước đó và reset lại tổng số lượng và tổng giá bán
                                    if (lastMaGH != null) {
                                        model.addRow(new Object[]{
                                            resultSet2.getString("ThangNgay"),
                                            lastMaGH,
                                            tongSoLuong,
                                            tongGiaBan // Thêm cột cho tổng giá bán
                                        });
                                    }
                                    tongSoLuong = tongSoLuongMap.getOrDefault(maGH, 0);
                                    tongGiaBan = 0;
                                    lastMaGH = maGH;
                                }

                                // Cộng dồn số lượng vào tổng số lượng và thành tiền vào tổng giá bán
                                tongSoLuong += soLuong;
                                tongGiaBan += thanhTien;
                            }

                            // Thêm dòng cho dòng cuối cùng
                            if (lastMaGH != null) {
                                model.addRow(new Object[]{
                                    resultSet2.getString("ThangNgay"),
                                    lastMaGH,
                                    tongSoLuong,
                                    tongGiaBan // Thêm cột cho tổng giá bán
                                });
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jlb2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jlb3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jlb4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jlb1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblthongke = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(153, 255, 153));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setText("Doanh Thu Hôm Nay");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Solid.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("VND");

        jlb2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlb2.setText("jLabel23");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel3)
                .addContainerGap(63, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jlb2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlb2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 153));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setText("Doanh Thu Tháng");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Solid.png"))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("VND");

        jlb3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlb3.setText("jLabel24");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel4)
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jlb3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(2, 2, 2)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlb3))
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 102, 102));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setText("Doanh Thu Năm");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Solid.png"))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("VND");

        jlb4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlb4.setText("jLabel25");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                                .addComponent(jlb4)
                                .addGap(51, 51, 51)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlb4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setText("Tổng Đơn Hàng");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Note.png"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Đơn Hàng");

        jlb1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlb1.setText("jLabel21");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel2)
                .addContainerGap(69, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlb1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel13)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlb1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(40, 40, 40))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Mốc Thời Gian :");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12", " ", " ", " " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        tblthongke.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblthongke.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Ngày Tháng", "Mã Giỏ Hàng", "Số Lượng Bán", "Tổng Giá Bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblthongke);
        if (tblthongke.getColumnModel().getColumnCount() > 0) {
            tblthongke.getColumnModel().getColumn(0).setResizable(false);
            tblthongke.getColumnModel().getColumn(1).setResizable(false);
            tblthongke.getColumnModel().getColumn(2).setResizable(false);
            tblthongke.getColumnModel().getColumn(3).setResizable(false);
        }

        jSeparator2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jSeparator2)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(159, 159, 159)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(219, 6, 961, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Cart.png"))); // NOI18N
        jLabel27.setText("Tên Shop");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 6, 202, 60));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Solid.png"))); // NOI18N
        jLabel10.setText("Thống Kê");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Box.png"))); // NOI18N
        jLabel20.setText("Sản Phẩm");
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Note.png"))); // NOI18N
        jLabel22.setText("Hóa Đơn");
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Team.png"))); // NOI18N
        jLabel28.setText("Khách Hàng");
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/History.png"))); // NOI18N
        jLabel29.setText("Lịch Sử");
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, -1, -1));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Hot.png"))); // NOI18N
        jLabel30.setText("Khuyến Mãi");
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/User.png"))); // NOI18N
        jLabel12.setText("Nhân Viên");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, -1, -1));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Repost.png"))); // NOI18N
        jLabel31.setText("Đổi Mật Khẩu");
        jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel31MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, -1, -1));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/LogOut.png"))); // NOI18N
        jLabel32.setText("Đăng Xuất");
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 560, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
        new ThongKeDoanhThu().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new SanPham().setVisible(true);
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new HoaDon_QuanLi().setVisible(true);
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new bangKhachHang().setVisible(true);
    }//GEN-LAST:event_jLabel28MouseClicked

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new LichSu().setVisible(true);
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new KhuyenMai().setVisible(true);
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new Bangnhanvien().setVisible(true);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new DoiMatKhau().setVisible(true);
    }//GEN-LAST:event_jLabel31MouseClicked

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new DangNhap().setVisible(true);
    }//GEN-LAST:event_jLabel32MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ThongKeDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKeDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKeDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKeDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThongKeDoanhThu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel jlb1;
    private javax.swing.JLabel jlb2;
    private javax.swing.JLabel jlb3;
    private javax.swing.JLabel jlb4;
    private javax.swing.JTable tblthongke;
    // End of variables declaration//GEN-END:variables

//public javax.swing.JButton getBtnnhanvien() {
//        return btnnhanvien;
//    }
//public javax.swing.JButton getBtnkhachhang() {
//        return btnkhachhang;
//    }
//public javax.swing.JButton getBtnthongke() {
//        return jButton5;
//    }
}

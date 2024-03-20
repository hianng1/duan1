/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Khachhang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import doituong.dtkhuyenmai;
import java.sql.CallableStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JOptionPane;
/**
 *
 * @author tandm
 */
public class KhuyenMai extends javax.swing.JFrame {
     public static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=duan1;encrypt=true;trustServerCertificate=true;";
    public static String user = "sa";
    public static String password = "123";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dburl, user, password);
    }
    /**
     * Creates new form ThongKeDoanhThu
     */
    public KhuyenMai() {
        initComponents();
        setLocationRelativeTo(null);
        updatekhuyenmaiFromUsersTable();
    }
    private void updatekhuyenmaiFromUsersTable() {
    try (Connection connection = getConnection()) {
        String query = "SELECT MaKM, TenKM, LoaiKhuyenMai, GiaTriKhuyenMai, DieuKienApDung, HieuLuc, TrangThai FROM KhuyenMai";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) tblkhuyenmai.getModel();
                model.setRowCount(0); // Xóa tất cả các dòng hiện tại trong bảng

                while (resultSet.next()) {
                    // Lấy dữ liệu từ ResultSet
                    String maKM = resultSet.getString("MaKM");
                    String tenKM = resultSet.getString("TenKM");
                    String loaiKM = resultSet.getString("LoaiKhuyenMai");
                    String giaTriKM = resultSet.getString("GiaTriKhuyenMai");
                    String DieuKienApDung = resultSet.getString("DieuKienApDung");
                    Date HieuLuc = resultSet.getDate("HieuLuc");
                    String TrangThai = resultSet.getString("TrangThai");
                    // Tạo đối tượng KhuyenMai từ dữ liệu
                    dtkhuyenmai khuyenMai = new dtkhuyenmai(maKM, tenKM, loaiKM, giaTriKM, DieuKienApDung, HieuLuc, TrangThai);

                    // Thêm dòng mới từ đối tượng KhuyenMai
                    model.addRow(new Object[]{
                            khuyenMai.getMaKM(),
                            khuyenMai.getTenKM(),
                            khuyenMai.getLoaiKM(),
                            khuyenMai.getGiaTriKM(),
                            khuyenMai.getDieuKienApDung(),
                            khuyenMai.getHieuLuc(),
                            khuyenMai.getTrangThai()
                    });
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
     private void retrievedata(String Makm) {
        try (Connection conn = getConnection()){    
            String query = "SELECT MaKM, TenKM, LoaiKhuyenMai, GiaTriKhuyenMai, DieuKienApDung, HieuLuc, TrangThai FROM KhuyenMai where MaKM = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, Makm);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String maKM = result.getString("MaKM");
                    String tenKM = result.getString("TenKM");
                    String loaiKM = result.getString("LoaiKhuyenMai");
                    String giaTriKM = result.getString("GiaTriKhuyenMai");
                    String DieuKienApDung = result.getString("DieuKienApDung");
                    Date HieuLuc = result.getDate("HieuLuc");
                    String TrangThai = result.getString("TrangThai");
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày mong muốn, ở đây là yyyy-MM-dd
                    String strHieuLuc = dateFormat.format(HieuLuc); // Chuyển đổi HieuLuc thành String
    
                    txtmakm.setText(maKM);
                    txttenkm.setText(tenKM);
                    cboloaikhuyenmai.setSelectedItem(loaiKM);
                    cbogiatrikhuyenmai.setSelectedItem(giaTriKM);
                    cboapdung.setSelectedItem(DieuKienApDung);
                    txthieuluc.setText(strHieuLuc);
                    if(TrangThai.equals("Hết hiệu lực")){
                        rdohethieuluc.setSelected(true);
                    }else if(TrangThai.equals("Đang hiệu lực")){
                        rdohieuluc.setSelected(true);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void ThemKM() {
        try (Connection conn = getConnection()){    
            CallableStatement statement = conn.prepareCall("{call ThemKhuyenMai(?, ?, ?, ?, ?, ?, ?)}");{
            // Thiết lập các tham số cho stored procedure
            statement.setString(1, txtmakm.getText());  // MaNV
            statement.setString(2, txttenkm.getText());   // TenNV
            statement.setString(3, (String) cboloaikhuyenmai.getSelectedItem());
            statement.setString(4, (String) cbogiatrikhuyenmai.getSelectedItem()); // MatKhau
            statement.setString(5, (String) cboapdung.getSelectedItem()); // SDT
            statement.setDate(6, java.sql.Date.valueOf(txthieuluc.getText())); // Email
            if(rdohethieuluc.isSelected()){
                statement.setString(7, "Hết hiệu lực"); // VaiTro
            }else if(rdohieuluc.isSelected()){
                statement.setString(7, "Đang hiệu lực"); // VaiTro
            }
            statement.execute();
            //System.out.println("Thêm nhân viên thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void SuaKM() {
        try (Connection conn = getConnection()){    
            CallableStatement statement = conn.prepareCall("{call CapNhatKhuyenMai(?, ?, ?, ?, ?, ?, ?)}");{
            // Thiết lập các tham số cho stored procedure
            statement.setString(1, txtmakm.getText());  // MaNV
            statement.setString(2, txttenkm.getText());   // TenNV
            statement.setString(3, (String) cboloaikhuyenmai.getSelectedItem());
            statement.setString(4, (String) cbogiatrikhuyenmai.getSelectedItem()); // MatKhau
            statement.setString(5, (String) cboapdung.getSelectedItem()); // SDT
            statement.setDate(6, java.sql.Date.valueOf(txthieuluc.getText())); // Email
            if(rdohethieuluc.isSelected()){
                statement.setString(7, "Hết hiệu lực"); // VaiTro
            }else if(rdohieuluc.isSelected()){
                statement.setString(7, "Đang hiệu lực"); // VaiTro
            }
            statement.execute();
            //System.out.println("Thêm nhân viên thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void XoaKM() {
        try (Connection conn = getConnection()){    
            CallableStatement statement = conn.prepareCall("{call XoaKhuyenMai(?)}");{
            // Thiết lập các tham số cho stored procedure
            statement.setString(1, txtmakm.getText());  // MaNV
            
            statement.execute();
            
            //System.out.println("Thêm nhân viên thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

private String formatDate(Date inputDate) {
    if (inputDate == null) {
        return "";
    }

    try {
        // Chuyển đổi ngày từ java.sql.Date thành dd-MM-yyyy
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        return outputFormat.format(inputDate);
    } catch (Exception e) {
        e.printStackTrace();
        return ""; // Trả về chuỗi rỗng nếu có lỗi
    }
}

private Date parseNgaySinh(String ngaySinhString) throws ParseException {
    if (ngaySinhString == null || ngaySinhString.isEmpty()) {
        return null;
    }

    // Chuyển đổi chuỗi ngày từ dd-MM-yyyy thành Date
    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
    return inputFormat.parse(ngaySinhString);
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txttenkm = new javax.swing.JTextField();
        cboloaikhuyenmai = new javax.swing.JComboBox<>();
        cbogiatrikhuyenmai = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        cboapdung = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        rdohieuluc = new javax.swing.JRadioButton();
        rdohethieuluc = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtmakm = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txthieuluc = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblkhuyenmai = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Tên khuyến mãi :");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 41, 118, 41));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Loại khuyến mãi :");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 88, 101, 31));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Giá trị khuyến mãi:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 137, -1, 30));

        txttenkm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttenkmActionPerformed(evt);
            }
        });
        jPanel3.add(txttenkm, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 50, 236, -1));

        cboloaikhuyenmai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giảm giá", "Tặng phẩm" }));
        jPanel3.add(cboloaikhuyenmai, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 92, 236, -1));

        cbogiatrikhuyenmai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "15%", "1 sản phẩm", "20%", "10%", " " }));
        jPanel3.add(cbogiatrikhuyenmai, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 141, 236, -1));

        jButton2.setText("Lưu");
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(646, 274, 121, 34));

        jButton12.setText("Xóa");
        jPanel3.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(785, 274, 121, 34));

        jButton13.setText("Cập Nhật");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(507, 274, 121, 34));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 228, 900, 10));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Áp dụng cho :");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(438, 26, 118, 33));

        cboapdung.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đơn hàng trên 500,000 VND", "Áp dụng cho sản phẩm A", "Chỉ trong 24 giờ", "Sử dụng thẻ thành viên", "Mua combo A + B", " " }));
        jPanel3.add(cboapdung, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 31, 220, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Trạng thái :");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 118, 33));

        buttonGroup5.add(rdohieuluc);
        rdohieuluc.setText("Đang Hiệu Lực");
        jPanel3.add(rdohieuluc, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 130, -1, -1));

        buttonGroup5.add(rdohethieuluc);
        rdohethieuluc.setText("Hết Hiệu Lực");
        jPanel3.add(rdohethieuluc, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 130, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Mã khuyến mãi:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 10, -1, -1));
        jPanel3.add(txtmakm, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 7, 236, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Hiệu lực đến:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, -1, -1));
        jPanel3.add(txthieuluc, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 220, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Folder+.png"))); // NOI18N
        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, -1, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/FolderOpen.png"))); // NOI18N
        jButton3.setText("Sửa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 190, -1, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Folder-.png"))); // NOI18N
        jButton4.setText("Xóa");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 190, -1, -1));

        jTabbedPane2.addTab("Tạo Khuyến Mãi", jPanel3);

        jTabbedPane3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        tblkhuyenmai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã KM", "Tên KM", "Loại KM", "Giá Trị KM", "Điều Kiện", "Hiệu Lực", "Trạng Thái"
            }
        ));
        tblkhuyenmai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblkhuyenmaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblkhuyenmai);

        jTabbedPane3.addTab("Quản Lý Khuyến Mãi", jScrollPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane2)
                    .addComponent(jTabbedPane3))
                .addGap(33, 33, 33))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 30, Short.MAX_VALUE)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 66, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Cart.png"))); // NOI18N
        jLabel9.setText("Tên Shop");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 6, 119, 60));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Solid.png"))); // NOI18N
        jLabel19.setText("Thống Kê");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Box.png"))); // NOI18N
        jLabel20.setText("Sản Phẩm");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Note.png"))); // NOI18N
        jLabel21.setText("Hóa Đơn");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Team.png"))); // NOI18N
        jLabel22.setText("Khách Hàng");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, -1, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/History.png"))); // NOI18N
        jLabel23.setText("Lịch Sử");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, -1, -1));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Hot.png"))); // NOI18N
        jLabel24.setText("Khuyến Mãi");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, -1, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/User.png"))); // NOI18N
        jLabel25.setText("Nhân Viên");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, -1, -1));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Repost.png"))); // NOI18N
        jLabel26.setText("Đổi Mật Khẩu");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 500, -1, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/LogOut.png"))); // NOI18N
        jLabel27.setText("Đăng Xuất");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 560, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void txttenkmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttenkmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttenkmActionPerformed

    private void tblkhuyenmaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblkhuyenmaiMouseClicked
        int selectedRow = tblkhuyenmai.getSelectedRow();
        // Lấy mã nhân viên từ cột 0
        String employeeID = tblkhuyenmai.getValueAt(selectedRow, 0).toString();
        // Gọi phương thức để lấy thông tin nhân viên từ cơ sở dữ liệu
        retrievedata(employeeID);
    }//GEN-LAST:event_tblkhuyenmaiMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ThemKM();
        JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công");
        updatekhuyenmaiFromUsersTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        SuaKM();
        JOptionPane.showMessageDialog(this, "Sửa khuyến mãi thành công");
        updatekhuyenmaiFromUsersTable();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        XoaKM();
        JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công");
        updatekhuyenmaiFromUsersTable();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(KhuyenMai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhuyenMai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhuyenMai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhuyenMai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KhuyenMai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JComboBox<String> cboapdung;
    private javax.swing.JComboBox<String> cbogiatrikhuyenmai;
    private javax.swing.JComboBox<String> cboloaikhuyenmai;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JRadioButton rdohethieuluc;
    private javax.swing.JRadioButton rdohieuluc;
    private javax.swing.JTable tblkhuyenmai;
    private javax.swing.JTextField txthieuluc;
    private javax.swing.JTextField txtmakm;
    private javax.swing.JTextField txttenkm;
    // End of variables declaration//GEN-END:variables
}

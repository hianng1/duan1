/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Khachhang;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import doituong.NhanVien;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Callable;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author tandm
 */
public class Bangnhanvien extends javax.swing.JFrame {

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
    public Bangnhanvien() {
        initComponents();
        setLocationRelativeTo(null);
        updateNhanVienFromUsersTable();
        tblnhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblnhanvienMouseClicked(evt);
            }
        });
    }

    private void createTaiKhoanChoKhachHang(String Manv, String tenTaiKhoan, String matKhau) {
        try (Connection conn = getConnection()) {
            // Kiểm tra xem khách hàng đã có tài khoản chưa bằng cách truy vấn cơ sở dữ liệu
            String query = "SELECT COUNT(*) AS count FROM NhanVien WHERE MaNV = ? AND TenTaiKhoan IS NOT NULL";
            PreparedStatement checkStatement = conn.prepareStatement(query);
            checkStatement.setString(1, Manv);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("count");

            // Nếu số lượng tài khoản của khách hàng > 0, có nghĩa là họ đã có tài khoản, không thêm mới
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Khách hàng đã có tài khoản");
                return;
            }

            // Nếu không, thêm tài khoản mới cho khách hàng
            String insertQuery = "UPDATE NhanVien SET TenTaiKhoan = ?, MatKhau = ? WHERE MaNV = ?";
            PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
            insertStatement.setString(1, tenTaiKhoan);
            insertStatement.setString(2, matKhau);
            insertStatement.setString(3, Manv);

            int rowsUpdated = insertStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản thất bại");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateNhanVienFromUsersTable() {
        try (Connection connection = getConnection()) {
            String query = "SELECT MaNV as maNV, TenNV, NgaySinh, SDT FROM NhanVien";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    DefaultTableModel model = (DefaultTableModel) tblnhanvien.getModel();
                    model.setRowCount(0); // Xóa tất cả các dòng hiện tại trong bảng

                    while (resultSet.next()) {
                        // Tạo đối tượng NhanVien từ dữ liệu bảng users
                        String maNV = resultSet.getString("maNV");
                        String tenNV = resultSet.getString("TenNV");
                        String ngaySinhString = formatDate(resultSet.getDate("NgaySinh"));
                        String sdt = resultSet.getString("SDT");

                        Date ngaySinh = null;
                        try {
                            ngaySinh = parseNgaySinh(ngaySinhString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        NhanVien nhanVien = new NhanVien(maNV, tenNV, sdt, ngaySinh);

                        // Thêm dòng mới từ đối tượng NhanVien
                        model.addRow(new Object[]{
                            nhanVien.getMaNV(),
                            nhanVien.getTenNV(),
                            nhanVien.formatNgaySinh(),
                            nhanVien.getSdt(),});
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retrievedata1(String MaNV) {
        try (Connection conn = getConnection()) {
            String query = "SELECT MaNV, TenTaiKhoan, MatKhau FROM NhanVien WHERE MaNV = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, MaNV);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String manv = result.getString("MaNV");
                String ttk = result.getString("TenTaiKhoan");
                String mk = result.getString("MatKhau");

                txtmanv1.setText(manv);
                txttentaikhoan1.setText(ttk);
                txtmatkhau1.setText(mk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retrieveEmployeeInfoFromDatabase(String MaNV) {
        try (Connection conn = getConnection()) {
            String query = "SELECT MaNV ,TenNV, NgaySinh, SDT, Email, TenTaiKhoan ,MatKhau, VaiTro ,DiaChi FROM NhanVien WHERE MaNV = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, MaNV);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String tennv = result.getString("TenNV");
                String ngaysinh = result.getString("NgaySinh");
                String sdt = result.getString("SDT");
                String email = result.getString("Email");
                String tentaikhoan = result.getString("TenTaiKhoan");
                String matkhau = result.getString("MatKhau");
                String vaitro = result.getString("VaiTro");
                String diachi = result.getString("DiaChi");
                txtmanv.setText(MaNV);
                txttennv.setText(tennv);
                txtngaysinh.setText(ngaysinh);
                txtsdt.setText(sdt);
                txtemail.setText(email);
                txttentaikhoan.setText(tentaikhoan);
                txtmatkhau.setText(matkhau);
                if (vaitro.equals("Quản trị viên")) {
                    rdoquantrivien.setSelected(true);
                } else if (vaitro.equals("Nhân viên")) {
                    rdonhanvien.setSelected(true);
                }
                txtdiachi.setText(diachi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //sửa thêm nhân viên
    private void ThemNV() {
        try (Connection conn = getConnection()) {
            CallableStatement statement = conn.prepareCall("{call ThemNhanVien(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            {
//VALUES (@MaNV, @TenNV, @TenTaiKhoan, @MatKhau, @SDT, @Email, @DiaChi, @VaiTro, @NgaySinh)
                // Thiết lập các tham số cho stored procedure
                statement.setString(1, txtmanv.getText());  // MaNV
                statement.setString(2, txttennv.getText());   // TenNV
                statement.setString(3, txttentaikhoan.getText());
                statement.setString(4, Arrays.toString(txtmatkhau.getPassword())); // MatKhau
                statement.setString(5, txtsdt.getText()); // SDT
                statement.setString(6, txtemail.getText()); // Email
                statement.setString(7, txtdiachi.getText()); // DiaChi
                if (rdoquantrivien.isSelected()) {
                    statement.setString(8, "Quản trị viên"); // VaiTro
                } else if (rdonhanvien.isSelected()) {
                    statement.setString(8, "Nhân viên"); // VaiTro
                }
                statement.setDate(9, java.sql.Date.valueOf(txtngaysinh.getText())); // NgaySinh

                // Thực thi stored procedure
                statement.execute();

                //System.out.println("Thêm nhân viên thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void SuaNV() {
        try (Connection conn = getConnection()) {
            CallableStatement statement = conn.prepareCall("{call SuaNhanVien(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            {
                // Thiết lập các tham số cho stored procedure
                statement.setString(1, txtmanv.getText());  // MaNV
                statement.setString(2, txttennv.getText());   // TenNV
                statement.setString(3, txttentaikhoan.getText());
                statement.setString(4, Arrays.toString(txtmatkhau.getPassword())); // MatKhau
                statement.setString(5, txtsdt.getText()); // SDT
                statement.setString(6, txtemail.getText()); // Email
                statement.setString(7, txtdiachi.getText()); // DiaChi
                if (rdoquantrivien.isSelected()) {
                    statement.setString(8, "Quản trị viên"); // VaiTro
                } else if (rdonhanvien.isSelected()) {
                    statement.setString(8, "Nhân viên"); // VaiTro
                }
                statement.setDate(9, java.sql.Date.valueOf(txtngaysinh.getText())); // NgaySinh

                // Thực thi stored procedure
                statement.execute();

                //System.out.println("Thêm nhân viên thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void XoaNV() {
        try (Connection conn = getConnection()) {
            CallableStatement statement = conn.prepareCall("{call XoaNhanVien(?)}");
            {
                // Thiết lập các tham số cho stored procedure
                statement.setString(1, txtmanv.getText());  // MaNV

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
    private void increaseFontSize(JLabel label) {
    Font currentFont = label.getFont();
    int newSize = currentFont.getSize() + 1;
    label.setFont(currentFont.deriveFont(Font.PLAIN, newSize));
}

private void toggleBold(JLabel label) {
    Font currentFont = label.getFont();
    int style = currentFont.getStyle();
    if (style == Font.BOLD) {
        label.setFont(currentFont.deriveFont(Font.PLAIN));
    } else {
        label.setFont(currentFont.deriveFont(Font.BOLD));
    }
}

private void changeColor(JLabel label) {
    Color currentColor = label.getForeground();
    if (currentColor == Color.BLACK) {
        label.setForeground(Color.RED);
    } else {
        label.setForeground(Color.BLACK);
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblnhanvien = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jButton14 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        rdonhanvien = new javax.swing.JRadioButton();
        rdoquantrivien = new javax.swing.JRadioButton();
        jButton13 = new javax.swing.JButton();
        txtmanv = new javax.swing.JTextField();
        txttennv = new javax.swing.JTextField();
        txtngaysinh = new javax.swing.JTextField();
        txtsdt = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtmatkhau = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txttentaikhoan = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtdiachi = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtmanv1 = new javax.swing.JTextField();
        txttentaikhoan1 = new javax.swing.JTextField();
        txtmatkhau1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        tblnhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã NV", "Tên NV", "Ngày Sinh", "Số ĐT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblnhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblnhanvienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblnhanvien);

        jTabbedPane3.addTab("Quản Lý Nhân Viên", jScrollPane1);

        jPanel2.add(jTabbedPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 26, 579, 526));

        jTabbedPane2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Mã NV:");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Tên :");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, -1));
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(244, 129, 40, 10));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Số ĐT:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Ngày Sinh :");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 83, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Email:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, -1, -1));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 278, 10));

        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Note.png"))); // NOI18N
        jButton14.setText("Tạo Mới");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 500, -1, 47));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Cập Nhật Thông Tin");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 390, -1, -1));
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 589, 284, 10));

        jButton15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Note.png"))); // NOI18N
        jButton15.setText("Thêm ");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 124, 48));

        jButton16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Repost.png"))); // NOI18N
        jButton16.setText("Sửa");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 440, 113, 48));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("Vai Trò:");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 262, -1, -1));

        buttonGroup2.add(rdonhanvien);
        rdonhanvien.setText("Nhân Viên");
        rdonhanvien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdonhanvienActionPerformed(evt);
            }
        });
        jPanel3.add(rdonhanvien, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, -1, -1));

        buttonGroup2.add(rdoquantrivien);
        rdoquantrivien.setText("Quản Trị Viên");
        jPanel3.add(rdoquantrivien, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, -1, -1));

        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Hot.png"))); // NOI18N
        jButton13.setText("Xóa");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 124, 47));

        txtmanv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmanvActionPerformed(evt);
            }
        });
        jPanel3.add(txtmanv, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 166, -1));
        jPanel3.add(txttennv, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 166, -1));
        jPanel3.add(txtngaysinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 168, -1));
        jPanel3.add(txtsdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 168, -1));
        jPanel3.add(txtemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 168, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Mật Khẩu:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 223, -1, -1));
        jPanel3.add(txtmatkhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 170, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Địa Chỉ:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 302, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Tên Tài Khoản:");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, -1, -1));
        jPanel3.add(txttentaikhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 170, -1));

        txtdiachi.setColumns(20);
        txtdiachi.setRows(5);
        jScrollPane2.setViewportView(txtdiachi);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(94, 290, 190, 70));

        jTabbedPane2.addTab("Thông Tin Nhân Viên", jPanel3);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Mã NV:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Tên Tài Khoản:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Mật Khẩu:");

        jButton1.setText("Tạo Tài Khoản");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Sửa Thông Tin");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Xóa Tài Khoản");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(31, 31, 31)
                                .addComponent(jButton3))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(jButton4)))
                        .addGap(0, 33, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txttentaikhoan1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtmatkhau1)
                            .addComponent(txtmanv1))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtmanv1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txttentaikhoan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtmatkhau1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(369, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Thông tin tài khoản", jPanel4);

        jPanel2.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(681, 26, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 72, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Cart.png"))); // NOI18N
        jLabel9.setText("Tên Shop");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 130, 60));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Solid.png"))); // NOI18N
        jLabel10.setText("Thống Kê");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 105, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Box.png"))); // NOI18N
        jLabel11.setText("Sản Phẩm");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 158, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/User.png"))); // NOI18N
        jLabel12.setText("Nhân Viên");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Note.png"))); // NOI18N
        jLabel13.setText("Hóa Đơn");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Team.png"))); // NOI18N
        jLabel14.setText("Khách Hàng");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/History.png"))); // NOI18N
        jLabel15.setText("Lịch Sử");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Hot.png"))); // NOI18N
        jLabel16.setText("Khuyến Mãi");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Repost.png"))); // NOI18N
        jLabel17.setText("Đổi Mật Khẩu");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 520, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/LogOut.png"))); // NOI18N
        jLabel18.setText("Đăng Xuất");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 580, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        txtdiachi.setText("");
        txtmanv.setText("");
        txttennv.setText("");
        txttentaikhoan.setText("");
        txtmatkhau.setText("");
        txtngaysinh.setText("");
        txtsdt.setText("");
        txtemail.setText("");
        rdonhanvien.setSelected(true);


    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        ThemNV();
        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công");
        updateNhanVienFromUsersTable();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void rdonhanvienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdonhanvienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdonhanvienActionPerformed

    private void tblnhanvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblnhanvienMouseClicked
        int selectedRow = tblnhanvien.getSelectedRow();
        // Lấy mã nhân viên từ cột 0
        String employeeID = tblnhanvien.getValueAt(selectedRow, 0).toString();
        // Gọi phương thức để lấy thông tin nhân viên từ cơ sở dữ liệu
        retrieveEmployeeInfoFromDatabase(employeeID);
        retrievedata1(employeeID);
    }//GEN-LAST:event_tblnhanvienMouseClicked

    private void txtmanvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmanvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmanvActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        SuaNV();
        JOptionPane.showMessageDialog(this, "Sửa thành công");
        updateNhanVienFromUsersTable();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        XoaNV();
        JOptionPane.showMessageDialog(this, "Xóa thành công");
        updateNhanVienFromUsersTable();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String MaKH = txtmanv1.getText();
        String tenTaiKhoan = txttentaikhoan.getText();
        char[] matKhau = txtmatkhau.getPassword();
        // Chuyển đổi mật khẩu từ mảng char thành chuỗi
        String matKhauStr = new String(matKhau);
        createTaiKhoanChoKhachHang(MaKH, tenTaiKhoan, matKhauStr);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try (Connection conn = getConnection()) {
            String tenTaiKhoan = txttentaikhoan.getText();
            String matKhau = new String(txtmatkhau.getPassword());

            // Xác thực dữ liệu đầu vào (ví dụ: kiểm tra các trường nhập liệu không rỗng)
            if (tenTaiKhoan.isEmpty() || matKhau.isEmpty()) {
                System.out.println("Vui lòng nhập đầy đủ thông tin");
                return;
            }

            String query = "UPDATE KhachHang SET TenTaiKhoan = ?, MatKhau = ? WHERE MaKH = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, tenTaiKhoan);
            statement.setString(2, matKhau);
            statement.setString(3, txtmanv1.getText());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật không thành công");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try (Connection conn = getConnection()) {
            String query = "UPDATE KhachHang SET TenTaiKhoan = NULL, MatKhau = NULL WHERE MaKH = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, txtmanv1.getText());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "xóa thành công");
            } else {
                JOptionPane.showMessageDialog(this, "xóa không thành công");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        new ThongKeDoanhThu().setVisible(true);
        this.setVisible(false);     
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new SanPham().setVisible(true);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new HoaDon_QuanLi().setVisible(true);
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new bangKhachHang().setVisible(true);
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new LichSu().setVisible(true);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new KhuyenMai().setVisible(true);
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new Bangnhanvien().setVisible(true);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new DoiMatKhau().setVisible(true);
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new DangNhap().setVisible(true);
    }//GEN-LAST:event_jLabel18MouseClicked

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
            java.util.logging.Logger.getLogger(NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new Bangnhanvien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JRadioButton rdonhanvien;
    private javax.swing.JRadioButton rdoquantrivien;
    private javax.swing.JTable tblnhanvien;
    private javax.swing.JTextArea txtdiachi;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtmanv;
    private javax.swing.JTextField txtmanv1;
    private javax.swing.JPasswordField txtmatkhau;
    private javax.swing.JPasswordField txtmatkhau1;
    private javax.swing.JTextField txtngaysinh;
    private javax.swing.JTextField txtsdt;
    private javax.swing.JTextField txttennv;
    private javax.swing.JTextField txttentaikhoan;
    private javax.swing.JTextField txttentaikhoan1;
    // End of variables declaration//GEN-END:variables
}

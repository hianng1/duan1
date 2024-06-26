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
import javax.swing.JOptionPane;


/**
 *
 * @author Dell
 */
public class khoiphucmatkhau extends javax.swing.JFrame {

    public static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=duan1;encrypt=true;trustServerCertificate=true;";
    public static String username = "sa";
    public static String password = "123";
     
    private static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(dburl, username, password);
}
    private String phone;
    private String user;

    /**
     * Creates new form khoiphucmatkhau
     */
    public khoiphucmatkhau(String phone, String username) {
        initComponents();
        this.phone = phone;
        this.user = username;
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtmatkhaumoi = new javax.swing.JTextField();
        txtmatkhaumoi1 = new javax.swing.JTextField();
        btndoimatkhau = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("KHÔI PHỤC MẬT KHẨU");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Mật khẩu mới :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Nhập lại mật khẩu :");

        btndoimatkhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/accept.png"))); // NOI18N
        btndoimatkhau.setText("Đổi Mật Khẩu");
        btndoimatkhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndoimatkhauActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(102, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(91, 91, 91))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btndoimatkhau)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtmatkhaumoi)
                            .addComponent(txtmatkhaumoi1, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtmatkhaumoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtmatkhaumoi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btndoimatkhau)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btndoimatkhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndoimatkhauActionPerformed
        // TODO add your handling code here:
        String newPassword = txtmatkhaumoi.getText();
        String confirmPassword = txtmatkhaumoi1.getText();

        // Kiểm tra xem mật khẩu mới và xác nhận mật khẩu có trùng khớp hay không
        if (newPassword.equals(confirmPassword)) {
            // Lấy số điện thoại và tên đăng nhập từ hàm quenmatkhau
            // Cập nhật mật khẩu trong CSDL
            if (isValidPassword(newPassword)) {
                // Nếu mật khẩu hợp lệ, thêm đối tượng User vào CSDL
                if (updatePassword(phone, user, newPassword)) {
                    JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
                    // Đóng frame khoiphucmatkhau sau khi đổi mật khẩu thành công
                    this.setVisible(false);
                    new DangNhap().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể đổi mật khẩu");
                }
            } else {
                // Nếu mật khẩu không hợp lệ, hiển thị thông báo
                JOptionPane.showMessageDialog(this, "Mật khẩu không hợp lệ. Phải có ít nhất 6 kí tự, bao gồm ít nhất một chữ cái và một chữ số.");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Mật khẩu không trùng khớp");
        }
    }//GEN-LAST:event_btndoimatkhauActionPerformed
    private boolean isValidPassword(String password) {
        // Kiểm tra mật khẩu có ít nhất 6 kí tự, và phải có ít nhất một chữ cái và một chữ số
        return password.length() >= 6 && password.matches(".*\\d.*") && password.matches(".*[a-zA-Z].*");
    }

    private boolean updatePassword(String phone, String username, String newPassword) {
        try (Connection connection = getConnection()) {
            String query = "UPDATE users SET MatKhau=? WHERE SDT=? AND TenTaiKhoan=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, phone);
                preparedStatement.setString(3, username);
                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

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
            java.util.logging.Logger.getLogger(khoiphucmatkhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(khoiphucmatkhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(khoiphucmatkhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(khoiphucmatkhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndoimatkhau;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtmatkhaumoi;
    private javax.swing.JTextField txtmatkhaumoi1;
    // End of variables declaration//GEN-END:variables
}

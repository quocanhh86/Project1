/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Repository.ProductRepo;
import Repository.impl.ProductRepoImpl;
import Utils.Auth;
import Utils.MsgBox;
import entity.Product;
import java.awt.CardLayout;
import java.sql.SQLException;

/**
 *
 * @author THINKPAD
 */
public class Main extends javax.swing.JFrame {

    private CardLayout layCard;
    HoaDon hd = new HoaDon();
    QlSanPham sp = new QlSanPham();
    NhanVien_Form nv = new NhanVien_Form();
    KhachHangS kh = new KhachHangS();
    ThongKe tk = new ThongKe();
    BanHang bh = new BanHang();
    DoiMatKhau_Form dmk = new DoiMatKhau_Form();
    private String timeee, tongTienDauCaaa;
    private ProductRepo productRepo;

    /**
     * Creates new form Main
     */
    public Main() throws Exception {
        initComponents();
        layCard = new CardLayout();
        Panel3.setLayout(layCard);
        Panel3.add(bh, "banhang");
        Panel3.add(hd, "hoadon");
        Panel3.add(sp, "sanpham");
        Panel3.add(nv, "nhanvien");
        Panel3.add(kh, "khachhang");
        Panel3.add(tk, "thongke");
        Panel3.add(dmk, "doimk");
        setLocationRelativeTo(null);
        productRepo = new ProductRepoImpl();
    }

    public Main(String time, String tongTienDauCa) throws Exception {
        initComponents();
        setLocationRelativeTo(null);

        boolean user = Auth.isLogin();
        if (user == true) {
            lblUser.setText(Auth.user.getHoTen());
        } else {
            lblUser.setText("NULL");
            MsgBox.alert(this, "Bạn chưa đăng nhập! ");
        }
        this.tongTienDauCaaa = tongTienDauCa;
        this.timeee = time;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel1 = new javax.swing.JPanel();
        Panel2 = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        btnSanPham = new javax.swing.JButton();
        btnHoaDon = new javax.swing.JButton();
        btnKhachhang = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        btnNhanVien = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        btnDoiMatKhau = new javax.swing.JButton();
        btnBanHang = new javax.swing.JButton();
        Panel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Panel1.setBackground(new java.awt.Color(255, 204, 51));

        Panel2.setBackground(new java.awt.Color(255, 255, 204));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUser.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblUser.setForeground(new java.awt.Color(204, 0, 51));
        lblUser.setText("Nhóm 6 - The Pc Sneaker");
        Panel2.add(lblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 266, 72));

        btnSanPham.setBackground(new java.awt.Color(255, 153, 0));
        btnSanPham.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnSanPham.setText("Sản Phẩm");
        btnSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSanPhamActionPerformed(evt);
            }
        });
        Panel2.add(btnSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 266, 72));

        btnHoaDon.setBackground(new java.awt.Color(255, 153, 0));
        btnHoaDon.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnHoaDon.setText("Hóa Đơn");
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });
        Panel2.add(btnHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 266, 72));

        btnKhachhang.setBackground(new java.awt.Color(255, 153, 0));
        btnKhachhang.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnKhachhang.setText("Khách Hàng");
        btnKhachhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachhangActionPerformed(evt);
            }
        });
        Panel2.add(btnKhachhang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 266, 72));

        btnThongKe.setBackground(new java.awt.Color(255, 153, 0));
        btnThongKe.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnThongKe.setText("Thống Kê");
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });
        Panel2.add(btnThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 266, 72));

        btnNhanVien.setBackground(new java.awt.Color(255, 153, 0));
        btnNhanVien.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnNhanVien.setText("Nhân Viên");
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });
        Panel2.add(btnNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 590, 266, 72));

        btnDangXuat.setBackground(new java.awt.Color(255, 153, 0));
        btnDangXuat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnDangXuat.setText("Đăng Xuất");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        Panel2.add(btnDangXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 770, 266, 72));

        btnDoiMatKhau.setBackground(new java.awt.Color(255, 153, 0));
        btnDoiMatKhau.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnDoiMatKhau.setText("Đổi Mật Khẩu");
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });
        Panel2.add(btnDoiMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 680, 266, 72));

        btnBanHang.setBackground(new java.awt.Color(255, 153, 0));
        btnBanHang.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnBanHang.setText("Bán Hàng");
        btnBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBanHangActionPerformed(evt);
            }
        });
        Panel2.add(btnBanHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 266, 72));

        Panel3.setBackground(new java.awt.Color(255, 255, 255));
        Panel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Panel3.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout Panel1Layout = new javax.swing.GroupLayout(Panel1);
        Panel1.setLayout(Panel1Layout);
        Panel1Layout.setHorizontalGroup(
            Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Panel2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel1Layout.setVerticalGroup(
            Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Panel2, javax.swing.GroupLayout.PREFERRED_SIZE, 864, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Panel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) Panel3.getLayout();
        cardLayout.show(Panel3, "hoadon");
    }//GEN-LAST:event_btnHoaDonActionPerformed

    private void btnSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSanPhamActionPerformed
        CardLayout cardLayout = (CardLayout) Panel3.getLayout();
        cardLayout.show(Panel3, "sanpham");
    }//GEN-LAST:event_btnSanPhamActionPerformed

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienActionPerformed

        CardLayout cardLayout = (CardLayout) Panel3.getLayout();
        cardLayout.show(Panel3, "nhanvien");
    }//GEN-LAST:event_btnNhanVienActionPerformed

    private void btnKhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachhangActionPerformed
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) Panel3.getLayout();
        cardLayout.show(Panel3, "khachhang");
    }//GEN-LAST:event_btnKhachhangActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) Panel3.getLayout();
        cardLayout.show(Panel3, "thongke");
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        DangNhap_Form dangNhap_Form = new DangNhap_Form();
        dangNhap_Form.setVisible(true);
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) Panel3.getLayout();
        cardLayout.show(Panel3, "doimk");
    }//GEN-LAST:event_btnDoiMatKhauActionPerformed

    private void btnBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBanHangActionPerformed
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) Panel3.getLayout();
        cardLayout.show(Panel3, "banhang");
    }//GEN-LAST:event_btnBanHangActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Main().setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private javax.swing.JPanel Panel3;
    private javax.swing.JButton btnBanHang;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnKhachhang;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnSanPham;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JLabel lblUser;
    // End of variables declaration//GEN-END:variables
}

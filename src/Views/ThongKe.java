/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;


import Model.OrderViewModel;
import Model.ReportViewModel;
import Repository.OrderDetailRepo;
import Repository.OrderRepo;
import Repository.impl.OrderDetailRepoImpl;
import Repository.impl.OrderRepoImpl;
import Utils.FileUtils;
import entity.OrderDetail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author admin
 */
public class ThongKe extends javax.swing.JPanel {
    private OrderDetailRepo orderDetailRepo;
    private Object[] reportDataList;
    private List<ReportViewModel> reportDetailList;
    private List<OrderViewModel> orderViewModelList;
    private List<OrderDetail> orderDetailList;
    private OrderRepo orderRepo;

    public ThongKe() throws Exception {
        initComponents();
        this.orderDetailRepo = new OrderDetailRepoImpl();
        this.orderRepo = new OrderRepoImpl();
        this.loadReportData();
        this.loadReportDetailData();
        this.showOrder(null, null);
        this.addActionListener();
    }

    private void exportExcel() {
        try {
            // List<String> columnNames = Arrays.asList("Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá");
            List<String> columnNames = new ArrayList<>();
            columnNames.add("Mã Sản Phẩm");
            columnNames.add("Tên Sản Phẩm");
            columnNames.add("Số Lượng");
            columnNames.add("Đơn Giá");
            List<String[]> data = new ArrayList<>();
            for (ReportViewModel reportViewModel : reportDetailList) {
                data.add(new String[]{
                    String.valueOf(reportViewModel.getProductDetailId()),
                    reportViewModel.getProductName(),
                    String.valueOf(reportViewModel.getQuantity()),
                    String.valueOf(reportViewModel.getPrice())
                });
            }
            FileUtils.writeExcelReportFile("ThongKe", columnNames, data, new Date(), "admin");
            JOptionPane.showMessageDialog(this, "Xuất file thành công");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addActionListener() {
        // set mouse click event for table
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    showOrderDetail();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String fromDate = jTextField3.getText();
                String toDate = jTextField4.getText();
                try {
                    reportDetailList = orderDetailRepo.reportDetail(fromDate, toDate);
                    reportDataList = orderDetailRepo.report(fromDate, toDate);
                    loadReportData();
                    loadReportDetailData();
                    showOrder(fromDate, toDate);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportExcel();
            }
        });
    }

    private void showOrder(String fromDate, String toDate) throws Exception {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        this.orderViewModelList = orderRepo.findByCondition(fromDate, toDate);
        DecimalFormat df = new DecimalFormat("#,###");
        for (OrderViewModel orderViewModel : orderViewModelList) {
            model.addRow(new Object[]{
                orderViewModel.getId(),
                orderViewModel.getStaffName(),
                orderViewModel.getCustomerName(),
                orderViewModel.getOrderDate(),
                df.format(orderViewModel.getTotal()),
                orderViewModel.getStatus()});
        }
    }

    private void showOrderDetail() throws Exception {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow >= 0) {
            int orderId = (int) jTable1.getValueAt(selectedRow, 0);
            this.orderDetailList = orderDetailRepo.findAllByCondition(orderId);
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);
            DecimalFormat df = new DecimalFormat("#,###");
            for (OrderDetail orderDetail : orderDetailList) {
                model.addRow(new Object[]{
                    orderDetail.getId(),
                    orderDetail.getOrderId(),
                    orderDetail.getProductDetailId(),
                    orderDetail.getProductName(),
                    df.format(orderDetail.getQuantity()),
                    df.format(orderDetail.getPrice())});
            }
        }
    }

    private void loadReportDetailData() {
        try {
            reportDetailList = orderDetailRepo.reportDetail(null, null);
            DefaultTableModel model = (DefaultTableModel) tblThongKe.getModel();
            model.setRowCount(0);
            DecimalFormat df = new DecimalFormat("#,###");
            for (ReportViewModel reportViewModel : reportDetailList) {
                model.addRow(new Object[]{
                    reportViewModel.getProductDetailId(),
                    reportViewModel.getProductName(),
                    df.format(reportViewModel.getQuantity()),
                    df.format(reportViewModel.getPrice())});
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadReportData() {
        DecimalFormat df = new DecimalFormat("#,###");
        try {
            reportDataList = orderDetailRepo.report(null, null);
            lbDoanhThu.setText(df.format(reportDataList[0]));
            lbSoLuong.setText(df.format(reportDataList[1]));
            lbHoaDon.setText(df.format(reportDataList[2]));
            lbKhachHang.setText(df.format(reportDataList[3]));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbDoanhThu = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbSoLuong = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbHoaDon = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongKe = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        rdTatCa = new javax.swing.JRadioButton();
        rdNamThang = new javax.swing.JRadioButton();
        pnDate = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        btnLoc = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbTuNgay = new javax.swing.JLabel();
        lbDenNgay = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        ccc = new javax.swing.JLabel();
        lbHuy = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbKhachHang = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(950, 760));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(950, 760));
        jPanel1.setPreferredSize(new java.awt.Dimension(950, 760));

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Doanh Thu");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("VND");

        lbDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbDoanhThu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDoanhThu.setText("0");
        lbDoanhThu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lbDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addGap(28, 28, 28))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(53, 53, 53)
                    .addComponent(jLabel1)
                    .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(jLabel1)
                    .addGap(18, 18, 18)
                    .addComponent(lbDoanhThu)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Số Sản Phẩm Đã Bán");

        lbSoLuong.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbSoLuong.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSoLuong.setText("0");
        lbSoLuong.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jLabel3)
                    .addGap(26, 26, 26)
                    .addComponent(lbSoLuong)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Số HĐ Thành Công");

        lbHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHoaDon.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(13, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addGap(18, 18, 18))
                .addComponent(lbHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2)
                    .addGap(18, 18, 18)
                    .addComponent(lbHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(49, Short.MAX_VALUE))
        );

        tblThongKe.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblThongKe.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String[]{
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá"
            }
        ));
        jScrollPane1.setViewportView(tblThongKe);

        jPanel7.setBackground(new java.awt.Color(255, 255, 204));

        buttonGroup1.add(rdTatCa);
        rdTatCa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rdTatCa.setSelected(true);
        rdTatCa.setText("Tất Cả");
        rdTatCa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdTatCaItemStateChanged(evt);
            }
        });
        rdTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdTatCaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdNamThang);
        rdNamThang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rdNamThang.setText("Theo Tháng Năm");
        rdNamThang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdNamThangItemStateChanged(evt);
            }
        });
        rdNamThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNamThangActionPerformed(evt);
            }
        });

        pnDate.setBackground(new java.awt.Color(255, 204, 51));
        pnDate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setText("Từ");

        jLabel12.setText("Đến");

        javax.swing.GroupLayout pnDateLayout = new javax.swing.GroupLayout(pnDate);
        pnDate.setLayout(pnDateLayout);
        pnDateLayout.setHorizontalGroup(
            pnDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDateLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pnDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12)
                        .addComponent(jLabel9))
                    .addGap(18, 18, 18)
                    .addGroup(pnDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(62, Short.MAX_VALUE))
        );
        pnDateLayout.setVerticalGroup(
            pnDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnDateLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addGroup(pnDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(pnDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(13, Short.MAX_VALUE))
        );

        btnLoc.setBackground(new java.awt.Color(255, 204, 51));
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap(89, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(rdTatCa)
                            .addGap(18, 18, 18)
                            .addComponent(rdNamThang))
                        .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 84, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdTatCa)
                        .addComponent(rdNamThang))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(pnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnLoc)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Gửi Báo Cáo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Xuất Excel");

        lbTuNgay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTuNgay.setText("Từ ngày: ");

        lbDenNgay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbDenNgay.setText("Đến ngày: ");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String[]{
                "Mã Hóa Đơn", "Tên Nhân Viên", "Tên Khách Hàng", "Ngày Tạo", "Tổng Tiền", "Trạng Thái"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            null,
            new String[]{"Mã Giỏ Hàng", "Mã hóa đơn", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá"}
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable2);

        jPanel6.setBackground(new java.awt.Color(255, 255, 204));

        ccc.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        ccc.setText("Số HĐ Hủy");

        lbHuy.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbHuy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHuy.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lbHuy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                    .addContainerGap(76, Short.MAX_VALUE)
                    .addComponent(ccc)
                    .addGap(50, 50, 50))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(ccc)
                    .addGap(29, 29, 29)
                    .addComponent(lbHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Tổng Khách Hàng");

        lbKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbKhachHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbKhachHang.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap(22, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addGap(22, 22, 22))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lbKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel5)
                    .addGap(26, 26, 26)
                    .addComponent(lbKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2)
                                    .addComponent(jScrollPane3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbDenNgay)
                                            .addComponent(lbTuNgay))
                                        .addGap(22, 22, 22)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                            .addComponent(jTextField4))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton1)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton2)))
                            .addGap(15, 15, 15))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jScrollPane1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(15, 15, 15))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{jButton1, jButton2});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbTuNgay)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbDenNgay)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap(62, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[]{jButton1, jButton2});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1044, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTatCaActionPerformed

    }//GEN-LAST:event_rdTatCaActionPerformed

    private void rdNamThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNamThangActionPerformed

    }//GEN-LAST:event_rdNamThangActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void DateBatDauAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_DateBatDauAncestorAdded

    }//GEN-LAST:event_DateBatDauAncestorAdded

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnLocActionPerformed

    private void rdTatCaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdTatCaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rdTatCaItemStateChanged

    private void rdNamThangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdNamThangItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rdNamThangItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoc;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel ccc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lbDenNgay;
    private javax.swing.JLabel lbDoanhThu;
    private javax.swing.JLabel lbHoaDon;
    private javax.swing.JLabel lbHuy;
    private javax.swing.JLabel lbKhachHang;
    private javax.swing.JLabel lbSoLuong;
    private javax.swing.JLabel lbTuNgay;
    private javax.swing.JPanel pnDate;
    private javax.swing.JRadioButton rdNamThang;
    private javax.swing.JRadioButton rdTatCa;
    private javax.swing.JTable tblThongKe;
    // End of variables declaration//GEN-END:variables

}

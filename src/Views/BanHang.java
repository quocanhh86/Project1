package Views;


import Model.OrderViewModel;
import Model.ProductViewModel;
import Repository.*;
import Repository.impl.*;
import entity.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author THINKPAD
 */
public class BanHang extends javax.swing.JPanel {
    private List<ProductViewModel> productViewModelList;
    private ProductDetailRepo productDetailRepo;
    private CategoryRepo categoryRepo;
    private OrderRepo orderRepo;
    private StaffRepo staffRepo;
    private List<Category> categoryList;
    private List<OrderViewModel> orderViewModelList;
    private DefaultTableModel tblModel;
    private DefaultTableModel tblModelCart;
    private Staff staffCurrentLogin;
    private OrderDetailRepo orderDetailRepo;
    private Customer customer;
    private List<OrderDetail> orderDetailList;

    /**
     * Creates new form BanHang
     */
    public BanHang() throws Exception {
        initComponents();
        this.intiData();
        this.setEvent();
    }

    private void intiData() throws Exception {
        productDetailRepo = new ProductDetailRepoImpl();
        categoryRepo = new CategoryRepoImpl();
        orderRepo = new OrderRepoImpl();
        staffRepo = new StaffRepoImpl();
        orderDetailRepo = new OrderDetailRepoImpl();
        productViewModelList = productDetailRepo.findAllProduct(null, null);
        orderViewModelList = orderRepo.findAllPendingOrder();
        tblModel = (DefaultTableModel) tblSanPham.getModel();
        tblModelCart = (DefaultTableModel) tblGioHang.getModel();
        tblModelCart.setRowCount(0);
        showProductList();
        initCategory();
        showOrderList();
        this.staffCurrentLogin = staffRepo.findById(1).get(); // fix cứng id nhân viên
    }

    private void setEvent() {
        jComboBox4.addItemListener(e -> onChangeCategory());
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                onSearchProduct();
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                try {
                    addToCart();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        jButton4.addActionListener(e -> {
            try {
                removeProduct();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnChonKH.addActionListener(e -> {
            try {
                selectCustomer();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        btnTaoHD.addActionListener(e -> {
            try {
                createOrder();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtTongTien.setText(tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 5).toString());
                txtMaHD.setText(tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 1).toString());
                txtTongTien1.setText(tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 5).toString());
                try {
                    showCartTable();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
        txtTienKhachDua.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent evt) {
                try {
                    double total = Double.parseDouble(txtTongTien.getText());
                    double money = Double.parseDouble(txtTienKhachDua.getText());
                    DecimalFormat df = new DecimalFormat("#,###,###");
                    txtTienThua.setText(df.format(money - total));
                } catch (Exception e) {
                    txtTienThua.setText("");
                }
            }
        });
        btnHuyHD.addActionListener(e -> {
            try {
                cancelOrder();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        jButton3.addActionListener(e -> {
            try {
                payOrder();
                printReportFile();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    private void printReportFile() {
        JOptionPane.showMessageDialog(this, "Chức năng đang phát triển");
    }

    private void payOrder() throws Exception {
        if (tblHoaDon.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hóa đơn");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thanh toán hóa đơn này không?", "Thanh toán hóa đơn", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int orderId = (int) tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0);
            Order order = orderRepo.findById(orderId).get();
            order.setStatus("Đã thanh toán");
            orderRepo.update(orderId, order);
            this.orderViewModelList = orderRepo.findAllPendingOrder();
            this.showOrderList();
            JOptionPane.showMessageDialog(this, "Thanh toán hóa đơn thành công");
        }
    }

    private void cancelOrder() throws Exception {
        if (tblHoaDon.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hóa đơn");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy hóa đơn này không?", "Hủy hóa đơn", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int orderId = (int) tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0);
            List<OrderDetail> orderDetailList = orderDetailRepo.findAllByCondition(orderId);
            for (OrderDetail orderDetail : orderDetailList) {
                ProductDetail productDetail = productDetailRepo.findById(orderDetail.getProductDetailId()).get();
                productDetail.setQuantity(productDetail.getQuantity() + orderDetail.getQuantity());
                productDetailRepo.update(orderDetail.getProductDetailId(), productDetail);
            }
            orderDetailRepo.deleteByOrderId(orderId);
            orderRepo.deleteById(orderId);
            this.orderViewModelList = orderRepo.findAllPendingOrder();
            this.showOrderList();
            this.orderDetailList = null;
            this.tblModelCart.setRowCount(0);
            this.productViewModelList = productDetailRepo.findAllProduct(null, null);
            this.showProductList();
            JOptionPane.showMessageDialog(this, "Hủy hóa đơn thành công");
        }
    }

    private void createOrder() throws Exception {
        Double total = 0.0;
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            total += (Double) tblGioHang.getValueAt(i, 5) * (Integer) tblGioHang.getValueAt(i, 4);
        }
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setCode("HD" + System.currentTimeMillis());
        order.setCustomerId(customer.getId());
        order.setStaffId(staffCurrentLogin.getId());
        order.setTotal(total);
        order.setStatus("Chưa thanh toán");
        orderRepo.save(order);
        JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công");
        this.orderViewModelList = orderRepo.findAllPendingOrder();
        this.showOrderList();
    }

    private void selectCustomer() throws Exception {
        CustomerRepo customerRepo = new CustomerRepoImpl();
        List<Customer> customerList = customerRepo.findAll();
        KhachHangDialog khachHangDialog = new KhachHangDialog(customerList);
        khachHangDialog.setVisible(true);
        Customer customer = khachHangDialog.getSelectedCustomer();
        this.customer = customer;
        if (customer != null) {
            txtMaKH.setText(String.valueOf(customer.getCode()));
            txtTenKH.setText(customer.getName());
        }
    }

    private void removeProduct() throws SQLException {
        int row = tblGioHang.getSelectedRow();
        if (row >= 0) {
            int id = (int) tblGioHang.getValueAt(row, 0);
            orderDetailRepo.deleteById(id);
            int productDetailId = (int) tblGioHang.getValueAt(row, 2);
            int quantity = Integer.parseInt(tblGioHang.getValueAt(row, 4).toString());
            ProductDetail productDetail = productDetailRepo.findById(productDetailId).get();
            productDetail.setQuantity(productDetail.getQuantity() + quantity);
            productDetailRepo.update(productDetailId, productDetail);
            this.productViewModelList = productDetailRepo.findAllProduct(null, null);
            this.showProductList();
            tblModelCart.removeRow(row);
            JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công");
        }
    }

    private void showCartTable() throws Exception {
        Integer orderId = null;
        if (tblHoaDon.getSelectedRow() > -1) {
            orderId = Integer.parseInt(tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0).toString());
        }
        this.orderDetailList = orderDetailRepo.findAllByCondition(orderId);
        tblModelCart.setRowCount(0);
        for (OrderDetail detail : orderDetailList) {
            tblModelCart.addRow(new Object[]{
                detail.getId(),
                detail.getOrderId(),
                detail.getProductDetailId(),
                detail.getProductName(),
                detail.getQuantity(),
                detail.getPrice()
            });
        }
    }
    private void addToCart() throws Exception {
        if (tblHoaDon.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hóa đơn");
            return;
        }
        String quantity = JOptionPane.showInputDialog("Nhập số lượng");
        if (quantity != null && !quantity.isEmpty()) {
            if (!quantity.matches("[0-9]+")) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên");
                return;
            }
            int row = tblSanPham.getSelectedRow();
            int productDetailId = (int) tblSanPham.getValueAt(row, 0);
            int quantityInput = Integer.parseInt(quantity);
            int quantityInStock = (int) tblSanPham.getValueAt(row, 5);
            if (quantityInput > quantityInStock) {
                JOptionPane.showMessageDialog(this, "Số lượng không đủ");
                return;
            }
            int orderId = (int) tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0);
            tblSanPham.setValueAt(quantityInStock - quantityInput, row, 5);
            double price = Double.parseDouble(tblSanPham.getValueAt(row, 6).toString());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProductDetailId(productDetailId);
            orderDetail.setQuantity(quantityInput);
            orderDetail.setPrice(price);
            orderDetail.setOrderId(orderId);
            orderDetailRepo.save(orderDetail);
            this.showCartTable();
            ProductDetail productDetail = productDetailRepo.findById(productDetailId).get();
            productDetail.setQuantity(quantityInStock - quantityInput);
            productDetailRepo.update(productDetailId, productDetail);
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào giỏ hàng thành công");
            // cập nhật lại order
            Order order = orderRepo.findById(orderId).get();
            order.setTotal(order.getTotal() + price * quantityInput);
            orderRepo.update(orderId, order);
            this.orderViewModelList = orderRepo.findAllPendingOrder();
            this.showOrderList();
        }

    }

    private void onChangeCategory() {
        String category = Objects.requireNonNull(jComboBox4.getSelectedItem()).toString();
        String name = txtTimKiem.getText();
        try {
            productViewModelList = productDetailRepo.findAllProduct(category, name);
            showProductList();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void onSearchProduct() {
        String category = jComboBox4.getSelectedItem().toString();
        String name = txtTimKiem.getText();
        try {
            productViewModelList = productDetailRepo.findAllProduct(category, name);
            showProductList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initCategory() {
        categoryList = categoryRepo.findAll();
        categoryList.forEach(category -> {
            jComboBox4.addItem(category.getName());
        });
    }

    private void showProductList() {
        tblModel.setRowCount(0);
        productViewModelList.forEach(productViewModel -> {
            tblModel.addRow(new Object[]{
                productViewModel.getId(),
                productViewModel.getName(),
                productViewModel.getSize(),
                productViewModel.getColor(),
                productViewModel.getModel(),
                productViewModel.getQuantity(),
                productViewModel.getPrice(),
                productViewModel.getCategory()
            });
        });
    }

    private void showOrderList() {
        DefaultTableModel tblModel = (DefaultTableModel) tblHoaDon.getModel();
        tblModel.setRowCount(0);
        orderViewModelList.forEach(orderViewModel -> {
            tblModel.addRow(new Object[]{
                orderViewModel.getId(),
                orderViewModel.getCode(),
                orderViewModel.getStaffName(),
                orderViewModel.getCustomerName(),
                orderViewModel.getOrderDate(),
                orderViewModel.getTotal(),
                orderViewModel.getStatus()
            });
        });
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
        Panel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cbbKM = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JLabel();
        btnChonKH = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnTaoHD = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnHuyHD = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtMaHD = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        txtTongTien1 = new javax.swing.JTextField();
        txtTienThua = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();

        Panel1.setBackground(new java.awt.Color(255, 255, 153));
        Panel1.setPreferredSize(new java.awt.Dimension(950, 760));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            null,
            new String[]{
                "ID", "Mã Hóa Đơn", "Nhân Viên", "Khách Hàng", "Ngày Tạo", "Tổng Tiền", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tblHoaDon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoaDonMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                    .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblGioHang.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblGioHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblGioHangFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                tblGioHangFocusLost(evt);
            }
        });
        jScrollPane4.setViewportView(tblGioHang);

        jButton4.setBackground(new java.awt.Color(204, 153, 0));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setText("Xóa Sản Phẩm");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(586, 586, 586)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 10, Short.MAX_VALUE))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4)
                    .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String[]{
                "MaSp", "TenSp", "Size", "Mau", "Hang", "DanhMuc", "Số lượng", "Giá bán"
            }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tblSanPham.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSanPham.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblSanPham);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Tìm Kiếm :");

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jComboBox4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>());
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Danh Mục :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jScrollPane3)
                            .addContainerGap())
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addGap(18, 18, 18)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16)
                            .addGap(38, 38, 38)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Giỏ Hàng");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Hóa Đơn Chờ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Danh Sách Sản Phẩm");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Khuyến mại");

        cbbKM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbbKM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Mã Khách Hàng :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Tên Khách Hàng :");

        txtMaKH.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaKH.setText("             ");

        txtTenKH.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenKH.setText("             ");

        btnChonKH.setBackground(new java.awt.Color(204, 153, 0));
        btnChonKH.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnChonKH.setText("Chọn");
        btnChonKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(18, 18, 18)
                            .addComponent(txtTenKH)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnChonKH)))
                    .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtMaKH)
                        .addComponent(btnChonKH))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtTenKH))
                    .addGap(23, 23, 23))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Mã Hóa Đơn :");

        btnTaoHD.setBackground(new java.awt.Color(204, 153, 0));
        btnTaoHD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnTaoHD.setText("Tạo Hóa Đơn");
        btnTaoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHDActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Tổng Tiền :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Thanh Toán :");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Tiền Khách Đưa :");

        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Tiền Thừa :");

        btnHuyHD.setBackground(new java.awt.Color(204, 153, 0));
        btnHuyHD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnHuyHD.setText("Hủy Hóa Đơn");
        btnHuyHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHDActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 153, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setText("Thanh Toán");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtMaHD.setText("            ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                            .addComponent(btnTaoHD))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(18, 18, Short.MAX_VALUE)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTongTien1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(btnHuyHD, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtMaHD))
                        .addComponent(btnTaoHD))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtTongTien1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(16, 16, 16)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(29, 29, 29)
                    .addComponent(btnHuyHD, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(26, 26, 26)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout Panel1Layout = new javax.swing.GroupLayout(Panel1);
        Panel1.setLayout(Panel1Layout);
        Panel1Layout.setHorizontalGroup(
            Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(Panel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17)
                            .addGap(18, 18, 18)
                            .addComponent(cbbKM, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(Panel1Layout.createSequentialGroup()
                            .addGroup(Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(Panel1Layout.createSequentialGroup()
                                    .addGroup(Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(12, 12, 12)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 12, Short.MAX_VALUE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        Panel1Layout.setVerticalGroup(
            Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(Panel1Layout.createSequentialGroup()
                            .addGroup(Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(Panel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel1))
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel17)
                                .addComponent(cbbKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(Panel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(Panel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMousePressed
        // TODO add your handling code here:


    }//GEN-LAST:event_tblHoaDonMousePressed

    private void tblGioHangFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblGioHangFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_tblGioHangFocusGained

    private void tblGioHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblGioHangFocusLost


    }//GEN-LAST:event_tblGioHangFocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged

    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnHuyHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHDActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnHuyHDActionPerformed

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased

    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void btnTaoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTaoHDActionPerformed

    private void btnChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKHActionPerformed

    }//GEN-LAST:event_btnChonKHActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel1;
    private javax.swing.JButton btnChonKH;
    private javax.swing.JButton btnHuyHD;
    private javax.swing.JButton btnTaoHD;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbKM;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JLabel txtMaHD;
    private javax.swing.JLabel txtMaKH;
    private javax.swing.JLabel txtTenKH;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongTien;
    private javax.swing.JTextField txtTongTien1;
    // End of variables declaration//GEN-END:variables


}

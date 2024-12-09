package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderViewModel {
    private Integer id;
    private String code;
    private String staffName;
    private String customerName;
    private Date orderDate;
    private Double total;
    private String status;

    public OrderViewModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static List<OrderViewModel> mapToList(ResultSet resultSet) throws SQLException {
       List<OrderViewModel> list = new ArrayList<>();
        while (resultSet.next()) {
            OrderViewModel order = new OrderViewModel();
            order.setId(resultSet.getInt("ID"));
            order.setCode(resultSet.getString("MAHD"));
            order.setStaffName(resultSet.getString("TENNHANVIEN"));
            order.setCustomerName(resultSet.getString("TENKHACHHANG"));
            order.setOrderDate(resultSet.getDate("NGAYTAO"));
            order.setTotal(resultSet.getDouble("TONGTIEN"));
            order.setStatus(resultSet.getString("TRANGTHAI"));
            list.add(order);
        }
        return list;
    }
}

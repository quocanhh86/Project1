package entity;

import annotation.Column;
import annotation.Id;
import annotation.Table;

import java.util.Date;

@Table(name = "HOADON")
public class Order {
    @Column(name = "HOADONID")
    @Id
    private Integer id;
    @Column(name = "MAHOADON")
    private String code;
    @Column(name = "NGAYTAO")
    private Date orderDate;
    @Column(name = "TRANGTHAI")
    private String status;
    @Column(name = "KHACHHANGID")
    private Integer customerId;
    @Column(name = "NHANVIENID")
    private Integer staffId;
    @Column(name = "TONGTIEN")
    private Double total;

    public Order() {
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}

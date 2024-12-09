package entity;

import annotation.Column;
import annotation.Id;
import annotation.Table;

@Table(name = "HOADONCHITIET")
public class OrderDetail {
    @Column(name = "HOADONCHITIETID")
    @Id
    private Integer id;
    @Column(name = "HOADONID")
    private Integer orderId;
    @Column(name = "CHITIETSANPHAMID")
    private Integer productDetailId;
    @Column(name = "SOLUONG")
    private Integer quantity;
    @Column(name = "DONGIA")
    private Double price;

    private String productName;

    public OrderDetail() {
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(Integer productDetailId) {
        this.productDetailId = productDetailId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}

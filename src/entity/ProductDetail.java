package entity;

import annotation.Column;
import annotation.Id;
import annotation.Table;

@Table(name = "CHITIETSANPHAM")
public class ProductDetail {
    @Id
    @Column(name = "CHITIETSANPHAMID")
    private Integer id;
    @Column(name = "TENCHITIETSANPHAM")
    private String name;
    @Column(name = "SIZEID")
    private Integer sizeId;
    @Column(name = "MAUSACID")
    private Integer colorId;
    @Column(name = "SANPHAMID")
    private Integer productId;
    @Column(name = "GIABAN")
    private Double price;
    @Column(name = "SOLUONGTON")
    private Integer quantity;

    public ProductDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

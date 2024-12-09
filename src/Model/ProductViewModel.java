/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin
 */
public class ProductViewModel {
    private Integer id;
    private String name;
    private String size;
    private String color;
    private String model;
    private Integer quantity;
    private Double price;
    private String category;

    public ProductViewModel() {
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static List<ProductViewModel> mapToList(ResultSet rs) throws SQLException {
        List<ProductViewModel> list = new ArrayList<>();
        while (rs.next()) {
            ProductViewModel product = new ProductViewModel();
            product.setId(rs.getInt("MASP"));
            product.setName(rs.getString("TENSP"));
            product.setSize(rs.getString("SIZE"));
            product.setColor(rs.getString("MAUSAC"));
            product.setModel(rs.getString("KIEUDANG"));
            product.setQuantity(rs.getInt("SOLUONGTON"));
            product.setPrice(rs.getDouble("GIABAN"));
            product.setCategory(rs.getString("DANHMUC"));
            list.add(product);
        }
        return list;
    }
}

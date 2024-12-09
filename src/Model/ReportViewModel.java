package Model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportViewModel {
    private int productDetailId;
    private String productName;
    private int quantity;
    private double price;

    public ReportViewModel() {
    }

    public ReportViewModel(int productDetailId, String productName, int quantity, double price) {
        this.productDetailId = productDetailId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(int productDetailId) {
        this.productDetailId = productDetailId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static List<ReportViewModel> mapToList(ResultSet rs) throws Exception {
        List<ReportViewModel> list = new ArrayList<>();
        while (rs.next()) {
            ReportViewModel reportViewModel = new ReportViewModel();
            reportViewModel.setProductDetailId(rs.getInt("PRODUCT_DETAIL_ID"));
            reportViewModel.setProductName(rs.getString("PRODUCT_NAME"));
            reportViewModel.setQuantity(rs.getInt("QUANTITY"));
            reportViewModel.setPrice(rs.getDouble("PRICE"));
            list.add(reportViewModel);
        }
        return list;
    }
}

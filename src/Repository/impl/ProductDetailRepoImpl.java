package Repository.impl;

import Model.ProductViewModel;
import Repository.ProductDetailRepo;
import Utils.DatabaseUtils;
import entity.ProductDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDetailRepoImpl extends AbstractBaseRepo<ProductDetail, Integer> implements ProductDetailRepo{
    @Override
    public List<ProductViewModel> findAllProduct(String category,String name) throws SQLException {
        PreparedStatement pstm = super.connection.prepareStatement(findAllQuery);
        if (category != null && !category.isEmpty()) {
            pstm.setString(1, category);
        } else {
            pstm.setString(1, "%");
        }
        if (name != null && !name.isEmpty() || name != null && !name.isBlank()) {
            pstm.setString(2, "%" + name + "%");
        } else {
            pstm.setString(2, "%");
        }
        ResultSet rs = pstm.executeQuery();
        DatabaseUtils.printQueryLog(findAllQuery);
        return ProductViewModel.mapToList(rs);
    }
}

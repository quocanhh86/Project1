package Repository.impl;

import Model.ReportViewModel;
import Repository.OrderDetailRepo;
import Utils.DatabaseUtils;
import entity.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDetailRepoImpl extends AbstractBaseRepo<OrderDetail, Integer> implements OrderDetailRepo {

    @Override
    public List<OrderDetail> findAllByCondition(Integer orderId) throws Exception {
        List<OrderDetail> entities = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(queryFindAllByCondition);
        preparedStatement.setObject(1, orderId, Types.INTEGER);
        preparedStatement.setObject(2, orderId, Types.INTEGER);
        ResultSet resultSet = preparedStatement.executeQuery();
        DatabaseUtils.printQueryLog(queryFindAllByCondition);
        while (resultSet.next()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(resultSet.getInt("ID"));
            orderDetail.setOrderId(resultSet.getInt("ORDER_ID"));
            orderDetail.setProductDetailId(resultSet.getInt("PRODUCT_DETAIL_ID"));
            orderDetail.setProductName(resultSet.getString("PRODUCT_NAME"));
            orderDetail.setQuantity(resultSet.getInt("QUANTITY"));
            orderDetail.setPrice(resultSet.getDouble("PRICE"));
            entities.add(orderDetail);
        }
        return entities;
    }

    @Override
    public void deleteByOrderId(Integer orderId) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(queryDeleteByOrderId);
        preparedStatement.setInt(1, orderId);
        preparedStatement.executeUpdate();
    }

    @Override
    public Object[] report(String fromDate, String toDate) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(queryReport);
        preparedStatement.setObject(1, fromDate, Types.VARCHAR);
        preparedStatement.setObject(2, fromDate, Types.VARCHAR);
        preparedStatement.setObject(3, toDate, Types.VARCHAR);
        preparedStatement.setObject(4, toDate, Types.VARCHAR);
        ResultSet resultSet = preparedStatement.executeQuery();
        DatabaseUtils.printQueryLog(queryReport);
        if (resultSet.next()) {
            return new Object[]{resultSet.getDouble(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4)};
        }
        return null;
    }

    @Override
    public List<ReportViewModel> reportDetail(String fromDate, String toDate) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(queryReportDetail);
        preparedStatement.setObject(1, fromDate, Types.VARCHAR);
        preparedStatement.setObject(2, fromDate, Types.VARCHAR);
        preparedStatement.setObject(3, toDate, Types.VARCHAR);
        preparedStatement.setObject(4, toDate, Types.VARCHAR);
        ResultSet resultSet = preparedStatement.executeQuery();
        DatabaseUtils.printQueryLog(queryReportDetail);
        return ReportViewModel.mapToList(resultSet);
    }

    @Override
    public Object[] report1(Date date, Date date0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}

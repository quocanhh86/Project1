package Repository.impl;

import Model.OrderViewModel;
import Repository.OrderRepo;
import Utils.DatabaseUtils;
import entity.Order;

import java.sql.PreparedStatement;
import java.util.List;

public class OrderRepoImpl extends AbstractBaseRepo<Order, Integer> implements OrderRepo {
    @Override
    public List<OrderViewModel> findAllPendingOrder() throws Exception {
        PreparedStatement pstm = super.connection.prepareStatement(statusPendingQuery);
        DatabaseUtils.printQueryLog(statusPendingQuery);
        return OrderViewModel.mapToList(pstm.executeQuery());
    }

    @Override
    public List<OrderViewModel> findAllOrder() throws Exception {
        PreparedStatement pstm = super.connection.prepareStatement(statusAllQuery);
        DatabaseUtils.printQueryLog(statusAllQuery);
        return OrderViewModel.mapToList(pstm.executeQuery());
    }

    @Override
    public List<OrderViewModel> findByCondition(String fromDate, String toDate) throws Exception {
        String query = queryByCondition;
        if (fromDate != null && toDate != null) {
            query += " WHERE HD.NGAYTAO BETWEEN ? AND ?";
        }
        PreparedStatement pstm = super.connection.prepareStatement(query);
        if (fromDate != null && toDate != null) {
            pstm.setString(1, fromDate);
            pstm.setString(2, toDate);
        }
        DatabaseUtils.printQueryLog(query);
        return OrderViewModel.mapToList(pstm.executeQuery());
    }
}

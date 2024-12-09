package Repository;

import Model.ReportViewModel;
import entity.OrderDetail;
import java.util.Date;

import java.util.List;

public interface OrderDetailRepo extends BaseRepo<OrderDetail, Integer> {
    String queryFindAllByCondition = "SELECT hdct.CHITIETSANPHAMID ID,hdct.HOADONID ORDER_ID,hdct.CHITIETSANPHAMID PRODUCT_DETAIL_ID,ctsp.TENCHITIETSANPHAM PRODUCT_NAME,\n" +
        "hdct.SOLUONG QUANTITY, hdct.DONGIA PRICE\n" +
        "FROM HOADONCHITIET hdct \n" +
        "INNER JOIN CHITIETSANPHAM ctsp ON ctsp.CHITIETSANPHAMID = hdct.CHITIETSANPHAMID\n" +
        "WHERE (? is null or HOADONID = ?)";
    String queryDeleteByOrderId = "DELETE FROM HOADONCHITIET WHERE HOADONID = ?";

    String queryReport = "SELECT sum(hdct.DONGIA * hdct.SOLUONG), sum (hdct.SOLUONG) ,sum(hd.HOADONID), count(distinct hd.KHACHHANGID) FROM HOADONCHITIET hdct\n" +
        "inner join HOADON hd On hd.HOADONID = hdct.HOADONID and  hd.TRANGTHAI like N'Đã thanh toán'\n" +
        "WHERE (? is null or ? >= hd.NGAYTAO)\n" +
        "AND (? is null OR ? <= hd.NGAYTAO)";

    String queryReportDetail = "select ctsp.CHITIETSANPHAMID PRODUCT_DETAIL_ID, ctsp.TENCHITIETSANPHAM PRODUCT_NAME, sum(hdct.SOLUONG) QUANTITY, ctsp.GIABAN PRICE from HOADONCHITIET hdct \n" +
        "left join HOADON hd on hdct.HOADONID = hd.HOADONID\n" +
        "left join CHITIETSANPHAM ctsp on ctsp.CHITIETSANPHAMID = hdct.CHITIETSANPHAMID\n" +
        "WHERE (? is null or ? >= hd.NGAYTAO)\n" +
        "AND (? is null OR ? <= hd.NGAYTAO)\n" +
        "GROUP BY ctsp.CHITIETSANPHAMID,ctsp.TENCHITIETSANPHAM,ctsp.GIABAN \n";

    List<OrderDetail> findAllByCondition(Integer orderId) throws Exception;

    void deleteByOrderId(Integer orderId) throws Exception;

    Object[] report(String fromDate, String toDate) throws Exception;

    List<ReportViewModel> reportDetail(String fromDate, String toDate) throws Exception;

    public Object[] report1(Date date, Date date0);
}

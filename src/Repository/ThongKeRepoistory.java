/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

/**
 *
 * @author Admin
 */
import Model.ReportViewModel;
import java.sql.*;
import java.util.ArrayList;

public class ThongKeRepoistory {

    public ArrayList<ReportViewModel> getList(Date tuNgay, Date denNgay) {
        String sql = "SELECT HDCT.CHITIETSANPHAMID, CTSP.TENCHITIETSANPHAM, SUM(HDCT.SOLUONG), CTSP.GIABAN\n"
                + "FROM HOADONCHITIET HDCT \n"
                + "LEFT JOIN HOADON HD ON HDCT.HOADONID = HD.HOADONID\n"
                + "LEFT JOIN CHITIETSANPHAM CTSP ON CTSP.CHITIETSANPHAMID = HDCT.CHITIETSANPHAMID\n"
                + "WHERE ( ? IS NULL OR ? >= HD.NGAYTAO)\n"
                + "AND ( ? IS NULL OR ? <= HD.NGAYTAO)\n"
                + "GROUP BY CTSP.CHITIETSANPHAMID";
        ArrayList<ReportViewModel> ls = new ArrayList<>();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, tuNgay);
            ps.setObject(2, denNgay);
            ps.setObject(3, tuNgay);
            ps.setObject(4, denNgay);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer chiTietSanPhamId = rs.getInt("CHITIETSANPHAMID");
                String tenChiTietSanPham = rs.getString("TENCHITIETSANPHAM");
                Integer soLuong = rs.getInt("SOLUONG");
                Float giaBan = rs.getFloat("GIABAN");
                ReportViewModel model = new ReportViewModel(chiTietSanPhamId, tenChiTietSanPham, soLuong, giaBan);
                ls.add(model);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ls;
    }
}

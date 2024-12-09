/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

/**
 *
 * @author Admin
 */
import Model.HoaDonViewModel;
import Model.ProductViewModel;
import java.sql.*;
import java.util.ArrayList;

public class HoaDonRepository {
    DbConnection dbc;
    public ArrayList<HoaDonViewModel> getListHD(){
        String sql = "SELECT HOADONID, TENKHACHHANG, NGAYTAO, TRANGTHAI FROM HOADON AS HD INNER JOIN KHACHHANG AS KH ON HD.KHACHHANGID = KH.KHACHHANGID \n" +
"WHERE TRANGTHAI = N'CHƯA THANH TOÁN'";
        ArrayList<HoaDonViewModel> list = new ArrayList<>();
        try(Connection conn = dbc.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maHoaDon = rs.getInt("HOADONID");
                String tenKH = rs.getString("TENKHACHHANG");
                Date ngayTao = rs.getDate("NGAYTAO");
                String trangThai = rs.getString("TRANGTHAI");
                HoaDonViewModel hdvm = new HoaDonViewModel(maHoaDon, tenKH, ngayTao, trangThai);
                list.add(hdvm);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<ProductViewModel> getListSP(){
//        String sql = "SELECT TENSANPHAM, KIEUDANG, SIZE, GIABAN, SOLUONGTON FROM KIEUDANG AS KD INNER JOIN (SANPHAM AS SP INNER JOIN (CHITIETSANPHAM AS CTSP INNER JOIN SIZE AS S ON S.SIZEID = CTSP.SIZEID)  ON SP.SANPHAMID = CTSP.SANPHAMID) ON SP.KIEUDANGID = KD.KIEUDANGID ";
//        ArrayList<ProductViewModel> list = new ArrayList<>();
//        try(Connection conn = dbc.getConnection();
//                PreparedStatement ps = conn.prepareStatement(sql)){
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                String tenSanPham = rs.getString("TENSANPHAM");
//                String kieuDang = rs.getString("KIEUDANG");
//                String size = rs.getString("SIZE");
//                Float giaBan = rs.getFloat("GIABAN");
//                Integer soLuong = rs.getInt("SOLUONGTON");
//                ProductViewModel spvm = new ProductViewModel(tenSanPham, kieuDang, size, giaBan, soLuong);
//                list.add(spvm);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return list;
        return null;
    }
   
}

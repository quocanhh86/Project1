package Repository;

import Model.ProductViewModel;
import entity.ProductDetail;

import java.sql.SQLException;
import java.util.List;

public interface ProductDetailRepo extends BaseRepo<ProductDetail, Integer> {
    String findAllQuery = "SELECT DISTINCT * FROM (\n" +
        "\tSELECT CTSP.CHITIETSANPHAMID MASP,CTSP.TENCHITIETSANPHAM TENSP,S.SIZE SIZE,M.MAUSAC,KD.KIEUDANG,CTSP.SOLUONGTON,CTSP.GIABAN,DM.TENDANHMUC DANHMUC\n" +
        "\tFROM CHITIETSANPHAM CTSP\n" +
        "\tJOIN SANPHAM SP ON SP.SANPHAMID = CTSP.SANPHAMID\n" +
        "\tLEFT JOIN SIZE S ON S.SIZEID = CTSP.SIZEID\n" +
        "\tLEFT JOIN MAUSAC M ON M.MAUSACID = CTSP.MAUSACID\n" +
        "\tLEFT JOIN KIEUDANG KD ON KD.KIEUDANGID = SP.KIEUDANGID\n" +
        "\tLEFT JOIN DANHMUC DM ON DM.DANHMUCID = SP.DANHMUCID\n" +
        "\tWHERE DM.TENDANHMUC LIKE ? AND CTSP.TENCHITIETSANPHAM LIKE ?\n" +
        ") DATA_TABLE \n" +
        "\n" +
        "\n";
    List<ProductViewModel> findAllProduct(String category,String name) throws SQLException;

}

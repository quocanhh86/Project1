/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.HoaDonViewModel;
import Model.ProductViewModel;
import Repository.HoaDonRepository;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class QuanLyHoaDonService {
    HoaDonRepository hdr = new HoaDonRepository();
    
    public ArrayList<HoaDonViewModel> getListHD(){
        return hdr.getListHD();
    }
    
    public ArrayList<ProductViewModel> getListSP(){
        return hdr.getListSP();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Admin
 */
public class XImages {
    public static Image getAppIcon(){
        URL url = XImages.class.getResource("/icon/Sneaker.png");
        return new ImageIcon(url).getImage();
    }
}

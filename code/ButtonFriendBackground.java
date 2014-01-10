/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class ButtonFriendBackground extends Thread{
    /* properties */
    private JButton butt = null;
    private String id = null;
    private BufferedImage image = null;
    
    public ButtonFriendBackground(JButton butt, String id){
        this.butt = butt;
        this.id = id;
    }
    
    @Override
    public void run(){
        try{
            URL url = new URL("http://graph.facebook.com/" + id + "/picture?width=150&height=150");
            URLConnection urlc = url.openConnection();
            image = ImageIO.read(urlc.getInputStream());
            ImageIcon img = new ImageIcon(image);
            butt.setIcon(img);
            butt.setHorizontalTextPosition(AbstractButton.CENTER);
            butt.setVerticalTextPosition(AbstractButton.CENTER);
            butt.setFont(new Font("Segoe UI", Font.BOLD, 0));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

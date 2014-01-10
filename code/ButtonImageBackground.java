/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package code;
import com.restfb.FacebookClient;
import com.restfb.types.Photo;
import com.restfb.types.Photo.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class ButtonImageBackground extends Thread{
    /* properties */
    private FacebookClient facebookClient = null;
    private JButton butt =  null;
    private String photoId = null;
    private Photo cover = null;
    private int index = 3;
    public List HighResolution = new LinkedList(); // for storing highest resolution available
    public List MediumResolution = new LinkedList();  // for storing the second highest resolution made available
    public static long count = 0;
    
    public ButtonImageBackground(FacebookClient facebookClient, JButton butt, String photoId){
        this.facebookClient = facebookClient;
        this.butt = butt;
        this.photoId = photoId;
    }
    
    @Override
    public void run(){
        try{
            cover = facebookClient.fetchObject(photoId, Photo.class);
            Image cover_pic = (cover.getImages()).get(index);
            // fetch image
            URL url = new URL(cover_pic.getSource());
            URLConnection urlc = url.openConnection();
            BufferedImage img = ImageIO.read(urlc.getInputStream());
            ImageIcon icon = new ImageIcon(img);
            if(icon.getIconWidth() < 150 || icon.getIconHeight() < 150){
                throw new Exception("Small Size");
            }
            butt.setBackground(Color.BLACK);
            butt.setIcon(icon);
            butt.setHorizontalTextPosition(AbstractButton.CENTER);
            butt.setVerticalTextPosition(AbstractButton.CENTER);
            butt.setFont(new Font("Segoe UI", Font.BOLD, 0));
            count = count + 1;
        }
        catch(Exception e){
            index --;
            if(index >= 0 && e.getMessage().equals("Small Size"))
                run();
            e.printStackTrace();
        }
        finally{
            Image cover_pic = (cover.getImages()).get(index);
            // store in the list
            HighResolution.add(((Image)(cover.getImages()).get(0)).getSource());
            try{
                MediumResolution.add(((Image)(cover.getImages()).get(1)).getSource());
            }
            catch(Exception e){ }
        }
    }
}

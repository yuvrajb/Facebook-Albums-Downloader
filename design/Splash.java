/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package design;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import javax.imageio.ImageIO;

/**
 *
 * @author Yuvraj Singh Babrah
 */
class Splash extends javax.swing.JWindow{
    /* properties */
    BufferedImage img = null;
    
    public Splash(){
        try{
            String protected_path = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
            protected_path = protected_path.substring(protected_path.indexOf("file:/") + 6, protected_path.length());
            File check_file = new File(protected_path);       
            String parent_name = check_file.getParentFile().getName();
            String parent_path = protected_path.substring(0, protected_path.indexOf(parent_name) + parent_name.length());
            parent_path = URLDecoder.decode(parent_path, "UTF-8");
            img = ImageIO.read(new File(parent_path + "/lib/resources/splash.jpg"));
            this.setSize(800, 523);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
            paint(this.getGraphics());
            Thread.sleep(3000);
            this.dispose();
        }
        catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void paint(Graphics g){
        g.drawImage(img, 0, 0, this);
    }
}

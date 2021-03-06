/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package code;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Album;
import com.restfb.types.Photo;
import design.Skeleton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class AlbumPhotos extends Thread implements ActionListener{
    /* properties */
    private FacebookClient facebookClient = null;
    private Connection<Photo> AlbumPhotos = null;
    private List AlbumPhotosList = null;
    private Iterator itr = null;
    public static String albumId = null;
    public static List PhotoIds = new LinkedList();
    public static long count = 0;
    public static int flag = 0;  
    public static List SelectIds = new ArrayList();
    public static List SelectedButtons = new ArrayList();
    
    public AlbumPhotos(FacebookClient facebookClient, String albumId){
        this.facebookClient = facebookClient;
        this.albumId = albumId;
        this.flag = 1; // raise flag
    }
    
    @Override
    public void run(){
        try{
            PhotoIds.clear(); // clear list
            Album temp = facebookClient.fetchObject(albumId, Album.class);
            this.count = temp.getCount();
            AlbumPhotos = facebookClient.fetchConnection(albumId + "/photos", Photo.class, Parameter.with("limit", count));
            AlbumPhotosList = AlbumPhotos.getData();
            itr = AlbumPhotosList.iterator();
            /* set up buttons */
            int count = AlbumPhotosList.size();
            JButton[] butt = new JButton[count];
            /* positions */
            double x = 8.6;
            double y = 8.6;
            int mulx = 1;
            int muly = 1;
            int index = 0;
            while(itr.hasNext()){
                try{
                    String imageId = ((Photo)itr.next()).getId();
                    PhotoIds.add(imageId); // store the picture ids ** important
                    butt[index] = new JButton(imageId);
                    butt[index].setFont(new Font("Arial", Font.PLAIN, 0));
                    butt[index].setSize(150, 150);
                    int posx = (int)Math.ceil(x);
                    int posy = (int)Math.ceil(y);
                    butt[index].setLocation(posx, posy);
                    x += 158.6;
                    if(x + 158.6 > 836){
                        x = 8.6;
                        y += 158.6;
                    }
                    // add default image
                    ImageIcon Icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/photo.png")));
                    butt[index].setIcon(Icon);
                    butt[index].setHorizontalTextPosition(AbstractButton.CENTER);
                    butt[index].setVerticalTextPosition(AbstractButton.CENTER);
                    // add listener
                    butt[index].addActionListener(this);
                    Skeleton.AlbumsPicturePanel.add(butt[index]);
                    index ++;
                    Skeleton.AlbumsPicturePanel.repaint();
                    Skeleton.AlbumsScrollPane.setPreferredSize(new Dimension(836, (int)y + 200));
                    if(itr.hasNext() == false){
                        flag = 0; // lower flag
                        Skeleton.Loader.setVisible(false); // hide loader
                    }
                }
                catch(Exception e){ }
            }
            Skeleton.AlbumsScrollPane.setPreferredSize(new Dimension(836, (int)y + 159 + 100));
            for(int i = 0; i < index; i ++){
                new ButtonImageBackground(facebookClient, butt[i], butt[i].getText()).start();
            }
        }
        catch(Exception e){
            flag = 0; // lower flag
            Skeleton.Loader.setVisible(false); // hide loader
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent event){
        JButton Temp = ((JButton)event.getSource());
        String Id = ((JButton)event.getSource()).getText(); // fetch id
        if(SelectIds.contains(Id)){ // remove from list
            int x = Temp.getLocation().x;
            int y = Temp.getLocation().y;
            Temp.setSize(150, 150);
            Temp.setLocation(x - 5, y - 5);
            SelectIds.remove(Id);
            SelectedButtons.remove(Temp);
        }
        else{ // add the photo id
            int x = Temp.getLocation().x;
            int y = Temp.getLocation().y;
            Temp.setSize(140, 140);
            Temp.setLocation(x + 5, y + 5);
            SelectedButtons.add(Temp);
            SelectIds.add(Id);
        }
    }
        
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package code;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.FriendList;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class FriendsList extends Thread implements ActionListener{
    /* properties */
    private FacebookClient facebookClient = null;
    private Connection<FriendList> friendsList = null;
    private JScrollPane FriendsScroll = null;
    private JPanel FriendsPane = null;
    private List friends_list = new LinkedList();
    private Iterator itr = null;
    private List friends_selected = new ArrayList();
    private String id = null;
    
    public FriendsList(FacebookClient facebookClient, String id, Connection<FriendList> friendsList, JScrollPane FriendsScroll, JPanel FriendsPane){
        this.facebookClient = facebookClient;
        this.id = id;
        this.friendsList = friendsList;
        this.FriendsScroll = FriendsScroll;
        this.FriendsPane = FriendsPane;
    }
    
    @Override
    public void run(){
        try{
            friends_list = friendsList.getData();
            itr = friends_list.iterator();
            /* positions */
            double x = 8.6 + 158.6;
            double y = 8.6;
            int mulx = 1;
            int muly = 1;
            int index = 1;
            JButton[] butt = new JButton[friends_list.size()];
            JLabel[] label = new JLabel[friends_list.size()];
            String[] friends_id = new String[friends_list.size()];
            /* set the logged in users button */
            butt[0] = new JButton(id + " " + "me");
            butt[0].setFont(new Font("Arial", Font.PLAIN, 0));
            butt[0].setSize(150, 150);
            butt[0].setLocation(9, 9);
            butt[0].addActionListener(this);
            friends_id[0] = id;
            label[0] = new JLabel("me");
            label[0].setSize(150, 25);
            label[0].setFont(new Font("Segoe UI", Font.BOLD, 12));
            label[0].setLocation(9, 9 + 155);
            FriendsPane.add(butt[0]);
            FriendsPane.add(label[0]);
            while(itr.hasNext()){
                try{ // important for continuing the while loop
                    FriendList temp = (FriendList)itr.next();
                    String name = temp.getName();
                    // obtain id and store in the friend_id List
                    String id = temp.getId();
                    friends_id[index] = id;
                    butt[index] = new JButton(id + " " + name);
                    butt[index].setFont(new Font("Arial", Font.PLAIN, 0));
                    butt[index].setSize(150, 150);
                    label[index] = new JLabel(name);
                    label[index].setSize(150, 25);
                    label[index].setFont(new Font("Segoe UI", Font.BOLD, 12));
                    int posx = (int)Math.ceil(x);
                    int posy = (int)Math.ceil(y);
                    butt[index].setLocation(posx, posy);
                    label[index].setLocation(posx, posy + 155);
                    x += 158.6;
                    if(x + 158.6 > 836){
                        x = 8.6;
                        y += 189;
                    }
                    // add default image
                    ImageIcon Icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/photo.png")));
                    butt[index].setIcon(Icon);
                    butt[index].setHorizontalTextPosition(AbstractButton.CENTER);
                    butt[index].setVerticalTextPosition(AbstractButton.CENTER);
                    // add listener
                    butt[index].addActionListener(this);
                    //  add the buttons to container
                    FriendsPane.add(butt[index]);
                    FriendsPane.add(label[index]);
                    index ++;
                    FriendsPane.repaint();
                }
                catch(Exception e){}
                FriendsScroll.setPreferredSize(new Dimension(836, (int)y + 189));
            }
            // set the prefered size of the FriendsPanel JPanel ** important
            FriendsScroll.setPreferredSize(new Dimension(836, (int)y + 189));
            // set background of each button
            for(int i = 0; i < index; i ++){
                new ButtonFriendBackground(butt[i], friends_id[i]).start(); // thread
            }
        }
        catch(Exception e){ }
    }
    
    @Override
    public void actionPerformed(ActionEvent event){
        // clear the selected photos list
        AlbumPhotos.SelectIds.clear();
        JButton source = (JButton)event.getSource();
        String text = source.getText();
        String[] split = text.split(" ");
        String id = split[0];
        String name = split[1];
        Skeleton.AlbumsList.removeAllItems();
        Skeleton.AlbumsId.removeAllItems();
        Skeleton.AlbumsPicturePanel.removeAll();
        Skeleton.AlbumsPicturePanel.repaint();
        Skeleton.Loader.setVisible(true);
        Skeleton.AlbumsList.addItem("loading albums");
        Skeleton.AlbumsId.addItem("loading ids");
        // remove the old album id
        AlbumPhotos.albumId = null;
        new AlbumsList(facebookClient, id).start(); // thread
    }
}

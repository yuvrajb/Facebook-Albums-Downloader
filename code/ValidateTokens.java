/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package code;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Album;
import com.restfb.types.FriendList;
import com.restfb.types.User;
import design.FetchTokens;
import design.Skeleton;
import easyJSON.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class ValidateTokens extends Thread{
    /* properties */
    private String token = null;
    private JFrame FetchTokensFrame = null;
    private JLabel ValidationStatus = null;
    private easyJSON json = new easyJSON();
    private FacebookClient facebookClient = null;
    private Connection<FriendList> friendsList = null;
    private Connection<Album> albumsList = null;
    
    public ValidateTokens(String token, JFrame FetchTokensFrame, JLabel ValidationStatus){
        this.token = token;
        this.FetchTokensFrame = FetchTokensFrame;
        this.ValidationStatus = ValidationStatus;
    }
    
    @Override
    public void run(){
        try{
            facebookClient = new DefaultFacebookClient(token);
            User user = facebookClient.fetchObject("me", User.class);
            /* fetch complete name and id of the user */
            String firstName = user.getFirstName();
            String middleName = user.getMiddleName();
            String lastName = user.getLastName();
            String id = user.getId();
            /* fetch the freinds list of the user */
            friendsList = facebookClient.fetchConnection("me/friends", FriendList.class);
            /* fetch the albums list of the user */
            albumsList = facebookClient.fetchConnection("me/albums", Album.class);
            /* all set */
            ValidationStatus.setText("all set!!");
            new Skeleton(facebookClient, id, firstName, friendsList, albumsList); // show the next frame
            FetchTokensFrame.setVisible(false);
        }
        catch(Exception e){
            ValidationStatus.setText("authentication failed!! either the token expired or the value is incorrect");
            FetchTokens.flag = false;
        }
    }
}

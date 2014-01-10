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
import easyJSON.*;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class ValidateTokens {
    /* properties */
    private String token = null;
    private easyJSON json = new easyJSON();
    private FacebookClient facebookClient = null;
    private Connection<FriendList> friendsList = null;
    private Connection<Album> albumsList = null;
    
    public ValidateTokens(String token){
        this.token = token;
    }
    
    // send validation result
    public easyJSON validate(){
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
            /* set up json */
            json.add("status", 200);
            json.add("name", firstName);
            json.add("id", id);
        }
        catch(Exception e){
            json.add("status", 500);
        }
        return json;
    }
    
    // send facebookClient object
    public FacebookClient getFacebookClient(){
        return facebookClient;
    }
    
    // send friends list
    public Connection<FriendList> getFriends(){
        return friendsList;
    }
    
    // send albums list
    public Connection<Album> getAlbums(){
        return albumsList;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package code;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.Album;
import design.Skeleton;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class AlbumsList extends Thread{
   /* properties */
    private FacebookClient facebookClient = null;
    private String id = null;
    private Connection<Album> AlbumList = null;
    private List AlbumListList = new LinkedList();
    private ListIterator itr = null;
    private Map albums = new LinkedHashMap();
    
    public AlbumsList(FacebookClient facebookClient, String id){
        this.facebookClient = facebookClient;
        this.id = id;
    }     
    
    @Override
    public void run(){
        try{
            AlbumList = facebookClient.fetchConnection(id + "/albums", Album.class);
            AlbumListList = AlbumList.getData();
            itr = AlbumListList.listIterator();
            Skeleton.AlbumsList.removeAllItems();
            Skeleton.AlbumsId.removeAllItems();
            long firstCount = 0;
            int index = 0;
            while(itr.hasNext()){
                Album temp = (Album)itr.next();
                if(index == 0){ // important to avoid getting a wrong count only for first album
                    firstCount = temp.getCount();
                    index ++;
                }
                try{
                    //long count = temp.getCount(); // photos in an album
                    albums.put(temp.getId(), temp.getName()); // store in the map
                    Skeleton.AlbumsList.addItem(temp.getName());
                    Skeleton.AlbumsId.addItem(temp.getId());
                    if(itr.hasNext() == false){
                        //count = firstCount;
                        new AlbumPhotos(facebookClient, Skeleton.AlbumsId.getItemAt(0).toString()).start(); // thread
                    }
                }
                catch(Exception e){ }
            }
        }
        catch(Exception e){ }
    }
}

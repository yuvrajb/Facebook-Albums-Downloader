/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package code;

import com.restfb.FacebookClient;
import design.FileChooser;
import design.Skeleton;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class DownloadAlbum extends Thread{
    /* properties */
    private FacebookClient facebookClient = null;
    private int index;
    private String AlbumId = null;
    private boolean HighResolution = false;
    private List PhotoIds = null;
    private Iterator itr = null;
    private String FilePath = null;
            
    public DownloadAlbum(FacebookClient facebookClient, int index, String AlbumId, boolean HighResolution, List PhotoIds){
        this.facebookClient = facebookClient;
        this.index = index;
        this.AlbumId = AlbumId;
        this.HighResolution = HighResolution;
        this.PhotoIds = PhotoIds;
    }
    
    @Override
    public void run(){
        itr = PhotoIds.iterator();
        FilePath = FileChooser.Path;
        if(FilePath == null || FilePath.length() == 0){
            File[] Drives = File.listRoots();
            for(File Curr : Drives){
                if(Curr.canExecute()){
                    FilePath = Curr.toString();
                    new File(FilePath + "Facebook Album Downloader").mkdir();
                    FilePath = FilePath + "Facebook Album Downloader";
                    break;
                }
            }
        }
        Skeleton.DownloadPanel[index].setToolTipText(FilePath);
        while(itr.hasNext()){
            new DownloadPhoto(facebookClient, index, AlbumId, itr.next().toString(), HighResolution, FilePath).start(); // thread
        }
    }
}

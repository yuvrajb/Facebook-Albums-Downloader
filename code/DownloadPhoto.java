/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package code;

import com.restfb.FacebookClient;
import com.restfb.types.Photo;
import com.restfb.types.Photo.Image;
import design.Skeleton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class DownloadPhoto extends Thread{
    /* properties */
    private FacebookClient facebookClient = null;
    private int index;
    private String PhotoId = null;
    private String AlbumId = null;
    private boolean HighResolution = false;
    private String Link = null;
    private String FilePath =  null;
    private File File = null;
    
    public DownloadPhoto(FacebookClient facebookClient, int index, String AlbumId, String PhotoId, boolean HighResolution, String FilePath){
        this.facebookClient = facebookClient;
        this.index = index;
        this.PhotoId = PhotoId;
        this.AlbumId = AlbumId;
        this.HighResolution = HighResolution;
        this.FilePath = FilePath;
    }
    
    @Override
    public void run(){
        if(Skeleton.AlbumsForDownload[index] == false){
            return;
        }
        try{
            Photo Img = facebookClient.fetchObject(PhotoId, Photo.class);
            List Images = Img.getImages();
            if(HighResolution)
                Link = ((Image)Images.get(0)).getSource();
            else{
                try{ // incase average resolution doesn't exists
                    Link = ((Image)Images.get(1)).getSource();
                    if(Link == null)
                        throw new Exception("null");
                }
                catch(Exception e){
                    Link = ((Image)Images.get(0)).getSource();
                }
            }
            // open URL Conenction
            URL Url = new URL(Link);
            URLConnection UrlConnection = Url.openConnection();
            BufferedImage BufferedURLImage = ImageIO.read(UrlConnection.getInputStream());
            if(Skeleton.AlbumsForDownload[index] == false){
                return;
            }
            File File = new File(FilePath + "\\" + PhotoId + ".jpg");
            File.createNewFile();
            ImageIO.write(BufferedURLImage, "jpg", File);
            // on completion
            Map AlbumIdsForDownload = Skeleton.AlbumIdsForDownload;
            Skeleton.decrement(index, AlbumId); // important
        }
        catch(Exception e){
            Skeleton.decrement(index, AlbumId); // important
        }
    }
}

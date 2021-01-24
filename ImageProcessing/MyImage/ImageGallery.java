package MyImage;

import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.io.File;
import java.awt.Graphics;
import javax.imageio.ImageIO;


public class ImageGallery{
    
    // variable and dataTypes
    public static class ImageIndex {    // 這個struct用來表示一個照片的編號，如照片(3, 5)就是    
        public int x;
        public int y;
    }
    enum ImageStatus {
        USER_PRINTED,
        USER_AVAILABLE,
        ADMIN_PRINTED,
        ADMIN_AVAILABLE
    }
    // public static class StatBufferedImage extends BufferedImage
    // {
    //     public StatBufferedImage()
    //     {   

    //     }
    // }

    BufferedImage[][] imageGallery; 
    ImageStatus[][] trackingGallery;    // track status of each small img
    int imageGallery_width;
    int imageGallery_height;

    // Methods
    public void preparePrintImage(){

    }

    private void partition(BufferedImage image) throws IOException
    {
        // getReal width, height
        int width = image.getWidth();
        int height = image.getHeight();
        
        // should be modified
        int small_img_width = 108;
        int small_img_height = 108;

        imageGallery_width = width/small_img_width; 
        imageGallery_height = height/small_img_height;
        
        // space allocation -- index starts from 1
        imageGallery = new BufferedImage[imageGallery_width][];
        trackingGallery = new ImageStatus[imageGallery_width][];
        for(int i = 0; i < imageGallery_height; i++)
        {
            imageGallery[i] = new BufferedImage[imageGallery_height];
            trackingGallery[i] = new ImageStatus[imageGallery_height];
        }

        for(int i = 0; i < imageGallery_width; i++)
        {
            for(int j = 0; j < imageGallery_height; j++)
            {
                // System.out.print("i: "+String.valueOf(i)+" j: "+String.valueOf(j));
                int image_start_x = small_img_width * i, image_start_y = small_img_width * j;                
                BufferedImage img = image.getSubimage(image_start_x, image_start_y, small_img_width, small_img_height);  // fill in the corners of the desired crop location here
                BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics g = copyOfImage.createGraphics();
                g.drawImage(img, 0, 0, null);
                imageGallery[i][j] = copyOfImage;

                // Initialize trackingGallery 
                trackingGallery[i][j] = ImageStatus.ADMIN_AVAILABLE;

                // Test
                // File f = new File("./["+String.valueOf(i)+"]"+"["+String.valueOf(j)+"]"+".jpg");
                // ImageIO.write(img, "jpg", f);
            }
        }   
    }

    // 我們可以用這個function保留自己列印的照片編號，傳入值是一個照片編號的array
    public void adminPartitionsUpdate(ImageIndex[] idxes)
    {
        
    }

    public void userSendprint()
    {

    }

    public void adminSendPrint()
    {

    }

    public ImageGallery(BufferedImage image)  throws IOException // constructor
    {
        // do partition and save subImages into BufferedImage[][]
        partition(image);

        // user partitions update
        // adminPartitionsUpdate();
    }
}
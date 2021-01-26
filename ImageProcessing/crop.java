import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Vector;
import java.awt.Graphics;
import MyImage.*;

public class Crop {
    
    public static void main(String[] args) throws IOException{

        BufferedImage big_img = null, image_in = null;
        big_img = ImageGallery.preparePrintImage("./rex.jpg");
        image_in = ImageGallery.preparePrintImage("./[4][7].jpg");

        
        // Base Image save
        ImageGallery img_gallery = new ImageGallery(big_img);

        // algorithm test
        BufferedImage returnImg = img_gallery.algorithm_BAI(image_in, img_gallery.get_baseImg(1, 7), true, true);
        ImageGallery.stdSaveImg(returnImg, "output.jpg");
    }
}
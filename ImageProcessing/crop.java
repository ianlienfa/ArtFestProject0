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
        ImageGallery img_gallery = new ImageGallery(big_img, 108, 108);

        // algorithm test
        BufferedImage returnImg = img_gallery.algorithm_BAI(image_in, img_gallery.get_baseImg(1, 7), true, true);
        ImageGallery.stdSaveImg(returnImg, "output.jpg");

        // ============================== 彥廷

        File f = null;
        BufferedImage image = null;

        f = new File("./rex1.jpg");
        if(f.exists())
            System.out.println("Loading photo...\n");
        image = ImageIO.read(f);

        ImageGallery img = new ImageGallery(image, 1080, 1080);

        f = new File("./user.jpg");
        BufferedImage img_user = ImageIO.read(f);
        
        BufferedImage img_new = img.getImageGallery(0, 0);
        img_new = img.colorToGray(img_new);
        img_new = img.algorithm_Tim(img_user, img_new, 10, 10);
        f = new File("./output.jpg");
        ImageIO.write(img_new, "jpg", f);


        // ==============================
    }
}
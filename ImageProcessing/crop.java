import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Vector;
import java.awt.Graphics;
import MyImage.*;

public class Crop {
    public static void main(String[] args) throws IOException{
        File f = null;
        BufferedImage image = null;

        f = new File("./rex.jpg");
        if(f.exists())
            System.out.println("Loading photo...\n");
        image = ImageIO.read(f);

        // int width = image.getWidth();
        // int height = image.getHeight();
        // System.out.println("width: "+String.valueOf(width)+" height: "+String.valueOf(height)+"\n");

        // BufferedImage img = image.getSubimage(0, 0, width/2, height/2); //fill in the corners of the desired crop location here
        // BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        // Graphics g = copyOfImage.createGraphics();
        // g.drawImage(img, 0, 0, null);

        // f = new File("./output.jpg");
        // ImageIO.write(img, "jpg", f);

        ImageGallery img = new ImageGallery(image);
    }
}
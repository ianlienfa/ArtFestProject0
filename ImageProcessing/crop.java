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


    // public Example01() throws IOException{
        // //Title our frame.
        // super("Java 2D Example01");
        
        // //Set the size for the frame.
        // setSize(400,300);
        
        // //We need to turn on the visibility of our frame
        // //by setting the Visible parameter to true.
        // setVisible(true);

        // addWindowListener(new WindowAdapter()
        //   {public void windowClosing(WindowEvent e)
        //      {dispose(); System.exit(0);}
        //     }
        // );
        
    // }
    
    //     public void paint(Graphics g) {
    // //Here is how we used to draw a square with width
    // //of 200, height of 200, and starting at x=50, y=50.
    // g.setColor(Color.red);
    // g.drawRect(50,50,200,200);

    // //Let's set the Color to blue and then use the Graphics2D
    // //object to draw a rectangle, offset from the square.
    // //So far, we've not done anything using Graphics2D that
    // //we could not also do using Graphics.  (We are actually
    // //using Graphics2D methods inherited from Graphics.)
    // Graphics2D g2d = (Graphics2D)g;
    // g2d.setColor(Color.blue);
    // g2d.drawRect(75,75,300,200);
    // }
}
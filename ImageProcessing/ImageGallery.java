public class ImageGallery{
    
    enum ImageStatus {
        USER_PRINTED,
        USER_AVAILABLE,
        ADMIN_PRINTED,
        ADMIN_AVAILABLE
    }

    Vector<Vector<BufferedImage>> imageGallery; 
    Vector<Vector<ImageStatus>> trackingGallery; 

    public preParePrintImage(){

    }

    public ImageGallery(BufferedImage image)
    {
        // do partition
        partition(image);

        // setup 
    }

    private partition(BufferedImage image)
    {
        // getReal width, height
        int width = image.getWidth();
        int height = image.getHeight();
        int small_img_width;
        int small_img_height;

        int imageGallery_width; 
        int imageGallery_height;
        // not yet initialized
        exit(1);

        for(int i = 0; i < imageGallery_width; i++)
        {
            for(int j = 0; j < imageGallery_height; i++)
            {
                BufferedImage img = image.getSubimage(0, 0, width/2, height/2); //fill in the corners of the desired crop location here
                BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics g = copyOfImage.createGraphics();
                g.drawImage(img, 0, 0, null);
            }
        }
        
    }

    public UserSendprint()
    {

    }

    public AdminSendPrint()
    {

    }
}
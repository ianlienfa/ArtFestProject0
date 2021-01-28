package com.example.artfestproject0.MyImage;

import org.bytedeco.javacpp.indexer.UByteIndexer;
import org.bytedeco.opencv.opencv_core.*;
import java.io.File;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

public class ImageGallery{

    /*   variable and dataTypes  */
    private Mat[][] imageGallery;
    private ImageStatus[][] trackingGallery;    // track status of each small img
    private int imageGallery_width;
    private int imageGallery_height;
    private static final String DIRPATH = "/Users/linenyan/Coding/ArtFestProject0/app/src/main/java/com/example/artfestproject0/MyImage/";

    public static class ImageIndex {    // 這個struct用來表示一個照片的編號，如照片(3, 5)就是
        public int x;
        public int y;
        public ImageIndex(){}
        public ImageIndex(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
    enum ImageStatus {
        USER_PRINTED,
        USER_AVAILABLE,
        ADMIN_PRINTED,
        ADMIN_AVAILABLE
    }


    /*  Methods  */
    public Mat get_baseImg(int w, int h)
    {
        return imageGallery[w][h];
    }

    public Mat get_baseImg(ImageIndex idx)
    {
        return imageGallery[idx.x][idx.y];
    }

    public ImageStatus get_Imgstatus(ImageIndex idx)
    {
        return trackingGallery[idx.x][idx.y];
    }

    public ImageStatus get_Imgstatus(int w, int h)
    {
        return trackingGallery[w][h];
    }

    public static Mat preparePrintImage(String filename){
        Mat image = imread(filename);
        if (image == null)
        {
            System.out.println("Photo not found.");
            System.exit(-2);
        }
        return image;
    }

    private void partition(Mat image, int small_img_width, int small_img_height)
    {
        // getReal width, height
        int width = image.arrayWidth();
        int height = image.arrayHeight();

        // // should be modified
        // int small_img_width = 108;
        // int small_img_height = 108;

        this.imageGallery_width = width/small_img_width;
        this.imageGallery_height = height/small_img_height;

        // space allocation -- index starts from 1
        imageGallery = new Mat[imageGallery_width][];
        trackingGallery = new ImageStatus[imageGallery_width][];
        for(int i = 0; i < imageGallery_height; i++)
        {
            imageGallery[i] = new Mat[imageGallery_height];
            trackingGallery[i] = new ImageStatus[imageGallery_height];
        }

        for(int i = 0; i < imageGallery_width; i++)
        {
            for(int j = 0; j < imageGallery_height; j++)
            {
                // System.out.print("i: "+String.valueOf(i)+" j: "+String.valueOf(j));
                int image_start_x = small_img_width * i, image_start_y = small_img_width * j;
                Rect rect = new Rect(image_start_x, image_start_y, small_img_width, small_img_height);
                Mat sub = new Mat(image, rect);
                imageGallery[i][j] = sub;

                // Initialize trackingGallery
                trackingGallery[i][j] = ImageStatus.USER_AVAILABLE;

                // Debug
                String destination = (DIRPATH+"["+String.valueOf(i)+"]"+"["+String.valueOf(j)+"]"+".jpg");
                imwrite(destination, sub);
            }
        }
    }

    public static Mat stdLoadImg(String filename)
    {
        String from = (DIRPATH+filename);
        Mat image = imread(from);
        if(image == null)
        {
            System.out.println("stdLoadImg error: img is null.");
            System.exit(-1);
        }
        return image;
    }

    public static void stdSaveImg(Mat img, String filename)
    {
        String destination = (DIRPATH+filename);
        if(img == null)
        {
            System.out.println("stdSaveImg error: img is null.");
            System.exit(-1);
        }
        imwrite(destination, img);
    }

    public void userSendprint()
    {
        // update trackingGallery
    }

    public void adminSendPrint()
    {
        // update trackingGallery
    }

    public ImageIndex[] readImageIndex(String filename)
    {
        Vector<ImageIndex>
                vector = new Vector<ImageIndex>();

        try {
            File f = new File(filename);
            Scanner scan = new Scanner(f);

            while (scan.hasNextLine()) {

                ImageIndex idx = new ImageIndex();
                String line = scan.nextLine();
                Scanner stringParse = new Scanner(line);
                if(stringParse.hasNextInt())
                {
                    idx.x = stringParse.nextInt();
                    if(stringParse.hasNextInt())
                    {
                        idx.y = stringParse.nextInt();
                    }
                    else{System.out.println("Index file format error."); System.exit(-1);}

                    // insert into vector
                    vector.add(idx);
                }
                else{System.out.println("Index file format error."); System.exit(-1);}
                stringParse.close();
            }
            scan.close();

        } catch (FileNotFoundException e) {

            System.out.println("File not found.");
            e.printStackTrace();
        }

        ImageIndex[] idxes = vector.toArray(new ImageIndex[vector.size()]);
        for(int i = 0; i < idxes.length; i++)
        {
            if(idxes[i].x >= this.imageGallery_width || idxes[i].y >= imageGallery_height)
            {
                System.out.println(filename+": index outof bound.");
                System.exit(-1);
            }
            // debug
            // System.out.println("x: "+String.valueOf(idxes[i].x)+" y: "+String.valueOf(idxes[i].y));
        }
        return idxes;
    }


    // 我們可以用這個function保留自己列印的照片編號，傳入值是一個照片編號的array
    public void adminPartitionsUpdate(String filename)
    {
        filename = DIRPATH+filename;
        ImageIndex[] idxes = readImageIndex(filename);
        for(int i = 0; i < idxes.length; i++)
        {
            trackingGallery[idxes[i].x][idxes[i].y] = ImageStatus.ADMIN_AVAILABLE;
        }
    }

    public enum degreeEnum
    {
        A(1),B(2),C(20);

        private int value;
        degreeEnum(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    public static Mat setRGB(Mat mat, int col, int row, int r, int g, int b) //效率差，不推薦使用
    {
        UByteIndexer indexer = mat.createIndexer();
        // imageBuffer.put(row, col, (b: 0, g:0, r:0), rgb量)
        indexer.put(row, col, 0, b);
        indexer.put(row, col, 1, g);
        indexer.put(row, col, 2, r);
        indexer.release();
        return mat;
    }

    private ImageIndex[] infectionRegion(Mat image_base, int w, int h, int affect_degree)
    {
        Vector<ImageIndex> imgidx = new Vector<ImageIndex>();

        // safe region
        int w0 = 0, w1 = image_base.arrayWidth()-1;
        int h0 = 0, h1 = image_base.arrayHeight()-1;

        // set up safe bound
        int w_up = (w+affect_degree > w1) ? w1 : w+affect_degree;
        int w_down = (w-affect_degree < w0)? w0 : w-affect_degree;
        int h_up = (h+affect_degree > h1) ? h1 : h+affect_degree;
        int h_down = (h-affect_degree < h0) ? h0 : h-affect_degree;

        for(int i = w_down; i <= w_up; i++)
        {
            if(i == w)continue;
            for(int j = h_down; j <= h_up; j++)
            {
                if(j == h)continue;
                ImageIndex idx = new ImageIndex(i, j);
                imgidx.add(idx);
            }
        }

        ImageIndex[] idxes = imgidx.toArray(new ImageIndex[imgidx.size()]);
        return idxes;
    }

    private int getDirection()
    {
        double rand = Math.random();
        if(rand < 0.3)
            return 0;
        else if(rand < 0.6)
            return -1;
        else return 1;
    }


//    public BufferedImage algorithm_BAI(BufferedImage image_in, BufferedImage image_base)
//    {
//        double lottery_brightness = 0.5;
//        double win_prob = 0.2;
//        int breed_radius = 10;
//        boolean infection_effect_on = true;
//        boolean breed_effect_on = true;
//
//        // width, height check
//        if(image_in.getWidth() != image_base.getWidth() || image_in.getHeight() != image_base.getHeight())
//        {
//            System.out.println("image_in -- w:" + image_in.getWidth() + " h: " + image_in.getHeight());
//            System.out.println("image_base -- w:" + image_base.getWidth() + " h: " + image_base.getHeight());
//            System.out.println("Image size error!");
//            System.exit(-2);
//        }
//
//        // change every pixel of the image_in
//        for(int w = 0; w < image_in.getWidth(); w++)
//        {
//            for(int h = 0; h < image_in.getHeight(); h++)
//            {
//                int pixel_base = image_base.getRGB(w, h);
//                float hsb_base[] = Color.RGBtoHSB(getR(pixel_base),getG(pixel_base),getB(pixel_base), null);
//                float brightness = hsb_base[2];  // brightness will be between 0~100
//
//                // Debug
//                // System.out.print(String.valueOf(brightness)+ " ");;
//
//                // set this pixel to gray scale and tune its brightness to match the pixel_base
//
//                int pixel_in = image_in.getRGB(w, h);
//                float hsb_in[] = Color.RGBtoHSB(getR(pixel_in),getG(pixel_in),getB(pixel_in), null);
//                image_in.setRGB(w, h, Color.HSBtoRGB(hsb_in[0], 0, brightness));
//
//                //---- lottery breed ----//
//                if(breed_effect_on && brightness < lottery_brightness)
//                {
//                    int breed_ct = 0, breed_w = 0, breed_h = 0;
//                    int direction_w = getDirection();
//                    int direction_h = getDirection();
//                    while(Math.random() > win_prob && breed_ct < breed_radius)
//                    {
//                        breed_ct++;
//                        breed_w = direction_w + w;
//                        breed_h = direction_h + h;
//                        int w0 = 0, w1 = image_base.getWidth()-1;
//                        int h0 = 0, h1 = image_base.getHeight()-1;
//                        if(breed_w > w1 || breed_w < w0) breed_w = w;
//                        if(breed_h > h1 || breed_h < h0) breed_h = h;
//
//                        // set this pixel to gray scale and tune its brightness to match the pixel_base
//                        image_in.setRGB(breed_w, breed_h, Color.HSBtoRGB(hsb_in[0], 0, brightness));
//                    }
//                }
//            }
//        }
//
//        // set the affected pixels
//        if(infection_effect_on)
//        {    for(int w = 0; w < image_in.getWidth(); w++)
//        {
//            for(int h = 0; h < image_in.getHeight(); h++)
//            {
//                // get base pixel HSB
//                int pixel_base = image_base.getRGB(w, h);
//                float hsb_base[] = Color.RGBtoHSB(getR(pixel_base),getG(pixel_base),getB(pixel_base), null);
//                float brightness = hsb_base[2];  // brightness will be between 0~100
//
//                // set affect_degree
//                int affect_degree = 0;
//
//                // affect_table: 0~20: C, 20~30: B, 30~70: A, 70~80: B, 80~100:C
//                if(brightness <= 0.3)
//                    affect_degree = degreeEnum.C.value;
//                else if(brightness > 0.3 && brightness < 0.5)
//                    affect_degree = degreeEnum.B.value;
//                else
//                    affect_degree = degreeEnum.A.value;
//
//                // get infection_region
//                ImageIndex[] infection_region = infectionRegion(image_base, w, h, affect_degree);
//
//                // set the affected pixels
//                for(int i = 0; i < infection_region.length; i++)
//                {
//                    int inf_w = infection_region[i].x, inf_h = infection_region[i].y;
//                    int infRGB = image_base.getRGB(inf_w, inf_h);
//                    float inf_hsb[] = Color.RGBtoHSB(getR(infRGB),getG(infRGB),getB(infRGB), null);
//                    image_in.setRGB(inf_w, inf_h, Color.HSBtoRGB(inf_hsb[0], inf_hsb[1], (brightness+inf_hsb[2])/2));
//                }
//            }
//        }}
//        return image_in;
//    }
//
//
//    public BufferedImage colorToGray1(BufferedImage image_in)
//    {
//
//        BufferedImage image_out = image_in;
//        int width = image_out.getWidth();
//        int height = image_out.getHeight();
//
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                int pixel = image_out.getRGB(x, y);
//                int a = getA(pixel);
//                int r = getR(pixel);
//                int g = getG(pixel);
//                int b = getB(pixel);
//                int average = (int)(0.2989*r + 0.5870*g + 0.1140*b);
//                pixel = getPixel(a, average, average, average);
//                image_out.setRGB(x, y, pixel);
//            }
//        }
//
//        return image_out;
//
//    }
//
//    public BufferedImage algorithm_shiuan(BufferedImage image_in, BufferedImage image_base)
//    {
//        BufferedImage image_out, image_black;
//        image_black=colorToGray1(image_base);//send imageGallery[a][b]
//        image_in=colorToGray1(image_in);
//        image_out=image_in;
//        int width = image_in.getWidth();
//        int height = image_in.getHeight();
//        for (int j = 0; j < height; j++) {
//            for (int i = 0; i <width; i++) {
//                if ((i+j)%2==0) {
//                    int pixel_in = image_in.getRGB(j, i);
//                    int pixel_black = image_black.getRGB(j, i);
//                    double alpha = ((pixel_black >> 16) & 0xff) / 255.0;
//                    int a = (pixel_in >> 24) & 0xff;
//                    int r = (int) (((pixel_in >> 16) & 0xff) * alpha);
//                    int g = (int) (((pixel_in >> 8) & 0xff) * alpha);
//                    int b = (int) (((pixel_in >> 0) & 0xff) * alpha);
//                    pixel_in = getPixel(a, r, b, b);
//                    image_out.setRGB(j, i, pixel_in);
//                }
//                else
//                {
//                    int pixel_in = image_in.getRGB(j, i);
//                    image_out.setRGB(j, i, pixel_in);
//                }
//
//            }
//        }
//        return image_out;
//    }
//
//    // ==============================
//
//    public BufferedImage algorithm_Tim(BufferedImage image_in, BufferedImage image_base)
//    {
//
//        BufferedImage image_out = image_base;
//        int width = image_out.getWidth();
//        int height = image_out.getHeight();
//        // TODO: 值可以更改
//        int gridWidth = 30;
//        int gridHeight = 30;
//        int numCol = width / gridWidth;
//        int numRow = height / gridHeight;
//        int[][] newA = new int[numRow][numCol];
//        int[][] newR = new int[numRow][numCol];
//        int[][] newG = new int[numRow][numCol];
//        int[][] newB = new int[numRow][numCol];
//        int[][] pixels = new int[height][width];
//
//        image_out = colorToGray(image_out);
//
//        // 先把 pixel 資訊存到一個 2D array
//
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                pixels[y][x] = image_out.getRGB(x, y);
//            }
//        }
//
//        // 算每格各別的平均
//
//        for (int y = 0; y < numRow; y++) {
//            for (int x = 0; x < numCol; x++) {
//                // 在每個小格子裡面
//                int tempA = 0;
//                int tempR = 0;
//                int tempG = 0;
//                int tempB = 0;
//                for (int j = 0; j < gridHeight; j++) {
//                    for (int i = 0; i < gridWidth; i++) {
//                        int row = gridHeight * y + j;
//                        int col = gridWidth * x + i;
//                        tempA += getA(pixels[row][col]);
//                        tempR += getR(pixels[row][col]);
//                        tempG += getG(pixels[row][col]);
//                        tempB += getB(pixels[row][col]);
//                    }
//                }
//                newA[y][x] = tempA / (gridHeight * gridWidth);
//                newR[y][x] = tempR / (gridHeight * gridWidth);
//                newG[y][x] = tempG / (gridHeight * gridWidth);
//                newB[y][x] = tempB / (gridHeight * gridWidth);
//            }
//        }
//
//        // 把求出來的 set 回去
//
//        for (int y = 0; y < numRow; y++) {
//            for (int x = 0; x < numCol; x++) {
//                // 在每個小格子裡面
//                int pixel = getPixel(newA[y][x], newR[y][x], newG[y][x], newB[y][x]);
//
//                for (int j = 0; j < gridHeight; j++) {
//                    for (int i = 0; i < gridWidth; i++) {
//                        int row = gridHeight * y + j;
//                        int col = gridWidth * x + i;
//                        image_out.setRGB(col, row, pixel);
//                    }
//                }
//
//            }
//        }
//
//        // 疊加使用者照片
//
//        try {
//            image_in = resizeImage(image_in, width, height);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//
//        for (int j = 0; j < height; j++) {
//            for (int i = 0; i < width; i++) {
//                int pixel_user = image_in.getRGB(i, j);
//                int pixel_picture = image_out.getRGB(i, j);
//                double alpha = getR(pixel_picture) / 255.0;
//                // TODO:
//                alpha = alpha * 0.6 + 0.4;
//                int a = getA(pixel_user);
//                int r = (int)(getR(pixel_user) * alpha);
//                int g = (int)(getG(pixel_user) * alpha);
//                int b = (int)(getB(pixel_user) * alpha);
//                int pixel_result = getPixel(a, r, g, b);
//                image_out.setRGB(i, j, pixel_result);
//            }
//        }
//
//        return image_out;
//
//    }
//
//    public BufferedImage getImageGallery(int row, int col)
//    {
//        return imageGallery[row][col];
//    }
//
//    public BufferedImage colorToGray(BufferedImage image_in)
//    {
//
//        BufferedImage image_out = image_in;
//        int width = image_out.getWidth();
//        int height = image_out.getHeight();
//
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                int pixel = image_out.getRGB(x, y);
//                int a = getA(pixel);
//                int r = getR(pixel);
//                int g = getG(pixel);
//                int b = getB(pixel);
//                int average = (r + g + b) / 3;
//                pixel = getPixel(a, average, average, average);
//                image_out.setRGB(x, y, pixel);
//            }
//        }
//
//        return image_out;
//
//    }
//
//    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
//        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
//        Graphics2D graphics2D = resizedImage.createGraphics();
//        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
//        graphics2D.dispose();
//        return resizedImage;
//    }
//
//    private int getPixel(int a, int r, int g, int b) {
//        return (a << 24) | (r << 16) | (g << 8) | (b << 0);
//    }
//

// ==============================

    public ImageGallery(Mat image, int small_img_width, int small_img_height)
    {
        // do partition and save subImages into BufferedImage[][]
        partition(image, small_img_width, small_img_height);

        // user partitions update
        adminPartitionsUpdate("1.txt");
    }
}
package com.example.artfestproject0;

import org.bytedeco.opencv.opencv_core.Mat;
import com.example.artfestproject0.MyImage.*;
import java.io.File;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

public class Test {
    public static void main(String[] args) {

        Mat image = ImageGallery.stdLoadImg("rex.jpg");
        ImageGallery imageGallery = new ImageGallery(image, 108, 108);

        // ============================== Tim ==============================

        String destination = "/Users/linyanting/Desktop/ArtFestProject0/app/src/main/java/com/example/artfestproject0/MyImage/output.jpg";

        Mat img_user = ImageGallery.stdLoadImg("user.jpg");
        Mat img_new = ImageGallery.stdLoadImg("cat.jpg");
        ImageGallery imageGallery = new ImageGallery(img_new, 108, 108);
        img_new = imageGallery.algorithm_Tim(img_user, img_new);
        File f = new File("./output.jpg");
        imwrite(destination, img_new);

        // ============================== Tim ==============================

    }
}

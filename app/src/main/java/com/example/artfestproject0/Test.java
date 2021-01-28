package com.example.artfestproject0;

import org.bytedeco.opencv.opencv_core.Mat;
import com.example.artfestproject0.MyImage.*;

public class Test {
    public static void main(String[] args) {

        Mat image = ImageGallery.stdLoadImg("rex.jpg");
        ImageGallery imageGallery = new ImageGallery(image, 108, 108);

    }
}

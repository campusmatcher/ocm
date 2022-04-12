package com.example.ocmproject;

//import net.sourceforge.tess4j.Tesseract;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

//import org.opencv.android.Utils;
//import org.opencv.core.*;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;


import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ScheduleReader {
    String path;
    Mat src;
    Mat threshed;
    String absolutePath;
    Mat hierarchy;
    List<MatOfPoint> contours;
    ArrayList<Rect> boxes;
    ArrayList<String> courses;

    //For further improvements
//    Mat image1;
//    Mat image2;
//    Mat vertical;
//    Mat horizontal;
//    Mat rrrr;
    final int KERNEL_SIZE;



    public ScheduleReader(Bitmap bmp) {

        this.KERNEL_SIZE = 3;
        this.src = new Mat(bmp.getWidth(), bmp.getHeight(), CvType.CV_8UC4);
        Utils.bitmapToMat(bmp, src);
        //this.path = absolutePath + "/images/test.png";
        this.threshed = new Mat();
        this.courses = new ArrayList<>();

//        Mat oooo = new Mat();
//        Imgproc.threshold(src, oooo, 130, 255, Imgproc.THRESH_BINARY);
    }
    public void grayscaleImage(){
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);
    }
    public void blurImage(){
        Imgproc.blur(src, src, new Size(KERNEL_SIZE, KERNEL_SIZE));
        Imgproc.blur(src, src, new Size(KERNEL_SIZE, KERNEL_SIZE));
    }
    public void thresholdImage(){
        Imgproc.threshold(src, threshed, 128, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        Imgcodecs.imwrite("../resources/results/threshed.png", threshed);
    }
    public void findContours(){
        this.contours = new ArrayList<>();
        this.hierarchy = new Mat();
        Imgproc.findContours(threshed, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
    }
    public void findBoxes(){
        this.boxes = new ArrayList<>();
        for (MatOfPoint p: contours){
            boxes.add(Imgproc.boundingRect(p));
        }
    }
    public void paintBoxes(){
        Random ran = new Random();
        for (Rect r : boxes){
            Imgproc.rectangle(threshed, r.tl(), r.br(), new Scalar(127, 127, 127), -1);
        }
        Imgcodecs.imwrite("/Users/alpsencer/IdeaProjects/ScheduleExtract/src/main/java/boxboxobx.png", this.threshed);
    }
//    public void readText(){
//        Tesseract tes = new Tesseract();
//        tes.setDatapath("/opt/homebrew/Cellar/tesseract/5.1.0/share/tessdata");//!!!!!!!!!!!!!!! VERY important
//        bSort(boxes);
//        try {
//            int x = 0;
//            for (Rect r : boxes) {
//                if (r.x - x > 50){
//                    x = r.x;
//                    System.out.println("------------------------------");
//                }
//                Mat cropped = new Mat(src, r);
//                Imgcodecs.imwrite("/Users/alpsencer/IdeaProjects/ScheduleExtract/src/main/java/temp.png", cropped);
//
//                String text = tes.doOCR(new File("/Users/alpsencer/IdeaProjects/ScheduleExtract/src/main/java/temp.png"));
//                System.out.println(text);
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }
    public void newThread(){
        new Thread(new Runnable() {
            public void run() {
                readText();
                        }


        }).start();
    }
    public ArrayList<String> readText(){
        //https://developers.google.com/ml-kit/vision/text-recognition/android
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        bSort(boxes);
        try {
            int x = 0;
            for (Rect r : boxes) {
                if (r.x - x > 50){
                    x = r.x;
                    System.out.println("------------------------------");
                }
                Mat cropped = new Mat(src, r);
                Bitmap mBitmap = Bitmap.createBitmap(cropped.width(), cropped.height(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(cropped, mBitmap);
                Imgcodecs.imwrite("/Users/alpsencer/IdeaProjects/ScheduleExtract/src/main/java/temp.png", cropped);
                InputImage image = InputImage.fromBitmap(mBitmap, 0); // degree should be edited later

                Task<Text> result =
                        recognizer.process(image)
                                .addOnSuccessListener(new OnSuccessListener<Text>() {
                                    @Override
                                    public void onSuccess(Text visionText) {
                                        // Task completed successfully
                                        // ...
                                        String resultText = visionText.getText();
                                        for (Text.TextBlock block : visionText.getTextBlocks()) {
                                            String blockText = block.getText();
                                            Log.i("Course: ",blockText);
                                            courses.add(blockText);
                                            //Thread.sleep(1000000);
                                            android.graphics.Point[] blockCornerPoints = block.getCornerPoints(); // prevent name ambiguity
                                            android.graphics.Rect blockFrame = block.getBoundingBox();
                                            for (Text.Line line : block.getLines()) {
                                                String lineText = line.getText();
                                                android.graphics.Point[] lineCornerPoints = line.getCornerPoints();
                                                android.graphics.Rect lineFrame = line.getBoundingBox();
                                                for (Text.Element element : line.getElements()) {
                                                    String elementText = element.getText();
                                                    android.graphics.Point[] elementCornerPoints = element.getCornerPoints();
                                                    android.graphics.Rect elementFrame = element.getBoundingBox();
                                                }
                                            }
                                        }
                                    }
                                })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Task failed with an exception
                                                // ...
                                            }
                                        });

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return courses;
    }


    public void omitBigBoxes (ArrayList<Rect> rects){
        int deleted = 0;
        for (int i = 0; i < rects.size() - deleted; i++){
            Rect r = rects.get(i);
            for (Rect o: rects){
                if (r.contains(o.tl()) && !(r.equals(o))) {
                    rects.remove(r);
                    deleted ++;
                    break;
                }
            }
        }
    }
    public void omitSizeBoxes(ArrayList<Rect> rects){
        int deleted = 0;
        int sizeLimit = 1000000;
        int size = rects.size();
        for (int i = 0; i < size - deleted; i++){
            Rect r = rects.get(i);
            if ((r.width > 10000 || r.height > 5000) || (r.width < 150 || r.height < 50)){
                rects.remove(i);
                i--;
                deleted ++;

            }
        }
    }
    public void swap(ArrayList<Rect> rects, int a, int b){
        Rect temp = rects.get(a);
        rects.set(a, rects.get(b));
        rects.set(b, temp);
    }

    public void bSort(ArrayList<Rect> rects){
        int error = 30;
        for (int i = 0; i < rects.size(); i ++){
            for (int j = i; j < rects.size(); j++) {
                Rect r = rects.get(i);
                Rect o = rects.get(j);
                if (Math.abs(r.x - o.x) > error && r.x > o.x){
                    swap(rects, rects.indexOf(r), rects.indexOf(o));
                }
            }
        }
        for (int i = 0; i < rects.size(); i ++){
            for (int j = i; j < rects.size(); j++) {
                Rect r = rects.get(i);
                Rect o = rects.get(j);
                if (Math.abs(r.x - o.x) < error && r.y - o.y > error){
                    swap(rects, rects.indexOf(r), rects.indexOf(o));
                }
            }
        }
    }
    public ArrayList<Rect> getBoxes(){
        return this.boxes;
    }


//    public static void main(String[] args) {
//
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // must be in main
//
//        String input = "/Users/alpsencer/IdeaProjects/ScheduleExtract/src/main/resources/images/test.png";
//
//        src = Imgcodecs.imread(input, Imgcodecs.IMREAD_GRAYSCALE);
//        Imgproc.blur(src, src, new Size(3, 3));
//        Imgproc.blur(src, src, new Size(3, 3));
//
//        Imgproc.threshold(src, threshed, 128, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
//        Imgcodecs.imwrite("../resources/results/threshed.png", threshed);
//
//        List<MatOfPoint> contours = new ArrayList<>();
//        Mat hierarchy = new Mat();
//        Imgproc.findContours(threshed, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
//
//        ArrayList<Rect> boxes = new ArrayList<>();
//        for (MatOfPoint p: contours){
//            boxes.add(Imgproc.boundingRect(p));
//        }
//        omitBigBoxes(boxes);
//        omitSizeBoxes(boxes);
//        Random ran = new Random();
//        for (Rect r : boxes){
//            Imgproc.rectangle(threshed, r.tl(), r.br(), new Scalar(127, 127, 127), -1);
//        }
//        Imgcodecs.imwrite("/Users/alpsencer/IdeaProjects/ScheduleExtract/src/main/java/boxboxobx.png", threshed);
////        Mat oooo = new Mat();
////        Imgproc.threshold(src, oooo, 130, 255, Imgproc.THRESH_BINARY);
//        Tesseract tes = new Tesseract();
//        tes.setDatapath("/opt/homebrew/Cellar/tesseract/5.1.0/share/tessdata");//!!!!!!!!!!!!!!! VERY important
//        bSort(boxes);
//        try {
//            int x = 0;
//            for (Rect r : boxes) {
//                if (r.x - x > 50){
//                    x = r.x;
//                    System.out.println("------------------------------");
//                }
//                Mat cropped = new Mat(src, r);
//                Imgcodecs.imwrite("/Users/alpsencer/IdeaProjects/ScheduleExtract/src/main/java/temp.png", cropped);
//
//                String text = tes.doOCR(new File("/Users/alpsencer/IdeaProjects/ScheduleExtract/src/main/java/temp.png"));
//                System.out.println(text);
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();;
//        }
//
//    }
}
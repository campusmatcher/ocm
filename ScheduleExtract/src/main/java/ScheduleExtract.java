import net.sourceforge.tess4j.Tesseract;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ScheduleExtract {
    String path;
    Mat src;
    Mat threshed;
    String absolutePath;
    Mat hierarchy;
    List<MatOfPoint> contours;
    ArrayList<Rect> boxes;

    //For further improvements
//    Mat image1;
//    Mat image2;
//    Mat vertical;
//    Mat horizontal;
//    Mat rrrr;
    final int KERNEL_SIZE;


    public ScheduleExtract() {

        this.KERNEL_SIZE = 3;
        URL file = getClass().getResource("");
        this.absolutePath = file.getPath();
        //this.path = absolutePath + "/images/test.png";
        this.path = "/Users/alpsencer/IdeaProjects/ScheduleExtract/src/main/resources/images/test.png";
        this.threshed = new Mat();

//        Mat oooo = new Mat();
//        Imgproc.threshold(src, oooo, 130, 255, Imgproc.THRESH_BINARY);
    }
    public void grayscaleImage(){
        this.src = Imgcodecs.imread(path, Imgcodecs.IMREAD_GRAYSCALE);
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
    public void readText(){
            Tesseract tes = new Tesseract();
            tes.setDatapath("/opt/homebrew/Cellar/tesseract/5.1.0/share/tessdata");//!!!!!!!!!!!!!!! VERY important
            bSort(boxes);
            try {
                int x = 0;
                for (Rect r : boxes) {
                    if (r.x - x > 50){
                        x = r.x;
                        System.out.println("------------------------------");
                    }
                    Mat cropped = new Mat(src, r);
                    Imgcodecs.imwrite("/Users/alpsencer/IdeaProjects/ScheduleExtract/src/main/java/temp.png", cropped);

                    String text = tes.doOCR(new File("/Users/alpsencer/IdeaProjects/ScheduleExtract/src/main/java/temp.png"));
                    System.out.println(text);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
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

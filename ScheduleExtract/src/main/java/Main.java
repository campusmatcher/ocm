import org.opencv.core.Core;

public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // must be in main
        ScheduleExtract scheduler = new ScheduleExtract();
        scheduler.grayscaleImage();
        scheduler.blurImage();
        scheduler.blurImage();
        scheduler.thresholdImage();
        scheduler.findContours();
        scheduler.findBoxes();
        scheduler.omitBigBoxes(scheduler.getBoxes());
        scheduler.omitSizeBoxes(scheduler.getBoxes());
        scheduler.paintBoxes();
        scheduler.paintBoxes();
        scheduler.readText();



    }
}

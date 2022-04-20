/**
 * @author bilal
 * gives the schedule of given url in boolean and byte format
 */
import javax.lang.model.element.Element;
import org.jsoup.*;import org.jsoup.nodes.*;

public class TrySRS {
    public static void main(String[] args) {
        try {
            java.util.Scanner sc = new java.util.Scanner(System.in);

            System.out.println("enter department letter code:");
            String deptCode = sc.nextLine().toUpperCase();

            System.out.println("enter course number code:");
            String numberCode = sc.nextLine();

            System.out.println("enter the section number:");
            String section = sc.nextLine();

            System.out.println("enter the year:");
            String year = sc.nextLine();

            System.out.println("spring or fall?:");
            String semester = sc.nextLine().toLowerCase();
            if (semester.equals("spring"))
            {semester = "2";}
            else {
                semester = "1";
            }
            
            Document doc = Jsoup.connect("https://stars.bilkent.edu.tr/syllabus/view/" + deptCode + "/" +  numberCode +"/" + year + semester +"?section=" + section).get();
            scheduleMaker( sourceParser(doc.toString()));
            sc.close();

        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
































































































































































































































































































































































































    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 



































     Looking at the source requires punishment. Do not look at it. Besides, you will regret it.
     * @param source
     * @return
     */
    public static String sourceParser(String source) {
        String toReturn = "";
        int start = 0;
        int end = 0;

        for (int i = 0; i < source.length() - 35; i++) {
            if (source.substring(i,i+24).equals("<th width=\"13%\">Sun</th>"))
            start = i;

            if (source.substring(i, 31+i).equals("<table style=\"font-size: 10px\">"))
            end = i;
        }
        toReturn = source.substring(start, end);


        return toReturn;
    }
    public static void scheduleMaker(String html) {
        int saatSlotu = 0;
        int xCoordinate = 0; 

        int[][] schedule = new int[14][7];
        //System.out.println(divTag);

        System.out.println("\t    monday\ttuesday\t  wednesday   thursday  friday  saturday   sunday");
        for (int i = 0; i < html.length() - 16; i++) {


            if (html.substring(i,4+i).equals("<tr>")) {
                saatSlotu++;
                System.out.println();
                System.out.print("saat " +(saatSlotu + 7 )+".30:  ");
                xCoordinate = 0;                
            }
            if (html.substring(i,i+5).equals("&nbsp")) {
                System.out.printf("  yok      ");
                schedule[saatSlotu - 1][xCoordinate] = 0;
                xCoordinate++;
            }
            if (html.substring(i,i+16).equals("font-weight:bold")) {
                System.out.print("ders var   ");
                schedule[saatSlotu - 1][xCoordinate] = 1;
                xCoordinate++;
            }
        }
        for (int[] each : schedule) {
            System.out.println();
            for (int is : each) {
                System.out.print(is + "\t");
            }
        }
    }
}

package com.example.ocmproject;

import java.util.HashMap;

public class Section {
    String name; // Format = Course000-00
    String courseName;
    String courseCode;
    String sectionCode;
    HashMap<String, Integer> hmap;


    public Section(String name, HashMap<String, Integer> hmap){
        this.name = name;
        this.hmap = hmap;
        this.courseName = "";
        this.courseCode = "";
        int i;
        for (i = 0; i < name.length() && Character.isLetter(name.charAt(i)); i++){ courseName += name.charAt(i);}
        for (; i < name.length() && name.charAt(i) != '-'; i++){ courseCode += name.charAt(i);}
        this.sectionCode = name.substring(i);
    }

    public String getCourseName(){return this.courseName;}
    public String getCourseCode() {return courseCode;}
    public String getSectionCode(){return this.sectionCode;}
}

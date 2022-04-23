package com.example.ocmproject;

import java.util.ArrayList;
import java.util.HashMap;

public class Schedule {
    ArrayList<Section> sections;
    HashMap<String, Integer> hmap;

    public Schedule (ArrayList<Section> sections){
        this.sections = sections;
        this.hmap = new HashMap<>();
        for (String key: sections.get(0).getHashMap().keySet()){
            for (Section sect: sections){
                if (sect.getHashMap().get(key) == 1){
                    this.hmap.put(key, 1);
                }
                else if (sect.getHashMap().get(key) == 2){ // for spare hours
                    this.hmap.put(key, 2);
                }
            }
        }
    }

    public ArrayList<Section> getSections(){return this.sections;}
    public HashMap<String, Integer> getHmap(){return this.hmap;}


}

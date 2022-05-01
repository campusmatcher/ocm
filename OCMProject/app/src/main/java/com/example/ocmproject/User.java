package com.example.ocmproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private String name;
    private String surname;
    private String email;
    private String username;
    //private ArrayList<String> contacts;
    private ArrayList<Section> sections;
    private HashMap<String, Object> connections;
    private String id;
    private List<String> Schedule;
    private HashMap<String, String> Contacts;
    private List<String> Lessons;
    private List<String> Interest;
    private List<String> MatchList;
    private HashMap<String, String> Pending;
    private HashMap<String, String> Sent;

    public HashMap<String, String> getPending() {
        return Pending;
    }

    public void setPending(HashMap<String, String> pending) {
        Pending = pending;
    }

    public HashMap<String, String> getSent() {
        return Sent;
    }

    public void setSent(HashMap<String, String> sent) {
        Sent = sent;
    }





    public List<String> getMatchList() {
        return MatchList;
    }

    public void setMatchList(List<String> matchList) {
        MatchList = matchList;
    }

    public List<String> getInterest() {
        return Interest;
    }

    public void setInterest(List<String> interest) {
        Interest = interest;
    }

    public List<String> getLessons() {
        return Lessons;
    }

    public void setLessons(List<String> lessons) {
        Lessons = lessons;
    }

    public User() {
    }

    public User(String name, String surname, String email, String username, HashMap<String, Object> connections, ArrayList<Section> sections) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.connections = connections;
        this.sections = sections;
        //this.Schedule = new List<>();
    }

    public User(String name, String surname, String email, String username) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
    }
    public User(String name, String surname, String email, String username, HashMap<String, Object> connections, ArrayList<Section> sections, String id) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.connections = connections;
        this.sections = sections;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }
    public String getId() {
        return id;
    }
    public List<String> getSchedule(){
        return Schedule;
    }
    public void setSchedule(List<String> map){
        Schedule = map;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashMap<String, String> getContacts() {
        return Contacts;
    }

    public void setContacts(HashMap<String, String> contacts) {
        this.Contacts = contacts;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }


//    public void addConnection(String newConnectionID){
//        Contacts.add(newConnectionID);
//    }
}

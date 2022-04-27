package com.example.ocmproject;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String name;
    private String surname;
    private String email;
    private String username;
    private ArrayList<String> contacts;
    private ArrayList<Section> sections;
    private HashMap<String, Object> connections;

    public User() {
    }

    public User(String name, String surname, String email, String username, HashMap<String, Object> connections, ArrayList<Section> sections) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.connections = connections;
        this.sections = sections;
    }

    public User(String name, String surname, String email, String username) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }

    public void addConnection(String newConnectionID){
        contacts.add(newConnectionID);
    }
}

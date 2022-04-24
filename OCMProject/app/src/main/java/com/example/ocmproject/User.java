package com.example.ocmproject;

import java.util.ArrayList;

public class User {

    private String name;
    private String surname;
    private String email;
    private String username;
    private ArrayList<User> contacts;
    private ArrayList<Section> sections;

    public User() {
    }

    public User(String name, String surname, String email, String username, ArrayList<User> contacts, ArrayList<Section> sections) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.contacts = contacts;
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

    public ArrayList<User> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<User> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }
}

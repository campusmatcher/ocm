package com.example.ocmproject;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    FirebaseAuth auth;
    DatabaseReference mDatabase;
    DatabaseReference userReference;


    private String name;
    private String surname;
    private String email;
    private String username;
    //private ArrayList<String> contacts;
    private ArrayList<Section> sections;
    //private HashMap<String, Object> connections;
    private String id;
    private List<String> Schedule;
    private List <String> Contacts;
    private List<String> Lessons;
    private List<String> Interest;
    private List<String> MatchList;
    private List<String> Pending;
    private List<String> Sent;



    public User() {
    }

    public User(String name, String surname, String email, String username, HashMap<String, Object> connections, ArrayList<Section> sections) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        //this.connections = connections;
        this.sections = sections;
        //this.Schedule = new List<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        userReference = mDatabase.child("NewUser").child(this.id);

    }

    public User(String name, String surname, String email, String username) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        userReference = mDatabase.child("NewUser").child(this.id);

    }

    public void setId(String id) {
        this.id = id;
    }

    public User(String name, String surname, String email, String username, HashMap<String, Object> connections, ArrayList<Section> sections, String id) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        //this.connections = connections;
        this.sections = sections;
        this.id = id;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        userReference = mDatabase.child("NewUser").child(this.id);

    }

//    public static User getCurrentUser(){
//        final User[] current = new User[1];
//        FirebaseDatabase.getInstance().getReference().child("NewUser").
//                child(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                    current[0] = null;
//                }
//                else {
//                    //Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                    User user = task.getResult().getValue(User.class);
//                    current[0] = user;
//
//                }
//
//            }
//        });
//        return current[0]; // doesn't work bcs of asynchronous behavior of firebase
//    }

    public void updateFirebase(){
        FirebaseDatabase.getInstance().getReference().child("NewUser").child(this.id).setValue(this);
    }
    public void updateSent(){
        FirebaseDatabase.getInstance().getReference().child("NewUser").child(this.id).child("Sent").setValue(this.Sent);
    }

    public void updateMatchList(){
        if (MatchList == null){MatchList = new ArrayList<>();}
        FirebaseDatabase.getInstance().getReference().child("NewUser").child(this.id).child("MatchList").setValue(this.MatchList);
    }

    public void updatePending(){ FirebaseDatabase.getInstance().getReference().child("NewUser").child(this.id).child("Pending").setValue(this.Pending); }
    public void updateContacts(){ FirebaseDatabase.getInstance().getReference().child("NewUser").child(this.id).child("Contacts").setValue(this.Contacts); }


    public void addItemToSent(String otherID){
        if(Sent == null || !Sent.contains(otherID)){
            if (Sent == null){Sent = new ArrayList<>();}
            Sent.add(otherID);
            this.updateSent();
            FirebaseDatabase.getInstance().getReference().child("NewUser")
                    .child(otherID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()){

                    }
                    else {
                        DataSnapshot snapshot = task.getResult();
                        User other = snapshot.getValue(User.class);
                        List<String> pend = other.getPending();
                        pend.add(id);
                        other.setPending(pend);
                        other.updatePending();
                    }
                }

//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
            });
        }
    }
    public void acceptContact(String otherID){
        if(Pending.contains(otherID)){
            if (Contacts == null) {Contacts = new ArrayList<>();}
            Contacts.add(otherID);
            Pending.remove(otherID);
            this.updatePending();
            this.updateContacts();
            FirebaseDatabase.getInstance().getReference().child("NewUser")
                    .child(otherID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()){

                    }
                    else {
                        DataSnapshot snapshot = task.getResult();
                        User other = snapshot.getValue(User.class);
                        List<String> sent = other.getSent();
                        List<String> cont = other.getContacts();
                        sent.remove(id);
                        cont.add(id);
                        other.setSent(sent);
                        other.updateSent();
                        other.setContacts(cont);
                        other.updateContacts();
                    }
                }

//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
            });
        }
    }

//    public void acceptedByOther(String otherID){
//        if(Sent.contains(otherID)){
//            Contacts.add(otherID);
//            Sent.remove(otherID);
//            this.updateSent();
//            this.updateContacts();
//            FirebaseDatabase.getInstance().getReference().child("NewUser").child(this.id).child("NewUser").child(otherID).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    User other = snapshot.getValue(User.class);
//                    List<String> pend= other.getPending();
//                    List<String> cont = other.getContacts();
//                    pend.remove(id);
//                    cont.add(id);
//                    other.setPending(pend);
//                    other.updatePending();
//                    other.setContacts(cont);
//                    other.updateSent();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//    }
    public void deleteFromPending(String otherID){
        if(Pending.contains(otherID)){
            Pending.remove(otherID);
            this.updatePending();
            FirebaseDatabase.getInstance().getReference().child("NewUser")
                    .child(otherID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()){

                    }
                    else {
                        DataSnapshot snapshot = task.getResult();
                        User other = snapshot.getValue(User.class);
                        List<String> sent = other.getSent();
                        //if (sent == null){sent = new ArrayList<>();}
                        sent.remove(id);
                        other.setSent(sent);
                        other.updateSent();
                    }
                }

//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
            });
        }
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




    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }

    public List<String> getContacts() {
        if (Contacts != null) {
            return Contacts;
        }
        else{
            return new ArrayList<>();
        }
    }

    public void setContacts(List<String> contacts) {
        Contacts = contacts;
    }

    public List<String> getPending() {
        if (Pending != null) {
            return Pending;
        }
        else{
            return new ArrayList<>();
        }
    }

    public void setPending(List<String> pending) {
        Pending = pending;
    }

    public List<String> getSent() {
        if (Sent != null) {
            return Sent;
        }
        else{
            return new ArrayList<>();
        }
    }

    public void setSent(List<String> sent) {
        Sent = sent;
    }

    public List<String> getMatchList() {
        return MatchList;
    }

    public void setMatchList(List<String> matchList) {
        MatchList = matchList;
    }

    public List<String> getInterest() {
        if (Interest != null) {
            return Interest;
        }
        else{
            return new ArrayList<>();
        }
    }

    public void setInterest(List<String> interest) {
        Interest = interest;
    }

    public List<String> getLessons() {
        if (Lessons != null) {
            return Lessons;
        }
        else{
            return new ArrayList<>();
        }
    }

    public void setLessons(List<String> lessons) {
        Lessons = lessons;
    }


//    public void addConnection(String newConnectionID){
//        Contacts.add(newConnectionID);
//    }
}

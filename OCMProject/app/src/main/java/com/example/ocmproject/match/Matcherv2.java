package com.example.ocmproject.match;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ocmproject.User;
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

public class Matcherv2 {
    private User user;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private String userId;
    private HashMap<String, Integer> matchesMap;
    private List<String> matchList;
    private final int INTEREST_POINT;
    private final int SECTION_POINT;
    private final int FREE_HOUR_POINT;

    public Matcherv2(){
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = auth.getUid();
        matchesMap = new HashMap<String, Integer>();
        matchList = new ArrayList<String>();
        INTEREST_POINT = 10;
        SECTION_POINT = 10;
        FREE_HOUR_POINT = 10;
    }


    public void findAndUpdateMatchList(){
        mDatabase.child("NewUser").child(auth.getUid()).child("Contacts")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                matchList.clear();
                matchesMap.clear();
                mDatabase.child("NewUser").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot snapshot2 = task.getResult();
                        if(!task.isSuccessful()){
                            Log.e("get not completed", "error");
                        }
                        else{
                            user = snapshot2.child(userId).getValue(User.class);
                            for (DataSnapshot snap : snapshot2.getChildren()) {
                                User other = snap.getValue(User.class);
                                if (user.getContacts() == null || !user.getContacts().contains(other.getId())) { // if it is not in my contacts
                                    if (!userId.equals(other.getId())) {

                                        int simPoint = calculateSimilarity(user, other);
                                        matchesMap.put(other.getId(), simPoint);
                                        matchList.add(other.getId());

                                    }
                                }
                            }

                            ArrayList<String> temp = new ArrayList<String>();
                            for (int i = 0; i < matchList.size(); i++) {
                                temp.add(null);
                            }
                            mergeSort(matchesMap, matchList, temp, 0, matchList.size() - 1);
                            //mDatabase.child("NewUser").child(userId).child("MatchList").setValue(matchList);
                            user.setMatchList(matchList);
                            user.updateMatchList();
                        }
                    }

//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public int findFreeHourNumbers(List<String> sch1, List<String> sch2){
        if (sch1 == null || sch2 == null){return 0;}
        int common = 0;
        for (int i = 0; i < sch1.size(); i ++){
            if(Integer.valueOf(sch1.get(i)) + Integer.valueOf(sch2.get(i)) == 0){
                common ++;
            }
        }
        return common;
    }

    public int findCommonSections(List<String> les1, List<String> les2){
        if (les1 == null || les2 == null){return 0;}

        int common = 0;
        for (int i = 0; i < les1.size(); i++){
            if (les2.contains(les1.get(i))){
                common++;
            }
        }
        return common;
    }

    public int findCommonInterests(List<String> in1, List<String> in2){
        if (in1 == null || in2 == null){return 0;}

        int common = 0;
        for (int i = 0; i < in1.size(); i++){
            if (in2.contains(in1.get(i))){
                common++;
            }
        }
        return common;
    }

    public int calculateSimilarity(User user, User other){
        int point = 0;
        point += findFreeHourNumbers(user.getSchedule(), other.getSchedule()) * FREE_HOUR_POINT;
        point += findCommonInterests(user.getInterest(), other.getInterest()) * INTEREST_POINT;
        point += findCommonSections(user.getLessons(), other.getLessons()) * SECTION_POINT;
        return point;
    }



    public void mergeSort(HashMap<String, Integer> map, List<String> list,
                          List<String> temp, int start, int end){ //MergeSort

        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(map, list, temp, start, mid);
            mergeSort(map, list, temp, mid + 1, end);
            merge(map, list, temp, start, mid, end );

        }
    }
    public void merge(HashMap<String, Integer> map, List<String> list,
                      List<String> temp, int start, int mid, int end){
        int a = start;
        int index = start;
        int b = mid + 1;
        while (a <= mid && b <= end){
            if (map.get(matchList.get(a)).compareTo(map.get(matchList.get(b))) > 0){
                temp.set(index,matchList.get(a));
                a++;
                index++;
            }
            else{
                temp.set(index,matchList.get(b));
                b++;
                index++;
            }
        }

        while (a <= mid){
            temp.set(index, matchList.get(a));
            a++;
            index++;
        }
        while (b <= end){
            temp.set(index, matchList.get(b));
            b++;
            index++;
        }

        for(int i = start; i <= end; i++){
            matchList.set(i, temp.get(i));
        }
    }

}

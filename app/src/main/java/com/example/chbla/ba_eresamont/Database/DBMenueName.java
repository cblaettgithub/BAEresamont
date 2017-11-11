package com.example.chbla.ba_eresamont.Database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.chbla.ba_eresamont.Models.Page_lang;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chbla on 10.11.2017.
 */

public class DBMenueName extends AppCompatActivity {
    private ConnectFirebase connectFirebase;
    private String select="/supp_B/pages/5/pages_lang/";
    private ArrayList<String> values;
    Query query;
    private Page_lang page_lang;

    HashMap<String, Object> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectFirebase= new ConnectFirebase();
        final DatabaseReference myRef =  connectFirebase.getDatabaseReference
                (select);

        myRef.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName)
            {
                result = (HashMap<String, Object>) dataSnapshot.getValue();

            }
            public void onChildRemoved(DataSnapshot dataSnapshot){
                Pages value = dataSnapshot.getValue(Pages.class);
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName){}
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName){}

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG:", "Failed to read value.", error.toException()); }
        });
    }
    public void makeData(){
        connectFirebase= new ConnectFirebase();
        final DatabaseReference myRef =  connectFirebase.getDatabaseReference
                (select);

        myRef.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName)
            {
               result = (HashMap<String, Object>) dataSnapshot.getValue();

            }
            public void onChildRemoved(DataSnapshot dataSnapshot){
                Pages value = dataSnapshot.getValue(Pages.class);
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName){}
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName){}

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG:", "Failed to read value.", error.toException()); }
        });
    }
    public HashMap<String, Object> getResult(){
        return result;
    }

}

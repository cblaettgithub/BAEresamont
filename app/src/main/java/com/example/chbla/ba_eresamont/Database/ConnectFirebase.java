package com.example.chbla.ba_eresamont.Database;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by chbla on 01.11.2017.
 */

public class ConnectFirebase {
    private FirebaseDatabase database=null;
    private static boolean isPersistenceEnabled = false;
    private final String PAGEROOT="/Ba_2020/pages/";

    public ConnectFirebase(){
        if (!isPersistenceEnabled) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            isPersistenceEnabled = true;
        }
        database = FirebaseDatabase.getInstance();
    }
    public DatabaseReference getDatabaseReference(String value) {
        return database.getReference(value);
    }
    public void close(){
        database.goOffline();
        database =null;
    }

    @NonNull
    public DatabaseReference getDatabaseReference() {
        String select=PAGEROOT;
        final DatabaseReference myRef =  this.getDatabaseReference(select);
        myRef.keepSynced(true);
        return myRef;
    }



}

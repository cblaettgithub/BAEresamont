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
    private DatabaseReference myRef;

    public ConnectFirebase(){
        if (!isPersistenceEnabled) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            isPersistenceEnabled = true;
        }
        database = FirebaseDatabase.getInstance();
    }
    public DatabaseReference getDatabaseReference() {
        this.myRef = database.getReference(PAGEROOT);
        myRef.keepSynced(true);
        return myRef;
    }
    public void close(){
        database.goOffline();
        database =null;
    }
}

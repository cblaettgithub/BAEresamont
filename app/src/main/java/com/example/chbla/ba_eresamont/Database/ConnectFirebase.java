package com.example.chbla.ba_eresamont.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by chbla on 01.11.2017.
 */

public class ConnectFirebase {
    private FirebaseDatabase database=null;

    public ConnectFirebase(){
        database = FirebaseDatabase.getInstance();
        //database.setPersistenceEnabled(true);
    }
    public DatabaseReference getDatabaseReference(String value) {
        return database.getReference(value);
    }
    public void close(){
        database.goOffline();
        database =null;
    }

}

package com.example.chbla.ba_eresamont.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by chbla on 01.11.2017.
 */

public class ConnectFirebase {
    public DatabaseReference getDatabaseReference(String value) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        return database.getReference(value);
    }
}

package com.example.chbla.ba_eresamont.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

import com.example.chbla.ba_eresamont.Models.Pages_lang;
import com.example.chbla.ba_eresamont.Models.Pages;


/**
 * Created by chbla on 11.10.2017.
 */

public class SelectAddPages {
    // [START define_database_reference]
    private final String myFolder="/supp_B/";
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    public SelectAddPages() {
    }
    //nicht eingesetzt
    public Query ReadMyPages(){
        mDatabase = FirebaseDatabase.getInstance().getReference(myFolder);
        String myId="2";
        Query PageQuery = mDatabase.child("pages").child(myId);
        return PageQuery;
    }
    public void AddPages(Pages newPage){
        mDatabase = FirebaseDatabase.getInstance().getReference(myFolder);
        mDatabase.keepSynced(true);

        String key = mDatabase.child("page_lang").push().getKey();
        //Pages_lang page_lang = new Pages_lang("plaintext", "<p>Page de test</p>", "'Test FR'", "1", "1");

        //mDatabase.child("page_lang").child(key).setValue(page_lang);
        Pages pages = new Pages();

        Map<String, Object> page_Values = pages.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        mDatabase.child("page_lang").child(key).child("pages").updateChildren(pages.toMap());

    }
  }
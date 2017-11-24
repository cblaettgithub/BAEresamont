package com.example.chbla.ba_eresamont.Database;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;

import com.example.chbla.ba_eresamont.Classes.ButtonManager;
import com.example.chbla.ba_eresamont.Fragment.FirstFragment;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by chbla on 01.11.2017.
 */

public class ConnectFirebase {
    private Pages pages;
    public static final String LANGUAGE="1"; //0 French, 1 English, 2 Italy
    private FirebaseDatabase database=null;
    private static boolean isPersistenceEnabled = false;
    private final String PAGEROOT="/Ba_2020/pages/";
    private DatabaseReference myRef;
    private String LOG_TAG=ConnectFirebase.class.getSimpleName();
    private TreeMap hashMap;
    private ButtonManager buttonManager;

    public void setButtonManager(ButtonManager buttonManager) {
        this.buttonManager = buttonManager;
    }

    public Pages getPages() {     return pages;   }
    public void setPages(Pages pages) { this.pages = pages;    }
    public TreeMap getHashMap() {        return hashMap;    }
    private WebView webView;

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
    public DatabaseReference getDatabaseReference2() {
        this.myRef = database.getReference("/Ba_2020/");
        myRef.keepSynced(true);
        return myRef;
    }
    public void close(){
        database.goOffline();
        database =null;
    }
}

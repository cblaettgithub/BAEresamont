package com.example.chbla.ba_eresamont.Database;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.chbla.ba_eresamont.Classes.ButtonManager;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.TreeMap;

/**
 * Created by chbla on 27.11.2017.
 */

public class aDAOImplProgress extends aDAO {
    public aDAOImplProgress(Query query, String choice, ButtonManager buttonManager, TreeMap hashMap, int mlanguage) {
        super(query, choice, buttonManager, hashMap, mlanguage);
    }

    public  static final String LANGUAGE="1";
    @Override
    public void ReadDBData_Firebase(View view) {
       final WebView webView = view.findViewById(R.id.webView);
       WebSettings webSettings = webView.getSettings();
       webSettings.setJavaScriptEnabled(true);

       this.query.addChildEventListener(new ChildEventListener() {
            String temp;
            Pages pages;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.child("pages_lang").child(LANGUAGE).
                        child("translate").toString()!=""){
                    temp = (String) dataSnapshot.child("pages_lang").
                            child(LANGUAGE).child("title").getValue();
                    Log.w(LOG_TAG + ":ReadDBData Progres", temp);
                    Pages pages = dataSnapshot.getValue(Pages.class);
                    //getArrayIndex(pages, mlanuageId);
                    //buttonManager.ButtonCreator(pages, null, hashMap, mlanuageId);
                    buttonManager.ButtonCreator(pages, pages, hashMap,
                            cLanguageID.getArrayIndex(pages, mlanuageId), null);
                    mCallback.onArticleSelected(buttonManager.getHashMap());
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

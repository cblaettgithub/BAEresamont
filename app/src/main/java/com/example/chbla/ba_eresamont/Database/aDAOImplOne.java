package com.example.chbla.ba_eresamont.Database;


import com.example.chbla.ba_eresamont.Classes.ButtonManager;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;


import java.util.TreeMap;

/**
 * Created by chbla on 27.11.2017.
 */

public class aDAOImplOne extends aDAO {
    public aDAOImplOne(Query query, String choice, ButtonManager buttonManager, TreeMap hashMap, int mlanguage) {
        super(query, choice, buttonManager, hashMap, mlanguage);
    }

    @Override
    public void ReadDBData_Firebase(View view) {
       final WebView webView = view.findViewById(R.id.webView);
       WebSettings webSettings = webView.getSettings();
       webSettings.setJavaScriptEnabled(true);

       this.query.addChildEventListener(new ChildEventListener() {
            String content, neu;
            Pages pages;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Pages pages=dataSnapshot.getValue(Pages.class);
                    content = pages.getPages_lang().get(cLanguageID.GetLanguageID(pages,mlang)).getTranslate().toString();
                    neu = setCorrectContent(content);
                    webView.loadData(neu, "text/html", "UTF-8");

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
    @NonNull
    private String setCorrectContent(String content) {
        String neu;
        neu=content.replaceAll("style=", "style=\"").toString();
        neu=neu.replaceAll(";>", ";\">").toString();
        neu=neu.replaceAll("src=", "src=\"").toString();
        neu=neu.replaceAll("alt", "\" alt").toString();
        return neu;
    }
}

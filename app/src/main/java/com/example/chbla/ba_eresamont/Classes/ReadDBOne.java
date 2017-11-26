package com.example.chbla.ba_eresamont.Classes;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.chbla.ba_eresamont.Interface.IDBManager;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.TreeMap;

/**
 * Created by chbla on 26.11.2017.
 */

public class ReadDBOne implements IDBManager {
    private String LOG_TAG=ReadDBOne.class.getSimpleName();
    CLanguageID cLanguageID = new CLanguageID("3");

    @Override
    public void ReadDBData_Firebase(Query query, String choice,
                                    ButtonManager buttonManager, View view, WebView webView, TreeMap hashMap, final String mlanguage) {
        {
            final WebView webViewx = view.findViewById(R.id.webView);
            WebSettings webSettings = webViewx.getSettings();
            webSettings.setJavaScriptEnabled(true);

            query.addChildEventListener(new ChildEventListener() {
                String temp;
                Pages pages;
                CLanguageID cLanguageID=new CLanguageID("3");
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    for (DataSnapshot single : dataSnapshot.getChildren()) {
                        Pages pages=dataSnapshot.getValue(Pages.class);
                        String temp = pages.getPages_lang().get(Integer.parseInt(cLanguageID.GetLanguageID(pages,mlanguage ))).
                                getTranslate();
                        String neu=temp.replaceAll("(\r\n|\n)", "<br />");
                        webViewx.loadData(neu, "text/html", "UTF-8");
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
}

package com.example.chbla.ba_eresamont.Database;


import com.example.chbla.ba_eresamont.Classes.ButtonManager;
import com.example.chbla.ba_eresamont.Classes.ContentCorrecter;
import com.example.chbla.ba_eresamont.Fragment.FirstFragment;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;


import java.util.TreeMap;

/**
 * Created by chbla on 27.11.2017.
 */

public class aDAOImplOne extends aDAO {
    public aDAOImplOne(Query query, String choice, ButtonManager buttonManager, TreeMap hashMap, long mlanguage, boolean onetwopage) {
        super(query, choice, buttonManager, hashMap, mlanguage, onetwopage);
    }

    @Override
    public void ReadDBData_Firebase(View view,FirstFragment.OnHeadlineSelectedListener Callback) {
       final WebView webView = view.findViewById(R.id.webView);
       WebSettings webSettings = webView.getSettings();
       webSettings.setJavaScriptEnabled(true);

       this.query.addChildEventListener(new ChildEventListener() {
            String content="", neu="";
            Pages pages;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Pages pages=dataSnapshot.getValue(Pages.class);
                    if (pages!=null){
                        content = pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanuageId)).getTranslate().toString();
                        //neu = setCorrectContent(content);
                        if (pages.getParent_id()==87){//only boite a outil
                            webView.setTag(pages.getId());
                            webView.setInitialScale(1);
                            webView.getSettings().setLoadWithOverviewMode(true);
                            webView.getSettings().setUseWideViewPort(true);
                            webView.getSettings().setJavaScriptEnabled(true);
                        }
                        if (pages.getId()==100)
                            content=new ContentCorrecter(content).removeComments();
                        content=new ContentCorrecter(content).contentEscapeProcessing();
                        webView.loadData(content, "text/html", "UTF-8");
                        Log.d(LOG_TAG, "aDAoImpOne");
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

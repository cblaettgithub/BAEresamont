package com.example.chbla.ba_eresamont.Classes;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;

import com.example.chbla.ba_eresamont.Fragment.FirstFragment;
import com.example.chbla.ba_eresamont.Interface.IDBManager;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.TreeMap;

import static com.example.chbla.ba_eresamont.Classes.ButtonManager.LANGUAGE;

/**
 * Created by chbla on 26.11.2017.
 */

public class ReadDBProgress implements IDBManager {
    private String LOG_TAG=ReadDBProgress.class.getSimpleName();
    CLanguageID cLanguageID = new CLanguageID("3");
    public  static final String LANGUAGE="1";
    ReadDBProgress.OnHeadlineSelectedListener mCallback;

    public interface OnHeadlineSelectedListener {
        void onArticleSelected(TreeMap hashMap);
    }
    public void onListItemClick(ListView l, View v, TreeMap hashMap) {
        mCallback.onArticleSelected(hashMap);
    }

    @Override
    public void ReadDBData_Firebase(Query query, String choice, final ButtonManager buttonManager,
                                    View view, final WebView webView, final TreeMap hashMap, String mlanguage) {
        final String select = choice;
        final WebView myWebView = view.findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        final String mlanguagex=mlanguage;

        query.addChildEventListener(new ChildEventListener() {
            String temp;
            Pages pages;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.child("pages_lang").child(LANGUAGE).
                        child("translate").toString()!=""){
                    temp = (String) dataSnapshot.child("pages_lang").
                            child(LANGUAGE).child("title").getValue();
                    Log.w(LOG_TAG + ":ReadDBData Progres", temp);
                    Pages pages = dataSnapshot.getValue(Pages.class);
                    //GetLanguageID(pages, mlanguage);
                    //buttonManager.ButtonCreator(pages, null, hashMap, mlang);
                    buttonManager.ButtonCreator(pages, pages, hashMap,
                            cLanguageID.GetLanguageID(pages,mlanguagex ));
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
                Log.w(LOG_TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
}

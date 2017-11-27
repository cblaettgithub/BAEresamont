package com.example.chbla.ba_eresamont.Classes;

import android.provider.ContactsContract;
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

/**
 * Created by chbla on 26.11.2017.
 */

public class ReadDBHome implements IDBManager {
    private String LOG_TAG=ReadDBHome.class.getSimpleName();
    CLanguageID cLanguageID = new CLanguageID("3");
    public  static final String LANGUAGE="1";
    TreeMap hashmap;

    OnHeadlineSelectedListener mCallback;
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(TreeMap hashMap);
    }
    public void onListItemClick(ListView l, View v, TreeMap hashMap) {
        mCallback.onArticleSelected(hashMap);
    }

    @Override
    public void ReadDBData_Firebase(Query query, String choice, ButtonManager buttonManager,
                                    View view, WebView webView, TreeMap phashMap, String mlanguage) {

        final String select = choice;
        final WebView myWebView = view.findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        final String mlanguagex=mlanguage;
        final ButtonManager buttonManager1=buttonManager;
        Log.w(LOG_TAG + ":ReadDBData_Firebase", "start");
        final TreeMap hashMap=phashMap;

            query.addChildEventListener(new ChildEventListener() {
                String temp;
                Pages pages;

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.child("parent_id").exists() == false) {
                        if (dataSnapshot.child("pages_lang").child(mlanguagex).child("title").
                                getValue() != null) {
                            pages = dataSnapshot.getValue(Pages.class);
                            Log.w(LOG_TAG + ":ReadDBData Home", "out");
                            buttonManager1.ButtonCreator(pages, pages, hashMap, cLanguageID.GetLanguageID(pages,mlanguagex ));
                            //mCallback.onArticleSelected(buttonManager.getHashMap());
                        }
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

package com.example.chbla.ba_eresamont.Database;


import com.example.chbla.ba_eresamont.Classes.ButtonManager;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;


import java.util.TreeMap;

/**
 * Created by chbla on 27.11.2017.
 */

public class aDAOImplOne extends aDAO {
    public aDAOImplOne(Query query, String choice, ButtonManager buttonManager, TreeMap hashMap, String mlanguage) {
        super(query, choice, buttonManager, hashMap, mlanguage);
    }

    @Override
    public void ReadDBData_Firebase(View view) {
       final WebView webView = view.findViewById(R.id.webView);
       WebSettings webSettings = webView.getSettings();
       webSettings.setJavaScriptEnabled(true);

       this.query.addChildEventListener(new ChildEventListener() {
            String temp;
            Pages pages;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot single : dataSnapshot.getChildren()) {
                    Pages pages=dataSnapshot.getValue(Pages.class);
                    String temp = pages.getPages_lang().get(Integer.parseInt(cLanguageID.GetLanguageID(pages,mlanguage ))).
                            getTranslate();
                    String neu=temp.replaceAll("(\r\n|\n)", "<br />");
                    webView.loadData(neu, "text/html", "UTF-8");
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

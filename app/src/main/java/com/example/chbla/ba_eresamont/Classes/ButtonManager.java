package com.example.chbla.ba_eresamont.Classes;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.chbla.ba_eresamont.Database.ConnectFirebase;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by chbla on 22.11.2017.
 */
public class ButtonManager {
    private String LOG_TAG=ButtonManager.class.getSimpleName();
    private Context contex;
    public static final String LANGUAGE="3";
    //language 1= french, 2= italy, 3= english
    private LinearLayout linearLayout=null;
    private TreeMap hashMap;
    private Pages pages;
    private WebView webView;
    private ArrayList<Pages> pagesArrayList;
    ConnectFirebase connectFirebase=new ConnectFirebase();
    Query query=null;
    private String mlang;

    final DatabaseReference myRef = this.connectFirebase.getDatabaseReference();
    public WebView getWebView() {
        return webView;
    }

    public ButtonManager(Context contex, LinearLayout linearLayout, WebView webView) {
        this.contex = contex;
        this.linearLayout = linearLayout;
        this.webView = webView;
        pagesArrayList=new ArrayList<>();
    }

    public TreeMap getHashMap() { return hashMap;   }
    public void setHashMap(TreeMap hashMap) {this.hashMap = hashMap;  }
    public LinearLayout getLinearLayout() { return linearLayout;   }
    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;   }
    public Context getContex() {    return contex;    }

    public Button ConfigButton(String buttonname, int key) {
        Button button = new Button(this.getContex());
        button.setText(buttonname);
        button.setHeight(40);
        button.setId(key);
        return button;
    }
    public String GetLanguageID(Pages pages, String lang){
       int languageid=0;
       for(int i=0;i<pages.getPages_lang().size();i++){
           if (( String.valueOf(pages.getPages_lang().get(i).getLanguage())).equals(mlang))
               languageid=i;
       }
       return (String.valueOf(languageid));
    }

    public void ButtonCreator(final Pages pages, final Pages pages2, final TreeMap hashMap, String lang) {
        mlang=lang;
        String rowlang=GetLanguageID(pages, lang);
        Button button = this.ConfigButton(pages.getPages_lang().
                get(Integer.parseInt(rowlang)).getTitle(), ((int) pages.getPages_lang().get(Integer.parseInt(rowlang)).getId()));
        this.pages=pages;
        this.hashMap=hashMap;
        Log.w(LOG_TAG+":ButtonCreator:", pages.getId().toString());
        hashMap.put(pages.getId().toString(), pages.getPages_lang().get(Integer.parseInt(rowlang)).getTitle());
        pagesArrayList.add(pages);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (pages2==null){
                    ButtonShowContent(pages);
                }
                else{//create button in button
                    SubButton(pages, hashMap);
                }
            }
        });
        linearLayout.addView(button);
    }

    private void SubButton(final Pages pages, final TreeMap hashMap) {
        for(int i=0;i<linearLayout.getChildCount();i++)
            linearLayout.removeViewAt(i);
        linearLayout.removeViewAt(0);
        linearLayout.removeViewAt(1);

        query=myRef.orderByChild("parent_id").equalTo(Integer.parseInt(pages.getId().toString()));
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /*for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    pagesArrayList.add(dataSnapshot.getValue(Pages.class));
                }
               for(int i=0;i<pagesArrayList.size();i++)
                    ButtonCreator(pagesArrayList.get(i),  null,hashMap);*/
               ButtonCreator(dataSnapshot.getValue(Pages.class),  null,hashMap, mlang);
               pagesArrayList.add(pages);
                Collections.sort(pagesArrayList, new Comparator<Pages>(){
                    public int compare(Pages o1, Pages o2){
                        return (int) (o1.getId() - o2.getId());
                    }
                });
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
    private void ButtonShowContent(Pages pages){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        linearLayout.removeAllViews();
        webView.loadData(pages.getPages_lang().get(Integer.parseInt(LANGUAGE)).
                getTranslate().toString(), "text/html", "UTF-8");
    }
}


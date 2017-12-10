package com.example.chbla.ba_eresamont.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.chbla.ba_eresamont.Classes.ButtonManager;
import com.example.chbla.ba_eresamont.Classes.CLanguageID;
import com.example.chbla.ba_eresamont.Database.aDAOImplHome;
import com.example.chbla.ba_eresamont.Database.aDAOImplOne;
import com.example.chbla.ba_eresamont.Database.aDAOImplProgress;
import com.example.chbla.ba_eresamont.Interface.IDAO;
import com.example.chbla.ba_eresamont.Database.ConnectFirebase;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.TreeMap;

/**
 * Created by chbla on 31.10.2017.
 */
public class FirstFragment extends Fragment {
    private TreeMap hashMap;
    private ConnectFirebase connectFirebase;
    private DatabaseReference databaseReference;
    Context context;
    public void setContext(Context context) {
        this.context = context;
    }
    private View view;
    @Nullable
    @Override
    public View getView() {
        return view;
    }
    public void setView(View view) {
        this.view = view;
    }
    private String LOG_TAG=FirstFragment.class.getSimpleName();
    private ButtonManager buttonManager;
    private LinearLayout linearLayout=null;
    private WebView webView;
    private Pages pages;
    public long mlanguageId;
    private static String LANGUAGE="0";
    private Integer mMenuId;
    private static boolean mMainDetail;
    public ButtonManager getButtonManager() {
        return buttonManager;
    }
    public long getMlanguageId() {
        return mlanguageId;
    }
    public void setMlanguageId(long mlanguageId) {
        this.mlanguageId = mlanguageId;
        this.buttonManager.setMlanguageID(mlanguageId);
    }
    public void setHashMap(TreeMap hashMap) {
        this.hashMap = hashMap;
    }
    OnHeadlineSelectedListener mCallback;
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(TreeMap hashMap, String choice);
    }
    public void onListItemClick(ListView l, View v, TreeMap hashMap, String choice) {
        mCallback.onArticleSelected(hashMap, choice);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        setContext(container.getContext());
        Bundle bundle = getArguments();
        if (hashMap==null)//beim ersten Mal
            hashMap=new TreeMap();
        //hashMap=new TreeMap();alte
        buttonManager= new ButtonManager(getContext(),
                (LinearLayout)view.findViewById(R.id.outputlabel),
                (WebView) view.findViewById(R.id.webView));
        connectFirebase= new ConnectFirebase();

         if (bundle != null){
            GetDataFirebase( bundle.getString("Fragmentname"),
                    bundle.getLong("Language"), bundle.getInt("MenuID"));
        }
        return view;
        //return inflater.inflate(R.layout.fragment_first, container, false);
    }
    public void GetDataFirebase(String choice, long lang, int MenuId) {
        final DatabaseReference myRef = this.connectFirebase.getDatabaseReference();
        Query query=null;
        mlanguageId =lang;
        mMenuId=MenuId;
        hashMap.put("10", "10");

        //LinearLayout linearLayout = view.findViewById(R.id.outputlabel);
        //linearLayout.removeAllViews();
        IDAO idao;
        switch (choice){
            case "home"://show all topmenues
                query=myRef.orderByChild("pages_lang/0/title");
                idao = new aDAOImplHome(query,"",buttonManager,hashMap,mlanguageId, false);
                idao.ReadDBData_Firebase(view, mCallback);//ReadDBData_Firebase(query, "home");
                mCallback.onArticleSelected(hashMap,"MenuChange");
                break;
            case "progress"://show under menue
                query=myRef.orderByChild("parent_id").equalTo(Integer.parseInt(choice));
                idao = new aDAOImplProgress(query,"",buttonManager,hashMap,mlanguageId, true);
                idao.ReadDBData_Firebase(view, mCallback);
                //ReadDBData_Firebase(query, "progress");  //mMainDetail=true;
                mCallback.onArticleSelected(hashMap,"MenuChange");
                //hashMap.clear();
                break;
            default://linkes menü
                query = myRef.orderByChild("id").equalTo(Integer.parseInt(choice));
                idao = new aDAOImplOne(query, "", buttonManager, hashMap, mlanguageId, false);
                idao.ReadDBData_Firebase(view, null);
                if (choice!="progress") {
                    query = myRef.orderByChild("parent_id").equalTo(Integer.parseInt(choice));
                    idao = new aDAOImplProgress(query, "", buttonManager, hashMap, mlanguageId, false);
                    idao.ReadDBData_Firebase(view, mCallback);
                    mCallback.onArticleSelected(hashMap, "");
                 }
                break;
        }
        //hashMap.clear();

        //this.connectFirebase.close();//nachdem hier ausgeklammert wurde, konnte ih die app im handy starten
    }

   /* private void ReadDBData_Firebase(Query query, String choice) {
        final String select = choice;
        final WebView myWebView = view.findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        final CLanguageID cLanguageID = new CLanguageID();

        query.addChildEventListener(new ChildEventListener() {
            String temp;
            Pages pages;

            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                switch (select){
                    case "home":
                        if (dataSnapshot.child("parent_id").exists() == false) {
                            if (dataSnapshot.child("pages_lang").child(LANGUAGE).child("title").
                                    getValue() != null) {
                                pages = dataSnapshot.getValue(Pages.class);
                                if (pages.getId().toString().equals("89") ||pages.getId().toString().equals("129"))//89 reasomant, 129 news
                                    buttonManager.ButtonCreator(pages, null, hashMap, mlanguageId, mCallback);
                                else
                                    buttonManager.ButtonCreator(pages, pages, hashMap, mlanguageId, mCallback);
                                mCallback.onArticleSelected(buttonManager.getHashMap(),"MenuChange");
                                }
                        }
                         break;
                    case "progress":
                        if (dataSnapshot.child("pages_lang").child(LANGUAGE).
                                child("translate").toString()!=""){
                            temp = (String) dataSnapshot.child("pages_lang").
                                    child(LANGUAGE).child("title").getValue();
                            Pages pages = dataSnapshot.getValue(Pages.class);
                            buttonManager.ButtonCreator(pages, null, hashMap, mlanguageId, mCallback);
                            mCallback.onArticleSelected(buttonManager.getHashMap(),"MenuChange");
                        }
                        break;
                    default:
                        webView.loadData("<p> there is no content available", "text/html", "UTF-8");
                        break;
                }
            }
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(LOG_TAG, "Failed to read value.", error.toException());
            }            ;
        });
    }*/

}

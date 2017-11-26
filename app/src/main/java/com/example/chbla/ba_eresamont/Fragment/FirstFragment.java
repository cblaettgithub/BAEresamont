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
import com.example.chbla.ba_eresamont.Classes.ReadDBHome;
import com.example.chbla.ba_eresamont.Classes.ReadDBProgress;
import com.example.chbla.ba_eresamont.Interface.IDBManager;
import com.example.chbla.ba_eresamont.Classes.ReadDBOne;
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
    OnHeadlineSelectedListener mCallback;
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(TreeMap hashMap);
    }
    public void onListItemClick(ListView l, View v, TreeMap hashMap) {
        mCallback.onArticleSelected(hashMap);
    }
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
    public static final String FRAGMENTNAME ="";     //language = 1 = french, 2 = italy, 3 = english
    public  static final String LANGUAGE="1";
    public  static final String MENUID="0";
    private String LOG_TAG=FirstFragment.class.getSimpleName();
    private ButtonManager buttonManager;
    private LinearLayout linearLayout=null;
    private WebView webView;
    private Pages pages;
    private String mlanguage;
    private String mlang="3";//no value
    private Integer mMenuId;
    private static boolean mMainDetail;

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

        buttonManager= new ButtonManager(getContext(),
                (LinearLayout)view.findViewById(R.id.outputlabel),(WebView) view.findViewById(R.id.webView));
        connectFirebase= new ConnectFirebase();
        hashMap=new TreeMap();

        Log.d(LOG_TAG+":onCreateView Fragment","" + bundle.getString(FRAGMENTNAME));
        if (bundle != null){
            GetDataFirebase( bundle.getString(FRAGMENTNAME),
                    bundle.getString(LANGUAGE), bundle.getInt(MENUID));
        }
        return view;
        //return inflater.inflate(R.layout.fragment_first, container, false);
    }
    private void GetDataFirebase(String choice, String lang, int MenuId) {
        final DatabaseReference myRef = this.connectFirebase.getDatabaseReference();
        Query query=null;
        mlanguage=lang;
        mMenuId=MenuId;
        Log.d(LOG_TAG+":Start:GetdataFirebase", choice+"mID:"+mMenuId);
        IDBManager idbManager;

        LinearLayout linearLayout = view.findViewById(R.id.outputlabel);
        linearLayout.removeAllViews();

        if (choice =="home"){
            mMainDetail=false;
            Log.w(LOG_TAG+":GetDataFirebase:home:", choice);
            query=myRef.orderByChild("pages_lang/0/title");
            //idbManager = new ReadDBHome();
            //idbManager.ReadDBData_Firebase(query,"",buttonManager,view,webView,hashMap,mlang);
            ReadDBData_Firebase(query, "home");
        }
        else{
            if (mMainDetail==false){
                Log.w(LOG_TAG+":GetDataFirebase:else:", choice);
                query=myRef.orderByChild("parent_id").equalTo(Integer.parseInt(choice));
                //idbManager = new ReadDBProgress();
                //idbManager.ReadDBData_Firebase(query,"",buttonManager,view,webView,hashMap,mlang);
                ReadDBData_Firebase(query, "progress");
                mMainDetail=true;
            }
           else{
                Log.w(LOG_TAG+":GetDataFirebase:true:", choice);
                query=myRef.orderByChild("id").equalTo(Integer.parseInt(choice));
                idbManager = new ReadDBOne();
                idbManager.ReadDBData_Firebase(query,"",null,view,webView,hashMap,mlang);
                //ReadDBData_FirebaseOneItem(query);
            }
        }
        hashMap.clear();
        mCallback.onArticleSelected(hashMap);
        this.connectFirebase.close();
    }

    private void ReadDBData_Firebase(Query query, String choice) {

        final String select = choice;
        final WebView myWebView = view.findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        CLanguageID cLanguageID = new CLanguageID("3");

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
                                GetLanguageID(pages, mlanguage);
                                Log.w(LOG_TAG + ":ReadDBData Home", "out");
                                buttonManager.ButtonCreator(pages, pages, hashMap, mlang);
                                mCallback.onArticleSelected(buttonManager.getHashMap());
                            }
                        }
                        break;
                    case "progress":
                        if (dataSnapshot.child("pages_lang").child(LANGUAGE).
                                child("translate").toString()!=""){
                            temp = (String) dataSnapshot.child("pages_lang").
                                    child(LANGUAGE).child("title").getValue();
                            Log.w(LOG_TAG + ":ReadDBData Progres", temp);
                            Pages pages = dataSnapshot.getValue(Pages.class);
                            GetLanguageID(pages, mlanguage);
                            buttonManager.ButtonCreator(pages, null, hashMap, mlang);
                            mCallback.onArticleSelected(buttonManager.getHashMap());
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
    }
    //mlang stands for the destination row
    public void GetLanguageID(Pages pages, String inputlang){
        Log.w(LOG_TAG+":GetLanguageID:Input", inputlang);
        for(int i=0;i<pages.getPages_lang().size();i++){
            if (( String.valueOf(pages.getPages_lang().get(i).getLanguage())).equals(inputlang))
                mlang=Integer.toString(i);
        }
        Log.w(LOG_TAG+":GetLanguageID:", mlang);
    }
}

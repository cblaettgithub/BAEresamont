package com.example.chbla.ba_eresamont.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.chbla.ba_eresamont.Activity.MainActivity;
import com.example.chbla.ba_eresamont.Database.ConnectFirebase;
import com.example.chbla.ba_eresamont.Models.Page_lang;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.TreeMap;


/**
 * Created by chbla on 31.10.2017.
 */
public class FirstFragment extends Fragment {
    OnHeadlineSelectedListener mCallback;
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(TreeMap hashMap);
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
    public static final String FRAGMENTNAME ="";
    private final String pageroot="/Ba_2020/pages/";
    private String LOG_TAG=FirstFragment.class.getSimpleName();

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

        connectFirebase= new ConnectFirebase();
        hashMap=new TreeMap();
        Log.d(LOG_TAG+":onCreateView Fragment","" + bundle.getString(FRAGMENTNAME));
        if (bundle != null)
            GetDataFirebase( bundle.getString(FRAGMENTNAME));
        return view;
        //return inflater.inflate(R.layout.fragment_first, container, false);
    }
    private void GetDataFirebase(String choice) {
        connectFirebase= new ConnectFirebase();
        String select="/Ba_2020/pages/";
        final DatabaseReference myRef =  connectFirebase.getDatabaseReference(select);
        myRef.keepSynced(true);
        Query query=null;

        final WebView myWebView = (WebView) view.findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        Log.d(LOG_TAG+":Start:GetdataFirebase", choice);

        LinearLayout linearLayout = view.findViewById(R.id.outputlabel);
        linearLayout.removeAllViews();

        if (choice =="home"){
            Log.w(LOG_TAG+":GetDataFirebase:home:", choice);
            query=myRef.orderByChild("id");
            query.addChildEventListener(new ChildEventListener(){
                String temp;
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName)
                {
                    if (dataSnapshot.child("parent_id").exists()==false) {
                        if (dataSnapshot.child("pages_lang").child("0").child("title").getValue()!=null){
                            temp = (String) dataSnapshot.child("pages_lang").child("0").child("title").getValue();
                            Log.w(LOG_TAG+":GetData:Parentid :", temp);
                            ButtonCreator(temp, 1, dataSnapshot.getKey());
                        }
                    }
                }
                public void onChildRemoved(DataSnapshot dataSnapshot){
                }
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName){}
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName){}
                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(LOG_TAG, "Failed to read value.", error.toException()); }
            });
            hashMap.clear();
            mCallback.onArticleSelected(hashMap);
        }
        else{
            Log.w(LOG_TAG+":GetDataFirebase:else:", choice);
            query=myRef.orderByChild("parent_id").equalTo(Integer.parseInt(choice));
            hashMap.clear();
            query.addChildEventListener(new ChildEventListener(){
                String temp;
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName)
                {
                    if (dataSnapshot.child("pages_lang").child("0").child("title").getValue()!=null) {
                        temp = (String) dataSnapshot.child("pages_lang").child("0").child("title").getValue();
                        Log.w(LOG_TAG+":GetDataFirebase:!home:", temp);
                        ButtonCreator(temp, 1, dataSnapshot.getKey());
                    }
                    if (dataSnapshot.child("pages_lang").child("0").child("translate").getValue()!=null)
                        myWebView.loadData(dataSnapshot.child("pages_lang").child("0").child("translate").getValue().toString(), "text/html", "UTF-8");
                }
                public void onChildRemoved(DataSnapshot dataSnapshot){
                }
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName){}
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName){}
                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(LOG_TAG, "Failed to read value.", error.toException()); }
            });
        }
    }
    private void ButtonCreator(String buttonname, int id, String key) {
        LinearLayout linearLayout = view.findViewById(R.id.outputlabel);
        Button button = new Button(getContext());
        button.setText(buttonname);
        button.setHeight(40);
        final String finalTemp = key;
        Log.w(LOG_TAG+":ButtonCreator:", finalTemp);
        hashMap.put(key, buttonname);
        button.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              ButtonShowContent(finalTemp);
              mCallback.onArticleSelected(hashMap);
          }
        });
        linearLayout.addView(button);
    }

    private void ButtonShowContent(String key){
        LinearLayout linearLayout = view.findViewById(R.id.outputlabel);
        linearLayout.removeAllViews();
        connectFirebase= new ConnectFirebase();
        String select="/Ba_2020/pages/";
        final DatabaseReference myRef =  connectFirebase.getDatabaseReference(select);
        myRef.keepSynced(true);
        Query queryhtml=myRef.orderByKey().equalTo(key);
        Log.w(LOG_TAG+":ButtonShowContent:", key);

        final WebView myWebView = (WebView) view.findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        queryhtml.addChildEventListener(new ChildEventListener(){
           String temp="";
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName)
            {
                if(dataSnapshot.child("pages_lang").child("0").child("title").getValue()!=null){
                temp = (String) dataSnapshot.child("pages_lang").child("0").child("title").getValue();
                Log.w(LOG_TAG+":GetDataFirebase:!home:", temp);
                ButtonCreator(temp, 1, dataSnapshot.getKey());
            }

            }
            public void onChildRemoved(DataSnapshot dataSnapshot){
            }
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName){}
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName){}
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG:", "Failed to read value.", error.toException()); }
        });
    }
}

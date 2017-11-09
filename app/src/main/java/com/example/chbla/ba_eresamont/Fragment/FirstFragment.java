package com.example.chbla.ba_eresamont.Fragment;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.chbla.ba_eresamont.Database.ConnectFirebase;
import com.example.chbla.ba_eresamont.Models.Page_lang;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chbla on 31.10.2017.
 */
public class FirstFragment extends Fragment {

    private ConnectFirebase connectFirebase;
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
    public static final String FRAGMENTNAME ="text";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        setContext(container.getContext());
        Bundle bundle = getArguments();
        if (bundle != null) {
            GetDataPages();
            switch(bundle.getString(FRAGMENTNAME)){
                case"first":
                    for(int i=0;i<3;i++){
                        ButtonCreator("Guide médical", 1);
                    }
                    break;
                case"second":
                    for(int i=0;i<2;i++){
                        ButtonCreator("Testing", 1);
                    }
                    break;
            }
        }
        return view;
        //return inflater.inflate(R.layout.fragment_first, container, false);
    }

    private void ButtonCreator(String buttonname, int id) {
        Button button = new Button(getContext());
        button.setText(buttonname);
        button.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              GetDataPages();}
        });
        LinearLayout linearLayout = view.findViewById(R.id.outputlabel);
        linearLayout.addView(button);
    }

    private void GetDataPages() {
        final WebView myWebView = (WebView) view.findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //database.setPersistenceEnabled(true);
        connectFirebase= new ConnectFirebase();
        final DatabaseReference myRef =  connectFirebase.getDatabaseReference
                ("/supp_B/pages/5/pages_lang/");

        final ArrayAdapter<ArrayList<String>> adapter;
        final String key="text";
        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1);
        myRef.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName)
            {
                ///supp_B/pages_lang/
                //Page_lang pages = dataSnapshot.getValue(Page_lang.class);
                //myWebView.loadData(pages.getText(), "text/html", "UTF-8");
                //Hinweis jedesmal holt er ein neues items
                HashMap<String, Object> result = (HashMap<String, Object>) dataSnapshot.getValue();
                String output=result.get(key).toString();//nicht in String einlesen <p>
                Log.w("Title:", output);
                myWebView.loadData("<p>Page de test</p>", "text/html", "UTF-8");
            }
            public void onChildRemoved(DataSnapshot dataSnapshot){
                Pages value = dataSnapshot.getValue(Pages.class);
                adapter.remove(new ArrayList<String>());
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName){}
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName){}

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG:", "Failed to read value.", error.toException()); }
        });
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

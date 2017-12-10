package com.example.chbla.ba_eresamont.Classes;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.chbla.ba_eresamont.Activity.MainActivity;
import com.example.chbla.ba_eresamont.Database.ConnectFirebase;
import com.example.chbla.ba_eresamont.Fragment.FirstFragment;
import com.example.chbla.ba_eresamont.Models.Pages;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by chbla on 10.12.2017.
 */

public class ChangeLanuage {
    private String LOG_TAG=MainActivity.class.getSimpleName();
    private ConnectFirebase connectFirebase;
    private TreeMap hashMap;
    private long mlanguageID;
    private Context context;
    private long mparentid;
    NavigationView navigationView;
    final CLanguageID cLanguageID = new CLanguageID();
    public TreeMap getHashMap() {
        return hashMap;    }
    public Context getContext() {
        return context;    }
    CreatingMenuLeft creatingMenuLeft;


    public ChangeLanuage(TreeMap hashMap, long mlanguageID, Context context,NavigationView navigationView) {
        this.hashMap = hashMap;
        this.mlanguageID = mlanguageID;
        this.context = context;
        this.connectFirebase = new ConnectFirebase();
        this.navigationView=navigationView;
        creatingMenuLeft= new CreatingMenuLeft(navigationView);
    }

    public void changeLanguage(LinearLayout linearLayout) {
        final DatabaseReference myRef = connectFirebase.getDatabaseReference();
        Query query = myRef.orderByChild("pages_lang/0/title");
        String parent_id = "0";
        final LinearLayout contentview = linearLayout;
        hashMap.clear();
        final Button button = (Button) contentview.getChildAt(2);
        if (button.getTag().toString() != "0")//wenn parent_id = 0, dann ist parent_id = null-> oberste stfue
            parent_id = button.getTag().toString();        //sonst button in button
        final ArrayList<Button> buttonArrayList = new ArrayList<>();

        switch (parent_id) {//Liste anzeigen
            //we start again the frameset with new language.
            case "0":
                //setHomeAtfirst();
                query.addChildEventListener(new ChildEventListener() {
                    Pages pages;
                    int i = 0;

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                        if (dataSnapshot.child("parent_id").exists() == false) {
                            if (dataSnapshot.child("pages_lang").child("0").child("title").
                                    getValue() != null) {
                                pages = dataSnapshot.getValue(Pages.class);
                                Log.d(LOG_TAG, "Sprache 0:" + mlanguageID);
                                Button button;
                                if (pages != null) {
                                    button = new Button(getContext());
                                    //Button button= (Button)contentview.getChildAt(i);//error null
                                    button.setText(pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTitle());
                                    hashMap.put(String.valueOf(i), button.getText());
                                    buttonArrayList.add(button);
                                    i++;
                                }
                            }
                        }
                        SortButtons(buttonArrayList);
                        for (int i = 0; i < buttonArrayList.size(); i++) {
                            ((Button) contentview.getChildAt(i)).setText(buttonArrayList.get(i).getText());
                        }
                        creatingMenuLeft.creatingMenus("MenuChange", hashMap);

                        //SortButtons(buttonArrayList);
                        //creatingMenus("MenuChange");//Menü updaten
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
                break;
            default://Ein Element anzeigen
                DatabaseReference ref = myRef.getParent();
                query = ref.orderByChild("/pages/");
                query.addChildEventListener(new ChildEventListener() {
                    int i = 0;
                    ArrayList<Button> buttonArrayList;

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                        Button button2 = (Button) contentview.getChildAt(2);
                        long parent_id = Long.valueOf(button2.getTag().toString());
                        buttonArrayList = new ArrayList<>();
                        Button button;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Pages pages1 = data.getValue(Pages.class);
                            if (pages1.getParent_id() != null) {
                                if (pages1.getParent_id() == parent_id) {
                                    button = new Button(getContext());
                                    button.setText(pages1.getPages_lang().get(cLanguageID.getArrayIndex(pages1, mlanguageID)).getTitle());
                                    buttonArrayList.add(button);
                                    //((Button)contentview.getChildAt(i)).setText(pages1.getPages_lang().get(cLanguageID.getArrayIndex(pages1, mlanguageID)).getTitle());
                                   /*if (pages1.getPages_lang()!=null){
                                       button.setText(pages1.getPages_lang().get(cLanguageID.getArrayIndex(pages1, mlanguageID)).getTitle());
                                   }*/
                                    //hashMap.put(String.valueOf(i),button.getText());old 10.12.2017
                                    i++;
                                }
                            }
                        }
                        SortButtons(buttonArrayList);
                        for (int i = 0; i < buttonArrayList.size(); i++) {
                            hashMap.put(String.valueOf(i), buttonArrayList.get(i).getText());
                            ((Button) contentview.getChildAt(i)).setText(buttonArrayList.get(i).getText());
                        }
                        creatingMenuLeft.creatingMenus("MenuChange", hashMap);  //creatingMenus("MenuChange");
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
                break;
        }
    }

    public void refrehMenuLanuager(long parentid){
        final DatabaseReference myRef = connectFirebase.getDatabaseReference();
        Query query=myRef.orderByChild("parent_id").equalTo(parentid);
        //Query query = myRef.orderByChild("pages_lang/0/title");
        hashMap.clear();

        query.addChildEventListener(new ChildEventListener() {
            Pages pages;
            int i = 0;
            boolean temp=true;
            ArrayList<String> StringArrayList = new ArrayList<>();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    Pages pages1 = dataSnapshot.getValue(Pages.class);
                    if (pages1!=null) {
                        hashMap.put(String.valueOf(i), pages1.getPages_lang().get(cLanguageID.getArrayIndex(pages1, mlanguageID)).getTitle());
                        Log.d("ChangelangMenue", pages1.getPages_lang().get(cLanguageID.getArrayIndex(pages1, mlanguageID)).getTitle());
                        i++;
                        StringArrayList.add(pages1.getPages_lang().get(cLanguageID.getArrayIndex(pages1, mlanguageID)).getTitle());
                    }
                for(int i=0;i<StringArrayList.size();i++)
                    hashMap.put(String.valueOf(i), StringArrayList.get(i));
                creatingMenuLeft.creatingMenus("MenuChange", hashMap);
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
    private void SortButtons (ArrayList < Button > buttonArrayList) {
        Collections collections = null;
        collections.sort(buttonArrayList, new ButtonsComparator());
    }        //connectFirebase.close();
}


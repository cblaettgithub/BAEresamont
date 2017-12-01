package com.example.chbla.ba_eresamont.Classes;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

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
import java.util.Comparator;
import java.util.TreeMap;

/**
 * Created by chbla on 22.11.2017.
 */
public class ButtonManager  {

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
    private String mlang="3";//no value
    int i=1;
    private int mlevel =0;//Level 0, Startdesk, Level 1: Subbuttons
    public int getMlevel() {     return mlevel;    }

    public FirstFragment.OnHeadlineSelectedListener mCallback;
    final DatabaseReference myRef = this.connectFirebase.getDatabaseReference();
    public WebView getWebView() {
        return webView;
    }

    public ButtonManager(Context contex, LinearLayout linearLayout, WebView webView, int level ) {
        this.contex = contex;
        this.linearLayout = linearLayout;
        this.webView = webView;
        pagesArrayList=new ArrayList<>();
        this.mlevel=level;
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
        button.setTag("test");
        return button;
    }
    //check if value exist if not work with mlang = 3
    public void ButtonCreator(final Pages pages, final Pages pages2, final TreeMap hashMap,
                              String lang, FirstFragment.OnHeadlineSelectedListener mCallback) {
       this.mlang=lang;
       this.mCallback=mCallback;
       Log.d(LOG_TAG, "ButtonCreator"+pages.getPages_lang().get(Integer.parseInt(mlang)).getTitle());
       if (!pages.getPages_lang().get(Integer.parseInt(mlang)).getTitle().equals("")){
            Button button = this.ConfigButton(pages.getPages_lang().
                    get(Integer.parseInt(mlang)).getTitle(), ((int) pages.getPages_lang().get(Integer.parseInt(mlang)).getId()));
            this.pages=pages;
            this.hashMap=hashMap;
            mlevel =0;
            Log.w(LOG_TAG+":ButtonCreator:", pages.getId().toString());
            hashMap.put(pages.getId().toString(), pages.getPages_lang().get(Integer.parseInt(mlang)).getTitle());

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (pages2==null || mlevel!=0){
                        ButtonShowContent(pages);
                    }
                    else{//create button in button
                        SubButton(pages, hashMap);
                    }
                }
            });
            linearLayout.addView(button);
          // String text=((Button) ((android.view.View[])linearLayout.findViewWithTag("button"))[1]).getText() ;

       }
    }
    private void SubButton(final Pages pages, final TreeMap hashMap) {
        final int parent_id;
        for(int i=0;i<linearLayout.getChildCount();i++)
            linearLayout.removeViewAt(i);
        linearLayout.removeViewAt(0);
        if(linearLayout.getChildCount()>1)
            linearLayout.removeViewAt(1);

        // 2 ok query=myRef.orderByChild("parent_id").equalTo(Integer.parseInt(pages.getId().toString()));
        DatabaseReference ref=myRef.getParent();
        query=ref.orderByChild("/pages/");
        parent_id = Integer.parseInt(pages.getId().toString());
        mlevel =1;
        Log.d(LOG_TAG, "Output Parent-id:"+i);
        //ref.getParent();
        //query = ref;

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot data :dataSnapshot.getChildren()) {
                    //ButtonCreator(dataSnapshot.getValue(Pages.class),  null,hashMap, mlang);
                    //ButtonCreator(data.getValue(Pages.class),  null,hashMap, mlang);

                   // if (data.getValue(Pages.class).getParent_id().equals(i))
                    Pages pages1=data.getValue(Pages.class);
                    if (pages1.getParent_id()!=null) {
                        if (Integer.parseInt(pages1.getParent_id().toString()) == parent_id)
                            pagesArrayList.add(data.getValue(pages.getClass()));
                    }

                }
                Collections.sort(pagesArrayList, new Comparator<Pages>(){
                    public int compare(Pages o1, Pages o2) {
                        int indexA=o1.getPages_lang().get(0).getTitle().indexOf('.');
                        int indexB=o2.getPages_lang().get(0).getTitle().indexOf('.');
                        String title1 =o1.getPages_lang().get(0).getTitle().trim().substring(0,indexA);//.substring(0, '.');
                        String title2 = o2.getPages_lang().get(0).getTitle().trim().substring(0,indexB);;
                        return title1.compareTo(title2);
                    }
                });
                hashMap.clear();
                for(int i=0;i<pagesArrayList.size();i++){
                    ButtonCreator(pagesArrayList.get(i),  null,hashMap, mlang, mCallback);
                    hashMap.put(pages.getId().toString(), pages.getPages_lang().get(Integer.parseInt(mlang)).getTitle());
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
        for(int i=0;i<pagesArrayList.size();i++)
            Log.d(LOG_TAG, "Totalpagearray"+pagesArrayList.get(i).getPages_lang().get(0).getTitle());

    }
    private void ButtonShowContent(Pages pages){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        linearLayout.removeAllViews();
        Log.w(LOG_TAG+":ButtShowContent:mlang:", mlang);
        mlevel =2;
        mCallback.onArticleSelected(hashMap, mlevel);//Menüs aktualisieren
        if (mlang.equals("3")){
            webView.loadData("<p> there is no content available", "text/html", "UTF-8");
        }
        else{
            String temp=pages.getPages_lang().get(Integer.parseInt(mlang)).
                    getTranslate();
            String neu=temp.replaceAll("(\r\n|\n)", "<br />");
            webView.loadData(neu, "text/html", "UTF-8");
        }
    }
}
 class Sortbyroll implements Comparator<Pages>
{
    @Override
    public int compare(Pages o1, Pages o2) {
        String title1 = o1.getPages_lang().get(0).getTitle();
        String title2 = o1.getPages_lang().get(0).getTitle();
        return title1.compareTo(title2);
    }
  }


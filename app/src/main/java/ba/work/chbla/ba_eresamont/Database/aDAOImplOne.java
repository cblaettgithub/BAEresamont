package ba.work.chbla.ba_eresamont.Database;


import ba.work.chbla.ba_eresamont.Classes.ButtonManager;
import ba.work.chbla.ba_eresamont.Classes.ShowContentApp;
import ba.work.chbla.ba_eresamont.Classes.TitleFiller;
import ba.work.chbla.ba_eresamont.Fragment.FirstFragment;
import ba.work.chbla.ba_eresamont.Models.Pages;

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

    String[] mTitleArray=new String[4];
    long mlanguage=mlanuageId;
    public aDAOImplOne(Query query, String choice, ButtonManager buttonManager, TreeMap hashMap, long mlanguage, boolean onetwopage) {
        super(query, choice, buttonManager, hashMap, mlanguage, onetwopage);
    }

    @Override
    public void ReadDBData_Firebase(View view,final FirstFragment.OnHeadlineSelectedListener Callback) {
       final WebView webView = view.findViewById(ba.work.chbla.ba_eresamont.R.id.webView);
       WebSettings webSettings = webView.getSettings();
       webSettings.setJavaScriptEnabled(true);

       this.query.addChildEventListener(new ChildEventListener() {
            String content="", neu="";
            Pages pages;

            //just for one item
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Pages pages=dataSnapshot.getValue(Pages.class);
                final FirstFragment.OnHeadlineSelectedListener mCallback=Callback;
                mTitleArray=new TitleFiller().filltitlearray(pages);
                //filltitlearray(pages);
                if (mCallback!=null)//wies mcallback null ?
                    mCallback.onArticleSelected(hashMap,"MenuChange", mTitleArray);//Menüs aktualisieren
                new ShowContentApp().showContentApp(pages, webView, mlanuageId, true);
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

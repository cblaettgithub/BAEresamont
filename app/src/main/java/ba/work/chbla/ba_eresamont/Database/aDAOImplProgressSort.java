package ba.work.chbla.ba_eresamont.Database;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import ba.work.chbla.ba_eresamont.Classes.ButtonManager;
import ba.work.chbla.ba_eresamont.Fragment.FirstFragment;
import ba.work.chbla.ba_eresamont.Models.Pages;
import ba.work.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.TreeMap;

/**
 * Created by chbla on 27.11.2017.
 */

public class aDAOImplProgressSort extends aDAO {

    public  static final String LANGUAGE="1";

    public aDAOImplProgressSort(Query query, String choice, ButtonManager buttonManager, TreeMap hashMap, long mlanguage, boolean onetwopage) {
        super(query, choice, buttonManager, hashMap, mlanguage, onetwopage);
    }

    @Override
    public void ReadDBData_Firebase(View view,FirstFragment.OnHeadlineSelectedListener Callback) {
       final WebView webView = view.findViewById(R.id.webView);
       WebSettings webSettings = webView.getSettings();
       webSettings.setJavaScriptEnabled(true);
       final FirstFragment.OnHeadlineSelectedListener mCallback=Callback;

       this.query.addChildEventListener(new ChildEventListener() {
            Pages pages;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.child("pages_lang").child(LANGUAGE).
                        child("translate").toString()!="" && dataSnapshot.child("parent_id")!=null){
                    Pages pages = dataSnapshot.getValue(Pages.class);
                    buttonManager.ButtonCreator(pages, null,hashMap, cLanguageID.getArrayIndex(pages, mlanuageId), null);
                    buttonManager.sortButtonsProgress();
                   mCallback.onArticleSelected(buttonManager.getHashMap(),"MenuChange", null);
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

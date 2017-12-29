package ba.work.chbla.ba_eresamont.Database;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import ba.work.chbla.ba_eresamont.Classes.ButtonManager;
import ba.work.chbla.ba_eresamont.Classes.ButtonsComparator;
import ba.work.chbla.ba_eresamont.Fragment.FirstFragment;
import ba.work.chbla.ba_eresamont.Models.Pages;
import ba.work.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.Collections;
import java.util.TreeMap;

/**
 * Created by chbla on 27.11.2017.
 */

public class aDAOImplHome extends aDAO {
    public  static final String LANGUAGE="1";
    public aDAOImplHome(Query query, String choice, ButtonManager buttonManager, TreeMap hashMap, long mlanguage, boolean onetwopage) {
        super(query, choice, buttonManager, hashMap, mlanguage, onetwopage);
    }

    @Override
    public void ReadDBData_Firebase(View view, FirstFragment.OnHeadlineSelectedListener Callback) {
        final WebView webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        final FirstFragment.OnHeadlineSelectedListener mCallback=Callback;

        query.addChildEventListener(new ChildEventListener() {
            String temp;
            Pages pages;
            Collections collections = null;
            //we call the buttonmanager, after we call the method sort of buttonmanager
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.child("parent_id").exists() == false) {
                    if (dataSnapshot.child("pages_lang").child(LANGUAGE).child("title").
                            getValue() != null)               {
                        pages = dataSnapshot.getValue(Pages.class);
                        if (pages.getId().toString().equals("89") ||pages.getId().toString().equals("129"))//89 reasomant, 129 news
                            buttonManager.ButtonCreator(pages, null, hashMap, mlanuageId, mCallback);
                        else
                          buttonManager.ButtonCreator(pages, pages, hashMap, mlanuageId, mCallback);
                        buttonManager.sortButtonsProgress();
                        mCallback.onArticleSelected(buttonManager.getHashMap(),"MenuChange", "");

                    }
                }
                //buttonManager.sortButtonsProgress();
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

package com.example.chbla.ba_eresamont.Database;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.example.chbla.ba_eresamont.Classes.ButtonManager;
import com.example.chbla.ba_eresamont.Classes.CLanguageID;
import com.example.chbla.ba_eresamont.Interface.IDAO;
import com.google.firebase.database.Query;

import java.util.TreeMap;

/**
 * Created by chbla on 27.11.2017.
 */

public abstract class aDAO   implements IDAO {
    String LOG_TAG=aDAO.class.getSimpleName();
    CLanguageID cLanguageID = new CLanguageID();
    public Query query;
    String choice;
    ButtonManager buttonManager;
    TreeMap hashMap;
    long mlanuageId;

    /*public OnHeadlineSelectedListener mCallback;
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(TreeMap hashMap);
    }
    public void onListItemClick(ListView l, View v, TreeMap hashMap) {
        mCallback.onArticleSelected(hashMap);
    }*/


    public aDAO(Query query, String choice, ButtonManager buttonManager, TreeMap hashMap, long mlanguage) {
        this.query = query;
        this.choice = choice;
        this.buttonManager = buttonManager;
        this.hashMap = hashMap;
        this.mlanuageId = mlanguage;
    }
}

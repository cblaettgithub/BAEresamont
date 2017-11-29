package com.example.chbla.ba_eresamont.Interface;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.chbla.ba_eresamont.Classes.ButtonManager;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by chbla on 26.11.2017.
 */

public interface IDBManager {
     void ReadDBData_Firebase(Query query, String choice,
                                final ButtonManager buttonManager, View view, final WebView webView,
                                final TreeMap hashMap, String mlanguage);

}

package com.example.chbla.ba_eresamont.Interface;

import android.app.Activity;
import android.view.View;
import android.webkit.WebView;

import com.example.chbla.ba_eresamont.Fragment.FirstFragment;

/**
 * Created by chbla on 27.11.2017.
 */

public interface IDAO {
    void ReadDBData_Firebase(View view,FirstFragment.OnHeadlineSelectedListener Callback);

}

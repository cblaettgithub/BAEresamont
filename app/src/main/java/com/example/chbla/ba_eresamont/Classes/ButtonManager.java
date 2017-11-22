package com.example.chbla.ba_eresamont.Classes;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.chbla.ba_eresamont.Models.Pages;

import java.util.TreeMap;

/**
 * Created by chbla on 22.11.2017.
 */
public class ButtonManager {
    private String LOG_TAG=ButtonManager.class.getSimpleName();
    private Context contex;
    public static final String LANGUAGE="0";
    private LinearLayout linearLayout=null;
    private TreeMap hashMap;
    private Pages pages;
    private WebView webView;

    public ButtonManager(Context contex, LinearLayout linearLayout, WebView webView) {
        this.contex = contex;
        this.linearLayout = linearLayout;
        this.webView = webView;
    }

    public TreeMap getHashMap() { return hashMap;   }
    public void setHashMap(TreeMap hashMap) {this.hashMap = hashMap;  }
    public LinearLayout getLinearLayout() { return linearLayout;   }
    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;   }
    public Context getContex() {    return contex;    }

    public Button ConfigButton(String buttonname) {
        Button button = new Button(this.getContex());
        button.setText(buttonname);
        button.setHeight(40);
        return button;
    }

    public void ButtonCreator(final Pages pages, final Pages pages2, final TreeMap hashMap) {
        Button button = this.ConfigButton(pages.getPages_lang().
                get(Integer.parseInt(LANGUAGE)).getTitle());
        this.pages=pages;
        this.hashMap=hashMap;
        Log.w(LOG_TAG+":ButtonCreator:", pages.getId().toString());
        hashMap.put(pages.getId().toString(), pages.getPages_lang().get(0).getTitle());
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (pages2==null){
                    ButtonShowContent();
                }
                else{//create button in button
                    for(int i=0;i<linearLayout.getChildCount();i++)
                    linearLayout.removeViewAt(i);
                    linearLayout.removeViewAt(0);
                    linearLayout.removeViewAt(1);
                    for (int i=0;i<4;i++)
                        ButtonCreator(pages, null, hashMap);
                }
            }
        });
        linearLayout.addView(button);
    }
    private void ButtonShowContent(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        linearLayout.removeAllViews();
        webView.loadData(this.pages.getPages_lang().get(Integer.parseInt(LANGUAGE)).
                getTranslate().toString(), "text/html", "UTF-8");
    }
}

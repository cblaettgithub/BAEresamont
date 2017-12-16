package com.example.chbla.ba_eresamont.Classes;

import android.webkit.WebView;

import com.example.chbla.ba_eresamont.Models.Pages;

/**
 * Created by chbla on 16.12.2017.
 */

public class ShowContentApp {
    CLanguageID cLanguageID = new CLanguageID();
    public ShowContentApp() {
    }
    public void showContentApp(Pages pages, WebView webView, long mlanguageID) {
        String content;
        if (pages!=null){
            content = pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTranslate().toString();
            //neu = setCorrectContent(content);
            webView.setTag(pages.getId());
            if (pages.getParent_id()!=null){
                if (pages.getParent_id()==87){//only boite a outil
                    webView.setInitialScale(1);
                    webView.getSettings().setLoadWithOverviewMode(true);
                    webView.getSettings().setUseWideViewPort(true);
                }
            }
            if (pages.getId()==100)
                content=new ContentCorrecter(content).removeComments();
            content=new ContentCorrecter(content).contentEscapeProcessing();
            webView.loadData(content, "text/html", "UTF-8");
        }
    }
}

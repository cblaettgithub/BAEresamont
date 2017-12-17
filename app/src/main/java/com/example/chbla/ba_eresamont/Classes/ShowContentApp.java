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
    public void showContentApp(Pages pages, WebView webView, long mlanguageID, boolean tagModus) {
        String content;
        if (pages!=null){
            content = pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTranslate().toString();
            if (tagModus)
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
            if (pages.getId()==95 || pages.getId()==100 || pages.getId()==113)
                webView.reload();
             /*pages.getId()==33 || pages.getId()==34 || pages.getId()==103||pages.getId()==118||
                    pages.getId()==122 || pages.getId()==107 || pages.getId()==91 ||pages.getId()==109*/

        }
    }
}

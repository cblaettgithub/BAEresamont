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
        String content="";
        Long parentid;
        if (pages!=null){
            content = pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTranslate().toString();
            if (tagModus)
                webView.setTag(pages.getId());
            if (pages.getParent_id()!=null ){//89 le project
                parentid=pages.getParent_id();// 100 = oxygen, 85=medical guide
                if (parentid==87 || pages.getId()==100 ) {//only boite a outil, oxygen algot
                    webView.setInitialScale(1);
                    webView.getSettings().setLoadWithOverviewMode(true);
                    webView.getSettings().setUseWideViewPort(true);
                }
            }
            if (pages.getId()==100)
                content=new ContentCorrecter(content).removeComments();
            if (excluding((pages.getId()))) {//Otherwise i got a backgroumd text under questionaire and evaluation de sante
                content = new ContentCorrecter(content).contentEscapeProcessing();
                webView.loadData(content, "text/html", "UTF-8");
                if (pages.getId() == 95 || pages.getId() == 100 || pages.getId() == 113)
                    webView.reload();
            }
             /*pages.getId()==33 || pages.getId()==34 || pages.getId()==103||pages.getId()==118||
                    pages.getId()==122 || pages.getId()==107 || pages.getId()==91 ||pages.getId()==109*/

        }

    }
    public boolean excluding(long id)
    {
        boolean check=true;
        if (id==126)
            check=false;
        if (id ==86)
            check=false;
        return check;
    }
}

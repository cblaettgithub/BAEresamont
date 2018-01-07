package ba.work.chbla.ba_eresamont.Classes;

import ba.work.chbla.ba_eresamont.Models.Pages;

/**
 * Created by chbla on 07.01.2018.
 */

public class TitleFiller {
    Pages pages;
    private long mlanguageID;//no value
    private String[] mTitleArray=new String[4];
    CLanguageID cLanguageID = new CLanguageID();
    public TitleFiller(Pages pages) {
        this.pages = pages;
    }
    public TitleFiller() {
    }
    //we read here all language title (3 titles)//french, italien, english
    //fill into this array, arrayindex 1 for french ,2 for italian, 3 for english
    public String[] filltitlearray(Pages pages){
        int rowid =(int)mlanguageID;
        mTitleArray[rowid]=pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTitle();//Titel oben
        int i=1;
        for(long j=1;j<=3;j++){
            mTitleArray[i]=pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, j)).getTitle();
            i++;
        }
        return mTitleArray;
    }
}

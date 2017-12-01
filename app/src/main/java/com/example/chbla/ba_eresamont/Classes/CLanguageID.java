package com.example.chbla.ba_eresamont.Classes;

import android.util.Log;

import com.example.chbla.ba_eresamont.Models.Pages;

/**
 * Created by chbla on 26.11.2017.
 */

public class CLanguageID {
    private String mlang;

    public CLanguageID(String mlang) {
        this.mlang = mlang;
    }
    public String GetLanguageID(Pages pages, String inputlang){
        for(int i=0;i<pages.getPages_lang().size();i++){
            if (( String.valueOf(pages.getPages_lang().get(i).getLanguage())).equals(inputlang))
                mlang=Integer.toString(i);
        }
        return mlang;
    }
}

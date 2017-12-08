package com.example.chbla.ba_eresamont.Classes;

import android.util.Log;

import com.example.chbla.ba_eresamont.Models.Pages;

/**
 * Created by chbla on 26.11.2017.
 */

public class CLanguageID {
    private int mlang;//setted language 1 French, 2 Italian 3 Englisch
    public CLanguageID(){};
 //check the array of pages-lang which item of array has the number
    public int GetLanguageID(Pages pages, int inputlang){
        for(int i=0;i<pages.getPages_lang().size();i++){
            if (pages.getPages_lang().get(i).getLanguage()==inputlang)
                mlang=i;
        }
        return mlang;
    }
}

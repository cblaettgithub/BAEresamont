package ba.work.chbla.ba_eresamont.Classes;
import ba.work.chbla.ba_eresamont.Models.Pages;

/**
 * Created by chbla on 26.11.2017.
 */
//we get the languaged id, with that we get the arrayindes of the language
public class CLanguageID {
    private int mArrayIndex;//setted language 1 French, 2 Italian 3 Englisch
    public CLanguageID(){}

    //check the array of pages-lang which item of array has the number
    public int getArrayIndex(Pages pages, long languageID){
        for(int i=0;i<pages.getPages_lang().size();i++){
            if (pages.getPages_lang().get(i).getLanguage()==languageID)
                mArrayIndex =i;
        }
        return mArrayIndex;
    }
}

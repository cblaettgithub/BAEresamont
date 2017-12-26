package ba.work.chbla.ba_eresamont.Classes;

import ba.work.chbla.ba_eresamont.Models.Pages;

import java.util.Comparator;

/**
 * Created by chbla on 10.12.2017.
 */

class Sortbyroll implements Comparator<Pages>
{
    private long mlanguageID;
    CLanguageID cLanguageID= new CLanguageID();

    public Sortbyroll(long mlanguageID) {
        this.mlanguageID = mlanguageID;
    }

    @Override
    public int compare(Pages o1, Pages o2) {
        int result;
        String title1 = o1.getPages_lang().get(cLanguageID.getArrayIndex(o1, mlanguageID)).getTitle();//new
        String title2 = o2.getPages_lang().get(cLanguageID.getArrayIndex(o2, mlanguageID)).getTitle();//new
        if ( title1.toString().contains(".")&& title2.toString().contains(".")){
            int indexA = title1.toString().indexOf('.');
            int indexB = title2.toString().indexOf('.');
            int a=Integer.parseInt(title1.substring(0, indexA));
            int b=Integer.parseInt(title2.substring(0, indexB));
            result= a-b;
        }
        else{
            result= title1.compareTo(title2);
        }

        return result;
    }
}

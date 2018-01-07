package ba.work.chbla.ba_eresamont.Classes;

import android.widget.Button;

import java.util.Comparator;

/**
 * Created by chbla on 10.12.2017.
 */

public class ButtonsComparator implements Comparator<Button> {

    public ButtonsComparator(){}

    public int compare(Button o1, Button o2) {
        int result;
        String title1 = o1.getText().toString();
        String title2 = o2.getText().toString();
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


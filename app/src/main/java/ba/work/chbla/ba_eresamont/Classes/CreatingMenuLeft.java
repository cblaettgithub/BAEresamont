package ba.work.chbla.ba_eresamont.Classes;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import ba.work.chbla.ba_eresamont.R;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by chbla on 10.12.2017.
 */
//we set here the mainmenue, if the hashvalue is null we do nothing,
// after that we sorted the hashmap with the valuecomparator
// we set the menue with the key and the content of the hashmap
 //this method from several in the classes of this app
public class CreatingMenuLeft extends AppCompatActivity {
    NavigationView navigationView;
    public CreatingMenuLeft(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    public CreatingMenuLeft() {
    }

    public void creatingMenus(String choice, TreeMap hashMap) {
        switch (choice){
            case "MenuChange":
                Menu menushow= this.navigationView.getMenu();
                menushow.clear();
                menushow.add(0, R.id.fragment_zero , 1,
                        "Home").setIcon(R.drawable.ic_menu_gallery);
                Map sortedMap = new TreeMap(new ValueComparator(hashMap));
                if (sortedMap!=null){
                    sortedMap.putAll(hashMap);
                    Iterator iterator=sortedMap.entrySet().iterator();
                    while(iterator.hasNext()){//neu
                        Map.Entry<String, String> entry =   (Map.Entry<String, String>) iterator.next();
                        if (!entry.getValue().equals("")){
                            menushow.add(0, Integer.parseInt(entry.getKey()) , 1,
                                    entry.getValue()).setIcon(R.drawable.ic_menu_gallery);}
                    }
                }
                break;
            default:
                break;
        }
    }
}

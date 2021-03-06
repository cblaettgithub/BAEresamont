package ba.work.chbla.ba_eresamont.Database;

import ba.work.chbla.ba_eresamont.Classes.ButtonManager;
import ba.work.chbla.ba_eresamont.Classes.CLanguageID;
import ba.work.chbla.ba_eresamont.Interface.IDAO;
import com.google.firebase.database.Query;

import java.util.TreeMap;

/**
 * Created by chbla on 27.11.2017.
 */

public abstract class aDAO   implements IDAO {
    String LOG_TAG=aDAO.class.getSimpleName();
    CLanguageID cLanguageID = new CLanguageID();
    public Query query;
    String choice;
    ButtonManager buttonManager;
    TreeMap hashMap;
    long mlanuageId;
    boolean monetwopages;

    public aDAO(Query query, String choice, ButtonManager buttonManager, TreeMap hashMap, long mlanguage, boolean onetwopage) {
        this.query = query;
        this.choice = choice;
        this.buttonManager = buttonManager;
        this.hashMap = hashMap;
        this.mlanuageId = mlanguage;
        this.monetwopages=onetwopage;
    }
}

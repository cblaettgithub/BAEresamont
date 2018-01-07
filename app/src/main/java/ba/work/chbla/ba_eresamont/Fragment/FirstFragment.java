package ba.work.chbla.ba_eresamont.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import java.util.TreeMap;
import ba.work.chbla.ba_eresamont.Activity.MainActivity;
import ba.work.chbla.ba_eresamont.Classes.ButtonManager;
import ba.work.chbla.ba_eresamont.Database.ConnectFirebase;
import ba.work.chbla.ba_eresamont.Database.aDAOImplHome;
import ba.work.chbla.ba_eresamont.Database.aDAOImplOne;
import ba.work.chbla.ba_eresamont.Database.aDAOImplProgress;
import ba.work.chbla.ba_eresamont.Database.aDAOImplProgressSort;
import ba.work.chbla.ba_eresamont.Interface.IDAO;
import ba.work.chbla.ba_eresamont.Models.Pages;

/**
 * Created by chbla on 31.10.2017.
 */
public class FirstFragment extends Fragment {
    private TreeMap hashMap;
    private ConnectFirebase connectFirebase;
    private DatabaseReference databaseReference;
    Context context;
    public void setContext(Context context) {
        this.context = context;
    }
    private View view;
    @Nullable
    @Override
    public View getView() {
        return view;
    }
    public void setView(View view) {
        this.view = view;
    }
    private String LOG_TAG=FirstFragment.class.getSimpleName();
    private ButtonManager buttonManager;
    private LinearLayout linearLayout=null;
    private WebView webView;
    private Pages pages;
    public long mlanguageId;
    private static String LANGUAGE="0";
    private Integer mMenuId;
    private static boolean mMainDetail;
    public ButtonManager getButtonManager() {
        return buttonManager;
    }
    public long getMlanguageId() {
        return mlanguageId;
    }
    public void setMlanguageId(long mlanguageId) {
        this.mlanguageId = mlanguageId;
        this.buttonManager.setMlanguageID(mlanguageId);
    }
    public void setHashMap(TreeMap hashMap) {
        this.hashMap = hashMap;
    }
    OnHeadlineSelectedListener mCallback;
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(TreeMap hashMap, String choice, String[] mTitle);
    }
    public void onListItemClick(ListView l, View v, TreeMap hashMap, String choice) {
        mCallback.onArticleSelected(hashMap, choice, null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(ba.work.chbla.ba_eresamont.R.layout.fragment_first, container, false);
        setContext(container.getContext());
        Bundle bundle = getArguments();
        if (hashMap==null)//beim ersten Mal
        hashMap=new TreeMap();
        buttonManager= new ButtonManager(getContext(),
                (LinearLayout)view.findViewById(ba.work.chbla.ba_eresamont.R.id.outputlabel),
                (WebView) view.findViewById(ba.work.chbla.ba_eresamont.R.id.webView));
        connectFirebase= new ConnectFirebase();

         if (bundle != null){
            GetDataFirebase( bundle.getString("Fragmentname"),
                    bundle.getLong("Language"), bundle.getInt("MenuID"));
        }
        return view;
        //return inflater.inflate(R.layout.fragment_first, container, false);
    }
    public void GetDataFirebase(String choice, long lang, int MenuId) {
        final DatabaseReference myRef = this.connectFirebase.getDatabaseReference();
        Query query=null;
        mlanguageId =lang;
        mMenuId=MenuId;
        //we switch between the home, progress and default
        //home stand for the homebutton, progess shows the undermenue
        //default we will start when we click the left menue
        IDAO idao;
        switch (choice){
            case "home"://show all topmenues
                query=myRef.orderByChild("pages_lang/0/title");
                buttonManager.setaDaoName("leftStart");
                idao = new aDAOImplHome(query,"",buttonManager,hashMap,mlanguageId, false);
                idao.ReadDBData_Firebase(view, mCallback);//ReadDBData_Firebase(query, "home");
                 //mCallback.onArticleSelected(hashMap,"MenuChange");
                break;
            case "progress"://show under menue
                query=myRef.orderByChild("parent_id").equalTo(Integer.parseInt(choice));
                idao = new aDAOImplProgress(query,"",buttonManager,hashMap,mlanguageId, false);
                idao.ReadDBData_Firebase(view, mCallback);
                break;
            default://left menue start
                query = myRef.orderByChild("id").equalTo(Integer.parseInt(choice));//with(Mainact) mcallback we get bach the menueitems
                idao = new aDAOImplOne(query, "", buttonManager, ((MainActivity) mCallback).getHashMap(), mlanguageId, false);
                idao.ReadDBData_Firebase(view, mCallback);
                if (choice!="progress") {
                    buttonManager.setaDaoName("leftStart");
                    query = myRef.orderByChild("parent_id").equalTo(Integer.parseInt(choice));
                    idao = new aDAOImplProgressSort(query, "", buttonManager, hashMap, mlanguageId, false);
                    idao.ReadDBData_Firebase(view, mCallback);
                 }
                break;
        }
        //this.connectFirebase.close();//nachdem hier ausgeklammert wurde, konnte ih die app im handy starten
    }


}

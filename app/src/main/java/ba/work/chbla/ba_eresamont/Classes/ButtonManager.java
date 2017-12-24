package ba.work.chbla.ba_eresamont.Classes;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import ba.work.chbla.ba_eresamont.Database.ConnectFirebase;
import ba.work.chbla.ba_eresamont.Fragment.FirstFragment;
import ba.work.chbla.ba_eresamont.Models.Pages;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

/**
 * Created by chbla on 22.11.2017.
 */
public class ButtonManager  {

    private String LOG_TAG=ButtonManager.class.getSimpleName();
    private Context contex;
    public static final String LANGUAGE="3";
    //language 1= french, 2= italy, 3= english
    private LinearLayout linearLayout=null;
    private TreeMap hashMap;
    private Pages pages;
    private WebView webView;
    private ArrayList<Pages> pagesArrayList;
    ConnectFirebase connectFirebase=new ConnectFirebase();
    CLanguageID cLanguageID = new CLanguageID();
    Query query=null;
    private long mlanguageID;//no value
    int i=1;
    private String aDaoName;
    public void setaDaoName(String aDaoName) {
        this.aDaoName = aDaoName;
    }
    public String getaDaoName() {
        return aDaoName;
    }
    public void setMlanguageID(long mlanguageID) {
        this.mlanguageID = mlanguageID;
    }
    public FirstFragment.OnHeadlineSelectedListener mCallback;
    final DatabaseReference myRef = this.connectFirebase.getDatabaseReference();
    public WebView getWebView() {
        return webView;
    }
    final ArrayList<Button> buttonArrayList = new ArrayList<>();
    private String mTitle;

    public ButtonManager(Context contex, LinearLayout linearLayout, WebView webView) {
        this.contex = contex;
        this.linearLayout = linearLayout;
        this.webView = webView;
        pagesArrayList=new ArrayList<>();
    }

    public TreeMap getHashMap() { return hashMap;   }
    public void setHashMap(TreeMap hashMap) {this.hashMap = hashMap;  }
    public LinearLayout getLinearLayout() { return linearLayout;   }
    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;   }
    public Context getContex() {    return contex;    }

    public Button ConfigButton(Pages pages) {
        String parent_id="0";
        Button button = new Button(this.getContex());
        button.setShadowLayer(2, 1, 1, 60);

        button.setText(pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTitle());//pages.getPages_lang().get(Integer.parseInt(mlanguageID)).getTitle()
        button.setHeight(40);
        button.setId((int) pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getId());// ((int) pages.getPages_lang().get(Integer.parseInt(mlanguageID)).getId())
        if (pages.getParent_id()!=null)
            parent_id=pages.getParent_id().toString();
        button.setTag(parent_id);//(Integer.parseInt(pages.getParent_id().toString()))
        return button;
    }
    //check if value exist if not work with mlanguageID = 3
    public void ButtonCreator(final Pages pages, final Pages pages2, final TreeMap hashMap,//language will we give
                              long mlanguageID, FirstFragment.OnHeadlineSelectedListener mCallback) {
       this.mlanguageID =mlanguageID;//Sprache wird mitgegeben respr korrekter mlanguageID
       this.mCallback=mCallback;
        Collections collections = null;
        if (!pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTitle().equals("")){
            Button button = this.ConfigButton(pages);
            this.pages=pages;
            this.hashMap=hashMap;
            hashMap.put(pages.getId().toString(), pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTitle());
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (pages2==null ){
                        ButtonShowContent(pages);
                    }
                    else{//create button in button
                        SubButton(pages);
                    }
                }
            });
            if (aDaoName=="leftStart")  //after add, probleme i also want to sort from left menu
                buttonArrayList.add(button);
            else
                linearLayout.addView(button);
        }
        for(int i=0;i<pagesArrayList.size();i++)
            hashMap.put(pages.getId().toString(), pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTitle());

    }
    private void SubButton(final Pages pages) {
        final int parent_id;
        mTitle=pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTitle();//Titel oben
        Log.d(LOG_TAG, "Title:"+mTitle);
        for(int i=0;i<linearLayout.getChildCount()+1;i++)
            linearLayout.removeViewAt(i);
        linearLayout.removeViewAt(0);
        if(linearLayout.getChildCount()>1){
            linearLayout.removeViewAt(1);
            linearLayout.removeViewAt(0);
        }
        //linearLayout.removeAllViewsInLayout();;
        DatabaseReference ref=myRef.getParent();
        query=ref.orderByChild("/pages/");
        parent_id = Integer.parseInt(pages.getId().toString());
        query.addChildEventListener(new ChildEventListener() {
            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot data :dataSnapshot.getChildren()) {
                   // if (data.getValue(Pages.class).getParent_id().equals(i))
                    Pages pages1=data.getValue(Pages.class);
                      /*if (pages1.getId()==86 || pages1.getId()==126){
                        webView.setTop(550);//for querstion +evaluation de sante
                        new ShowContentApp().showContentApp(pages1, webView, mlanguageID, true);
                    }*/
                    if (pages1.getParent_id()!=null ) {
                        if (Integer.parseInt(pages1.getParent_id().toString()) == parent_id)
                            pagesArrayList.add(data.getValue(pages.getClass()));
                    }
                }
                Collections.sort(pagesArrayList, new Sortbyroll());
                hashMap.clear();
                for(int i=0;i<pagesArrayList.size();i++){
                    ButtonCreator(pagesArrayList.get(i),  null,hashMap, mlanguageID, mCallback);
                    //hashMap.put(pages.getId().toString(), pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTitle());
                }
                mCallback.onArticleSelected(null,"", mTitle);// /settile
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {            }
            @Override
            public void onCancelled(DatabaseError databaseError) {            }
        });
    }
    public void sortButtonsProgress(){
        Collections.sort(buttonArrayList, new ButtonsComparator());
        linearLayout.removeAllViewsInLayout();;
        for(int i=0;i<buttonArrayList.size();i++)
            linearLayout.addView(buttonArrayList.get(i));
    }
    private void ButtonShowContent(Pages pages){//problem hashmap nicht aktualisert
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        linearLayout.removeAllViews();
        //tittel ändern
        mTitle=pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTitle();//Titel oben
        Log.d(LOG_TAG, "Title:"+mTitle);

        new ShowContentApp().showContentApp(pages, webView, mlanguageID, true);
        if (mCallback!=null)//wies mcallback null ?
            mCallback.onArticleSelected(hashMap,"MenuChange", mTitle);//Menüs aktualisieren
    }


}



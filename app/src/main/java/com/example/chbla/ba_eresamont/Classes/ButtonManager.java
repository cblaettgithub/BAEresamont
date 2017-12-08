package com.example.chbla.ba_eresamont.Classes;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.chbla.ba_eresamont.Database.ConnectFirebase;
import com.example.chbla.ba_eresamont.Fragment.FirstFragment;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

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
    Query query=null;
    private String mlang="1";//no value
    int i=1;

    public FirstFragment.OnHeadlineSelectedListener mCallback;
    final DatabaseReference myRef = this.connectFirebase.getDatabaseReference();
    public WebView getWebView() {
        return webView;
    }

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
        button.setText(pages.getPages_lang().get(Integer.parseInt(mlang)).getTitle());//pages.getPages_lang().get(Integer.parseInt(mlang)).getTitle()
        button.setHeight(40);
        button.setId((int) pages.getPages_lang().get(Integer.parseInt(mlang)).getId());// ((int) pages.getPages_lang().get(Integer.parseInt(mlang)).getId())
        if (pages.getParent_id()!=null)
            parent_id=pages.getParent_id().toString();
        button.setTag(parent_id);//(Integer.parseInt(pages.getParent_id().toString()))
        return button;
    }
    //check if value exist if not work with mlang = 3
    public void ButtonCreator(final Pages pages, final Pages pages2, final TreeMap hashMap,
                              String lang, FirstFragment.OnHeadlineSelectedListener mCallback) {
       this.mlang=lang;
       this.mCallback=mCallback;
       Log.d(LOG_TAG, "ButtonCreator"+pages.getPages_lang().get(Integer.parseInt(mlang)).getTitle());
       if (!pages.getPages_lang().get(Integer.parseInt(mlang)).getTitle().equals("")){
            Button button = this.ConfigButton(pages);
            this.pages=pages;
            this.hashMap=hashMap;
            Log.w(LOG_TAG+":ButtonCreator:", pages.getId().toString());
            hashMap.put(pages.getId().toString(), pages.getPages_lang().get(Integer.parseInt(mlang)).getTitle());

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (pages2==null ){
                        ButtonShowContent(pages);
                    }
                    else{//create button in button
                        SubButton(pages, hashMap);
                    }
                }
            });
            linearLayout.addView(button);
          // String text=((Button) ((android.view.View[])linearLayout.findViewWithTag("button"))[1]).getText() ;

       }
    }
    private void SubButton(final Pages pages, final TreeMap hashMap) {
        final int parent_id;
        for(int i=0;i<linearLayout.getChildCount();i++)
            linearLayout.removeViewAt(i);
        linearLayout.removeViewAt(0);
        if(linearLayout.getChildCount()>1){
            linearLayout.removeViewAt(1);
        }

        // 2 ok query=myRef.orderByChild("parent_id").equalTo(Integer.parseInt(pages.getId().toString()));
        DatabaseReference ref=myRef.getParent();
        query=ref.orderByChild("/pages/");
        parent_id = Integer.parseInt(pages.getId().toString());
        Log.d(LOG_TAG, "Output Parent-id:"+i);
        //ref.getParent();
        //query = ref;

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot data :dataSnapshot.getChildren()) {
                   // if (data.getValue(Pages.class).getParent_id().equals(i))
                    Pages pages1=data.getValue(Pages.class);
                    if (pages1.getParent_id()!=null) {
                        if (Integer.parseInt(pages1.getParent_id().toString()) == parent_id)
                            pagesArrayList.add(data.getValue(pages.getClass()));
                    }
                }
                Collections.sort(pagesArrayList, new Sortbyroll());
                hashMap.clear();
                for(int i=0;i<pagesArrayList.size();i++){
                    ButtonCreator(pagesArrayList.get(i),  null,hashMap, mlang, mCallback);
                    hashMap.put(pages.getId().toString(), pages.getPages_lang().get(Integer.parseInt(mlang)).getTitle());
                }
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
        for(int i=0;i<pagesArrayList.size();i++)
            Log.d(LOG_TAG, "Totalpagearray"+pagesArrayList.get(i).getPages_lang().get(0).getTitle());

    }
    private void ButtonShowContent(Pages pages){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        linearLayout.removeAllViews();
        Log.w(LOG_TAG+":ButtShowContent:mlang:", mlang);
        mCallback.onArticleSelected(hashMap,"MenuChange");//Menüs aktualisieren
        webView.setTag(pages.getId());
        String converted;
        String content="";
        String neu;
        content=pages.getPages_lang().get(Integer.parseInt(mlang)).getTranslate().toString();
        //if (content=="")
         //   content=pages.getPages_lang().get(Integer.parseInt(mlang)).getPlaintext().toString();
        neu = setCorrectContent(content);
        webView.loadData( neu, "text/html", "UTF-8");
    }

    @NonNull
    private String setCorrectContent(String content) {
        String neu;
        neu=content.replaceAll("style=", "style=\"").toString();
        neu=neu.replaceAll(";>", ";\">").toString();
        neu=neu.replaceAll("src=", "src=\"").toString();
        neu=neu.replaceAll("alt", "\" alt").toString();
        return neu;
    }

    private String contentTranslateProcessing(Pages pages){
        String content=pages.getPages_lang().get(Integer.parseInt(mlang)).
                getTranslate();

        Document doc = Jsoup.parse(content);
        Elements all=doc.getAllElements();
        String document=doc.text().replaceAll("\n" ,"");
        String html;

        for(int i=0;i<all.size();i++){
            //all.get(i).childNode(0).outerHtml().replaceAll("\n", "");
            //TextNode textNode= (TextNode) all.get(i).childNode(0);
            //textNode.text().replaceAll("\n", "");
            Element e = all.get(i);
            String work=e.text();
            e.text(work.replaceAll("&#92;n", ""));

        }
        html=doc.toString();
         return html;
    }
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

}
 class Sortbyroll implements Comparator<Pages>
{
    @Override
    public int compare(Pages o1, Pages o2) {
        int result;
        String title1 = o1.getPages_lang().get(0).getTitle();
        String title2 = o2.getPages_lang().get(0).getTitle();
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


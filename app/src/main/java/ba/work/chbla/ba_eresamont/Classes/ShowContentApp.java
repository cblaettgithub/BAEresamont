package ba.work.chbla.ba_eresamont.Classes;

import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import java.util.TreeMap;
import ba.work.chbla.ba_eresamont.Models.Pages;

/**
 * Created by chbla on 16.12.2017.
 */

public class ShowContentApp {
    CLanguageID cLanguageID = new CLanguageID();
    //for every method we set a constant
    // with the pages id we know which one we work for
    //exclude: we dont show this pagea
    //relaod: we reload the page of the webview for working
    //sizing: we resized the webview for zoom
    //nocomment: we remove comments
    //local: we set a local image at it
    private  final String Name_exclude="exlude";
    private  final String Name_reload="reload";
    private  final String Name_sizing="sizing";
    private  final String Name_nocomment="nocomment";
    private  final String Name_local="local";
    private long mlanguageID;
    private String[] mTitleArray=new String[4];
    OnHeadlineSelectedListener mCallback;
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(TreeMap hashMap, String choice, String[] mTitle);
    }
    public void onListItemClick(ListView l, View v, TreeMap hashMap, String choice) {
        mCallback.onArticleSelected(hashMap, choice, null);
    }
    public ShowContentApp() {
    }
    public void showContentApp(Pages pages, WebView webView, long mlanguageID, boolean tagModus) {

        String content="";
        this.mlanguageID=mlanguageID;
        Long parentid;
         if (pages!=null){
            content = pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTranslate().toString();
            if (tagModus)
                webView.setTag(pages.getId());
            if (pages.getParent_id()!=null ){//89 le project

                parentid=pages.getParent_id();// 100 = oxygen, 85=medical guide
                if (exclude_reload_sizing(parentid, Name_sizing)){
                    webView.setInitialScale(1);
                    webView.getSettings().setLoadWithOverviewMode(true);
                    webView.getSettings().setUseWideViewPort(true);
                    webView.getSettings().setSupportZoom(true);//for zooming
                    webView.getSettings().setBuiltInZoomControls(true);
                    webView.getSettings().setDisplayZoomControls(true);
                }
            }
            if (exclude_reload_sizing(pages.getId(), Name_nocomment))
                content=new ContentCorrecter(content).removeComments();
            if (exclude_reload_sizing(pages.getId(),Name_exclude)==false){
                content = new ContentCorrecter(content).contentEscapeProcessing();
                if (exclude_reload_sizing(pages.getId(), Name_local)){
                    // String data = "<body>" + content+"<img src=\"resources/img/scaladiborg-en.png\"/ height=\"300\" width=\"300\"></body>";
                    webView.loadDataWithBaseURL("file:///android_asset/",content , "text/html", "utf-8",null);
                }
                else {
                    webView.loadData(content, "text/html", "UTF-8");
                    if (exclude_reload_sizing(pages.getId(), Name_reload)) {
                        webView.loadData(content, "text/html", "UTF-8");
                        webView.reload();
                    }
                }
            }
        }
    }
    //methode for 3 settings
    public boolean exclude_reload_sizing(long id, String name){
        boolean check=false;
        long arrayprocess[]={};//exclude Otherwise i got a backgroumd text under questionaire
        long arrayexclud[]={86, 126};//evaluation de sante, questionare
        long arrayreload[]=  {95, 100, 113};//process consulation,oxygen checklist for medic
        long arraysizing[]={87};// boite a util, parentid
        long arrayremcomment[]={100};//algorithme oxigen
        long arrayimglocal[]={125};//questionarie, altitude

        switch (name) {
            case Name_exclude:
                arrayprocess = arrayexclud;
                break;
            case Name_reload:
                arrayprocess = arrayreload;
                break;
            case Name_sizing:
                arrayprocess = arraysizing;
                break;
            case Name_nocomment:
                arrayprocess = arrayremcomment;
                break;
            case Name_local:
                arrayprocess = arrayimglocal;
                break;

        }
        for(int i=0;i<arrayprocess.length;i++){
            if (id ==arrayprocess[i])
                check=true;
        }
        return check;
    }

}

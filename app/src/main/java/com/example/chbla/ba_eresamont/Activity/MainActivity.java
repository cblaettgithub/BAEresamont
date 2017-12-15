package com.example.chbla.ba_eresamont.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.chbla.ba_eresamont.Classes.*;

import com.example.chbla.ba_eresamont.Classes.ButtonManager;
import com.example.chbla.ba_eresamont.Classes.CLanguageID;
import com.example.chbla.ba_eresamont.Database.ConnectFirebase;
import com.example.chbla.ba_eresamont.Fragment.FirstFragment;

import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FirstFragment.OnHeadlineSelectedListener     {
    private DrawerLayout drawerLayout;
    private TreeMap hashMap;
    private String LOG_TAG=MainActivity.class.getSimpleName();
    private long mlanguageID;//1 French //default
    private Integer mMenuID;
    private ArrayList<Pages> pagesArrayList;
    private ConnectFirebase connectFirebase;
    private int parent_id=0;
    private WebView webView;
    private boolean started=false;
    private long parentidChangelanguage;
    public long getParentidChangelanguage() {
        return parentidChangelanguage;    }
    public void setParentidChangelanguage(long parentidChangelanguage) {
        this.parentidChangelanguage = parentidChangelanguage;    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pagesArrayList= new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu= navigationView.getMenu();
        menu.clear();

        Menu menushow= navigationView.getMenu();
        menushow.add(0, R.id.fragment_zero , 1,
                "Home").setIcon(R.drawable.ic_menu_gallery);

        navigationView.invalidate();
        setHomeAtfirst();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                navigationItem(item);
                return true;
            }
        });
      }

    public void creatingMenus(String choice) {
         switch (choice){
             case "MenuChange":
                 NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);;
                 Menu menushow= navigationView.getMenu();
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
            default://Menü bleibt gleich Aufruf mit mcallback..hashmap choice ""
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.d("backpressed","*******************backpressed");
        //Fragment_Man("1", mlanguageID, 0);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() >0){
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            setHomeAtfirst();
        }
        if (getSupportFragmentManager().getBackStackEntryCount() >1)        {
            Fragment fragment=getSupportFragmentManager().getFragments().get(0);
            Fragment_Man("89", 89, 89);
            getSupportFragmentManager().popBackStack();
        }
        //super.onBackPressed();
    }
    private void Fragment_Man(String value, long mlanguage, int menuID) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        Bundle args;
        args = new Bundle();
        fragment = new FirstFragment();
        args.putString("Fragmentname", value);
        args.putLong("Language", mlanguage);
        args.putInt("MenuID", menuID);
        fragment.setArguments(args);

        fragmentManager.beginTransaction().replace(R.id.container, fragment)
                .addToBackStack(null).commit();
    }

    public void setHomeAtfirst(){
        Fragment_Man("home", mlanguageID, 0);
    }
    private void navigationItem(MenuItem menuItem)
    {
        FragmentManager(menuItem);
    }

    private void FragmentManager(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.fragment_zero:
                Fragment_Man("home", mlanguageID, menuItem.getItemId());
                break;
            default:
                Fragment_Man(Integer.toString(menuItem.getItemId()), mlanguageID, menuItem.getItemId());
                break;
        }
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.French_settings:
                mlanguageID =1;
                fragmentGetter();
                break;
            case R.id.Italy_settings:
               mlanguageID=2;
               fragmentGetter();
                break;
            case R.id.English_settings:
                mlanguageID =3;
                fragmentGetter();
                break;
                default:
            break;
        }
        return true;
        //noinspection SimplifiableIfStatement
    }
    private void fragmentGetter() {
        List<Fragment> fragmentList;
        fragmentList=getSupportFragmentManager().getFragments();

        if (fragmentList.get(0)!=null){
           FirstFragment fragment=(FirstFragment)fragmentList.get(0);
           fragment.setMlanguageId(mlanguageID);//change language for buttonmangar
           fragment.setHashMap(hashMap);
            ReplaceFragmentContent(fragment);
        }
    }
    public void ReplaceFragmentContent(Fragment fragment)//either we change the webview or else the buttons
    {//buttons start changelanguage button

        final View view =fragment.getView();
        LinearLayout buttons = view.findViewById(R.id.outputlabel);
        LinearLayout line1 = view.findViewById(R.id.line1);
        WebView contentView = (WebView)line1.getChildAt(0);
        Bundle bundle;
        final ChangeLanuage changeLanuage;
        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);;
        changeLanuage= new ChangeLanuage(hashMap, mlanguageID, getApplication(),navigationView);

        if (buttons.getChildAt(2)!=null){
            Log.d(LOG_TAG, "Replace1 parentid:"+buttons.getChildAt(2).getTag().toString());
            this.setParentidChangelanguage(Long.parseLong(buttons.getChildAt(2).getTag().toString()));
            Log.d(LOG_TAG, "Method1 parentid:"+this.getParentidChangelanguage());
        }
        //parentid weiss ich über buttontag

        if (buttons.getChildCount()==0) { //  if (contentview.getChildCount()==0){
            connectFirebase= new ConnectFirebase();//nur ein content wechseln
            final DatabaseReference myRef = connectFirebase.getDatabaseReference();
            bundle = fragment.getArguments();
            //int id=Integer.parseInt(bundle.getString(""));
            Query query;
            if (contentView.getTag()==null)//Aufruf vom linken Menü
                 query=myRef.orderByChild(("id")).equalTo(Integer.parseInt(bundle.getString("Fragmentname")));
            else
                 query=myRef.orderByChild(("id")).equalTo(Integer.parseInt(contentView.getTag().toString()));


            query.addChildEventListener(new ChildEventListener() {
                LinearLayout line1 = view.findViewById(R.id.line1);
                WebView contentView = (WebView)line1.getChildAt(0);
                CLanguageID cLanguageID= new CLanguageID();


                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    String content="";
                    String neu="";
                    Pages pages = dataSnapshot.getValue(Pages.class);
                    content=pages.getPages_lang().get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTranslate().toString();
                    if (pages.getParent_id()==87){//only boite a outil
                        contentView.setTag(pages.getId());
                        contentView.setInitialScale(1);
                        contentView.getSettings().setLoadWithOverviewMode(true);
                        contentView.getSettings().setUseWideViewPort(true);
                        contentView.getSettings().setJavaScriptEnabled(true);
                    }
                    //if (content=="")
                    //    content=pages.getPages_lang().get(Integer.parseInt(cLanguageID.getArrayIndex(pages,mlanguageID))).getPlaintext().toString();
                    //neu = new ContentCorrecter(content).setCorrectContent();
                    contentView.loadData(content, "text/html", "UTF-8");
                    if (pages.getId()==95 || pages.getId()==100 || pages.getId()==113)//lake louis quiz, algoatirh, checklist telemedicine
                        contentView.reload();
                    if (pages.getParent_id()!=null)
                           changeLanuage.refrehMenuLanuager(pages.getParent_id());//um die Sprachen im Menü zu wechseln
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {                    }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {                    }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {                    }
                @Override
                public void onCancelled(DatabaseError databaseError) {                    }
            });
            Log.d(LOG_TAG, "Method parentid2:"+getParentidChangelanguage());

            //changeLanuage.refrehMenuLanuager(getParentidChangelanguage());//um die Sprachen im Menü zu wechseln
            hashMap=changeLanuage.getHashMap();
            //creatingMenus("MenuChange");//Menü updaten
           }
        else{
            changeLanuage.changeLanguage(buttons);
            //creatingMenus("MenuChange");//Menü updaten
            //changeLanguage(buttons);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onArticleSelected(TreeMap ohashMap, String choice) {
        hashMap=ohashMap;
        creatingMenus(choice);
    }

}


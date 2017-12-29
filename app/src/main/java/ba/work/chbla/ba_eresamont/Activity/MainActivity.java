package ba.work.chbla.ba_eresamont.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ba.work.chbla.ba_eresamont.Classes.CLanguageID;
import ba.work.chbla.ba_eresamont.Classes.ChangeLanuage;
import ba.work.chbla.ba_eresamont.Classes.ShowContentApp;
import ba.work.chbla.ba_eresamont.Classes.ValueComparator;
import ba.work.chbla.ba_eresamont.Database.ConnectFirebase;
import ba.work.chbla.ba_eresamont.Fragment.ChatFragment;
import ba.work.chbla.ba_eresamont.Fragment.FirstFragment;
import ba.work.chbla.ba_eresamont.Models.Pages;
import ba.work.chbla.ba_eresamont.R;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FirstFragment.OnHeadlineSelectedListener, ChatFragment.OnHeadlineSelectedListener {
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
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    private String username;
    public long getParentidChangelanguage() {
        return parentidChangelanguage;    }
    public void setParentidChangelanguage(long parentidChangelanguage) {
        this.parentidChangelanguage = parentidChangelanguage;    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pagesArrayList= new ArrayList<>();
        Bundle extras = getIntent().getExtras();//setting name for chatting
        this.setUsername(extras.getString("User"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
                 if (hashMap !=null){
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
        if (mlanguageID==0)
            mlanguageID=1;
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

    //switch betweend the different Language Settings
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
            case R.id.Chat:
                fragmentGetterChat();
                /*Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("User", this.getUsername());
                startActivity(intent);*/
                break;
            default:
        }
        return true;
        //noinspection SimplifiableIfStatement
    }
    //Replace the fragment, add Username
    private void fragmentGetterChat() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args;
        Fragment fragment = null;
        fragment = new ChatFragment();
        args = new Bundle();
        args.putString("username", this.getUsername());
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
     }
     //Get the fragment, if last fragment is chat then start sethomefirst,
     // else set the language and the hashmap
    private void fragmentGetter() {
        //old hier sortiert
        Fragment fragment;
        List<Fragment> fragmentList;//damit ist es sortiert
        fragmentList=getSupportFragmentManager().getFragments();
        fragment=fragmentList.get(0);
        if (fragment instanceof ChatFragment)
           //getSupportFragmentManager().popBackStack();
           setHomeAtfirst();
        else {
            //getSupportFragmentManager().popBackStack();
            FirstFragment fragment1 = (FirstFragment) fragmentList.get(0);
            fragment1.setMlanguageId(mlanguageID);//change language for buttonmangar
            fragment1.setHashMap(hashMap);
            ReplaceFragmentContent(fragment1);
        }
    }
    //Replace the fragmentconent and also refresh the language title and menues
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

        //parentid know we about the buttontag
        if (buttons.getChildCount()==0) { //  if (contentview.getChildCount()==0){
            connectFirebase= new ConnectFirebase();//only change onecontent
            final DatabaseReference myRef = connectFirebase.getDatabaseReference();
            bundle = fragment.getArguments();
            Query query;
            String parentchoice;

            if(contentView.getTag()!=null)//we know now that we callled from left menue
                parentchoice=contentView.getTag().toString();
            else
                parentchoice=bundle.getString("Fragmentname");
            query= myRef.orderByChild(("id")).equalTo(Integer.parseInt(parentchoice));

            query.addChildEventListener(new ChildEventListener() {
                LinearLayout line1 = view.findViewById(R.id.line1);
                WebView contentView = (WebView)line1.getChildAt(0);
                CLanguageID cLanguageID= new CLanguageID();

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    Pages pages = dataSnapshot.getValue(Pages.class);
                    new ShowContentApp().showContentApp(pages, contentView, mlanguageID, false); ;
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
            hashMap=changeLanuage.getHashMap();
            //creatingMenus("MenuChange");//Menü updaten
           }
        else{
            changeLanuage.changeLanguage(buttons);
       }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // we set the new hasmap(the names of the menuitesm, and also set new the title
    //at the top, we have called this methode from fragment with an interface
    @Override
    public void onArticleSelected(TreeMap ohashMap, String choice, String title) {
       if (title!=""){
               setTitle(title);
       }
        hashMap=ohashMap;
        creatingMenus(choice);
    }
}


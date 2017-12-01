package com.example.chbla.ba_eresamont.Activity;

import android.content.Intent;
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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.chbla.ba_eresamont.Fragment.FirstFragment;

import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FirstFragment.OnHeadlineSelectedListener
        {
    private DrawerLayout drawerLayout;
    private TreeMap hashMap;
    private String LOG_TAG=MainActivity.class.getSimpleName();
    private String mlanguage="1";//1 French //default
    private Integer mMenuID;
    private ArrayList<Pages> pagesArrayList;
    private int mlevel;
    public int getMlevel() {return mlevel;}
    public void setMlevel(int mlevel) { this.mlevel = mlevel;}

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
                 Iterator iterator=hashMap.entrySet().iterator();
                     while(iterator.hasNext()){//neu
                         Map.Entry<String, String> entry =   (Map.Entry<String, String>) iterator.next();
                         if (!entry.getValue().equals("")){
                         menushow.add(0, Integer.parseInt(entry.getKey()) , 1,
                                 entry.getValue()).setIcon(R.drawable.ic_menu_gallery);}
                 }
                 break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.d("backpressed","*******************backpressed");
        Fragment_Man("1", mlanguage, 0);
        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }
    private void Fragment_Man(String value, String mlanguage, int menuID) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        Bundle args;
        args = new Bundle();
        fragment = new FirstFragment();
        args.putString(FirstFragment.FRAGMENTNAME, value);
        args.putString(FirstFragment.LANGUAGE, mlanguage);
        args.putInt(FirstFragment.MENUID, menuID);
        args.putInt(FirstFragment.LEVEL, mlevel);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void setHomeAtfirst(){
        Fragment_Man("home", mlanguage, 0);
    }
    private void navigationItem(MenuItem menuItem)
    {
        FragmentManager(menuItem);
    }

    private void FragmentManager(MenuItem menuItem) {
        Log.d(LOG_TAG+":navigationItem","*****"+menuItem.getItemId()+":"+menuItem.getTitle());
        switch (menuItem.getItemId()){
            case R.id.fragment_zero:
                Fragment_Man("home", mlanguage, menuItem.getItemId());
                break;
            default:
                Fragment_Man(Integer.toString(menuItem.getItemId()), mlanguage, menuItem.getItemId());
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.w(LOG_TAG+":onOptionsItemSed",Integer.toString(id));
        switch(id){
            case R.id.French_settings:
                mlanguage="1";
                setHomeAtfirst();;
                break;
            case R.id.English_settings:
                mlanguage="3";
                List<Fragment> fragmentList =getSupportFragmentManager().getFragments();
                ReplaceFragmentContent(fragmentList.get(0));
                break;
            case R.id.Italy_settings:
                mlanguage="2";
                setHomeAtfirst();;
                break;
                default:
                mlanguage="1";
                 setHomeAtfirst();;
            break;
        }
        Log.w(LOG_TAG+":onOptionsItemSed",mlanguage);
        return true;
        //noinspection SimplifiableIfStatement
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onArticleSelected(TreeMap ohashMap, int level) {
        hashMap=ohashMap;
        mlevel=level;
        creatingMenus("MenuChange");
    }
    public void ReplaceFragmentContent(Fragment fragment)
    {
        View view =fragment.getView();
        LinearLayout contentview = view.findViewById(R.id.outputlabel);
        WebView webView =view.findViewById(R.id.webView);
        webView.loadData("<p>Testdaten</p>", "text/html", "UTF-8");
        hashMap.clear();
        for(int i=0;i< contentview.getChildCount();i++)  {
            Button button= (Button)contentview.getChildAt(i);
            button.setText("test");
            hashMap.put(String.valueOf(i),"test");
        }
        creatingMenus("MenuChange");
    }
}

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

import com.example.chbla.ba_eresamont.Database.ConnectFirebase;
import com.example.chbla.ba_eresamont.Fragment.FirstFragment;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FirstFragment.OnHeadlineSelectedListener     {

    private DrawerLayout drawerLayout;
    private ConnectFirebase connectFirebase;
    private String select="/supp_B/pages/5/pages_lang/";
    private TreeMap hashMap;
    private TreeMap hashMapold;
    private String LOG_TAG=MainActivity.class.getSimpleName();
    private final String PAGEROOT="/Ba_2020/pages/";
    private String mlanguage="0";//0 French

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //creatingMenus("Menu");
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
        connectFirebase= new ConnectFirebase();
        String select=PAGEROOT;
        final DatabaseReference myRef =  connectFirebase.getDatabaseReference(select);
        myRef.keepSynced(true);
        Query query=null;//=myRef.orderByKey().equalTo("1");///pages mit id 1;

         switch (choice){
            case "Menu"://wird nicht mehr aufgerufen
                query=myRef.orderByKey().startAt("85").endAt("87");
                query.addChildEventListener(new ChildEventListener(){
                    String name, id, parent;
                    int i=0;

                    NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);;
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName)
                    {
                        name= (String) dataSnapshot.child("pages_lang").child("0").child("title").getValue();
                        id = dataSnapshot.child("pages_lang").getKey();
                        id = dataSnapshot.child("pages_lang").child("0").child("parent_id").toString();
                        Log.w(LOG_TAG+":creatingMenus ::",  name+": id:"+ id+" parent:"+parent);

                        Menu menushow= navigationView.getMenu();
                        menushow.add(0, R.id.fragment_first , 1,
                                name).setIcon(R.drawable.ic_menu_gallery);
                        i++;
                    }
                    public void onChildRemoved(DataSnapshot dataSnapshot){

                    }
                    public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName){}
                    public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName){}
                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w("TAG:", "Failed to read value.", error.toException()); }
                });
                break;
             case "MenuChange":
                 NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);;
                 Menu menushow= navigationView.getMenu();
                 menushow.clear();
                 menushow.add(0, R.id.fragment_zero , 1,
                         "Home").setIcon(R.drawable.ic_menu_gallery);
                 hashMap.descendingKeySet();
                 Iterator iterator=hashMap.entrySet().iterator();
                     while(iterator.hasNext()){//neu
                         Map.Entry<String, String> entry =   (Map.Entry<String, String>) iterator.next();
                         Log.w(LOG_TAG+":MenuChange:", entry.getValue()+" :key "+entry.getKey());
                         menushow.add(0, Integer.parseInt(entry.getKey()) , 1,
                                 entry.getValue()).setIcon(R.drawable.ic_menu_gallery);
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        Bundle args;
        args= new Bundle();
        fragment = new FirstFragment();
        args.putString(FirstFragment.FRAGMENTNAME, "1");
        args.putString(FirstFragment.LANGUAGE, mlanguage);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }
    public void setHomeAtfirst(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        Bundle args;
        args= new Bundle();
        fragment = new FirstFragment();
        args.putString(FirstFragment.FRAGMENTNAME, "home");
        args.putString(FirstFragment.LANGUAGE, mlanguage);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    private void navigationItem(MenuItem menuItem)
    {
        FragmentManager(menuItem);
    }

    private void FragmentManager(MenuItem menuItem) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        Log.d(LOG_TAG+":navigationItem","*****"+menuItem.getItemId()+":"+menuItem.getTitle());
        Bundle args;
        //kein switch mhr, key hier herauslesen
        switch (menuItem.getItemId()){
            case R.id.fragment_zero:
                args= new Bundle();
                fragment = new FirstFragment();
                args.putString(FirstFragment.FRAGMENTNAME, "home");
                args.putString(FirstFragment.LANGUAGE, mlanguage);
                fragment.setArguments(args);
                break;
            default:
                args= new Bundle();
                fragment = new FirstFragment();
                args.putString(FirstFragment.FRAGMENTNAME,Integer.toString(menuItem.getItemId()));
                args.putString(FirstFragment.LANGUAGE, mlanguage);
                fragment.setArguments(args);
                break;
        }
        //With this code it will replace the container with the selected fragment
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
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
                mlanguage="0";
                break;
            case R.id.English_settings:
                mlanguage="1";
                break;
            case R.id.Italy_settings:
                mlanguage="2";
                break;
                default:
                mlanguage="0";
            break;
        }
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
    public void onArticleSelected(TreeMap ohashMap) {
        hashMap=ohashMap;
        creatingMenus("MenuChange");
    }
}

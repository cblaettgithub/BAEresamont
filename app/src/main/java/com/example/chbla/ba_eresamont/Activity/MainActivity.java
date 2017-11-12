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
import android.widget.ArrayAdapter;

import com.example.chbla.ba_eresamont.Database.ConnectFirebase;
import com.example.chbla.ba_eresamont.Database.DBMenueName;
import com.example.chbla.ba_eresamont.Fragment.FirstFragment;
import com.example.chbla.ba_eresamont.Models.Page_lang;
import com.example.chbla.ba_eresamont.Models.Pages;
import com.example.chbla.ba_eresamont.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ConnectFirebase connectFirebase;
    private String select="/supp_B/pages/5/pages_lang/";

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
        GetDataFirebase("Menu");
        navigationView.invalidate();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            DBMenueName dbMenueName= new DBMenueName();
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                navigationItem(item);
                return true;
            }
        });
      }
    private void GetDataFirebase(String choice) {
        connectFirebase= new ConnectFirebase();
        String select="/Ba_2017/pages/";
        final DatabaseReference myRef =  connectFirebase.getDatabaseReference(select);
        myRef.keepSynced(true);
        Query query=null;//=myRef.orderByKey().equalTo("1");///pages mit id 1;

         switch (choice){
            case "Menu":
                query=myRef.orderByKey().startAt("85").endAt("87");
                break;
            default:
                break;
        }

        query.addChildEventListener(new ChildEventListener(){
            String temp;
            int i=0;
            NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);;
            int[] fragmentarray=new int[]{R.id.fragment_first, R.id.fragment_second,R.id.fragment_third};
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName)
            {
                temp= (String) dataSnapshot.child("pages_lang").child("0").child("title").getValue();
                Log.w("GetDataFirebase 1:hash:",  temp);
                Menu menushow= navigationView.getMenu();
                menushow.add(0, fragmentarray[i] , 1,
                       temp).setIcon(R.drawable.ic_menu_gallery);
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
    }

    private void CreateMenus(NavigationView navigationView, String[] menusnames,
                             int[] fragmentnames) {
        for(int i=0;i<menusnames.length;i++){
            Menu menushow= navigationView.getMenu();
            menushow.add(0, fragmentnames[i] , 1,
                    menusnames[i]).setIcon(R.drawable.ic_menu_gallery);        ;
        }
        navigationView.invalidate();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.d("backpressed","*******************backpressed");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onUserLeaveHint()
    {
        Log.d("onUserLeaveHint","**********************Home button pressed");
        super.onUserLeaveHint();
    }

    private void navigationItem(MenuItem menuItem)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        Log.d("navigationItem","**********************navigationItem");
        Bundle args;
        switch (menuItem.getItemId()){
            case R.id.fragment_first:
                args= new Bundle();
                fragment = new FirstFragment();
                args.putString(FirstFragment.FRAGMENTNAME, "first");
                fragment.setArguments(args);
                break;
            case R.id.fragment_second:
                args= new Bundle();
                fragment = new FirstFragment();
                args.putString(FirstFragment.FRAGMENTNAME, "second");
                fragment.setArguments(args);
                break;
            case R.id.fragment_third:
                args= new Bundle();
                fragment = new FirstFragment();
                args.putString(FirstFragment.FRAGMENTNAME, "second");
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
        switch(id){
            case R.id.homeAsUp:
                return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

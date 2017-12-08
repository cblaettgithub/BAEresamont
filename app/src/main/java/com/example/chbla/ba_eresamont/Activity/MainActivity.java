package com.example.chbla.ba_eresamont.Activity;

import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
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
    private long mlanguage=1;//1 French //default
    private Integer mMenuID;
    private ArrayList<Pages> pagesArrayList;
    private int mlevel;
    private ConnectFirebase connectFirebase;
    private int parent_id=0;
    private WebView webView;
    private boolean started=false;


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
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.d("backpressed","*******************backpressed");
        //Fragment_Man("1", mlanguage, 0);
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
        Fragment_Man("home", mlanguage, 0);
    }
    private void navigationItem(MenuItem menuItem)
    {
        FragmentManager(menuItem);
    }

    private void FragmentManager(MenuItem menuItem) {
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
        List<Fragment> fragmentList;
        switch(id){
            case R.id.French_settings:
                mlanguage=1;
                fragmentGetter();
                break;
            case R.id.Italy_settings:
                mlanguage=2;
                fragmentGetter();
                break;
            case R.id.English_settings:
                mlanguage=3;
                fragmentGetter();
                break;
                default:
                mlanguage=1;
            break;
        }
        return true;
        //noinspection SimplifiableIfStatement
    }

    private void fragmentGetter() {
        List<Fragment> fragmentList;
        fragmentList=getSupportFragmentManager().getFragments();
        if (fragmentList.get(0)!=null){
            ReplaceFragmentContent(fragmentList.get(0));
        }
    }


    public void ReplaceFragmentContent(Fragment fragment)//either we change the webview or else the buttons
    {//buttons start changelanguage button
     //webview start this
        final View view =fragment.getView();
        LinearLayout buttons = view.findViewById(R.id.outputlabel);
        LinearLayout line1 = view.findViewById(R.id.line1);
        WebView contentView = (WebView)line1.getChildAt(0);
        Bundle bundle;
        if (buttons.getChildCount()==0) { //  if (contentview.getChildCount()==0){
            connectFirebase= new ConnectFirebase();
            final DatabaseReference myRef = connectFirebase.getDatabaseReference();
            bundle = fragment.getArguments();
            //int id=Integer.parseInt(bundle.getString(""));
            Query query;
            if (contentView.getTag()==null)//Aufruf vom linken Menü
                 query=myRef.orderByChild(("id")).equalTo(Integer.parseInt(bundle.getString("")));
            else
                 query=myRef.orderByChild(("id")).equalTo(Integer.parseInt(contentView.getTag().toString()));

            query.addChildEventListener(new ChildEventListener() {
                LinearLayout line1 = view.findViewById(R.id.line1);
                WebView contentView = (WebView)line1.getChildAt(0);
                CLanguageID cLanguageID= new CLanguageID();

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    String content="";
                    String neu;
                    Pages pages = dataSnapshot.getValue(Pages.class);
                    content=pages.getPages_lang().get(cLanguageID.GetLanguageID(pages,(int)mlanguage)).getTranslate().toString();
                    //if (content=="")
                    //    content=pages.getPages_lang().get(Integer.parseInt(cLanguageID.GetLanguageID(pages,mlanguage))).getPlaintext().toString();
                    neu = setCorrectContent(content);
                    contentView.loadData(neu, "text/html", "UTF-8");
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

            //contentView.loadData("<p>Testdaten</p><br><br>" +
             //       "<br><br><br><br><p>Testdaten</p>", "text/html", "UTF-8");
          }
        else{
            changeLanguage(buttons);
        }
    }

    public void changeLanguage(LinearLayout linearLayout){
        final CLanguageID cLanguageID = new CLanguageID();
        connectFirebase= new ConnectFirebase();
        final DatabaseReference myRef = connectFirebase.getDatabaseReference();
        Query query=myRef.orderByChild("pages_lang/0/title");
        String parent_id="0";
        final LinearLayout contentview=linearLayout;
        hashMap.clear();
        Button button= (Button)contentview.getChildAt(2);
        if (button.getTag().toString()!="0")//wenn parent_id = 0, dann ist parent_id = null-> oberste stfue
            parent_id=button.getTag().toString();        //sonst button in button
        final ArrayList<Button> buttonArrayList=new ArrayList<>();

        switch (parent_id){
            //we start again the frameset with new language.
            case "0":
                setHomeAtfirst();
               /* query.addChildEventListener(new ChildEventListener() {
                    Pages pages;
                    int i=0;

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                        if (dataSnapshot.child("parent_id").exists() == false) {
                            if (dataSnapshot.child("pages_lang").child("0").child("title").
                                    getValue() != null) {
                                pages = dataSnapshot.getValue(Pages.class);
                                if (pages!=null){
                                    Button button= (Button)contentview.getChildAt(i);//error null
                                    button.setText(pages.getPages_lang().get(Integer.parseInt(cLanguageID.GetLanguageID(pages,mlanguage))).getTitle());
                                    hashMap.put(String.valueOf(i),button.getText());
                                    buttonArrayList.add(button);
                                    i++;
                                }
                            }
                        }
                        SortButtons(buttonArrayList);
                        creatingMenus("MenuChange");//Menü updaten
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {                    }
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {                    }
                });*/
                break;
            default:
                DatabaseReference ref=myRef.getParent();
                query=ref.orderByChild("/pages/");
                query.addChildEventListener(new ChildEventListener() {
                    int i=0;
                    ArrayList<Button> buttonArrayList;

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                        Button button2= (Button)contentview.getChildAt(2);
                        long parent_id;
                        parent_id=Long.valueOf(button2.getTag().toString());
                        buttonArrayList=new ArrayList<>();
                        for (DataSnapshot data :dataSnapshot.getChildren()) {
                            Pages pages1=data.getValue(Pages.class);
                            if (pages1.getParent_id()!=null ) {
                                if (pages1.getParent_id()== parent_id){
                                   Button button= (Button)contentview.getChildAt(i);
                                   if (pages1.getPages_lang()!=null)
                                        button.setText(pages1.getPages_lang().get(cLanguageID.GetLanguageID(pages1,(int)mlanguage)).getTitle());
                                    hashMap.put(String.valueOf(i),button.getText());
                                    i++;
                                   buttonArrayList.add(button);
                                }
                            }
                        }
                        SortButtons(buttonArrayList);
                        creatingMenus("MenuChange");
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
                break;
        }
        //connectFirebase.close();
    }
    private void SortButtons(ArrayList<Button> buttonArrayList) {
        Map sortedMap = new TreeMap(new ValueComparator(hashMap));
        sortedMap.putAll(hashMap);
        int i=0;
        Iterator iterator=sortedMap.entrySet().iterator();
        while(iterator.hasNext()){//ne
            Map.Entry<String, String> entry =   (Map.Entry<String, String>) iterator.next();
            buttonArrayList.get(i).setText(entry.getValue());
            i++;
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
        @NonNull
        private String setCorrectContent( String content) {
            String neu;
            neu=content.replaceAll("style=", "style=\"").toString();
            neu=neu.replaceAll(";>", ";\">").toString();
            neu=neu.replaceAll("src=", "src=\"").toString();
            neu=neu.replaceAll("alt", "\" alt").toString();
            return neu;
        }
}
class ValueComparator implements Comparator<String> {
    Map map;
    public ValueComparator(Map map) {
        this.map = map;
    }
    public int compare(String keyA, String keyB) {
        int result;
        Comparable valueA = (Comparable) map.get(keyA);
        Comparable valueB = (Comparable) map.get(keyB);
        if (map.get(keyA).toString().contains(".")&&
                map.get(keyB).toString().contains(".")){
            int indexA =  map.get(keyA).toString().indexOf('.');
            int indexB =  map.get(keyB).toString().indexOf('.');
            int a=Integer.parseInt(map.get(keyA).toString().substring(0, indexA));
            int b=Integer.parseInt(map.get(keyB).toString().substring(0, indexB));
            result =a-b;
        }
        else{
            result =valueA.compareTo(valueB);
        }
        return result;
    }
}

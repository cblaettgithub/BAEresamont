package ba.work.chbla.ba_eresamont.Classes;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import ba.work.chbla.ba_eresamont.Activity.MainActivity;
import ba.work.chbla.ba_eresamont.Database.ConnectFirebase;
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
 * Created by chbla on 10.12.2017.
  * changinge the language for the app, all content
 */

public class ChangeLanuage {
    private String LOG_TAG=MainActivity.class.getSimpleName();
    private ConnectFirebase connectFirebase;
    private TreeMap hashMap;
    private long mlanguageID;
    private Context context;
    private long mparentid;
    NavigationView navigationView;
    final CLanguageID cLanguageID = new CLanguageID();
    public TreeMap getHashMap() {
        return hashMap;    }
    public Context getContext() {
        return context;    }
    CreatingMenuLeft creatingMenuLeft;
    private String title;
    public String getTitle() {      return title;    }
    public void setTitle(String title) {       this.title = title;    }

    public ChangeLanuage(TreeMap hashMap, long mlanguageID, Context context, NavigationView navigationView) {
        this.hashMap = hashMap;
        this.mlanguageID = mlanguageID;
        this.context = context;
        this.connectFirebase = new ConnectFirebase();
        this.navigationView=navigationView;
        creatingMenuLeft= new CreatingMenuLeft(navigationView);
    }

    public void changeLanguage(LinearLayout linearLayout) {
        final DatabaseReference myRef = connectFirebase.getDatabaseReference();
        Query query = myRef.orderByChild("pages_lang/0/title");
        String parent_id = "0";
        final LinearLayout contentview = linearLayout;
        if (hashMap!=null)
            hashMap.clear();
        else
            hashMap= new TreeMap();
        final Button button = (Button) contentview.getChildAt(2);
        if (button.getTag().toString() != "0")//wenn parent_id = 0, dann ist parent_id = null-> oberste stfue
            parent_id = button.getTag().toString();        //sonst button in button
        final ArrayList<Button> buttonArrayList = new ArrayList<>();

        switch (parent_id) {//show list
            //we start again the frameset with new language.//create new button, add buttons to arraylist
            //sort buttons, replace the content with the content of buttons
            case "0":
                query.addChildEventListener(new ChildEventListener() {
                    Pages pages;
                    int i = 0;
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                        if (dataSnapshot.child("parent_id").exists() == false) {
                            if (dataSnapshot.child("pages_lang").child("0").child("title").
                                    getValue() != null) {
                                pages = dataSnapshot.getValue(Pages.class);
                                Button button=new Button(getContext());;
                                if (pages != null) {
                                    for(int i=0;i<contentview.getChildCount();i++)
                                    {   //we get a button from the linearview(gui)
                                        Button button1 =(Button)contentview.getChildAt(i);
                                        if (button1.getId()==pages.getId()) {//check if same id
                                            ((Button) contentview.getChildAt(i)).setText(pages.getPages_lang()
                                                    .get(cLanguageID.getArrayIndex(pages, mlanguageID)).getTitle());//set new title in the other language
                                            buttonArrayList.add((Button) contentview.getChildAt(i));//add to the list for later sorting
                                            hashMap.put(String.valueOf(pages.getId().toString()), button1.getText());//for the left menue
                                        }
                                    }
                                    i++;
                                }
                            }
                        }
                        if (buttonArrayList.size()==7) {//only when i have seven element of the topmenu
                            SortButtons(buttonArrayList);
                            for(int i=0;i<contentview.getChildCount()+1;i++)//we remove all items
                                contentview.removeViewAt(i);
                            contentview.removeViewAt(0);
                            if(contentview.getChildCount()>1){
                                contentview.removeViewAt(1);
                                contentview.removeViewAt(0);
                            }
                            for (int i = 0; i < buttonArrayList.size(); i++) {//we add the new button to the gui
                                contentview.addView(buttonArrayList.get(i));
                                hashMap.put(String.valueOf(buttonArrayList.get(i).getId()), buttonArrayList.get(i).getText());
                            }
                        }
                        creatingMenuLeft.creatingMenus("MenuChange", hashMap);
                        //SortButtons(buttonArrayList);
                        //creatingMenus("MenuChange");//Menü updaten
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                break;
            default://show one element, create a button, set to the arraylist, sort and replace contetn
                DatabaseReference ref = myRef.getParent();
                query = ref.orderByChild("/pages/");
                query.addChildEventListener(new ChildEventListener() {
                    int i = 0;
                    ArrayList<Button> buttonArrayList;

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                        Button button2 = (Button) contentview.getChildAt(2);
                        long parent_id = Long.valueOf(button2.getTag().toString());
                        buttonArrayList = new ArrayList<>();
                        Button button;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Pages pages1 = data.getValue(Pages.class);
                            if (pages1.getParent_id() != null) {
                                if (pages1.getParent_id() == parent_id) {
                                    button = new Button(getContext());//we create a button because we need that kind of type for copying
                                    button.setText(pages1.getPages_lang().get(cLanguageID.getArrayIndex(pages1, mlanguageID)).getTitle());
                                    button.setId(Integer.parseInt(pages1.getId().toString()));
                                    buttonArrayList.add(button);
                                    i++;
                                    //((Button)contentview.getChildAt(i)).setText(pages1.getPages_lang().get(cLanguageID.getArrayIndex(pages1, mlanguageID)).getTitle());
                                   /*if (pages1.getPages_lang()!=null){
                                       button.setText(pages1.getPages_lang().get(cLanguageID.getArrayIndex(pages1, mlanguageID)).getTitle());
                                   }*/
                                    //hashMap.put(String.valueOf(i),button.getText());old 10.12.2017

                                }
                            }
                        }
                        SortButtons(buttonArrayList);
                        for (int i = 0; i < buttonArrayList.size(); i++) {
                            hashMap.put(String.valueOf(buttonArrayList.get(i).getId()), buttonArrayList.get(i).getText());//neu 12.12.2017
                            ((Button) contentview.getChildAt(i)).setText(buttonArrayList.get(i).getText());
                        }
                        creatingMenuLeft.creatingMenus("MenuChange", hashMap);  //creatingMenus("MenuChange");
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                break;
        }
    }
    // refresh the menuelanugage, put to the hashmap the menuelangueitiems
    public void refrehMenuLanuager(long parentid){
        final DatabaseReference myRef = connectFirebase.getDatabaseReference();
        Query query=myRef.orderByChild("parent_id").equalTo(parentid);
        //Query query = myRef.orderByChild("pages_lang/0/title");
        if (hashMap!=null)
         hashMap.clear();

        query.addChildEventListener(new ChildEventListener() {
            Pages pages;
            int i = 0;
            boolean temp=true;
            ArrayList<String> StringArrayList = new ArrayList<>();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    Pages pages1 = dataSnapshot.getValue(Pages.class);
                    if (pages1!=null) {
                        hashMap.put(pages1.getId().toString(), pages1.getPages_lang().get(cLanguageID.getArrayIndex(pages1, mlanguageID)).getTitle());
                        Log.d("ChangelangMenue", pages1.getPages_lang().get(cLanguageID.getArrayIndex(pages1, mlanguageID)).getTitle());
                        i++;
                        StringArrayList.add(pages1.getPages_lang().get(cLanguageID.getArrayIndex(pages1, mlanguageID)).getTitle());
                    }
                //for(int i=0;i<StringArrayList.size();i++)
                //    hashMap.put(String.valueOf(i), StringArrayList.get(i));
                creatingMenuLeft.creatingMenus("MenuChange", hashMap);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    private void SortButtons (ArrayList < Button > buttonArrayList) {
        Collections collections = null;
        Collections.sort(buttonArrayList, new ButtonsComparator());
    }        //connectFirebase.close();
}


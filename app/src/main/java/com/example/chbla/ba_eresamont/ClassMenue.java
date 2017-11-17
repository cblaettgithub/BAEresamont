package com.example.chbla.ba_eresamont;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chbla.ba_eresamont.Activity.MainActivity;
import com.example.chbla.ba_eresamont.Database.ConnectFirebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by chbla on 15.11.2017.
 */

public class ClassMenue extends AppCompatActivity {
    public ClassMenue(){};

    public void CreatingMenus(String choice, View view) {
        ConnectFirebase connectFirebase;
        connectFirebase= new ConnectFirebase();
        String select="/Ba_2019/pages/";
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
        final NavigationView navigationView= (NavigationView) view.findViewById(R.id.nav_view);;

        query.addChildEventListener(new ChildEventListener(){
            String temp;
            int i=0;

            int[] fragmentarray=new int[]{R.id.fragment_first, R.id.fragment_second,R.id.fragment_third};
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName)
            {
                temp= (String) dataSnapshot.child("pages_lang").child("0").child("title").getValue();
                Log.w("creatingMenus 10:hash:",  temp);
                Menu menushow= navigationView.getMenu();
                menushow.add(0, fragmentarray[i] , 1,
                        "test").setIcon(R.drawable.ic_menu_gallery);
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

}

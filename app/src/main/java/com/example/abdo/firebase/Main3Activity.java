package com.example.abdo.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {
     DatabaseReference root ;
     String temp_key , name ;
     EditText editText ;
     ListView listView ;
     ArrayList<String> arrayList = new ArrayList() ;
     ArrayAdapter<String> arrayAdapter ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        root = FirebaseDatabase.getInstance().getReference().child("MainChatRoom") ;
        listView=(ListView)findViewById(R.id.list) ;
        editText=(EditText)findViewById(R.id.edit) ;
        name = getIntent().getExtras().getString("Name") ;
        arrayAdapter =  new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 ,arrayList) ;
        listView.setAdapter(arrayAdapter);
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Add_chat(dataSnapshot) ;

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Add_chat(dataSnapshot) ;


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

    public void send(View view) {
        Map <String ,Object> map = new HashMap<>() ;
        temp_key=root.push().getKey() ;
        root.updateChildren(map) ;
        DatabaseReference massage_root =  root.child(temp_key) ;
        Map <String ,Object> map2 = new HashMap<>() ;
        map2.put("name" ,name) ;
        map2.put("msg" ,editText.getText().toString()) ;
        massage_root.updateChildren(map2) ;


    }
    private String chat_msg ,chat_user_name ;
    private void Add_chat(DataSnapshot dataSnapshot){
        Iterator i = dataSnapshot.getChildren().iterator() ;
        editText.setText("");
        while (i.hasNext())
        {
         chat_msg = (String)((DataSnapshot)i.next()).getValue() ;
            chat_user_name = (String)((DataSnapshot)i.next()).getValue() ;
            arrayList.add(chat_user_name + ":" + chat_msg);
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(arrayList.size());

        }

    }

}

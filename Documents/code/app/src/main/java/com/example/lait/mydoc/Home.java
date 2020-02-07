package com.example.lait.mydoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseUser user;

    RecyclerView mInfoRecycler;

    ArrayList<String> myListInfo = new ArrayList<>();

    Button listPerson;
    Button inputInfo;

    /*TextView firsName;
    TextView name;
    TextView lastName;
    TextView age;
    TextView job;
    TextView post;
    TextView myInfo;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String way = "/doctor/"+ user.getUid() +"/info";
        myRef = database.getReference(way);



        mInfoRecycler = findViewById(R.id.recyclerhome);

        mInfoRecycler.setLayoutManager(new LinearLayoutManager(this));

        final DataAdapterHome dataAdapterHome = new DataAdapterHome(this, myListInfo);

        mInfoRecycler.setAdapter(dataAdapterHome);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String str = dataSnapshot.getValue(String.class);
                myListInfo.add(str);
                dataAdapterHome.notifyDataSetChanged();
                mInfoRecycler.smoothScrollToPosition(myListInfo.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        listPerson = findViewById(R.id.btnListPersons);
        inputInfo = findViewById(R.id.btnInputInfo);

        listPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, ListPerson.class);
                startActivity(intent);
            }
        });

        inputInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, inputMyInfo.class);
                startActivity(intent);
            }
        });

    }
}

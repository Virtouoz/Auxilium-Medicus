package com.example.lait.mydoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SearchPerson extends AppCompatActivity {

    private DatabaseReference myRef;

    RecyclerView mInfoRecycler;

    ArrayList<String> myListInfo = new ArrayList<>();

    Button btn_backspace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_person);

        Bundle arguments = getIntent().getExtras();
        String name = arguments.get("name").toString();

        btn_backspace = findViewById(R.id.btn_backspace);

        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String way = "/pasients/" + name;
        myRef = database.getReference(way);
        Toast.makeText(getApplicationContext(), "Пациент не найден", Toast.LENGTH_SHORT).show();

        if (myRef == null) {
            Intent intent = new Intent(SearchPerson.this, ListPerson.class);
            startActivity(intent);
            finish();
        } else {

            mInfoRecycler = findViewById(R.id.recyclerInfoPerson);

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


        }

        btn_backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPerson.this, ListPerson.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

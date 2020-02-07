package com.example.lait.mydoc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class ListPerson extends AppCompatActivity {

    private static final int MAX_PERSON = 50;
    private DatabaseReference myRef;
    private FirebaseUser user;

    EditText mEditText;
    Button mButton;
    RecyclerView  mPersonRecycler;

    ArrayList<String> personlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_person);

        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String way = "/doctor/"+ user.getUid() +"/persons";
        myRef = database.getReference(way);

        mButton = findViewById(R.id.button_input);
        mEditText = findViewById(R.id.str_input);
        mPersonRecycler = findViewById(R.id.recycler);

        mPersonRecycler.setLayoutManager(new LinearLayoutManager(this));

        final DataAdapter dataAdapter = new DataAdapter(this, personlist);

        mPersonRecycler.setAdapter(dataAdapter);

        mPersonRecycler.callOnClick();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mEditText.getText().toString();
                if (str.equals("")) {
                    Toast.makeText(getApplicationContext(), "ЧЕ ЗА ФИГНЯ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (str.length() > MAX_PERSON) {
                    Toast.makeText(getApplicationContext(), "А НЕ ТРЕСНЕТ ЛИ?!", Toast.LENGTH_SHORT).show();
                    return;
                }

                myRef.push().setValue(str);//myRef.child(user.getUid()).child("ListPerson").push().setValue(str);
                mEditText.setText("");
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String str = (String) dataSnapshot.getValue(String.class);
                personlist.add(str);
                dataAdapter.notifyDataSetChanged();
                mPersonRecycler.smoothScrollToPosition(personlist.size());
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
}

package com.example.lait.mydoc;

import android.content.Intent;
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


    EditText mEditTextInfoPersonFirst;
    EditText mEditTextInfoPersonName;
    EditText mEditTextInfoPersonLast;
    Button mButtonInfoPerson;
    Button mButtonSearch;
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

        mButtonInfoPerson = findViewById(R.id.btn_person_info);
        mButtonSearch = findViewById(R.id.btn_search);
        mEditTextInfoPersonFirst = findViewById(R.id.str_seach_person_firstname);
        mEditTextInfoPersonName = findViewById(R.id.str_seach_person_name);
        mEditTextInfoPersonLast = findViewById(R.id.str_seach_person_lastname);
        mPersonRecycler = findViewById(R.id.recycler);

        mPersonRecycler.setLayoutManager(new LinearLayoutManager(this));

        final DataAdapter dataAdapter = new DataAdapter(this, personlist);

        mPersonRecycler.setAdapter(dataAdapter);

        mPersonRecycler.callOnClick();

        mButtonInfoPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str1 = mEditTextInfoPersonFirst.getText().toString();
                String str2 = mEditTextInfoPersonName.getText().toString();
                String str3 = mEditTextInfoPersonLast.getText().toString();

                if(str1.equals("") || str2.equals("") || str3.equals("")){
                    Toast.makeText(ListPerson.this, "Введите ФИО", Toast.LENGTH_SHORT).show();
                    return;
                }

                String str0 = str1 + "-" + str2 + "-" + str3;
                myRef.push().setValue(str0);
                mEditTextInfoPersonLast.setText("");
                mEditTextInfoPersonName.setText("");
                mEditTextInfoPersonFirst.setText("");

                Intent intent = new Intent(ListPerson.this, InfoPerson.class);
                intent.putExtra("name", str0);
                startActivity(intent);
            }
        });
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1 = mEditTextInfoPersonFirst.getText().toString();
                String str2 = mEditTextInfoPersonName.getText().toString();
                String str3 = mEditTextInfoPersonLast.getText().toString();

                if(str1.equals("") || str2.equals("") || str3.equals("")){
                    Toast.makeText(ListPerson.this, "Введите ФИО", Toast.LENGTH_SHORT).show();
                    return;
                }

                String str0 = str1 + "-" + str2 + "-" + str3;
                //myRef.push().setValue(str0);
                mEditTextInfoPersonLast.setText("");
                mEditTextInfoPersonName.setText("");
                mEditTextInfoPersonFirst.setText("");

                Intent intent = new Intent(ListPerson.this, SearchPerson.class);
                intent.putExtra("name", str0);
                startActivity(intent);
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

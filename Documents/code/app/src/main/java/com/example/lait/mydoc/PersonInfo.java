package com.example.lait.mydoc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PersonInfo extends AppCompatActivity {

    TextView str1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        str1 = (TextView) findViewById(R.id.NamePerson);

        Intent intent = getIntent();

        String fName = intent.getStringExtra("fname");

        str1.setText("Your name is: " + fName);
    }
}

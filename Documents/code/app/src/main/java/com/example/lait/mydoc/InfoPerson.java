package com.example.lait.mydoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoPerson extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseUser user;

    EditText a1firsName;
    EditText a2name;
    EditText a3lastName;
    EditText a4ward;
    EditText a5age;
    EditText a6job;
    EditText a7post;
    EditText a8myPhone;
    EditText a9complaints;

    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_person);

        Bundle arguments = getIntent().getExtras();

        String name = arguments.get("name").toString();

        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();

        btn_save = findViewById(R.id.savePerson);
        a1firsName = findViewById(R.id.firsNamePerson);
        a2name = findViewById(R.id.namePerson);
        a3lastName = findViewById(R.id.lastNamePerson);
        a4ward = findViewById(R.id.wardPerson);
        a5age = findViewById(R.id.agePerson);
        a6job = findViewById(R.id.jobPerson);
        a7post = findViewById(R.id.postPerson);
        a8myPhone = findViewById(R.id.myPhonePerson);
        a9complaints = findViewById(R.id.myInfoPerson);

        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String way = "/pasients/"+ name;
        myRef = database.getReference(way);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str1 = a1firsName.getText().toString();
                String str2 = a2name.getText().toString();
                String str3 = a3lastName.getText().toString();
                String str4 = a4ward.getText().toString();
                String str5 = a5age.getText().toString();
                String str6 = a6job.getText().toString();
                String str7 = a7post.getText().toString();
                String str8 = a8myPhone.getText().toString();
                String str9 = a9complaints.getText().toString();

                if (str1.equals("") || str2.equals("") || str3.equals("") || str4.equals("") || str5.equals("") || str6.equals("") || str7.equals("") || str8.equals("") || str9.equals("")) {
                    Toast.makeText(getApplicationContext(), "Не все поля заполнены!", Toast.LENGTH_SHORT).show();
                    return;
                }

                PasientInfo pasientInfo = new PasientInfo(str1,str2,str3,str4,str5,str6,str7,str8,str9);

                myRef.setValue(pasientInfo);

                Intent intent = new Intent(InfoPerson.this, ListPerson.class);
                startActivity(intent);
                finish();
            }
        });



    }
}

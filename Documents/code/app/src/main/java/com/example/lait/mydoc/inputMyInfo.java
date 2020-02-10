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

public class inputMyInfo extends AppCompatActivity {

    private static final int MIN_SIZE = 20;
    private DatabaseReference myRef;
    private FirebaseUser user;

    EditText firsName;
    EditText name;
    EditText lastName;
    EditText age;
    EditText job;
    EditText post;
    EditText myInfo;
    EditText myPhone;

    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_my_info);

        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String way = "/doctor/"+ user.getUid() +"/info";
        myRef = database.getReference(way);

        save = findViewById(R.id.save);
        firsName = findViewById(R.id.firsName);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        age = findViewById(R.id.age);
        job = findViewById(R.id.job);
        post = findViewById(R.id.post);
        myInfo = findViewById(R.id.myInfo);
        myPhone = findViewById(R.id.myPhone);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str1 = firsName.getText().toString();
                String str2 = name.getText().toString();
                String str3 = lastName.getText().toString();
                String str4 = age.getText().toString();
                String str5 = job.getText().toString();
                String str6 = post.getText().toString();
                String str7 = myInfo.getText().toString();
                String str8 = myPhone.getText().toString();

                DoctorInfo doctorInfo = new DoctorInfo(str1,str2,str3,str4,str5,str6,str7,str8);

                if (str1.equals("") || str2.equals("")|| str3.equals("")|| str4.equals("")|| str5.equals("")|| str6.equals("")|| str7.equals("")) {
                    Toast.makeText(getApplicationContext(), "ЧЕ ЗА ФИГНЯ! ЗАПОЛНИ ВСЕ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (str7.length() < MIN_SIZE) {
                    Toast.makeText(getApplicationContext(), "А НЕ МАЛО ЛИ ПРО СЕБЯ?!", Toast.LENGTH_SHORT).show();
                    return;
                }

                myRef.setValue(doctorInfo);

                firsName.setText("");
                name.setText("");
                lastName.setText("");
                age.setText("");
                job.setText("");
                post.setText("");
                myInfo.setText("");
                myPhone.setText("");

                Intent intent = new Intent(inputMyInfo.this, Home.class);
                startActivity(intent);
                finish();

            }
        });
    }
}

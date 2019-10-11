package com.example.exampractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import database.DBHelper;

public class TeacherActivity extends AppCompatActivity {

    TextView textViewWelcome;

    EditText editTextSubject , editTextMessage;
    Button buttonSend;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        Intent i = getIntent();
        final String userName = i.getStringExtra("userName");


        textViewWelcome= (TextView)findViewById(R.id.textViewTeacherWelcome);

        textViewWelcome.setText("Welcome "+userName);


        editTextMessage = (EditText)findViewById(R.id.editTextTeacherMessage);
        editTextSubject = (EditText)findViewById(R.id.editTextTeacherSubject);

        buttonSend = (Button)findViewById(R.id.buttonTeacherSend);

        dbHelper = new DBHelper(getApplicationContext());

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long i = dbHelper.AddNewMessage(userName , editTextSubject.getText().toString().trim() , editTextMessage.getText().toString());
                if( i > 0 ){
                    Toast.makeText(TeacherActivity.this, "Insert Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TeacherActivity.this, "Insert NOT Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
